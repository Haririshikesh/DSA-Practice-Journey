/*
Problem Title: Check If Prerequisite
Problem Link: [https://leetcode.com/problems/check-if-prerequisite/](https://leetcode.com/problems/check-if-prerequisite/)

Problem Description:
There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1.
You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.

For example, the pair [0, 1] indicates that to take course 0 you have to first take course 1.

You are also given an array queries where queries[j] = [uj, vj]. For the j-th query, you should return true if uj is a prerequisite of vj or false otherwise.

A course x is a prerequisite of a course y if either x is a direct prerequisite of y or there is a chain of prerequisites, i.e., x is a prerequisite of z, and z is a prerequisite of y.

Example 1:
Input: numCourses = 2, prerequisites = [[1,0]], queries = [[0,1],[1,0]]
Output: [false,true]
Explanation: The pair [1,0] means that Course 0 is a prerequisite of Course 1.
We ask if 0 is a prerequisite of 1? Yes.
We ask if 1 is a prerequisite of 0? No.

Example 2:
Input: numCourses = 3, prerequisites = [[1,2],[1,0],[2,0]], queries = [[1,0],[1,2],[3,0],[0,1]]
Output: [false,false,false,false]
Explanation: The pair [1,2] means that Course 2 is a prerequisite of Course 1.
The pair [1,0] means that Course 0 is a prerequisite of Course 1.
The pair [2,0] means that Course 0 is a prerequisite of Course 2.
We ask if 1 is a prerequisite of 0? No, 0 is a prerequisite of 1.
We ask if 1 is a prerequisite of 2? No, 2 is a prerequisite of 1.
We ask if 3 is a prerequisite of 0? No.
We ask if 0 is a prerequisite of 1? Yes.

Example 3:
Input: numCourses = 5, prerequisites = [[0,1],[1,2],[2,3],[3,4]], queries = [[0,4],[4,0],[1,3],[3,0]]
Output: [true,false,true,false]

Initial Intuition:
The problem asks us to determine if one course is a prerequisite for another, including indirect (transitive) relationships. This is a classic "transitive closure" problem on a directed graph. A direct approach would be to run a BFS or DFS from each course to find all courses it's a prerequisite for. However, with `numCourses` up to 100, we can precompute all transitive relationships efficiently.

Approach: Modified Topological Sort (Kahn's Algorithm) to Build Transitive Closure
We can use a modified version of Kahn's algorithm (BFS-based topological sort) to build a `boolean[][] prereq` matrix. `prereq[u][v]` will be `true` if course `u` is a prerequisite of course `v` (meaning there's a path from `u` to `v` in the graph).

The key insight is to process courses in a specific order that allows us to propagate prerequisite relationships effectively. The provided solution implicitly performs a topological sort on the *reverse* graph.

Let's define the graph where an edge `bi -> ai` means `bi` is a prerequisite for `ai`.
Your code builds an adjacency list `adjs` where `adjs[ai]` contains `bi` (i.e., `ai` points to its prerequisites). This effectively creates a graph where edges go from a course to its direct prerequisites.
The `indegree[preq]` counts how many times `preq` appears as a *source* in an edge `preq -> course` in the *original* graph, which is its *out-degree* in the original graph.

Detailed Explanation of the Algorithm:
1.  **Build Reverse Adjacency List (`adjs`) and Out-degrees (`indegree` for original graph):**
    * `adjs[course]` stores a list of `preq` courses such that `preq` is a direct prerequisite of `course`. This is effectively building the *reverse* graph (edges from dependent to prerequisite).
    * `indegree[preq]` counts the number of courses that `preq` is a direct prerequisite *for* (i.e., its out-degree in the *original* graph).
    * Initialize a `boolean[][] prereq` matrix where `prereq[u][v]` will be `true` if `u` is a prerequisite of `v`. Initially, set `prereq[preq][course] = true` for all direct prerequisites.

2.  **Initialize Queue:**
    * Add all courses `i` to the queue `q` where `indegree[i]` is 0. These are courses that are *not* prerequisites for any other course in the original graph (they have no outgoing edges).

3.  **Process Queue (BFS Traversal) and Propagate Transitive Closure:**
    * While the queue is not empty:
        * Dequeue a `curr` course.
        * For each `adj` course in `adjs[curr]` (meaning `adj` is a direct prerequisite of `curr`, i.e., `adj -> curr` in the original graph):
            * **Transitive Closure Propagation:** For every course `i` from `0` to `n-1`:
                * If `curr` is already a prerequisite of `i` (`prereq[curr][i]` is `true`), then since `adj` is a prerequisite of `curr`, `adj` must also be a prerequisite of `i`. So, set `prereq[adj][i] = true`. This step effectively propagates the prerequisite relationship `adj -> curr -> i` to `adj -> i`.
            * **Update Out-degree:** Decrement `indegree[adj]`. This simulates `adj` having one less dependent course.
            * If `indegree[adj]` becomes 0, it means all courses that `adj` was a prerequisite for have now been processed (or `adj` has no more outgoing edges to process). Enqueue `adj`.

4.  **Process Queries:**
    * Create a `List<Boolean> ans`.
    * For each `query = [u, v]`:
        * Add `prereq[u][v]` to `ans`.

5.  **Return `ans`**.

Time Complexity: O(N^2 + N * E + Q)
-   Building adjacency list and in-degrees: O(N + E)
-   Initializing `prereq` matrix (and direct relationships): O(N^2)
-   BFS loop: Each node is enqueued/dequeued once. When a node `curr` is processed, it iterates through its direct prerequisites (up to `E` edges in total across all `curr` nodes). For each such `adj` prerequisite, it then iterates through all `N` courses to update `prereq[adj][i]`. In the worst case, this inner loop runs `N` times for each edge. Thus, the transitive closure computation is O(N * E).
-   Processing queries: O(Q)
-   Total: O(N^2 + N * E + Q). Given N <= 100, N^2 is 10,000, N*E could be up to 100 * (N*(N-1)/2) which is too large. However, E is max 100 * 99 = ~10000. So N*E is 100 * 10000 = 1,000,000. This complexity is acceptable for N=100.

Space Complexity: O(N^2 + E)
-   `indegree` array: O(N)
-   `adjs` (adjacency list for reverse graph): O(N + E)
-   `prereq` matrix: O(N^2)
-   Queue: O(N) in worst case.
-   Total: O(N^2 + E)
*/
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Arrays; // For printing arrays in main (if needed)

