// LeetCode Problem: Evaluate Division
// Problem Link: https://leetcode.com/problems/evaluate-division/

// This file contains the Java solution for the Evaluate Division problem on LeetCode.
// The problem asks us to evaluate division queries given a set of equations and their corresponding values.
// We can model this problem as a graph where variables are nodes and the division results are edge weights.

import java.util.*;

// class Solution {
//     /**
//      * Calculates the results of division queries based on given equations.
//      *
//      * @param equations A list of pairs of strings representing the equations (e.g., [["a", "b"], ["b", "c"]]).
//      * @param values An array of doubles representing the values of the equations (e.g., [2.0, 3.0] for a/b=2.0, b/c=3.0).
//      * @param queries A list of pairs of strings representing the queries to evaluate (e.g., [["a", "c"], ["b", "a"]]).
//      * @return An array of doubles where each element is the result of the corresponding query.
//      * Returns -1.0 if a query cannot be evaluated.
//      */
//     public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
//         // Step 1: Build the graph from the given equations and values.
//         // The graph will store variables as nodes and the division results as edge weights.
//         HashMap<String, HashMap<String, Double>> grp = buildGraph(equations, values);

//         // Initialize the result array for the queries.
//         double[] res = new double[queries.size()];

//         // Step 2: Process each query.
//         for(int i = 0; i < queries.size(); i++){
//             String divisor = queries.get(i).get(0);    // The numerator of the query
//             String dividend = queries.get(i).get(1);   // The denominator of the query

//             // Check if both the divisor and dividend exist in our graph.
//             // If either is not present, it means we cannot evaluate the query.
//             if(!grp.containsKey(divisor) || !grp.containsKey(dividend) ){
//                 res[i] = -1.0;
//             } else {
//                 // Use a HashSet to keep track of visited nodes during DFS to prevent cycles.
//                 HashSet<String> vis = new HashSet<>();
//                 // Use a single-element array to store the result of the DFS.
//                 // This allows the DFS function to update a value that's visible to the caller.
//                 double ans[] = {-1.0};
//                 // 'temp' variable stores the cumulative product of edge weights along the DFS path.
//                 double temp = 1.0;

//                 // Perform DFS to find the path from divisor to dividend and calculate the product.
//                 dfs(divisor, dividend, grp, vis, ans , temp);
//                 res[i] = ans[0]; // Store the calculated result for the current query.
//             }
//         }
//         return res;
//     }

//     /**
//      * Builds a graph representing the relationships between variables from the given equations.
//      * Each node in the graph is a variable (String), and edges represent the division result.
//      * For an equation a/b = value, an edge from 'a' to 'b' with weight 'value' is created,
//      * and an edge from 'b' to 'a' with weight '1.0 / value' is also created.
//      *
//      * @param equations A list of equations.
//      * @param values An array of corresponding values for the equations.
//      * @return A HashMap representing the graph. Outer HashMap maps a variable to an inner HashMap.
//      * The inner HashMap maps an adjacent variable to the edge weight (division result).
//      */
//     public HashMap<String, HashMap<String, Double>> buildGraph (List<List<String>> equations, double[] values){
//         HashMap<String, HashMap<String, Double>> grp = new HashMap<>();

//         for(int i = 0; i < equations.size(); i++){
//             String dividend = equations.get(i).get(0); // Numerator
//             String divisor = equations.get(i).get(1);  // Denominator

//             // Ensure both dividend and divisor nodes exist in the graph.
//             grp.putIfAbsent(dividend, new HashMap<>());
//             grp.putIfAbsent(divisor, new HashMap<>());

//             // Add edge for dividend / divisor = values[i]
//             grp.get(dividend).put(divisor, values[i]);
//             // Add inverse edge for divisor / dividend = 1.0 / values[i]
//             grp.get(divisor).put(dividend, 1.0 / values[i]);
//         }
//         return grp;
//     }

//     /**
//      * Performs a Depth-First Search (DFS) to find a path from a starting node to a destination node
//      * in the graph and calculates the cumulative product of edge weights along that path.
//      *
//      * @param node The current node in the DFS traversal.
//      * @param destination The target node we are trying to reach.
//      * @param grp The graph representation.
//      * @param vis A set to keep track of visited nodes to avoid cycles.
//      * @param ans A single-element array to store the final calculated answer.
//      * It is updated when the destination is reached.
//      * @param temp The cumulative product of edge weights from the starting node to the current 'node'.
//      */
//     public void dfs(String node, String destination, HashMap<String, HashMap<String, Double>> grp, HashSet<String> vis, double[] ans, double temp){
//         // If the current node has already been visited in the current path, return to avoid cycles.
//         if(vis.contains(node)) return;

