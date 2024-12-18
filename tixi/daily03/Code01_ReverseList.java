package tixi.daily03;

import java.util.ArrayList;
import java.util.List;

public class Code01_ReverseList {
    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            value = data;
        }
    }

    public static Node reverseLinkedList(Node head) {
        Node prev = null;
        Node next = null;
        while (head != null) {
             next = head.next;
             head.next = prev;
             prev = head;
             head = next;
        }

        return prev;
    }

    /*
        for test
     */
    public static Node test(Node head) {
        List<Node> reverse = new ArrayList<Node>();
        while (head != null) {
            reverse.add(head);
            head = head.next;
        }

        Node ret_node = null;
        if (reverse.size() > 0) {
            ret_node = reverse.get(reverse.size() - 1);
        } else {
            return null;
        }

        Node node = null;
        for (int i = reverse.size() - 1; i > 0; i--) {
            node = reverse.get(i);
            node.next = reverse.get(i-1);
        }

        node.next.next = null;
        return ret_node;
    }

    public static Node generateRandomLinkedList(int max_len, int max_value) {
        int listLen = (int)(Math.random() * (max_len+ 1));
        if (listLen == 0) {
            return null;
        }

        Node head = new Node((int)(Math.random()*(max_value+1)));
        Node prev = head;
        while (listLen != 0) {
            Node node = new Node((int)(Math.random()*(max_value+1)));
            prev.next = node;
            prev = node;
            listLen--;
        }

        return head;
    }

    public static Node copyLinkedList(Node head) {
        if (head == null) {
            return null;
        }

        Node prev = new Node(head.value);
        Node retNode = prev;
        Node cur = head.next;
        while (cur != null) {
            Node node = new Node(cur.value);
            prev.next = node;
            cur = cur.next;
            prev = node;
        }

        return retNode;
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
        System.out.println("test start...");
        int testTime = 1000000;
        int maxLen = 12;
        int maxValue = 40; 
        boolean success = true;

        for (int i = 0; i < testTime; i++) {
            Node node = generateRandomLinkedList(maxLen, maxValue);
            Node node1 = copyLinkedList(node);
            if (!checkTwoLinkedListIsEqual(node, node1)) {
                printLinkedList(node);
                printLinkedList(node1);
                success = false;
                break;
            }

            Node reverse_node = reverseLinkedList(node);
            Node reverse_node1 = test(node1);
            if (!checkTwoLinkedListIsEqual(reverse_node, reverse_node1)) {
                printLinkedList(reverse_node);
                printLinkedList(reverse_node1);
                success = false;
                break;
            }
        }

        System.out.println(success ? "success": "failed");
        System.out.println("test end");
    }
}
