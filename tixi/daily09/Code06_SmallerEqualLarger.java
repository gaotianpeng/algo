package tixi.daily09;

import java.util.ArrayList;

// 将单向链表按某值划分成左边小、中间相等、右边大的形式
public class Code06_SmallerEqualLarger {
    public static class Node {
        public int value;
        public Node next;

        public Node(int val) {
            value = val;
            next = null;
        }
    }

    public static Node reorganizeLinkedList(Node head, int pivot) {
        Node smallH = null;
        Node smallT = null;
        Node equalH = null;
        Node equalT = null;
        Node biggerH = null;
        Node biggerT = null;

        Node next = null;
        while (head != null) {
            next = head.next;
            head.next = null;
            if (head.value < pivot) {
                if (smallH == null) {
                    smallH = head;
                    smallT = head;
                } else {
                    smallT.next = head;
                    smallT = smallT.next;
                }
            } else if (head.value == pivot) {
                if (equalH == null) {
                    equalH = head;
                    equalT = head;
                } else {
                    equalT.next = head;
                    equalT = equalT.next;
                }
            } else {
                if (biggerH == null) {
                    biggerH = head;
                    biggerT = head;
                } else {
                    biggerT.next = head;
                    biggerT = biggerT.next;
                }
            }

            head = next;
        }

        if (smallT != null) {
            smallT.next = equalH;
            equalT = equalT == null ? smallT: equalT;
        }

        if (equalT != null) {
            equalT.next = biggerH;
        }

        return smallH != null ? smallH : (equalH != null? equalH: biggerH);
    }

    /*
        for test
     */
    public static Node test(Node head, int pivot) {
        if (head == null || head.next == null) {
            return head;
        }

        ArrayList<Node> sortedList = new ArrayList<>();
        Node cur = head;
        while (cur != null) {
            if (cur.value < pivot) {
                sortedList.add(cur);
            }
            cur = cur.next;
        }

        cur = head;
        while (cur != null) {
            if (cur.value == pivot) {
                sortedList.add(cur);
            }
            cur = cur.next;
        }

        cur = head;
        while (cur != null) {
            if (cur.value > pivot) {
                sortedList.add(cur);
            }
            cur = cur.next;
        }

        Node prev = sortedList.get(0);
        for (int i = 1; i < sortedList.size(); i++) {
            Node node = sortedList.get(i);
            prev.next = node;
            prev = node;
            prev.next = null;
        }
        return sortedList.get(0);
    }

    public static int getLinkListRandomPivot(Node head) {
        if (head == null) {
            return Integer.MAX_VALUE;
        }
        ArrayList<Integer> values = new ArrayList<>();
        Node cur = head;
        while (cur != null) {
            values.add(cur.value);
            cur = cur.next;
        }
        return values.get((int)(Math.random()*(values.size())));
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

    public static boolean isEqual(Node head1, Node head2, int pivot) {
        ArrayList<Integer> arr1 = new ArrayList<>();
        ArrayList<Integer> arr2 = new ArrayList<>();
        Node cur = head1;
        while (cur != null) {
            arr1.add(cur.value);
            cur = cur.next;
        }

        cur = head2;
        while (cur!= null) {
            arr2.add(cur.value);
            cur = cur.next;
        }

        if (arr1.size() != arr2.size()) {
            return false;
        }

        int start1 = -1;
        int cnt1 = 0;
        for (int i = 0; i < arr1.size(); i++) {
            if (arr1.get(i) == pivot) {
                if (start1 != -1) {
                    start1 = i;
                }
                ++cnt1;
            }
        }
        int start2 = -1;
        int cnt2 = 0;
        for (int i = 0; i < arr2.size(); i++) {
            if (arr2.get(i) == pivot) {
                if (start2 != -1) {
                    start2 = i;
                }
                ++cnt2;
            }
        }

        if (start1 != start2 || cnt1 != cnt2) {
            return false;
        }

        return true;
    }

    public static Node copyLinkedList(Node head) {
        if (head == null) {
            return null;
        }

        Node ret = new Node(head.value);
        Node cur = head.next;
        Node prev = ret;
        while (cur != null) {
            Node node = new Node(cur.value);
            prev.next = node;
            prev = node;
            cur = cur.next;
        }

        return ret;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int testTimes = 1000000;
        int maxVal = 10;
        int maxLen = 30;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            Node head1 = generateRandomLinkedList(maxVal, maxLen);
            Node head2 = copyLinkedList(head1);
            Node head3 = copyLinkedList(head1);
            int pivot = getLinkListRandomPivot(head2);
            Node new_head2 = reorganizeLinkedList(head2, pivot);
            Node new_head3 = test(head3, pivot);
            if (!isEqual(new_head2, new_head3, pivot)) {
                printRandomLinkedList(head1);
                printRandomLinkedList(new_head2);
                printRandomLinkedList(new_head3);
                System.out.println("pivot: " + pivot);
                success = false;
                break;
            }
        }

        System.out.println(success? "success":"failed");
        System.out.println("test end");
    }
}