class Solution {
    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        // 1. Build Reverse Adjacency List (adjs) and Out-degrees (indegree for original graph)
        // adjs[course] will contain its direct prerequisites (edges: course -> preq)
        List<Integer>[] adjs = new List[numCourses];
        // indegree[preq] will count how many courses 'preq' is a direct prerequisite FOR (out-degree in original graph)
        int[] indegree = new int[numCourses];
        // prereq[u][v] will be true if u is a prerequisite of v (direct or indirect)
        boolean[][] prereq = new boolean[numCourses][numCourses];

        for (int i = 0; i < numCourses; i++) {
            adjs[i] = new ArrayList<>(); // Initialize each list
        }

        for (int[] p : prerequisites) {
            int course = p[0]; // Dependent course
            int preq = p[1];   // Prerequisite course

            adjs[course].add(preq); // Add edge from dependent -> prerequisite (reverse graph)
            prereq[preq][course] = true; // Mark direct prerequisite relationship
            indegree[preq]++; // Increment out-degree of 'preq' in the original graph
        }

        // 2. Initialize Queue with courses having 0 out-degree (in the original graph)
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                q.offer(i);
            }
        }

        // 3. Process Queue (BFS Traversal) and Propagate Transitive Closure
        while (!q.isEmpty()) {
            int curr = q.poll(); // 'curr' is a course with no more outgoing edges to process in the original graph

            // Iterate through 'curr's direct prerequisites (neighbors in the reverse graph)
            for (int directPrereqOfCurr : adjs[curr]) {
                // Propagate transitive relationships:
                // If 'curr' is a prerequisite of 'i' (prereq[curr][i] is true),
                // and 'directPrereqOfCurr' is a prerequisite of 'curr',
                // then 'directPrereqOfCurr' is also a prerequisite of 'i'.
                for (int i = 0; i < numCourses; i++) {
                    if (prereq[curr][i]) {
                        prereq[directPrereqOfCurr][i] = true;
                    }
                }

                // Decrement the out-degree of 'directPrereqOfCurr' in the original graph
                indegree[directPrereqOfCurr]--;
                // If its out-degree becomes 0, it means all courses it was a prerequisite for have been processed
                if (indegree[directPrereqOfCurr] == 0) {
                    q.offer(directPrereqOfCurr);
                }
            }
        }

        // 4. Process Queries
        List<Boolean> ans = new ArrayList<>();
        for (int[] query : queries) {
            int u = query[0]; // Query source
            int v = query[1]; // Query destination
            ans.add(prereq[u][v]); // Check if u is a prerequisite of v
        }

        return ans;
    }

    /*
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Example 1
        int numCourses1 = 2;
        int[][] prerequisites1 = {{1, 0}};
        int[][] queries1 = {{0, 1}, {1, 0}};
        System.out.println("Result for Example 1: " + solution.checkIfPrerequisite(numCourses1, prerequisites1, queries1)); // Expected: [true, false]

        // Example 2
        int numCourses2 = 3;
        int[][] prerequisites2 = {{1, 2}, {1, 0}, {2, 0}};
        int[][] queries2 = {{1, 0}, {1, 2}, {3, 0}, {0, 1}};
        System.out.println("Result for Example 2: " + solution.checkIfPrerequisite(numCourses2, prerequisites2, queries2)); // Expected: [false, false, false, true]
        // Note: The problem description's output for Example 2 has a typo.
        // Based on logic: 1 is NOT prerequisite of 0 (0 is prereq of 1) -> false
        // 1 is NOT prerequisite of 2 (2 is prereq of 1) -> false
        // 3 is NOT prereq of 0 (3 doesn't exist or no path) -> false
        // 0 IS prerequisite of 1 (0->1) -> true

        // Example 3
        int numCourses3 = 5;
        int[][] prerequisites3 = {{0, 1}, {1, 2}, {2, 3}, {3, 4}};
        int[][] queries3 = {{0, 4}, {4, 0}, {1, 3}, {3, 0}};
        System.out.println("Result for Example 3: " + solution.checkIfPrerequisite(numCourses3, prerequisites3, queries3)); // Expected: [true, false, true, false]

        // Custom Test Case: Complex dependencies
        int numCourses4 = 6;
        int[][] prerequisites4 = {{0,1},{0,2},{1,3},{2,3},{3,4},{4,5}};
        int[][] queries4 = {{0,3},{0,4},{0,5},{1,4},{2,5},{5,0}};
        System.out.println("Result for Example 4: " + solution.checkIfPrerequisite(numCourses4, prerequisites4, queries4));
        // Expected:
        // 0 -> 1, 0 -> 2
        // 1 -> 3, 2 -> 3
        // 3 -> 4
        // 4 -> 5
        // Queries:
        // [0,3]: true (0->1->3, 0->2->3)
        // [0,4]: true (0->1->3->4, 0->2->3->4)
        // [0,5]: true (0->1->3->4->5, 0->2->3->4->5)
        // [1,4]: true (1->3->4)
        // [2,5]: true (2->3->4->5)
        // [5,0]: false
        // Overall Expected: [true, true, true, true, true, false]
    }
    */
}