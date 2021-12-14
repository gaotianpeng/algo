#include <vector>
#include <unordered_set>
using namespace std;

/*
    349 两个数组的交集
        给定两个数组，编写一个函数来计算它们的交集

    示例
        输入：nums1 = [1,2,2,1], nums2 = [2,2]
        输出：[2]

        输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
        输出：[9,4]
*/
class Solution {
public:
    vector<int> intersection1(vector<int>& nums1, vector<int>& nums2) {
        unordered_set<int> inter_elem;
        for (int i = 0; i < nums1.size(); ++i) {
            for (int j = 0; j < nums2.size(); ++j) {
                if (nums1[i] == nums2[j]) {
                    inter_elem.insert(nums1[i]);
                }
            }
        }

        return vector<int>(inter_elem.begin(), inter_elem.end());
    }

    vector<int> intersection2(vector<int>& nums1, vector<int>& nums2) {
        unordered_set<int> nums1_set(nums1.begin(), nums1.end());
        unordered_set<int> inter_set;
        for (auto& elem: nums2) {
            if (nums1_set.find(elem) != nums1_set.end()) {
                inter_set.insert(elem);
            }
        }

        return vector<int>(inter_set.begin(), inter_set.end());
    }
};