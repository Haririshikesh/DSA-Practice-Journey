/*
Problem Title: Longest Common Prefix
Problem Link: [https://leetcode.com/problems/longest-common-prefix/](https://leetcode.com/problems/longest-common-prefix/)

Problem Description:
Write a function to find the longest common prefix string amongst an array of strings.
If there is no common prefix, return an empty string "".

Example 1:
Input: strs = ["flower","flow","flight"]
Output: "fl"

Example 2:
Input: strs = ["dog","racecar","car"]
Output: ""
Explanation: There is no common prefix among the input strings.

Constraints:
- 1 <= strs.length <= 200
- 0 <= strs[i].length <= 200
- strs[i] consists of lowercase English letters.

Initial Intuition:
The problem asks for the longest string that is a prefix of *all* strings in the input array. This means we need to compare characters at the same position across all strings. If at any point a character at a certain position doesn't match across all strings, or if we run out of characters in any string, then the common prefix ends at the character *before* that position.

Approach: Horizontal Scanning
The most intuitive way to solve this is to pick the first string as an initial reference for the common prefix. Then, iterate through the characters of this reference string one by one. For each character, compare it with the character at the same position in *all* other strings. If all characters match, we extend our common prefix. If we find a mismatch or if any string is shorter than the current character's index, we know we've found the end of the common prefix.

Detailed Explanation of Horizontal Scanning Approach:
1.  **Handle Empty/Single Input:** If the input array `strs` is empty or `null`, there's no common prefix, so return `""`. (Your current code assumes `strs` is not empty, which is covered by constraints `1 <= strs.length`).
2.  **Initialize Result:** Initialize an empty string, `res`, which will store the longest common prefix found so far.
3.  **Iterate Through First String:** Use the first string `strs[0]` as the reference. Iterate through its characters using an index `i` from `0` up to `strs[0].length() - 1`. This loop represents the potential length of the common prefix.
4.  **Compare with Other Strings:** Inside the first loop, start another loop that iterates through each `str` in the `strs` array (including `strs[0]` itself, though the check for `strs[0]` will always pass for its own characters up to its length).
5.  **Check for Mismatch or Short String:** For each `str` and the current character index `i`:
    * **Length Check:** If `i` is greater than or equal to the length of the current `str` (`i >= str.length()`), it means this `str` is shorter than the potential common prefix we are building. Therefore, the common prefix cannot extend beyond the current `res`. Return `res`.
    * **Character Mismatch:** If the character at index `i` in `strs[0]` (`strs[0].charAt(i)`) is *not* equal to the character at index `i` in the current `str` (`str.charAt(i)`), it means there's a mismatch. The common prefix ends here. Return `res`.
6.  **Extend Prefix:** If the inner loop completes without returning (meaning the character at index `i` matched across all strings and all strings are long enough), append `strs[0].charAt(i)` to `res`.
7.  **Return Final Result:** After the outer loop completes (meaning we've checked all characters of `strs[0]`), `res` will hold the longest common prefix. Return `res`.

Time Complexity: O(S)
Where S is the sum of the lengths of all characters in all strings in the worst case. More precisely, O(N * M), where N is the number of strings and M is the length of the shortest string (or the length of the common prefix).
-   The outer loop runs up to `min(length of all strings)` times.
-   Inside the outer loop, the inner loop iterates `strs.length` times (N strings).
-   In the worst case (e.g., all strings are identical), we compare every character of the first string against every character of every other string. If the common prefix length is `L` and there are `N` strings, this is roughly `L * N` comparisons. `L` can be up to `M` (length of shortest string). So, O(N * M).

Space Complexity: O(1)
-   We only use a few variables for iteration and to build the `res` string. The space used does not scale with the input size in a significant way beyond the output string itself. If the output string `res` is considered part of the output and not auxiliary space, then it's O(1). If `res` is considered auxiliary, then it's O(M) where M is the length of the common prefix.
*/
import java.util.Arrays; // Only used for the main method's output formatting

class Solution {
    public String longestCommonPrefix(String[] strs) {
        // Handle edge case: empty input array
        if (strs == null || strs.length == 0) {
            return "";
        }

        String res = ""; // Initialize the result string

        // Iterate through the characters of the first string (strs[0]).
        // We assume the common prefix cannot be longer than the first string.
        for (int i = 0; i < strs[0].length(); i++) {
            char currentChar = strs[0].charAt(i); // Get the character from the first string

            // Compare this character with the character at the same position in all other strings.
            for (String str : strs) {
                // Check 1: If the current string 'str' is shorter than the current index 'i',
                //          it means the common prefix cannot extend further.
                // Check 2: If the character at index 'i' in 'str' does not match 'currentChar',
                //          it means the common prefix ends here.
                if (i >= str.length() || currentChar != str.charAt(i)) {
                    return res; // Return the common prefix found so far.
                }
            }
            // If all strings matched the currentChar at index 'i', append it to the result.
            res += currentChar;
        }

        // If the loop completes, it means strs[0] itself is the common prefix (or all strings are identical).
        return res;
    }

    /*
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Example 1
        String[] strs1 = {"flower", "flow", "flight"};
        System.out.println("Longest Common Prefix for " + Arrays.toString(strs1) + ": \"" + solution.longestCommonPrefix(strs1) + "\""); // Expected: "fl"

        // Example 2
        String[] strs2 = {"dog", "racecar", "car"};
        System.out.println("Longest Common Prefix for " + Arrays.toString(strs2) + ": \"" + solution.longestCommonPrefix(strs2) + "\""); // Expected: ""

        // Custom Test Case: All strings are identical
        String[] strs3 = {"apple", "apple", "apple"};
        System.out.println("Longest Common Prefix for " + Arrays.toString(strs3) + ": \"" + solution.longestCommonPrefix(strs3) + "\""); // Expected: "apple"

        // Custom Test Case: Empty string in array
        String[] strs4 = {"abc", "", "ab"};
        System.out.println("Longest Common Prefix for " + Arrays.toString(strs4) + ": \"" + solution.longestCommonPrefix(strs4) + "\""); // Expected: ""

        // Custom Test Case: Single string in array
        String[] strs5 = {"single"};
        System.out.println("Longest Common Prefix for " + Arrays.toString(strs5) + ": \"" + solution.longestCommonPrefix(strs5) + "\""); // Expected: "single"

        // Custom Test Case: No common prefix, but all strings are long
        String[] strs6 = {"abcde", "abcfg", "abchijkl"};
        System.out.println("Longest Common Prefix for " + Arrays.toString(strs6) + ": \"" + solution.longestCommonPrefix(strs6) + "\""); // Expected: "abc"
    }
    */
}