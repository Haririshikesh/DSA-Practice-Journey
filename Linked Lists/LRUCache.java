// LeetCode Question Link: https://leetcode.com/problems/lru-cache/

// This file contains the solution for the LRU Cache problem on LeetCode.
// The problem asks us to implement a Least Recently Used (LRU) cache.
// An LRU cache is a cache replacement policy where, when the cache is full and
// a new entry needs to be added, the least recently used item is discarded to
// make space for the new item.

// The core idea behind an efficient LRU cache implementation is to use a combination
// of a HashMap and a Doubly Linked List (DLL).

// Why HashMap?
// A HashMap is crucial for O(1) average time complexity for `get` and `put` operations.
// It allows us to quickly check if an element exists in the cache and retrieve its
// corresponding node in the Doubly Linked List. Without a HashMap, searching for an
// element would require iterating through the linked list, leading to O(N) complexity,
// where N is the cache capacity. The HashMap stores the key-value pairs where the
// value is a reference to the Node in the Doubly Linked List, allowing direct access.

// Why Doubly Linked List (DLL)?
// A Doubly Linked List is essential for maintaining the order of usage and enabling
// O(1) time complexity for moving nodes around.
// 1. Maintaining Usage Order: The DLL is ordered by recency of use. The most recently
//    used items are at the front (closer to the `head`), and the least recently
//    used items are at the back (closer to the `tail`).
// 2. O(1) Deletion and Insertion: Because it's a Doubly Linked List, we can remove
//    any node in O(1) time if we have a reference to it (which we get from the HashMap).
//    We can also add a new node to the front (most recently used position) in O(1) time.
//    If it were a Singly Linked List, removing a node would require traversing from the
//    head to find the previous node, leading to O(N) complexity for deletion.
//    The DLL allows us to efficiently re-arrange the nodes based on their usage without
//    expensive traversals.

// Problem Explanation:
// Implement the LRUCache class:
// - `LRUCache(int capacity)`: Initializes the LRU cache with the given positive capacity.
// - `int get(int key)`: Get the value of the `key` if the key exists in the cache,
//   otherwise return -1. When accessed, this item becomes the most recently used.
// - `void put(int key, int value)`: Update the value of the `key` if the `key` already
//   exists. Otherwise, add the `key-value` pair to the cache. If the number of keys
//   exceeds the `capacity` from this operation, evict the least recently used key.
//   Both `get` and `put` operations must run in O(1) average time complexity.

// Approach:
// We use a `HashMap<Integer, Node>` to store `(key, Node)` pairs. The `Node`
// contains the `key` and `value` and pointers to `prev` and `next` nodes in the
// Doubly Linked List.
// We maintain two dummy nodes, `head` and `tail`, to simplify operations.
// `head` points to the most recently used node.
// `tail` points to the least recently used node.
//
// `get(int key)`:
// 1. Check if the `key` exists in the HashMap. If not, return -1.
// 2. If it exists, retrieve the `Node` from the HashMap.
// 3. Remove this `Node` from its current position in the DLL.
// 4. Add this `Node` to the front of the DLL (after the `head` dummy node),
//    marking it as most recently used.
// 5. Update the HashMap with the (potentially re-added) `Node` (though the
//    reference hasn't changed, it's good practice to reflect the state).
// 6. Return the `value` of the node.
//
// `put(int key, int value)`:
// 1. If the `key` already exists in the HashMap:
//    a. Remove the existing `Node` from the DLL.
//    b. Remove the `key` from the HashMap.
// 2. If the cache is full (i.e., `mp.size() == capacity`):
//    a. Remove the least recently used node. This node is `tail.prev`.
//    b. Remove its `key` from the HashMap.
//    c. Remove the node from the DLL.
// 3. Create a new `Node` with the given `key` and `value`.
// 4. Add this new `Node` to the front of the DLL (after the `head` dummy node),
//    making it the most recently used.
// 5. Add the new `(key, Node)` pair to the HashMap.

// Why not other approaches?
// - Using only a HashMap: A HashMap alone cannot maintain the order of usage
//   required for LRU eviction. It provides O(1) access but no information about
//   recency.
// - Using only a Linked List (or ArrayList): A Linked List or ArrayList would
//   allow maintaining order, but `get` operations would require iterating through
//   the list to find an element, resulting in O(N) time complexity. Deleting an
//   arbitrary element would also be O(N) (unless it's a doubly linked list and
//   you have a direct reference).
// - Using a TreeMap: A TreeMap keeps keys sorted, but not by recency of use.
//   It also doesn't offer O(1) operations for arbitrary element removal or
//   reordering based on access patterns.

// The combination of HashMap and DLL provides the optimal time complexities
// for all required operations.

import java.util.HashMap;


public class LRUCache {

    // Inner class to represent a node in the Doubly Linked List.
    // Each node stores the key, value, and pointers to the previous and next nodes.
    class Node {
        int key;
        int val;
        Node prev;
        Node next;

        // Constructor for the Node.
        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    // HashMap to store key-Node mappings for O(1) lookup.
    // The key of the map is the cache key, and the value is the Node object in the DLL.
    HashMap<Integer, Node> mp;
    // Dummy head node of the Doubly Linked List. Points to the most recently used node.
    Node head;
    // Dummy tail node of the Doubly Linked List. Points to the least recently used node.
    Node tail;
    // Maximum capacity of the cache.
    int capacity;

