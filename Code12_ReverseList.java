package AlgoNew;

import java.util.ArrayList;
import java.util.List;

public class Code12_ReverseList {
    public static class Node {
        public int value;
        public Node next;
        public Node(int val) {
            value = val;
        }
    }

    public static Node reverseLinkedList(Node head) {
        Node pre = null;
        Node next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }

        return pre;
    }

    public static Node genRandomLinkedList(int len, int value) {
        int size = (int)(Math.random() * (len + 1));
        if (size == 0) {
            return null;
        }

        size--;
        Node head = new Node((int)(Math.random() * (value + 1)));
        Node pre = head;
        while (size != 0) {
            Node cur = new Node((int)(Math.random() * (value + 1)));
            pre.next = cur;
            pre = cur;
            size--;
        }

        return head;
    }

    public static Node testReverseLinkedList(Node head) {
        if (head == null) {
            return null;
        }

        ArrayList<Node> list = new ArrayList<>();
        while (head != null) {
            list.add(head);
            head = head.next;
        }
        list.get(0).next = null;
        int N = list.size();
        for (int i = 0; i < N; ++i) {
            list.get(i).next = list.get(i - 1);
        }

        return list.get(N - 1);
    }

    public static List<Integer> getLinkedListOriginOrder(Node head) {
        List<Integer> ans = new ArrayList<>();
        while (head != null) {
            ans.add(head.value);
            head = head.next;
        }

        return ans;
    }

    public static boolean checkLinkedListReverse(List<Integer> origin, Node head) {
        for (int i = origin.size() - 1; i >= 0; i--) {
            if (!origin.get(i).equals(head.value)) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    public static void main(String[] args) {
        int len = 50;
        int value = 100;
        int testTime = 100000;
        for (int i = 0; i < testTime; ++i) {
            Node node1 = genRandomLinkedList(len, value);
            List<Integer> list1 = getLinkedListOriginOrder(node1);
            node1 = reverseLinkedList(node1);
            if (!checkLinkedListReverse(list1, node1)) {
                System.out.println("failed");
            }
        }

        System.out.println("test finished!!");
    }
}
