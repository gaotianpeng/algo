#include <string>
#include <algorithm>
using namespace std;

/*
    151. 翻转字符串里的单词
        给你一个字符串 s ，逐个翻转字符串中的所有 单词 。

        单词 是由非空格字符组成的字符串。s 中使用至少一个空格将字符串中的 单词 分隔开。

        请你返回一个翻转 s 中单词顺序并用单个空格相连的字符串。

        说明：
            输入字符串 s 可以在前面、后面或者单词间包含多余的空格。
            翻转后单词间应当仅用一个空格分隔。
            翻转后的字符串中不应包含额外的空格

*/

class Solution {
public:
    void reverseStr(string& s, int start, int end) {
        for (int i = start, j = end; i < j; i++, j--) {
            std::swap(s[i], s[j]);
        }
    }

    void removeUselessSpace(string& s) {
        int slow = 0, fast = 0;
        // 去掉s前面的空格
        while (s.size() > 0 && fast < s.size() && s[fast] == ' ') {
            fast++;
        }
        // 去掉s中单词间的冗余空格
        for (; fast < s.size(); ++fast) {
            if (fast - 1 > 0 && s[fast - 1 ] == s[fast] && s[fast] == ' ') {
                continue;
            } else {
                s[slow++] = s[fast];
            }
        }
        // 去掉s末尾的空格
        if (slow - 1 > 0 && s[slow - 1] == ' ') {
            s.resize(slow - 1);
        } else {
            s.resize(slow);
        }
    }

    string reverseWords(string s) {
        // 删除冗余空格
        removeUselessSpace(s);
        reverseStr(s, 0, s.size() - 1);
        for (int i = 0; i < s.size(); i++) {
            int j = i;
            while (j < s.size() && s[j] != ' ') {
                j++;
            }
            reverseStr(s, i, j - 1);
            i = j;
        }
        return s;
    }
};