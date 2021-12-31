package list;

import java.util.Stack;

public class Code08_QueueImplInStack {
    public static class QueueImplInStack {
        public QueueImplInStack() {
            stack_push_ = new Stack<>();
            stack_pop_ = new Stack<>();
        }

        public void add(int val) {
            stack_push_.push(val);
            pushToPop();
        }

        public int poll() {
            if (stack_push_.empty() && stack_pop_.empty()) {
                throw new RuntimeException("队列中无元素");
            }
            pushToPop();
            return stack_pop_.pop();
        }

        public int peek() {
            if (stack_push_.empty() && stack_pop_.empty()) {
                throw new RuntimeException("队列中无元素");
            }

            pushToPop();
            return stack_pop_.peek();
        }

        private void pushToPop() {
            if (stack_pop_.empty()) {
                while (!stack_push_.empty()) {
                    stack_pop_.push(stack_push_.pop());
                }
            }
        }

        private Stack<Integer> stack_push_;
        private Stack<Integer> stack_pop_;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int test_times = 1000000;

        for (int i = 0; i < test_times; i++) {

        }
        System.out.println("test end");
    }
}
