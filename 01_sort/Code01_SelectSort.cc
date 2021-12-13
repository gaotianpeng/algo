#include <iostream>
#include <ctime>
#include "boost/random.hpp"
using namespace std;
using boost::mt19937;
using boost::uniform_01;

/*
    选择排序
        arr[0~N-1] 范围上, 找到最小值所在的位置，把最小值交换到0位置
        arr[1~N-1] 范围上, 找到最小值所在的位置，把最小值交换到1位置
        arr[2~N-1] 范围上, 找到最小值所在的位置，把最小值交换到2位置
        ...
        arr[N-1~N-1] 范围上, 找到最小值所在的位置，把最小值交换到N-1位置  
*/
void SelectSort(int arr[], int arr_len) {
    if (!arr || arr_len < 2) {
        return;
    }

    for (int i = 0; i < arr_len; i++) {
        int min_idx = i;
        for (int j = i + 1; j < arr_len; j++) {
            if (arr[min_idx] > arr[j]) {
                min_idx = j;
            }
        }

        if (min_idx != i) {
            std::swap(arr[i], arr[min_idx]);
        }
    }
}

/*
* for test
*/
class GenerateDouble01 {
public:
    GenerateDouble01():generator_(time(0)) {}
    double Random01() {
        static uniform_01<mt19937> dist(generator_);
        return dist();
    }

private:
    mt19937 generator_;
};

struct MyArray {
    MyArray(): arr_(nullptr), len_(0) {}
    int* arr_;
    int len_;
};

bool IsEqual(const MyArray& arr1, const MyArray& arr2) {
    if ((arr1.arr_ != nullptr && arr2.arr_ == nullptr) || 
                arr1.arr_ == nullptr && arr2.arr_  != nullptr) {
        return false;
    }

    if (arr1.arr_ == nullptr && arr2.arr_ == nullptr) {
        return true;
    }

    if (arr1.len_ != arr1.len_) {
        return false;
    }

    if (arr1.len_ == 0 && arr2.len_ == 0) {
        return true;
    }

    for (int i = 0; i < arr1.len_; i++) {
        if (arr1.arr_[i] != arr2.arr_[i]) {
            return false;
        }
    }

    return true;
}

void GenerateRandomArray(GenerateDouble01& gen, int max_value, int max_arr_size, MyArray& arr) {
    arr.len_ = (int)((max_arr_size + 1) * gen.Random01());
    if (arr.len_ < 1) {
        return;
    }
    arr.arr_ = new int[arr.len_];

    for (int i = 0; i < arr.len_; i++) {
        arr.arr_[i] = (int)((max_value + 1) * gen.Random01()) - 
                (int)(max_value * gen.Random01());
    }
}

void PrintArr(const MyArray& arr) {
    for (int i = 0; i < arr.len_; i++) {
        cout << arr.arr_[i] << " ";
    }
    cout << endl;
}

void CopyArray(const MyArray& src, MyArray& dst) {
    if (src.len_ > 0) {
        dst.len_ = src.len_;
        dst.arr_ = new int[dst.len_];
        for (int i = 0; i < src.len_; i++) {
            dst.arr_[i] = src.arr_[i];
        }
    }
}

int main(int argc, char* argv[]) {
    GenerateDouble01 gen;
    int test_time = 50000;
    int max_arr_size = 100;
    int max_value = 100;
    bool succeed = true;
    for (int i = 0; i < test_time; i++) {
        MyArray arr1, arr2;
        GenerateRandomArray(gen, max_value, max_arr_size, arr1);
        CopyArray(arr1, arr2);

        if (arr1.len_ > 0) {
            SelectSort(arr1.arr_, arr1.len_);
            std::sort(arr2.arr_, arr2.arr_ + arr2.len_);
        }

        if (!IsEqual(arr1, arr2)) {
            cout << "failed " << endl;
            PrintArr(arr1);
            PrintArr(arr2);
            return -1;
        }

        if (arr1.arr_) {
            delete[] arr1.arr_;
        }

        if (arr2.arr_) {
            delete[] arr2.arr_;
        }
    }

    cout << "success" << endl;
    return 0;
}