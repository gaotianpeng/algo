package tixi.daily14;

import java.util.Comparator;
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
        PriorityQueue<Program> min_cost_q = new PriorityQueue<>(new MinCostComparator());
        PriorityQueue<Program> max_profit_q = new PriorityQueue<>(new MaxProfitComparator());
        for (int i = 0; i < profit.length; i++) {
            min_cost_q.add(new Program(profit[i], cost[i]));
        }

        for (int i = 0; i < k; i++) {
            while (!min_cost_q.isEmpty() && min_cost_q.peek().cost <= w) {
                max_profit_q.add(min_cost_q.poll());
            }
            if (max_profit_q.isEmpty()) {
                return w;
            }
            w += max_profit_q.poll().profit;
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
}
