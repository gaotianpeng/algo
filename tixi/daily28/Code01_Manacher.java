package tixi.daily28;

public class Code01_Manacher {
    /*
        假设字符串str长度为N，想返回最长回文子串的长度,时间复杂度O(N)
     */
    public static int manacher(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        char[] str = manacherString(s);
        int n = str.length;
        int[] p_radius_arr = new int[n]; // 回文半径大小数组
        int p_center = -1;
        int right = -1; // 成功扩至最右的下一个位置
        int ans = Integer.MIN_VALUE;
        for (int i = 0; i < n; ++i) {
            // R第一个违规的位置，i>= R
            // i位置扩出来的答案，i位置扩的区域，至少是多大。
            p_radius_arr[i] = right > i ? Math.min(right - i, p_radius_arr[2*p_center - i]) : 1;
            while (i + p_radius_arr[i] < str.length && i - p_radius_arr[i] > -1) {
                if (str[i+p_radius_arr[i]] == str[i-p_radius_arr[i]]) {
                    p_radius_arr[i]++;
                } else {
                    break;
                }
            }
            if (i + p_radius_arr[i] > right) {
                right = i + p_radius_arr[i];
                p_center = i;
            }

            ans = Math.max(ans, p_radius_arr[i]);
        }

        return ans - 1;
    }

    public static char[] manacherString(String s) {
        int n = s.length();
        char[] str = s.toCharArray();
        char[] ans = new char[2*n + 1];
        int index = 0;
        for (int i = 0; i < ans.length; ++i) {
            ans[i] = (i & 1) == 0 ? '#' : str[index++];
        }
        return ans;
    }

    public static int test(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        char[] str = manacherString(s);
        int n = str.length;
        int ans = 0;
        for (int i = 0; i < n; ++i) {
            int left = i - 1;
            int right = i + 1;
            while (left >= 0 && right < n && str[left] == str[right]) {
                --left;
                ++right;
            }
            ans = Math.max(right - left - 1, ans);
        }

        return ans / 2;
    }

    // for test
    public static String getRandomString(int possibilities, int size) {
        char[] ans = new char[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int posibilities = 5;
        int str_size = 30;
        int test_times = 100000;
        for (int i = 0; i < test_times; ++i) {
            String s = getRandomString(posibilities, str_size);
            if (test(s) != manacher(s)) {
                System.out.println("test failed");
                break;
            }
        }

        System.out.println("test end");
    }
}
