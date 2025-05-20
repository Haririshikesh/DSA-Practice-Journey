/*
Problem Title: Pacific Atlantic Water Flow
Problem Link: [https://leetcode.com/problems/pacific-atlantic-water-flow/](https://leetcode.com/problems/pacific-atlantic-water-flow/)

Problem Description:
There is an m x n rectangular island that borders both the Pacific Ocean and Atlantic Ocean. The Pacific Ocean touches the island's left and top edges, and the Atlantic Ocean touches the island's right and bottom edges.

The island is partitioned into a grid of square cells. You are given an m x n integer matrix heights where heights[r][c] represents the height above sea level of the cell at coordinate (r, c).

Water can flow from any cell to an adjacent one (horizontal or vertical) if the height of the adjacent cell is less than or equal to the current cell's height. Water can flow from the island into an ocean if the cell is adjacent to that ocean.

Return a list of grid coordinates result where result[i] = [ri, ci] denotes that water can flow from cell (ri, ci) to both the Pacific and Atlantic oceans.

Example 1:
Input: heights = [[1,2,2,3,5],[3,2,3,4,4],[2,4,5,3,1],[6,7,1,4,5],[5,1,1,2,4]]
Output: [[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]
Explanation: The cells that can flow to both oceans are highlighted in blue.
(Visual of example grid here)

Example 2:
Input: heights = [[2,1],[1,2]]
Output: [[0,1],[1,0]]

Example 3:
Input: heights = [[1,2,3],[8,9,4],[7,6,5]]
Output: [[0,2],[1,0],[1,1],[1,2],[2,0],[2,1],[2,2]]
Explanation: All cells can flow to both oceans.

Initial Intuition:
The problem asks for cells from which water can flow to both the Pacific and Atlantic oceans. Instead of trying to simulate water flowing *from* every cell *to* the oceans, which would be inefficient, it's more efficient to think in reverse: which cells can water *reach* if it starts from the oceans and flows *uphill* or at the same level? If a cell can be reached from both the Pacific and Atlantic "sources" (by uphill flow), then water from that cell can flow downhill to both oceans.

Approach: Depth-First Search (DFS) from Oceans
We will perform two separate DFS traversals:
1.  One DFS starting from all cells bordering the Pacific Ocean (top row, left column). This DFS will mark all cells from which water can reach the Pacific.
2.  Another DFS starting from all cells bordering the Atlantic Ocean (bottom row, right column). This DFS will mark all cells from which water can reach the Atlantic.

After both DFS traversals are complete, we iterate through every cell in the grid. If a cell is marked as reachable by water from *both* the Pacific and Atlantic oceans, then it is a valid coordinate to be included in our result list.

Detailed Explanation of DFS:
The `dfs` helper function will take the current cell's coordinates (row, col), the grid dimensions (rows, cols), a boolean `visited` array (either `pac` or `atl`), the height of the *previous* cell from which water flowed (`prevh`), and the `heights` grid itself.

- **Base Cases for DFS:**
    - If the current cell (row, col) is out of bounds.
    - If the current cell has already been visited (marked `true` in the `visited` array).
    - If the height of the current cell (`h[row][col]`) is less than the `prevh` (water cannot flow uphill or stay at the same level in reverse). In the *reverse* flow, water flows *from* higher or equal to lower or equal. So, `prevh` is the height of the cell we just came from, and `h[row][col]` is the current cell. We can only move to `(row, col)` if `h[row][col] >= prevh`.
- **Recursive Step:**
    - Mark the current cell `(row, col)` as visited in the `visited` array (set `visited[row][col] = true`).
    - Explore all four adjacent neighbors (up, down, left, right). For each neighbor, recursively call `dfs`, passing the current cell's height (`h[row][col]`) as the `prevh` for the next call. This ensures that water is "flowing uphill" or staying level.

Main Logic:
1.  Initialize two boolean 2D arrays, `pac` and `atl`, of the same dimensions as `heights`, all initialized to `false`. These will track cells reachable from Pacific and Atlantic, respectively.
2.  Iterate along the top and bottom rows to start DFS for Pacific (from `(0, col)`) and Atlantic (from `(rows-1, col)`).
3.  Iterate along the left and right columns to start DFS for Pacific (from `(row, 0)`) and Atlantic (from `(row, cols-1)`).
    - For these initial calls, `prevh` should be a very small number (e.g., `Integer.MIN_VALUE`) to allow water to flow into any border cell.
4.  After both sets of DFS traversals are complete, iterate through every cell `(r, c)` in the grid.
5.  If `pac[r][c]` is `true` AND `atl[r][c]` is `true`, add `[r, c]` to the result list.
6.  Return the result list.

Time Complexity: O(M * N)
Where M is the number of rows and N is the number of columns. Each cell in the grid is visited at most twice (once for the Pacific DFS and once for the Atlantic DFS). Each visit involves constant time operations (boundary checks, array lookups, and recursive calls to neighbors).

Space Complexity: O(M * N)
This is primarily due to the two boolean 2D arrays (`pac` and `atl`) used to track visited cells, and the recursion stack depth during the DFS, which can go up to M * N in the worst case (e.g., a path spanning the entire grid).
*/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class PacificAtlanticWaterFlow {
    int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // Right, Down, Left, Up
    int rows, cols; // Class level variables for dimensions

    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        if (heights == null || heights.length == 0 || heights[0].length == 0) {
            return new ArrayList<>();
        }

        rows = heights.length;
        cols = heights[0].length;

        // visited arrays for pacific and atlantic reachable cells
        boolean[][] pacificReachable = new boolean[rows][cols];
        boolean[][] atlanticReachable = new boolean[rows][cols];

        // DFS from top and bottom rows
        for (int c = 0; c < cols; c++) {
            dfs(0, c, Integer.MIN_VALUE, heights, pacificReachable); // Top border to Pacific
            dfs(rows - 1, c, Integer.MIN_VALUE, heights, atlanticReachable); // Bottom border to Atlantic
        }

        // DFS from left and right columns
        for (int r = 0; r < rows; r++) {
            dfs(r, 0, Integer.MIN_VALUE, heights, pacificReachable); // Left border to Pacific
            dfs(r, cols - 1, Integer.MIN_VALUE, heights, atlanticReachable); // Right border to Atlantic
        }

        List<List<Integer>> result = new ArrayList<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (pacificReachable[r][c] && atlanticReachable[r][c]) {
                    result.add(Arrays.asList(r, c));
                }
            }
        }
        return result;
    }

    private void dfs(int r, int c, int prevHeight, int[][] heights, boolean[][] reachable) {
        // Base cases:
        // 1. Out of bounds
        // 2. Already visited/reachable from this ocean
        // 3. Current cell is lower than the previous height (cannot flow uphill/same level from prevHeight)
        if (r < 0 || r >= rows || c < 0 || c >= cols || reachable[r][c] || heights[r][c] < prevHeight) {
            return;
        }

        reachable[r][c] = true; // Mark current cell as reachable

        // Recursively visit neighbors
        for (int[] dir : dirs) {
            int newR = r + dir[0];
            int newC = c + dir[1];
            dfs(newR, newC, heights[r][c], heights, reachable); // Pass current cell's height as prevHeight for next call
        }
    }

    /*
    public static void main(String[] args) {
        PacificAtlanticWaterFlow pacificAtlanticWaterFlow = new PacificAtlanticWaterFlow();

        // Example 1
        int[][] heights1 = {
            {1,2,2,3,5},
            {3,2,3,4,4},
            {2,4,5,3,1},
            {6,7,1,4,5},
            {5,1,1,2,4}
        };
        List<List<Integer>> result1 = pacificAtlanticWaterFlow.pacificAtlantic(heights1);
        System.out.println("Result for grid1: " + result1);
        // Expected: [[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]] (Order might vary)

        System.out.println("\n--- Resetting for next test ---");
        // Re-initialize solution for next test because class-level 'reachable' arrays are not reset
        // For local testing, it's often easier to create a new Solution object per test.
        PacificAtlanticWaterFlow = new PacificAtlanticWaterFlow();

        // Example 2
        int[][] heights2 = {{2,1},{1,2}};
        List<List<Integer>> result2 = pacificAtlanticWaterFlow.pacificAtlantic(heights2);
        System.out.println("Result for grid2: " + result2);
        // Expected: [[0,1],[1,0]] (Order might vary)

        System.out.println("\n--- Resetting for next test ---");
        PacificAtlanticWaterFlow = new PacificAtlanticWaterFlow();

        // Example 3
        int[][] heights3 = {{1,2,3},{8,9,4},{7,6,5}};
        List<List<Integer>> result3 = pacificAtlanticWaterFlow.pacificAtlantic(heights3);
        System.out.println("Result for grid3: " + result3);
        // Expected: [[0,2],[1,0],[1,1],[1,2],[2,0],[2,1],[2,2]] (Order might vary)
    }
    */
}