//         // Mark the current node as visited.
//         vis.add(node);

//         // If the current node is the destination, we have found a path.
//         // Update the 'ans' array with the cumulative product 'temp'.
//         // And then return, as we found our answer.
//         if(node.equals(destination)){
//             ans[0] = temp;
//             return;
//         }

//         // Iterate over all neighbors of the current node.
//         // Check if the current node exists in the graph. This handles cases where a node in a query
//         // might not be part of any equation given, although this is checked earlier in calcEquation.
//         if (grp.containsKey(node)) {
//             for(Map.Entry<String,Double> entry: grp.get(node).entrySet()){
//                 String key = entry.getKey();     // Neighboring node
//                 double value = entry.getValue(); // Edge weight to the neighboring node

//                 // Recursively call DFS for the neighbor, updating the cumulative product 'temp'.
//                 // Only proceed if a solution hasn't already been found. This optimization can prune redundant searches
//                 // if multiple paths exist and one has already found the destination.
//                 if (ans[0] == -1.0) { // Check if a path to destination has been found
//                     dfs(key, destination, grp, vis, ans, temp * value);
//                 }
//             }
//         }
//     }
// }

// The uncommented solution class for LeetCode submission:
class Solution {
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        HashMap<String, HashMap<String, Double>> grp = buildGraph(equations, values);
        double[] res = new double[queries.size()];
        for(int i=0; i<queries.size(); i++){
            String divisor = queries.get(i).get(0);
            String dividend = queries.get(i).get(1);
            if(!grp.containsKey(divisor) || !grp.containsKey(dividend) ){
                res[i] = -1.0;
            } else {
                HashSet<String> vis = new HashSet<>();
                double ans[] = {-1.0};
                double temp = 1.0;
                dfs(divisor, dividend, grp, vis, ans , temp);
                res[i] = ans[0];
            }
        }
        return res;        
    }
    public HashMap<String, HashMap<String, Double>> buildGraph (List<List<String>> equations, double[] values){
        HashMap<String, HashMap<String, Double>> grp = new HashMap<>();
        for(int i=0; i<equations.size(); i++){
            String dividend = equations.get(i).get(0);
            String divisor = equations.get(i).get(1);
            grp.putIfAbsent(dividend, new HashMap<>());
            grp.putIfAbsent(divisor, new HashMap<>());
            grp.get(dividend).put(divisor, values[i]);
            grp.get(divisor).put(dividend, 1.0 / values[i]);
        }
        return grp;
    }
    public void dfs(String node, String destination, HashMap<String, HashMap<String, Double>> grp, HashSet<String> vis, double[] ans, double temp){
        if(vis.contains(node)) return;
        vis.add(node);
        if(node.equals(destination)){
            ans[0] = temp;
            return;
        }
        if (grp.containsKey(node)) { // Added null check for safety, though graph construction should prevent this.
            for(Map.Entry<String,Double> entry: grp.get(node).entrySet()){
                String key = entry.getKey();
                double value = entry.getValue();
                // Optimization: only continue DFS if a valid path hasn't been found yet
                if (ans[0] == -1.0) {
                    dfs(key, destination, grp, vis, ans, temp * value);
                }
            }
        }
    }
}

// Example Usage and Main Method:
// This section demonstrates how to use the Solution class with sample inputs.
// It's commented out to ensure the file is clean for LeetCode submission,
// but you can uncomment it to run and test locally.

