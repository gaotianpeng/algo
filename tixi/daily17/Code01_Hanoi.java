package tixi.daily17;

import java.util.Stack;

public class Code01_Hanoi {
    public static void hanio1(int n) {
        leftToRight(n);
    }

    public static void leftToRight(int n) {
        if (n == 1) {
            System.out.println("Move 1 form left to right");
            return;
        }

        leftToMid(n - 1);
        System.out.println("Move " + n + " from left to right");
        midToRight(n - 1);
    }

    public static void leftToMid(int n) {
        if (n == 1) {
            System.out.println("Move 1 from left to mid");
            return;
        }
        leftToRight(n - 1);
        System.out.println("Move " + n + " from left to mid");
        rightToMid(n - 1);
    }

    public static void rightToMid(int n) {
        if (n == 1) {
            System.out.println("Move 1 from right to mid");
            return;
        }
        rightToLeft(n - 1);
        System.out.println("Move " + n + " from right to mid");
        leftToMid(n - 1);
    }

    public static void midToRight(int n) {
        if (n == 1) {
            System.out.println("Move 1 form mid to right");
            return;
        }

        midToLeft(n - 1);
        System.out.println("Move " + n  + " form mid to right");
        leftToRight(n - 1);
    }

    public static void midToLeft(int n) {
        if (n == 1) {
            System.out.println("Move 1 from mid to left");
            return;
        }

        midToRight(n - 1);
        System.out.println("Move " + n + " from mid to left");
        rightToLeft(n - 1);
    }

    public static void rightToLeft(int n) {
        if (n == 1) {
            System.out.println("Move 1 from right to left");
            return;
        }

        rightToMid(n - 1);
        System.out.println("Move " + n + " from right to left");
        midToLeft(n - 1);
    }

    public static void hanoi2(int n) {
        if (n > 0) {
            func(n, "left" , "right", "mid");
        }
    }

    public static void func(int n, String from, String to, String other) {
        if (n == 1) {
            System.out.println("Move 1 form " + from + " to " + to);
        } else {
            func(n - 1, from, other, to);
            System.out.println("Move " + n + " from " +  from + " to " + to);
            func(n - 1, other, to, from);
        }
    }

    public static class Record {
        public boolean finish;
        public int base;
        public String from;
        public String to;
        public String other;

        public Record(boolean f1, int b, String f, String t, String o) {
            finish = false;
            base = b;
            from = f;
            to = t;
            other = o;
        }
    }

    public static void hanoi3(int n) {
        if (n < 1) {
            return;
        }

        Stack<Record> stack = new Stack<>();
        stack.add(new Record(false, n, "left", "right", "mid"));
        while (!stack.isEmpty()) {
            Record cur = stack.pop();
            if (cur.base == 1) {
                System.out.println("Move 1 form " + cur.from + " to " + cur.to);
                if (!stack.isEmpty()) {
                    stack.peek().finish = true;
                }
            } else {
                if (!cur.finish) {
                    stack.push(cur);
                    stack.push(new Record(false, cur.base - 1, cur.from, cur.other, cur.to));
                } else {
                    System.out.println("Move " + cur.base + " from " + cur.from + " to " + cur.to);
                    stack.push(new Record(false, cur.base - 1, cur.other, cur.to, cur.from));
                }
            }
        }
    }

    public static void main(String[] args) {
        int n = 3;
        hanio1(n);
        System.out.println("---------------------");
        hanoi2(n);
        System.out.println("----------------------");
        hanoi3(n);
    }
}
