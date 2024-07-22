package leetcode.all;
/*
    208. Implement Trie (Prefix Tree)
        A trie (pronounced as "try") or prefix tree is a tree data structure used to efficiently
        store and retrieve keys in a dataset of strings. There are various applications of this
         data structure, such as autocomplete and spellchecker.

        Implement the Trie class:

        Trie() Initializes the trie object.
        void insert(String word) Inserts the string word into the trie.
        boolean search(String word) Returns true if the string word is in the trie (i.e., was inserted before), and false otherwise.
        boolean startsWith(String prefix) Returns true if there is a previously inserted string word that has the prefix prefix, and false otherwise.

        Example 1:
            Input
            ["Trie", "insert", "search", "search", "startsWith", "insert", "search"]
            [[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]
            Output
            [null, null, true, false, true, null, true]

        Explanation
            Trie trie = new Trie();
            trie.insert("apple");
            trie.search("apple");   // return True
            trie.search("app");     // return False
            trie.startsWith("app"); // return True
            trie.insert("app");
            trie.search("app");     // return True

        Constraints:
            1 <= word.length, prefix.length <= 2000
            word and prefix consist only of lowercase English letters.
            At most 3 * 104 calls in total will be made to insert, search, and startsWith.
 */
public class Code_0208_ImplementTriePrefixTree {
    public static class Node {
        public int pass;
        public int end;
        public Node[] nexts;

        public Node() {
            pass = 0;
            end = 0;
            nexts = new Node[26];
        }
    }

    public static class Trie {
        private Node root;

        public Trie() {
            root = new Node();
        }

        public void insert(String word) {
            if (word == null || word.isEmpty()) {
                return;
            }

            char[] str = word.toCharArray();
            int index = 0;
            Node node = root;
            for (int i = 0; i < str.length; ++i) {
                index = str[i] - 'a';
                if (node.nexts[index] == null) {
                    node.nexts[index] = new Node();
                }
                node.nexts[index].pass++;
                node = node.nexts[index];
            }

            node.end++;
        }

        public boolean search(String word) {
            if (word == null || word.isEmpty()) {
                return false;
            }

            char[] str = word.toCharArray();
            int index = 0;
            Node node = root;
            for (int i = 0; i < str.length; ++i) {
                index = str[i] - 'a';
                if (node.nexts[index] == null) {
                    return false;
                }
                node = node.nexts[index];
            }

            return node.end != 0;
        }

        public boolean startsWith(String prefix) {
            if (prefix == null || prefix.isEmpty()) {
                return false;
            }

            char[] str = prefix.toCharArray();
            int index = 0;
            Node node = root;
            for (int i = 0; i < str.length; ++i) {
                index = str[i] - 'a';
                if (node.nexts[index] == null) {
                    return false;
                }
                node = node.nexts[index];
            }

            return true;
        }
    }
}
