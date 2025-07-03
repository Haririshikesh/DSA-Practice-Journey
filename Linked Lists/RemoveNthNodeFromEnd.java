// Main class to demonstrate the solution
public class RemoveNthFromEnd {

    // Definition for singly-linked list.
    // This part would typically be provided by LeetCode or in a separate file.
    // For a self-contained Java file, we include it here.
    static class ListNode {
        int val;
        ListNode next;

        // Default constructor
        ListNode() {}

        // Constructor with value
        ListNode(int val) { this.val = val; }

        // Constructor with value and next node
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    // LeetCode Solution Class
    static class Solution {
        /**
         * Removes the nth node from the end of the linked list and returns its head.
         *
         * @param head The head of the linked list.
         * @param n The position from the end of the list to remove (1-indexed).
         * @return The head of the modified linked list.
         */
        public ListNode removeNthFromEnd(ListNode head, int n) {
            // Create a dummy node. This handles edge cases where the head itself
            // needs to be removed (e.g., n equals the size of the list).
            // The dummy node points to the original head.
            ListNode dummy = new ListNode(0);
            dummy.next = head;

            // Initialize two pointers: 'slow' and 'fast'.
            // 'slow' will eventually point to the node *before* the one to be removed.
            // 'fast' will be 'n' steps ahead of 'slow'.
            ListNode slow = dummy;
            ListNode fast = dummy;

            // ******************* Step 1: Move 'fast' pointer 'n' steps ahead *******************
            // The goal is to create a gap of 'n' nodes between 'slow' and 'fast'.
            // If fast starts at dummy, it needs to move n steps to be n nodes ahead of slow.
            // Example: if n=1, fast moves 1 step. If n=2, fast moves 2 steps.
            for (int i = 0; i < n; i++) {
                // We are guaranteed that n is valid and <= list size, so fast will not become null here.
                fast = fast.next;
            }

            // ******************* Step 2: Move both pointers until 'fast' reaches the end *******************
            // Now, move both 'slow' and 'fast' pointers one step at a time
            // until `fast.next` becomes `null`.
            // When `fast.next` is `null`, it means `fast` is at the last node of the list.
            // At this point, `slow` will be pointing to the node *before* the nth node from the end.
            while (fast.next != null) {
                slow = slow.next;
                fast = fast.next;
            }

            // ******************* Step 3: Remove the nth node from the end *******************
            // `slow` is now pointing to the node just before the node to be removed.
            // `slow.next` is the node to be removed.
            // To remove `slow.next`, we bypass it by setting `slow.next` to `slow.next.next`.
            // This effectively removes the node that `slow.next` was originally pointing to.
            slow.next = slow.next.next;

            // Return the head of the modified list. Since we used a dummy node,
            // the actual head is `dummy.next`.
            return dummy.next;
        }
    }

    // Main method for testing and demonstration
    public static void main(String[] args) {
        Solution sol = new Solution();

        // Example 1: Remove 2nd from end: 1->2->3->4->5, n=2  => 1->2->3->5
        ListNode head1 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        System.out.print("Original List 1: ");
        printList(head1);
        ListNode result1 = sol.removeNthFromEnd(head1, 2);
        System.out.print("Result List 1 (n=2): ");
        printList(result1); // Expected: 1->2->3->5
        System.out.println("---");

        // Example 2: Remove 1st from end: 1, n=1  => empty list
        ListNode head2 = new ListNode(1);
        System.out.print("Original List 2: ");
        printList(head2);
        ListNode result2 = sol.removeNthFromEnd(head2, 1);
        System.out.print("Result List 2 (n=1): ");
        printList(result2); // Expected: empty list
        System.out.println("---");

        // Example 3: Remove 2nd from end: 1->2, n=2  => 2
        ListNode head3 = new ListNode(1, new ListNode(2));
        System.out.print("Original List 3: ");
        printList(head3);
        ListNode result3 = sol.removeNthFromEnd(head3, 2);
        System.out.print("Result List 3 (n=2): ");
        printList(result3); // Expected: 2
        System.out.println("---");

        // Example 4: Remove 3rd from end: 1->2->3->4->5, n=3 => 1->2->4->5
        ListNode head4 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        System.out.print("Original List 4: ");
        printList(head4);
        ListNode result4 = sol.removeNthFromEnd(head4, 3);
        System.out.print("Result List 4 (n=3): ");
        printList(result4); // Expected: 1->2->4->5
        System.out.println("---");
    }

