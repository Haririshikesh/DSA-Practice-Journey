// Main class to demonstrate the MyCircularQueue implementation
public class MyCircularQueueMain {

    // Definition for a ListNode.
    // This is a standard linked list node used within the MyCircularQueue.
    static class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
            this.next = null; // Explicitly set to null for clarity
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    // LeetCode Solution Class for MyCircularQueue
    static class MyCircularQueue {

        // Head of the linked list representing the front of the queue
        ListNode head;
        // Tail of the linked list representing the rear of the queue
        ListNode tail;
        // Maximum capacity of the queue
        int capacity; // Renamed from 'size' to avoid confusion with current 'ind'
        // Current number of elements in the queue
        int currentSize; // Renamed from 'ind' for better clarity

        /**
         * Constructor: Initializes the queue with a maximum capacity 'k'.
         *
         * @param k The maximum number of elements the queue can hold.
         */
        public MyCircularQueue(int k) {
            this.head = null;
            this.tail = null;
            this.capacity = k;
            this.currentSize = 0;
        }

        /**
         * Inserts an element into the circular queue. Return true if the operation is successful.
         *
         * @param value The value to be enqueued.
         * @return true if enQueue is successful, false otherwise (if queue is full).
         */
        public boolean enQueue(int value) {
            // If the queue is already full, we cannot add more elements.
            if (isFull()) {
                return false;
            }

            // Create a new ListNode with the given value.
            ListNode newNode = new ListNode(value);

            // If the queue is currently empty, the new node becomes both the head and the tail.
            if (isEmpty()) {
                head = newNode;
                tail = newNode;
            } else {
                // If the queue is not empty, add the new node at the tail.
                // The current tail's 'next' pointer points to the new node.
                tail.next = newNode;
                // Update the tail to be the newly added node.
                tail = newNode;
            }
            // Increment the current size of the queue.
            currentSize++;
            return true;
        }

        /**
         * Deletes an element from the circular queue. Return true if the operation is successful.
         *
         * @return true if deQueue is successful, false otherwise (if queue is empty).
         */
        public boolean deQueue() {
            // If the queue is empty, there's nothing to dequeue.
            if (isEmpty()) {
                return false;
            }

            // Move the head pointer to the next node.
            // This effectively removes the current head node from the queue.
            head = head.next;
            // Decrement the current size of the queue.
            currentSize--;

            // If after decrementing, the queue becomes empty,
            // ensure the tail is also set to null to maintain consistency.
            if (currentSize == 0) {
                tail = null;
            }
            return true;
        }

        /**
         * Get the front item from the queue. If the queue is empty, return -1.
         *
         * @return The value of the front element, or -1 if the queue is empty.
         */
        public int Front() {
            // If the queue is empty, return -1 as per problem requirements.
            // Otherwise, return the value of the head node.
            return (isEmpty()) ? -1 : head.val;
        }

        /**
         * Get the last item from the queue. If the queue is empty, return -1.
         *
         * @return The value of the rear element, or -1 if the queue is empty.
         */
        public int Rear() {
            // If the queue is empty, return -1 as per problem requirements.
            // Otherwise, return the value of the tail node.
            return (isEmpty()) ? -1 : tail.val;
        }

        /**
         * Checks whether the circular queue is empty.
         *
         * @return true if the queue is empty, false otherwise.
         */
        public boolean isEmpty() {
            // The queue is empty if its current size is 0.
            return currentSize == 0;
        }

        /**
         * Checks whether the circular queue is full.
         *
         * @return true if the queue is full, false otherwise.
         */
        public boolean isFull() {
            // The queue is full if its current size equals its maximum capacity.
            return currentSize == capacity;
        }
    }

