#include <vector>
#include <unordered_map>
using namespace std;

/*
    454 四数相加 II
        给你四个整数数组nums1、nums2、nums3 和 nums4 ，数组长度都是n ，请你计算有多少个元组 (i, j, k, l) 能满足：
            0 <= i, j, k, l < n
            nums1[i] + nums2[j] + nums3[k] + nums4[l] == 0

    示例
        输入：nums1 = [1,2], nums2 = [-2,-1], nums3 = [-1,2], nums4 = [0,2]
        输出：2
        解释：
        两个元组如下：
        1. (0, 0, 0, 1) -> nums1[0] + nums2[0] + nums3[0] + nums4[1] = 1 + (-2) + (-1) + 2 = 0
        2. (1, 1, 0, 0) -> nums1[1] + nums2[1] + nums3[0] + nums4[0] = 2 + (-1) + (-1) + 0 = 0

*/

class Solution {
public:
    int fourSumCount(vector<int>& nums1, vector<int>& nums2, vector<int>& nums3, vector<int>& nums4) {
        unordered_map<int, int> umap;
        for (auto& elem_1: nums1) {
            for (auto& elem_2: nums2 ) {
                umap[elem_1 + elem_2]++;
            }
        }

        int ret = 0;
        for (auto& elem_1: nums3) {
            for (auto& elem_2: nums4) {
                if (umap.find(0 - (elem_1 + elem_2)) != umap.end()) {
                    ret += umap[0 - (elem_1 + elem_2)];
                }
            }
        }

        return ret;
    }
};