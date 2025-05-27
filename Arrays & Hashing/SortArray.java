/*
Problem Title: Sort an Array
Problem Link: [https://leetcode.com/problems/sort-an-array/](https://leetcode.com/problems/sort-an-array/)

Problem Description:
Given an array of integers `nums`, sort the array in ascending order and return it.
You must solve the problem without using any built-in library's sort function.

Constraints:
- 1 <= nums.length <= 5 * 10^4
- -5 * 10^4 <= nums[i] <= 5 * 10^4

Initial Intuition:
The problem asks us to implement a sorting algorithm from scratch. Given the constraints, an efficient comparison-based sort with an average time complexity of O(N log N) is required. Common choices include Merge Sort, Quick Sort, and Heap Sort. The provided solution implements Quick Sort, specifically a variant called Dual-Pivot Quick Sort.

Approach: Dual-Pivot Quick Sort
Quick Sort is a divide-and-conquer algorithm. It works by:
1.  **Picking a Pivot:** Choosing an element from the array, called a pivot.
2.  **Partitioning:** Rearranging the array such that elements smaller than the pivot come before it, and elements greater than the pivot come after it. Elements equal to the pivot can go on either side (or in a separate equal partition).
3.  **Recursion:** Recursively applying the above steps to the sub-arrays on either side of the pivot.

Dual-Pivot Quick Sort is an optimization where two pivots are chosen. This partitions the array into three parts:
-   Elements less than the first pivot.
-   Elements between the two pivots.
-   Elements greater than the second pivot.
This often leads to better performance than single-pivot quicksort on average, especially for large datasets. Java's `Arrays.sort()` for primitive types uses a Dual-Pivot Quicksort.

Detailed Explanation of Dual-Pivot Quick Sort:

1.  **`sortArray(int[] nums)`:**
    * This is the main public method. It initiates the quicksort process by calling the recursive `quickSort` helper function with the entire array range (`0` to `nums.length - 1`).
    * Finally, it returns the sorted `nums` array (as quicksort sorts in-place).

2.  **`quickSort(int[] nums, int low, int high)`:**
    * This is the recursive helper function that sorts the sub-array from `low` to `high`.
    * **Base Case:** If `low` is greater than or equal to `high`, it means the sub-array has 0 or 1 element, which is already sorted. So, it simply returns.
    * **Partitioning:** It calls the `partition` method to arrange the elements around two pivots and gets back the final indices of the two pivots. `int[] pvtIndices = partition(nums, low, high);`
    * **Recursive Calls:** It then makes three recursive calls to sort the three partitions:
        * Left partition (elements less than the first pivot): `quickSort(nums, low, pvtIndices[0] - 1);`
        * Middle partition (elements between the two pivots): `quickSort(nums, pvtIndices[0] + 1, pvtIndices[1] - 1);`
        * Right partition (elements greater than the second pivot): `quickSort(nums, pvtIndices[1] + 1, high);`

3.  **`partition(int[] nums, int low, int high)`:**
    * This is the core logic for partitioning the array using two pivots.
    * **Pivot Selection and Initial Swap:**
        * It selects `nums[low]` as the first pivot (`pvt1`) and `nums[high]` as the second pivot (`pvt2`).
        * It ensures `pvt1 <= pvt2` by swapping them if `nums[low] > nums[high]`.
    * **Pointers:**
        * `i`: Current element pointer, traverses from `low + 1` towards `gt`.
        * `lt`: Pointer for elements less than `pvt1`. Elements `nums[low+1]` to `nums[lt-1]` are `< pvt1`.
        * `gt`: Pointer for elements greater than `pvt2`. Elements `nums[gt+1]` to `nums[high-1]` are `> pvt2`.
    * **Partitioning Loop (`while(i <= gt)`):**
        * If `nums[i] < pvt1`: Swap `nums[i]` with `nums[lt]`. Increment both `lt` and `i`.
        * If `nums[i] > pvt2`: Swap `nums[i]` with `nums[gt]`. Decrement `gt` (note: `i` does not increment here, as the swapped element from `gt` needs to be checked).
        * Else (`pvt1 <= nums[i] <= pvt2`): `nums[i]` is in the middle partition. Just increment `i`.
    * **Place Pivots:** After the loop, the pivots are not yet in their final sorted positions.
        * `swap(nums, low, --lt);` : Places `pvt1` into its correct sorted position. `lt` now points to the index of `pvt1`.
        * `swap(nums, high, ++gt);` : Places `pvt2` into its correct sorted position. `gt` now points to the index of `pvt2`.
    * **Return Pivot Indices:** Returns `new int[]{lt, gt}`, which are the final indices of `pvt1` and `pvt2`, respectively. These indices are used by `quickSort` to define the recursive sub-problems.

4.  **`swap(int[] nums, int i, int j)`:**
    * A simple helper function to swap two elements in the array.

Time Complexity: O(N log N) on average, O(N^2) in worst case.
-   N is the number of elements in the array.
-   Dual-Pivot Quick Sort performs partitioning in linear time for each level of recursion.
-   The recursion depth is log N on average.
-   In the worst case (e.g., already sorted array or reverse sorted array if not handled), standard Quick Sort can degrade to O(N^2). Dual-Pivot Quick Sort generally performs better and is less prone to worst-case behavior with certain input distributions, though it technically can still hit O(N^2).

Space Complexity: O(log N) on average, O(N) in worst case.
-   This is due to the recursion stack used during the `quickSort` calls.
-   The depth of the recursion stack is proportional to the number of partitions.
-   On average, the partitions are balanced, leading to O(log N) space. In the worst case (unbalanced partitions), it can go up to O(N).
*/
import java.util.Arrays; // Required for Arrays.toString() in main method

