/*
Problem Title: Accounts Merge
Problem Link: [https://leetcode.com/problems/accounts-merge/](https://leetcode.com/problems/accounts-merge/)

Problem Description:
Given a list of `accounts` where each `account[i]` is a `List<String>` that consists of a name and a list of emails, return the accounts merged. The accounts can be merged if they have some email in common. Note that two accounts definitely belong to the same person if they have the same email. A person might have different names for different accounts, but we should choose the first name that appeared in the list.

Return the accounts in any order. Also, the emails within each account must be sorted in ascending order.

Example 1:
Input: accounts = [["John","johnsmith@mail.com","john_newyork@mail.com"],["John","johnsmith@mail.com","john00@mail.com"],["Mary","mary@mail.com"],["John","johnnybravo@mail.com"]]
Output: [["John","john00@mail.com","john_newyork@mail.com","johnsmith@mail.com"],["Mary","mary@mail.com"],["John","johnnybravo@mail.com"]]
Explanation:
The first two accounts "John" having the email "johnsmith@mail.com" are merged into one account, so the emails "johnsmith@mail.com", "john_newyork@mail.com", and "john00@mail.com" are listed.
The third account "Mary" with email "mary@mail.com" is a separate account.
The fourth account "John" with email "johnnybravo@mail.com" is also a separate account.

Example 2:
Input: accounts = [["Gabe","Gabe0@m.co","Gabe3@m.co","Gabe1@m.co"],["Kevin","Kevin3@m.co","Kevin5@m.co","Kevin0@m.co"],["Ethan","Ethan5@m.co","Ethan4@m.co","Ethan0@m.co"],["Hanzo","Hanzo3@m.co","Hanzo1@m.co","Hanzo0@m.co"],["Fern","Fern5@m.co","Fern1@m.co","Fern0@m.co"]]
Output: [["Ethan","Ethan0@m.co","Ethan4@m.co","Ethan5@m.co"],["Gabe","Gabe0@m.co","Gabe1@m.co","Gabe3@m.co"],["Hanzo","Hanzo0@m.co","Hanzo1@m.co","Hanzo3@m.co"],["Kevin","Kevin0@m.co","Kevin3@m.co","Kevin5@m.co"],["Fern","Fern0@m.co","Fern1@m.co","Fern5@m.co"]]

Constraints:
- 1 <= accounts.length <= 1000
- 2 <= account[i].length <= 10
- 1 <= account[i][j].length <= 30
- account[i][0] consists of English letters.
- account[i][j] (for j > 0) is a valid email.
- The same email will not appear in two different accounts unless they are the same person. (This simplifies the problem significantly.)

Initial Intuition:
This problem is about grouping connected components. Each account is a "node," and if two accounts share a common email, they are connected and belong to the same person. The goal is to merge all accounts belonging to the same person and list all their unique emails, sorted, along with their name. This structure strongly suggests using the **Union-Find** (also known as Disjoint Set Union - DSU) data structure.

Approach: Union-Find (Disjoint Set Union - DSU)
We can model each original account (by its index) as an element in a disjoint set. When we encounter an email, we check if it has been seen before.
- If an email has been seen, it means the current account shares an email with a previously processed account. We then `union` these two accounts (their representatives) into the same set.
- If an email is new, we associate it with the current account's index.

After processing all accounts and their emails, the Union-Find structure will tell us which original account indices belong to the same merged group (person). We can then collect all emails belonging to accounts in the same group and consolidate them.

Detailed Explanation:

1.  **UnionFind Class:**
    * **`parents` array:** `parents[i]` stores the parent of element `i`. If `parents[i] == i`, `i` is the root (representative) of its set.
    * **`ranks` array:** This array is used for the "union by size/rank" optimization to keep the tree structure flat. In this specific implementation, `ranks[i]` stores the size of the component if `i` is a root.
    * **`find(n)`:** This method finds the root (representative) of the set that `n` belongs to. It uses **path compression** optimization: during the traversal to the root, it also updates the `parents` of all visited nodes directly to the root, flattening the tree for faster future lookups.
    * **`union(x, y)`:** This method merges the sets containing `x` and `y`.
        * It first finds the roots of `x` and `y` (`rootX`, `rootY`).
        * If `rootX == rootY`, they are already in the same set, so do nothing.
        * Otherwise, it merges the two sets. The optimization used here is **union by size**: the smaller tree is attached as a child of the root of the larger tree. This ensures the tree depth remains small. The `ranks` (sizes) are updated accordingly for the new root.

2.  **Main `accountsMerge` Logic:**

    * **Initialization:**
        * Create an instance of `UnionFind` with `accounts.size()`. Each account (identified by its original index `0` to `N-1`) starts as its own disjoint set.
        * Create a `HashMap<String, Integer> mailIdMp`. This map will store each `email` encountered and map it to the *first account index* it appeared in. This mapping is crucial for identifying which accounts to union.

    * **First Pass - Building Disjoint Sets:**
        * Iterate through each `account` in the `accounts` list using its index `i`.
        * For each email `details.get(j)` (starting from `j=1` because `details.get(0)` is the name):
            * If `mailIdMp.containsKey(email)`:
                * This means this `email` has been seen before in an account with index `mailIdMp.get(email)`.
                * Therefore, the current account `i` and the previously seen account `mailIdMp.get(email)` belong to the same person. `uf.union(i, mailIdMp.get(email))`.
            * Else (`email` is new):
                * Store this `email` in `mailIdMp` and associate it with the current account index `i`: `mailIdMp.put(email, i)`.

    * **Second Pass - Grouping Emails by Root:**
        * Create a `HashMap<Integer, List<String>> idMailMp`. This map will group emails by their merged account's *root ID*. The key will be the representative (root) of a merged set, and the value will be a list of all emails belonging to that merged set.
        * Iterate through all unique emails collected in `mailIdMp.keySet()`.
        * For each `email`:
            * Find the `root` representative of the account this email belongs to using `uf.find(mailIdMp.get(email))`.
            * Retrieve the list of emails associated with this `root` in `idMailMp`. If the `root` isn't a key yet, create a new `ArrayList<String>`.
            * Add the `email` to this list.
            * Store/update the list for this `root` in `idMailMp`.

    * **Final Result Formatting:**
        * Create a `List<List<String>> mergedList` to store the final output.
        * Iterate through the keys (`root IDs`) in `idMailMp`. Each `key` represents a unique merged account.
        * For each `root ID`:
            * Get the `List<String> emails` from `idMailMp.get(root)`.
            * Sort these `emails` alphabetically: `Collections.sort(emails)`.
            * Prepend the original account name to this list. The name is taken from the account `accounts.get(root).get(0)` (since `root` is one of the original account indices).
            * Add this formatted list (`[Name, email1, email2, ...]`) to `mergedList`.
        * Return `mergedList`.

Time Complexity: O(E * α(N) + M log M)
Where:
-   `N` is the number of accounts.
-   `E` is the total number of emails across all accounts (sum of `account[i].length - 1` for all `i`).
-   `α` is the inverse Ackermann function, which grows extremely slowly, making `α(N)` practically a very small constant (less than 5 for any realistic N).
-   `M` is the maximum number of emails in a single merged account. `M log M` comes from sorting emails within each merged group. In the worst case, `M` can be `E`.

-   **Building `mailIdMp` and Union-Find operations:** O(E * α(N))
    -   Each email is processed once. HashMap operations are O(1) on average. Union-Find operations (`find` and `union`) with path compression and union by size take nearly constant time.
-   **Grouping emails by root:** O(E * α(N))
    -   Iterating through `mailIdMp` (E unique emails) and performing `find` for each.
-   **Sorting emails:** For each merged account, emails are sorted. In the worst case, all emails belong to one person, resulting in O(E log E).
-   **Total:** The dominant factor is O(E * α(N) + E log E). Since E <= 1000 * 9 = 9000 (max emails), E log E is feasible.

Space Complexity: O(N + E)
-   `parents` and `ranks` arrays: O(N)
-   `mailIdMp` (email to account index): O(E) unique emails.
-   `idMailMp` (root to list of emails): O(N + E) in worst case (N distinct accounts, E distinct emails grouped).
-   Recursion stack for `find` (very shallow due to path compression).
-   Total: O(N + E).
*/
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AccountsMerge {

    // Inner class for Union-Find data structure
    class UnionFind {
        int[] parents;
        int[] ranks; // Used for union by size (stores size of component if root)

        public UnionFind(int n) {
            parents = new int[n];
            ranks = new int[n];
            for (int i = 0; i < n; i++) {
                parents[i] = i; // Each element is initially its own parent
                ranks[i] = 1;   // Each set initially has a size of 1
            }
        }

        // Find operation with path compression
        public int find(int i) {
            if (parents[i] == i) {
                return i; // 'i' is the root of its set
            }
            // Path compression: set parent[i] directly to the root
            parents[i] = find(parents[i]);
            return parents[i];
        }

        // Union operation with union by size optimization
        // Merges the sets containing x and y
        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX == rootY) {
                return; // x and y are already in the same set
            }

            // Union by size: attach the smaller tree to the root of the larger tree
            if (ranks[rootX] >= ranks[rootY]) { // If sizes are equal, attach Y to X
                parents[rootY] = rootX;
                ranks[rootX] += ranks[rootY]; // Update size of the new root
            } else {
                parents[rootX] = rootY;
                ranks[rootY] += ranks[rootX]; // Update size of the new root
            }
        }
    }

    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        // Initialize Union-Find structure with the number of accounts
        UnionFind uf = new UnionFind(accounts.size());

        // Map emails to their original account index.
        // If an email is encountered again, it means the current account and the
        // mapped account belong to the same person.
        Map<String, Integer> mailIdMp = new HashMap<>(); // email -> account index

        // First pass: Build disjoint sets based on shared emails
        for (int i = 0; i < accounts.size(); i++) {
            List<String> accountDetails = accounts.get(i);
            // Iterate through emails for the current account (skip the name at index 0)
            for (int j = 1; j < accountDetails.size(); j++) {
                String email = accountDetails.get(j);
                if (mailIdMp.containsKey(email)) {
                    // If email already seen, union current account 'i' with the account it was first seen in.
                    uf.union(i, mailIdMp.get(email));
                } else {
                    // If email is new, map it to the current account index 'i'.
                    mailIdMp.put(email, i);
                }
            }
        }

        // Second pass: Group all emails by their merged account's root ID
        // The key will be the representative (root) account index, value is list of emails.
        Map<Integer, List<String>> idMailMp = new HashMap<>(); // root_id -> list of emails

        for (String email : mailIdMp.keySet()) {
            // Find the representative (root) for the account this email belongs to
            int root = uf.find(mailIdMp.get(email));
            
            // Get the list of emails for this root. If not present, create a new list.
            idMailMp.computeIfAbsent(root, k -> new ArrayList<>()).add(email);
        }

        // Final step: Format the result as required
        List<List<String>> mergedAccountsList = new ArrayList<>();
        for (Integer rootId : idMailMp.keySet()) {
            List<String> emails = idMailMp.get(rootId);
            Collections.sort(emails); // Sort emails in ascending order

            // Add the account name at the beginning of the list.
            // The name is taken from the original account list corresponding to the rootId.
            emails.add(0, accounts.get(rootId).get(0));
            mergedAccountsList.add(emails);
        }

        return mergedAccountsList;
    }

    /*
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Example 1
        List<List<String>> accounts1 = new ArrayList<>();
        accounts1.add(Arrays.asList("John", "johnsmith@mail.com", "john_newyork@mail.com"));
        accounts1.add(Arrays.asList("John", "johnsmith@mail.com", "john00@mail.com"));
        accounts1.add(Arrays.asList("Mary", "mary@mail.com"));
        accounts1.add(Arrays.asList("John", "johnnybravo@mail.com"));
        System.out.println("Accounts 1 (Input): " + accounts1);
        List<List<String>> merged1 = solution.accountsMerge(accounts1);
        System.out.println("Accounts 1 (Merged): " + merged1);
        // Expected: [["John","john00@mail.com","john_newyork@mail.com","johnsmith@mail.com"],["Mary","mary@mail.com"],["John","johnnybravo@mail.com"]]
        // (Order of outer lists may vary)

        System.out.println("\n--- Next Test ---");
        // Reset solution for next test because UnionFind uses instance variables
        solution = new Solution();

        // Example 2
        List<List<String>> accounts2 = new ArrayList<>();
        accounts2.add(Arrays.asList("Gabe","Gabe0@m.co","Gabe3@m.co","Gabe1@m.co"));
        accounts2.add(Arrays.asList("Kevin","Kevin3@m.co","Kevin5@m.co","Kevin0@m.co"));
        accounts2.add(Arrays.asList("Ethan","Ethan5@m.co","Ethan4@m.co","Ethan0@m.co"));
        accounts2.add(Arrays.asList("Hanzo","Hanzo3@m.co","Hanzo1@m.co","Hanzo0@m.co"));
        accounts2.add(Arrays.asList("Fern","Fern5@m.co","Fern1@m.co","Fern0@m.co"));
        System.out.println("Accounts 2 (Input): " + accounts2);
        List<List<String>> merged2 = solution.accountsMerge(accounts2);
        System.out.println("Accounts 2 (Merged): " + merged2);
        // Expected (order of outer lists may vary, emails sorted):
        // [["Ethan","Ethan0@m.co","Ethan4@m.co","Ethan5@m.co"],
        //  ["Gabe","Gabe0@m.co","Gabe1@m.co","Gabe3@m.co"],
        //  ["Hanzo","Hanzo0@m.co","Hanzo1@m.co","Hanzo3@m.co"],
        //  ["Kevin","Kevin0@m.co","Kevin3@m.co","Kevin5@m.co"],
        //  ["Fern","Fern0@m.co","Fern1@m.co","Fern5@m.co"]]

        System.out.println("\n--- Next Test ---");
        solution = new Solution();
        // Custom Test Case: More complex merging
        List<List<String>> accounts3 = new ArrayList<>();
        accounts3.add(Arrays.asList("Alice", "alice@example.com", "alice2@example.com")); // idx 0
        accounts3.add(Arrays.asList("Bob", "bob@example.com", "bob2@example.com"));       // idx 1
        accounts3.add(Arrays.asList("Alice", "alice@example.com", "alice3@example.com")); // idx 2 (merges with 0)
        accounts3.add(Arrays.asList("Charlie", "charlie@example.com"));                   // idx 3
        accounts3.add(Arrays.asList("Bob", "bob2@example.com", "bob3@example.com"));      // idx 4 (merges with 1)
        System.out.println("Accounts 3 (Input): " + accounts3);
        List<List<String>> merged3 = solution.accountsMerge(accounts3);
        System.out.println("Accounts 3 (Merged): " + merged3);
        // Expected:
        // [["Alice","alice2@example.com","alice3@example.com","alice@example.com"],
        //  ["Bob","bob2@example.com","bob3@example.com","bob@example.com"],
        //  ["Charlie","charlie@example.com"]]
        // (Order of outer lists may vary)
    }
    */
}