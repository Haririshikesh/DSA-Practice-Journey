// LeetCode Question Link: https://leetcode.com/problems/add-two-numbers/

// This file contains the solution to LeetCode problem "Add Two Numbers".
// The problem asks us to add two numbers represented by linked lists.

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

public class AddTwoNumbers {

    // Main method for testing the solution
    public static void main(String[] args) {
        Solution sol = new Solution();

        // Example 1: Standard case
        // l1 = [2,4,3] (represents 342)
        // l2 = [5,6,4] (represents 465)
        // Expected Output: [7,0,8] (represents 807)
        ListNode l1_1 = new ListNode(2, new ListNode(4, new ListNode(3)));
        ListNode l2_1 = new ListNode(5, new ListNode(6, new ListNode(4)));
        System.out.println("Input List 1 (342): ");
        printList(l1_1);
        System.out.println("Input List 2 (465): ");
        printList(l2_1);
        ListNode sumList1 = sol.addTwoNumbers(l1_1, l2_1);
        System.out.println("Sum List (807): ");
        printList(sumList1);
        System.out.println("--------------------");

        // Example 2: One list is larger than the other
        // l1 = [9,9,9,9,9,9,9] (represents 9,999,999)
        // l2 = [9,9,9,9] (represents 9,999)
        // Expected Output: [8,9,9,9,0,0,0,1] (represents 10,009,998)
        ListNode l1_2 = new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9)))))));
        ListNode l2_2 = new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9))));
        System.out.println("Input List 1 (9999999): ");
        printList(l1_2);
        System.out.println("Input List 2 (9999): ");
        printList(l2_2);
        ListNode sumList2 = sol.addTwoNumbers(l1_2, l2_2);
        System.out.println("Sum List (10009998): ");
        printList(sumList2);
        System.out.println("--------------------");

        // Example 3: Lists with zero and carry handling
        // l1 = [0]
        // l2 = [0]
        // Expected Output: [0]
        ListNode l1_3 = new ListNode(0);
        ListNode l2_3 = new ListNode(0);
        System.out.println("Input List 1 (0): ");
        printList(l1_3);
        System.out.println("Input List 2 (0): ");
        printList(l2_3);
        ListNode sumList3 = sol.addTwoNumbers(l1_3, l2_3);
        System.out.println("Sum List (0): ");
        printList(sumList3);
        System.out.println("--------------------");

        // Example 4: Carry out to a new digit
        // l1 = [9]
        // l2 = [1]
        // Expected Output: [0,1] (represents 10)
        ListNode l1_4 = new ListNode(9);
        ListNode l2_4 = new ListNode(1);
        System.out.println("Input List 1 (9): ");
        printList(l1_4);
        System.out.println("Input List 2 (1): ");
        printList(l2_4);
        ListNode sumList4 = sol.addTwoNumbers(l1_4, l2_4);
        System.out.println("Sum List (10): ");
        printList(sumList4);
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
     * Problem: Add Two Numbers
     *
     * You are given two non-empty linked lists representing two non-negative integers.
     * The digits are stored in reverse order, and each of their nodes contains a single digit.
     * Add the two numbers and return the sum as a linked list.
     * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
     *
     * Example 1:
     * Input: l1 = [2,4,3], l2 = [5,6,4]
     * Output: [7,0,8]
     * Explanation: 342 + 465 = 807.
     *
     * Example 2:
     * Input: l1 = [0], l2 = [0]
     * Output: [0]
     *
     * Example 3:
     * Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
     * Output: [8,9,9,9,0,0,0,1]
     *
     * Constraints:
     * The number of nodes in each linked list is in the range [1, 100].
     * 0 <= Node.val <= 9
     * It is guaranteed that the list represents a number that does not have leading zeros, except as a number 0 itself.
     */

    /**
     * Approach Explanation: Iterative Summation with Carry
     *
     * The problem asks us to add two numbers represented by linked lists, where digits are stored
     * in reverse order. This setup is convenient because it means the head of the list represents
     * the least significant digit (units place), which is exactly how we perform addition manually
     * (from right to left, i.e., from least significant to most significant digit).
     *
     * We can simulate the process of manual addition, carrying over any tens digit to the next position.
     *
     * Algorithm Steps:
     * 1. Initialize a `dummy` node (e.g., `new ListNode(0)`) to simplify the creation of the result list.
     * Also, initialize a `current` pointer to `dummy`. This `current` pointer will build the new list.
     * 2. Initialize a `carry` variable to `0`. This will store any carry-over from the sum of digits.
     * 3. Start a `while` loop that continues as long as `l1` is not `null` OR `l2` is not `null` OR `carry` is not `0`.
     * The `carry != 0` condition is important to handle cases where there's a final carry-over
     * after all digits have been processed (e.g., 9 + 1 = 10, resulting in [0, 1]).
     * 4. Inside the loop, for each position:
     * a. Get the value of the current digit from `l1`. If `l1` is `null` (meaning we've exhausted `l1`), consider its value as `0`.
     * `int x = (l1 != null) ? l1.val : 0;`
     * b. Get the value of the current digit from `l2`. If `l2` is `null`, consider its value as `0`.
     * `int y = (l2 != null) ? l2.val : 0;`
     * c. Calculate the `total` sum for the current position: `int total = x + y + carry;`
     * d. Update the `carry` for the next iteration: `carry = total / 10;` (e.g., if `total` is 17, `carry` becomes 1).
     * e. Calculate the `digit` to be placed in the current node: `int digit = total % 10;` (e.g., if `total` is 17, `digit` becomes 7).
     * f. Create a new `ListNode` with this `digit` and attach it to `current.next`: `current.next = new ListNode(digit);`
     * g. Move `current` pointer forward: `current = current.next;`
     * h. Advance `l1` and `l2` pointers if they are not `null`:
     * `if (l1 != null) l1 = l1.next;`
     * `if (l2 != null) l2 = l2.next;`
     * 5. After the loop, `dummy.next` will point to the head of the newly formed linked list representing the sum. Return `dummy.next`.
     *
     * Why a Dummy Node?
     * Similar to the "Merge Two Sorted Lists" problem, a dummy node (or placeholder node) simplifies the
     * creation of the new linked list. It allows us to consistently append new nodes to `current.next`
     * without special handling for the very first node. The actual result list starts from `dummy.next`.
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // Dummy node to simplify the creation of the result list
        ListNode result = new ListNode(0);
        // 'curr' pointer will traverse and build the result list
        ListNode curr = result;
        // 'carry' variable to store carry-over from addition
        int rem = 0; // Renamed 'rem' to 'carry' for clarity, as it represents the carry-over

        // Loop continues as long as there are digits in either list or there's a carry-over
        while (l1 != null || l2 != null || rem != 0) {
            // Get value from l1, default to 0 if l1 is null
            int x = (l1 != null) ? l1.val : 0;
            // Get value from l2, default to 0 if l2 is null
            int y = (l2 != null) ? l2.val : 0;

            // Calculate the total sum for the current digit position
            int total = x + y + rem;

            // Calculate the new carry-over for the next position
            rem = total / 10; // Integer division gives the carry (e.g., 17 / 10 = 1)

            // Calculate the digit to be placed in the current node
            int val = total % 10; // Modulo gives the digit (e.g., 17 % 10 = 7)

            // Create a new node with the calculated digit and append it to the result list
            curr.next = new ListNode(val);
            // Move 'curr' pointer to the newly added node
            curr = curr.next;

            // Move l1 and l2 pointers to their next nodes, if they exist
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        // The actual head of the sum list is result.next (skipping the dummy node)
        return result.next;
    }

    /**
     * Time Complexity Analysis:
     *
     * The time complexity of this iterative approach is $O(\max(M, N))$, where $M$ is the number of nodes
     * in `l1` and $N$ is the number of nodes in `l2`.
     *
     * We iterate through both linked lists simultaneously. The loop continues for a number of iterations
     * equal to the length of the longer list, plus potentially one more iteration if there's a final carry-over.
     * In each iteration, we perform a constant number of operations (variable assignments, additions, divisions,
     * modulo operations, and pointer manipulations).
     *
     * For example, if `l1` has 5 nodes and `l2` has 3 nodes, the loop will run approximately 5 times.
     * If `l1` is [9,9] and `l2` is [9,9], the result is [8,9,1], requiring 3 iterations for 2-node lists due to final carry.
     *
     * Therefore, the total time taken is proportional to the maximum length of the two input lists, leading to $O(\max(M, N))$ time complexity.
     */

    /**
     * Space Complexity Analysis:
     *
     * The space complexity of this iterative approach is $O(\max(M, N))$, where $M$ is the number of nodes
     * in `l1` and $N$ is the number of nodes in `l2`.
     *
     * This is because we are creating a new linked list to store the sum. In the worst case, the length of the
     * sum list can be $\max(M, N) + 1$ (e.g., adding two single-digit numbers that result in a two-digit number, like 5+5=10).
     *
     * The number of new `ListNode` objects created is directly proportional to the number of digits in the sum,
     * which is roughly equal to the maximum length of the input lists.
     *
     * Aside from the new list, we use a constant amount of extra space for pointers (`result`, `curr`, `l1`, `l2`)
     * and the `carry` variable. However, the space dominated by the new list nodes makes the overall space complexity $O(\max(M, N))$.
     */
}