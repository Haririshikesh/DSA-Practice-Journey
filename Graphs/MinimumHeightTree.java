/*
Problem Title: Minimum Height Trees
Problem Link: [https://leetcode.com/problems/minimum-height-trees/](https://leetcode.com/problems/minimum-height-trees/)

Problem Description:
A tree is an undirected graph in which any two vertices are connected by exactly one path. In other words, any connected graph without simple cycles is a tree.

Given a tree of `n` nodes labeled from `0` to `n - 1`, and an array of `n - 1` `edges` where `edges[i] = [ai, bi]` indicates that there is an undirected edge between the two nodes `ai` and `bi` in the tree.

We can choose any node as the root. When we choose a node as the root, the tree becomes a rooted tree. The height of a rooted tree is the maximum distance from the root to any leaf.

Return a list of all `minimum height trees` (MHTs). You can return the answer in any order.
The height of a tree is the number of edges in the longest path from the root to a leaf.

Example 1:
Input: n = 4, edges = [[1,0],[1,2],[1,3]]
Output: [1]
Explanation: As shown, the tree is:
        0
        |
        1
       / \
      2   3
If node 1 is the root, the height is 1. If any other node is the root, the height is 2.
So, node 1 is the only MHT root.

Example 2:
Input: n = 6, edges = [[3,0],[3,1],[3,2],[3,4],[5,4]]
Output: [3,4]
Explanation: As shown, the tree is:
  0  1  2
   \ | /
     3
     |
     4
     |
     5
If node 3 is the root, height is 3. If node 4 is the root, height is 2.
The roots of MHTs are 3 and 4.

Constraints:
- 1 <= n <= 2 * 10^4
- edges.length == n - 1
- 0 <= ai, bi < n
- ai != bi
- The given input always represents a tree.

Initial Intuition:
The problem asks for the root(s) that result in the minimum height for the tree. Intuitively, the roots of minimum height trees should be located "in the middle" or "center" of the tree. If we imagine repeatedly trimming the leaves of a tree, the nodes that remain at the very end will be the center(s). This process is similar to peeling an onion layer by layer.

Approach: Leaf Peeling (BFS-based Iterative Removal)
This problem can be solved using a BFS-based approach that simulates removing leaf nodes layer by layer.
1.  **Identify Leaves:** Start by finding all initial leaf nodes (nodes with a degree of 1). Add these leaves to a queue.
2.  **Iterative Trimming:** In each step (or "minute"), remove all current leaf nodes. When a leaf node is removed, its neighbor's degree decreases. If a neighbor's degree drops to 1, that neighbor effectively becomes a new leaf node in the "shrunk" tree, and we add it to the queue for the next minute's processing.
3.  **Stopping Condition:** We continue this process until only 1 or 2 nodes remain. These remaining nodes are the roots of the Minimum Height Trees. A tree can have at most two MHT roots (e.g., in a path graph with an even number of nodes, the two middle nodes are MHT roots). If `n` is 1, the root is simply that single node.

Detailed Explanation of Leaf Peeling Algorithm:

1.  **Handle Base Case (n=1):** If `n` is 1, the tree consists of a single node (node 0). This node is trivially the root of the MHT. We return `Collections.singletonList(0)`.
    * **Why `Collections.singletonList(0)` instead of `new ArrayList<>(0)` or `new ArrayList<>()` and then adding?**
        * `Collections.singletonList(0)` returns an immutable list containing only the specified element (0). It's very efficient in terms of both time and space because it doesn't create a new `ArrayList` object that might need to resize. It's a specialized, optimized list for a single element.
        * `new ArrayList<>(0)` creates an empty `ArrayList` with an initial capacity of 0. You'd then need to `add(0)`. This involves more object creation and potential internal array management than `singletonList`.
        * `new ArrayList<>()` (default constructor) creates an empty `ArrayList` with a default initial capacity (usually 10). Again, less efficient for a single element.
        * Using `singletonList` clearly communicates the intent of returning a list with exactly one element and benefits from Java's internal optimizations.

2.  **Build Adjacency List and Calculate Degrees:**
    * Create an `indegree` array of size `n` to store the degree (number of connections) for each node.
    * Create an `adj` (adjacency list, using `HashMap<Integer, List<Integer>>`) to represent the graph. For each edge `[u, v]`:
        * Increment `indegree[u]` and `indegree[v]`.
        * Add `v` to `adj.get(u)` and `u` to `adj.get(v)` (since it's an undirected graph).
    * **Note:** The provided code uses `mp` instead of `adj` for the adjacency list, which is functionally the same.

3.  **Initialize Queue with Initial Leaves:**
    * Create a queue (`q`).
    * Iterate from `0` to `n-1`. If `indegree[i]` is 1 (meaning it's a leaf), add node `i` to the queue.

4.  **Iterative Removal (BFS Layers):**
    * Initialize `remainingNodes = n`. This variable tracks how many nodes are left in the tree.
    * Loop `while (remainingNodes > 2)`: The process continues until only the center 1 or 2 nodes remain.
        * `currentLayerSize = q.size()`: Get the number of leaves in the current layer.
        * `remainingNodes -= currentLayerSize`: Reduce the count of nodes to be processed in subsequent layers.
        * Loop `for (int i = 0; i < currentLayerSize; i++)`: Process each leaf in the current layer.
            * `leaf = q.poll()`: Dequeue a leaf node.
            * For each `neighbor` of this `leaf` (from `adj.get(leaf)`):
                * Decrement `indegree[neighbor]`. This simulates removing the `leaf` and its edge.
                * If `indegree[neighbor]` becomes 1, it means this `neighbor` has now become a new leaf in the "shrunk" tree. Add `neighbor` to the queue for the next layer.

5.  **Return Remaining Nodes:**
    * After the `while` loop finishes, the `q` will contain either 1 or 2 nodes. These are the roots of the Minimum Height Trees. Return `new ArrayList<>(q)`.

Time Complexity: O(N + E)
**How:**
1.  **Graph Construction (O(N + E)):**
    * Initializing `indegree` array: O(N) operations.
    * Initializing `HashMap` for adjacency lists: O(N) operations (for `put` calls with `new ArrayList<>()`).
    * Processing `edges`: The loop runs `E` times. Inside the loop, `indegree` updates are O(1). `HashMap.computeIfAbsent` and `List.add` operations are O(1) on average. Since each edge `[u, v]` adds `u` to `v`'s list and `v` to `u`'s list, each edge contributes a constant amount of work. Thus, building the graph is O(N + E).
2.  **Initial Queue Population (O(N)):**
    * The loop iterates `N` times. Checking `indegree[i] == 1` and `q.offer(i)` are O(1) operations.
3.  **BFS (Leaf Peeling) Traversal (O(N + E)):**
    * Each node is added to the queue (`q.offer`) at most once (when its degree becomes 1).
    * Each node is removed from the queue (`q.poll`) at most once.
    * When a node is dequeued, we iterate through its neighbors. For an undirected graph, each edge `(u, v)` will be traversed twice in total across the entire BFS process (once when `u` is processed and `v` is its neighbor, and once when `v` is processed and `u` is its neighbor). The `indegree` decrement and `q.offer` are O(1).
    * Therefore, the total work done in the `while` loop is proportional to the sum of degrees of all nodes, which is `2 * E`. So, O(N + E).
**Why:** This algorithm is optimal for tree traversals because it visits each node and each edge a constant number of times. It avoids redundant computations by systematically pruning leaves.

Space Complexity: O(N + E)
**How:**
1.  **`indegree` array:** O(N) space to store degrees for `N` nodes.
2.  **`adj` (HashMap for adjacency lists):** In the worst case (e.g., a star graph), it stores `N` keys (nodes) and `2 * E` values (edges). So, O(N + E) space.
3.  **`q` (Queue):** In the worst case (e.g., a path graph where almost all nodes are leaves in the first few layers), the queue can hold up to O(N) nodes.
**Why:** The space used directly scales with the size of the graph (number of nodes and edges) because we need to explicitly store the graph structure and the auxiliary data for BFS.
*/
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Arrays; // For Arrays.deepToString in main

