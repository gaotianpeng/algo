package tixi.daily14;

import java.util.HashSet;

/*
    给定一个字符串str，只由‘X’和‘.’两种字符构成。
    ‘X’表示墙，不能放灯，也不需要点亮
    ‘.’表示居民点，可以放灯，需要点亮
    如果灯放在i位置，可以让i-1，i和i+1三个位置被点亮
    返回如果点亮str中所有需要点亮的位置，至少需要几盏灯
 */
public class Code01_Light {

    public static int minLight(String road) {
        if (road == null || road.length() == 0) {
            return 0;
        }

        char[] str = road.toCharArray();
        int i = 0;
        int lights = 0;
        while (i < str.length) {
            if (str[i] == 'X') {
                i++;
            } else {
                lights++;
                if (i + 1 == str.length) {
                    break;
                } else {
                    if (str[i+1] == 'X') {
                        i = i + 2;
                    } else {
                        i = i + 3;
                    }
                }
            }
        }
        return lights;
    }

    /*
        for test
     */
    public static int test(String road) {
        if (road == null || road.length() == 0) {
            return 0;
        }

        return process(road.toCharArray(), 0, new HashSet<Integer>());
    }

    /*
        str[index...]位置，自由选择放灯还是不放灯
        str[0...index-1]已经决定了哪些位置放灯, 存放在lights里
        选出能照亮所有.的方案，并且在这些有效方案中返回至少需要几个灯
     */
    private static int process(char[] str, int index, HashSet<Integer> lights) {
        if (index == str.length) {
            for (int i = 0; i < str.length; i++) {
                if (str[i] == '.') {
                    if (!lights.contains(i-1) && !lights.contains(i)
                            && !lights.contains(i+1)) {
                        return Integer.MAX_VALUE;
                    }
                }
            }

            return lights.size();
        } else {
            int no = process(str, index +1, lights);
            int yes = Integer.MAX_VALUE;
            if (str[index] == '.') {
                lights.add(index);
                yes = process(str, index + 1, lights);
                lights.remove(index);
            }

            return Math.min(no, yes);
        }
    }


    public static String randomString(int len) {
        char[] ans = new char[(int)(Math.random()*len) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = Math.random() < 0.5 ? 'X' : '.';
        }

        return String.valueOf(ans);
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int test_times = 100000;
        int max_str_len = 20;
        boolean success = true;

        for (int i = 0; i < test_times; i++) {
            String road = randomString(max_str_len);
            if (minLight(road) != test(road)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
