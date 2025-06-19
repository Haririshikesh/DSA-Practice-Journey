// LeetCode Problem: Reverse Linked List II
// Problem Link: https://leetcode.com/problems/reverse-linked-list-ii/

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
// class Solution {
//     public ListNode reverseBetween(ListNode head, int m, int n) {
//         // Create a dummy node. This simplifies edge cases, especially when the reversal
//         // starts from the head of the original list (m=1).
//         // The dummy node points to the original head.
//         ListNode dummy = new ListNode(0, head);

//         // Lprev will eventually point to the node *before* the sublist to be reversed.
//         // Start Lprev at dummy.
//         ListNode Lprev = dummy;

//         // Move Lprev to the node just before the 'm-th' node.
//         // If m=1, Lprev will remain the dummy node.
//         // The loop runs m-1 times.
//         for(int i=0; i<m-1; i++) {
//             Lprev = Lprev.next;
//         }

//         // 'curr' will be the head of the sublist to be reversed.
//         // 'prev' will be used to build the reversed sublist.
//         // Initially, 'prev' is null.
//         ListNode curr = Lprev.next; // This is the 'm-th' node
//         ListNode prev = null;       // This will become the new head of the reversed sublist

//         // Traverse and reverse the sublist from 'm' to 'n'.
//         // The loop runs (n - m + 1) times, which is the length of the sublist to be reversed.
//         // Example: if m=2, n=4, then n-m+1 = 3 iterations (nodes 2, 3, 4)
//         for(int i=0; i<n-m+1; i++){
//             ListNode next = curr.next; // Store the next node before modifying curr.next
//             curr.next = prev;         // Reverse the current node's pointer
//             prev = curr;              // Move 'prev' to the current node (which is now reversed)
//             curr = next;              // Move 'curr' to the next node in the original list
//         }
//         // After this loop:
//         // - 'prev' is the new head of the reversed sublist (the original 'n-th' node).
//         // - 'curr' is the node immediately *after* the reversed sublist (the (n+1)-th node).
//         // - 'Lprev' is the node immediately *before* the reversed sublist (the (m-1)-th node).
//         // - 'Lprev.next' currently points to the original 'm-th' node (which is now part of the reversed segment but its 'next' is not correctly linked to the new end of the reversed segment).
//         //   More precisely, the original 'm-th' node is now the TAIL of the reversed segment, and its `next` pointer was set to `prev` (which was null in the first iteration).
//         //   So, `Lprev.next` still points to the node that was *originally* the m-th node.
//         //   Let's introduce a temporary pointer to hold the original start of the segment.

//         // Let's refine the pointers for clarity before re-linking:
//         // original_m_node = Lprev.next; // This is the 'm-th' node *before* reversal
//         // Lprev.next will be the new head of the reversed segment, which is 'prev'.

//         // Connect the end of the original list segment (which is now the head of the reversed segment 'prev')
//         // to the node that comes *after* the reversed segment ('curr').
//         // The node that was *originally* the 'm-th' node (and is now the tail of the reversed segment)
//         // needs to point to 'curr'.
//         // The node at Lprev.next was the original 'm-th' node. After reversal, it's the tail.
//         // So, Lprev.next.next needs to point to 'curr'.
//         // Example: 1 -> 2 -> 3 -> 4 -> 5, m=2, n=4
//         // Lprev = 1
//         // After loop: prev=4, curr=5, Lprev.next = 2 (still)
//         // reversed segment: 4 -> 3 -> 2
//         // Lprev (1) should point to 4.
//         // The original '2' (which is now tail of 4->3->2) should point to 5.
//         // The original '2' is `temp_start_of_reversed_segment`
//         // Store the original start of the segment before its next pointer is changed by `Lprev.next = prev`.
//         ListNode tailOfReversedSegment = Lprev.next; // This is the original m-th node

//         Lprev.next.next = curr; // The original 'm-th' node (now the end of the reversed segment)
//                               // points to the node that was immediately after 'n'.
//                               // This line should be: `tailOfReversedSegment.next = curr;`
//                               // If Lprev.next is directly modified first, we lose the reference.

//         // Connect the node before the reversed sublist to the new head of the reversed sublist.
//         Lprev.next = prev; // 'prev' is the new head of the reversed segment (original 'n-th' node)

//         // Return the head of the modified list (which is dummy.next).
//         return dummy.next;
//     }
// }

