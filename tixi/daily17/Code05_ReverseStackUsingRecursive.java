package tixi.daily17;

import java.util.ArrayList;
import java.util.Stack;

/*
    给你一个栈，请你逆序这个栈，
    不能申请额外的数据结构，
    只能使用递归函数。 如何实现?
 */
public class Code05_ReverseStackUsingRecursive {
    public static void reverse(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return;
        }

        int i = f(stack);
        reverse(stack);;
        stack.push(i);
    }

    /*
        栈底元素移除掉
        上面的元素盖下来
        返回移除掉的栈底元素
     */
    private static int f(Stack<Integer> stack) {
        int result = stack.pop();
        if (stack.empty()) {
            return result;
        } else {
            int last = f(stack);
            stack.push(result);
            return last;
        }
    }

    /*
        for test
     */
    public static void test(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return;
        }

        ArrayList<Integer> list = new ArrayList<>(stack);
        while (!stack.isEmpty()) {
            stack.pop();
        }
        for (int i = list.size() - 1; i >= 0; i--) {
            stack.push(list.get(i));
        }
    }

    public static Stack<Integer> generateRandomStack(int max_size, int max_val) {
        Stack<Integer> ans = new Stack<>();
        int size = (int)(Math.random() * (max_size + 1));
        while (size != 0) {
            ans.push((int)(Math.random() * (max_val + 1)));
            size--;
        }

        return ans;
    }

    public static Stack<Integer> copyStack(Stack<Integer> stack) {
        Stack<Integer> ans = new Stack<>();
        if (stack == null || stack.size() == 0) {
            return ans;
        }

        ArrayList<Integer> list = new ArrayList<>(stack);
        for (Integer val: list) {
            ans.push(val);
        }
        return ans;
    }

    public static boolean isEqual(Stack<Integer> stack1, Stack<Integer> stack2) {
        if (stack1 == null && stack2 == null) {
            return true;
        }

        if (stack1 != null && stack2 == null) {
            return false;
        }

        if (stack1 == null && stack2 != null) {
            return false;
        }

        if (stack1.size() != stack2.size()) {
            return false;
        }

        while (!stack1.isEmpty()) {
            if (stack1.pop() != stack2.pop()) {
                return false;
            }
        }

        return true;
    }

    private static void print(Stack<Integer> stack) {
        if (stack == null) {
            return;
        }

        ArrayList<Integer> list = new ArrayList<>(stack);
        for (Integer val: list) {
            System.out.print(val + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int test_times = 10000;
        boolean success = true;
        int maxSize = 20;
        int maxVal = 30;
        for (int i = 0; i < test_times; i++) {
            Stack<Integer> stack1 = generateRandomStack(maxSize, maxVal);
            Stack<Integer> stack2 = copyStack(stack1);
            reverse(stack1);
            test(stack2);
            if (!isEqual(stack1, stack2)) {
                print(stack1);
                print(stack2);
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
