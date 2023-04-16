package tixi.daily32;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
    解决在一个大字符串中，找到多个候选字符串的问题
    核心算法
        1）把所有匹配串生成一棵前缀树
        2）前缀树节点增加 fail 指针
        3）fail指针的含义：如果必须以当前字符结尾，当前形成的路径是str，剩下哪一个字符串的前缀和str的后缀，
        拥有最大的匹配长度。fail指针就指向那个字符串的最后一个字符所对应的节点
 */
public class Code03_AC {
    public static class Node {
        public String end;
        public boolean end_use;
        public Node fail;
        public Node[] nexts;

        public Node() {
            end_use = false;
            end = null;
            fail = null;
            nexts = new Node[26];
        }
    }

    public static class ACAutomation {
        private Node root;

        public ACAutomation() {
            root = new Node();
        }

        public void insert(String s) {
            char[] str = s.toCharArray();
            int index = 0;
            Node cur = root;
            for (int i = 0; i < str.length; ++i) {
                index = str[i] - 'a';
                if (cur.nexts[index] == null) {
                    cur.nexts[index] = new Node();
                }
                cur = cur.nexts[index];
            }
            cur.end = s;
        }

        public void build() {
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            Node cur = null;
            Node cfail = null;

            while (!queue.isEmpty()) {
                // 取出父Node
                cur = queue.poll();
                for (int i = 0; i < 26; ++i) { // 所有的路
                    // 把i号儿子的 fail 设置好
                    if (cur.nexts[i] != null) {
                        cur.nexts[i].fail = root;
                        cfail = cur.fail;
                        while (cfail != null) {
                            if (cfail.nexts[i] != null) {
                                cur.nexts[i].fail = cfail.nexts[i];
                                break;
                            }
                            cfail = cfail.fail;
                        }
                        queue.add(cur.nexts[i]);
                    }
                }
            }
        }
        /*
            abcde
              cde
               de
                e
         */
        // content 大文章
        public List<String> containWords(String content) {
            char[] str = content.toCharArray();
            int index = 0;
            Node cur = root;
            Node follow = null;
            List<String> ans = new LinkedList<>();
            for (int i = 0; i < str.length; ++i) {
                index = str[i] - 'a';
                // 如果在当前字符在这条路上没配出来，就随着fail方向走向下条路径
                while (cur.nexts[index] == null && cur != root) {
                    cur = cur.fail;
                }
                // 1) 现在来到的路径，是可以继续匹配的
                // 2) 现在来到的节点，就是前缀树的根节点
                cur = cur.nexts[index] != null ? cur.nexts[index] : root;
                follow = cur;
                while (follow != root) {
                    if (follow.end_use) {
                        break;
                    }
                    if (follow.end != null) {
                        ans.add(follow.end);
                        follow.end_use = true;
                    }
                    follow = follow.fail;
                }
            }

            return ans;
        }
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        ACAutomation ac = new ACAutomation();
        ac.insert("dhe");
        ac.insert("he");
        ac.insert("abcdheks");
        ac.insert("aaaaaa");
        // 设置fail指针
        ac.build();

        List<String> contains = ac.containWords("abcdhekskdjfafhasldkflskdjhwqaeruv");
        for (String word : contains) {
            System.out.println(word);
        }

        System.out.println("test end");
    }
}