class Solution {
    public ListNode reverseBetween(ListNode head, int m, int n) {
        // Handle edge cases: empty list, or m >= n (no reversal needed), or invalid m/n.
        if (head == null || m >= n) {
            return head;
        }

        // Create a dummy node to simplify handling cases where m = 1 (reversal starts from head).
        // The dummy node points to the original head.
        ListNode dummy = new ListNode(0, head);

        // 'nodeBeforeSublist' will point to the node just before the 'm-th' node.
        // Initialize it to the dummy node.
        ListNode nodeBeforeSublist = dummy;

        // Traverse to the (m-1)-th node.
        // After this loop, 'nodeBeforeSublist' is at the node just before the segment to be reversed.
        // E.g., for 1->2->3->4->5, m=2, n=4: nodeBeforeSublist will be at node 1.
        for (int i = 0; i < m - 1; i++) {
            nodeBeforeSublist = nodeBeforeSublist.next;
        }

        // 'sublistTail' will be the actual 'm-th' node (the start of the segment to be reversed).
        // After reversal, this node will become the tail of the reversed segment.
        ListNode sublistTail = nodeBeforeSublist.next;

        // 'current' will iterate through the segment to be reversed.
        // 'previous' will build the reversed segment.
        ListNode current = sublistTail;
        ListNode previous = null; // This will become the new head of the reversed segment

        // Reverse the sublist from 'm' to 'n'.
        // The loop runs (n - m + 1) times.
        // E.g., for m=2, n=4, (4-2+1) = 3 iterations for nodes 2, 3, 4.
        for (int i = 0; i < n - m + 1; i++) {
            ListNode nextNode = current.next; // Store the next node before modifying current.next
            current.next = previous;          // Reverse the current node's pointer
            previous = current;               // Move 'previous' to the current node
            current = nextNode;               // Move 'current' to the next node in the original list
        }
        // After this loop:
        // - 'previous' is the new head of the reversed sublist (which was originally the n-th node).
        // - 'current' is the node immediately *after* the reversed sublist (which was originally the (n+1)-th node).
        // - 'nodeBeforeSublist' is the node immediately *before* the reversed sublist (the (m-1)-th node).
        // - 'sublistTail' is the original 'm-th' node, which is now the tail of the reversed segment.

        // Connect the original 'm-th' node (which is now 'sublistTail' and the end of the reversed segment)
        // to the node that comes *after* the reversed segment ('current').
        sublistTail.next = current;

        // Connect the node before the reversed sublist ('nodeBeforeSublist')
        // to the new head of the reversed sublist ('previous').
        nodeBeforeSublist.next = previous;

        // Return the head of the modified list (which is dummy.next).
        return dummy.next;
    }
}


// Helper class for ListNode (for local testing/example)
class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }

    // Utility to create a list from an array
    public static ListNode fromArray(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        ListNode head = new ListNode(arr[0]);
        ListNode current = head;
        for (int i = 1; i < arr.length; i++) {
            current.next = new ListNode(arr[i]);
            current = current.next;
        }
        return head;
    }

    // Utility to print the list
    public void printList() {
        ListNode current = this;
        while (current != null) {
            System.out.print(current.val + (current.next != null ? " -> " : ""));
            current = current.next;
        }
        System.out.println();
    }
}

// Main class for demonstration
public class ReverseLinkedListIIExample {
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Example 1: Reverse a segment in the middle
        // 1 -> 2 -> 3 -> 4 -> 5, m=2, n=4
        // Expected: 1 -> 4 -> 3 -> 2 -> 5
        ListNode head1 = ListNode.fromArray(new int[]{1, 2, 3, 4, 5});
        System.out.print("Original List 1: ");
        head1.printList();
        ListNode reversedHead1 = solution.reverseBetween(head1, 2, 4);
        System.out.print("Reversed List 1 (m=2, n=4): ");
        reversedHead1.printList(); // Output: 1 -> 4 -> 3 -> 2 -> 5
        System.out.println("--------------------");

        // Example 2: Reverse from the beginning
        // 1 -> 2 -> 3 -> 4 -> 5, m=1, n=3
        // Expected: 3 -> 2 -> 1 -> 4 -> 5
        ListNode head2 = ListNode.fromArray(new int[]{1, 2, 3, 4, 5});
        System.out.print("Original List 2: ");
        head2.printList();
        ListNode reversedHead2 = solution.reverseBetween(head2, 1, 3);
        System.out.print("Reversed List 2 (m=1, n=3): ");
        reversedHead2.printList(); // Output: 3 -> 2 -> 1 -> 4 -> 5
        System.out.println("--------------------");

        // Example 3: Reverse to the end
        // 1 -> 2 -> 3, m=2, n=3
        // Expected: 1 -> 3 -> 2
        ListNode head3 = ListNode.fromArray(new int[]{1, 2, 3});
        System.out.print("Original List 3: ");
        head3.printList();
        ListNode reversedHead3 = solution.reverseBetween(head3, 2, 3);
        System.out.print("Reversed List 3 (m=2, n=3): ");
        reversedHead3.printList(); // Output: 1 -> 3 -> 2
        System.out.println("--------------------");

        // Example 4: Single node list
        // 5, m=1, n=1
        // Expected: 5
        ListNode head4 = ListNode.fromArray(new int[]{5});
        System.out.print("Original List 4: ");
        head4.printList();
        ListNode reversedHead4 = solution.reverseBetween(head4, 1, 1);
        System.out.print("Reversed List 4 (m=1, n=1): ");
        reversedHead4.printList(); // Output: 5
        System.out.println("--------------------");

        // Example 5: Two node list, reverse all
        // 1 -> 2, m=1, n=2
        // Expected: 2 -> 1
        ListNode head5 = ListNode.fromArray(new int[]{1, 2});
        System.out.print("Original List 5: ");
        head5.printList();
        ListNode reversedHead5 = solution.reverseBetween(head5, 1, 2);
        System.out.print("Reversed List 5 (m=1, n=2): ");
        reversedHead5.printList(); // Output: 2 -> 1
        System.out.println("--------------------");

        // Example 6: Empty list
        // null, m=1, n=1
        // Expected: null
        ListNode head6 = null;
        System.out.print("Original List 6: ");
        if (head6 != null) head6.printList(); else System.out.println("null");
        ListNode reversedHead6 = solution.reverseBetween(head6, 1, 1);
        System.out.print("Reversed List 6 (m=1, n=1): ");
        if (reversedHead6 != null) reversedHead6.printList(); else System.out.println("null");
        System.out.println("--------------------");
    }
}