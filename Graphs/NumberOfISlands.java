/*
Problem Title: Number of Islands
Problem Link: [https://leetcode.com/problems/number-of-islands/](https://leetcode.com/problems/number-of-islands/)

Problem Description:
Given an m x n 2D binary grid grid which represents a map of '1's (land) and '0's (water), return the number of islands.
An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.

Example 1:
Input: grid = [
  {"1","1","1","1","0"},
  {"1","1","0","1","0"},
  {"1","1","0","0","0"},
  {"0","0","0","0","0"}
]
Output: 1

Example 2:
Input: grid = [
  {"1","1","0","0","0"},
  {"1","1","0","0","0"},
  {"0","0","1","0","0"},
  {"0","0","0","1","1"}
]
Output: 3

Initial Intuition:
The problem asks us to count the number of distinct landmasses in a grid. We can think of each '1' as part of a potential island. When we encounter a '1' that we haven't visited yet, it signifies the start of a new island. We then need to explore all the connected '1's to mark them as visited so that they are not counted as part of a new island later.

Approach: Breadth-First Search (BFS)
We iterate through each cell of the grid. If we find a '1' that hasn't been visited yet, we've found a new island. We then use BFS to explore all the adjacent '1' cells connected to this starting cell. The BFS systematically visits all reachable land cells belonging to the current island and marks them as visited (using the 'vis' HashSet). This ensures that we count each island only once.

Detailed Explanation of BFS:
1. Initialize a queue and add the starting land cell (i, j) to it.
2. While the queue is not empty:
   a. Dequeue a cell (current row 'r', current column 'c').
   b. For each of the four possible directions (up, down, left, right):
      i. Calculate the coordinates of the neighbor cell (next row 'nr', next column 'nc').
      ii. Check if the neighbor cell is within the grid boundaries.
      iii. Check if the neighbor cell is land ('1') and has not been visited yet (not in 'vis').
      iv. If all the conditions are met, mark the neighbor cell as visited by adding its coordinates to the 'vis' HashSet and enqueue it for further exploration.

Time Complexity: O(M * N)
Where M is the number of rows and N is the number of columns in the grid. In the worst case, we might visit every cell in the grid. The outer loops iterate through each cell once. The BFS, in total across all islands, will also visit each land cell at most once because we mark them as visited.

Space Complexity: O(min(M, N)) in the best case (e.g., a single horizontal or vertical line of land) and O(M * N) in the worst case (e.g., the entire grid is land).
- The space complexity is dominated by the 'vis' HashSet and the queue used for BFS.
- In the best case, the queue will hold a number of cells proportional to the shorter dimension of the grid during a single island traversal.
- In the worst case, if the entire grid is land, both the queue and the 'vis' set might contain all M * N cells.
*/
// import java.util.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

class NumberOfISlands {
    int m;
    int n;
    HashSet<String> vis = new HashSet<>();
    int[][] dirs= {{1,0},{0,1},{-1,0},{0,-1}};
    public int numIslands(char[][] grid) {
        int islands = 0;
        m= grid.length; n= grid[0].length;
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                if(grid[i][j]=='1' && !vis.contains(i+','+j)){
                    bfs(grid, i, j);
                    islands++;
                }
            }
        }
        return islands;
    }
    public void bfs(char[][] grid, int i, int j){
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{i,j});
        vis.add(i + "," + j); // Mark the starting cell as visited
        while(!q.isEmpty()){
            int[] ele = q.poll();
            int r = ele[0];
            int c = ele[1];
            for(int[] dir: dirs){
                int nr = dir[0] + r;
                int nc = dir[1] + c;
                if(nr >= 0 && nr < m && nc >= 0 && nc < n && grid[nr][nc] == '1' && !vis.contains(nr + "," + nc)){
                    vis.add(nr + "," + nc);
                    q.offer(new int[]{nr, nc});
                }
            }
        }
    }

    // public static void main(String[] args) {
    //     NumberOfISlands numberOfISlands = new NumberOfISlands();

    //     // Example 1
    //     char[][] grid1 = new char[][]{
    //             {'1','1','1','1','0'},
    //             {'1','1','0','1','0'},
    //             {'1','1','0','0','0'},
    //             {'0','0','0','0','0'}
    //     };
    //     System.out.println("Number of islands in grid1: " + numberOfISlands.numIslands(grid1)); // Expected output: 1

    //     // Example 2
    //     char[][] grid2 = new char[][]{
    //             {'1','1','0','0','0'},
    //             {'1','1','0','0','0'},
    //             {'0','0','1','0','0'},
    //             {'0','0','0','1','1'}
    //     };
    //     System.out.println("Number of islands in grid2: " + numberOfISlands.numIslands(grid2)); // Expected output: 3
    // }
}