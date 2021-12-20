#include <queue>
using namespace std;
/*
    225 用队列实现栈
    请你仅使用两个队列实现一个后入先出（LIFO）的栈，并支持普通栈的全部四种操作（push、top、pop 和 empty）。

    实现 MyStack 类：
        void push(int x) 将元素 x 压入栈顶。
        int pop() 移除并返回栈顶元素。
        int top() 返回栈顶元素。
        boolean empty() 如果栈是空的，返回 true ；否则，返回 false 

    注意：
        你只能使用队列的基本操作 —— 也就是 push to back、peek/pop from front、size 和 is empty 这些操作。
        你所使用的语言也许不支持队列。 你可以使用 list （列表）或者 deque（双端队列）来模拟一个队列 , 只要是标准的队列操作即可。
*/
class MyStack {
public:
    MyStack() {

    }
    // 将元素x压入栈顶
    void push(int x) {
        out_que_.push(x);
    }
    
    // 移除并返回栈顶元素
    int pop() {
        int size = out_que_.size();
        --size;
        // out_que_保留一个元素用作模拟栈pop
        while (size--) {
            backup_que_.push(out_que_.front());
            out_que_.pop();
        }

        int ret = out_que_.front();
        out_que_.pop();
        out_que_ = backup_que_;
        while (!backup_que_.empty()) {
            backup_que_.pop();
        }

        return ret;
    }
    
    // 返回栈顶元素
    int top() {
        return out_que_.back();
    }
    
    // 如何栈是空的返回true, 否则返回false
    bool empty() {
        return out_que_.empty();
    }

private:
    queue<int> out_que_;
    queue<int> backup_que_;
};
