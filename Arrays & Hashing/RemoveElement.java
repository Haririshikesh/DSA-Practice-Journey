/*
Problem Title: Majority Element
Problem Link: [https://leetcode.com/problems/majority-element/](https://leetcode.com/problems/majority-element/)

Problem Description:
Given an array `nums` of size `n`, return the majority element.
The majority element is the element that appears more than `⌊n / 2⌋` times.
You may assume that the majority element always exists in the array.

Example 1:
Input: nums = [3,2,3]
Output: 3

Example 2:
Input: nums = [2,2,1,1,1,2,2]
Output: 2

Constraints:
- n == nums.length
- 1 <= n <= 5 * 10^4
- -10^9 <= nums[i] <= 10^9

Initial Intuition:
The problem asks us to find an element that appears more than half the time in an array. Since the majority element is guaranteed to exist and appears more than `n/2` times, it means that if we sort the array, this element *must* occupy the middle position.

Approach: Sorting
A simple and effective approach for this problem, given the constraint that the majority element *always* exists, is to sort the array. Once the array is sorted, the majority element, by definition (appearing more than `n/2` times), will always be present at the middle index of the array.

Detailed Explanation of Sorting Approach:
1.  **Sort the array:** Use Java's built-in `Arrays.sort(nums)` method. This will arrange the elements of the `nums` array in non-decreasing order.
2.  **Identify Middle Element:** Since the majority element appears more than `⌊n / 2⌋` times, it will necessarily "take over" the middle position(s) of the sorted array. For an array of length `n`, the middle index is `n / 2` (using integer division).
3.  **Return Middle Element:** The element at `nums[nums.length / 2]` will be the majority element.

Why this works:
Consider an array of length `n`. If an element appears `k` times, and `k > n/2`, then in a sorted array, these `k` occurrences will be contiguous. Since `k` is more than half of `n`, the block of `k` identical elements *must* span across the middle index `n/2`. Therefore, `nums[n/2]` will always be the majority element.

Time Complexity: O(N log N)
Where N is the number of elements in the `nums` array.
-   The dominant operation is `Arrays.sort()`, which typically uses a Dual-Pivot Quicksort in Java, giving an average time complexity of O(N log N).
-   Retrieving the element at `nums.length / 2` is an O(1) operation.
-   Therefore, the overall complexity is dominated by the sorting step, leading to O(N log N).

Space Complexity: O(log N) or O(N)
-   The space complexity depends on the sorting algorithm used. Java's `Arrays.sort()` for primitive types (like `int[]`) often uses a Quicksort variation, which might use O(log N) space due to recursion stack (average case) or O(N) in the worst case. For object types, it might use O(N) for temporary storage. In competitive programming contexts, if an in-place sort is assumed, it can sometimes be considered O(1) auxiliary space beyond input.
*/
import java.util.Arrays; // Required for Arrays.sort() and Arrays.toString()

class Solution {
    public int majorityElement(int[] nums) {
        // Step 1: Sort the array.
        // After sorting, all occurrences of the majority element will be contiguous.
        Arrays.sort(nums);

        // Step 2 & 3: The majority element (appearing > n/2 times) will always be at the middle index.
        return nums[nums.length / 2];
    }

    /*
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Example 1
        int[] nums1 = {3, 2, 3};
        System.out.println("Majority element in " + Arrays.toString(nums1) + ": " + solution.majorityElement(nums1)); // Expected: 3

        // Example 2
        int[] nums2 = {2, 2, 1, 1, 1, 2, 2};
        System.out.println("Majority element in " + Arrays.toString(nums2) + ": " + solution.majorityElement(nums2)); // Expected: 2

        // Custom Test Case: Even length array
        int[] nums3 = {6, 5, 5, 5, 6, 5};
        System.out.println("Majority element in " + Arrays.toString(nums3) + ": " + solution.majorityElement(nums3)); // Expected: 5

        // Custom Test Case: Single element array (n=1, majority element is itself)
        int[] nums4 = {10};
        System.out.println("Majority element in " + Arrays.toString(nums4) + ": " + solution.majorityElement(nums4)); // Expected: 10

        // Custom Test Case: Larger array
        int[] nums5 = {1, 1, 1, 2, 2, 2, 1, 1, 1, 3, 1}; // n=11, majority > 5.5, so > 5 times. '1' appears 7 times.
        System.out.println("Majority element in " + Arrays.toString(nums5) + ": " + solution.majorityElement(nums5)); // Expected: 1
    }
    */
}