#include <vector>
#include <deque>
using namespace std;

/*
    239 滑动窗口最大值
        给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
    返回滑动窗口中的最大值。

    示例
        输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
        输出：[3,3,5,5,6,7]
        解释：
        滑动窗口的位置                最大值
        ---------------               -----
        [1  3  -1] -3  5  3  6  7       3
        1 [3  -1  -3] 5  3  6  7       3
        1  3 [-1  -3  5] 3  6  7       5
        1  3  -1 [-3  5  3] 6  7       5
        1  3  -1  -3 [5  3  6] 7       6
        1  3  -1  -3  5 [3  6  7]      7
*/

class Solution {
public:
    vector<int> maxSlidingWindow(vector<int>& nums, int k) {
        MyQueue que;
        vector<int> ret;
        // 先将前k个元素放进队列
        for (int i = 0; i < k; i++) {
            que.push(nums[i]);
        }

        // ret 记录前k个元素的最大值
        ret.push_back(que.front());
        for (int i = k; i < nums.size(); i++) {
            que.pop(nums[i-k]); // 滑动窗口移除最前面的元素
            que.push(nums[i]); // 滑动窗口前加入最后面的元素
            ret.push_back(que.front()); // 记录对应的最大值
        }

        return ret;
    }

private:
    /*
        单调队列(从大到小)
    */
    class MyQueue {
    public:
        /*
            每次弹出时，比较当前要弹出的元素是否等于队列出口元素的数组
            如果相等则弹出
        */
        void pop(int value) {
            if (!que_.empty() && value == que_.front()) {
                que_.pop_front();
            }
        }

        /*
            如果push的元素大于入口的数值，将队列后端数值弹出，直到push的元素<= 队列入口元素的数值为止
        */
        void push(int value) {
            while (!que_.empty() && value > que_.back()) {
                que_.pop_back();
            }
            que_.push_back(value);
        }

        int front() {
            return que_.front();
        }

    private:
        deque<int> que_;
    };
};