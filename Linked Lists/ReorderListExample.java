// LeetCode Problem: Reorder List
// Problem Link: https://leetcode.com/problems/reorder-list/

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
//     public void reorderList(ListNode head) {
//         // Step 1: Find the middle of the linked list
//         // We use two pointers, 'slow' and 'fast'. 'slow' moves one step at a time,
//         // while 'fast' moves two steps at a time. When 'fast' reaches the end,
//         // 'slow' will be at the middle.
//         ListNode slow = head;
//         ListNode fast = head;
//         while( fast!=null && fast.next !=null ){
//             slow = slow.next;
//             fast = fast.next.next;
//         }

//         // Step 2: Reverse the second half of the linked list
//         // 'slow.next' is the head of the second half. We need to reverse this part.
//         // For example, if the list is 1->2->3->4->5->6, 'slow' will be at 3.
//         // 'curr' will start at 4. After reversing, it becomes 6->5->4.
//         ListNode curr = slow.next;
//         slow.next = null; // Disconnect the first half from the second half
//         ListNode prev = null; // 'prev' will be the new head of the reversed second half
//         while(curr!=null){
//             ListNode next = curr.next; // Store the next node before changing 'curr.next'
//             curr.next = prev;         // Reverse the pointer
//             prev = curr;              // Move 'prev' to the current node
//             curr = next;              // Move 'curr' to the next node
//         }

//         // Step 3: Merge the first half and the reversed second half
//         // 'first' points to the head of the original list (1->2->3 for our example).
//         // 'second' points to the head of the reversed second half (6->5->4 for our example).
//         // We interleave the nodes: 1 -> 6 -> 2 -> 5 -> 3 -> 4.
//         ListNode first = head;
//         ListNode second = prev; // 'prev' holds the head of the reversed second half
//         while(second!=null){
//             ListNode fNext = first.next;  // Store next node of first half
//             ListNode sNext = second.next; // Store next node of second half

//             first.next = second;         // Link first half node to second half node
//             second.next = fNext;         // Link second half node back to first half's next node

//             first = fNext;               // Move 'first' pointer
//             second = sNext;              // Move 'second' pointer
//         }
//     }
// }

class Solution {
    public void reorderList(ListNode head) {
        // Step 1: Find the middle of the linked list
        ListNode slow = head;
        ListNode fast = head;
        while( fast!=null && fast.next !=null ){
            slow = slow.next;
            fast = fast.next.next;
        }

        // Step 2: Reverse the second half of the linked list
        ListNode curr = slow.next;
        slow.next = null;
        ListNode prev = null;
        while(curr!=null){
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        // Step 3: Merge the first half and the reversed second half
        ListNode first = head;
        ListNode second = prev;
        while(second!=null){
            ListNode fNext = first.next;
            ListNode sNext = second.next;
            first.next = second;
            second.next = fNext;
            first = fNext;
            second = sNext;
        }
    }
}

// Helper class for ListNode (for local testing/example)
class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }

    // Utility to print the list
    public void printList() {
        ListNode current = this;
        while (current != null) {
            System.out.print(current.val + " ");
            current = current.next;
        }
        System.out.println();
    }
}

// Main class for demonstration
public class ReorderListExample {
    public static void main(String[] args) {
        // Example 1: Even number of nodes
        ListNode head1 = new ListNode(1);
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(3);
        head1.next.next.next = new ListNode(4);

        System.out.println("Original List 1:");
        head1.printList(); // Output: 1 2 3 4

        Solution solution = new Solution();
        solution.reorderList(head1);
        System.out.println("Reordered List 1:");
        head1.printList(); // Output: 1 4 2 3

        System.out.println("--------------------");

        // Example 2: Odd number of nodes
        ListNode head2 = new ListNode(1);
        head2.next = new ListNode(2);
        head2.next.next = new ListNode(3);
        head2.next.next.next = new ListNode(4);
        head2.next.next.next.next = new ListNode(5);

        System.out.println("Original List 2:");
        head2.printList(); // Output: 1 2 3 4 5

        solution.reorderList(head2);
        System.out.println("Reordered List 2:");
        head2.printList(); // Output: 1 5 2 4 3

        System.out.println("--------------------");

        // Example 3: Two nodes
        ListNode head3 = new ListNode(1);
        head3.next = new ListNode(2);

        System.out.println("Original List 3:");
        head3.printList(); // Output: 1 2

        solution.reorderList(head3);
        System.out.println("Reordered List 3:");
        head3.printList(); // Output: 1 2 (no change as per problem definition for lists with <=2 nodes, though the algo handles it gracefully)

        System.out.println("--------------------");

        // Example 4: Single node
        ListNode head4 = new ListNode(1);

        System.out.println("Original List 4:");
        head4.printList(); // Output: 1

        solution.reorderList(head4);
        System.out.println("Reordered List 4:");
        head4.printList(); // Output: 1

        System.out.println("--------------------");

        // Example 5: Null head
        ListNode head5 = null;

        System.out.println("Original List 5:");
        // No output for null list

        solution.reorderList(head5);
        System.out.println("Reordered List 5:");
        // No output for null list
    }
}