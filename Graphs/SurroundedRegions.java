/*
Problem Title: Surrounded Regions
Problem Link: [https://leetcode.com/problems/surrounded-regions/](https://leetcode.com/problems/surrounded-regions/)

Problem Description:
Given an m x n matrix board containing 'X' and 'O', capture all regions that are 4-directionally surrounded by 'X'.
A region is captured by flipping all 'O's in that surrounded region to 'X's.

A region is "surrounded" if it is not connected to the border of the board.
An 'O' cell is connected to the border if it can reach any 'O' cell on the border by moving 4-directionally (horizontally or vertically).

Example 1:
Input: board = [["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]
Output: [["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]
Explanation: The 'O's in the middle are surrounded and flipped to 'X's. The 'O' on the bottom-left is connected to the border and remains an 'O'.

Example 2:
Input: board = [["X"]]
Output: [["X"]]

Initial Intuition:
The problem asks us to flip 'O's that are "surrounded" by 'X's. A key clarification is that an 'O' is *not* surrounded if it's connected to an 'O' on the border. This immediately tells us we should focus on the 'O's that are *connected to the border*. Any 'O' that can reach the border (and all 'O's connected to it) should *not* be flipped. All other 'O's must be surrounded and should be flipped.

Approach: Depth-First Search (DFS) from Borders
We can solve this by using DFS (or BFS) starting from all 'O's located on the grid's borders.
1.  Any 'O' on the border, and any 'O' connected to it, is considered "safe" and should not be flipped.
2.  We'll traverse these "safe" 'O's and mark them to distinguish them from the 'O's that *are* surrounded. A common way is to temporarily change 'safe' 'O's to a placeholder character (e.g., '#').
3.  After marking all "safe" 'O's, we iterate through the entire grid one last time.
    * Any 'O' that remains (meaning it was not reachable from a border 'O') is a truly surrounded 'O' and should be flipped to 'X'.
    * Any placeholder character (e.g., '#') should be converted back to 'O' because these were the 'safe' ones.

Detailed Explanation of DFS:
1.  **Initialize a `visited` array:** Create a boolean 2D array, `vis`, of the same dimensions as `board` to keep track of cells visited during the DFS.
2.  **Boundary DFS:**
    * Iterate through all cells on the top border (row 0, all columns). If `board[0][j]` is 'O' and not yet visited, start a DFS from `(0, j)`.
    * Iterate through all cells on the bottom border (row `m-1`, all columns). If `board[m-1][j]` is 'O' and not yet visited, start a DFS from `(m-1, j)`.
    * Iterate through all cells on the left border (col 0, all rows). If `board[i][0]` is 'O' and not yet visited, start a DFS from `(i, 0)`.
    * Iterate through all cells on the right border (col `n-1`, all rows). If `board[i][n-1]` is 'O' and not yet visited, start a DFS from `(i, n-1)`.
3.  **The `dfs` helper function:**
    * Takes current coordinates `(x, y)`, the `board`, and the `vis` array.
    * **Base Cases:**
        * If `(x, y)` is out of bounds.
        * If `vis[x][y]` is already `true` (already visited).
        * If `board[x][y]` is 'X' (it's water, cannot traverse).
    * **Recursive Step:**
        * Mark `vis[x][y] = true` to indicate this cell and its connected 'O's are safe.
        * Recursively call `dfs` for all four adjacent neighbors (up, down, left, right).
4.  **Final Grid Transformation:**
    * After all border-connected 'O's and their connected components are marked as visited in `vis`, iterate through the entire `board` again.
    * If `board[i][j]` is 'O' AND `vis[i][j]` is `false` (meaning it's an 'O' not reachable from a border 'O'), change `board[i][j]` to 'X'.
    * The cells that were part of border-connected 'O's (and thus `vis[i][j]` is `true`) remain 'O's; no change is needed for them.

Time Complexity: O(M * N)
Where M is the number of rows and N is the number of columns in the grid. In the worst case, each cell might be visited a constant number of times: once during the initial border checks and potentially once during a DFS traversal.

Space Complexity: O(M * N)
This is primarily due to the `boolean[][] vis` array used to track visited cells. In the worst case, the DFS recursion stack depth could also go up to M * N if the entire grid is a single path of 'O's.
*/
import java.util.Arrays;

class SurroundedRegions {
    int m, n;
    int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // Right, Down, Left, Up

    public void solve(char[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            return;
        }

        m = board.length;
        n = board[0].length;
        boolean[][] visited = new boolean[m][n];

        // Perform DFS from 'O's on the left and right borders
        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O' && !visited[i][0]) {
                dfs(i, 0, board, visited);
            }
            if (board[i][n - 1] == 'O' && !visited[i][n - 1]) {
                dfs(i, n - 1, board, visited);
            }
        }

        // Perform DFS from 'O's on the top and bottom borders
        for (int j = 0; j < n; j++) {
            if (board[0][j] == 'O' && !visited[0][j]) {
                dfs(0, j, board, visited);
            }
            if (board[m - 1][j] == 'O' && !visited[m - 1][j]) {
                dfs(m - 1, j, board, visited);
            }
        }

        // After DFS, flip surrounded 'O's to 'X's
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // If it's an 'O' and NOT marked as visited (i.e., not connected to a border 'O')
                if (board[i][j] == 'O' && !visited[i][j]) {
                    board[i][j] = 'X';
                }
            }
        }
    }

    private void dfs(int r, int c, char[][] board, boolean[][] visited) {
        // Base cases: out of bounds, already visited, or not an 'O'
        if (r < 0 || r >= m || c < 0 || c >= n || visited[r][c] || board[r][c] == 'X') {
            return;
        }

        visited[r][c] = true; // Mark as visited (safe)

        // Explore neighbors
        for (int[] dir : dirs) {
            int nr = r + dir[0];
            int nc = c + dir[1];
            dfs(nr, nc, board, visited);
        }
    }

    /*
    public static void main(String[] args) {
        SurroundedRegions surroundedRegions = new SurroundedRegions();

        // Helper to print board
        Runnable printBoard = (board) -> {
            for (char[] row : board) {
                System.out.println(Arrays.toString(row));
            }
        };

        // Example 1
        char[][] board1 = {
            {'X','X','X','X'},
            {'X','O','O','X'},
            {'X','X','O','X'},
            {'X','O','X','X'}
        };
        System.out.println("Original Board 1:");
        printBoard.run(board1);
        surroundedRegions.solve(board1);
        System.out.println("Modified Board 1:");
        printBoard.run(board1);
        // Expected:
        // [X, X, X, X]
        // [X, X, X, X]
        // [X, X, X, X]
        // [X, O, X, X]

        System.out.println("\n--- Next Test ---");

        // Example 2
        char[][] board2 = {{'X'}};
        System.out.println("Original Board 2:");
        printBoard.run(board2);
        surroundedRegions.solve(board2);
        System.out.println("Modified Board 2:");
        printBoard.run(board2);
        // Expected:
        // [X]

        System.out.println("\n--- Next Test ---");

        // Custom Test Case
        char[][] board3 = {
            {'O','O','O'},
            {'O','X','O'},
            {'O','O','O'}
        };
        System.out.println("Original Board 3:");
        printBoard.run(board3);
        surroundedRegions.solve(board3);
        System.out.println("Modified Board 3:");
        printBoard.run(board3);
        // Expected: All O's remain 'O' because they are connected to the border
        // [O, O, O]
        // [O, X, O]
        // [O, O, O]
    }
    */
}