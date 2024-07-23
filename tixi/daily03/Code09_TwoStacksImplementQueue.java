package tixi.daily03;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Code09_TwoStacksImplementQueue {
    public static class TwoStacksQueue {
        private Stack<Integer> pushStack;
        private Stack<Integer> popStack;

        public TwoStacksQueue() {
            pushStack = new Stack<>();
            popStack = new Stack<>();
        }

        public void add(int val) {
            pushStack.push(val);
        }

        public int poll() {
            if (popStack.empty() && pushStack.empty()) {
                throw new RuntimeException("queue is empty");
            }

            if (popStack.empty()) {
                pushToPop();
            }

            return popStack.pop();
        }

        public int peek() {
            if (popStack.empty() && pushStack.empty()) {
                throw new RuntimeException("queue is empty");
            }

            if (popStack.empty()) {
                pushToPop();
            }

            return popStack.peek();
        }

        private void pushToPop() {
            while (!pushStack.empty()) {
                popStack.push(pushStack.pop());
            }
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
        int testTimes = 100000;
        int oneTestDataNum = 100;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            TwoStacksQueue myQueue = new TwoStacksQueue();
            Queue<Integer> queue = new LinkedList<>();
            for (int j = 0; j < oneTestDataNum; j++) {
                int nums = (int)(Math.random() * 1000);
                if (queue.isEmpty()) {
                    myQueue.add(nums);
                    queue.offer(nums);
                } else {
                    if (Math.random() < 0.5) {
                        myQueue.poll();
                        queue.poll();
                    } else {
                        if (!isEqual(myQueue.peek(), queue.peek())) {
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
