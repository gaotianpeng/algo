package tixi.daily03;

import java.util.ArrayList;
import java.util.List;

public class Code02_ReverseList2 {
    public static class DoubleNode {
        public int value;
        public DoubleNode last;
        public DoubleNode next;

        public DoubleNode(int data) {
            value = data;
            last = null;
            next = null;
        }
    }

    public static DoubleNode reverseDoubleList(DoubleNode head) {
        DoubleNode prev = null;
        DoubleNode next = null;

        while (head != null) {
            next = head.next;
            head.next = prev;
            head.last = next;
            prev = head;
            head = next;
        }

        return prev;
    }

    public static DoubleNode test(DoubleNode head) {
        if (head == null) {
            return null;
        }

        List<DoubleNode> link_list = new ArrayList<>();
        while (head != null) {
            link_list.add(head);
            head = head.next;
        }

        int list_len = link_list.size();
        if (list_len == 1) {
            return head;
        }

        for (int i = list_len - 1; i > 0; i--) {
            link_list.get(i).next = link_list.get(i-1);
            link_list.get(i-1).last = link_list.get(i);
        }

        link_list.get(0).next = null;
        link_list.get(list_len - 1).last = null;
        return link_list.get(list_len - 1);
    }

    public static DoubleNode generateRandomDoubleLinkedList(int max_len, int max_value) {
        int list_len = (int)(Math.random()*(max_len + 1));
        if (list_len == 0) {
            return null;
        }

        DoubleNode head = new DoubleNode((int)(Math.random()*(max_value + 1)));
        DoubleNode prev = head;

        while (list_len != 0) {
            DoubleNode node = new DoubleNode((int)(Math.random()*(max_value + 1)));
            prev.next = node;
            node.last = prev;
            prev = node;
            list_len--;
        }

        return head;
    }

    public static DoubleNode copyDoubleLinkedList(DoubleNode head) {
        if (head == null) {
            return null;
        }

        List<DoubleNode> new_list = new ArrayList<>();
        while (head != null) {
            new_list.add(new DoubleNode(head.value));
            head = head.next;
        }

        int new_list_size = new_list.size();
        for (int i = 0; i < new_list_size - 1; i++) {
            new_list.get(i).next = new_list.get(i+1);
            new_list.get(i+1).last = new_list.get(i);
        }

        new_list.get(0).last = null;
        new_list.get(new_list_size-1).next = null;
        return new_list.get(0);
    }

    public static boolean checkDoubleLinkedListIsEqual(DoubleNode head1, DoubleNode head2) {
        if (head1 == head2) {
            return true;
        }

        while (head1 != null && head2 != null) {
            if (head1.value != head2.value) {
                return false;
            }
            head1 = head1.next;
            head2 = head2.next;
        }

        if (head1 != null || head2 != null) {
            return false;
        }

        return true;
    }

    public static void printDoubleLinkedList(DoubleNode head) {
        if (head == null) {
            System.out.println("null");
            return;
        }

        System.out.print("null<->");
        while (head != null) {
            System.out.print(head.value + "<->");
            head = head.next;
        }

        System.out.println("null");
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int test_times = 1000000;
        int max_val = 40;
        int max_len = 30;
        boolean success = true;
        for (int i = 0; i < test_times; i++) {
            DoubleNode node1 = generateRandomDoubleLinkedList(max_len, max_val);
            DoubleNode node2 = copyDoubleLinkedList(node1);

            DoubleNode reverse_node1 = reverseDoubleList(node1);
            DoubleNode reverse_node2 = test(node2);
            if (!checkDoubleLinkedListIsEqual(reverse_node1, reverse_node2)) {
                printDoubleLinkedList(node1);
                printDoubleLinkedList(node2);
                printDoubleLinkedList(reverse_node1);
                printDoubleLinkedList(reverse_node2);
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "faield");
        System.out.println("test end");
    }
}
