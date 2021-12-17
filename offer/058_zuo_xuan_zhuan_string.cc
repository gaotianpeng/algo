#include <string>
#include <algorithm>
using namespace std;

/*
    58 左旋转字符串
        字符串的左旋转操作是把字符串前面的若干个字符转移到字符串的尾部。请定义一个函数实现字符串左旋转操作的功能
    示例 1：

        输入: s = "abcdefg", k = 2
        输出: "cdefgab"
*/

class Solution {
public:
    string reverseLeftWords(string s, int n) {
        reverse(s.begin(), s.begin() + n);
        reverse(s.begin() + n, s.end());
        reverse(s.begin(), s.end());
        return s;
    }
};