class Solution {

    public int[] sortArray(int[] nums) {
        quickSort(nums, 0, nums.length - 1);
        return nums;
    }

    // Main recursive Quick Sort function
    public void quickSort(int[] nums, int low, int high) {
        if (low < high) {
            // Partition the array and get the final indices of the two pivots
            int[] pvtIndices = partition(nums, low, high);

            // Recursively sort the three partitions
            quickSort(nums, low, pvtIndices[0] - 1);        // Left partition (elements < pivot1)
            quickSort(nums, pvtIndices[0] + 1, pvtIndices[1] - 1); // Middle partition (elements between pivot1 and pivot2)
            quickSort(nums, pvtIndices[1] + 1, high);       // Right partition (elements > pivot2)
        }
    }

    // Partition function for Dual-Pivot Quick Sort
    public int[] partition(int[] nums, int low, int high) {
        // Ensure pivot1 is less than or equal to pivot2
        if (nums[low] > nums[high]) {
            swap(nums, low, high);
        }

        int pvt1 = nums[low];  // First pivot
        int pvt2 = nums[high]; // Second pivot

        // i: current element pointer, moves from left to right
        // lt: pointer for elements smaller than pvt1 (left partition end)
        // gt: pointer for elements larger than pvt2 (right partition start)
        int i = low + 1;
        int lt = i;
        int gt = high - 1;

        while (i <= gt) {
            if (nums[i] < pvt1) {
                // Move elements less than pvt1 to the left partition
                swap(nums, lt++, i++);
            } else if (nums[i] > pvt2) {
                // Move elements greater than pvt2 to the right partition
                swap(nums, gt--, i); // i does not increment here as the swapped element needs to be checked
            } else {
                // Element is between pvt1 and pvt2, leave it in the middle partition
                i++;
            }
        }

        // Place pivots into their final sorted positions
        swap(nums, low, --lt);  // pvt1 moves to its final position
        swap(nums, high, ++gt); // pvt2 moves to its final position

        // Return the final indices of the two pivots
        return new int[]{lt, gt};
    }

    // Helper function to swap two elements in the array
    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /*
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Example 1
        int[] nums1 = {5, 2, 3, 1};
        System.out.println("Original: " + Arrays.toString(nums1));
        solution.sortArray(nums1);
        System.out.println("Sorted: " + Arrays.toString(nums1)); // Expected: [1, 2, 3, 5]

        System.out.println("\n--- Next Test ---");

        // Example 2
        int[] nums2 = {5, 1, 1, 2, 0, 0};
        System.out.println("Original: " + Arrays.toString(nums2));
        solution.sortArray(nums2);
        System.out.println("Sorted: " + Arrays.toString(nums2)); // Expected: [0, 0, 1, 1, 2, 5]

        System.out.println("\n--- Next Test ---");

        // Custom Test Case: Already sorted
        int[] nums3 = {1, 2, 3, 4, 5};
        System.out.println("Original: " + Arrays.toString(nums3));
        solution.sortArray(nums3);
        System.out.println("Sorted: " + Arrays.toString(nums3)); // Expected: [1, 2, 3, 4, 5]

        System.out.println("\n--- Next Test ---");

        // Custom Test Case: Reverse sorted
        int[] nums4 = {5, 4, 3, 2, 1};
        System.out.println("Original: " + Arrays.toString(nums4));
        solution.sortArray(nums4);
        System.out.println("Sorted: " + Arrays.toString(nums4)); // Expected: [1, 2, 3, 4, 5]

        System.out.println("\n--- Next Test ---");

        // Custom Test Case: Array with duplicates and negatives
        int[] nums5 = {-3, 0, 2, -5, 0, 7, -1};
        System.out.println("Original: " + Arrays.toString(nums5));
        solution.sortArray(nums5);
        System.out.println("Sorted: " + Arrays.toString(nums5)); // Expected: [-5, -3, -1, 0, 0, 2, 7]

        System.out.println("\n--- Next Test ---");

        // Custom Test Case: Large array (within constraints)
        int[] nums6 = new int[100];
        for (int i = 0; i < 100; i++) {
            nums6[i] = (int) (Math.random() * 200) - 100; // Random numbers between -100 and 99
        }
        System.out.println("Original (partial): " + Arrays.toString(Arrays.copyOfRange(nums6, 0, 10)) + "...");
        solution.sortArray(nums6);
        System.out.println("Sorted (partial): " + Arrays.toString(Arrays.copyOfRange(nums6, 0, 10)) + "...");
        // You'd typically verify this with a larger print or a test framework for correctness.
    }
    */
}