/*
public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();

        // Example 1
        List<List<String>> equations1 = new ArrayList<>();
        equations1.add(Arrays.asList("a", "b"));
        equations1.add(Arrays.asList("b", "c"));
        double[] values1 = {2.0, 3.0};
        List<List<String>> queries1 = new ArrayList<>();
        queries1.add(Arrays.asList("a", "c"));
        queries1.add(Arrays.asList("b", "a"));
        queries1.add(Arrays.asList("a", "e"));
        queries1.add(Arrays.asList("a", "a"));
        queries1.add(Arrays.asList("x", "x"));

        System.out.println("Example 1:");
        double[] results1 = sol.calcEquation(equations1, values1, queries1);
        for (double res : results1) {
            System.out.printf("%.5f ", res);
        }
        System.out.println("\nExpected: 6.00000 0.50000 -1.00000 1.00000 -1.00000\n");

        // Example 2
        List<List<String>> equations2 = new ArrayList<>();
        equations2.add(Arrays.asList("a", "b"));
        equations2.add(Arrays.asList("b", "c"));
        equations2.add(Arrays.asList("bc", "cd"));
        double[] values2 = {1.0, 1.0, 1.0};
        List<List<String>> queries2 = new ArrayList<>();
        queries2.add(Arrays.asList("a", "c"));
        queries2.add(Arrays.asList("c", "b"));
        queries2.add(Arrays.asList("bc", "cd"));
        queries2.add(Arrays.asList("cd", "bc"));

        System.out.println("Example 2:");
        double[] results2 = sol.calcEquation(equations2, values2, queries2);
        for (double res : results2) {
            System.out.printf("%.5f ", res);
        }
        System.out.println("\nExpected: 1.00000 1.00000 1.00000 1.00000\n");

        // Example 3
        List<List<String>> equations3 = new ArrayList<>();
        equations3.add(Arrays.asList("a", "b"));
        double[] values3 = {0.5};
        List<List<String>> queries3 = new ArrayList<>();
        queries3.add(Arrays.asList("a", "b"));
        queries3.add(Arrays.asList("b", "a"));
        queries3.add(Arrays.asList("a", "c"));
        queries3.add(Arrays.asList("c", "a"));

        System.out.println("Example 3:");
        double[] results3 = sol.calcEquation(equations3, values3, queries3);
        for (double res : results3) {
            System.out.printf("%.5f ", res);
        }
        System.out.println("\nExpected: 0.50000 2.00000 -1.00000 -1.00000\n");
    }
}
*/

