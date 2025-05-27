/*
Problem Title: Design HashSet
Problem Link: [https://leetcode.com/problems/design-hashset/](https://leetcode.com/problems/design-hashset/)

Problem Description:
Design a HashSet without using any built-in hash table libraries.

Implement the `MyHashSet` class:
- `void add(int key)`: Inserts the value `key` into the HashSet.
- `void remove(int key)`: Removes the value `key` from the HashSet. Returns `false` if the key is not in the HashSet.
- `boolean contains(int key)`: Returns `true` if the value `key` exists in the HashSet, and `false` otherwise.

Constraints:
- 0 <= key <= 10^6
- At most 10^4 calls will be made to `add`, `remove`, and `contains`.

Initial Intuition:
A HashSet needs to store unique elements and provide fast `add`, `remove`, and `contains` operations. Since the keys are non-negative integers within a relatively small and fixed range (0 to 10^6), we don't need a complex hashing function or collision resolution. We can directly use a boolean array where the index represents the key and the boolean value indicates presence.

Approach: Direct Addressing with Boolean Array
Given the constraints (keys are non-negative integers up to 10^6), the simplest and most efficient approach is to use a boolean array. The array index directly corresponds to the key, and the value at that index (`true` or `false`) indicates whether the key is present in the set.

Detailed Explanation of Direct Addressing Approach:
1.  **`MyHashSet` Constructor:**
    * Initialize a boolean array, `buckets` (or `data`), with a size large enough to accommodate all possible keys. Since keys go up to 10^6, an array of size `10^6 + 1` (to include index 0 up to 10^6) is appropriate.
    * By default, all elements in a boolean array are initialized to `false` in Java, which perfectly represents an empty set.

2.  **`add(int key)` Method:**
    * To add a `key` to the set, simply set the boolean value at `buckets[key]` to `true`. This marks the presence of the `key`.

3.  **`remove(int key)` Method:**
    * To remove a `key` from the set, simply set the boolean value at `buckets[key]` to `false`. This marks the absence of the `key`.

4.  **`contains(int key)` Method:**
    * To check if a `key` exists in the set, simply return the boolean value at `buckets[key]`. If it's `true`, the key is present; if `false`, it's not.

Time Complexity: O(1) for all operations (`add`, `remove`, `contains`)
-   Accessing or modifying an element in a boolean array by index is a constant-time operation.
-   The size of the array is fixed, not dependent on the number of elements *added* to the set, but on the maximum possible key value.

Space Complexity: O(Max_Key_Value)
-   We declare a boolean array whose size is determined by the maximum possible value of `key` (10^6 + 1).
-   Therefore, the space complexity is O(10^6), which is a constant amount of memory, but it's important to note it's not O(1) relative to the *number of elements actually stored* if the key range was much larger. However, given the constraint, it's a fixed, acceptable amount of memory.
*/
import java.util.Arrays; // Only for main method's output formatting

class MyHashSet {

    // The maximum possible key value is 10^6.
    // We need an array of size (max_key_value + 1) to accommodate keys from 0 to 10^6.
    private static final int MAX_KEY_VALUE = 1_000_000;
    private boolean[] buckets;

    /** Initialize your data structure here. */
    public MyHashSet() {
        buckets = new boolean[MAX_KEY_VALUE + 1];
        // By default, all elements in a boolean array are initialized to false,
        // which means the set is initially empty.
    }

    public void add(int key) {
        // Mark the presence of the key by setting the corresponding index to true.
        buckets[key] = true;
    }

    public void remove(int key) {
        // Mark the absence of the key by setting the corresponding index to false.
        buckets[key] = false;
    }

    /** Returns true if this set contains the specified element key. */
    public boolean contains(int key) {
        // Return the boolean value at the key's index.
        // True means the key exists, false means it does not.
        return buckets[key];
    }

    /*
    public static void main(String[] args) {
        // Test cases for MyHashSet
        MyHashSet hashSet = new MyHashSet();

        System.out.println("Adding 1");
        hashSet.add(1);
        System.out.println("Adding 2");
        hashSet.add(2);

        System.out.println("Contains 1: " + hashSet.contains(1)); // Expected: true
        System.out.println("Contains 3: " + hashSet.contains(3)); // Expected: false

        System.out.println("Adding 2 again");
        hashSet.add(2); // Adding an existing key should have no effect on contains()

        System.out.println("Contains 2: " + hashSet.contains(2)); // Expected: true

        System.out.println("Removing 2");
        hashSet.remove(2);

        System.out.println("Contains 2 after removal: " + hashSet.contains(2)); // Expected: false

        System.out.println("Contains 1 after 2 removal: " + hashSet.contains(1)); // Expected: true (1 should still be there)

        System.out.println("\n--- Custom Test Cases ---");
        MyHashSet customHashSet = new MyHashSet();
        System.out.println("Adding 0");
        customHashSet.add(0);
        System.out.println("Contains 0: " + customHashSet.contains(0)); // Expected: true
        System.out.println("Removing 0");
        customHashSet.remove(0);
        System.out.println("Contains 0 after removal: " + customHashSet.contains(0)); // Expected: false

        System.out.println("Adding 1000000");
        customHashSet.add(1000000);
        System.out.println("Contains 1000000: " + customHashSet.contains(1000000)); // Expected: true
    }
    */
}