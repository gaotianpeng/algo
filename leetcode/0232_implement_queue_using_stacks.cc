#include <stack>
using namespace std;

/*
    232 用栈实现队列
        请你仅使用两个栈实现先入先出队列。队列应当支持一般队列支持的所有操作（push、pop、peek、empty）：

    实现 MyQueue 类：
        void push(int x) 将元素 x 推到队列的末尾
        int pop() 从队列的开头移除并返回元素
        int peek() 返回队列开头的元素
        boolean empty() 如果队列为空，返回 true ；否则，返回 false
*/

class MyQueue {
public:
    MyQueue() {

    }
    
    void push(int x) {
        in_.push(x);
    }
    
    int pop() {
        if (out_.empty()) {
            while (!in_.empty()) {
                out_.push(in_.top());
                in_.pop();
            }
        }

        int ret = out_.top();
        out_.pop();
        return ret;
    }
    
    int peek() {
        int ret = this->pop();
        out_.push(ret);
        return ret;
    }
    
    bool empty() {
        return in_.empty() && out_.empty();
    }

private:
    stack<int> in_;
    stack<int> out_;
};

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue* obj = new MyQueue();
 * obj->push(x);
 * int param_2 = obj->pop();
 * int param_3 = obj->peek();
 * bool param_4 = obj->empty();
 */