/*
Problem Title: Find Redundant Connection
Problem Link: [https://leetcode.com/problems/find-redundant-connection/](https://leetcode.com/problems/find-redundant-connection/)

Problem Description:
In this problem, a tree is an undirected graph that is connected and has no cycles.
You are given a graph that started as a tree with n nodes labeled from 1 to n, with one additional edge added. The added edge has two different vertices, and it forms a cycle.
Return the edge that was added to form the cycle. If there are multiple such edges, return the one that appears last in the input.

Example 1:
Input: edges = [[1,2],[1,3],[2,3]]
Output: [2,3]
Explanation: The original tree could be [[1,2],[1,3]]. Adding [2,3] creates a cycle.

Example 2:
Input: edges = [[1,2],[2,3],[3,4],[1,4],[1,5]]
Output: [1,4]
Explanation: The original tree could be [[1,2],[2,3],[3,4],[1,5]]. Adding [1,4] creates a cycle.

Initial Intuition:
We are given a graph that was originally a tree, and then one extra edge was added, which created a cycle. We need to find this specific edge. A key property of a tree is that it has N nodes and N-1 edges, and there is exactly one path between any two nodes. If we add an Nth edge to a tree, it *must* create a cycle. The problem asks for the *last* such edge in the input `edges` array that forms a cycle. This suggests processing edges one by one.

Approach: Union-Find (Disjoint Set Union - DSU)
This problem is a classic application of the Union-Find data structure. Union-Find is used to keep track of a set of elements partitioned into a number of disjoint (non-overlapping) subsets. It provides two main operations:
1.  `find(i)`: Determines which subset a particular element `i` belongs to. It returns the "representative" (or "root") of that subset.
2.  `union(x, y)`: Merges two subsets into a single subset.

We can iterate through the given `edges` one by one. For each edge `(u, v)`:
-   We check if `u` and `v` already belong to the same connected component (i.e., `find(u) == find(v)`).
-   If they do, it means adding this edge `(u, v)` would create a cycle, because `u` and `v` are already connected by a path. Since we are iterating through the edges in order, this `(u, v)` is the *first* edge encountered that creates a cycle, and thus, it's the redundant connection we are looking for.
-   If `u` and `v` belong to different components, we `union` them, effectively merging their components into one.

Detailed Explanation of Union-Find Operations:

1.  **`parents` array:** `parents[i]` stores the parent of element `i`. If `parents[i] == i`, then `i` is the root (representative) of its set.
2.  **`ranks` array (or `sizes` array):** This array is used for optimization during the `union` operation, specifically for "union by size" (or "union by rank"). In your provided code, `ranks[i]` seems to store the size of the component if `i` is a root. This helps in keeping the tree structure flat and balanced, improving performance.
    * When merging two sets, the root of the smaller set is attached to the root of the larger set. This minimizes the depth of the resulting tree.
    * If sizes are equal, either can be attached to the other, and the new root's size is updated.

3.  **`find(i, parents)` operation (with Path Compression):**
    * This function recursively finds the root of the set containing `i`.
    * **Path Compression:** During the traversal up to the root, it also updates the `parents` of all visited nodes directly to the root. This flattens the tree, making future `find` operations faster.
    * `if (parents[i] == i)`: Base case, `i` is its own parent, so it's the root.
    * `parents[i] = find(parents[i], parents)`: Recursively find the root of `parents[i]` and then set `parents[i]` directly to that root.

4.  **`union(x, y, ranks, parents)` operation (with Union by Size):**
    * Find the roots of `x` and `y` using the `find` operation: `rootX = find(x, parents)`, `rootY = find(y, parents)`.
    * **Cycle Detection:** If `rootX == rootY`, it means `x` and `y` are already in the same set. Adding an edge between them would create a cycle. Return `false` to indicate a redundant connection.
    * **Merging Sets (Union by Size):**
        * If `ranks[rootX] > ranks[rootY]`: Attach `rootY` (the smaller set) to `rootX`. Update `parents[rootY] = rootX`. Update the size of `rootX`: `ranks[rootX] += ranks[rootY]`.
        * If `ranks[rootY] > ranks[rootX]`: Attach `rootX` (the smaller set) to `rootY`. Update `parents[rootX] = rootY`. Update the size of `rootY`: `ranks[rootY] += ranks[rootX]`.
        * If `ranks[rootX] == ranks[rootY]`: Attach `rootY` to `rootX` (arbitrary choice). Update `parents[rootY] = rootX`. Update the size of `rootX`: `ranks[rootX] += ranks[rootY]`.
    * Return `true` to indicate a successful union (no cycle formed yet).

Time Complexity: O(N + E * α(N))
Where N is the number of nodes (up to `edges.length + 1`), E is the number of edges, and α is the inverse Ackermann function, which grows extremely slowly. For all practical purposes, α(N) is a very small constant (less than 5 for any realistic N). Thus, the time complexity is effectively almost linear, O(N + E).

Space Complexity: O(N)
This is due to storing the `parents` array and the `ranks` (or `sizes`) array, both of which are proportional to the number of nodes.
*/
import java.util.Arrays; // For printing arrays in main

