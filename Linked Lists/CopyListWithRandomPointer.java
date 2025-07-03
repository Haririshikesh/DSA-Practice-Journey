// Main class to demonstrate the solution
public class CopyRandomList {

    // Definition for a Node.
    // This part would typically be provided by LeetCode or in a separate file.
    // For a self-contained Java file, we include it here.
    static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    // LeetCode Solution Class
    static class Solution {
        public Node copyRandomList(Node head) {
            // If the head is null, return null as there's nothing to copy.
            if (head == null) {
                return null;
            }

            // A HashMap to store the mapping from original nodes to their corresponding
            // copied nodes. This is crucial for handling random pointers.
            // Key: Original Node, Value: Copied Node
            Map<Node, Node> originalToCopyMap = new HashMap<>();

            // ******************* First Pass: Create new nodes and populate the map *******************
            // Iterate through the original list.
            // In this pass, we only create the new nodes and establish the mapping
            // between original and new nodes. We don't link them yet.
            Node current = head;
            while (current != null) {
                // Create a new node with the same value as the original node.
                originalToCopyMap.put(current, new Node(current.val));
                // Move to the next node in the original list.
                current = current.next;
            }

            // ******************* Second Pass: Establish next and random pointers *******************
            // Reset 'current' back to the head of the original list.
            current = head;
            while (current != null) {
                // Get the corresponding copied node for the current original node from the map.
                Node copiedNode = originalToCopyMap.get(current);

                // Set the 'next' pointer of the copied node.
                // The 'next' pointer of the copied node should point to the copied version
                // of the original current.next node. We retrieve this from our map.
                // If current.next is null, originalToCopyMap.get(null) will return null,
                // which is correct for the end of the list.
                copiedNode.next = originalToCopyMap.get(current.next);

                // Set the 'random' pointer of the copied node.
                // The 'random' pointer of the copied node should point to the copied version
                // of the original current.random node. We retrieve this from our map.
                // If current.random is null, originalToCopyMap.get(null) will return null,
                // which is correct.
                copiedNode.random = originalToCopyMap.get(current.random);

                // Move to the next node in the original list.
                current = current.next;
            }

            // Return the head of the deep-copied list.
            // This is the copied node corresponding to the original head node.
            return originalToCopyMap.get(head);
        }
    }

    // Main method for testing and demonstration
    public static void main(String[] args) {
        // Example 1: Basic list with random pointers
        // Original List: 7 -> 13 -> 11 -> 10 -> 1
        // Random Pointers:
        // 7 -> null
        // 13 -> 7
        // 11 -> 1
        // 10 -> 11
        // 1 -> 7

        Node node7 = new Node(7);
        Node node13 = new Node(13);
        Node node11 = new Node(11);
        Node node10 = new Node(10);
        Node node1 = new Node(1);

        node7.next = node13;
        node13.next = node11;
        node11.next = node10;
        node10.next = node1;

        node7.random = null;
        node13.random = node7;
        node11.random = node1;
        node10.random = node11;
        node1.random = node7;

        System.out.println("Original List Example 1:");
        printList(node7);

        Solution sol = new Solution();
        Node copiedHead1 = sol.copyRandomList(node7);
        System.out.println("Copied List Example 1:");
        printList(copiedHead1);
        System.out.println("---");

        // Example 2: List with only one node
        Node node100 = new Node(100);
        node100.random = node100; // Random pointer to itself

        System.out.println("Original List Example 2:");
        printList(node100);
        Node copiedHead2 = sol.copyRandomList(node100);
        System.out.println("Copied List Example 2:");
        printList(copiedHead2);
        System.out.println("---");

        // Example 3: Empty list
        System.out.println("Original List Example 3 (empty):");
        printList(null);
        Node copiedHead3 = sol.copyRandomList(null);
        System.out.println("Copied List Example 3 (empty):");
        printList(copiedHead3);
        System.out.println("---");
    }

    // Helper method to print the linked list (for demonstration)
    public static void printList(Node head) {
        if (head == null) {
            System.out.println("List is empty.");
            return;
        }
        Node current = head;
        while (current != null) {
            System.out.print("Node(" + current.val + ")");
            if (current.random != null) {
                System.out.print(" random -> " + current.random.val);
            } else {
                System.out.print(" random -> null");
            }
            if (current.next != null) {
                System.out.print(" -> ");
            }
            current = current.next;
        }
        System.out.println();
    }
}

