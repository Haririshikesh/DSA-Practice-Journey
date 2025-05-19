/*
Problem Title: Rotting Oranges
Problem Link: [https://leetcode.com/problems/rotting-oranges/](https://leetcode.com/problems/rotting-oranges/)

Problem Description:
You are given an m x n grid of integers where:
- 0 represents an empty cell,
- 1 represents a fresh orange, and
- 2 represents a rotten orange.

Every minute, any fresh orange that is 4-directionally adjacent to a rotten orange becomes rotten.

Return the minimum number of minutes that must elapse until no cell has a fresh orange. If it is impossible, return -1.

Example 1:
Input: grid = [[2,1,1],[1,1,0],[0,1,1]]
Output: 4

Example 2:
Input: grid = [[2,1,1],[0,1,1],[1,0,1]]
Output: -1
Explanation: The orange in the bottom left corner will never rot.

Example 3:
Input: grid = [[0,2]]
Output: 0
Explanation: Since there are no fresh oranges, 0 minutes are needed.

Initial Intuition:
The problem describes a process of oranges rotting over time, spreading to adjacent fresh oranges. This suggests a simulation-like approach, where we can track the state of the grid at each minute. Breadth-First Search (BFS) seems suitable here because we are exploring the grid layer by layer, simulating the spread of rottenness at each minute.

Approach: Breadth-First Search (BFS)
We can start by identifying all the initially rotten oranges and adding them to a queue. Then, we can perform a level-order traversal (like in BFS). Each level of the BFS represents one minute passing. In each minute, we process all the currently rotten oranges in the queue and infect their adjacent fresh oranges. We continue this until no more fresh oranges can be infected.

Detailed Explanation of BFS:
1. Initialize a queue to store the coordinates of rotten oranges.
2. Count the initial number of fresh oranges in the grid.
3. Iterate through the grid and add the coordinates of all rotten oranges to the queue.
4. If there are no fresh oranges initially, return 0 minutes.
5. Initialize a variable `minutes` to -1 (incremented before processing each minute).
6. While the queue is not empty:
   a. Increment `minutes` to represent the passage of one minute.
   b. Get the current size of the queue (number of oranges that will rot in this minute).
   c. Process all the oranges that were rotten at the beginning of this minute (using the initial size of the queue):
      i. Dequeue a rotten orange's coordinates.
      ii. Explore its four adjacent cells (up, down, left, right).
      iii. For each adjacent cell:
          - Check if it's within the grid boundaries.
          - Check if it contains a fresh orange (value 1).
          - If both conditions are true, mark the adjacent orange as rotten (change its value to 2), decrement the `fresh` count, and enqueue its coordinates for the next minute's processing.
7. After the BFS is complete, check if the `fresh` count is 0.
   - If `fresh` is 0, it means all oranges have rotted, so return the `minutes` elapsed.
   - If `fresh` is still greater than 0, it means some fresh oranges could not be reached, so return -1.

Time Complexity: O(M * N)
Where M is the number of rows and N is the number of columns in the grid. In the worst case, we might visit each cell a constant number of times.

Space Complexity: O(M * N) in the worst case, as the queue might contain all the cells in the grid if all oranges eventually rot.
*/
import java.util.LinkedList;
import java.util.Queue;
import java.util.Arrays; // For easy grid creation in main

class RottingOranges {
    public int orangesRotting(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] vis = new int[m][n]; // Create a separate visited array if you don't want to modify the original grid
        Queue<int[]> q = new LinkedList<>();
        int fresh = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                vis[i][j] = grid[i][j]; // Initialize visited array
                if (vis[i][j] == 2) {
                    q.offer(new int[]{i, j});
                }
                if (vis[i][j] == 1) {
                    fresh++;
                }
            }
        }

        if (fresh == 0) return 0;
        if (q.isEmpty() && fresh > 0) return -1; // No initial rotten oranges, but there are fresh ones

        int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        int minutes = 0;

        while (!q.isEmpty() && fresh > 0) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int[] cell = q.poll();
                for (int[] dir : dirs) {
                    int x = dir[0] + cell[0];
                    int y = dir[1] + cell[1];
                    if (x >= 0 && x < m && y >= 0 && y < n && vis[x][y] == 1) {
                        vis[x][y] = 2;
                        fresh--;
                        q.offer(new int[]{x, y});
                    }
                }
            }
            if (!q.isEmpty()) {
                minutes++;
            }
        }

        return fresh == 0 ? minutes : -1;
    }

    // public static void main(String[] args) {
    //     RottingOranges rottingOranges = new RottingOranges();

    //     // Example 1
    //     int[][] grid1 = new int[][]{{2, 1, 1}, {1, 1, 0}, {0, 1, 1}};
    //     System.out.println("Minutes to rot all oranges in grid1: " + rottingOranges.orangesRotting(grid1)); // Expected output: 4

    //     // Example 2
    //     int[][] grid2 = new int[][]{{2, 1, 1}, {0, 1, 1}, {1, 0, 1}};
    //     System.out.println("Minutes to rot all oranges in grid2: " + rottingOranges.orangesRotting(grid2)); // Expected output: -1

    //     // Example 3
    //     int[][] grid3 = new int[][]{{0, 2}};
    //     System.out.println("Minutes to rot all oranges in grid3: " + rottingOranges.orangesRotting(grid3)); // Expected output: 0

    //     // Example 4
    //     int[][] grid4 = new int[][]{{1, 2, 0, 1}};
    //     System.out.println("Minutes to rot all oranges in grid4: " + rottingOranges.orangesRotting(grid4)); // Expected output: -1
    // }
}