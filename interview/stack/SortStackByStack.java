package interview.stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/*
    一个栈中元素的类型为整型，现在想将该栈从顶到底按从大到小的顺序排序，只许申请一个栈。
    除此之外可以申请新的变量，但不能申请额外的数据结构。如何完成排序
 */
public class SortStackByStack {
    public static void sortStack(Stack<Integer> stack) {
        if (stack == null || stack.size() < 2) {
            return;
        }

        Stack<Integer> help = new Stack<>();
        while (!stack.isEmpty()) {
            int cur = stack.pop();
            while (!help.isEmpty() && help.peek() < cur) {
                stack.push(help.pop());
            }
            help.push(cur);
        }

        while (!help.isEmpty()) {
            stack.push(help.pop());
        }
    }

    public static void test(Stack<Integer> stack) {
        if (stack == null || stack.size() < 2) {
            return;
        }

        int[] array = new int[stack.size()];
        int i = 0;
        while (!stack.isEmpty()) {
            array[i++] = stack.pop();
        }

        Arrays.sort(array);

        for (i = 0; i < array.length; ++i) {
            stack.push(array[i]);
        }
    }

    public static Stack<Integer> generateRandomStack(int maxLen, int min, int max) {
        int len = generateRandomVal(0, maxLen);
        if (len == 0) {
            return null;
        }

        Stack<Integer> ans = new Stack<>();
        for (int i = 0; i < len; ++i) {
            ans.push(generateRandomVal(min, max));
        }

        return ans;
    }

    public static Stack<Integer> copyStack(Stack<Integer> stack) {
        if (stack == null) {
            return null;
        }

        Stack<Integer> ans = new Stack<>();
        for (int i = 0; i < stack.size(); ++i) {
            ans.push(stack.get(i));
        }

        return ans;
    }

    public static boolean isEqual(Stack<Integer> stack1, Stack<Integer> stack2) {
        if (stack1 == null && stack2 == null) {
            return true;
        }

        if ((stack1 != null && stack2 == null) || (stack1 == null && stack2 != null)) {
            return false;
        }

        if (stack1.size() != stack2.size()) {
            return false;
        }

        for (int i = 0; i < stack1.size(); ++i) {
            if (stack1.get(i) != stack2.get(i)) {
                return false;
            }
        }

        return true;
    }

    public static int generateRandomVal(int min, int max) {
        return (int)(Math.random() * (max - min + 1) + min);
    }
    public static void main(String[] args) {
        System.out.println("test start ...");
        int testTimes = 100000;
        int min = 1, max = 100;
        int maxLen = 50;
        boolean success = true;
        for (int i = 0; i < testTimes; ++i) {
            Stack<Integer> stack1 = generateRandomStack(maxLen, min, max);
            Stack<Integer> stack2 = copyStack(stack1);
            test(stack1);
            sortStack(stack2);

            if (!isEqual(stack1, stack2)) {
                success = false;
            }
        }

        System.out.println(success ? "test success":"test failed");
        System.out.println("test end");
    }
}
