package leetcode;
/*
    125. 验证回文串
        给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写

    示例
        输入: "A man, a plan, a canal: Panama"
        输出: true
        解释："amanaplanacanalpanama" 是回文串

        输入: "race a car"
        输出: false
        解释："raceacar" 不是回文串
 */
public class Code_0125_ValidPalindrome {
    public boolean isPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }

        char[] str = s.toCharArray();
        int left = 0;
        int right = str.length - 1;
        while (left < right) {
            if (validChar(str[left]) && validChar(str[right])) {
                if (!equal(str[left], str[right])) {
                    return false;
                }
                left++;
                right--;
            } else {
                left += validChar(str[left]) ? 0: 1;
                right -= validChar(str[right]) ? 0 : 1;
            }
        }
        return true;
    }

    public static boolean validChar(char c) {
        return isLetter(c) || isNumber(c);
    }

    public static boolean equal(char c1, char c2) {
        if (isNumber(c1) || isNumber(c2)) {
            return c1 == c2;
        }
        return (c1 == c2) || (Math.max(c1, c2) - Math.min(c1, c2) == 32);
    }

    public static boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public static boolean isNumber(char c) {
        return (c >= '0' && c <= '9');
    }
}
