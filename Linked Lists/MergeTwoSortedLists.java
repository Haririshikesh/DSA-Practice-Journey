// LeetCode Question Link: https://leetcode.com/problems/merge-two-sorted-lists/

// This file contains the solution to LeetCode problem "Merge Two Sorted Lists".
// The problem asks us to merge two sorted linked lists into one new sorted linked list.

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

public class MergeTwoSortedLists {

    // Main method for testing the solution
    public static void main(String[] args) {
        Solution sol = new Solution();

        // Example 1: Standard merge
        // list1 = [1,2,4], list2 = [1,3,4]
        // Expected Output: [1,1,2,3,4,4]
        ListNode list1_1 = new ListNode(1, new ListNode(2, new ListNode(4)));
        ListNode list2_1 = new ListNode(1, new ListNode(3, new ListNode(4)));
        System.out.println("Input List 1: ");
        printList(list1_1);
        System.out.println("Input List 2: ");
        printList(list2_1);
        ListNode mergedList1 = sol.mergeTwoLists(list1_1, list2_1);
        System.out.println("Merged List 1: ");
        printList(mergedList1);
        System.out.println("--------------------");

        // Example 2: One list is empty
        // list1 = [], list2 = [0]
        // Expected Output: [0]
        ListNode list1_2 = null;
        ListNode list2_2 = new ListNode(0);
        System.out.println("Input List 1 (empty): ");
        printList(list1_2);
        System.out.println("Input List 2: ");
        printList(list2_2);
        ListNode mergedList2 = sol.mergeTwoLists(list1_2, list2_2);
        System.out.println("Merged List 2: ");
        printList(mergedList2);
        System.out.println("--------------------");

        // Example 3: Both lists are empty
        // list1 = [], list2 = []
        // Expected Output: []
        ListNode list1_3 = null;
        ListNode list2_3 = null;
        System.out.println("Input List 1 (empty): ");
        printList(list1_3);
        System.out.println("Input List 2 (empty): ");
        printList(list2_3);
        ListNode mergedList3 = sol.mergeTwoLists(list1_3, list2_3);
        System.out.println("Merged List 3: ");
        printList(mergedList3);
        System.out.println("--------------------");

        // Example 4: One list fully smaller than the other
        // list1 = [1,2], list2 = [3,4,5]
        // Expected Output: [1,2,3,4,5]
        ListNode list1_4 = new ListNode(1, new ListNode(2));
        ListNode list2_4 = new ListNode(3, new ListNode(4, new ListNode(5)));
        System.out.println("Input List 1: ");
        printList(list1_4);
        System.out.println("Input List 2: ");
        printList(list2_4);
        ListNode mergedList4 = sol.mergeTwoLists(list1_4, list2_4);
        System.out.println("Merged List 4: ");
        printList(mergedList4);
        System.out.println("--------------------");
    }

