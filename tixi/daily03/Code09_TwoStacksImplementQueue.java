package tixi.daily03;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Code09_TwoStacksImplementQueue {
    public static class TwoStacksQueue {
        public TwoStacksQueue() {
            push_ = new Stack<>();
            pop_ = new Stack<>();
        }

        public void add(int val) {
            push_.add(val);
            pushToPop();
        }

        public int poll() {
            if (push_.isEmpty() && pop_.isEmpty()) {
                throw new RuntimeException("Queue is empty");
            }
            pushToPop();
            return pop_.pop();
        }

        public int peek()  {
            if (push_.isEmpty() && pop_.isEmpty()) {
                throw new RuntimeException("Queue is empty");
            }
            pushToPop();
            return pop_.peek();
        }

        private void pushToPop() {
            if (pop_.isEmpty()) {
                while (!push_.isEmpty()) {
                    pop_.push(push_.pop());
                }
            }
        }

        private Stack<Integer> push_;
        private Stack<Integer> pop_;
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
            TwoStacksQueue my_queue = new TwoStacksQueue();
            Queue<Integer> queue = new LinkedList<>();
            for (int j = 0; j < one_test_data_num; j++) {
                int nums = (int)(Math.random() * 1000);
                if (queue.isEmpty()) {
                    my_queue.add(nums);
                    queue.offer(nums);
                } else {
                    if (Math.random() < 0.5) {
                        my_queue.poll();
                        queue.poll();
                    } else {
                        if (!isEqual(my_queue.peek(), queue.peek())) {
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
