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
        private Stack<Integer> data;
        private Stack<Integer> curMin;

        public MyStack()  {
            data = new Stack<>();
            curMin = new Stack<>();
        }

        public int pop() {
            if (data.empty()) {
                throw new RuntimeException("current stack is empty!!!");
            }

            curMin.pop();
            return data.pop();
        }

        public void push(int val) {
            data.push(val);
            if (curMin.empty()) {
                curMin.push(val);
            } else {
                int min = curMin.peek();
                if (val <= min) {
                    curMin.push(val);
                } else {
                    curMin.push(min);
                }
            }
        }

        public int getMin() {
            if (data.empty()) {
                throw new RuntimeException("current stack is empty!!!");
            }

            return curMin.peek();
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