/*
// Problem Description: Copy List with Random Pointer

// You are given a special linked list, where each node has a `next` pointer pointing to the next node in the list,
// and a `random` pointer that can point to any node in the list or to `null`.

// Your task is to create a deep copy of the list. A deep copy means creating new nodes for all nodes in the
// original list and ensuring that the `next` and `random` pointers of the new nodes point to the
// *corresponding new nodes* in the copied list, not the original ones.

// The linked list is represented in the input as a series of `Node` objects. Each `Node` has:
// - `int val`: The value of the node.
// - `Node next`: A pointer to the next node.
// - `Node random`: A pointer to a random node in the list or `null`.

// Constraints:
// - The number of nodes in the list is between 0 and 1000.
// - -10^4 <= Node.val <= 10^4
// - The `random` pointer of a node could point to `null` or to any node in the original list.

// Example 1:
// Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
// Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]
// Explanation: The random pointer indices are 0-based.
// For node with value 13, random points to node at index 0 (value 7).
// For node with value 11, random points to node at index 4 (value 1).
// And so on. The output structure should exactly mirror this.

// Problem Explanation:
// The core challenge here is that the `random` pointers can point to any node, including nodes that appear later in the list, or even to `null`. When we create a copy, we can easily create new nodes for each original node and link their `next` pointers. However, for `random` pointers, simply copying `originalNode.random` will make the new node's `random` pointer point to an *original* node, which is not a deep copy. We need its `random` pointer to point to the *copied version* of `originalNode.random`.

// Approach to the Problem (Using a HashMap):

// Why this approach?
// The main difficulty is linking the `random` pointers. When we iterate through the original list and create new nodes, we might encounter a `random` pointer that points to a node not yet created in our copied list. This is where a mapping is essential. A HashMap allows us to store a relationship between an original node and its newly created copy.

// Why not other approaches?
// 1. Simply iterating and creating new nodes without a map: If we just iterate and create `new Node(current.val)` and then try to set `copiedNode.next = new Node(current.next.val)` and `copiedNode.random = new Node(current.random.val)`, this would lead to:
//    - Redundant node creation (creating new nodes for `current.next` and `current.random` multiple times).
//    - Incorrect linking for `random` pointers if `random` points to a node already created earlier, we would create a duplicate instead of pointing to the existing copied node.
//    - This doesn't solve the core problem of connecting random pointers to their *corresponding copied nodes*.

// 2. Interleaving nodes (without a map): This is an advanced approach that modifies the original list. It involves:
//    a. Creating a copy of each node and inserting it right after the original node (e.g., A -> A' -> B -> B' -> C -> C').
//    b. Setting the `random` pointers of the new nodes: `A'.random = A.random.next` (because A.random.next would be A.random's copy).
//    c. Separating the original and copied lists.
//    While this approach achieves O(1) space complexity (excluding recursion stack for deep calls), it modifies the original list temporarily and can be more complex to implement correctly. The HashMap approach is generally more intuitive and easier to reason about for this problem.

// Detailed Steps for the HashMap Approach:

// Step 1: Create new nodes and establish a mapping.
// - Initialize an empty `HashMap<Node, Node>` called `originalToCopyMap`. This map will store the relationship between an original node and its newly created, corresponding copied node.
// - Iterate through the original linked list using a `current` pointer starting from `head`.
// - For each `current` node in the original list:
//   - Create a new `Node` with the same `val` as the `current` node: `new Node(current.val)`.
//   - Store this mapping in our HashMap: `originalToCopyMap.put(current, new Node(current.val))`.
// - After this first pass, `originalToCopyMap` will contain an entry for every original node, mapping it to its freshly created copy. At this point, the `next` and `random` pointers of these new nodes are all `null`.

// Step 2: Establish `next` and `random` pointers for the copied nodes.
// - Reset the `current` pointer back to the `head` of the original list.
// - Iterate through the original linked list again.
// - For each `current` node in the original list:
//   - Retrieve the corresponding copied node from our map: `Node copiedNode = originalToCopyMap.get(current);`.
//   - Set the `next` pointer of `copiedNode`: `copiedNode.next = originalToCopyMap.get(current.next);`.
//     - If `current.next` is not `null`, `originalToCopyMap.get(current.next)` will return the *copied version* of `current.next`.
//     - If `current.next` is `null` (end of the list), `originalToCopyMap.get(null)` will correctly return `null`.
//   - Set the `random` pointer of `copiedNode`: `copiedNode.random = originalToCopyMap.get(current.random);`.
//     - Similarly, if `current.random` is not `null`, `originalToCopyMap.get(current.random)` will return the *copied version* of `current.random`.
//     - If `current.random` is `null`, `originalToCopyMap.get(null)` will correctly return `null`.
// - Continue this process until `current` becomes `null`.

// Step 3: Return the head of the copied list.
// - The head of the new, deep-copied list will be the copied node corresponding to the original `head`.
// - Retrieve this from our map: `return originalToCopyMap.get(head);`.

// Time Complexity Analysis:

// The algorithm performs two distinct passes over the linked list.
// 1. First Pass (Populating the HashMap): We iterate through the original linked list once. For each of the `N` nodes in the list, we perform a constant number of operations: accessing the node, creating a new node, and putting an entry into the HashMap. HashMap `put` operation (on average) takes O(1) time.
//    Therefore, the first pass takes O(N) time.

// 2. Second Pass (Setting Pointers): We iterate through the original linked list a second time. For each of the `N` nodes, we perform a constant number of operations: accessing the node, retrieving its corresponding copied node from the HashMap, and setting its `next` and `random` pointers. HashMap `get` operation (on average) takes O(1) time.
//    Therefore, the second pass also takes O(N) time.

// Combining both passes, the total time complexity is O(N) + O(N) = O(N), where N is the number of nodes in the linked list.

// Space Complexity Analysis:

// The algorithm uses a `HashMap` to store the mapping between original nodes and their copied counterparts.
// In the worst case, the HashMap will store an entry for every node in the original linked list. If there are `N` nodes in the list, the HashMap will store `N` key-value pairs. Each key is an `original Node` object, and each value is a `copied Node` object.
// The space required by the HashMap is directly proportional to the number of nodes in the list.
// Therefore, the space complexity is O(N), where N is the number of nodes in the linked list.

// This approach is efficient in terms of time complexity (linear) and uses additional space proportional to the number of nodes.
*/