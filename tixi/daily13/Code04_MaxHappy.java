package tixi.daily13;

import java.util.ArrayList;
import java.util.List;

/*
    派对的最大快乐值
        这个公司现在要办party，你可以决定哪些员工来，哪些员工不来，规则：
        1.如果某个员工来了，那么这个员工的所有直接下级都不能来
        2.派对的整体快乐值是所有到场员工快乐值的累加
        3.你的目标是让派对的整体快乐值尽量大
        给定一棵多叉树的头节点boss，请返回派对的最大快乐值
 */
public class Code04_MaxHappy {

    public static class Employee {
        public int happy;
        public List<Employee> nexts;

        public Employee(int h) {
            happy = h;
            nexts = new ArrayList<>();
        }
    }

    public static int maxHappy(Employee boss) {
        Info all = process(boss);
        return Math.max(all.no, all.yes);
    }

    private static Info process(Employee e) {
        if (e == null) {
            return new Info(0, 0);
        }

        int no = 0;
        int yes = e.happy;
        for (Employee next: e.nexts) {
            Info nextInfo = process(next);
            no += Math.max(nextInfo.no, nextInfo.yes);
            yes += nextInfo.no;
        }

        return new Info(no, yes);
    }

    private static class Info {
        public int no;
        public int yes;

        public Info(int n, int y) {
            no = n;
            yes = y;
        }
    }
    /*
        for test
     */
    public static int test(Employee boss) {
        if (boss == null) {
            return 0;
        }
        return process1(boss, false);
    }
    /*
        cur: 当前来到节点cur
        up: 表示cur的上级是否来
            true: 表示cur上级已经确定来的情况下, cur整棵树能够提供最大快乐值是多少
            false: 表示cur上级已经确定不来的情况下, cur整棵树能够提供最大快乐值是多少
     */
    private static int process1(Employee cur, boolean up) {
        if (up) {   // cur的上级来, cur没得选,只能不来
            int ans = 0;
            for (Employee next : cur.nexts) {
                ans += process1(next, false);
            }
            return ans;
        } else {    // cur的上级不来, cur可以选来也可以选择不来
            int p1 = cur.happy;
            int p2 = 0;
            for (Employee next: cur.nexts) {
                p1 += process1(next, true);
                p2 += process1(next, false);
            }
            return Math.max(p1, p2);
        }
    }

    public static Employee generateBoss(int maxLevel, int maxNexts, int maxHappy) {
        if (Math.random() < 0.2) {
            return null;
        }

        Employee boss = new Employee((int)(Math.random() * (maxHappy + 1)));
        generateNexts(boss, 1, maxLevel, maxNexts, maxHappy);
        return boss;
    }

    public static void generateNexts(Employee e, int level, int maxLevel, int maxNexts, int maxHappy) {
        if (level > maxLevel) {
            return;
        }
        int next_size = (int)(Math.random() * (maxNexts + 1));
        for (int i = 0; i < next_size; i++) {
            Employee next = new Employee((int)(Math.random()*(maxHappy + 1)));
            e.nexts.add(next);
            generateNexts(next, level + 1, maxLevel, maxNexts, maxHappy);
        }
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int maxLevel = 4;
        int testTimes = 10000;
        int maxNexts = 10;
        int maxHappy = 100;
        for (int i = 0; i < testTimes; i++) {
            Employee boss = generateBoss(maxLevel, maxNexts, maxHappy);
            if (maxHappy(boss) != test(boss)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
