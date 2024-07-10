package leetcode.all;
/*
    121 买卖股票的最佳时机
        给定一个数组prices，它的第i个元素prices[i]表示一支给定股票第i天的价格。
        你只能选择某一天买入这只股票，并选择在未来的某一个不同的日子卖出该股票。设计一个算法来计算你所能获取的最大利润。
        返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0
 */
public class Code_0121_BestTimeToBuyAndSellStock {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int min = prices[0];
        int ans = 0;
        for (int i = 0; i < prices.length; i++) {
            min = Math.min(min, prices[i]);
            ans = Math.max(ans, prices[i] - min);
        }
        return ans;
    }
}
