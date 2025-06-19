// LeetCode Problem: Top K Frequent Elements
// Problem Link: https://leetcode.com/problems/top-k-frequent-elements/

// This file contains the Java solution for the Top K Frequent Elements problem on LeetCode.
// The problem asks us to find the k most frequent elements in a given integer array.
// The solution uses a combination of HashMap for frequency counting and an array of Lists (buckets) for sorting by frequency.

import java.util.*;

// The uncommented solution class for LeetCode submission:
class Solution {
    /**
     * Finds the k most frequent elements in the given array.
     *
     * @param nums The input array of integers.
     * @param k The number of most frequent elements to find.
     * @return An array containing the k most frequent elements.
     */
    public int[] topKFrequent(int[] nums, int k) {
        // Step 1: Count the frequency of each number using a HashMap.
        // The key will be the number, and the value will be its frequency.
        HashMap<Integer, Integer> mp = new HashMap<>();
        for (int num : nums) {
            mp.put(num, 1 + mp.getOrDefault(num, 0));
        }

        // Step 2: Create an array of Lists (buckets) where the index represents the frequency
        // and the List at that index contains all numbers with that frequency.
        // The maximum possible frequency is nums.length (if all elements are the same).
        // So, the size of the buckets array should be nums.length + 1.
        List<Integer>[] buckets = new List[nums.length + 1];

        // Populate the buckets with numbers based on their frequencies.
        for (int key : mp.keySet()) {
            int freq = mp.get(key); // Get the frequency of the current number (key).
            // If the bucket for this frequency is null, initialize it as a new ArrayList.
            if (buckets[freq] == null) {
                buckets[freq] = new ArrayList<>();
            }
            // Add the number (key) to the list corresponding to its frequency.
            buckets[freq].add(key);
        }

        // Step 3: Iterate through the buckets from the highest possible frequency down to 0
        // to collect the top k frequent elements.
        int[] ans = new int[k]; // Array to store the final k frequent elements.
        int ind = 0; // Index for the 'ans' array.

        // Iterate from the highest frequency (nums.length) down to 0.
        for (int i = nums.length; i >= 0; i--) {
            // If the current frequency bucket is not null (i.e., there are elements with this frequency).
            if (buckets[i] != null) {
                // Iterate through the elements in this frequency bucket.
                // We stop if we have already collected 'k' elements (ind == k).
                for (int j = 0; j < buckets[i].size() && ind < k; j++) {
                    ans[ind++] = buckets[i].get(j); // Add the element to our result array.
                }
            }
        }
        return ans; // Return the array of k most frequent elements.
    }
}

// Example Usage and Main Method:
// This section demonstrates how to use the Solution class with sample inputs.
// It's commented out to ensure the file is clean for LeetCode submission,
// but you can uncomment it to run and test locally.

/*
public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();

        // Example 1
        int[] nums1 = {1, 1, 1, 2, 2, 3};
        int k1 = 2;
        System.out.println("Example 1:");
        int[] result1 = sol.topKFrequent(nums1, k1);
        System.out.print("Output: [");
        for (int i = 0; i < result1.length; i++) {
            System.out.print(result1[i] + (i == result1.length - 1 ? "" : ", "));
        }
        System.out.println("]"); // Expected: [1, 2] or [2, 1] (order doesn't matter for elements with same frequency)
        System.out.println("Expected: [1, 2] (order doesn't matter)\n");


        // Example 2
        int[] nums2 = {1};
        int k2 = 1;
        System.out.println("Example 2:");
        int[] result2 = sol.topKFrequent(nums2, k2);
        System.out.print("Output: [");
        for (int i = 0; i < result2.length; i++) {
            System.out.print(result2[i] + (i == result2.length - 1 ? "" : ", "));
        }
        System.out.println("]"); // Expected: [1]
        System.out.println("Expected: [1]\n");

        // Example 3
        int[] nums3 = {4,1,-1,2,-1,2,3};
        int k3 = 2;
        System.out.println("Example 3:");
        int[] result3 = sol.topKFrequent(nums3, k3);
        System.out.print("Output: [");
        for (int i = 0; i < result3.length; i++) {
            System.out.print(result3[i] + (i == result3.length - 1 ? "" : ", "));
        }
        System.out.println("]"); // Expected: [-1, 2] (order doesn't matter)
        System.out.println("Expected: [-1, 2] (order doesn't matter)\n");
    }
}
*/

