/*
Problem Title: Concatenation of Array
Problem Link: [https://leetcode.com/problems/concatenation-of-array/](https://leetcode.com/problems/concatenation-of-array/)

Problem Description:
Given an integer array `nums` of length `n`, you want to create an array `ans` of length `2n` where `ans[i] == nums[i]` and `ans[i + n] == nums[i]` for `0 <= i < n`.
Specifically, `ans` is formed by concatenating two copies of `nums`.

Return the array `ans`.

Example 1:
Input: nums = [1,2,1]
Output: [1,2,1,1,2,1]
Explanation: The array ans is formed as follows:
- ans = [nums[0],nums[1],nums[2],nums[0],nums[1],nums[2]]
- ans = [1,2,1,1,2,1]

Example 2:
Input: nums = [1,3,2,1]
Output: [1,3,2,1,1,3,2,1]
Explanation: The array ans is formed as follows:
- ans = [nums[0],nums[1],nums[2],nums[3],nums[0],nums[1],nums[2],nums[3]]
- ans = [1,3,2,1,1,3,2,1]

Constraints:
- n == nums.length
- 1 <= n <= 1000
- 1 <= nums[i] <= 1000

Initial Intuition:
The problem is very direct: we need to create a new array that is essentially two copies of the original array placed side-by-side. This means the new array will be twice the size of the original.

Approach: Direct Array Copying
We can achieve this by creating a new array of double the size and then iterating through the original array once. In each iteration, we place the current element from the original array into two positions in the new array: its original index and its original index plus the length of the original array.

Detailed Explanation of Direct Array Copying Approach:
1.  **Determine Length:** Get the length of the input array `nums`, let's call it `n`.
2.  **Create Result Array:** Declare a new integer array, `ans`, with a length of `2 * n`.
3.  **Populate `ans` Array:** Iterate from `i = 0` to `n - 1` (the length of `nums`). In each iteration:
    * Assign `nums[i]` to `ans[i]`. This populates the first half of the `ans` array.
    * Assign `nums[i]` to `ans[i + n]`. This populates the second half of the `ans` array, effectively placing a second copy of `nums` right after the first.
4.  **Return `ans`:** After the loop completes, the `ans` array will contain the concatenated result. Return this array.

Time Complexity: O(N)
Where N is the length of the input array `nums`.
-   Creating the `ans` array takes O(N) time.
-   The single loop iterates N times. Inside the loop, array assignments are O(1) operations.
-   Therefore, the total time complexity is linear, O(N).

Space Complexity: O(N)
-   We create a new array `ans` whose size is `2 * N`. This means the space used scales linearly with the input size.
-   Therefore, the space complexity is O(N).
*/
import java.util.Arrays; // Required for Arrays.toString() in the main method

class Solution {
    public int[] getConcatenation(int[] nums) {
        // Get the length of the input array.
        int n = nums.length;

        // Create a new array 'ans' with double the length of 'nums'.
        int[] ans = new int[2 * n];

        // Iterate through the original 'nums' array.
        for (int i = 0; i < n; i++) {
            // Place the current element nums[i] into the first half of 'ans'.
            ans[i] = nums[i];
            // Place the current element nums[i] into the second half of 'ans'.
            // The index for the second half is i + n.
            ans[i + n] = nums[i];
        }

        // Return the concatenated array.
        return ans;
    }

    /*
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Example 1
        int[] nums1 = {1, 2, 1};
        System.out.println("Concatenation of " + Arrays.toString(nums1) + ": " + Arrays.toString(solution.getConcatenation(nums1))); // Expected: [1, 2, 1, 1, 2, 1]

        // Example 2
        int[] nums2 = {1, 3, 2, 1};
        System.out.println("Concatenation of " + Arrays.toString(nums2) + ": " + Arrays.toString(solution.getConcatenation(nums2))); // Expected: [1, 3, 2, 1, 1, 3, 2, 1]

        // Custom Test Case: Single element array
        int[] nums3 = {5};
        System.out.println("Concatenation of " + Arrays.toString(nums3) + ": " + Arrays.toString(solution.getConcatenation(nums3))); // Expected: [5, 5]

        // Custom Test Case: Empty array (though constraints say n >= 1, good for robustness)
        // int[] nums4 = {}; // This would cause an error due to n=0 and 2*n=0 array creation.
        // The problem constraints state 1 <= n <= 1000, so an empty array is not a valid input.
    }
    */
}