package tixi.daily03;

import java.util.Stack;

/*
    双向链表实现栈
 */
public class Code04_DoubleListToStack {

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
        private Node<T> head;
        private Node<T> tail;

        public DoubleEndsQueue() {
            head = null;
            tail = null;
        }

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
            if (tail == null) {
                return null;
            }

            Node<T> cur = tail;
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                tail = tail.last;
                tail.next = null;
                cur.next = null;
            }

            return cur.value;
        }

        public boolean isEmpty() {
            return head == null;
        }
    }

    public static class MyStack<T> {
        private DoubleEndsQueue<T> queue_;

        public MyStack() {
            queue_ = new DoubleEndsQueue<>();
        }

        public void push(T value) {
            queue_.addFromHead(value);
        }

        public T pop() {
            return queue_.popFromHead();
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
        boolean success = true;
        for (int i = 0; i < test_times; i++) {
            MyStack<Integer> my_stack = new MyStack<>();
            Stack<Integer> stack = new Stack<>();
            for (int j = 0; j < one_test_data_num; j++) {
                int nums = (int)(Math.random() * 1000);
                if (stack.isEmpty()) {
                    my_stack.push(nums);
                    stack.push(nums);
                } else {
                    if (Math.random() < 0.5) {
                        my_stack.push(nums);
                        stack.push(nums);
                    } else {
                        if (!isEqual(my_stack.pop(), stack.pop())) {
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
