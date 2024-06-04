package tixi.daily07;

import java.rmi.StubNotFoundException;
import java.sql.Array;
import java.sql.Struct;
import java.util.*;

// 小根堆
public class HeapGreater<T> {
    private int heapSize;
    private ArrayList<T> heap;
    private HashMap<T, Integer> indexMap;
    private Comparator<? super T> comp;

    public HeapGreater(Comparator<T> c) {
        heapSize = 0;
        heap = new ArrayList<>();
        indexMap = new HashMap<>();
        comp = c;
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    public int size() {
        return heapSize;
    }

    public boolean contains(T obj) {
        return indexMap.containsKey(obj);
    }

    public T peek() {
        return heap.get(0);
    }

    public void push(T obj) {
        heap.add(obj);
        indexMap.put(obj, heapSize);
        heapInsert(heapSize++);
    }

    public T pop() {
        T ans = heap.get(0);
        swap(0, heapSize-1);
        indexMap.remove(ans);
        heap.remove(--heapSize);
        heapify(0);
        return ans;
    }

    public void remove(T obj) {
        T replace = heap.get(heapSize - 1);
        int index = indexMap.get(obj);
        indexMap.remove(obj);
        heap.remove(--heapSize);
        if (obj != replace) {
            heap.set(index, replace);
            indexMap.put(replace, index);
            resign(replace);
        }
    }

    public void resign(T obj) {
        heapify(indexMap.get(obj));
        heapInsert(indexMap.get(obj));
    }

    public List<T> getAllElements() {
        List<T> ans = new ArrayList<>();
        for (T cur: heap) {
            ans.add(cur);
        }
        return ans;
    }

    private void heapInsert(int index) {
        while (comp.compare(heap.get(index), heap.get((index-1)/2)) < 0) {
            swap(index, (index-1) / 2);
            index = (index-1)/2;
        }
    }

    private void heapify(int index) {
        int left = 2 * index + 1;
        while (left < heapSize) {
            int best = left + 1 < heapSize && comp.compare(heap.get(left + 1), heap.get(left)) < 0
                    ? left + 1 : left;
            best = comp.compare(heap.get(index), heap.get(best)) < 0? index : best;
            if (best == index) {
                break;
            }
            swap(index, best);
            index = best;
            left = index * 2 + 1;
        }
    }

    private void swap(int i, int j) {
        T o1 = heap.get(i);
        T o2 = heap.get(j);
        heap.set(i, o2);
        heap.set(j, o1);
        indexMap.put(o1, j);
        indexMap.put(o2, i);
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