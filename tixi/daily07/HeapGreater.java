package tixi.daily07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class HeapGreater<T> {

    private ArrayList<T> heap_;
    private HashMap<T, Integer> index_map_;
    private int heap_size_;
    private Comparator<? super T> comp_;

    public HeapGreater(Comparator<T> c) {
       heap_ = new ArrayList<>();
       index_map_ = new HashMap<>();
       heap_size_ = 0;
       comp_ = c;
    }

    public boolean isEmpty() {
        return heap_size_ == 0;
    }

    public int size() {
        return heap_size_;
    }

    public boolean contains(T obj) {
        return index_map_.containsKey(obj);
    }

    public T peek() {
        return heap_.get(0);
    }

    public void push(T obj) {
        heap_.add(obj);
        index_map_.put(obj, heap_size_);
        heapInsert(heap_size_++);
    }

    public T pop() {
        T ans = heap_.get(0);
        swap(0, heap_size_ - 1);
        index_map_.remove(ans);
        heap_.remove(--heap_size_);
        heapify(0);

        return ans;
    }

    public void remove(T obj) {
        T replace = heap_.get(heap_size_ - 1);
        int index = index_map_.get(obj);
        index_map_.remove(obj);
        heap_.remove(--heap_size_);
        if (obj != replace) {
            heap_.set(index, replace);
            index_map_.put(replace, index);
            resign(replace);
        }
    }

    public void resign(T obj) {
        heapInsert(index_map_.get(obj));
        heapify(index_map_.get(obj));
    }

    public List<T> getAllElements() {
        List<T> ans = new ArrayList<>();
        for (T c: heap_) {
            ans.add(c);
        }
        return ans;
    }

    private void heapInsert(int index) {
        while (comp_.compare(heap_.get(index), heap_.get((index - 1) / 2)) < 0) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    private void heapify(int index) {
        int left = index * 2 + 1;
        while (left < heap_size_) {
            int best = left + 1 < heap_size_ && comp_.compare(
                    heap_.get(left + 1), heap_.get(left)) < 0 ?(left + 1) : left;
            best = comp_.compare(heap_.get(best), heap_.get(index)) < 0 ? best : index;
            if (best == index) {
                break;
            }
            swap(best, index);
            index = best;
            left = index * 2 + 1;
        }
    }

    private void swap(int i, int j) {
        T o1 = heap_.get(i);
        T o2 = heap_.get(j);
        heap_.set(i, o2);
        heap_.set(j, o1);
        index_map_.put(o2, i);
        index_map_.put(o1, j);
    }
}