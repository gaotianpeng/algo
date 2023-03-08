package tixi.daily07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/*
    一对arr[i]和op[i]就代表一个事件：
    用户号为arr[i]，op[i] == T就代表这个用户购买了一件商品
    op[i] == F就代表这个用户退货了一件商品
    现在你作为电商平台负责人，你想在每一个事件到来的时候，
    都给购买次数最多的前K名用户颁奖。
    所以每个事件发生后，你都需要一个得奖名单（得奖区）

    得奖系统的规则：
    1，如果某个用户购买商品数为0，但是又发生了退货事件，
         则认为该事件无效，得奖名单和上一个事件发生后一致，例子中的5用户
    2，某用户发生购买商品事件，购买商品数+1，发生退货事件，购买商品数-1
    3，每次都是最多K个用户得奖，K也为传入的参数
          如果根据全部规则，得奖人数确实不够K个，那就以不够的情况输出结果
    4，得奖系统分为得奖区和候选区，任何用户只要购买数>0，
          一定在这两个区域中的一个
    5，购买数最大的前K名用户进入得奖区，
          在最初时如果得奖区没有到达K个用户，那么新来的用户直接进入得奖区
    6，如果购买数不足以进入得奖区的用户，进入候选区
    7，如果候选区购买数最多的用户，已经足以进入得奖区，
     该用户就会替换得奖区中购买数最少的用户（大于才能替换），
     如果得奖区中购买数最少的用户有多个，就替换最早进入得奖区的用户
     如果候选区中购买数最多的用户有多个，机会会给最早进入候选区的用户
    8，候选区和得奖区是两套时间，
         因用户只会在其中一个区域，所以只会有一个区域的时间，另一个没有
         从得奖区出来进入候选区的用户，得奖区时间删除，
         进入候选区的时间就是当前事件的时间（可以理解为arr[i]和op[i]中的i）
         从候选区出来进入得奖区的用户，候选区时间删除，
         进入得奖区的时间就是当前事件的时间（可以理解为arr[i]和op[i]中的i
    9，如果某用户购买数==0，不管在哪个区域都离开，区域时间删除，
         离开是指彻底离开，哪个区域也不会找到该用户
         如果下次该用户又发生购买行为，产生>0的购买数，
         会再次根据之前规则回到某个区域中，进入区域的时间重记

    请遍历arr数组和op数组，遍历每一步输出一个得奖名单
    public List<List<Integer>>  topK (int[] arr, boolean[] op, int k)

 */
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