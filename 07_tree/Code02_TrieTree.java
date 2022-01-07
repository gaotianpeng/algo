package tree;
import java.util.HashMap;

public class Code02_TrieTree {

    public static class TrieNode {
        public int pass_;
        public int end_;
        HashMap<Integer, TrieNode> nexts_;

        public TrieNode() {
            pass_ = 0;
            end_ = 0;
            nexts_ = new HashMap<>();
        }
    }

    public static class TrieTree {
        private TrieNode root_node_;
        public TrieTree() {
            root_node_ = new TrieNode();
        }

        public int search(String word) {
            if (word == null) {
                return 0;
            }

            char[] chs = word.toCharArray();
            TrieNode node = root_node_;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = (int)(chs[i]);
                if (!node.nexts_.containsKey(index)) {
                    return 0;
                }
                node = node.nexts_.get(index);
            }

            return node.end_;
        }

        public void insert(String word) {
            if (word == null) {
                return;
            }
            char[] chs = word.toCharArray();
            TrieNode node = root_node_;
            node.pass_++;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = (int)chs[i];
                if (!node.nexts_.containsKey(index)) {
                    node.nexts_.put(index, new TrieNode());
                }
                node = node.nexts_.get(index);
                node.pass_++;
            }
            node.end_++;
        }

        public void delete(String word) {
            if (word == null) {
                return;
            }

            if (search(word) == 0) {
                return;
            }

            TrieNode node = root_node_;
            char[] chs = word.toCharArray();
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = (int)(chs[i]);
                if (--node.nexts_.get(index).pass_ == 0) {
                    node.nexts_.remove(index);
                    return;
                }
                node.nexts_.get(index).pass_--;
                node = node.nexts_.get(index);
            }
            node.end_--;
        }

        public int prefixNumber(String pre) {
            if (pre == null) {
                return 0;
            }

            char[] chs = pre.toCharArray();
            TrieNode node = root_node_;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = (int)(chs[i]);
                if (!node.nexts_.containsKey(index)) {
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

        public int search(String word) {
            if (word == null) {
                return 0;
            }

            if (map_.containsKey(word)) {
                return map_.get(word);
            }

            return 0;
        }

        public void insert(String word) {
            if (word == null) {
                return;
            }

            if (map_.containsKey(word)) {
                map_.put(word, map_.get(word) + 1);
            } else {
                map_.put(word, 1);
            }
        }

        public void delete(String word) {
            if (word == null) {
                return;
            }

            if (map_.containsKey(word)) {
                int cnt = map_.get(word);
                if (cnt == 1) {
                    map_.remove(word);
                } else {
                    map_.put(word, map_.get(word) - 1);
                }
            }
        }

        public int prefixNumber(String pre) {
            if (pre == null) {
                return 0;
            }
            int count = 0;
            for (String word: map_.keySet()) {
                if (word.startsWith(pre)) {
                    count += map_.get(word);
                }
            }
            return count;
        }
    }

    public static String generateRandomString(int max_str_len) {
        int str_len = (int)(Math.random() * max_str_len + 1);
        char[] chs = new char[str_len];
        for (int i = 0; i < str_len; i++) {
            chs[i] = (char)(97 + (int)(Math.random() * 6));
        }

        return String.valueOf(chs);
    }

    public static String[] generateRandomStringArray(int max_str_len, int max_arr_len) {
        int arr_len = (int)(Math.random() * max_arr_len + 1);
        String[] arr = new String[arr_len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = generateRandomString(max_str_len);
        }
        return arr;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int arr_len = 50;
        int str_len = 30;
        int test_times = 10000;
        boolean success = true;
        for (int i = 0; i < test_times; i++) {
            TrieTree trie_tree = new TrieTree();
            TrieTest test_tree = new TrieTest();
            String[] str_arrays = generateRandomStringArray(str_len, arr_len);
            for (int j = 0; j < str_arrays.length; j++) {
                double p = Math.random();
                if (p < 0.25) {
                    trie_tree.insert(str_arrays[j]);
                    test_tree.insert(str_arrays[j]);
                } else if (p < 0.5) {
                    trie_tree.delete(str_arrays[j]);
                    test_tree.delete(str_arrays[j]);
                } else if (p < 0.75) {
                    int cnt1 = trie_tree.search(str_arrays[j]);
                    int cnt2 = test_tree.search(str_arrays[j]);
                    if (cnt1 != cnt2) {
                        success = false;
                        System.out.println(str_arrays[j]);
                        System.out.println("search failed");
                        break;
                    }
                } else if (p < 1.0) {
                    int cnt1 = trie_tree.prefixNumber(str_arrays[j]);
                    int cnt2 = test_tree.prefixNumber(str_arrays[j]);
                    if (cnt1 != cnt2) {
                        success = false;
                        System.out.println(str_arrays[j]);
                        System.out.println(cnt1);
                        System.out.println(cnt2);
                        System.out.println("prefix failed");
                        break;
                    }
                }
                if (!success) {
                    break;
                }
            }
        }

        System.out.println(success? "success" : "failed");
        System.out.println("test end");
    }
}
