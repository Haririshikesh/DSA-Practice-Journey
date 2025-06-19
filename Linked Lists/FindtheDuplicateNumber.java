// LeetCode Problem: Find the Duplicate Number
// Problem Link: https://leetcode.com/problems/find-the-duplicate-number/

public class FindtheDuplicateNumber {
    public int findDuplicate(int[] nums) {
        // This problem can be elegantly solved using Floyd's Cycle-Finding Algorithm
        // (also known as the Tortoise and Hare algorithm), which is typically
        // used for linked lists. The trick here is to view the array as a linked list.

        // Analogy:
        // - Array indices (0 to n-1) are like nodes in a linked list.
        // - The value at an index `nums[i]` is like the `next` pointer from node `i` to node `nums[i]`.
        // - Since all numbers are in the range [1, n] and there are n+1 numbers,
        //   there must be at least one duplicate. This duplicate creates a cycle
        //   in our "conceptual linked list".
        //   For example, if nums = [1,3,4,2,2]:
        //   Path starting from index 0: 0 -> nums[0]=1 -> nums[1]=3 -> nums[3]=2 -> nums[2]=4 -> nums[4]=2 (cycle detected: 2 -> 4 -> 2)
        //   The duplicate number is the entry point of this cycle.

        // Step 1: Find the meeting point (intersection) of the two pointers.
        // This step is identical to detecting a cycle in a linked list.
        int slow = nums[0]; // Start slow pointer from the value at index 0
        int fast = nums[0]; // Start fast pointer from the value at index 0

        // Move slow one step and fast two steps until they meet.
        // The 'do-while' loop ensures that the pointers move at least once
        // before checking for equality, as they start at the same position.
        do {
            slow = nums[slow];        // Slow moves 1 step
            fast = nums[nums[fast]];  // Fast moves 2 steps (nums[fast] gets the next "node", then nums[...] gets the next-next "node")
        } while (slow != fast); // Continue until they meet

        // At this point, 'slow' and 'fast' have met inside the cycle.

        // Step 2: Find the entry point of the cycle.
        // According to Floyd's algorithm, if one pointer (e.g., slow) is reset
        // to the beginning (nums[0] in this case) and both pointers move one
        // step at a time, they will meet at the start of the cycle.
        int slow2 = nums[0]; // Initialize a second slow pointer from the start

        // Move both 'slow' (from the meeting point) and 'slow2' (from the start)
        // one step at a time until they meet.
        while( slow != slow2 ){
            slow = nums[slow];   // Move 'slow' one step
            slow2 = nums[slow2]; // Move 'slow2' one step
        }

        // The point where they meet is the entry point of the cycle,
        // which is the duplicate number.
        return slow; // Or return slow2, as they are at the same position
    }
}

// Example usage and ListNode definition for completeness if needed elsewhere,
// but not directly used by the Solution class for this problem as it's array-based.
/*
class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {
        val = x;
        next = null;
    }
}

public class FindDuplicateExample {
    public static void main(String[] args) {
        Solution sol = new Solution();

        // Example 1
        int[] nums1 = {1, 3, 4, 2, 2};
        System.out.println("Duplicate in [1, 3, 4, 2, 2]: " + sol.findDuplicate(nums1)); // Expected: 2

        // Example 2
        int[] nums2 = {3, 1, 3, 4, 2};
        System.out.println("Duplicate in [3, 1, 3, 4, 2]: " + sol.findDuplicate(nums2)); // Expected: 3

        // Example 3
        int[] nums3 = {1, 1};
        System.out.println("Duplicate in [1, 1]: " + sol.findDuplicate(nums3)); // Expected: 1

        // Example 4
        int[] nums4 = {1, 1, 2};
        System.out.println("Duplicate in [1, 1, 2]: " + sol.findDuplicate(nums4)); // Expected: 1

        // Example 5
        int[] nums5 = {2, 5, 9, 6, 9, 3, 8, 9, 7, 1};
        System.out.println("Duplicate in [2, 5, 9, 6, 9, 3, 8, 9, 7, 1]: " + sol.findDuplicate(nums5)); // Expected: 9
    }
}
*/

