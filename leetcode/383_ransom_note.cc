#include <string>
using namespace std;

/*
    383 赎金信
        给你两个字符串：ransomNote 和 magazine ，判断 ransomNote 能不能由 magazine 里面的字符构成。
        如果可以，返回 true ；否则返回 false 。
        magazine 中的每个字符只能在 ransomNote 中使用一次。
        注: 只有小写字母
*/
class Solution {
public:
    bool canConstruct1(string ransomNote, string magazine) {
        for (int i = 0; i < magazine.length(); ++i) {
            for (int j = 0; j < ransomNote.length(); ++j) {
                if (magazine[i] == ransomNote[j]) {
                    ransomNote.erase(ransomNote.begin() + j);
                    break;
                }
            }
        }

        if (ransomNote.empty()) {
            return true;
        }
        return false;
    }

    bool canConstruct2(string ransomNote, string magazine) {
        int counts[26] = {0};
        for (auto& elem: magazine) {
            counts[elem - 'a']++;
        }

        for (auto& elem: ransomNote) {
            counts[elem - 'a']--;
            if (counts[elem - 'a'] < 0) {
                return false;
            }
        }

        return true;
    }
};