/*
    0027 移除元素
    给你一个数组 nums 和一个值 val，你需要原地移除所有数值等于 val 的元素，并返回移除后数组的新长度。
    不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并原地修改输入数组。
    元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
*/
#include <vector>
using namespace std;

class Solution {
public:
    // 最优解法
    int removeElement1(vector<int>& nums, int val) {
        int pre_pos = 0;
        for (int i = 0; i < nums.size(); i++) {
            if (nums[i] != val) {
                nums[pre_pos++] = nums[i];
            }
        }
        return pre_pos;
    }

    // 暴力解法
    int removeElement2(vector<int>& nums, int val) {
        int size = nums.size();
        for (int i = 0; i < size; ++i) {
            if (nums[i] == val) {
                for (int j = i+1; j < size; j++) {
                    nums[j-1] = nums[j]; 
                }
                i--;
                size--;
            }
        }

        return size;
    }
};