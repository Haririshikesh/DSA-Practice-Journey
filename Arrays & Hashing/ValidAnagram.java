/*
Problem Title: Valid Anagram
Problem Link: [https://leetcode.com/problems/valid-anagram/](https://leetcode.com/problems/valid-anagram/)

Problem Description:
Given two strings `s` and `t`, return `true` if `t` is an anagram of `s`, and `false` otherwise.
An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.

Example 1:
Input: s = "anagram", t = "nagaram"
Output: true

Example 2:
Input: s = "rat", t = "car"
Output: false

Constraints:
- 1 <= s.length, t.length <= 5 * 10^4
- `s` and `t` consist of lowercase English letters.

Initial Intuition:
An anagram means that two strings contain the exact same characters with the exact same frequencies, just possibly in a different order. To check this, we need a way to count the occurrences of each character in both strings and then compare those counts.

Approach: Character Counting (Frequency Array)
Since the problem states that `s` and `t` consist of lowercase English letters, we know there are only 26 possible characters ('a' through 'z'). We can use a fixed-size array (of size 26) to store the frequency of each character.

Detailed Explanation of Character Counting Approach:
1.  **Length Check:** First, perform a quick check: if the lengths of `s` and `t` are different, they cannot be anagrams. Return `false` immediately.
2.  **Initialize Frequency Array:** Create an integer array, say `count`, of size 26 (initialized to all zeros). Each index `0` to `25` will correspond to a letter ('a' to 'z').
3.  **Process String `s`:** Iterate through each character in string `s`. For each character `c`:
    * Increment the count for that character in the `count` array. (e.g., `count[c - 'a']++`).
4.  **Process String `t`:** Iterate through each character in string `t`. For each character `c`:
    * Decrement the count for that character in the `count` array. (e.g., `count[c - 'a']--`).
5.  **Validate Frequencies:** After processing both strings, iterate through the `count` array.
    * If any value in the `count` array is not zero, it means either `s` had a character that `t` didn't have (positive count) or `t` had a character that `s` didn't have (negative count), or their frequencies didn't match. In this case, return `false`.
6.  **Return True:** If the loop completes and all values in `count` are zero, it means both strings had the exact same characters with the exact same frequencies. Return `true`.

Time Complexity: O(N)
Where N is the length of the strings `s` and `t`.
-   The initial length check is O(1).
-   The first loop iterates through `s` once (N characters), performing constant time operations.
-   The second loop iterates through `t` once (N characters), performing constant time operations.
-   The final loop iterates through the `count` array (26 elements), which is a constant operation.
-   Therefore, the overall time complexity is linear, O(N).

Space Complexity: O(1)
-   We use a fixed-size integer array `count` of size 26, regardless of the input string length. This constant space usage means the space complexity is O(1).
*/
import java.util.Arrays; // Only used for the main method's output formatting

class Solution {
    public boolean isAnagram(String s, String t) {
        // Step 1: Length Check
        if (s.length() != t.length()) {
            return false;
        }

        // Step 2: Initialize Frequency Array
        // 'count' array of size 26 for lowercase English letters ('a' through 'z')
        int[] count = new int[26];

        // Step 3: Process String 's'
        // Increment count for each character in 's'
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
        }

        // Step 4: Process String 't'
        // Decrement count for each character in 't'
        for (int i = 0; i < t.length(); i++) {
            count[t.charAt(i) - 'a']--;
        }

        // Step 5: Validate Frequencies
        // If any count is not zero, the strings are not anagrams
        for (int val : count) {
            if (val != 0) {
                return false;
            }
        }

        // Step 6: All counts are zero, so they are anagrams
        return true;
    }

    /*
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Example 1
        String s1 = "anagram";
        String t1 = "nagaram";
        System.out.println("'" + s1 + "' and '" + t1 + "' are anagrams: " + solution.isAnagram(s1, t1)); // Expected: true

        // Example 2
        String s2 = "rat";
        String t2 = "car";
        System.out.println("'" + s2 + "' and '" + t2 + "' are anagrams: " + solution.isAnagram(s2, t2)); // Expected: false

        // Custom Test Case: Different lengths
        String s3 = "hello";
        String t3 = "hell";
        System.out.println("'" + s3 + "' and '" + t3 + "' are anagrams: " + solution.isAnagram(s3, t3)); // Expected: false

        // Custom Test Case: Same characters, different frequencies
        String s4 = "app";
        String t4 = "pap";
        System.out.println("'" + s4 + "' and '" + t4 + "' are anagrams: " + solution.isAnagram(s4, t4)); // Expected: true

        String s5 = "aacc";
        String t5 = "ccac";
        System.out.println("'" + s5 + "' and '" + t5 + "' are anagrams: " + solution.isAnagram(s5, t5)); // Expected: true
    }
    */
}