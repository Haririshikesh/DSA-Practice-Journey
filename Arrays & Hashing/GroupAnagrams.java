/*
Problem Title: Group Anagrams
Problem Link: [https://leetcode.com/problems/group-anagrams/](https://leetcode.com/problems/group-anagrams/)

Problem Description:
Given an array of strings `strs`, group the anagrams together. You can return the answer in any order.
An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.

Example 1:
Input: strs = ["eat","tea","tan","ate","nat","bat"]
Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
(The order of the output lists and the order of strings within each list does not matter.)

Example 2:
Input: strs = [""]
Output: [[""]]

Example 3:
Input: strs = ["a"]
Output: [["a"]]

Constraints:
- 1 <= strs.length <= 10^4
- 0 <= strs[i].length <= 100
- strs[i] consists of lowercase English letters.

Initial Intuition:
The core idea of anagrams is that they contain the exact same characters with the exact same frequencies, just in a different order. To group them, we need a way to identify a "canonical" representation for each group of anagrams. If we can transform every anagram into the same unique key, we can use a hash map to store and retrieve them.

Approach: Sorting as Key
A very effective way to create a canonical key for anagrams is to sort the characters of each string. For example, "eat", "tea", and "ate" all become "aet" when their characters are sorted. This sorted string can then serve as the key in a hash map. The value associated with this key will be a list of all original strings that produce this sorted key (i.e., all anagrams).

Detailed Explanation of Sorting as Key Approach:
1.  **Initialize HashMap:** Create a `HashMap<String, List<String>>` (let's call it `mp`). The `String` key will be the sorted version of an anagram, and the `List<String>` value will store all the original strings that are anagrams of each other.
2.  **Iterate Through Input Strings:** Loop through each `str` in the input `strs` array.
3.  **Create Canonical Key:**
    * Convert the current `str` into a character array: `char[] ch = str.toCharArray()`.
    * Sort this character array: `Arrays.sort(ch)`.
    * Convert the sorted character array back into a new `String`: `String newStr = new String(ch)`. This `newStr` is our canonical key.
4.  **Populate HashMap:**
    * Check if the `mp` already `containsKey(newStr)`.
    * If `false` (the key doesn't exist), it means this is the first time we've encountered an anagram with this canonical form. Create a new `ArrayList<String>` and associate it with `newStr` in the map: `mp.put(newStr, new ArrayList<>())`.
    * Regardless of whether the key existed or was just created, get the `List<String>` associated with `newStr` and add the *original* `str` to this list: `mp.get(newStr).add(str)`.
5.  **Return Result:** After iterating through all input strings, the `mp.values()` will give you a `Collection` of all the `List<String>` values, each representing a group of anagrams. Convert this `Collection` to a new `ArrayList<List<String>>` and return it.

Time Complexity: O(N * K log K)
Where N is the number of strings in the input array `strs`, and K is the maximum length of a string in `strs`.
-   The loop iterates N times (once for each string).
-   Inside the loop:
    -   `toCharArray()` takes O(K) time.
    -   `Arrays.sort(ch)` takes O(K log K) time (for a string of length K).
    -   `new String(ch)` takes O(K) time.
    -   HashMap operations (`containsKey`, `get`, `put`) take O(1) on average.
-   Therefore, the dominant operation for each string is sorting, leading to an overall complexity of O(N * K log K).

Space Complexity: O(N * K)
-   In the worst case, if all strings are unique (no anagrams), the HashMap will store N entries. Each key (sorted string) can be up to length K, and each value (original string) can also be up to length K.
-   Thus, the total space required for the HashMap is proportional to the total number of characters across all strings, which is O(N * K).
*/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        // Initialize a HashMap to store sorted string (key) -> list of original anagrams (value).
        Map<String, List<String>> mp = new HashMap<>();

        // Iterate through each string in the input array.
        for (String str : strs) {
            // Convert the string to a character array.
            char[] ch = str.toCharArray();
            // Sort the character array to create a canonical key.
            Arrays.sort(ch);
            // Convert the sorted character array back to a string.
            String newStr = new String(ch); // This is our unique key for anagrams.

            // If the map does not already contain this canonical key,
            // create a new empty list for it.
            if (!mp.containsKey(newStr)) {
                mp.put(newStr, new ArrayList<>());
            }
            // Add the original string to the list associated with its canonical key.
            mp.get(newStr).add(str);
        }

        // Return all the lists of strings (values) from the HashMap.
        // Each list represents a group of anagrams.
        return new ArrayList<>(mp.values());
    }

    /*
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Example 1
        String[] strs1 = {"eat", "tea", "tan", "ate", "nat", "bat"};
        List<List<String>> result1 = solution.groupAnagrams(strs1);
        System.out.println("Grouped Anagrams for " + Arrays.toString(strs1) + ": " + result1);
        // Expected output (order of lists and strings within lists may vary):
        // [[bat], [nat, tan], [ate, eat, tea]] or similar

        // Example 2
        String[] strs2 = {""};
        List<List<String>> result2 = solution.groupAnagrams(strs2);
        System.out.println("Grouped Anagrams for " + Arrays.toString(strs2) + ": " + result2);
        // Expected output: [[""]]

        // Example 3
        String[] strs3 = {"a"};
        List<List<String>> result3 = solution.groupAnagrams(strs3);
        System.out.println("Grouped Anagrams for " + Arrays.toString(strs3) + ": " + result3);
        // Expected output: [["a"]]

        // Custom Test Case
        String[] strs4 = {"listen", "silent", "enlist", "hello", "ollhe"};
        List<List<String>> result4 = solution.groupAnagrams(strs4);
        System.out.println("Grouped Anagrams for " + Arrays.toString(strs4) + ": " + result4);
        // Expected: [[hello, ollhe], [listen, silent, enlist]] or similar
    }
    */
}