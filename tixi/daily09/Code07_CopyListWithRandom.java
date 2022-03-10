package tixi.daily09;
/*
    一种特殊的单链表节点类描述如下
    class Node {
        int value;
        Node next;
        Node rand;
        Node(int val) { value = val; }
    }
    rand指针是单链表节点结构中新增的指针，rand可能指向链表中的任意一个节点，也可能指向null。
    给定一个由Node节点类型组成的无环单链表的头节点 head，请实现一个函数完成这个链表的复制，并返回复制的新链表的头节点。
    【要求】
        时间复杂度O(N)，额外空间复杂度O(1)
 */

import java.util.ArrayList;
import java.util.HashMap;

public class Code07_CopyListWithRandom {
    public static class Node {
        public int val;
        public Node next;
        public Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public static Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }

        Node cur = head;
        Node next = null;
        // 1 -> 2 -> 3 -> null
        // 1 -> 1' -> 2 -> 2' -> 3 -> 3' -> null
        while (cur != null) {
            next = cur.next;
            cur.next = new Node(cur.val);
            cur.next.next = next;
            cur = next;
        }
        cur = head;
        Node copy = null;
        // 1 1' 2 2' 3 3'
        // 依次设置 1' 2' 3' random 指针
        while (cur != null) {
            next = cur.next.next;
            copy = cur.next;
            copy.random = cur.random != null ? cur.random.next : null;
            cur = next;
        }

        Node res = head.next;
        cur = head;
        // 老新混在一起，next 方向上, random正确
        // next方向上，把新老链表分离
        while (cur != null) {
            next = cur.next.next;
            copy = cur.next;
            copy.next = next;
            copy.next = next != null ? next.next : null;
            cur = next;
        }
        return res;
    }

    public static Node test(Node head) {
        HashMap<Node, Node> map = new HashMap<>();
        Node cur = head;
        while (cur != null) {
            map.put(cur, new Node(cur.val));
            cur = cur.next;
        }

        cur = head;
        while (cur != null) {
            map.get(cur).next = map.get(cur.next);
            map.get(cur).random = map.get(cur.random);
            cur = cur.next;
        }

        return map.get(head);
    }

    /*
        for test
     */
    public static Node generateRandomLinkedList(int max_val, int max_len) {
        int len = (int)(Math.random()*(max_len + 1));
        if (len == 0) {
            return null;
        }

        ArrayList<Node> array_list = new ArrayList<>();
        for (int i = 0; i < len; ++i) {
            Node node = new Node((int)(Math.random()*(max_val + 1)));
            array_list.add(node);
        }

        for (int i = 0; i < array_list.size(); i++) {
            double p = Math.random();
            if (p < 0.6) {
                array_list.get(i).random = array_list.get((int)(Math.random() * len));
            }
        }

        for (int i = 0; i < array_list.size() - 1; i++) {
            array_list.get(i).next = array_list.get(i+1);
        }

        return array_list.get(0);
    }

    public static void printRandomLinkedList(Node head) {
        Node cur = head;
        while (cur != null) {
            String str = cur.random == null ? "(null)": "("+Integer.toString(cur.random.val)+")";
            System.out.print(cur.val + str + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    public static boolean isEqual(Node head1, Node head2) {
        ArrayList<Node> list1 = new ArrayList<>();
        ArrayList<Node> list2 = new ArrayList<>();
        Node cur = head1;
        while (cur != null) {
            list1.add(cur);
            cur = cur.next;
        }

        cur = head2;
        while (cur != null) {
            list2.add(cur);
            cur = cur.next;
        }

        if (list1.size() != list1.size()) {
            return false;
        }

        for (int i = 0; i < list1.size(); i++) {
            if (list1.get(i).val != list2.get(i).val) {
                return false;
            }

            if (list1.get(i).random == null && list2.get(i).random != null) {
                return false;
            }

            if (list1.get(i).random != null && list2.get(i).random == null) {
                return false;
            }

            if (list1.get(i).random == null && list2.get(i).random == null) {
                continue;
            }

            if (list1.get(i).random.val != list2.get(i).random.val) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int max_val = 20;
        int max_len = 30;
        int test_times = 1000000;
        boolean success = true;
        for (int i = 0; i < test_times; i++) {
            Node head = generateRandomLinkedList(max_val, max_len);
            Node new_head1 = test(head);
            Node new_head2 = copyRandomList(head);
            if (!isEqual(new_head1, new_head2)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success": "failed");
        System.out.println("test end");
    }
}
