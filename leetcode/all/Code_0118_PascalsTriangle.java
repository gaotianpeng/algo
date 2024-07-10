package leetcode.all;

import java.util.ArrayList;
import java.util.List;

/*
    0118 杨辉三角
        给定一个非负整数 numRows，生成「杨辉三角」的前 numRows 行。
        在「杨辉三角」中，每个数是它左上方和右上方的数的和

    示例
        输入: numRows = 5
        输出: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
 */
public class Code_0118_PascalsTriangle {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            ans.add(new ArrayList<>());
            ans.get(i).add(1);
        }

        for (int i = 1; i < numRows; i++) {
            for (int j = 1; j < i; j++) {
                ans.get(i).add(ans.get(i-1).get(j-1) + ans.get(i-1).get(j));
            }
            ans.get(i).add(1);
        }

        return ans;
    }
}
