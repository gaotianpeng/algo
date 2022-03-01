package tixi.daily06;

import java.util.Comparator;

public class Code01_Heap {
    public static class MyMaxHeap {
        private int[] heap_;
        private int size_;
        private int limit_;

        public MyMaxHeap(int limit) {
            heap_ = new int[limit];
            limit_ = limit;
            size_ = 0;
        }

        public boolean isEmpty() {
            return size_ == 0;
        }

        public boolean isFull() {
            return size_ == limit_;
        }

        public void push(int val) {
            if (isFull()) {
                throw new RuntimeException("heap is full");
            }
            heap_[size_] = val;
            heapInsert(heap_, size_++);
        }

        public int pop() {
            if (isEmpty()) {
                throw new RuntimeException("heap is empty");
            }

            int ans = heap_[0];
            swap(heap_, 0, --size_);
            heapify(heap_, 0, size_);
            return ans;
        }

        private void heapInsert(int[] arr, int index) {
            while (arr[index] > arr[(index - 1) / 2]) {
                swap(arr, index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        private void heapify(int[] arr, int index, int heap_size) {
            int left = index * 2 + 1;
            while (left < heap_size) {
                int largest =  left + 1 < heap_size && arr[left] < arr[left + 1] ?
                        left + 1 : left;
                largest = arr[index] < arr[largest] ? largest : index;
                if (largest == index) {
                    break;
                }
                
                swap(arr, index, largest);
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
        private int size_;
        private int limit_;

        TestHeap(int limit) {
            heap_ = new int[limit];
            limit_ = limit;
            size_ = 0;
        }

        public boolean isEmpty() {
            return size_ == 0;
        }

        public boolean isFull() {
            return size_ == limit_;
        }

        public int pop() {
            if (isEmpty()) {
                throw new RuntimeException("heap is empty");
            }

            int max_index = 0;
            for (int i = 1; i < size_; i++) {
                if (heap_[i] > heap_[max_index]) {
                    max_index = i;
                }
            }

            int ans = heap_[max_index];
            heap_[max_index] = heap_[--size_];
            return ans;
        }

        public void push(int val) {
            if (isFull()) {
                throw new RuntimeException("heap is full");
            }
            heap_[size_++] = val;
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