class Solution {
    // parents[i] stores the parent of element i. If parents[i] == i, i is the root.
    private int[] parents;
    // ranks[i] stores the size of the component if i is a root, used for union by size optimization.
    private int[] ranks;

    // find operation with path compression
    private int find(int i) {
        if (parents[i] == i) {
            return i;
        }
        // Path compression: set parent[i] directly to the root
        parents[i] = find(parents[i]);
        return parents[i];
    }

    // union operation with union by size optimization
    // Returns true if union was successful (no cycle formed), false if a cycle was detected
    private boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        // If both elements already have the same root, adding this edge creates a cycle.
        if (rootX == rootY) {
            return false; // Redundant connection found
        }

        // Union by size: attach the smaller tree to the root of the larger tree
        if (ranks[rootX] > ranks[rootY]) {
            parents[rootY] = rootX;
            ranks[rootX] += ranks[rootY]; // Update size of the new root
        } else { // ranks[rootY] >= ranks[rootX] (includes the case where ranks are equal)
            parents[rootX] = rootY;
            ranks[rootY] += ranks[rootX]; // Update size of the new root
        }
        return true; // Union successful, no cycle yet
    }

    public int[] findRedundantConnection(int[][] edges) {
        // Initialize parents and ranks arrays. Nodes are 1-indexed.
        // The number of nodes is edges.length (N) + 1 because nodes are from 1 to N.
        // Max node label is edges.length
        int numNodes = edges.length; // Max node label is N, so array size N+1 for 1-indexing
        parents = new int[numNodes + 1];
        ranks = new int[numNodes + 1];

        // Initialize each node as its own parent and its rank/size as 1
        for (int i = 1; i <= numNodes; i++) {
            parents[i] = i;
            ranks[i] = 1; // Each node initially forms a set of size 1
        }

        // Iterate through the edges
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            // If union returns false, it means adding this edge created a cycle
            if (!union(u, v)) {
                return edge; // This is the redundant connection (the one encountered last)
            }
        }

        // This line should theoretically not be reached if the problem guarantees
        // exactly one redundant connection. It's a fallback.
        return new int[0];
    }

    /*
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Example 1
        int[][] edges1 = {{1,2},{1,3},{2,3}};
        System.out.println("Redundant connection for edges1: " + Arrays.toString(solution.findRedundantConnection(edges1))); // Expected: [2, 3]

        // Reset solution for next test case (important for instance variables like parents, ranks)
        solution = new Solution();

        // Example 2
        int[][] edges2 = {{1,2},{2,3},{3,4},{1,4},{1,5}};
        System.out.println("Redundant connection for edges2: " + Arrays.toString(solution.findRedundantConnection(edges2))); // Expected: [1, 4]

        // Reset solution for next test case
        solution = new Solution();

        // Custom Test Case: No redundant connection (just a tree)
        int[][] edges3 = {{1,2},{2,3},{3,1}}; // This is a cycle, so [3,1] should be redundant
        System.out.println("Redundant connection for edges3: " + Arrays.toString(solution.findRedundantConnection(edges3))); // Expected: [3, 1]

        // Reset solution for next test case
        solution = new Solution();

        // Custom Test Case: A larger tree with one redundant connection
        int[][] edges4 = {{1,2},{2,3},{4,5},{1,4},{3,5}};
        System.out.println("Redundant connection for edges4: " + Arrays.toString(solution.findRedundantConnection(edges4))); // Expected: [3, 5]
    }
    */
}