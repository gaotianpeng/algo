#include <iostream>
using namespace std;

class Solution {
 public:
    void PrintB(int num) {
      for (int i = 31; i >= 0; i--) {
        cout << ((num & 1 << i) ? 1: 0);
      }
      cout << endl;
    }
};

int main(int argc, char* argv[]) {
    Solution s;
    s.PrintB(1);
    s.PrintB(-1);
    s.PrintB(-2);
    return 0;
}