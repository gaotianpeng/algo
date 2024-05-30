package tixi.daily09;

import java.util.ArrayList;

// 输入链表头节点，奇数长度返回中点，偶数长度返回下中点
public class Code02_MidOrDownNode {
    public static class Node {
        public int value;
        public Node next;

        public Node(int v) {
            value = v;
            next = null;
        }
    }

    public static Node getDownMidNode(Node head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node slow = head.next;
        Node fast = head.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    /*
        for test
     */
    public static Node test(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get(arr.size() / 2);
    }

    public static Node generateRandomLinkedList(int max_val, int max_len) {
        int link_len = (int)(Math.random() * (max_len + 1));
        if (link_len == 0) {
            return null;
        }

        ArrayList<Node> list = new ArrayList<>();
        for(int i = 0; i < link_len; i++) {
            Node node = new Node((int)(Math.random()*(max_val + 1)));
            list.add(node);
        }
        Node head = list.get(0);
        Node pre = head;
        for (int i = 1; i < list.size() - 1; i++) {
            pre.next = list.get(i);
            pre = list.get(i);
        }

        return head;
    }

    public static void printRandomLinkedList(Node head) {
        if (head == null) {
            return;
        }

        Node cur = head;
        while (cur != null) {
            System.out.print(cur.value + "->");
            cur = cur.next;
        }
        System.out.println("null");
    }

    public static boolean isNodeEqual(Node node1, Node node2) {
        if (node1 == null && node2 == null) {
            return true;
        }

        if (node1 != null && node2 == null) {
            return false;
        }

        if (node1 == null && node2 != null) {
            return false;
        }

        if (node1.value == node2.value) {
            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int max_val = 100;
        int max_len = 30;
        int test_times = 1000000;

        for (int i = 0; i < test_times; i++) {
            Node head = generateRandomLinkedList(max_val, max_len);
            Node node1 = getDownMidNode(head);
            Node node2 = test(head);
            if (!isNodeEqual(node1, node2)) {
                printRandomLinkedList(head);
                System.out.println(node1.value);
                System.out.println(node2.value);
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
