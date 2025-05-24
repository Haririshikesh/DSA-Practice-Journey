/*
Problem Title: Two Sum
Problem Link: [https://leetcode.com/problems/two-sum/](https://leetcode.com/problems/two-sum/)

Problem Description:
Given an array of integers `nums` and an integer `target`, return *indices of the two numbers such that they add up to `target`*.

You may assume that each input would have exactly one solution, and you may not use the same element twice.
You can return the answer in any order.

Example 1:
Input: nums = [2,7,11,15], target = 9
Output: [0,1]
Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].

Example 2:
Input: nums = [3,2,4], target = 6
Output: [1,2]

Example 3:
Input: nums = [3,3], target = 6
Output: [0,1]

Constraints:
- 2 <= nums.length <= 10^4
- -10^9 <= nums[i] <= 10^9
- -10^9 <= target <= 10^9
- Only one valid answer exists.

Initial Intuition:
We need to find two numbers in the array that sum up to a specific `target`. A naive approach would be to check every possible pair of numbers, but this would be inefficient (O(N^2)). We need a faster way to look up the "complement" (i.e., `target - current_number`) of the current number we are examining.

Approach: Hash Map (One-Pass)
A Hash Map (or HashMap in Java) is ideal for efficient lookups. We can iterate through the array once. For each number, we calculate its `complement` (the value needed to reach the `target`). We then check if this `complement` already exists in our HashMap. If it does, we've found our pair. If not, we store the current number and its index in the HashMap for future lookups.

Detailed Explanation of Hash Map Approach:
1.  **Initialize HashMap:** Create a `HashMap<Integer, Integer>` (let's call it `mp`). This map will store `(number, index)` pairs.
2.  **Iterate Through `nums`:** Loop through the `nums` array from `i = 0` to `nums.length - 1`.
3.  **Calculate Complement:** In each iteration, get the `current_number = nums[i]`. Calculate the `complement = target - current_number`.
4.  **Check for Complement:**
    * Check if the `mp` already `containsKey(complement)`.
    * If `true`, it means we've previously encountered the number that, when added to `current_number`, equals `target`. We have found our pair. Return a new integer array containing the current index `i` and the index of the `complement` (which we can retrieve from the map using `mp.get(complement)`).
5.  **Store Current Number:** If the `complement` is not found, it means the `current_number` hasn't found its partner yet. Store the `current_number` and its index `i` in the map: `mp.put(current_number, i)`. This makes it available for future numbers to look up.
6.  **Return Empty Array (Fallback):** The problem guarantees exactly one solution, so this line should technically not be reached in a valid LeetCode test case. It's a good practice for robustness in general.

Time Complexity: O(N)
Where N is the number of elements in the `nums` array.
-   We iterate through the array once.
-   In each iteration, `containsKey()` and `get()` operations on a HashMap take, on average, O(1) time. `put()` also takes, on average, O(1) time.
-   Therefore, the total time complexity is linear, O(N).

Space Complexity: O(N)
-   In the worst case, we might store all N elements from the `nums` array into the HashMap before finding the pair (e.g., if the `target` is the sum of the last two elements).
-   Therefore, the space complexity is linear, O(N).
*/
import java.util.HashMap; // Required for HashMap
import java.util.Map;     // Interface for Map
import java.util.Arrays;  // For Arrays.toString() in main method

class Solution {
    public int[] twoSum(int[] nums, int target) {
        // Initialize a HashMap to store number -> index mappings.
        Map<Integer, Integer> mp = new HashMap<>();

        // Iterate through the array.
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            int complement = target - num; // Calculate the complement needed to reach the target.

            // Check if the complement exists in the map.
            if (mp.containsKey(complement)) {
                // If it does, we found the two numbers.
                // Return their indices: current index 'i' and the index of the complement from the map.
                return new int[]{i, mp.get(complement)};
            }

            // If the complement is not found, add the current number and its index to the map.
            mp.put(num, i);
        }

        // The problem guarantees exactly one solution, so this line should theoretically not be reached.
        return new int[]{};
    }

    /*
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Example 1
        int[] nums1 = {2, 7, 11, 15};
        int target1 = 9;
        System.out.println("Nums: " + Arrays.toString(nums1) + ", Target: " + target1 + " -> Result: " + Arrays.toString(solution.twoSum(nums1, target1))); // Expected: [1, 0] or [0, 1]

        // Example 2
        int[] nums2 = {3, 2, 4};
        int target2 = 6;
        System.out.println("Nums: " + Arrays.toString(nums2) + ", Target: " + target2 + " -> Result: " + Arrays.toString(solution.twoSum(nums2, target2))); // Expected: [2, 1] or [1, 2]

        // Example 3
        int[] nums3 = {3, 3};
        int target3 = 6;
        System.out.println("Nums: " + Arrays.toString(nums3) + ", Target: " + target3 + " -> Result: " + Arrays.toString(solution.twoSum(nums3, target3))); // Expected: [1, 0] or [0, 1]

        // Custom Test Case
        int[] nums4 = {1, 5, 9, 2, 8};
        int target4 = 10;
        System.out.println("Nums: " + Arrays.toString(nums4) + ", Target: " + target4 + " -> Result: " + Arrays.toString(solution.twoSum(nums4, target4))); // Expected: [4, 1] or [1, 4]
    }
    */
}