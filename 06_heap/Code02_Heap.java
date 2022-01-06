package heap;

import java.util.Comparator;

public class Code02_Heap {
    public static class MyMaxHeap {
        private int[] heap_;
        private final int limit_;
        private int heap_size_;

        public MyMaxHeap(int limit) {
            heap_ = new int[limit];
            limit_ = limit;
            heap_size_ = 0;
        }

        public boolean isEmpty() {
            return heap_size_ == 0;
        }

        public boolean isFull() {
            return limit_ == heap_size_;
        }

        public void push(int value) {
            if (heap_size_ == limit_) {
                throw new RuntimeException("heap is full");
            }

            heap_[heap_size_] = value;
            heapInsert(heap_, heap_size_++);
        }

        // 用户自行判断操作合法性
        public int pop() {
            int ans = heap_[0];
            swap(heap_, 0, --heap_size_);
            heapify(heap_, 0, heap_size_);
            return ans;
        }

        /*
            新加进来的数,现在停在了index位置, 请依次往上移动
            移动到0位置, 或者不比自己父亲大了, 停
         */
        private void heapInsert(int[] arr, int index) {
            while (arr[index] > arr[(index - 1)/2]) {
                swap(arr, index, (index-1)/2);
                index = (index - 1)/2;
            }
        }

        /*
            从index位置往下看, 不断的下沉
            停：1, 较大的孩子不再比index位置的数大. 2 已经没孩子了
         */
        private void heapify(int[] arr, int index, int heap_size) {
            int left = index * 2 + 1;
            while (left < heap_size_) {
                int larger = left + 1 < heap_size_ && arr[left + 1] > arr[left]? left + 1: left;
                int largest = arr[larger] > arr[index] ? larger: index;
                if (largest == index) {
                    break;
                }
                swap(arr, largest, index);
                index = largest;
                left = index * 2 + 1;
            }
        }

        private void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    public static class MyComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    }

    /*
        for test
     */
    public static class TestHeap {
        private int[] heap_;
        private int heap_size_;
        private int limit_;

        public TestHeap(int limit) {
            heap_ = new int[limit];
            limit_ = limit;
            heap_size_ = 0;
        }

        public void push(int value) {
            if (heap_size_ == limit_) {
                throw new RuntimeException("heap is full");
            }
            heap_[heap_size_++] = value;
        }

        public int pop() {
            int max_index = 0;
            for (int i = 0; i < heap_size_; i++) {
                if (heap_[i] > heap_[max_index]) {
                    max_index = i;
                }
            }

            int ret = heap_[max_index];
            heap_[max_index] = heap_[--heap_size_];
            return ret;
        }
        
        public boolean isEmpty() {
            return heap_size_ == 0;
        }

        public boolean isFull() {
            return heap_size_ == limit_;
        }

        private void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int value = 1000;
        int limit = 100;
        int test_times = 1000000;
        boolean success = true;
        for (int i = 0; i < test_times; i++) {
            int cur_limit = (int) (Math.random() * limit) + 1;
            MyMaxHeap my = new MyMaxHeap(cur_limit);
            TestHeap test = new TestHeap(cur_limit);
            int curOpTimes = (int) (Math.random() * limit);
            for (int j = 0; j < curOpTimes; j++) {
                if (my.isEmpty() != test.isEmpty()) {
                    success = false;
                    break;
                }
                if (my.isFull() != test.isFull()) {
                    success = false;
                    break;
                }
                if (my.isEmpty()) {
                    int curValue = (int) (Math.random() * value);
                    my.push(curValue);
                    test.push(curValue);
                } else if (my.isFull()) {
                    if (my.pop() != test.pop()) {
                        success = false;
                        break;
                    }
                } else {
                    if (Math.random() < 0.5) {
                        int curValue = (int) (Math.random() * value);
                        my.push(curValue);
                        test.push(curValue);
                    } else {
                        if (my.pop() != test.pop()) {
                            success = false;
                            break;
                        }
                    }
                }
            }
            if (!success) {
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