    // Constructor for the LRUCache.
    public LRUCache(int capacity) {
        this.capacity = capacity;
        // Initialize the HashMap.
        mp = new HashMap<>();
        // Initialize dummy head and tail nodes.
        // These nodes help in simplifying edge cases (e.g., empty list, adding/removing at ends).
        head = new Node(-1, -1); // Dummy node, key and value don't matter.
        tail = new Node(-1, -1); // Dummy node, key and value don't matter.
        // Link head and tail initially to form an empty list.
        head.next = tail;
        tail.prev = head;
    }

    // Helper method to remove a node from the Doubly Linked List.
    // This operation is O(1) because we have direct pointers to prev and next.
    public void removeNode(Node node) {
        Node prev = node.prev;
        Node next = node.next;
        prev.next = next;
        next.prev = prev;
    }

    // Helper method to add a node to the front of the Doubly Linked List (after the head).
    // This signifies that the node is the most recently used. O(1) operation.
    public void addNode(Node node) {
        Node temp = head.next; // Store the current first node.

        // Link the new node between head and temp.
        head.next = node;
        node.prev = head;
        node.next = temp;
        temp.prev = node;
    }

    // Get method to retrieve the value associated with a key.
    // Time Complexity: O(1) average.
    // Space Complexity: O(1) for operations, but O(N) for storing the map and DLL.
    public int get(int key) {
        // If the key exists in the HashMap.
        if (mp.containsKey(key)) {
            // Get the node associated with the key.
            Node node = mp.get(key);
            // Store the value before moving the node.
            int ans = node.val;

            // Remove the node from its current position in the DLL.
            removeNode(node);
            // Add the node to the front of the DLL (most recently used).
            addNode(node);
            // The HashMap already holds the correct reference, so no need to put again
            // unless the Node object itself was recreated (which it isn't here).
            // mp.put(key, node); // This line is not strictly necessary as the reference hasn't changed.
            return ans;
        }
        // If the key does not exist, return -1.
        return -1;
    }

    // Put method to add or update a key-value pair in the cache.
    // Time Complexity: O(1) average.
    // Space Complexity: O(1) for operations, but O(N) for storing the map and DLL.
    public void put(int key, int value) {
        // If the key already exists in the cache.
        if (mp.containsKey(key)) {
            // Get the existing node.
            Node existingNode = mp.get(key);
            // Remove it from the DLL.
            removeNode(existingNode);
            // Remove it from the HashMap.
            mp.remove(key);
        }

        // If the cache is at its maximum capacity and we need to add a new element.
        if (mp.size() == capacity) {
            // Get the least recently used node (which is before the dummy tail).
            Node lruNode = tail.prev;
            // Remove its key from the HashMap.
            mp.remove(lruNode.key);
            // Remove the node from the DLL.
            removeNode(lruNode);
        }

        // Create a new node for the new key-value pair.
        Node newNode = new Node(key, value);
        // Add the new node to the front of the DLL (most recently used).
        addNode(newNode);
        // Add the new key-node mapping to the HashMap.
        mp.put(key, newNode);
    }

    // Main method for testing the LRUCache.
    public static void main(String[] args) {
        // Example 1: Basic operations
        System.out.println("Example 1:");
        LRUCache lruCache1 = new LRUCache(2);
        lruCache1.put(1, 1); // cache is {1=1}
        lruCache1.put(2, 2); // cache is {1=1, 2=2}
        System.out.println("Get(1): " + lruCache1.get(1)); // returns 1 (1 is now MRU)
                                                          // cache is {2=2, 1=1}
        lruCache1.put(3, 3); // LRU key 2 is evicted, cache is {1=1, 3=3}
        System.out.println("Get(2): " + lruCache1.get(2)); // returns -1 (not found)
        lruCache1.put(4, 4); // LRU key 1 is evicted, cache is {3=3, 4=4}
        System.out.println("Get(1): " + lruCache1.get(1)); // returns -1 (not found)
        System.out.println("Get(3): " + lruCache1.get(3)); // returns 3 (3 is now MRU)
                                                          // cache is {4=4, 3=3}
        System.out.println("Get(4): " + lruCache1.get(4)); // returns 4 (4 is now MRU)
                                                          // cache is {3=3, 4=4}
        System.out.println("--------------------");

        // Example 2: Update existing key
        System.out.println("Example 2:");
        LRUCache lruCache2 = new LRUCache(2);
        lruCache2.put(1, 1); // cache is {1=1}
        lruCache2.put(2, 2); // cache is {1=1, 2=2}
        lruCache2.put(1, 10); // Update key 1. 1 is now MRU.
                               // cache is {2=2, 1=10}
        System.out.println("Get(1): " + lruCache2.get(1)); // returns 10 (1 is MRU)
        System.out.println("Get(2): " + lruCache2.get(2)); // returns 2 (2 is MRU)
                                                          // cache is {1=10, 2=2}
        lruCache2.put(3, 3); // LRU key 1 is evicted, cache is {2=2, 3=3}
        System.out.println("Get(1): " + lruCache2.get(1)); // returns -1
        System.out.println("--------------------");

        // Example 3: Capacity 1
        System.out.println("Example 3:");
        LRUCache lruCache3 = new LRUCache(1);
        lruCache3.put(1, 1); // cache is {1=1}
        System.out.println("Get(1): " + lruCache3.get(1)); // returns 1
        lruCache3.put(2, 2); // LRU key 1 is evicted, cache is {2=2}
        System.out.println("Get(1): " + lruCache3.get(1)); // returns -1
        System.out.println("Get(2): " + lruCache3.get(2)); // returns 2
        System.out.println("--------------------");
    }
}