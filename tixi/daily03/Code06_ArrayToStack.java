package tixi.daily03;

import java.util.Stack;

public class Code06_ArrayToStack {
    public static class MyStack {
        public MyStack(int limit) {
            data_ = new int[limit];
            limit_ = limit;
            size_ = 0;
        }

        public void push(int value) {
            if (limit_ == size_) {
                throw new RuntimeException("栈满了, 不能再加了");
            }

            data_[size_++] = value;
        }

        public int pop() {
            if (size_ == 0) {
                throw new RuntimeException("栈空了, 不能再拿了");
            }

            return data_[--size_];
        }

        public boolean isEmpty() {
            return size_ == 0;
        }

        public int size() {
            return size_;
        }

        private int[] data_;
        private int limit_;
        private int size_;
    }

    public static void main(String[] args) {
        System.out.println("test start");
        int test_times = 100000;
        int one_test_data_num = 100;
        int max_value = 1000;
        boolean success = true;
        for (int i = 0; i < test_times; i++) {
            MyStack my_stack = new MyStack(one_test_data_num);
            Stack<Integer> stack = new Stack<>();
            for (int j = 0; j < one_test_data_num; j++) {
                int nums = (int)(Math.random() * max_value);
                try {
                    if (stack.isEmpty()) {
                        my_stack.push(nums);
                        stack.push(nums);
                    } else {
                        if (Math.random() < 0.5) {
                            my_stack.pop();
                            stack.pop();
                        } else {
                            if (my_stack.pop() != stack.pop()) {
                                System.out.println(my_stack.isEmpty());
                                System.out.println(stack.size());
                                success = false;
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    if (my_stack.size() != stack.size()) {
                        success = false;
                        break;
                    }
                }
            }
        }

        System.out.println(success ? "success": "failed");
        System.out.println("test end");
    }
}