/*
Problem Explanation:

The "Top K Frequent Elements" problem asks us to identify the `k` integers that appear most frequently in a given array `nums`. The order of the output array does not matter. If multiple elements have the same frequency and are among the top `k`, any of them can be included.

For instance, if `nums = [1,1,1,2,2,3]` and `k = 2`:
* `1` appears 3 times.
* `2` appears 2 times.
* `3` appears 1 time.
The 2 most frequent elements are `1` and `2`.

Approach:

The problem can be efficiently solved in two main steps:

1.  **Frequency Counting:** Determine how many times each unique number appears in the input array `nums`. A `HashMap` is perfectly suited for this. The keys of the map will be the numbers, and the values will be their corresponding frequencies.

2.  **Sorting by Frequency (Bucket Sort/Frequency Array):** Once we have the frequencies, we need to find the `k` elements with the highest frequencies. A common way to do this is using a "bucket sort" like approach or a frequency array.

    * **Bucket Array (Array of Lists):** Create an array of `Lists` (or `ArrayLists`). The index of this array will represent a frequency, and the `List` at that index will store all the numbers that have that particular frequency.
        * The maximum possible frequency for any number in `nums` is `nums.length` (if all elements are the same). So, the `buckets` array should be of size `nums.length + 1` to accommodate frequencies from `0` to `nums.length`.
        * Iterate through the `entrySet()` of the frequency `HashMap`. For each `(number, frequency)` pair:
            * Get the `frequency`.
            * Check if `buckets[frequency]` is `null`. If it is, initialize it as a new `ArrayList`.
            * Add the `number` to `buckets[frequency]`.

3.  **Collecting Top K:** After populating the `buckets` array, the elements with the highest frequencies will be stored in buckets with higher indices.
    * Iterate through the `buckets` array from the highest possible frequency (from `nums.length` down to `0`).
    * For each bucket `i` (representing frequency `i`):
        * If `buckets[i]` is not `null` (meaning there are elements with this frequency):
            * Iterate through the `List` `buckets[i]`. Add each element from this list to your result array until you have collected `k` elements in total.

**Detailed Steps:**

1.  **Initialize `HashMap<Integer, Integer> mp`:** This map will store `(number -> frequency)` pairs.
2.  **Populate `mp`:** Loop through `nums`. For each `num`, increment its count in `mp`. Use `mp.getOrDefault(num, 0)` for convenience.
3.  **Initialize `List<Integer>[] buckets = new List[nums.length + 1]`:** This declares an array where each element can hold a `List` of `Integers`. The size `nums.length + 1` is important because frequencies can range from `0` up to `nums.length`.
4.  **Populate `buckets`:** Iterate through the `keySet()` of `mp`.
    * `int freq = mp.get(key);`
    * `if (buckets[freq] == null) { buckets[freq] = new ArrayList<>(); }`
    * `buckets[freq].add(key);`
5.  **Collect results into `int[] ans = new int[k]`:**
    * Initialize `int ind = 0;` (index for `ans`).
    * Loop `for (int i = nums.length; i >= 0; i--)`: This ensures we process higher frequencies first.
        * `if (buckets[i] != null)`: If there are elements with this frequency.
            * Loop `for (int j = 0; j < buckets[i].size() && ind < k; j++)`: Iterate through the numbers in this bucket. The `ind < k` condition stops early if we've already found `k` elements.
                * `ans[ind++] = buckets[i].get(j);`
6.  **Return `ans`**.

Why not other approaches?

* **Sorting `HashMap` entries:** You could extract all `(number, frequency)` pairs from the `HashMap` into a `List` of `Map.Entry` objects and then sort this `List` based on frequencies in descending order. Then, pick the first `k` elements. While valid, sorting takes $O(N \log N)$ time (where $N$ is the number of unique elements), and the bucket sort approach can achieve better average-case performance.
* **Min-Heap (Priority Queue):** A common approach is to use a Min-Heap.
    1.  Count frequencies (same as Step 1).
    2.  Iterate through the frequency map. For each `(number, frequency)` pair, add it to a Min-Heap. The heap should order elements by frequency (lowest frequency at the top).
    3.  If the heap size exceeds `k`, remove the top element (the one with the lowest frequency).
    4.  After processing all elements, the heap will contain the `k` most frequent elements.
    This approach works well and is often preferred for its general applicability. The time complexity would be $O(N \log k)$ for insertion/deletion from the heap, after $O(N)$ for counting frequencies.
* **Why is the provided solution (Bucket Sort) often preferred here?**
    * It achieves a better time complexity of $O(N)$ (linear time) because we avoid explicit sorting. The "sorting" is done implicitly by placing elements into frequency-indexed buckets. This is because the maximum frequency is bounded by `nums.length`, which allows for direct indexing into the `buckets` array.

Complexity Analysis:

Let `n` be the number of elements in the input array `nums`.
Let `U` be the number of unique elements in `nums` ($U \le n$).

* **Time Complexity:**
    * **Step 1 (Frequency Counting):** Iterating through `nums` once and performing `HashMap` operations (put, get, getOrDefault). On average, `HashMap` operations are $O(1)$. In the worst case (hash collisions), they can be $O(L)$ where $L$ is the number of elements in a bucket. Assuming good hash functions, this step is $O(n)$.
    * **Step 2 (Populating Buckets):** Iterating through the `U` unique keys in the `HashMap` and performing `ArrayList` `add` operations. `ArrayList.add` is amortized $O(1)$. This step is $O(U)$. Since $U \le n$, this is $O(n)$.
    * **Step 3 (Collecting Top K):** Iterating through the `buckets` array from `nums.length` down to `0`. In the worst case, we might traverse all `nums.length + 1` buckets. Inside the loop, we iterate through the `List`s in the buckets. The total number of elements we process across all lists in all buckets is `n` (the total number of elements in the original `nums` array). This step is $O(n)$.
    * **Overall Time Complexity:** Summing up the complexities of all steps, we get $O(n) + O(n) + O(n) = O(n)$. This makes it a linear time solution, which is generally very efficient.

* **Space Complexity:**
    * **`mp` (HashMap):** Stores up to `U` unique elements and their frequencies. So, $O(U)$ space.
    * **`buckets` (Array of Lists):** The array has size `n + 1`. The total number of elements stored across all `Lists` in the `buckets` array is `U` (each unique number is stored once). So, $O(n + U)$ space, which simplifies to $O(n)$ since $U \le n$.
    * **`ans` (Result Array):** Stores `k` elements. So, $O(k)$ space.
    * **Overall Space Complexity:** $O(n + U + k) = O(n)$ (since $U \le n$ and $k \le n$).
*/