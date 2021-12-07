/*
    977 有序数组的平方
        给你一个按 非递减顺序 排序的整数数组 nums，返回 每个数字的平方 组成的新数组，要求也按 非递减顺序 排序。
*/

#include <vector>
#include <algorithm>
using namespace std;

class Solution {
public:
    vector<int> sortedSquares1(vector<int>& nums) {
        vector<int> ret(nums.size());
        for (int i = 0; i < nums.size(); ++i) {
            ret[i] = nums[i]*nums[i];
        }

        sort(ret.begin(), ret.end());
        return ret;
    }

    vector<int> sortedSquares2(vector<int>& nums) {
        int size = nums.size();
        int k = size - 1;
        vector<int> ret(nums.size());
        for (int i = 0, j = k; i <= j;) {
            int pow_i = nums[i]*nums[i], pow_j = nums[j]*nums[j];
            if (pow_i < pow_j) {
                ret[k--] = pow_j;
                j--;
            } else {
                ret[k--] = pow_i;
                i++;
            }
        }

        return ret;
    }
};