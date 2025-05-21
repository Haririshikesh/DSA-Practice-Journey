/*
Problem Title: Course Schedule II
Problem Link: [https://leetcode.com/problems/course-schedule-ii/](https://leetcode.com/problems/course-schedule-ii/)

Problem Description:
There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1.
You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.

For example, the pair [0, 1] indicates that to take course 0 you have to first take course 1.

Return the ordering of courses you should take to finish all courses. If there are many valid answers, return any of them. If it is impossible to finish all courses, return an empty array.

Example 1:
Input: numCourses = 2, prerequisites = [[1,0]]
Output: [0,1]
Explanation: There are a total of 2 courses to take. To take course 1 you should have finished course 0. So the correct course order is [0,1].

Example 2:
Input: numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]]
Output: [0,1,2,3] or [0,2,1,3]
Explanation: There are a total of 4 courses to take. To take course 3 you should have finished both courses 1 and 2. Both courses 1 and 2 should be taken after you finished course 0.
So one possible course order is [0,1,2,3]. Another possible course order is [0,2,1,3].

Example 3:
Input: numCourses = 1, prerequisites = []
Output: [0]

Initial Intuition:
Similar to "Course Schedule I," this problem can be modeled as a directed graph. The task is to find a valid sequence of courses to take such that all prerequisites are satisfied. If such a sequence exists, it's a topological sort of the graph. If a cycle exists, then no such valid ordering is possible, and we should return an empty array.

Approach: Topological Sort (Kahn's Algorithm - BFS-based)
Kahn's algorithm is a direct way to find a topological sort. It works by maintaining a count of incoming edges (in-degree) for each node. Nodes with an in-degree of 0 can be taken first. As we "take" a course, we reduce the in-degree of all courses that depend on it. If a dependent course's in-degree drops to 0, it can then be taken. We collect these courses in order. If, at the end, the number of collected courses equals the total number of courses, we have a valid order; otherwise, a cycle exists.

Detailed Explanation of Kahn's Algorithm:
1.  **Build Adjacency List (`adj`) and In-degrees (`indegree`):**
    * Initialize an `indegree` array of size `numCourses` to store the number of prerequisites for each course.
    * Initialize an adjacency list `adj` (array of lists) where `adj[i]` will store a list of courses that have `i` as a prerequisite.
    * Iterate through the `prerequisites` array `[course, preq]`:
        * Add `course` to `adj[preq]` (representing an edge from `preq` to `course`).
        * Increment `indegree[course]`.

2.  **Initialize Queue:**
    * Create a queue `q` (e.g., `LinkedList`).
    * Add all courses with an `indegree` of 0 to the queue. These are the courses that can be taken first as they have no outstanding prerequisites.

3.  **Process Queue (BFS Traversal) and Collect Order:**
    * Initialize an `ans` array of size `numCourses` to store the final course order.
    * Initialize an `index` variable (e.g., `ind`) to 0, which will track the current position in the `ans` array.
    * While the queue is not empty:
        * Dequeue a `curr` course from the queue.
        * Add `curr` to the `ans` array at the current `index` and then increment `index`.
        * For each `dependentCourse` that `curr` is a prerequisite for (i.e., for each neighbor `dependentCourse` in `adj[curr]`):
            * Decrement `indegree[dependentCourse]` (because `curr` has now been taken).
            * If `indegree[dependentCourse]` becomes 0, it means all its prerequisites have been met. Enqueue `dependentCourse`.

4.  **Check for Cycles and Return Result:**
    * After the queue becomes empty, if `index` is equal to `numCourses`, it means all courses were processed and a valid topological order was found. Return the `ans` array.
    * If `index` is less than `numCourses`, it means some courses could not be processed because they were part of a cycle. In this case, return an empty array `new int[] {}`.

Time Complexity: O(V + E)
Where V is `numCourses` (number of vertices/courses) and E is the number of `prerequisites` (number of edges).
- Building the adjacency list and in-degree array takes O(V + E) time.
- Each vertex is enqueued and dequeued at most once.
- Each edge is processed at most once (when its source vertex is dequeued).

Space Complexity: O(V + E)
- `indegree` array: O(V)
- `adj` (adjacency list): O(V + E) (stores all vertices and edges)
- Queue: In the worst case, it can hold up to O(V) vertices.
- `ans` array: O(V)
*/
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Arrays; // For printing arrays in main

class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // 1. Build Adjacency List (adj) and In-degrees (indegree)
        int[] indegree = new int[numCourses];
        List<Integer>[] adj = new List[numCourses];

        for (int i = 0; i < numCourses; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] p : prerequisites) {
            int course = p[0]; // Course to take
            int preq = p[1];   // Prerequisite for 'course'
            adj[preq].add(course); // Add edge from preq -> course
            indegree[course]++;    // Increment in-degree of the course that has a prerequisite
        }

        // 2. Initialize Queue with courses having 0 in-degree
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                q.offer(i);
            }
        }

        // 3. Process Queue (BFS Traversal) and Collect Order
        int[] ans = new int[numCourses];
        int ind = 0; // Index for the ans array

        while (!q.isEmpty()) {
            int curr = q.poll();
            ans[ind++] = curr; // Add current course to the order

            // For each course that 'curr' is a prerequisite for
            for (int dependentCourse : adj[curr]) {
                indegree[dependentCourse]--; // Decrement its in-degree
                if (indegree[dependentCourse] == 0) {
                    q.offer(dependentCourse); // If all prerequisites met, add to queue
                }
            }
        }

        // 4. Check for Cycles and Return Result
        // If 'ind' (number of courses added to ans) equals numCourses, all courses could be finished.
        // Otherwise, a cycle exists.
        return ind == numCourses ? ans : new int[]{};
    }

    /*
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Example 1
        int numCourses1 = 2;
        int[][] prerequisites1 = {{1, 0}}; // To take 1, must take 0
        System.out.println("Course Order (Example 1): " + Arrays.toString(solution.findOrder(numCourses1, prerequisites1))); // Expected: [0, 1]

        // Example 2
        int numCourses2 = 4;
        int[][] prerequisites2 = {{1, 0}, {2, 0}, {3, 1}, {3, 2}};
        System.out.println("Course Order (Example 2): " + Arrays.toString(solution.findOrder(numCourses2, prerequisites2))); // Expected: [0, 1, 2, 3] or [0, 2, 1, 3]

        // Example 3
        int numCourses3 = 1;
        int[][] prerequisites3 = {};
        System.out.println("Course Order (Example 3): " + Arrays.toString(solution.findOrder(numCourses3, prerequisites3))); // Expected: [0]

        // Example 4: Cycle present
        int numCourses4 = 2;
        int[][] prerequisites4 = {{1, 0}, {0, 1}}; // 1 requires 0, 0 requires 1 (cycle)
        System.out.println("Course Order (Example 4): " + Arrays.toString(solution.findOrder(numCourses4, prerequisites4))); // Expected: []
    }
    */
}