    // Helper method to print the linked list
    public static void printList(ListNode head) {
        if (head == null) {
            System.out.println("[]");
            return;
        }
        ListNode current = head;
        System.out.print("[");
        while (current != null) {
            System.out.print(current.val + (current.next != null ? "," : ""));
            current = current.next;
        }
        System.out.println("]");
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
     * Problem: Merge Two Sorted Lists
     *
     * You are given the heads of two sorted linked lists list1 and list2.
     * Merge the two lists in a one sorted list. The list should be made by splicing together
     * the nodes of the first two lists.
     * Return the head of the merged linked list.
     *
     * Example 1:
     * Input: list1 = [1,2,4], list2 = [1,3,4]
     * Output: [1,1,2,3,4,4]
     *
     * Example 2:
     * Input: list1 = [], list2 = [0]
     * Output: [0]
     *
     * Example 3:
     * Input: list1 = [], list2 = []
     * Output: []
     *
     * Constraints:
     * The number of nodes in both lists is in the range [0, 50].
     * -100 <= Node.val <= 100
     * Both list1 and list2 are sorted in non-decreasing order.
     */

    /**
     * Approach Explanation: Iterative Approach with a Dummy Node
     *
     * To merge two sorted linked lists into a single sorted linked list, we can use an iterative approach.
     * The core idea is to compare the values of the nodes at the current heads of `list1` and `list2`,
     * append the smaller value node to our new merged list, and then advance the pointer of the list from which
     * the node was taken. We continue this process until one of the lists becomes empty.
     * Finally, if any list still has remaining nodes, we append the rest of that list to our merged list,
     * as it's already sorted.
     *
     * A common and efficient technique for linked list problems that involve building a new list is to use a "dummy node"
     * (also known as a "sentinel node" or "placeholder node").
     *
     * Why a Dummy Node?
     * Without a dummy node, handling the head of the new merged list can be tricky. You'd need a special
     * check for the very first node to initialize the head, and then a different logic for subsequent nodes.
     * A dummy node simplifies this by providing a fixed starting point for our merged list. We can always
     * attach the first element to `dummy.next`, avoiding special case handling for the head.
     *
     * Algorithm Steps:
     * 1. Create a `dummy` node (e.g., `new ListNode(0)`). This node doesn't store any actual data;
     * it simply serves as a placeholder for the beginning of our merged list.
     * 2. Create a `current` pointer and initialize it to `dummy`. This `current` pointer will be
     * used to traverse and build our new merged list, always pointing to the last node added.
     * 3. Start a `while` loop that continues as long as both `list1` and `list2` are not `null`.
     * Inside the loop:
     * a. Compare the values of `list1.val` and `list2.val`.
     * b. If `list1.val` is less than or equal to `list2.val`:
     * i. Append `list1` to the `next` of `current`: `current.next = list1`.
     * ii. Move `list1` forward: `list1 = list1.next`.
     * c. Else (if `list2.val` is smaller):
     * i. Append `list2` to the `next` of `current`: `current.next = list2`.
     * ii. Move `list2` forward: `list2 = list2.next`.
     * d. In either case, after appending a node, move `current` forward to the newly added node:
     * `current = current.next`.
     * 4. After the `while` loop finishes, one of the lists (or both) might be `null`. If `list1` is not `null`,
     * it means there are remaining nodes in `list1` that are all greater than or equal to the last node
     * processed from `list2`. Similarly for `list2`. Append the remaining part of the non-null list
     * to the end of our merged list:
     * a. `if (list1 != null) current.next = list1;`
     * b. `if (list2 != null) current.next = list2;`
     * 5. Finally, return `dummy.next`. This is the actual head of our merged sorted list, skipping the dummy node.
     *
     * Why not simply create new ListNodes in the loop (as in the provided user code)?
     * The provided user code creates new `ListNode` objects (`new ListNode(x)` or `new ListNode(y)`)
     * and copies the values. While this works, it's generally less efficient and less idiomatic
     * for linked list merging. The standard approach "splices" the existing nodes, meaning
     * it re-points the `next` pointers of the existing nodes from the original lists
     * directly into the new merged list. This avoids the overhead of creating new objects
     * and copying values, making it more efficient in terms of both time (slightly, due to object creation)
     * and memory (avoiding redundant node objects).
     *
     * The provided code's logic is conceptually similar to merging, but it rebuilds the list by creating new nodes
     * instead of reusing existing ones. The standard "splicing" approach is shown below for comparison within
     * the Solution class comments, illustrating the preferred method for such problems.
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // Create a dummy node. This simplifies handling the head of the merged list.
        ListNode dummyHead = new ListNode(0);
        // 'current' pointer will traverse and build the merged list.
        ListNode current = dummyHead;

        // Iterate while both lists have nodes
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                // If list1's current node is smaller or equal, append it
                current.next = list1;
                // Move list1's pointer to its next node
                list1 = list1.next;
            } else {
                // If list2's current node is smaller, append it
                current.next = list2;
                // Move list2's pointer to its next node
                list2 = list2.next;
            }
            // Move 'current' pointer forward in the merged list
            current = current.next;
        }

        // After the loop, one of the lists might still have remaining elements.
        // Since both input lists are sorted, we can simply append the rest of the non-null list.
        if (list1 != null) {
            current.next = list1;
        } else if (list2 != null) {
            current.next = list2;
        }

        // The actual head of the merged list is dummyHead.next
        return dummyHead.next;
    }

    /**
     * Time Complexity Analysis:
     *
     * The time complexity of this iterative approach is $O(M + N)$, where $M$ is the number of nodes
     * in `list1` and $N$ is the number of nodes in `list2`.
     *
     * This is because in the worst case, we iterate through all nodes of both lists.
     * Each node from `list1` or `list2` is visited and processed exactly once.
     * In each step of the `while` loop, we perform constant time operations (comparison, pointer assignments).
     * The loop continues until both `list1` and `list2` become `null`.
     * The number of operations is directly proportional to the total number of nodes in the merged list, which is $M + N$.
     *
     * Therefore, the total time complexity is $O(M + N)$.
     */

    /**
     * Space Complexity Analysis:
     *
     * The space complexity of this iterative approach is $O(1)$.
     *
     * This is because we are only using a few extra pointers (`dummyHead`, `current`, `list1`, `list2`)
     * to manage the merging process. The number of these pointers is constant and does not depend on
     * the size of the input lists ($M$ or $N$). We are directly re-pointing the `next` pointers
     * of the existing nodes from the input lists to form the new merged list, rather than creating
     * a significant number of new nodes or using auxiliary data structures that grow with the input size.
     *
     * The space used by these pointers is constant, leading to $O(1)$ space complexity.
     */
}