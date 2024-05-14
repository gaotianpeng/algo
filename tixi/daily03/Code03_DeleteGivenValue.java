package tixi.daily03;

import java.util.List;
import java.util.ArrayList;

public class Code03_DeleteGivenValue {
    public static class Node {
        public int value;
        public Node next;

        public Node(int val) {
            value = val;
            next = null;
        }
    }

    public static Node deleteGivenValue(Node head, int num) {
        while (head != null) {
            if (head.value != num) {
                break;
            }
            head = head.next;
        }

        Node prev = head;
        Node cur = head;
        while (cur != null) {
            if (cur.value == num) {
                prev.next = cur.next;
            } else {
                prev = cur;
            }
            cur = cur.next;
        }

        return head;
    }

    public static Node test(Node head, int value) {
        if (head == null) {
            return null;
        }

        List<Node> link_list = new ArrayList<>();
        while (head != null) {
            if (head.value != value) {
                link_list.add(head);
            }
            head = head.next;
        }

        if (link_list.size() == 0) {
            return null;
        }

        for (int i = 0; i < link_list.size() - 1; i++) {
            link_list.get(i).next = link_list.get(i+1);
        }

        link_list.get(link_list.size() - 1).next = null;
        return link_list.get(0);
    }

    public static Node generateRandomLinkedList(int max_len, int max_value) {
        int list_len = (int)(Math.random() * (max_len+ 1));
        if (list_len == 0) {
            return null;
        }

        Node head = new Node((int)(Math.random()*(max_value+1)));
        Node prev = head;
        while (list_len != 0) {
            Node node = new Node((int)(Math.random()*(max_value+1)));
            prev.next = node;
            prev = node;
            list_len--;
        }

        return head;
    }

    public static Node copyLinkedList(Node head) {
        if (head == null) {
            return null;
        }

        Node prev = new Node(head.value);
        Node ret_node = prev;
        Node cur = head.next;
        while (cur != null) {
            Node node = new Node(cur.value);
            prev.next = node;
            cur = cur.next;
            prev = node;
        }

        return ret_node;
    }

    public static void printLinkedList(Node head) {
        while (head != null) {
            System.out.print(head.value + "->");
            head = head.next;
        }
        System.out.println("null");
    }

    public static boolean checkTwoLinkedListIsEqual(Node head1, Node head2) {
        if (head1 == head2) {
            return true;
        }

        boolean ret = true;
        while (head1 != null && head2 != null) {
            if (head1.value != head2.value) {
                ret = false;
            }
            head1 = head1.next;
            head2 = head2.next;
        }

        if (head1 != null || head2 != null) {
            ret = false;
        }

        return ret;
    }

    public static void main(String[] args) {
        System.out.println("test start ...");
        int test_times = 1000000;
        int max_value = 40;
        int max_len = 30;

        boolean success = true;
        for (int i = 0; i < test_times; i++) {
            Node node1 = generateRandomLinkedList(max_len, max_value);
            Node node2 = copyLinkedList(node1);

            int delete_val = (int)(Math.random()*(max_value + 1));
            Node node3 = deleteGivenValue(node1, delete_val);
            Node node4 = test(node2, delete_val);

            if (!checkTwoLinkedListIsEqual(node3, node4)) {
                printLinkedList(node1);
                printLinkedList(node3);
                printLinkedList(node4);
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
