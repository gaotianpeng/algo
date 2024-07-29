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

        List<DoubleNode> linkList = new ArrayList<>();
        while (head != null) {
            linkList.add(head);
            head = head.next;
        }

        int listLen = linkList.size();
        if (listLen == 1) {
            return head;
        }

        for (int i = listLen - 1; i > 0; i--) {
            linkList.get(i).next = linkList.get(i-1);
            linkList.get(i-1).last = linkList.get(i);
        }

        linkList.get(0).next = null;
        linkList.get(listLen - 1).last = null;
        return linkList.get(listLen - 1);
    }

    public static DoubleNode generateRandomDoubleLinkedList(int maxLen, int maxVal) {
        int listLen = (int)(Math.random()*(maxLen + 1));
        if (listLen == 0) {
            return null;
        }

        DoubleNode head = new DoubleNode((int)(Math.random()*(maxVal + 1)));
        DoubleNode prev = head;

        while (listLen != 0) {
            DoubleNode node = new DoubleNode((int)(Math.random()*(maxVal + 1)));
            prev.next = node;
            node.last = prev;
            prev = node;
            listLen--;
        }

        return head;
    }

    public static DoubleNode copyDoubleLinkedList(DoubleNode head) {
        if (head == null) {
            return null;
        }

        List<DoubleNode> newList = new ArrayList<>();
        while (head != null) {
            newList.add(new DoubleNode(head.value));
            head = head.next;
        }

        int new_list_size = newList.size();
        for (int i = 0; i < new_list_size - 1; i++) {
            newList.get(i).next = newList.get(i+1);
            newList.get(i+1).last = newList.get(i);
        }

        newList.get(0).last = null;
        newList.get(new_list_size-1).next = null;
        return newList.get(0);
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
        int testTimes = 1000000;
        int maxVal = 40;
        int maxLen = 30;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            DoubleNode node1 = generateRandomDoubleLinkedList(maxLen, maxVal);
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
