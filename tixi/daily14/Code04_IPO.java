package tixi.daily14;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/*
    输入: 正数数组costs、正数数组profits、正数K、正数M
    costs[i]表示i号项目的花费
    profits[i]表示i号项目在扣除花费之后还能挣到的钱(利润)
    K表示你只能串行的最多做k个项目
    M表示你初始的资金
    说明: 每做完一个项目，马上获得的收益，可以支持你去做下一个项目。不能并行的做项目。
    输出：你最后获得的最大钱数
 */
public class Code04_IPO {
    /*
        k : 能铸的最多项目数
        w : 初始资金
        profit : 项目收益
        cost : 项目利益

        返回做完k个项目后的最大收益
     */
    public static int findMaxProfit(int k, int w, int[] profit, int[] cost) {
        if (profit == null || cost == null || profit.length != cost.length) {
            return 0;
        }

        PriorityQueue<Program> minCostQ = new PriorityQueue<>(new MinCostComparator());
        PriorityQueue<Program> maxProfitQ = new PriorityQueue<>(new MaxProfitComparator());
        for (int i = 0; i < profit.length; i++) {
            minCostQ.add(new Program(profit[i], cost[i]));
        }

        for (int i = 0; i < k; i++) {
            while (!minCostQ.isEmpty() && minCostQ.peek().cost <= w) {
                maxProfitQ.add(minCostQ.poll());
            }
            if (maxProfitQ.isEmpty()) {
                return w;
            }
            w += maxProfitQ.poll().profit;
        }

        return w;
    }

    public static class Program {
        public int profit;
        public int cost;

        public Program(int p, int c) {
            profit = p;
            cost = c;
        }
    }

    public static class MinCostComparator implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            return o1.cost - o2.cost;
        }
    }

    public static class MaxProfitComparator implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            return o2.profit - o1.profit;
        }
    }

    // for test
    public static int test(int k, int w, int[] profit, int[] cost) {
        if (profit == null || cost == null || profit.length != cost.length) {
            return 0;
        }

        List<Program> programs = new ArrayList<>();
        for (int i = 0; i < profit.length; i++) {
            programs.add(new Program(profit[i], cost[i]));
        }

        // 按成本从小到大排序
        Collections.sort(programs, new MinCostComparator());

        /*
            在每次迭代中（最多 k 次），扫描排序后的项目列表，找到成本不超过当前资本 w 的项目中利润最大的项目
            如果找到了这样的项目，则将其利润添加到当前资本中，并将该项目从列表中移除
            如果找不到这样的项目，直接返回当前资本 w，因为无法进行更多的投资
         */
        for (int i = 0; i < k; i++) {
            int maxProfitIndex = -1;
            for (int j = 0; j < programs.size(); j++) {
                if (programs.get(j).cost <= w) {
                    if (maxProfitIndex == -1 || programs.get(j).profit > programs.get(maxProfitIndex).profit) {
                        maxProfitIndex = j;
                    }
                } else {
                    break;
                }
            }

            if (maxProfitIndex == -1) {
                return w;
            }

            w += programs.get(maxProfitIndex).profit;
            programs.remove(maxProfitIndex);
        }

        return w;
    }

    public static int randomVal(int maxVal) {
        return (int)(Math.random() * (maxVal + 1));
    }

    public static int[] randomArr(int len, int maxVal) {
        int[] ans = new int[len];
        for (int i = 0; i < len; ++i) {
            ans[i] = randomVal(maxVal);
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int maxK = 20;
        int maxW = 100;
        int maxProjects = 100;
        int maxCost = 200;
        int maxProfits = 500;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; ++i) {
            int projects = randomVal(maxProjects);
            int[] costs = randomArr(projects, maxCost);
            int[] profits = randomArr(projects, maxProfits);
            int k = randomVal(maxK);
            int w = randomVal(maxW);
            if (findMaxProfit(k, w, profits, costs) != test(k, w, profits, costs)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "test success" : "test failed");
        System.out.println("test end");
    }
}
