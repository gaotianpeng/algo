package tixi.daily08;

import java.util.HashMap;

public class Code02_TrieTree2 {
    public static class TrieNode {
        public int pass_;
        public int end_;
        public HashMap<Integer, TrieNode> nexts_;

        public TrieNode() {
            pass_ = 0;
            end_= 0;
            nexts_ = new HashMap<>();
        }
    }

    public static class TrieTree {
        private TrieNode root_node_;
        public TrieTree() {
            root_node_ = new TrieNode();
        }

        public void insert(String word) {
            if (word == null) {
                return;
            }

            char[] chs = word.toCharArray();
            TrieNode node = root_node_;
            node.pass_++;
            for (int i = 0; i < chs.length; i++) {
                int index = (int)chs[i];
                if (!node.nexts_.containsKey(index)) {
                    node.nexts_.put(index, new TrieNode());
                }
                node = node.nexts_.get(index);
                node.pass_++;
            }
            node.end_++;
        }

        public void delete(String word) {
            if (word == null || search(word) == 0) {
                return;
            }

            char[] chs = word.toCharArray();
            TrieNode node = root_node_;
            node.pass_--;
            for (int i = 0; i < chs.length; i++) {
                int index = (int)chs[i];
                if (--node.nexts_.get(index).pass_ == 0) {
                    node.nexts_.remove(index);
                    return;
                }
                node = node.nexts_.get(index);
            }
            node.pass_--;
        }

        public int search(String word) {
            if (word == null) {
                return 0;
            }

            char[] chs = word.toCharArray();
            TrieNode node = root_node_;
            for (int i = 0; i < chs.length; i++) {
                int index = (int)chs[i];
                if (node.nexts_.get(index) == null) {
                    return 0;
                }
                node = node.nexts_.get(index);
            }
            return node.end_;
        }

        public int prefixNumber(String pre) {
            if (pre == null) {
                return 0;
            }

            char[] chs = pre.toCharArray();
            TrieNode node = root_node_;
            for (int i = 0; i < chs.length; i++) {
                int index = (int)chs[i];
                if (node.nexts_.get(index) == null) {
                    return 0;
                }
                node = node.nexts_.get(index);
            }
            return node.pass_;
        }
    }

    /*
        for test
     */
    public static class TrieTest {
        private HashMap<String, Integer> map_;

        public TrieTest() {
            map_ = new HashMap<>();
        }

        public void insert(String word) {
            if (!map_.containsKey(word)) {
                map_.put(word, 1);
            } else {
                map_.put(word, map_.get(word) + 1);
            }
        }

        public void delete(String word) {
            if (map_.containsKey(word)) {
                if (map_.get(word) == 1) {
                    map_.remove(word);
                } else {
                    map_.put(word, map_.get(word) - 1);
                }
            }
        }

        public int search(String word) {
            if (!map_.containsKey(word)) {
                return 0;
            } else {
                return map_.get(word);
            }
        }

        public int prefixNumber(String pre) {
            int count = 0;
            for (String str: map_.keySet()) {
                if (str.startsWith(pre)) {
                    count += map_.get(str);
                }
            }

            return count;
        }
    }

    public static String generateRandomString(int str_len) {
        char[] chs = new char[(int)(Math.random() * str_len) + 1];
        for (int i = 0; i < chs.length; i++) {
            int val = (int)(Math.random() * 6);
            chs[i] = (char)(97+val);
        }
        return String.valueOf(chs);
    }

    public static String[] generateRandomStringArray(int arr_len, int str_len) {
        String[] ans = new String[(int)(Math.random() * arr_len) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(str_len);
        }

        return ans;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int arr_len = 30;
        int str_len = 20;
        int test_times = 10000;
        boolean success = true;
        for (int i = 0; i < test_times; i++) {
            String[] arr = generateRandomStringArray(arr_len, str_len);
            TrieTree trie_tree = new TrieTree();
            TrieTest test_tree = new TrieTest();
            for (int j = 0; j < arr.length; j++) {
                double decide = Math.random();
                if (decide < 0.25) {
                    trie_tree.insert(arr[j]);
                    test_tree.insert(arr[j]);
                } else if (decide < 0.5) {
                    trie_tree.delete(arr[j]);
                    test_tree.delete(arr[j]);
                } else if (decide < 0.75) {
                    int ans1 = trie_tree.search(arr[j]);
                    int ans2 = test_tree.search(arr[j]);
                    if (ans1 != ans2) {
                        success = false;
                        break;
                    }
                } else {
                    int ans1 = trie_tree.prefixNumber(arr[j]);
                    int ans2 = test_tree.prefixNumber(arr[j]);
                    if (ans1 != ans2) {
                        success = false;
                        break;
                    }
                }
            }
            if (!success) {
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
