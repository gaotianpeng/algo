#include <string>
#include <stack>
#include <algorithm>
using namespace std;
/*
    1047. 删除字符串中的所有相邻重复项
    给出由小写字母组成的字符串 S，重复项删除操作会选择两个相邻且相同的字母，并删除它们。
    在 S 上反复执行重复项删除操作，直到无法继续删除。
    在完成所有重复项删除操作后返回最终的字符串。答案保证唯一。

*/

class Solution {
public:
    string removeDuplicates(string s) {
        stack<char> st;
        for (auto& elem: s) {
            if (st.empty() || elem != st.top()) {
                st.push(elem);
            } else {
                st.pop();
            }
        }

        string ret;
        while (!st.empty()) {
            ret += st.top();
            st.pop();;
        }

        reverse(ret.begin(), ret.end());
        return ret;
    }
};