    /**
     * Your MyCircularQueue object will be instantiated and called as such:
     * MyCircularQueue obj = new MyCircularQueue(k);
     * boolean param_1 = obj.enQueue(value);
     * boolean param_2 = obj.deQueue();
     * int param_3 = obj.Front();
     * int param_4 = obj.Rear();
     * boolean param_5 = obj.isEmpty();
     * boolean param_6 = obj.isFull();
     */
    public static void main(String[] args) {
        System.out.println("Testing MyCircularQueue (Linked List Implementation):");

        // Example 1: Basic operations
        MyCircularQueue myQueue1 = new MyCircularQueue(3); // Capacity 3
        System.out.println("enQueue(1): " + myQueue1.enQueue(1)); // true
        System.out.println("enQueue(2): " + myQueue1.enQueue(2)); // true
        System.out.println("Front(): " + myQueue1.Front()); // 1
        System.out.println("Rear(): " + myQueue1.Rear());   // 2
        System.out.println("isFull(): " + myQueue1.isFull()); // false
        System.out.println("enQueue(3): " + myQueue1.enQueue(3)); // true
        System.out.println("isFull(): " + myQueue1.isFull()); // true
        System.out.println("enQueue(4): " + myQueue1.enQueue(4)); // false (queue is full)
        System.out.println("Front(): " + myQueue1.Front()); // 1
        System.out.println("Rear(): " + myQueue1.Rear());   // 3
        System.out.println("deQueue(): " + myQueue1.deQueue()); // true
        System.out.println("Front(): " + myQueue1.Front()); // 2
        System.out.println("enQueue(4): " + myQueue1.enQueue(4)); // true
        System.out.println("Front(): " + myQueue1.Front()); // 2
        System.out.println("Rear(): " + myQueue1.Rear());   // 4
        System.out.println("deQueue(): " + myQueue1.deQueue()); // true
        System.out.println("deQueue(): " + myQueue1.deQueue()); // true
        System.out.println("deQueue(): " + myQueue1.deQueue()); // true (now empty)
        System.out.println("isEmpty(): " + myQueue1.isEmpty()); // true
        System.out.println("deQueue(): " + myQueue1.deQueue()); // false (queue is empty)
        System.out.println("Front(): " + myQueue1.Front()); // -1
        System.out.println("Rear(): " + myQueue1.Rear());   // -1
        System.out.println("---");

        // Example 2: Empty queue operations
        MyCircularQueue myQueue2 = new MyCircularQueue(1);
        System.out.println("isEmpty(): " + myQueue2.isEmpty()); // true
        System.out.println("Front(): " + myQueue2.Front());     // -1
        System.out.println("Rear(): " + myQueue2.Rear());       // -1
        System.out.println("deQueue(): " + myQueue2.deQueue()); // false
        System.out.println("enQueue(10): " + myQueue2.enQueue(10)); // true
        System.out.println("Front(): " + myQueue2.Front()); // 10
        System.out.println("Rear(): " + myQueue2.Rear());   // 10
        System.out.println("isFull(): " + myQueue2.isFull()); // true
        System.out.println("enQueue(20): " + myQueue2.enQueue(20)); // false
    }
}

