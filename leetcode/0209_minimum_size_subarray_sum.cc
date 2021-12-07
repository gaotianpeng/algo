/*
209 长度最小的子数组
    找出该数组中满足其和 ≥ target 的长度最小的 连续子数组 [numsl, numsl+1, ..., numsr-1, numsr] 
    并返回其长度。如果不存在符合条件的子数组，返回 0 
*/

#include <vector>
using namespace std;

class Solution {
public:
    int minSubArrayLen(int target, vector<int>& nums) {
        int ret_len = INT32_MAX;
        int sum = 0;
        int sub_arr_len = 0;
        for (int i = 0; i < nums.size(); ++i) {
            sum = 0;
            for (int j = i; j < nums.size(); j++) {
                sum += nums[j];
                if (sum >= target) {
                    sub_arr_len = j - i + 1;
                    ret_len = sub_arr_len < ret_len ? sub_arr_len: ret_len;
                    break;
                }
            }
        }

        return ret_len == INT32_MAX ? 0: ret_len;
    }
};