    // Helper method to print the linked list (for demonstration)
    public static void printList(ListNode head) {
        if (head == null) {
            System.out.println("[]");
            return;
        }
        StringBuilder sb = new StringBuilder("[");
        ListNode current = head;
        while (current != null) {
            sb.append(current.val);
            if (current.next != null) {
                sb.append(" -> ");
            }
            current = current.next;
        }
        sb.append("]");
        System.out.println(sb.toString());
    }
}

/*
// Problem Description: Remove Nth Node From End of List

// Given the `head` of a linked list, remove the `nth` node from the end of the list and return its head.

// Constraints:
// - The number of nodes in the list is `sz`.
// - `1 <= sz <= 30`
// - `1 <= n <= sz` (This is an important constraint: `n` is always a valid position and within the list bounds).

// Example 1:
// Input: head = [1,2,3,4,5], n = 2
// Output: [1,2,3,5]
// Explanation: The 2nd node from the end is 4. Removing it results in [1,2,3,5].

// Example 2:
// Input: head = [1], n = 1
// Output: []
// Explanation: The 1st node from the end is 1. Removing it results in an empty list.

// Example 3:
// Input: head = [1,2], n = 1
// Output: [1]
// Explanation: The 1st node from the end is 2. Removing it results in [1].

// Problem Explanation:
// The problem asks us to delete a node that is located `n` positions from the end of a singly linked list.
// The key challenge is that we cannot directly traverse a singly linked list backward. If we only had the head,
// and we needed to remove the `nth` from the end, we would typically need to:
// 1. First pass: Find the length of the list.
// 2. Second pass: Traverse `(length - n)` steps from the beginning to find the node *before* the one to be removed.
// This would involve two passes. The goal is often to solve this in a single pass.

// Approach to the Problem (Two-Pointer Approach - One Pass):

// Why this approach?
// The two-pointer approach (specifically, the "fast and slow" pointer method) allows us to solve this problem in a single pass, which is more efficient than two separate passes. It leverages the idea of maintaining a constant gap between two pointers.

// Why not other approaches?
// 1. Two-Pass Approach (Calculate Length then Remove):
//    - First pass: Iterate through the entire list to count `length` (O(L) time).
//    - Second pass: Calculate `nodeToDeleteIndex = length - n`. Iterate `nodeToDeleteIndex - 1` times from the head to reach the node *before* the one to be deleted (O(L) time). Perform deletion (O(1)).
//    - While correct, this approach requires traversing the list twice, making it less optimal than a single-pass solution.

// 2. Recursive Approach:
//    - One could solve this recursively by reaching the end of the list, then returning up the call stack, incrementing a counter. When the counter equals `n`, the current node is the one to be removed. This requires handling the `next` pointer carefully in the recursive return.
//    - This method also effectively involves traversing the list twice (once down, once up the recursion stack) and might incur overhead from function calls. It's less intuitive for beginners for this specific problem compared to the two-pointer approach.

// Detailed Steps for the Two-Pointer Approach:

// The core idea is to use two pointers, `slow` and `fast`. We move `fast` ahead by `n` steps. Then, we move both `slow` and `fast` simultaneously, one step at a time, until `fast` reaches the end of the list. When `fast` reaches the end, `slow` will naturally be positioned just before the `nth` node from the end.

// Step 0: Handle Edge Cases with a Dummy Node.
// - Create a `dummy` node and set `dummy.next = head`. This dummy node simplifies edge cases, particularly when the node to be removed is the original `head` of the list (i.e., `n` is equal to the total number of nodes). By starting `slow` at `dummy`, `slow` will always be *before* the node to be removed.
// - Initialize `slow = dummy` and `fast = dummy`.

// Step 1: Move `fast` pointer `n` steps ahead.
// - Iterate `i` from `0` to `n-1`. In each iteration, move `fast = fast.next`.
// - After this loop, `fast` will be `n` nodes ahead of `slow`.
//   Example: If list is `1->2->3->4->5` and `n=2`.
//   Initial: `dummy -> 1 -> 2 -> 3 -> 4 -> 5`
//             `S`      `F`
//   `i=0`:   `S`      `1` `F` (after fast = fast.next)
//   `i=1`:   `S`      `1` `2` `F` (after fast = fast.next)
//   Now, `fast` is at node `2`, and `slow` is at `dummy`. The gap is 2 nodes.

// Step 2: Move both `slow` and `fast` pointers simultaneously.
// - Loop while `fast.next != null`.
// - In each iteration, move both `slow = slow.next` and `fast = fast.next`.
// - This maintains the `n`-node gap between `slow` and `fast`.
// - When `fast.next` becomes `null`, `fast` is pointing to the very last node of the original list. At this exact moment, `slow` will be pointing to the node that is `n+1` positions from the end, or equivalently, the node *before* the `nth` node from the end.

//   Continuing the example (List: `dummy->1->2->3->4->5`, `n=2`):
//   After Step 1: `dummy -> 1 -> 2 -> 3 -> 4 -> 5`
//                   `S`           `F`
//   `fast.next` (3) is not null.
//     `S` moves to `1`, `F` moves to `3`.
//     `dummy -> 1 -> 2 -> 3 -> 4 -> 5`
//              `S`      `F`
//   `fast.next` (4) is not null.
//     `S` moves to `2`, `F` moves to `4`.
//     `dummy -> 1 -> 2 -> 3 -> 4 -> 5`
//                   `S`      `F`
//   `fast.next` (5) is not null.
//     `S` moves to `3`, `F` moves to `5`.
//     `dummy -> 1 -> 2 -> 3 -> 4 -> 5`
//                        `S`      `F`
//   Now `fast.next` is `null` (5's next). The loop terminates.
//   `slow` is at node `3`. Node `4` is the 2nd from the end. `slow` is correctly pointing to the node *before* `4`.

// Step 3: Remove the `nth` node from the end.
// - The node to be removed is `slow.next`.
// - To remove it, update `slow.next = slow.next.next`. This bypasses the node to be removed.
//   Continuing the example: `slow.next` is node `4`. `slow.next.next` is node `5`.
//   `slow.next` (which was pointing to `4`) now points to `5`.
//   The list effectively becomes: `1 -> 2 -> 3 -> 5`. Node `4` is bypassed/removed.

// Step 4: Return the head of the modified list.
// - Since we used a `dummy` node, the true head of the list is `dummy.next`.
// - Return `dummy.next`.

// Time Complexity Analysis:

// The algorithm performs a single pass through the linked list.
// 1. Initializing dummy node and pointers: O(1).
// 2. Moving `fast` `n` steps ahead: This loop runs `n` times. Each step is O(1). So, O(n).
// 3. Moving both `slow` and `fast` until `fast.next` is null: In the worst case, `fast` traverses the rest of the list (approximately `L - n` steps, where `L` is the total length of the list). Each step is O(1). So, O(L - n).
// 4. Modifying pointers to remove the node: O(1).

// The total time complexity is O(1) + O(n) + O(L - n) + O(1) = O(L).
// Since `L` is the number of nodes in the list, the time complexity is **O(L)**, which is linear with respect to the number of nodes.

// Space Complexity Analysis:

// The algorithm uses a constant amount of extra space:
// - A `dummy` node (1 node).
// - Two pointers: `slow` and `fast`.
// These variables do not depend on the number of nodes in the input linked list.
// Therefore, the space complexity is **O(1)**.
*/