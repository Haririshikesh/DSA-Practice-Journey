/*
Problem Title: Contains Duplicate
Problem Link: [https://leetcode.com/problems/contains-duplicate/](https://leetcode.com/problems/contains-duplicate/)

Problem Description:
Given an integer array `nums`, return `true` if any value appears at least twice in the array, and return `false` if every element is distinct.

Example 1:
Input: nums = [1,2,3,1]
Output: true

Example 2:
Input: nums = [1,2,3,4]
Output: false

Example 3:
Input: nums = [1,1,1,3,3,4,3,2,4,2]
Output: true

Initial Intuition:
The core idea is to check for repetition. If we can easily compare adjacent elements after arranging them in order, or efficiently keep track of elements we've seen, we can solve this.

Approach: Sorting
One straightforward way to detect duplicates is to first sort the array. Once the array is sorted, any duplicate elements will be placed next to each other. We can then simply iterate through the sorted array and check if any adjacent elements are identical.

Detailed Explanation of Sorting Approach:
1.  **Sort the array:** Use Java's built-in `Arrays.sort(nums)` method. This will arrange the elements in non-decreasing order.
2.  **Iterate and Compare:** Start a loop from the second element (`i = 1`) up to the end of the array.
3.  **Check Adjacent Elements:** In each iteration, compare `nums[i]` with the element immediately before it, `nums[i-1]`.
4.  **Return True on Duplicate:** If `nums[i]` is equal to `nums[i-1]`, it means we've found a duplicate, so we can immediately return `true`.
5.  **Return False if No Duplicates:** If the loop completes without finding any adjacent identical elements, it means no duplicates exist in the array, so we return `false`.

Time Complexity: O(N log N)
Where N is the number of elements in the `nums` array.
-   The dominant operation is `Arrays.sort()`, which typically uses a Dual-Pivot Quicksort algorithm in Java, giving an average time complexity of O(N log N).
-   The subsequent loop to check for duplicates takes O(N) time.
-   Overall, the complexity is dominated by the sorting step, leading to O(N log N).

Space Complexity: O(log N) or O(N)
-   The space complexity depends on the sorting algorithm used. Java's `Arrays.sort()` for primitive types (like `int[]`) often uses a Quicksort variation, which might use O(log N) space due to recursion stack (average case) or O(N) in the worst case (though often optimized to avoid worst case). For object types, it might use O(N) for temporary storage (e.g., MergeSort). In Competitive Programming contexts, if an in-place sort is assumed, it can sometimes be considered O(1) auxiliary space beyond input.
*/
import java.util.Arrays; // Required for Arrays.sort() and Arrays.toString()

class Solution {
    public boolean containsDuplicate(int[] nums) {
        // Sort the array in non-decreasing order.
        Arrays.sort(nums);

        // Iterate from the second element and compare with the previous one.
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                // If adjacent elements are equal, a duplicate exists.
                return true;
            }
        }
        // If the loop completes, no duplicates were found.
        return false;
    }

    /*
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Example 1
        int[] nums1 = {1, 2, 3, 1};
        System.out.println("Contains duplicate in [1,2,3,1]: " + solution.containsDuplicate(nums1)); // Expected: true

        // Example 2
        int[] nums2 = {1, 2, 3, 4};
        System.out.println("Contains duplicate in [1,2,3,4]: " + solution.containsDuplicate(nums2)); // Expected: false

        // Example 3
        int[] nums3 = {1, 1, 1, 3, 3, 4, 3, 2, 4, 2};
        System.out.println("Contains duplicate in [1,1,1,3,3,4,3,2,4,2]: " + solution.containsDuplicate(nums3)); // Expected: true

        // Custom Test Case: Empty array
        int[] nums4 = {};
        System.out.println("Contains duplicate in []: " + solution.containsDuplicate(nums4)); // Expected: false

        // Custom Test Case: Single element array
        int[] nums5 = {7};
        System.out.println("Contains duplicate in [7]: " + solution.containsDuplicate(nums5)); // Expected: false
    }
    */
}