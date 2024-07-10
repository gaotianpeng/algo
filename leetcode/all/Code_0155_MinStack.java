package leetcode.all;

import java.util.Stack;

/*
    leetcode 155 最小栈
        设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
        实现 MinStack 类
            MinStack() 初始化堆栈对象。
            void push(int val) 将元素val推入堆栈。
            void pop() 删除堆栈顶部的元素。
            int top() 获取堆栈顶部的元素。
            int getMin() 获取堆栈中的最小元素

        提示
            -231<= val <= 231- 1
            pop、top 和 getMin 操作总是在非空栈上调用
            push,pop,top, and getMin最多被调用3 * 104次
 */
public class Code_0155_MinStack {
    class MinStack {
        private Stack<Integer> data_;
        private Stack<Integer> min_;
        public MinStack() {
            data_ = new Stack<>();
            min_ = new Stack<>();
        }

        public void push(int val) {
            if (min_.isEmpty() || min_.peek() > val) {
                min_.push(val);
            } else {
                min_.push(min_.peek());
            }
            data_.push(val);
        }

        public void pop() {
            if (data_.isEmpty()) {
                return;
            }
            data_.pop();
            min_.pop();
        }

        public int top() {
            return data_.peek();
        }

        public int getMin() {
            if (min_.isEmpty()) {
                return Integer.MAX_VALUE;
            }
            return min_.peek();
        }
    }
}
