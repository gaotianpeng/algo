/*
    704 二分查找 
        给定一个 n 个元素有序的（升序）整型数组 nums 和一个目标值 target，
        写一个函数搜索 nums 中的 target，如果目标值存在返回下标，否则返回 -1
*/
#include <iostream>
#include <vector>
#include <random>
using namespace std;

class Solution {
public:
    // 算法
    int search1(vector<int>& nums, int target) {
        int left = 0, right = nums.size() - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    int search2(vector<int>& nums, int target) {
        int left = 0, right = nums.size();
        while (left < right) {
            int mid = left + ((right - left) >> 1);
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid;
            } else {
                return mid;
            }
        }
        return -1;
    }


    // 测试
    int searchTest(vector<int>& nums, int target) {
        for (int i = 0; i < nums.size(); ++i) {
            if (nums[i] == target) {
                return i;
            }
        }

        return -1;
    }
};



int main(int argc, char* argv[]) {

    return 0;
}
