import java.util.ArrayList;
import java.util.Arrays; // For Arrays.toString in main method
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set; // More specific than java.util.HashSet

/*
Problem Title: Evaluate Division
Problem Link: [https://leetcode.com/problems/evaluate-division/](https://leetcode.com/problems/evaluate-division/)

Problem Description:
You are given an array of variable pairs `equations` and an array of real numbers `values`, where `equations[i] = [Ai, Bi]` and `values[i]` represent the equation `Ai / Bi = values[i]`. Each `Ai` or `Bi` is a string that represents a single variable.

You are also given an array of queries, where `queries[j] = [Cj, Dj]` represents the `j-th` query for which you should find the answer `Cj / Dj = ?`.

Return the answers to all queries. If a single answer cannot be determined, return -1.0.

Note: The input equations form a directed graph. The nodes are variables, and the edges are division relationships. For example, A/B = 2.0 creates a directed edge from A to B with weight 2.0, and implicitly an edge from B to A with weight 1/2.0.

Example 1:
Input: equations = [["a","b"],["b","c"]], values = [2.0,3.0], queries = [["a","c"],["b","a"],["a","e"],["a","a"],["x","x"]]
Output: [6.00000,0.50000,-1.00000,1.00000,-1.00000]
Explanation:
Given: a / b = 2.0, b / c = 3.0
queries are:
a / c = (a / b) * (b / c) = 2.0 * 3.0 = 6.0
b / a = 1 / (a / b) = 1 / 2.0 = 0.5
a / e = -1.0 (e is not defined)
a / a = 1.0 (same variable)
x / x = -1.0 (x is not defined)

Example 2:
Input: equations = [["a","b"]], values = [0.5], queries = [["a","b"],["b","a"],["a","c"],["x","y"]]
Output: [0.50000,2.00000,-1.00000,-1.00000]

Example 3:
Input: equations = [["a","b"],["b","c"],["bc","cd"]], values = [1.0,1.0,1.0], queries = [["a","c"],["c","f"],["bc","cd"],["cd","bc"]]
Output: [1.00000,-1.00000,1.00000,1.00000]

Constraints:
- 1 <= equations.length <= 100
- equations[i].length == 2
- 1 <= Ai.length, Bi.length <= 5
- values.length == equations.length
- 0.0 < values[i] <= 20.0
- 1 <= queries.length <= 100
- queries[j].length == 2
- 1 <= Cj.length, Dj.length <= 5
- Ai, Bi, Cj, Dj consist of lowercase English letters and digits.

Initial Intuition:
The problem inherently describes a graph where variables are nodes and the division relationships are weighted, directed edges. If we have `A / B = k`, we can think of this as an edge from `A` to `B` with weight `k`. We also know that `B / A = 1/k`, so there's an inverse edge from `B` to `A` with weight `1/k`. To find `C / D`, we need to find a path from `C` to `D` in this graph and multiply the edge weights along that path. This is a classic graph traversal problem (like finding a path in a graph and calculating a product).

Approach: Graph Traversal (DFS)
We can model the variables as nodes in a graph and the division relationships as directed edges with associated weights. For each query, we perform a Depth-First Search (DFS) starting from the `dividend` variable and trying to reach the `divisor` variable. During the DFS, we accumulate the product of edge weights along the path.

Detailed Explanation of the Algorithm:

1.  **Build the Graph (`buildGraph` method):**
    * The graph will be represented using a `HashMap<String, HashMap<String, Double>>`.
        * The outer `HashMap` maps a variable (String) to its neighbors.
        * The inner `HashMap` maps a neighbor variable (String) to the double value of the division (the edge weight). So, `graph.get("A").get("B")` would give the value of `A / B`.
    * Iterate through the `equations` and `values` arrays:
        * For each `equation[i] = [dividend_str, divisor_str]` and `value = values[i]`:
        * Add `dividend_str` and `divisor_str` as keys to the main graph `HashMap` if they don't already exist, initializing their inner `HashMap`s.
        * Add a directed edge from `dividend_str` to `divisor_str` with weight `value`: `graph.get(dividend_str).put(divisor_str, value)`.
        * Crucially, also add the inverse directed edge from `divisor_str` to `dividend_str` with weight `1.0 / value`: `graph.get(divisor_str).put(dividend_str, 1.0 / value)`. This makes the graph effectively bidirectional with inverse weights.

2.  **Process Queries (`calcEquation` method):**
    * Initialize a `double[]` array `res` to store the answers for all queries.
    * For each query `[divisor_q, dividend_q]` in `queries`:
        * **Check for Unknown Variables:** First, check if both the `divisor_q` and `dividend_q` variables exist as nodes in our built graph. If either is not present, it means the value cannot be determined. Set `res[i] = -1.0`.
        * **Handle Self-Division:** If `divisor_q` is the same as `dividend_q`, the result is always `1.0`. Set `res[i] = 1.0`. (This case is implicitly handled by DFS if a path exists, but it's a quick shortcut).
        * **Perform DFS:**
            * Initialize a `HashSet<String> visited` to keep track of nodes visited during the *current* DFS path. This prevents infinite loops in case of cycles and ensures we don't re-explore paths.
            * Use a `double[] ans = {-1.0}`. This is a common pattern in recursive DFS in Java to effectively "return" a value that gets updated deep in the recursion, as primitive types are passed by value. `ans[0]` will store the final result if a path is found.
            * Call the `dfs` helper function: `dfs(divisor_q, dividend_q, grp, visited, ans, 1.0)`. The `1.0` is the initial accumulated product.
            * Store the result from `ans[0]` into `res[i]`.

3.  **DFS Helper (`dfs` method):**
    * `node`: The current variable (node) being visited.
    * `destination`: The target variable (node) we are trying to reach.
    * `graph`: The adjacency list representation of the graph.
    * `visited`: A `Set` to keep track of nodes visited in the current path to prevent cycles.
    * `result`: The `double[]` array to store the final result if found.
    * `currentProduct`: The accumulated product of edge weights from the `start` node to `node`.
    * **Base Cases:**
        * If `visited.contains(node)`: The node has already been visited in this path, indicating a cycle or a redundant path. Stop exploration.
        * If `node.equals(destination)`: The destination has been reached. Set `result[0] = currentProduct` and return.
    * **Recursive Step:**
        * Mark `node` as `visited`.
        * Iterate through all neighbors of `node` from `graph.get(node).entrySet()`:
            * For each `neighbor_str` (key) and its `edge_weight` (value):
                * Recursively call `dfs(neighbor_str, destination, graph, visited, result, currentProduct * edge_weight)`.
                * **Early Exit Optimization:** If `result[0]` is no longer `-1.0` after a recursive call, it means a path to the destination has been found through that branch. We can immediately return to avoid unnecessary further exploration. This is implicit in the provided code's `if(ans[0]!=-1.0)` not being present, but DFS generally explores all paths if an answer isn't set. The implicit return is from the base case.

Time Complexity: O(N_vars + E + Q * (N_vars + E))
Let `N_vars` be the number of unique variables (nodes in the graph).
Let `E` be the number of unique equations (edges in the graph).
Let `Q` be the number of queries.

-   **Building the graph:** O(E)
    -   Each equation involves constant time operations for `HashMap.putIfAbsent` and `HashMap.put` (on average). There are `E` equations.
-   **Processing each query:** O(N_vars + E) in the worst case for each DFS.
    -   In the worst case, a DFS might explore every node and every edge reachable from the starting node.
    -   There are `Q` queries.
-   **Total Time Complexity:** O(E + Q * (N_vars + E)). Since `N_vars` can be up to `2 * E` (if every equation has new variables), and `E` is max 100, `N_vars` is max 200. `Q` is max 100. So, `100 + 100 * (200 + 100)` approximately `100 + 100 * 300 = 30100` operations, which is efficient.

Space Complexity: O(N_vars + E)
-   **Graph representation (`grp`):**
    -   Stores `N_vars` unique variables (outer HashMap keys).
    -   Stores `2 * E` entries in the inner HashMaps (for `A/B` and `B/A`). Each entry involves storing a string and a double.
    -   Therefore, graph storage is O(N_vars + E).
-   **`visited` set during DFS:** In the worst case, a path could include all `N_vars` nodes. So, O(N_vars).
-   **Recursion stack for DFS:** In the worst case, the recursion depth could be `N_vars`. So, O(N_vars).
-   `res` array: O(Q).
-   Overall space complexity is dominated by the graph representation, which is O(N_vars + E).

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set; 
// More specific than java.util.HashSet

class Solution {

    /**
     * Main method to calculate division results for given queries.
     *
     * @param equations A list of variable pairs (e.g., [["a","b"]]).
     * @param values    Corresponding values for the equations (e.g., a/b = 2.0).
     * @param queries   A list of variable pairs for which to find the division result.
     * @return An array of doubles representing the results for each query.
     */
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        // Step 1: Build the graph from equations and values.
        // The graph stores variables as nodes and division values as edge weights.
        // grp.get(A).get(B) represents A / B.
        HashMap<String, HashMap<String, Double>> graph = buildGraph(equations, values);

        // Array to store results for each query.
        double[] results = new double[queries.size()];

        // Step 2: Process each query.
        for (int i = 0; i < queries.size(); i++) {
            String startNode = queries.get(i).get(0); // The numerator/dividend
            String endNode = queries.get(i).get(1);   // The denominator/divisor

            // Edge Case 1: If either variable is not in the graph (never appeared in equations).
            if (!graph.containsKey(startNode) || !graph.containsKey(endNode)) {
                results[i] = -1.0;
            }
            // Edge Case 2: If the start and end nodes are the same, result is 1.0 (e.g., a/a = 1.0).
            // This case is implicitly handled by DFS if a path exists, but it's a quick shortcut.
            else if (startNode.equals(endNode)) {
                results[i] = 1.0;
            }
            // Step 3: Perform DFS to find the path and calculate the product.
            else {
                // visited set for the current DFS path to prevent cycles.
                Set<String> visited = new HashSet<>();
                // ans[0] will store the result from DFS. Initialized to -1.0 (not found).
                double[] ansContainer = {-1.0};
                // Initial product is 1.0.
                double currentProduct = 1.0;

                // Start DFS from startNode to endNode.
                dfs(startNode, endNode, graph, visited, ansContainer, currentProduct);
                results[i] = ansContainer[0]; // Store the found result (or -1.0 if no path).
            }
        }
        return results;
    }

    /**
     * Builds the graph representation from the given equations and values.
     *
     * @param equations List of equations [numerator, denominator].
     * @param values    Values corresponding to the equations.
     * @return A HashMap representing the graph, where graph.get(A).get(B) = A/B.
     */
    public HashMap<String, HashMap<String, Double>> buildGraph(List<List<String>> equations, double[] values) {
        HashMap<String, HashMap<String, Double>> graph = new HashMap<>();

        for (int i = 0; i < equations.size(); i++) {
            String dividend = equations.get(i).get(0);
            String divisor = equations.get(i).get(1);
            double value = values[i];

            // Add nodes to the graph if they don't exist.
            graph.putIfAbsent(dividend, new HashMap<>());
            graph.putIfAbsent(divisor, new HashMap<>());

            // Add edge: dividend -> divisor with weight 'value' (dividend / divisor = value).
            graph.get(dividend).put(divisor, value);
            // Add inverse edge: divisor -> dividend with weight '1.0 / value' (divisor / dividend = 1/value).
            graph.get(divisor).put(dividend, 1.0 / value);
        }
        return graph;
    }

    /**
     * Performs a Depth-First Search to find a path from 'currentNode' to 'destination'
     * and calculate the product of edge weights along the path.
     *
     * @param currentNode    The current node in the DFS traversal.
     * @param destination    The target node to reach.
     * @param graph          The graph representation.
     * @param visited        A set to keep track of visited nodes in the current path.
     * @param resultContainer An array to store the result (passed by reference).
     * @param currentProduct The product of edge weights accumulated so far from the start node to 'currentNode'.
     */
    public void dfs(String currentNode, String destination, HashMap<String, HashMap<String, Double>> graph,
                    Set<String> visited, double[] resultContainer, double currentProduct) {

        // Base Case 1: If the node has already been visited in this path, avoid cycles.
        if (visited.contains(currentNode)) {
            return;
        }

        // Base Case 2: If we reached the destination, store the current product and return.
        if (currentNode.equals(destination)) {
            resultContainer[0] = currentProduct;
            return;
        }

        // Mark the current node as visited for this path.
        visited.add(currentNode);

        // Explore neighbors.
        // The problem implies a single solution if a path exists, so we don't need to backtrack 'visited'
        // or clear it if a path is found. Once resultContainer[0] is set, subsequent calls will not modify it.
        // However, it's a good practice to ensure `resultContainer[0]` is checked as an early exit condition.
        for (Map.Entry<String, Double> neighborEntry : graph.get(currentNode).entrySet()) {
            String neighborNode = neighborEntry.getKey();
            double edgeWeight = neighborEntry.getValue();

            // If a result has already been found in a previous recursive call, stop further exploration.
            if (resultContainer[0] != -1.0) {
                 return; // An answer was found deeper in the recursion, propagate return.
            }

            dfs(neighborNode, destination, graph, visited, resultContainer, currentProduct * edgeWeight);
        }

        // Backtrack: Remove the current node from visited AFTER all its neighbors are explored
        // and its branch of the DFS is finished. This is crucial if multiple paths might
        // originate from the same node and need to use the same intermediate node later.
        // For this specific problem structure and a single result, it's less critical for correctness,
        // but good practice for general DFS.
        visited.remove(currentNode);
    }

    /*
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Example 1
        List<List<String>> eq1 = Arrays.asList(Arrays.asList("a", "b"), Arrays.asList("b", "c"));
        double[] val1 = {2.0, 3.0};
        List<List<String>> q1 = Arrays.asList(
                Arrays.asList("a", "c"),
                Arrays.asList("b", "a"),
                Arrays.asList("a", "e"),
                Arrays.asList("a", "a"),
                Arrays.asList("x", "x")
        );
        double[] res1 = solution.calcEquation(eq1, val1, q1);
        System.out.println("Result for Example 1: " + Arrays.toString(res1));
        // Expected: [6.0, 0.5, -1.0, 1.0, -1.0]

        System.out.println("\n--- Next Test ---");
        // Reset solution for next test because graph is built per call, no instance variables to reset here.

        // Example 2
        List<List<String>> eq2 = Arrays.asList(Arrays.asList("a", "b"));
        double[] val2 = {0.5};
        List<List<String>> q2 = Arrays.asList(
                Arrays.asList("a", "b"),
                Arrays.asList("b", "a"),
                Arrays.asList("a", "c"),
                Arrays.asList("x", "y")
        );
        double[] res2 = solution.calcEquation(eq2, val2, q2);
        System.out.println("Result for Example 2: " + Arrays.toString(res2));
        // Expected: [0.5, 2.0, -1.0, -1.0]

        System.out.println("\n--- Next Test ---");

        // Example 3
        List<List<String>> eq3 = Arrays.asList(
                Arrays.asList("a", "b"),
                Arrays.asList("b", "c"),
                Arrays.asList("bc", "cd")
        );
        double[] val3 = {1.0, 1.0, 1.0};
        List<List<String>> q3 = Arrays.asList(
                Arrays.asList("a", "c"),
                Arrays.asList("c", "f"),
                Arrays.asList("bc", "cd"),
                Arrays.asList("cd", "bc")
        );
        double[] res3 = solution.calcEquation(eq3, val3, q3);
        System.out.println("Result for Example 3: " + Arrays.toString(res3));
        // Expected: [1.0, -1.0, 1.0, 1.0]

        System.out.println("\n--- Next Test ---");

        // Custom Test Case: Disconnected components and more complex path
        List<List<String>> eq4 = Arrays.asList(
                Arrays.asList("x", "y"),
                Arrays.asList("y", "z"),
                Arrays.asList("u", "v"),
                Arrays.asList("v", "w")
        );
        double[] val4 = {2.0, 3.0, 4.0, 5.0};
        List<List<String>> q4 = Arrays.asList(
                Arrays.asList("x", "z"),   // 2*3 = 6
                Arrays.asList("x", "w"),   // -1.0 (disconnected)
                Arrays.asList("u", "w"),   // 4*5 = 20
                Arrays.asList("y", "u"),   // -1.0 (disconnected)
                Arrays.asList("x", "x")    // 1.0
        );
        double[] res4 = solution.calcEquation(eq4, val4, q4);
        System.out.println("Result for Custom Test 4: " + Arrays.toString(res4));
        // Expected: [6.0, -1.0, 20.0, -1.0, 1.0]
    }
    */
}