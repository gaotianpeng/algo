package tixi.daily08;

import java.util.HashMap;

/*
    前缀树
        1）单个字符串中，字符从前到后的加到一棵多叉树上
        2）字符放在路上，节点上有专属的数据项（常见的是pass和end值）
        3）所有样本都这样添加，如果没有路就新建，如有路就复用
        4）沿途节点的pass值增加1，每个字符串结束时来到的节点end值增加1

    支持的操作
        1）void insert(String str)            添加某个字符串，可以重复添加，每次算1个
        2）int search(String str)             查询某个字符串在结构中还有几个
        3) void delete(String str)            删掉某个字符串，可以重复删除，每次算1个
        4）int prefixNumber(String str)       查询有多少个字符串，是以str做前缀的
 */
public class Code01_TrieTree {
    public static class Node {
        public int pass;
        public int end;
        public Node[] nexts;

        public Node() {
            nexts = new Node[26];
        }
    }

    public static class TrieTree {
        private Node root;

        public TrieTree() {
            root = new Node();
        }

        public void insert(String word) {
            if (word == null || word.isEmpty()) {
                return;
            }

            char[] chs = word.toCharArray();
            int index = 0;
            Node node = root;
            for (int i = 0; i < chs.length; ++i) {
                index = chs[i] - 'a';
                if (node.nexts[index] == null) {
                    node.nexts[index] = new Node();
                }
                node.nexts[index].pass++;
                node = node.nexts[index];
            }
            node.end++;
        }

        public void delete(String word) {
            if (search(word) == 0) {
                return;
            }

            char[] chs = word.toCharArray();
            int index = 0;
            Node node = root;
            for (int i = 0; i < chs.length; ++i) {
                index = chs[i] - 'a';
                if (--node.nexts[index].pass == 0) {
                    node.nexts[index] = null;
                    return;
                }
                
                node = node.nexts[index];
            }
            node.end--;
        }

        public int search(String word) {
            if (word == null || word.isEmpty()) {
                return 0;
            }

            char[] chs = word.toCharArray();
            int index = 0;
            Node node = root;
            for (int i = 0; i < chs.length; ++i) {
                index = chs[i] - 'a';
                if (node.nexts[index] == null) {
                    return 0;
                }
                node = node.nexts[index];
            }

            return node.end;
        }

        public int prefixNumber(String word) {
            if (word == null || word.isEmpty()) {
                return 0;
            }

            char[] chs = word.toCharArray();
            int index = 0;
            Node node = root;
            for (int i = 0; i < chs.length; ++i) {
                index = chs[i] - 'a';
                if (node.nexts[index] == null) {
                    return 0;
                }
                node = node.nexts[index];
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
                if (map_.get(word) == 1) {
                    map_.remove(word);
                } else {
                    map_.put(word, map_.get(word) - 1);
                }
            }
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

        public int prefixNumber(String prefix) {
            if (prefix == null) {
                return 0;
            }

            int ans = 0;
            for (String s: map_.keySet()) {
                if (s.startsWith(prefix)) {
                    ans += map_.get(s);
                }
            }
            return ans;
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
        int arrLen = 100;
        int strLen = 20;
        int testTimes = 100000;
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
