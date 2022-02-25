package tixi.daily03;

import java.util.PriorityQueue;
import java.util.Stack;

/*
    实现一个特殊的栈，在基本功能的基础上，再实现返回栈中最小元素的功能
    1）pop、push、getMin操作的时间复杂度都是 O(1)。
    2）设计的栈类型可以使用现成的栈结构
 */
public class Code08_GetMinStack {
    public static class MyStack {
        private Stack<Integer> data_;
        private Stack<Integer> min_;

        public MyStack() {
            data_ = new Stack<>();
            min_ = new Stack<>();
        }

        public void push(int new_num) {
            if (min_.isEmpty() || new_num < min_.peek()) {
                min_.push(new_num);
            } else {
                min_.push(min_.peek());
            }
        }

        public int pop() {
            if (data_.empty()) {
                throw new RuntimeException("Your stack is empty.");
            }

            min_.pop();
            return data_.pop();
        }

        public int getMin() {
            if (min_.isEmpty()) {
                throw new RuntimeException("Your stack is empty.");
            }

            return min_.peek();
        }
    }

    public static class MyStackTest {
        private Stack<Integer> stack_;
        private PriorityQueue<Integer> queue_;
        public MyStackTest() {
            stack_ = new Stack<>();
            queue_ = new PriorityQueue<>();
        }

        public void push(int new_num) {
            stack_.push(new_num);
            queue_.add(new_num);
        }

        public int pop() {
            if (stack_.isEmpty()) {
                throw new RuntimeException("当前栈为空，无法弹出数据");
            }

            int ret = stack_.pop();
            queue_.remove(ret);
            return ret;
        }

        public int getMin() {
            if (stack_.isEmpty()) {
                throw new RuntimeException("当前栈为空，没有最小值");
            }

            return queue_.peek();
        }
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int test_times = 1000000;
        int one_time_max_num = 100;
        int max_val = 100;
        boolean success = true;

        for (int i = 0; i < test_times; i++) {
            MyStack stack = new MyStack();
            MyStackTest my_stack = new MyStackTest();
            int nums = (int)(Math.random() * max_val);
            if (Math.random() < 0.5 ) {
                stack.push(nums);
                my_stack.push(nums);
                if (stack.getMin() != my_stack.getMin()) {
                    success = false;
                    break;
                }
            } else {
                try {
                    stack.pop();
                    my_stack.pop();
                    if (stack.getMin() != my_stack.getMin()) {
                        success = false;
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
