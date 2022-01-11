package list;

import java.util.ArrayList;
/*
    输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个
 */
public class Code13_GetLinkedListDownPreMid {
    public static class Node {
        public int value;
        public Node next;

        public Node(int v) {
            value = v;
            next = null;
        }
    }

    public static Node getDownPreMidNode(Node head) {
        if (head == null || head.next == null) {
            return null;
        }

        if (head.next.next == null) {
            return head;
        }

        Node slow_node = head;
        Node fast_node = head.next;
        while (fast_node.next != null && fast_node.next.next != null) {
            slow_node = slow_node.next;
            fast_node = fast_node.next.next;
        }

        return slow_node;
    }

    /*
        for test
     */
    public static Node test(Node head) {
        if (head == null || head.next == null) {
            return null;
        }

        if (head.next.next == null) {
            return head;
        }

        ArrayList<Node> array_list = new ArrayList<>();
        Node cur = head;
        while (cur != null) {
            array_list.add(cur);
            cur = cur.next;
        }

        return array_list.get((array_list.size() - 2) / 2);
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
        int link_len = 0;
        while (cur != null) {
            ++link_len;
            System.out.print(cur.value + "->");
            cur = cur.next;
        }
        System.out.println("null");

        System.out.println("link_list size = " + link_len);
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
        int test_times = 10000000;

        for (int i = 0; i < test_times; i++) {
            Node head = generateRandomLinkedList(max_val, max_len);
            Node node1 = getDownPreMidNode(head);
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
