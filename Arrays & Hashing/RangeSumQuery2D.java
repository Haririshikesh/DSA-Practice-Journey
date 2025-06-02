/*
Problem Title: Range Sum Query 2D - Immutable
Problem Link: https://leetcode.com/problems/range-sum-query-2d-immutable/

Problem Description:
Given a 2D matrix `matrix`, handle multiple queries of the form:
sumRegion(row1, col1, row2, col2), where:
- (row1, col1) is the top-left corner of the query rectangle.
- (row2, col2) is the bottom-right corner.

You must optimize for repeated queries.

Example:
Input
["NumMatrix", "sumRegion", "sumRegion", "sumRegion"]
[[[[3, 0, 1, 4, 2],
   [5, 6, 3, 2, 1],
   [1, 2, 0, 1, 5],
   [4, 1, 0, 1, 7],
   [1, 0, 3, 0, 5]]], [2, 1, 4, 3], [1, 1, 2, 2], [1, 2, 2, 4]]

Output
[null, 8, 11, 12]

Constraints:
- 1 <= matrix.length, matrix[0].length <= 200
- -10⁴ <= matrix[i][j] <= 10⁴
- 0 <= row1 <= row2 < matrix.length
- 0 <= col1 <= col2 < matrix[0].length
- At most 10⁴ calls will be made to sumRegion

Approach:
We use a **prefix sum matrix** (`sums`) to preprocess the cumulative sum up to each point (i, j) in the matrix.

Key Idea:
For fast range sum queries, we precompute the 2D prefix sums using:
sums[i][j] = matrix[i-1][j-1] + sums[i-1][j] + sums[i][j-1] - sums[i-1][j-1]

For a query from (row1, col1) to (row2, col2), the sum is computed in constant time using:
sums[row2+1][col2+1] - sums[row1][col2+1] - sums[row2+1][col1] + sums[row1][col1]

Time Complexity:
- Constructor: O(m * n) — preprocessing time to compute prefix sums
- sumRegion(): O(1) — constant time per query using precomputed sums

Space Complexity:
- O(m * n) — for the prefix sum matrix
*/

class NumMatrix {
    private int[][] sums; // 2D array to store prefix sums

    public NumMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        // Initialize prefix sum matrix with 1 extra row and column for easier calculations
        sums = new int[rows + 1][cols + 1];

        // Build the prefix sum matrix
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                // Standard 2D prefix sum formula
                sums[i][j] = matrix[i - 1][j - 1]
                           + sums[i - 1][j]
                           + sums[i][j - 1]
                           - sums[i - 1][j - 1];
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        // Use the inclusion-exclusion principle
        return sums[row2 + 1][col2 + 1]
             - sums[row1][col2 + 1]
             - sums[row2 + 1][col1]
             + sums[row1][col1];
    }

    // For testing purposes
    public static void main(String[] args) {
        int[][] matrix = {
            {3, 0, 1, 4, 2},
            {5, 6, 3, 2, 1},
            {1, 2, 0, 1, 5},
            {4, 1, 0, 1, 7},
            {1, 0, 3, 0, 5}
        };

        NumMatrix obj = new NumMatrix(matrix);

        // Test cases
        System.out.println("sumRegion(2,1,4,3): " + obj.sumRegion(2, 1, 4, 3)); // Expected: 8
        System.out.println("sumRegion(1,1,2,2): " + obj.sumRegion(1, 1, 2, 2)); // Expected: 11
        System.out.println("sumRegion(1,2,2,4): " + obj.sumRegion(1, 2, 2, 4)); // Expected: 12

        // Additional test case
        System.out.println("sumRegion(0,0,0,0): " + obj.sumRegion(0, 0, 0, 0)); // Expected: 3
    }
}
