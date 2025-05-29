/*
Problem Title: Top K Frequent Elements
Problem Link: [https://leetcode.com/problems/top-k-frequent-elements/](https://leetcode.com/problems/top-k-frequent-elements/)

Problem Description:
Given an integer array `nums` and an integer `k`, return the `k` most frequent elements. You may return the answer in any order.

Example 1:
Input: nums = [1,1,1,2,2,3], k = 2
Output: [1,2]

Example 2:
Input: nums = [1], k = 1
Output: [1]

Constraints:
- 1 <= nums.length <= 10^5
- -10^4 <= nums[i] <= 10^4
- `k` is in the range [1, the number of unique elements in the array].
- It is guaranteed that the answer is unique.

Initial Intuition:
We need to find elements that appear most frequently. This involves two main steps:
1.  Counting the frequency of each unique element.
2.  Selecting the `k` elements with the highest frequencies.

A naive approach might involve counting frequencies then sorting all unique elements by their frequencies, but sorting would take O(N log N) or O(U log U) where U is unique elements. We can do better.

Approach: Bucket Sort (Frequency Buckets)
This approach leverages the fact that element frequencies can range from 1 to `nums.length`. We can create an array of "buckets" where each bucket `buckets[i]` stores a list of numbers that appear `i` times (i.e., have a frequency of `i`).

Detailed Explanation of Bucket Sort Approach:

1.  **Count Frequencies (HashMap):**
    * Use a `HashMap<Integer, Integer>` (e.g., `mp`) to store the frequency of each number in `nums`.
    * Iterate through `nums`. For each `num`, increment its count in the `mp`. `mp.getOrDefault(num, 0)` is a handy way to get the current count or 0 if not present.

2.  **Create Frequency Buckets (Array of Lists):**
    * Create an array of `List<Integer>` called `buckets`. The size of this array should be `nums.length + 1` because the maximum possible frequency of an element is `nums.length` (if all elements are the same). `buckets[i]` will store numbers that have appeared `i` times.
    * Initialize each element of the `buckets` array to a new `ArrayList<Integer>` only when it's first needed (`if(buckets[freq]==null)`).

3.  **Populate Buckets:**
    * Iterate through the `keySet()` of the `mp` (all unique numbers).
    * For each `num` (key from `mp`):
        * Get its `freq` (value from `mp`).
        * Add `num` to `buckets[freq]`.

4.  **Collect Top K Elements:**
    * Create an integer array `ans` of size `k` to store the final result.
    * Initialize an `index` variable (e.g., `ind`) to 0. This will track the current position in the `ans` array.
    * Iterate through the `buckets` array from the highest possible frequency down to 0 (`i = nums.length` down to `0`).
    * For each `bucket[i]` (representing frequency `i`):
        * If `buckets[i]` is not `null` (meaning there are elements with this frequency):
            * Iterate through the list of numbers in `buckets[i]`.
            * For each `num` in `buckets[i]`, add it to `ans[ind]` and increment `ind`.
            * Stop early if `ind` reaches `k` (we have found `k` most frequent elements).

5.  **Return `ans`:** The `ans` array now contains the `k` most frequent elements.

Time Complexity: O(N)
Where N is the number of elements in `nums`.
-   **Step 1 (Counting Frequencies):** Iterating through `nums` once and performing HashMap operations (`getOrDefault`, `put`) takes O(N) on average (O(N) in worst case for poorly chosen hash functions, but amortized O(1) for `HashMap` in Java).
-   **Step 2 (Initializing Buckets):** Creating the `buckets` array of size `N+1` takes O(N) time.
-   **Step 3 (Populating Buckets):** Iterating through the `mp.keySet()` (which contains at most N unique elements) and performing `get` and `add` operations (to `ArrayList`) takes O(N) on average.
-   **Step 4 (Collecting Top K):**
    -   The outer loop iterates `N+1` times (from `nums.length` down to 0).
    -   The inner loop iterates through `buckets[i]`. Crucially, each unique number is placed into *exactly one* bucket. So, the sum of lengths of all lists in `buckets` is `N` (the total number of unique elements).
    -   We stop once `k` elements are collected. So, this step takes O(N) in the worst case (e.g., if `k` is large and requires checking many buckets).
-   **Total Time Complexity:** All steps are linear with respect to N, making the overall time complexity O(N). This is often better than O(N log N) solutions that involve sorting.

Space Complexity: O(N)
-   **`mp` (HashMap):** In the worst case (all elements are unique), it stores N unique elements. So, O(N) space.
-   **`buckets` (Array of Lists):** The array has size `N+1`. The total number of elements stored across all lists in `buckets` is the number of unique elements, which is at most N. So, O(N) space.
-   **`ans` array:** O(K) space, which is at most O(N).
-   **Total Space Complexity:** O(N).

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        // Step 1: Count Frequencies using a HashMap.
        // mp: number -> frequency
        Map<Integer, Integer> mp = new HashMap<>();
        for (int num : nums) {
            mp.put(num, 1 + mp.getOrDefault(num, 0));
        }

        // Step 2: Create Frequency Buckets (Array of Lists).
        // buckets[i] will contain all numbers that have appeared 'i' times.
        // Max frequency can be nums.length, so array size is nums.length + 1.
        List<Integer>[] buckets = new List[nums.length + 1];

        // Step 3: Populate Buckets.
        // Iterate through the HashMap's keySet (unique numbers).
        for (int number : mp.keySet()) {
            int frequency = mp.get(number); // Get the frequency of the current number.
            if (buckets[frequency] == null) {
                // Initialize the list for this frequency if it's null.
                buckets[frequency] = new ArrayList<>();
            }
            // Add the number to the list corresponding to its frequency.
            buckets[frequency].add(number);
        }

        // Step 4: Collect Top K Elements.
        // Iterate through the buckets array from highest frequency down to 0.
        int[] ans = new int[k]; // Array to store the k most frequent elements.
        int ansIndex = 0; // Current index to fill in the 'ans' array.

        // Start from the highest possible frequency (nums.length) down to 1.
        for (int freq = nums.length; freq >= 0 && ansIndex < k; freq--) {
            // If there are numbers with this frequency, add them to our answer.
            if (buckets[freq] != null) {
                // Iterate through the list of numbers in the current frequency bucket.
                for (int number : buckets[freq]) {
                    if (ansIndex < k) { // Ensure we don't add more than k elements.
                        ans[ansIndex++] = number;
                    } else {
                        break; // Already found k elements.
                    }
                }
            }
        }

        // Return the array containing the k most frequent elements.
        return ans;
    }

    /*
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Example 1
        int[] nums1 = {1, 1, 1, 2, 2, 3};
        int k1 = 2;
        System.out.println("Top " + k1 + " frequent elements in " + Arrays.toString(nums1) + ": " + Arrays.toString(solution.topKFrequent(nums1, k1))); // Expected: [1, 2] (order may vary)

        // Example 2
        int[] nums2 = {1};
        int k2 = 1;
        System.out.println("Top " + k2 + " frequent elements in " + Arrays.toString(nums2) + ": " + Arrays.toString(solution.topKFrequent(nums2, k2))); // Expected: [1]

        // Custom Test Case: Multiple elements with same highest frequency
        int[] nums3 = {1, 2, 3, 4, 1, 2, 3, 1, 2}; // 1:3, 2:3, 3:2, 4:1
        int k3 = 2;
        System.out.println("Top " + k3 + " frequent elements in " + Arrays.toString(nums3) + ": " + Arrays.toString(solution.topKFrequent(nums3, k3))); // Expected: [1, 2] or [2, 1]

        // Custom Test Case: All unique elements
        int[] nums4 = {10, 20, 30, 40, 50};
        int k4 = 3;
        System.out.println("Top " + k4 + " frequent elements in " + Arrays.toString(nums4) + ": " + Arrays.toString(solution.topKFrequent(nums4, k4))); // Expected: [10, 20, 30] (any 3, order may vary)

        // Custom Test Case: k = num_unique_elements
        int[] nums5 = {7, 7, 7, 8, 8, 9}; // 7:3, 8:2, 9:1
        int k5 = 3;
        System.out.println("Top " + k5 + " frequent elements in " + Arrays.toString(nums5) + ": " + Arrays.toString(solution.topKFrequent(nums5, k5))); // Expected: [7, 8, 9] (order may vary)
    }
    */
}