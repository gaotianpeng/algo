package list;

import java.util.ArrayList;
import java.util.Stack;

/*
    给定一个单链表的头节点head，请判断该链表是否为回文结构
 */
public class Code14_isPalindromeList {
    public static class Node {
        public int value;
        public Node next;

        public Node(int val) {
            value = val;
            next = null;
        }
    }

    // need n extra space
    public static boolean isPalindrome(Node head) {
        if (head == null || head.next == null) {
            return true;
        }

        Stack<Node> stack = new Stack<>();
        Node cur = head;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }

        cur = head;
        while (!stack.isEmpty()) {
            if (stack.pop().value != cur.value) {
                return false;
            }
            cur = cur.next;
        }

        return true;
    }

    // need n/2 extra space
    public static boolean isPalindrome1(Node head) {
        if (head == null || head.next == null) {
            return true;
        }

        Node right = head.next;
        Node cur = head;
        while (cur.next != null && cur.next.next != null) {
            right = right.next;
            cur = cur.next.next;
        }

        Stack<Node> stack = new Stack<>();
        while (right != null) {
            stack.push(right);
            right = right.next;
        }

        cur = head;
        while (!stack.isEmpty()) {
            if (cur.value != stack.pop().value) {
                return false;
            }
            cur = cur.next;
        }

        return true;
    }

    /*
        for test
    */
    public static boolean test(Node head) {
        if (head == null || head.next == null) {
            return true;
        }

        ArrayList<Node> list_array = new ArrayList<>();
        Node cur = head;
        while (cur != null) {
            list_array.add(cur);
            cur = cur.next;
        }

        int list_size = list_array.size();
        int len = list_size / 2;
        for (int i = 0; i < len; i++) {
            if (list_array.get(i).value != list_array.get(list_size - 1 - i).value) {
                return false;
            }
        }

        return true;
    }

    public static Node generateRandomLinkList(int max_val, int max_len) {
        int linked_list_len = (int)(Math.random()*(max_len + 1));
        if (linked_list_len == 0) {
            return null;
        }

        Node head = null;
        double decision = Math.random();
        if (decision < 0.6) {
            head = genPalindromeLinkedList(max_val, linked_list_len);
        } else {
            head = genNormalLinkList(max_val, linked_list_len);
        }

        return head;
    }

    public static Node genNormalLinkList(int max_val, int len) {
        Node head = new Node((int)(Math.random()*(max_val + 1)));
        Node pre = head;

        for (int i = 1; i < len; i++) {
            Node cur = new Node((int)(Math.random()*(max_val + 1)));
            pre.next = cur;
            pre = cur;
        }

        return head;
    }

    public static Node genPalindromeLinkedList(int max_val, int len) {
        if (len == 1) {
            return new Node((int)(Math.random()*(max_val + 1)));
        }
        ArrayList<Node> array_list = new ArrayList<>();
        int palindrome_len = len / 2;
        for (int i = 0; i < palindrome_len; i++) {
            Node node = new Node((int)(Math.random()*(max_val + 1)));
            array_list.add(node);
        }

        Node head = array_list.get(0);
        Node pre = head;
        Node tail = null;
        for (int i = 1; i < array_list.size(); ++i) {
            tail = array_list.get(i);
            pre.next = tail;
            pre = tail;
        }

        if (len % 2 == 1) {
            tail = new Node((int)(Math.random()*(max_val + 1)));
            pre.next = tail;
            pre = tail;
        }

        for (int i = array_list.size() - 1; i >= 0; i--) {
            tail = new Node(array_list.get(i).value);
            pre.next = tail;
            pre = tail;
        }

        return head;
    }

    public static void printLinkList(Node head) {
        if (head == null) {
            return;
        }

        Node cur = head;
        int list_size = 0;
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = cur.next;
            ++list_size;
        }
        System.out.println();
        System.out.println("linked size:" + list_size);
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int test_times = 1000000;
        int max_val = 20;
        int max_len = 20;
        boolean success = true;

        for (int i = 0; i < test_times; ++i) {
            Node head = generateRandomLinkList(max_val, max_len);
            if (isPalindrome(head) != test(head)) {
                success = false;
                break;
            }
            if (isPalindrome1(head) != test(head)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
