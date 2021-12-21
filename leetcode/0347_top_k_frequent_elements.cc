#include <vector>
#include <unordered_map>
#include <queue>
using namespace std;
/*
    347 前K个高频元素
        给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。

    示例
        输入: nums = [1,1,1,2,2,3], k = 2
        输出: [1,2]
*/

class Solution {
public:
    /*
        1 要统计元素出现频率
        2 对频率进行排序
        3 找出前K个高频元素
    */
    vector<int> topKFrequent(vector<int>& nums, int k) {
        // 元素出现的频率
        unordered_map<int, int> freq_map;
        for (auto& elem: nums) {
            freq_map[elem]++;
        }

        /*
            对频率进行排序
        */
        // 定义一个小顶堆, 大小为K
        priority_queue<pair<int, int>, vector<pair<int, int>>, Comparator> pri_que;
        for (auto& elem: freq_map) {
            pri_que.push(elem);
            if (pri_que.size() > k) {
                pri_que.pop();
            }
        }

        vector<int> ret(k);
        for (int i = k - 1; i >= 0; i--) {
            ret[i] = pri_que.top().first;
            pri_que.pop();
        }

        return ret;
    }

private:
    class Comparator{
    public:
        bool operator()(const pair<int, int>& left, const pair<int, int>& right) {
            return left.second > right.second;
        }
    };
};