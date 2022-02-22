package shuati;

import java.io.File;
import java.util.Stack;

/*
    给定一个文件目录的路径，
    写一个函数统计这个目录下所有的文件数量并返回
    隐藏文件也算，但是文件夹不算
 */
public class Code_002_CountFiles {
    public static int getFileNumber(String path) {
        File root = new File(path);
        if (!root.isDirectory() && !root.isFile()) {
            return 0;
        }

        if (root.isFile()) {
            return 1;
        }

        Stack<File> stack = new Stack<>();
        stack.add(root);
        int files = 0;
        while (!stack.isEmpty()) {
            File folder = stack.pop();
            for (File item: folder.listFiles()) {
                if (item.isFile()) {
                    ++files;
                }

                if (item.isDirectory()) {
                    stack.push(item);
                }
            }
        }

        return files;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        System.out.println(getFileNumber("C:\\work\\algo"));
        System.out.println("test end...");
    }
}