class Solution {
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        // Base case: If there's only one node, it's the MHT root.
        // Collections.singletonList(0) returns an immutable list containing only the specified element.
        // It's efficient for single-element lists as it avoids creating a new ArrayList object.
        if (n == 1) {
            return Collections.singletonList(0);
        }

        // 1. Build Adjacency List and Calculate Degrees
        // indegree[i] stores the degree of node i
        int[] indegree = new int[n];
        // adj stores the neighbors for each node
        // Using Map<Integer, List<Integer>> to represent the adjacency list
        Map<Integer, List<Integer>> adj = new HashMap<>();

        // Initialize adjacency lists for all nodes
        for (int i = 0; i < n; i++) {
            adj.put(i, new ArrayList<>());
        }

        // Populate adjacency list and degrees from edges
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            indegree[u]++;
            indegree[v]++;
            adj.get(u).add(v);
            adj.get(v).add(u); // Undirected graph
        }

        // 2. Initialize Queue with Initial Leaves (nodes with degree 1)
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 1) { // Degree 1 means it's a leaf
                q.offer(i);
            }
        }

        // 3. Iterative Removal (Leaf Peeling - BFS Layers)
        // remainingNodes tracks how many nodes are left in the tree.
        // We stop when 1 or 2 nodes remain, as these are the MHT roots.
        int remainingNodes = n;
        while (remainingNodes > 2) {
            int currentLayerSize = q.size(); // Number of leaves in the current layer
            remainingNodes -= currentLayerSize; // Remove these leaves from the total count

            // Process all leaves in the current layer
            for (int i = 0; i < currentLayerSize; i++) {
                int leaf = q.poll(); // Dequeue a leaf node

                // For each neighbor of the removed leaf
                for (int neighbor : adj.get(leaf)) {
                    indegree[neighbor]--; // Decrement neighbor's degree
                    // If neighbor's degree becomes 1, it's a new leaf in the shrunk tree
                    if (indegree[neighbor] == 1) {
                        q.offer(neighbor); // Add to queue for next layer
                    }
                }
            }
        }

        // 4. Return Remaining Nodes
        // The queue now contains the 1 or 2 roots of the MHTs.
        return new ArrayList<>(q);
    }

    /*
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Example 1
        int n1 = 4;
        int[][] edges1 = {{1, 0}, {1, 2}, {1, 3}};
        System.out.println("MHT roots for n=" + n1 + ", edges=" + Arrays.deepToString(edges1) + ": " + solution.findMinHeightTrees(n1, edges1)); // Expected: [1]

        // Example 2
        int n2 = 6;
        int[][] edges2 = {{3, 0}, {3, 1}, {3, 2}, {3, 4}, {5, 4}};
        System.out.println("MHT roots for n=" + n2 + ", edges=" + Arrays.deepToString(edges2) + ": " + solution.findMinHeightTrees(n2, edges2)); // Expected: [3, 4] (order might vary)

        // Custom Test Case: Path graph with even nodes
        int n3 = 6;
        int[][] edges3 = {{0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 5}};
        System.out.println("MHT roots for n=" + n3 + ", edges=" + Arrays.deepToString(edges3) + ": " + solution.findMinHeightTrees(n3, edges3)); // Expected: [2, 3] (order might vary)

        // Custom Test Case: Path graph with odd nodes
        int n4 = 5;
        int[][] edges4 = {{0, 1}, {1, 2}, {2, 3}, {3, 4}};
        System.out.println("MHT roots for n=" + n4 + ", edges=" + Arrays.deepToString(edges4) + ": " + solution.findMinHeightTrees(n4, edges4)); // Expected: [2]

        // Custom Test Case: Star graph
        int n5 = 5;
        int[][] edges5 = {{0, 1}, {0, 2}, {0, 3}, {0, 4}};
        System.out.println("MHT roots for n=" + n5 + ", edges=" + Arrays.deepToString(edges5) + ": " + solution.findMinHeightTrees(n5, edges5)); // Expected: [0]
    }
    */
}