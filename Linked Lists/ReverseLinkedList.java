// LeetCode Question Link: https://leetcode.com/problems/reverse-linked-list/

// This file contains the solution to LeetCode problem "Reverse Linked List".
// The problem asks us to reverse a singly linked list.

// /**
//  * Definition for singly-linked list.
//  * public class ListNode {
//  * int val;
//  * ListNode next;
//  * ListNode() {}
//  * ListNode(int val) { this.val = val; }
//  * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
//  * }
//  */

public class ReverseLinkedList {

    // Main method for testing the solution
    public static void main(String[] args) {
        // Example 1: Reversing a simple linked list
        // Input: 1 -> 2 -> 3 -> 4 -> 5
        // Expected Output: 5 -> 4 -> 3 -> 2 -> 1
        ListNode head1 = new ListNode(1);
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(3);
        head1.next.next.next = new ListNode(4);
        head1.next.next.next.next = new ListNode(5);

        System.out.println("Original List 1:");
        printList(head1);
        Solution sol = new Solution();
        ListNode reversedHead1 = sol.reverseList(head1);
        System.out.println("Reversed List 1:");
        printList(reversedHead1);
        System.out.println("--------------------");

        // Example 2: Reversing a linked list with two nodes
        // Input: 1 -> 2
        // Expected Output: 2 -> 1
        ListNode head2 = new ListNode(1);
        head2.next = new ListNode(2);

        System.out.println("Original List 2:");
        printList(head2);
        ListNode reversedHead2 = sol.reverseList(head2);
        System.out.println("Reversed List 2:");
        printList(reversedHead2);
        System.out.println("--------------------");


        // Example 3: Reversing a linked list with a single node
        // Input: 1
        // Expected Output: 1
        ListNode head3 = new ListNode(1);

        System.out.println("Original List 3:");
        printList(head3);
        ListNode reversedHead3 = sol.reverseList(head3);
        System.out.println("Reversed List 3:");
        printList(reversedHead3);
        System.out.println("--------------------");

        // Example 4: Reversing an empty linked list
        // Input: null
        // Expected Output: null
        ListNode head4 = null;

        System.out.println("Original List 4 (empty):");
        printList(head4);
        ListNode reversedHead4 = sol.reverseList(head4);
        System.out.println("Reversed List 4 (empty):");
        printList(reversedHead4);
        System.out.println("--------------------");
    }

    // Helper method to print the linked list
    public static void printList(ListNode head) {
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val + (current.next != null ? " -> " : ""));
            current = current.next;
        }
        System.out.println();
    }
}

// Definition for singly-linked list.
// This class is typically provided by LeetCode.
class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class Solution {
    /**
     * Problem: Reverse Linked List
     *
     * Given the head of a singly linked list, reverse the list, and return the reversed list.
     *
     * Example 1:
     * Input: head = [1,2,3,4,5]
     * Output: [5,4,3,2,1]
     *
     * Example 2:
     * Input: head = [1,2]
     * Output: [2,1]
     *
     * Example 3:
     * Input: head = []
     * Output: []
     *
     * Constraints:
     * The number of nodes in the list is the range [0, 5000].
     * -5000 <= Node.val <= 5000
     */

    /**
     * Approach Explanation: Iterative Method
     *
     * The most intuitive and efficient way to reverse a singly linked list is using an iterative approach.
     * This method involves traversing the list and changing the 'next' pointer of each node to point to its previous node.
     * We need to keep track of three pointers:
     * 1. `prev`: This pointer keeps track of the previously processed node. Initially, it's null because the first node in the original list
     * will become the last node in the reversed list and its `next` should point to null.
     * 2. `curr`: This pointer iterates through the original linked list, pointing to the current node being processed. It starts at `head`.
     * 3. `nextTemp` (or `next` in the code): This temporary pointer is crucial. Before we change `curr.next` to `prev`, we need to store
     * a reference to the next node in the *original* list. If we don't, we'll lose our way in the list and won't be able to proceed.
     *
     * Algorithm Steps:
     * 1. Initialize `prev` to `null`. This will be the new head of the reversed list.
     * 2. Initialize `curr` to `head`. This pointer will traverse the original list.
     * 3. Loop while `curr` is not `null`:
     * a. Store `curr.next` in a temporary variable, say `nextTemp`. This saves the reference to the next node in the original list.
     * b. Change the `next` pointer of `curr` to ``prev`. This is the core reversal step. The current node now points to the previous one.
     * c. Move `prev` forward: `prev = curr`. The `curr` node now becomes the `prev` for the next iteration.
     * d. Move `curr` forward: `curr = nextTemp`. The `curr` pointer moves to the next node in the original list (which we saved in `nextTemp`).
     * 4. After the loop finishes (when `curr` becomes `null`, meaning we've processed all nodes), `prev` will be pointing to the new head of the reversed list.
     * 5. Return `prev`.
     *
     * Why not other approaches (e.g., Recursion without careful thought)?
     * While recursion can also be used, the iterative approach is generally preferred for linked list reversals due to its lower overhead and better stack memory management.
     * A naive recursive approach might involve passing around multiple pointers or building the list from the end, which can be less intuitive or efficient than the iterative method for this specific problem.
     * A well-structured recursive solution would involve reversing the rest of the list and then attaching the current node, but it often requires understanding the base case and the recursive step carefully, which can sometimes be more complex to reason about than the iterative approach.
     */

    public ListNode reverseList(ListNode head) {
        ListNode curr = head; // 'curr' will traverse the original list
        ListNode prev = null; // 'prev' will build the reversed list, starting as null (new tail)

        // Loop until 'curr' reaches the end of the original list
        while (curr != null) {
            // 1. Store the next node in the original list before modifying curr.next
            ListNode nextTemp = curr.next;

            // 2. Reverse the 'next' pointer of the current node to point to the previous node
            curr.next = prev;

            // 3. Move 'prev' pointer one step forward (it now points to the current node)
            prev = curr;

            // 4. Move 'curr' pointer one step forward (to the next node in the original list)
            curr = nextTemp;
        }

        // After the loop, 'prev' will be pointing to the new head of the reversed list
        return prev;
    }

    /**
     * Time Complexity Analysis:
     *
     * The time complexity of this iterative approach is $O(N)$, where $N$ is the number of nodes in the linked list.
     *
     * This is because we iterate through the linked list exactly once. In each iteration of the `while` loop,
     * we perform a constant number of operations (pointer assignments and comparisons).
     *
     * - Initializing `curr` and `prev`: $O(1)$
     * - The `while` loop runs $N$ times (once for each node).
     * - Inside the loop, operations like `ListNode nextTemp = curr.next;`, `curr.next = prev;`,
     * `prev = curr;`, and `curr = nextTemp;` are all $O(1)$ operations.
     *
     * Therefore, the total time taken is directly proportional to the number of nodes, resulting in $O(N)$ time complexity.
     */

    /**
     * Space Complexity Analysis:
     *
     * The space complexity of this iterative approach is $O(1)$.
     *
     * This is because we only use a few extra pointers (`curr`, `prev`, `nextTemp`) to perform the reversal.
     * The number of these pointers does not depend on the number of nodes $N$ in the linked list.
     * They are constant, regardless of the input size. We are not using any auxiliary data structures
     * like arrays, stacks, or queues that would grow with the input size.
     *
     * - `curr`: 1 pointer
     * - `prev`: 1 pointer
     * - `nextTemp`: 1 pointer
     *
     * These three pointers occupy a constant amount of memory. Thus, the space complexity is $O(1)$.
     */
}