/*
Problem Explanation:

The "Evaluate Division" problem asks us to determine the results of division queries given a set of equations. Each equation involves two variables and their ratio. For example, if we are given `a / b = 2.0` and `b / c = 3.0`, we might be asked to find `a / c` or `c / a`.

The core idea is to model this problem as a graph. Each variable (e.g., 'a', 'b', 'c') can be considered a node in the graph. An equation like `a / b = 2.0` can be represented as a directed edge from 'a' to 'b' with a weight of `2.0`. Crucially, we also know that `b / a = 1.0 / 2.0 = 0.5`, so we'll also add a directed edge from 'b' to 'a' with a weight of `0.5`. This makes our graph undirected in a sense, but with directional weights.

To answer a query like `a / c`, we need to find a path from node 'a' to node 'c' in this graph. If a path exists, say `a -> b -> c`, then the value of `a / c` would be the product of the edge weights along this path: `(a / b) * (b / c)`. In our example, `2.0 * 3.0 = 6.0`.

Approach:

1.  **Graph Construction:**
    * We use an `Adjacency List` representation for our graph. A `HashMap<String, HashMap<String, Double>>` is suitable:
        * The outer `HashMap` maps a variable (String) to another `HashMap`.
        * The inner `HashMap` maps an adjacent variable (String) to the `Double` value representing the division result (edge weight).
    * For each equation `equations[i] = [numerator, denominator]` with `values[i]`, we add two edges:
        * `graph.get(numerator).put(denominator, values[i])`
        * `graph.get(denominator).put(numerator, 1.0 / values[i])`
    * We use `putIfAbsent` to ensure that a new inner HashMap is created for a variable if it's encountered for the first time.

2.  **Query Processing (DFS/BFS):**
    * For each query `[queryNumerator, queryDenominator]`:
    * **Check for existence:** First, check if both `queryNumerator` and `queryDenominator` exist as nodes in our constructed graph. If either is missing, it means we have no information about this variable, and thus the query cannot be evaluated. In this case, the result is `-1.0`.
    * **Pathfinding (DFS):** If both nodes exist, we perform a Depth-First Search (DFS) starting from `queryNumerator` to find `queryDenominator`.
        * During the DFS, we maintain a `currentProduct` (initialized to `1.0` for the starting node) which accumulates the product of edge weights along the current path.
        * We also need a `visited` set to prevent infinite loops in case of cycles in the graph (e.g., `a/b=2, b/a=0.5`).
        * When the DFS reaches the `queryDenominator`, the `currentProduct` at that point is the answer for the query.
        * If the DFS completes without reaching the `queryDenominator` (meaning no path exists), the answer remains `-1.0`.

**Detailed DFS Logic:**

The `dfs` function takes:
* `node`: The current node being visited.
* `destination`: The target node.
* `grp`: The graph.
* `vis`: The set of visited nodes for the current DFS path.
* `ans`: A `double[]` array of size 1. This is a common pattern in Java to allow a recursive function to update a value that's visible to its caller, effectively passing by reference. `ans[0]` stores the found result, initialized to `-1.0`.
* `temp`: The accumulated product from the starting node to the current `node`.

1.  **Base Cases:**
    * If `node` is already in `vis`, it means we've encountered a cycle. Return immediately to avoid infinite recursion.
    * Mark `node` as visited (`vis.add(node)`).
    * If `node` is equal to `destination`, we've found a path! Set `ans[0] = temp` and return.

2.  **Recursive Step:**
    * Iterate through all neighbors of `node` (i.e., `grp.get(node).entrySet()`).
    * For each `neighbor` and its `edgeWeight`:
        * Recursively call `dfs(neighbor, destination, grp, vis, ans, temp * edgeWeight)`.
        * **Optimization:** After the recursive call, check if `ans[0]` is no longer `-1.0`. If it's been updated, it means a path to the destination has been found through one of the neighbors. In this case, we can stop further exploration from the current `node` and return early, as we already have our answer. This is important because there might be multiple paths, but any path will yield the same correct result if the graph is consistent.

**Why not other approaches?**

* **Why not just store equations and solve mathematically?**
    * Directly solving equations for complex relationships (e.g., `a/b, b/c, c/d` to find `a/d`) becomes cumbersome. Graph traversal naturally handles chaining relationships.
* **Why not BFS?**
    * BFS could also be used. Instead of accumulating the product in `temp` during recursion, you would store the cumulative product in the queue along with the current node. Both DFS and BFS are valid graph traversal algorithms for this problem. DFS is often simpler to implement recursively, which fits the current solution structure. The choice between DFS and BFS often depends on whether you need the shortest path (BFS) or just *any* path (DFS). Here, any path is sufficient.
* **Why a graph and not just a direct mapping for all possible pairs?**
    * The number of possible pairs can be very large ($N^2$ where N is the number of unique variables), making pre-computation of all pairs inefficient for large inputs. Building the graph and querying as needed is more memory and time efficient.

Complexity Analysis:

Let `N` be the number of equations, `M` be the number of queries, and `V` be the number of unique variables (nodes in the graph).
Let `E` be the number of edges in the graph. In our case, for each equation, we add two edges, so `E = 2 * N`.

* **Time Complexity:**
    * **`buildGraph` function:**
        * We iterate through `N` equations. For each equation, we perform constant time operations (HashMap `putIfAbsent` and `put`).
        * In the worst case, `putIfAbsent` and `put` operations on a HashMap can take $O(L)$ time, where $L$ is the average length of strings, due to hashing. Assuming constant time for string hashing, this is $O(1)$.
        * Therefore, `buildGraph` takes $O(N)$.
    * **`calcEquation` function (main loop):**
        * We iterate through `M` queries.
        * For each query, we perform a DFS.
        * **DFS Complexity:** In the worst case, a DFS can visit all `V` nodes and traverse all `E` edges in the graph. So, a single DFS takes $O(V + E)$ time.
        * Therefore, the total time for `calcEquation` is $O(N + M * (V + E))$.
        * Since $E = 2N$, this can be simplified to $O(N + M * (V + N))$.
        * In typical competitive programming scenarios, $V$ can be at most $2N$ (if all equations introduce new variables). So, $V$ and $E$ are roughly proportional to $N$.
        * Thus, the overall time complexity is approximately $O(N + M \times N)$.

* **Space Complexity:**
    * **`grp` (Graph):** The graph stores `V` nodes. Each node can have up to `V-1` connections in the worst case (dense graph). However, since each equation adds only two connections, the total number of edges stored is `2N`. The space is proportional to the number of nodes and edges.
        * $O(V + E)$ or $O(V + N)$ due to the adjacency list representation.
    * **`vis` (Visited Set):** In the worst case, during a DFS, the `visited` set can store up to `V` nodes. So, $O(V)$.
    * **Recursion Stack:** The depth of the recursion stack for DFS can go up to `V` in the worst case (a long linear path). So, $O(V)$.
    * **`res` (Result Array):** $O(M)$ to store query results.
    * Combining these, the overall space complexity is $O(V + N + M)$. Since $V$ is at most $2N$, this is effectively $O(N + M)$.
*/