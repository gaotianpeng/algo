package list;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Code06_QueueImplInArray {
    public static class MyQueue {
        public MyQueue(int limit) {
            limit_ = limit;
            arr_ = new int[limit + 1];
            start_ = 0;
            end_ = 0;
            size_ = 0;
        }

        public void push(int value) {
            if (size_ == limit_) {
                throw new RuntimeException("队列满了, 不能再加了");
            }

            size_++;
            arr_[end_] = value;
            end_ = nextIndex(end_);
        }

        public int pop() {
            if (size_ == 0) {
                throw new RuntimeException("队列空了, 不能再拿了");
            }

            size_--;
            int ans = arr_[start_];
            start_ = nextIndex(start_);
            return ans;
        }

        public boolean isEmpty() {
            return size_ == 0;
        }

        public int size() {
            return size_;
        }

        // 如果现在的下标是i，返回下一个位置
        private int nextIndex(int i) {
            return i < limit_ - 1 ? i+1: 0;
        }

        private int [] arr_;
        private int limit_;
        private int start_;
        private int end_;
        private int size_;
    }

    public static void main(String[] args) {
        System.out.println("test start");
        int test_times = 100000;
        int one_test_data_num = 100;
        int max_value = 1000;
        boolean success = true;
        for (int i = 0; i < test_times; i++) {
            MyQueue myqueue = new MyQueue(one_test_data_num);
            Queue<Integer> queue = new LinkedList<>();
            for (int j = 0; j < one_test_data_num; j++) {
                int nums = (int)(Math.random() * max_value);
                try {
                    if (queue.isEmpty()) {
                        myqueue.push(nums);
                        queue.offer(nums);
                    } else {
                        if (Math.random() < 0.5) {
                            myqueue.pop();
                            queue.poll();
                        } else {
                            if (myqueue.pop() != queue.poll()) {
                                System.out.println(myqueue.isEmpty());
                                System.out.println(queue.size());
                                success = false;
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    if (myqueue.size() != queue.size()) {
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
