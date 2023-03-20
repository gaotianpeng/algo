package tixi.daily07;

import java.rmi.StubNotFoundException;
import java.sql.Array;
import java.sql.Struct;
import java.util.*;

// 小根堆
public class HeapGreater<T> {
    private int heap_size_;
    private ArrayList<T> heap_;
    private HashMap<T, Integer> index_map_;
    private Comparator<? super T> comp_;

    public HeapGreater(Comparator<T> c) {
        heap_size_ = 0;
        heap_ = new ArrayList<>();
        index_map_ = new HashMap<>();
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
        swap(0, heap_size_-1);
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
        heapify(index_map_.get(obj));
        heapInsert(index_map_.get(obj));
    }

    public List<T> getAllElements() {
        List<T> ans = new ArrayList<>();
        for (T cur: heap_) {
            ans.add(cur);
        }
        return ans;
    }

    private void heapInsert(int index) {
        while (comp_.compare(heap_.get(index), heap_.get((index-1)/2)) < 0) {
            swap(index, (index-1) / 2);
            index = (index-1)/2;
        }
    }

    private void heapify(int index) {
        int left = 2 * index + 1;
        while (left < heap_size_) {
            int best = left + 1 < heap_size_ && comp_.compare(heap_.get(left + 1), heap_.get(left)) < 0
                    ? left + 1 : left;
            best = comp_.compare(heap_.get(index), heap_.get(best)) < 0? index : best;
            if (best == index) {
                break;
            }
            swap(index, best);
            index = best;
            left = index * 2 + 1;
        }
    }

    private void swap(int i, int j) {
        T o1 = heap_.get(i);
        T o2 = heap_.get(j);
        heap_.set(i, o2);
        heap_.set(j, o1);
        index_map_.put(o1, j);
        index_map_.put(o2, i);
    }


    public static class Student {
        public int classNo;
        public int age;
        public int id;

        public Student(int c, int a, int i) {
            classNo = c;
            age = a;
            id = i;
        }
    }

    public static class StudentComparator implements Comparator<Student> {
        @Override
        public int compare(Student o1, Student o2) {
            return o1.age - o2.age;
        }
    }



    public static void main(String[] args) {
        System.out.println("hello world");
        PriorityQueue<Student> priority_queue = new PriorityQueue<>(new StudentComparator());
        Student s1 = null;
        Student s2 = null;
        Student s3 = null;
        Student s4 = null;
        Student s5 = null;

        s1 = new Student(2, 50, 1111);
        s2 = new Student(1, 60, 2222);
        s3 = new Student(6, 15, 3333);
        s4 = new Student(3, 17, 4444);
        s5 = new Student(7, 28, 5555);




        HeapGreater<Student>  greater = new HeapGreater<>(new StudentComparator());
        greater.push(s1);
        greater.push(s2);
        greater.push(s3);
        greater.push(s4);
        greater.push(s5);

        System.out.println(greater.peek().age);

        s1.age = 5;
        greater.resign(s1);

        System.out.println(greater.peek().age);

    }
}