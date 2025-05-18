/*
Problem Title: Max Area of Island
Problem Link: [https://leetcode.com/problems/max-area-of-island/](https://leetcode.com/problems/max-area-of-island/)

Problem Description:
You are given an m x n binary matrix grid. An island is a group of 1's (representing land) connected 4-directionally (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.

The area of an island is the number of cells with a value 1 in the island.

Return the maximum area of an island in grid. If there is no island, return 0.

Example 1:
Input: grid = [[0,0,1,0,0,0,0,1,0,0,0,0,0],[0,0,0,0,0,0,0,1,1,1,0,0,0],[0,1,1,0,1,0,0,0,0,0,0,0,0],[0,1,0,0,1,1,0,0,0,0,0,0,0],[0,1,0,0,1,1,0,1,1,0,1,0,0],[0,0,0,0,0,0,0,0,0,0,1,0,0],[0,0,0,0,0,0,0,1,1,1,0,0,0],[0,0,0,0,0,0,0,1,1,0,0,0,0]]
Output: 6
Explanation: The answer is the area of the island in the bottom left (with 5 1s) and the middle (with 6 1s).

Example 2:
Input: grid = [[0,0,0,0,0,0,0,0]]
Output: 0

Initial Intuition:
The problem asks us to find the largest connected area of land ('1's) in a grid. We need to traverse the grid, identify islands, calculate their areas, and keep track of the maximum area found so far.

Approach: Depth-First Search (DFS)
We can iterate through each cell of the grid. If we encounter a '1' that hasn't been visited yet, it marks the beginning of a new island. We then use DFS to explore all the connected '1' cells belonging to this island. During the DFS traversal, we count the number of land cells we visit. For each new island found, we perform DFS to calculate its area and update the maximum area if the current island's area is larger. To avoid recounting cells, we can mark visited land cells (e.g., by changing their value to '0' or using a separate visited set).

Detailed Explanation of DFS:
1. Initialize a variable `maxArea` to 0.
2. Iterate through each cell (i, j) of the grid.
3. If `grid[i][j]` is '1', it means we've found a new island (or a part of one we haven't fully explored).
   a. Call a DFS helper function starting from this cell (i, j).
   b. The DFS function will recursively explore all connected '1' cells, count them, and mark them as visited.
   c. The DFS function returns the area of the island it explored.
   d. Update `maxArea` with the maximum of its current value and the area returned by DFS.
4. After iterating through the entire grid, return `maxArea`.

DFS Helper Function:
- Takes the grid, current row `r`, and current column `c` as input.
- Base Cases:
  - If `r` or `c` are out of bounds, return 0.
  - If `grid[r][c]` is '0' (water or already visited), return 0.
- Recursive Step:
  - Mark the current cell as visited (e.g., set `grid[r][c] = '0'`).
  - Initialize `area = 1` (for the current cell).
  - Recursively call DFS for the four neighboring cells (up, down, left, right) and add their returned areas to the current `area`.
  - Return the total `area` of the island explored from the starting cell.

Time Complexity: O(M * N)
Where M is the number of rows and N is the number of columns in the grid. In the worst case, we might visit every cell in the grid. The outer loops iterate through each cell. The DFS, in total across all islands, will visit each land cell at most once because we mark them as visited.

Space Complexity: O(M * N) in the worst case due to the recursive call stack of DFS. If the entire grid is land, the recursion depth could be M * N. In the best case (no islands), the space complexity is O(1).
*/

// import java.util.*;

class MaxAreaOfIsland {
    private int m;
    private int n;

    public int maxAreaOfIsland(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        m = grid.length;
        n = grid[0].length;
        int maxArea = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    maxArea = Math.max(maxArea, dfs(grid, i, j));
                }
            }
        }

        return maxArea;
    }

    private int dfs(int[][] grid, int r, int c) {
        if (r < 0 || r >= m || c < 0 || c >= n || grid[r][c] == 0) {
            return 0;
        }

        grid[r][c] = 0; // Mark as visited
        int area = 1;
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}}; // Right, Left, Down, Up

        for (int[] dir : dirs) {
            area += dfs(grid, r + dir[0], c + dir[1]);
        }

        return area;
    } 

    // public static void main(String[] args) {
    //     Solution solution = new Solution();

    //     // Example 1
    //     int[][] grid1 = new int[][]{
    //             {0,0,1,0,0,0,0,1,0,0,0,0,0},
    //             {0,0,0,0,0,0,0,1,1,1,0,0,0},
    //             {0,1,1,0,1,0,0,0,0,0,0,0,0},
    //             {0,1,0,0,1,1,0,0,0,0,0,0,0},
    //             {0,1,0,0,1,1,0,1,1,0,1,0,0},
    //             {0,0,0,0,0,0,0,0,0,0,1,0,0},
    //             {0,0,0,0,0,0,0,1,1,1,0,0,0},
    //             {0,0,0,0,0,0,0,1,1,0,0,0,0}
    //     };
    //     System.out.println("Max area of island in grid1: " + solution.maxAreaOfIsland(grid1)); // Expected output: 6

    //     // Example 2
    //     int[][] grid2 = new int[][]{{0,0,0,0,0,0,0,0}};
    //     System.out.println("Max area of island in grid2: " + solution.maxAreaOfIsland(grid2)); // Expected output: 0
    // }
}