/*
// Problem Description: Design Circular Queue

// The problem asks us to design a circular queue. A circular queue is a linear data structure that follows the
// FIFO (First In First Out) principle, and the last position is connected back to the first position to form a circle.
// It is also called a "Ring Buffer".

// Typically, a circular queue is implemented using a fixed-size array, where we use two pointers (front and rear)
// and the modulo operator to handle the "circular" wrapping. However, the provided solution implements it
// using a singly linked list, mimicking the behavior of a fixed-size queue by maintaining a `currentSize` and `capacity`.

// The class should implement the following methods:
// - `MyCircularQueue(k)`: Constructor, initializes the queue with a maximum capacity of `k`.
// - `enQueue(value)`: Inserts an element into the circular queue. Returns `true` if the operation is successful.
// - `deQueue()`: Deletes an element from the circular queue. Returns `true` if the operation is successful.
// - `Front()`: Gets the front item from the queue. Returns `-1` if the queue is empty.
// - `Rear()`: Gets the last item from the queue. Returns `-1` if the queue is empty.
// - `isEmpty()`: Checks whether the circular queue is empty. Returns `true` if empty, `false` otherwise.
// - `isFull()`: Checks whether the circular queue is full. Returns `true` if full, `false` otherwise.

// Constraints:
// - `0 <= k <= 1000` (k is the size of the queue)
// - `0 <= value <= 1000`
// - At most 3000 calls will be made to `enQueue`, `deQueue`, `Front`, `Rear`, `isEmpty`, `isFull`.
// - The values in the queue are integers.

// Problem Explanation (Linked List Implementation Context):
// In this linked list implementation, the "circular" aspect as in array-based circular queues (where indices wrap around)
// is not explicitly present. Instead, the "circular queue" property is interpreted as a queue with a *fixed maximum capacity*.
// We manage this capacity using `currentSize` and `capacity` variables.
// - `head` points to the front of the queue (where elements are dequeued).
// - `tail` points to the rear of the queue (where elements are enqueued).
// - `currentSize` tracks the number of elements currently in the queue.
// - `capacity` stores the maximum allowed elements.

// Approach to the Problem (Using a Singly Linked List):

// Why this approach?
// A linked list is a natural choice for implementing a queue because enqueue (adding to tail) and dequeue (removing from head)
// operations can be performed efficiently (O(1)) when pointers to both head and tail are maintained.
// The fixed capacity requirement is handled by explicitly tracking the `currentSize` and `capacity`.

// Why not other approaches (for a *true* circular queue context)?
// 1. Array-based circular queue: This is the most common and often preferred implementation for a circular queue.
//    It uses a fixed-size array and two pointers (front and rear) with modulo arithmetic to handle wrapping.
//    - Pros: Excellent cache locality, potentially faster due to contiguous memory. Fixed memory allocation.
//    - Cons: Fixed size needs to be known at compile time or upon initialization. Resizing is costly.
//    The provided solution doesn't use an array, so it avoids the need for modulo arithmetic but loses the "circular" physical layout.

// Detailed Steps for the Linked List Approach:

// Class Members:
// - `ListNode head`: Pointer to the first node of the queue.
// - `ListNode tail`: Pointer to the last node of the queue.
// - `int capacity`: Stores the maximum allowed size of the queue.
// - `int currentSize`: Stores the current number of elements in the queue.

// Constructor `MyCircularQueue(int k)`:
// - Initialize `head = null`, `tail = null`.
// - Set `capacity = k`.
// - Set `currentSize = 0`.

// Method `enQueue(int value)`:
// 1. Check if `isFull()`: If `currentSize == capacity`, return `false`.
// 2. Create a `newNode = new ListNode(value)`.
// 3. If `isEmpty()`:
//    - Set `head = newNode`.
//    - Set `tail = newNode`.
// 4. Else (queue is not empty):
//    - Set `tail.next = newNode`.
//    - Set `tail = newNode`.
// 5. Increment `currentSize`.
// 6. Return `true`.

// Method `deQueue()`:
// 1. Check if `isEmpty()`: If `currentSize == 0`, return `false`.
// 2. Move `head = head.next`.
// 3. Decrement `currentSize`.
// 4. If `currentSize == 0` after decrementing (meaning the queue became empty):
//    - Set `tail = null` (important to keep both pointers consistent when the list is empty).
// 5. Return `true`.

// Method `Front()`:
// 1. Check if `isEmpty()`: If `currentSize == 0`, return `-1`.
// 2. Else, return `head.val`.

// Method `Rear()`:
// 1. Check if `isEmpty()`: If `currentSize == 0`, return `-1`.
// 2. Else, return `tail.val`.

// Method `isEmpty()`:
// 1. Return `currentSize == 0`.

// Method `isFull()`:
// 1. Return `currentSize == capacity`.

// Time Complexity Analysis:

// - `MyCircularQueue(int k)` (Constructor):
//   - Initializes a few integer variables and pointers. This is a constant time operation.
//   - Time Complexity: O(1).

// - `enQueue(int value)`:
//   - Involves checking `isFull()` (O(1)), creating a new node (O(1)), and updating a few pointers (O(1)).
//   - Time Complexity: O(1).

// - `deQueue()`:
//   - Involves checking `isEmpty()` (O(1)) and updating `head` pointer (O(1)).
//   - Time Complexity: O(1).

// - `Front()`:
//   - Involves checking `isEmpty()` (O(1)) and returning `head.val` (O(1)).
//   - Time Complexity: O(1).

// - `Rear()`:
//   - Involves checking `isEmpty()` (O(1)) and returning `tail.val` (O(1)).
//   - Time Complexity: O(1).

// - `isEmpty()`:
//   - Involves a single comparison.
//   - Time Complexity: O(1).

// - `isFull()`:
//   - Involves a single comparison.
//   - Time Complexity: O(1).

// All operations are performed in constant time, which is optimal for queue operations.

// Space Complexity Analysis:

// The space complexity refers to the auxiliary space used by the data structure.
// - The `MyCircularQueue` object itself stores a few integer variables (`capacity`, `currentSize`) and two pointers (`head`, `tail`), which consume constant space.
// - The actual elements of the queue are stored in `ListNode` objects. Each `ListNode` occupies constant space.
// - As elements are added to the queue, new `ListNode` objects are created. The number of nodes stored in the linked list can grow up to `k` (the maximum capacity).
// - Therefore, the space used to store the elements is proportional to `k`, the maximum capacity of the queue.

// - Space Complexity: O(k), where k is the maximum capacity of the circular queue.
//   This is because, at any given time, the linked list will hold at most `k` nodes.
*/