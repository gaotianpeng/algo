package tixi.daily30;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Code02_MorrisPreTraversal {
    public static class Node {
        public int value;
        Node left;
        Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /*
        Morris 遍历
            假设来到当前节点 cur, 开始时，cur 来到头节点位置
            1）如果 cur 没有左孩子，cur 向右移动(cur = cur.right)
            2）如果 cur 有左孩子，找到左子树上最右的节点 mostRight
                a. 如果 mostRight 的右指针指向为空，让其指向 cur，然后cur向左移动(cur = cur.left)
                b. 如果 mostRight 的右指针指向cur, 让其指向 null，然后 cur 向右移动 (cur = cur.right)
            3）cur 为空时遍历停止
     */
    public static List<Integer> morrisPre(Node head) {
        if (head == null) {
            return null;
        }

        List<Integer> ans = new LinkedList<>();
        Node cur = head;
        Node mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }

                if (mostRight.right == null) {
                    ans.add(cur.value);
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }

            } else {
                ans.add(cur.value);
            }

            cur = cur.right;
        }

        return ans;
    }


    // for test
    public static List<Integer> test(Node head) {
        if (head == null) {
            return null;
        }

        List<Integer> ans = new LinkedList<>();
        process(head, ans);
        return ans;
    }

    public static void  process(Node head, List<Integer> ans) {
        if (head == null) {
            return;
        }

        ans.add(head.value);
        process(head.left, ans);
        process(head.right, ans);
    }

    public static Node generateRandomBT(int maxLevel, int maxValue) {
        HashSet<Integer> nums = new HashSet<>();
        return generate(1, maxLevel, maxValue, nums);
    }

    public static Node generate(int level, int maxLevel, int maxValue, HashSet<Integer> nums) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }

        int val = (int)(Math.random()*maxValue);
        while (nums.contains(val)) {
            val = (int)(Math.random()*maxValue);
        }

        Node head = new Node(val);
        head.left = generate(level + 1, maxLevel, maxValue, nums);
        head.right = generate(level + 1, maxLevel, maxValue, nums);
        return head;
    }

    public static boolean isEqual(List<Integer> list1, List<Integer> list2) {
        if (list1 == null && list2 == null) {
            return true;
        }

        if (list1 == null || list2 == null) {
            return false;
        }

        if (list1.size() != list2.size()) {
            return false;
        }

        for (int i = 0; i < list1.size(); ++i) {
            if (!list1.get(i).equals(list2.get(i))) {
                return false;
            }
        }

        return true;
    }

    public static void printArray(List<Integer> list) {
        if (list == null) {
            return;
        }

        for (Integer elem: list) {
            System.out.print(elem + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int testTimes = 100000;
        int maxLevel = 5;
        int maxValue = 200;
        for (int i = 0; i < testTimes; ++i) {
            Node tree = generateRandomBT(maxLevel, maxValue);
            List<Integer> ans2 = test(tree);
            List<Integer> ans1 = morrisPre(tree);
            if (!isEqual(ans1, ans2)) {
                printArray(ans1);
                printArray(ans2);
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
