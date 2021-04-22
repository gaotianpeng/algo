package AlgoNew;

public class Code14_Heap {
    public static class MyMaxHeap {
        private int[] heap;
        private final int limit;
        private int heapSize;

        public MyMaxHeap(int limit) {
            heap = new int[limit];
            this.limit = limit;
            heapSize = 0;
        }

        public boolean isEmpty() {
            return heapSize == 0;
        }

        public boolean isFull() {
            return heapSize == limit;
        }

        public void push(int value) {
            if (heapSize == limit) {
                throw new RuntimeException("heap is full");
            }

            heap[heapSize] = value;
            heapInsert(heap, heapSize++);
        }

        public int pop() {
            int ans = heap[0];
            swap(heap, 0, --heapSize);
            heapify(heap, 0, heapSize);
            return ans;
        }

        private void heapInsert(int[] arr, int index) {
            while (arr[index] > arr[(index-1)/2]) {
                swap(arr, index, (index-1)/2);
                index = (index-1)/2;
            }
        }

        private void heapify(int[] arr, int index, int heapSize) {
            int left = index*2 + 1;
            while (left < heapSize) {
                int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ?
                            left + 1: left;
                largest = arr[largest] > arr[index] ? largest: index;
                if (largest == index) {
                    break;
                }

                swap(arr, largest, index);
                index = largest;
                left = index*2 + 1;
            }
        }

        private void swap(int[] arr, int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }

    public static class ComparatorMaxHeap {
        private int[] arr;
        private final int limit;
        private int size;

        public ComparatorMaxHeap(int limit) {
            arr = new int[limit];
            this.limit = limit;
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == limit;
        }

        public void push(int value) {
            if (size == limit) {
                throw new RuntimeException("heap is full");
            }

            arr[size++] = value;
        }

        public int pop() {
            int maxIndex = 0;
            for (int i = 0; i < size; ++i) {
                if (arr[i] > arr[maxIndex]) {
                    maxIndex = i;
                }
            }

            int ans = arr[maxIndex];
            arr[maxIndex] = arr[--size];
            return ans;
        }
    }

    public static void main(String[] args) {
        int maxValue = 1000;
        int maxLimit = 100;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; ++i) {
            int curLimit = (int)(Math.random()*maxLimit) + 1;
            MyMaxHeap myHeap = new MyMaxHeap(curLimit);
            ComparatorMaxHeap compHeap = new ComparatorMaxHeap(curLimit);
            int curOpTimes = (int)(Math.random()*maxLimit);
            for (int j = 0; j < curOpTimes; ++j) {
                if (myHeap.isEmpty() != compHeap.isEmpty()) {
                    System.out.println("failed!" );
                }
                if (myHeap.isFull() != compHeap.isFull()) {
                    System.out.println("failed");
                }
                if (myHeap.isEmpty()) {
                    int curVal = (int)(Math.random()*maxValue);
                    myHeap.push(curVal);
                    compHeap.push(curVal);
                } else if (myHeap.isFull()) {
                    if (myHeap.pop() != compHeap.pop()) {
                        System.out.println("failed");
                    }
                } else {
                    if (Math.random() < 0.5) {
                        int curVal = (int)(Math.random()*maxValue);
                        myHeap.push(curVal);
                        compHeap.push(curVal);
                    } else {
                        if (myHeap.pop() != compHeap.pop()) {
                            System.out.println("failed");
                        }
                    }
                }
            }
        }

        System.out.println("finish!!!!");
    }
}
