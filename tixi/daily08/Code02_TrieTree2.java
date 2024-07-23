package tixi.daily08;

import java.util.HashMap;

public class Code02_TrieTree2 {
    public static class TrieNode {
        public int pass;
        public int end;
        public HashMap<Integer, TrieNode> nexts;

        public TrieNode() {
            pass = 0;
            end = 0;
            nexts = new HashMap<>();
        }
    }

    public static class TrieTree {
        private TrieNode rootNode;
        public TrieTree() {
            rootNode = new TrieNode();
        }

        public void insert(String word) {
            if (word == null) {
                return;
            }

            char[] chs = word.toCharArray();
            TrieNode node = rootNode;
            node.pass++;
            for (int i = 0; i < chs.length; i++) {
                int index = (int)chs[i];
                if (!node.nexts.containsKey(index)) {
                    node.nexts.put(index, new TrieNode());
                }
                node = node.nexts.get(index);
                node.pass++;
            }
            node.end++;
        }

        public void delete(String word) {
            if (word == null || search(word) == 0) {
                return;
            }

            char[] chs = word.toCharArray();
            TrieNode node = rootNode;
            node.pass--;
            for (int i = 0; i < chs.length; i++) {
                int index = (int)chs[i];
                if (--node.nexts.get(index).pass == 0) {
                    node.nexts.remove(index);
                    return;
                }
                node = node.nexts.get(index);
            }
            node.pass--;
        }

        public int search(String word) {
            if (word == null) {
                return 0;
            }

            char[] chs = word.toCharArray();
            TrieNode node = rootNode;
            for (int i = 0; i < chs.length; i++) {
                int index = (int)chs[i];
                if (node.nexts.get(index) == null) {
                    return 0;
                }
                node = node.nexts.get(index);
            }
            return node.end;
        }

        public int prefixNumber(String pre) {
            if (pre == null) {
                return 0;
            }

            char[] chs = pre.toCharArray();
            TrieNode node = rootNode;
            for (int i = 0; i < chs.length; i++) {
                int index = (int)chs[i];
                if (node.nexts.get(index) == null) {
                    return 0;
                }
                node = node.nexts.get(index);
            }
            return node.pass;
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

    public static String generateRandomString(int strLen) {
        char[] chs = new char[(int)(Math.random() * strLen) + 1];
        for (int i = 0; i < chs.length; i++) {
            int val = (int)(Math.random() * 6);
            chs[i] = (char)(97+val);
        }
        return String.valueOf(chs);
    }

    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int)(Math.random() * arrLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }

        return ans;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int arrLen = 30;
        int strLen = 20;
        int testTimes = 10000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            String[] arr = generateRandomStringArray(arrLen, strLen);
            TrieTree trieTree = new TrieTree();
            TrieTest testTree = new TrieTest();
            for (int j = 0; j < arr.length; j++) {
                double decide = Math.random();
                if (decide < 0.25) {
                    trieTree.insert(arr[j]);
                    testTree.insert(arr[j]);
                } else if (decide < 0.5) {
                    trieTree.delete(arr[j]);
                    testTree.delete(arr[j]);
                } else if (decide < 0.75) {
                    int ans1 = trieTree.search(arr[j]);
                    int ans2 = testTree.search(arr[j]);
                    if (ans1 != ans2) {
                        success = false;
                        break;
                    }
                } else {
                    int ans1 = trieTree.prefixNumber(arr[j]);
                    int ans2 = testTree.prefixNumber(arr[j]);
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
