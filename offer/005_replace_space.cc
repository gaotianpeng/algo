#include <string>
using namespace std;

/*
    05 替换空格
        请实现一个函数，把字符串 s 中的每个空格替换成"%20"。

    示例
        输入：s = "We are happy."
        输出："We%20are%20happy."
*/
class Solution {
public:
    string replaceSpace(string s) {
        int space_cnt = 0;
        int old_size = s.size();
        for (auto& elem: s) {
            if (elem == ' ') {
                space_cnt++;
            }
        }

        int new_size = s.size() + space_cnt * 2;
        s.resize(new_size);
        for (int i = new_size - 1, j = old_size - 1; j < i; i--, j--) {
            if (s[j] != ' ') {
                s[i] = s[j];
            } else {
                s[i] = '0';
                s[i-1] = '2';
                s[i-2] = '%';
                i -= 2;
            }
        }

        return s;
    }
};