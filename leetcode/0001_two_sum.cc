#include <vector>
#include <unordered_map>
using namespace std;
/*
    1. 两数之和
        给定一个整数数组nums和一个整数target，请你在该数组中找出和为target的那两个整数，并返回它们的数组下标

        输入：nums = [2,7,11,15], target = 9
        输出：[0,1]
        解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 
*/

class Solution {
public:
    vector<int> twoSum1(vector<int>& nums, int target) {
        for (int i = 0; i < nums.size() - 1; ++i) {
            for (int j = i + 1; j < nums.size(); ++j) {
                if (nums[i] + nums[j] == target) {
                    return {i, j};
                }
            }
        }
        return vector<int>();
    }

    vector<int> twoSum2(vector<int>& nums, int target) {
        unordered_map<int, int> elem_map;
        for (int i = 0; i < nums.size(); i++) {
            auto iter = elem_map.find(target - nums[i]);
            if (iter != elem_map.end()) {
                return {i, iter->second};
            }
            elem_map.insert({nums[i], i});
        }

        return {};
    }
};