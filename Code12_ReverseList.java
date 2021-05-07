package AlgoNew;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Code12_ReverseList {
    public static class Node {
        public int value;
        public Node next;
        public Node(int val) {
            value = val;
        }
    }

    public static class DoubleNode {
        public int value;
        public DoubleNode last;
        public DoubleNode next;
        public DoubleNode(int val) {
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

    // -------------------------------------
    public static DoubleNode reverseDoubleList(DoubleNode head) {
        DoubleNode pre = null;
        DoubleNode next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            head.last = next;
            pre = head;
            head = next;
        }

        return pre;
    }

    public static DoubleNode testReverseDoubleList(DoubleNode head) {
        if (head == null) {
            return null;
        }

        LinkedList<DoubleNode> list = new LinkedList<>();
        while (head != null) {
            list.add(head);
            head = head.next;
        }

        list.get(0).next = null;
        DoubleNode pre = list.get(0);
        int N = list.size();
        for (int i = 1; i < N; ++i) {
            DoubleNode cur = list.get(i);
            cur.last = null;
            cur.next = pre;
            pre.last = cur;
            pre = cur;
        }

        return list.get(N-1);
    }

    public static DoubleNode genRandomDoubleList(int len, int val) {
        int size = (int)(Math.random()*(len + 1));
        if (size == 0) {
            return null;
        }
        size--;
        DoubleNode head = new DoubleNode((int)(Math.random() * (val + 1)));
        DoubleNode pre = head;
        while (size != 0) {
            DoubleNode cur = new DoubleNode((int)(Math.random() * (val + 1)));
            pre.next = cur;
            cur.last = pre;
            pre = cur;
            size--;
        }

        return head;
    }

    public static List<Integer> getDoubleListOriginOrder(DoubleNode head) {
        List<Integer> ret = new ArrayList<>();
        while (head != null) {
            ret.add(head.value);
            head = head.next;
        }
        return ret;
    }

    public static boolean checkDoubleListReverse(List<Integer> origin, DoubleNode head) {
        DoubleNode end = null;
        for (int i = origin.size() - 1; i >= 0; i--) {
            if (!origin.get(i).equals(head.value)) {
                return false;
            }
            end = head;
            head = head.next;
        }

        for(int i = 0; i < origin.size(); i++) {
            if (!origin.get(i).equals(end.value)) {
                return false;
            }
            end = end.last;
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

            DoubleNode node3 = genRandomDoubleList(len, value);
            List<Integer> list3 = getDoubleListOriginOrder(node3);
            node3 = reverseDoubleList(node3);
            if (!checkDoubleListReverse(list3, node3)) {
                System.out.println("failed");
            }

            DoubleNode node4 = genRandomDoubleList(len, value);
            List<Integer> list4 = getDoubleListOriginOrder(node4);
            node4 = reverseDoubleList(node4);
            if (!checkDoubleListReverse(list4, node4)) {
                System.out.println("failed");
            }
        }

        System.out.println("test finished!!");
    }
}
