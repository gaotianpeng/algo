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
        Node small_head = null;
        Node small_tail = null;
        Node equal_head = null;
        Node equal_tail = null;
        Node large_head = null;
        Node large_tail = null;

        Node next = null;
        while (head != null) {
            next = head.next;
            head.next = null;
            if (head.value < pivot) {
                if (small_head == null) {
                    small_head = head;
                    small_tail = head;
                } else {
                    small_tail.next = head;
                    small_tail = small_tail.next;
                }
            } else if (head.value == pivot) {
                if (equal_head == null) {
                    equal_head = head;
                    equal_tail = head;
                } else {
                    equal_tail.next = head;
                    equal_tail = equal_tail.next;
                }
            } else {
                if (large_head == null) {
                    large_head = head;
                    large_tail = head;
                } else {
                    large_tail.next = head;
                    large_tail = large_tail.next;
                }
            }
            head = next;
        }

        if (small_tail != null) {
            small_tail.next = equal_head;
            equal_tail = equal_tail == null ? small_tail : equal_tail;
        }

        if (equal_tail != null) {
            equal_tail.next = large_head;
        }

        return small_head != null ? small_head : (equal_head != null ? equal_head: large_head);
    }

    /*
        for test
     */
    public static Node test(Node head, int pivot) {
        if (head == null || head.next == null) {
            return head;
        }

        ArrayList<Node> sorted_list = new ArrayList<>();
        Node cur = head;
        while (cur != null) {
            if (cur.value < pivot) {
                sorted_list.add(cur);
            }
            cur = cur.next;
        }

        cur = head;
        while (cur != null) {
            if (cur.value == pivot) {
                sorted_list.add(cur);
            }
            cur = cur.next;
        }

        cur = head;
        while (cur != null) {
            if (cur.value > pivot) {
                sorted_list.add(cur);
            }
            cur = cur.next;
        }

        Node prev = sorted_list.get(0);
        for (int i = 1; i < sorted_list.size(); i++) {
            Node node = sorted_list.get(i);
            prev.next = node;
            prev = node;
            prev.next = null;
        }
        return sorted_list.get(0);
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
        int test_times = 1000000;
        int max_val = 10;
        int max_len = 30;
        boolean success = true;
        for (int i = 0; i < test_times; i++) {
            Node head1 = generateRandomLinkedList(max_val, max_len);
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