/*
Detailed Explanation of "Find the Duplicate Number" (LeetCode 287)

Problem Link: https://leetcode.com/problems/find-the-duplicate-number/

Problem Description:
Given an array of integers `nums` containing `n + 1` integers where each integer is in the range `[1, n]` inclusive.
There is only one duplicate number in `nums`, find this duplicate number.
You must solve the problem without modifying the array `nums` and uses only constant extra space.

Why it's solved using a Linked List approach (Floyd's Cycle-Finding Algorithm):

The core idea is to transform the array problem into a linked list cycle detection problem.
Imagine the array `nums` as a series of nodes in a linked list.
- Each index `i` in the array `0, 1, ..., n` represents a "node".
- The value `nums[i]` represents the "next pointer" from node `i` to node `nums[i]`.

Since there are `n+1` numbers in the array, and all numbers are within the range `[1, n]`, by the Pigeonhole Principle, there must be at least one number that appears more than once. This duplicate number causes a "convergence" in our conceptual linked list, forming a cycle.

Example: `nums = [1, 3, 4, 2, 2]` (Here `n = 4`, `n+1 = 5` elements, values `[1, 4]`)

Let's trace the path starting from index 0:
- From index 0, the "next" node is `nums[0] = 1`. So, `0 -> 1`.
- From index 1, the "next" node is `nums[1] = 3`. So, `1 -> 3`.
- From index 3, the "next" node is `nums[3] = 2`. So, `3 -> 2`.
- From index 2, the "next" node is `nums[2] = 4`. So, `2 -> 4`.
- From index 4, the "next" node is `nums[4] = 2`. So, `4 -> 2`.

The path is `0 -> 1 -> 3 -> 2 -> 4 -> 2 ...`
Notice that `2` is visited twice: once from `3` and once from `4`. This forms a cycle `2 -> 4 -> 2`. The entry point of this cycle is `2`, which is our duplicate number.

Algorithm (Floyd's Cycle-Finding Algorithm):

1.  **Phase 1: Detect a cycle and find a meeting point.**
    - Initialize two pointers, `slow` and `fast`, both starting at `nums[0]`.
    - `slow` moves one step at a time: `slow = nums[slow]`.
    - `fast` moves two steps at a time: `fast = nums[nums[fast]]`.
    - Keep moving them until `slow == fast`. This meeting point will be inside the cycle.

2.  **Phase 2: Find the start of the cycle (the duplicate number).**
    - Reset one pointer (say, `slow2`) back to the starting point (`nums[0]`).
    - Keep `slow` at its meeting point from Phase 1.
    - Move both `slow` and `slow2` one step at a time:
        - `slow = nums[slow]`
        - `slow2 = nums[slow2]`
    - The point where `slow` and `slow2` meet is the entry point of the cycle, which is the duplicate number.

Proof intuition for Phase 2:
Let `D` be the distance from the start (`nums[0]`) to the cycle entry point.
Let `C` be the length of the cycle.
Let `K` be the distance from the cycle entry point to the meeting point of `slow` and `fast`.

When `slow` and `fast` meet:
- `slow` has traveled `D + K` steps.
- `fast` has traveled `D + K + m * C` steps (where `m` is some integer, because fast might have completed `m` full cycles).
Also, `fast` travels twice as fast as `slow`, so `fast_distance = 2 * slow_distance`.
`D + K + m * C = 2 * (D + K)`
`D + K + m * C = 2D + 2K`
`m * C = D + K`

Now, when `slow2` starts from the beginning (`nums[0]`) and `slow` continues from the meeting point.
We want `slow` and `slow2` to meet at the cycle entry point.
Distance `slow2` needs to travel to reach cycle entry = `D`.
Distance `slow` needs to travel *from the meeting point* to reach cycle entry = `C - K` (if moving forward within cycle) or `K` (if moving backward, but we move forward).
Since `m * C = D + K`, we can write `D = m * C - K`.
This means the distance from the start to the cycle entry `D` is equivalent to traveling `m` full cycles *backwards* from the meeting point, or simply `C - K` steps from the meeting point if `m=1`.
Thus, if `slow2` travels `D` steps from the start, and `slow` travels `D` steps (which is `m * C - K` steps from its current position inside the cycle), they will both arrive at the cycle entry point simultaneously.

Complexity Analysis:

Time Complexity: $O(N)$
- Phase 1 (finding meeting point): Both `slow` and `fast` pointers traverse at most $N$ steps. `fast` covers the distance to the cycle + a portion of the cycle. This is $O(N)$.
- Phase 2 (finding cycle entry): `slow` and `slow2` traverse at most $N$ steps. This is $O(N)$.
- Total time complexity: $O(N) + O(N) = O(N)$.

Space Complexity: $O(1)$
- We only use a few pointer variables (`slow`, `fast`, `slow2`). No additional data structures are allocated that depend on the input size.
*/