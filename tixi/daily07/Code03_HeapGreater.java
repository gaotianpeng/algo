package tixi.daily07;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Code03_HeapGreater {

    public static class HeapGreater<T> {
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
            swap(0, heapSize -1);
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
    }

    public static class Heap<T> {
        private ArrayList<T> heap;
        private Comparator<? super T> comp;

        public Heap(Comparator<T> c) {
            heap = new ArrayList<>();
            comp = c;
        }

        public boolean isEmpty() {
            return heap.isEmpty();
        }

        public int size() {
            return heap.size();
        }

        public boolean contains(T obj) {
            return heap.contains(obj);
        }

        public T peek() {
            return heap.isEmpty() ? null : heap.get(0);
        }

        public void push(T obj) {
            heap.add(obj);
            Collections.sort(heap, comp);
        }

        public T pop() {
            if (heap.isEmpty()) {
                return null;
            }
            T result = heap.get(0);
            heap.remove(0);
            Collections.sort(heap, comp);
            return result;
        }

        public void remove(T obj) {
            if (heap.contains(obj)) {
                heap.remove(obj);
                Collections.sort(heap, comp);
            }
        }

        public void resign(T obj) {
            if (heap.contains(obj)) {
                Collections.sort(heap, comp);
            }
        }

        public List<T> getAllElements() {
            return new ArrayList<>(heap);
        }
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

        @Override
        public String toString() {
            return "Student{" +
                    "classNo=" + classNo +
                    ", age=" + age +
                    ", id=" + id +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Student student = (Student) o;
            return classNo == student.classNo && age == student.age && id == student.id;
        }

        @Override
        public int hashCode() {
            int result = Integer.hashCode(classNo);
            result = 31 * result + Integer.hashCode(age);
            result = 31 * result + Integer.hashCode(id);
            return result;
        }
    }


    public static class StudentComparator implements Comparator<Student> {
        @Override
        public int compare(Student o1, Student o2) {
            return o1.age - o2.age;
        }
    }

    public static boolean isEqual(List<Student> ls1, List<Student> ls2) {
        if (ls1 == null && ls2 == null) {
            return true;
        }
        if (ls1 == null || ls2 == null) {
            return false;
        }
        if (ls1.size() != ls2.size()) {
            return false;
        }

        for (int i = 0; i < ls1.size(); ++i) {
            if (!ls1.get(i).equals(ls2.get(i))) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int testTimes = 10000;
        boolean success = true;

        for (int i = 0; i < testTimes; ++i) {
            Random random = new Random();
            if (!success) {
                break;
            }
            Comparator<Student> comparator = new StudentComparator();
            HeapGreater<Student> simpleHeap = new HeapGreater<>(comparator);
            HeapGreater<Student> heapGreater = new HeapGreater<>(comparator);
            for (int j = 0; j < 10000; j++) {
                int classNo = random.nextInt(100);
                int age = random.nextInt(100);
                int id = random.nextInt(1000000);
                Student student = new Student(classNo, age, id);
                simpleHeap.push(student);
                heapGreater.push(student);

                if (Math.random() < 0.66) {
                    Student simpleHeapPeeked = simpleHeap.peek();
                    Student heapGreaterPeeked = heapGreater.peek();

                    if (!simpleHeapPeeked.equals(heapGreaterPeeked)) {
                        success = false;
                        break;
                    }
                    Student simpleHeapPopped = simpleHeap.pop();
                    Student heapGreaterPopped = heapGreater.pop();
                    if (!simpleHeapPopped.equals(heapGreaterPopped)) {
                        success = false;
                        break;
                    }
                }

                if (Math.random() < 0.99) {
                    boolean simpleHeapContains = simpleHeap.contains(student);
                    boolean heapGreaterContains = heapGreater.contains(student);
                    if (simpleHeapContains != heapGreaterContains) {
                        success = false;
                        break;
                    }

                    if (simpleHeapContains) {
                        simpleHeap.remove(student);
                    }

                    if (heapGreaterContains) {
                        heapGreater.remove(student);
                    }
                }
            }

            if (simpleHeap.size() != heapGreater.size()) {
                success = false;
                break;
            }

            List<Student> simpleHeapAllElements = simpleHeap.getAllElements();
            List<Student> heapGreaterAllElements = heapGreater.getAllElements();
            if (!isEqual(simpleHeapAllElements, heapGreaterAllElements)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "test success" : "test failed");
        System.out.println("test end");
    }
}
