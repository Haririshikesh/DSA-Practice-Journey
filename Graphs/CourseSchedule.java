/*
Problem Title: Course Schedule
Problem Link: [https://leetcode.com/problems/course-schedule/](https://leetcode.com/problems/course-schedule/)

Problem Description:
There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1.
You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.

For example, the pair [0, 1] indicates that to take course 0 you have to first take course 1.

Return true if you can finish all courses. Otherwise, return false.

Example 1:
Input: numCourses = 2, prerequisites = [[1,0]]
Output: true
Explanation: There are a total of 2 courses to take.
To take course 1 you should have finished course 0. So it is possible.

Example 2:
Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
Output: false
Explanation: There are a total of 2 courses to take.
To take course 1 you should have finished course 0, and to take course 0 you should also have finished course 1. So it is impossible.

Initial Intuition:
This problem can be modeled as a directed graph where courses are nodes and a prerequisite `[a, b]` (to take 'a', you must take 'b') represents a directed edge from `b` to `a` (b -> a). We need to determine if it's possible to complete all courses. This is equivalent to checking if there is a cycle in the directed graph. If there's a cycle (e.g., A -> B -> C -> A), you can never start any course within that cycle. If there are no cycles, a topological sort is possible, meaning all courses can be finished.

Approach: Topological Sort (Kahn's Algorithm - BFS-based)
Kahn's algorithm for topological sorting is ideal for detecting cycles. It works by iteratively removing nodes that have no incoming edges (an in-degree of 0). If, at the end, we have processed all nodes, then there was no cycle. If some nodes remain unprocessed, it indicates a cycle.

Detailed Explanation of Kahn's Algorithm:
1.  **Build Adjacency List and In-degrees:**
    * Create an adjacency list (`deps` or `adj`) to represent the graph. `deps[i]` will store a list of courses that have `i` as a prerequisite.
    * Create an `in-degree` array where `in-degree[i]` stores the number of prerequisites that course `i` has.
    * Iterate through the `prerequisites` array. For each `[course, preq]`:
        * Add `course` to `deps[preq]`.
        * Increment `in-degree[course]`.

2.  **Initialize Queue:**
    * Create a queue (`q`) and add all courses that have an `in-degree` of 0. These are the courses that have no prerequisites and can be taken first.

3.  **Process Queue (BFS Traversal):**
    * Initialize a counter `coursesTaken` to 0.
    * While the queue is not empty:
        * Dequeue a `curr` course.
        * Increment `coursesTaken`.
        * For each `dependent` course that `curr` is a prerequisite for (i.e., for each neighbor `dep` in `deps[curr]`):
            * Decrement the `in-degree` of `dep` (because `curr` has now been taken).
            * If `in-degree[dep]` becomes 0, it means all its prerequisites have now been met. Enqueue `dep`.

4.  **Check for Cycles:**
    * After the queue becomes empty, compare `coursesTaken` with `numCourses`.
    * If `coursesTaken == numCourses`, it means all courses were successfully processed (a valid topological order was found), so return `true`.
    * If `coursesTaken < numCourses`, it means there are still courses whose `in-degree` never reached 0. These courses must be part of a cycle, so it's impossible to finish all courses. Return `false`.

Time Complexity: O(V + E)
Where V is `numCourses` (number of vertices/courses) and E is the number of `prerequisites` (number of edges).
- Building the adjacency list and in-degree array takes O(V + E) time.
- Each vertex is enqueued and dequeued at most once.
- Each edge is processed at most once (when its source vertex is dequeued).

Space Complexity: O(V + E)
- `in-degree` array: O(V)
- `deps` (adjacency list): O(V + E) (stores all vertices and edges)
- Queue: In the worst case, it can hold up to O(V) vertices.
*/
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class CourseSchedule {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 1. Build Adjacency List (deps) and In-degrees
        int[] indegree = new int[numCourses];
        List<Integer>[] adj = new List[numCourses]; // Renamed deps to adj for clarity as adjacency list

        for (int i = 0; i < numCourses; i++) {
            adj[i] = new ArrayList<>(); // Initialize each list
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

        // 3. Process Queue (BFS Traversal)
        int coursesTaken = 0;
        while (!q.isEmpty()) {
            int curr = q.poll();
            coursesTaken++;

            // For each course that 'curr' is a prerequisite for
            // (i.e., iterate through neighbors of 'curr' in adjacency list)
            for (int dependentCourse : adj[curr]) {
                indegree[dependentCourse]--; // Decrement its in-degree
                if (indegree[dependentCourse] == 0) {
                    q.offer(dependentCourse); // If all prerequisites met, add to queue
                }
            }
        }

        // 4. Check for Cycles
        return coursesTaken == numCourses;
    }

    /*
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Example 1: Possible
        int numCourses1 = 2;
        int[][] prerequisites1 = {{1, 0}}; // To take 1, must take 0
        System.out.println("Can finish courses (Example 1): " + solution.canFinish(numCourses1, prerequisites1)); // Expected: true

        // Example 2: Impossible (Cycle)
        int numCourses2 = 2;
        int[][] prerequisites2 = {{1, 0}, {0, 1}}; // 1 requires 0, 0 requires 1 (cycle)
        System.out.println("Can finish courses (Example 2): " + solution.canFinish(numCourses2, prerequisites2)); // Expected: false

        // Example 3: More complex - Possible
        int numCourses3 = 4;
        int[][] prerequisites3 = {{1,0},{2,0},{3,1},{3,2}}; // 1<-0, 2<-0, 3<-1, 3<-2
        System.out.println("Can finish courses (Example 3): " + solution.canFinish(numCourses3, prerequisites3)); // Expected: true

        // Example 4: Impossible (Cycle)
        int numCourses4 = 3;
        int[][] prerequisites4 = {{1,0},{2,1},{0,2}}; // 1<-0, 2<-1, 0<-2 (cycle 0->1->2->0)
        System.out.println("Can finish courses (Example 4): " + solution.canFinish(numCourses4, prerequisites4)); // Expected: false
    }
    */
}