#include <algorithm>
#include <unordered_set>
#include <vector>
using namespace std;

/*
    15 三数之和
        给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组。
        注意：答案中不可以包含重复的三元组

    示例
        给定数组 nums = [-1, 0, 1, 2, -1, -4]，
        满足要求的三元组集合为： [ [-1, 0, 1], [-1, -1, 2] ]
*/

class Solution {
public:
    vector<vector<int>> threeSum(vector<int>& nums) {
        vector<vector<int>> result;
        if (nums.size() == 0) {
            return result;
        }

        vector<int> tmp(nums.begin(), nums.end());
        sort(tmp.begin(), tmp.end());

        if (tmp[0] > 0) {
            return result;
        }

        for (int i = 0; i < tmp.size(); ++i) {
            if (tmp[i] > 0) {
                continue;
            }
            if (i > 0 && tmp[i] == tmp[i - 1]) {
                continue;
            }

            unordered_set<int> set;
            for (int j = i+1; j < tmp.size(); ++j) {
                if (j > i + 2 && tmp[j] == tmp[j-1] && tmp[j-1] == tmp[j-2]) {
                    continue;
                }
                int c = 0 - (tmp[i] + tmp[j]);
                if (set.find(c) != set.end()) {
                    result.push_back({tmp[i], tmp[j], c});
                    set.erase(c);
                } else {
                    set.insert(tmp[j]);
                }
            }
        }

        return result;
    }
};