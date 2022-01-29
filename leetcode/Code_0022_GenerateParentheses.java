package leetcode;

import java.util.ArrayList;
import java.util.List;

/*
    0022 括号生成
        数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合

    示例
        输入：n = 3
        输出：["((()))","(()())","(())()","()(())","()()()"]

        输入：n = 1
        输出：["()"]
 */
public class Code_0022_GenerateParentheses {
    public List<String> generateParenthesis(int n) {
        char[] path = new char[n << 1];
        List<String> ans = new ArrayList<>();
        process(path, 0, 0, n, ans);
        return ans;
    }
    /*
        依次在path上填写决定
        ( ( ) ) ( ) ....
        0 1 2 3 4 5
        path[0...index-1] 决定已经做完了
     */
    public static void process(char[] path, int index, int left_minus_right,
                               int left_rest, List<String> ans) {
        if (index == path.length) {
            ans.add(String.valueOf(path));
        } else {
            if (left_rest > 0) {
                path[index] = '(';
                process(path, index + 1, left_minus_right + 1,
                        left_rest - 1, ans);
            }
            if (left_minus_right > 0) {
                path[index] = ')';
                process(path, index + 1, left_minus_right - 1,
                        left_rest, ans);
            }
        }
    }

    public List<String> generateParenthesis2(int n) {
        char[] path = new char[n<<1];
        List<String> ans = new ArrayList<>();
        process2(path, 0, ans);
        return ans;
    }

    public static void process2(char[] path, int index, List<String> ans) {
        if (index == path.length) {
            if (isValid(path)) {
                ans.add(String.valueOf(path));
            }
        } else {
            path[index] = '(';
            process2(path, index + 1, ans);
            path[index] = ')';
            process2(path, index + 1, ans);
        }
    }

    public static boolean isValid(char[] path) {
        int count = 0;
        for (char ch: path) {
            if (ch == '(') {
                count++;
            } else {
                count--;
            }

            if (count < 0) {
                return false;
            }
        }

        return count == 0;
    }
}
