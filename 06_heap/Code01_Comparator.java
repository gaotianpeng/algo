package heap;

import java.util.Arrays;
import java.util.Comparator;

public class Code01_Comparator {
    public static class Student {
        public String name;
        public int id;
        public int age;

        public Student(String name, int id, int age) {
            this.name = name;
            this.id = id;
            this.age = age;
        }
    }

    // 任何比较器：
    // compare方法里，遵循一个统一的规范：
    // 返回负数的时候，认为第一个参数应该排在前面
    // 返回正数的时候，认为第二个参数应该排在前面
    // 返回0的时候，认为无所谓谁放前面
    public static class IdAscendAgeDescendOrder implements Comparator<Student> {
        // 根据id从小到大, 但是如果id一样, 按照年龄从大到小
        @Override
        public int compare(Student stu1, Student stu2) {
            return stu1.id != stu2.id ? (stu1.id - stu2.id) : (stu2.age - stu1.age);
        }
    }

    public static class IdAscendComparator implements Comparator<Student> {
        @Override
        public int compare(Student stu1, Student stu2) {
            return stu1.id - stu2.id;
        }
    }

    public static class IdDescendComparator implements Comparator<Student> {
        @Override
        public int compare(Student stu1, Student stu2) {
            return stu2.id - stu1.id;
        }
    }

    public static void printStudents(Student[] students) {
        for (Student stu : students) {
            System.out.println("Name: " + stu.name + ", Id: " + stu.id
                    + ", Age : " + stu.age);
        }
    }

    public static void printArray(Integer[] arr) {
        if (arr == null) {
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static class MyComp implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    }

    public static class MyComp2 implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1 - o2;
        }
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        Integer[] arr = {5, 4, 3, 2, 7, 9};
        Integer[] arr1 = {5, 4, 3, 2, 7, 9};
        printArray(arr);
        Arrays.sort(arr);
        printArray(arr);
        printArray(arr1);
        Arrays.sort(arr1, new MyComp2());
        printArray(arr1);

        System.out.println("===========================");
        Student student1 = new Student("A", 4, 40);
        Student student2 = new Student("B", 4, 21);
        Student student3 = new Student("C", 3, 12);
        Student student4 = new Student("D", 3, 62);
        Student student5 = new Student("E", 3, 42);
        // D E C A B

        Student[] students = new Student[] { student1, student2, student3, student4, student5 };
        System.out.println("第一条打印");

        Arrays.sort(students, new IdAscendComparator());
        for (int i = 0; i < students.length; i++) {
            Student s = students[i];
            System.out.println(s.name + "," + s.id + "," + s.age);
        }

        System.out.println("test end");
    }
}
