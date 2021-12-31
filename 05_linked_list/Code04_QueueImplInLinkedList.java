package list;

import java.util.LinkedList;
import java.util.Queue;

public class Code04_QueueImplInLinkedList {
    public static class Node<T> {
        public T value;
        public Node<T> last;
        public Node<T> next;

        public Node(T val) {
            value = val;
            last = null;
            next = null;
        }
    }


    public static class DoubleEndsQueue<T> {
        public Node<T> head;
        public Node<T> tail;

        public void addFromHead(T value) {
            Node<T> cur = new Node<>(value);
            if (head == null) {
                head = cur;
                tail = cur;
            } else {
                cur.next = head;
                head.last = cur;
                head = cur;
            }
        }

        public void addFromEnd(T value) {
            Node<T> cur = new Node<>(value);
            if (tail == null) {
                head = cur;
                tail = cur;
            } else {
                tail.next = cur;
                cur.last = tail;
                tail = cur;
            }
        }

        public T popFromHead() {
            if (head == null) {
                return null;
            }

            Node<T> cur = head;
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                head = head.next;
                head.last = null;
                cur.next = null;
            }

            return cur.value;
        }

        public T popFromEnd() {
            if (head == null) {
                return null;
            }

            Node<T> cur = tail;
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                tail = tail.last;
                tail.next = null;
                cur.last = null;
            }

            return cur.value;
        }

        public boolean isEmpty() {
            return head == null;
        }
    }

    public static class MyQueue<T> {
        private DoubleEndsQueue<T> queue_;

        public MyQueue() {
            queue_ = new DoubleEndsQueue<>();
        }

        public void push(T value) {
            queue_.addFromHead(value);
        }

        public T poll() {
            return queue_.popFromEnd();
        }

        public boolean isEmpty() {
            return queue_.isEmpty();
        }
    }

    public static boolean isEqual(Integer o1, Integer o2) {
        if (o1 == null && o2 != null) {
            return false;
        }

        if (o1 != null && o2 == null) {
            return false;
        }

        if (o1 == null && o2 == null) {
            return true;
        }

        return o1.equals(o2);
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int test_times = 100000;
        int one_test_data_num = 100;
        int max_value = 1000;
        boolean success = true;
        for (int i = 0; i < test_times; i++) {
            MyQueue<Integer> my_queue = new MyQueue<>();
            Queue<Integer> queue = new LinkedList<>();
            for (int j = 0; j < one_test_data_num; j++) {
                int nums = (int)(Math.random() * 1000);
                if (queue.isEmpty()) {
                    my_queue.push(nums);
                    queue.offer(nums);
                } else {
                    if (Math.random() < 0.5) {
                        my_queue.push(nums);
                        queue.offer(nums);
                    } else {
                        if (!isEqual(my_queue.poll(), queue.poll())) {
                            success = false;
                            break;
                        }
                    }
                }
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
