package leetcode;
/*
    123 买卖股票的最佳时机
        给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
        设计一个算法来计算你所能获取的最大利润。你最多可以完成两笔交易。
        注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）

    示例
        输入：prices = [3,3,5,0,0,3,1,4]
        输出：6
        解释：在第 4 天（股票价格 = 0）的时候买入，在第 6 天（股票价格 = 3）的时候卖出，这笔交易所能获得利润 = 3-0 = 3 。
        随后，在第 7 天（股票价格 = 1）的时候买入，在第 8 天 （股票价格 = 4）的时候卖出，这笔交易所能获得利润 = 4-1 = 3 。

 */
public class Code_0123_BestTimeToBuyAndSellStock3 {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int ans = 0;
        int done_once_minus_buy_max = -prices[0];
        int done_once_max = 0;
        int min = prices[0];
        for (int i = 1; i < prices.length; i++) {
            ans = Math.max(ans, done_once_minus_buy_max + prices[i]);
            min = Math.min(min, prices[i]);
            done_once_max = Math.max(done_once_max, prices[i] - min);
            done_once_minus_buy_max = Math.max(done_once_minus_buy_max, done_once_max - prices[i]);
        }
        return ans;
    }
}
