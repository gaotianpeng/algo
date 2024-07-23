package tixi.daily14;

import java.util.Arrays;
import java.util.Comparator;

/*
    一些项目要占用一个会议室宣讲，会议室不能同时容纳两个项目的宣讲。
    给你每一个项目开始的时间和结束的时间
    你来安排宣讲的日程，要求会议室进行的宣讲的场次最多。
    返回最多的宣讲场次
 */
public class Code03_BestArrange {
    public static class Program {
        public int start;
        public int end;

        public Program(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static int bestArrange(Program[] programs) {
        if (programs == null || programs.length == 0) {
            return 0;
        }

        Arrays.sort(programs, new ProgramComparator());
        int timeLine = 0;
        int result = 0;
        for (int i = 0; i < programs.length; ++i) {
            if (timeLine <= programs[i].start) {
                result++;
                timeLine = programs[i].end;
            }
        }

        return result;
    }

    public static class ProgramComparator implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            return o1.end - o2.end;
        }
    }

    // for test
    public static int test(Program[] programs) {
        if (programs == null || programs.length == 0) {
            return 0;
        }

        return process(programs, 0, 0);
    }

    /*
     *  programs: 还剩什么会义没安排
     *  done: 之前已经安排了多少会议数量
     *  timeLine：当前来到的时间点
     */
    // 当前来到timeLine时间点，已经安排了done个会议，剩下的会议programs可以自由安排
    // 返回能安排的最多会议数量
    public static int process(Program[] programs, int done, int timeLine) {
        if (programs.length == 0) {
            return done;
        }

        int max = done;
        for (int i = 0; i < programs.length; ++i) {
            if (programs[i].start >= timeLine) {
                Program[] next = copyButExcept(programs, i);
                max = Math.max(max, process(next, done + 1, programs[i].end));
            }
        }

        return max;
    }

    public static Program[] copyButExcept(Program[] programs, int i) {
        Program[] ans = new Program[programs.length - 1];
        int index = 0;
        for (int k = 0; k < programs.length; k++) {
            if (k != i) {
                ans[index++] = programs[k];
            }
        }
        return ans;
    }

    public static Program[] generatePrograms(int programSize, int timeMax) {
        Program[] ans = new Program[(int) (Math.random() * (programSize + 1))];
        for (int i = 0; i < ans.length; i++) {
            int r1 = (int) (Math.random() * (timeMax + 1));
            int r2 = (int) (Math.random() * (timeMax + 1));
            if (r1 == r2) {
                ans[i] = new Program(r1, r1 + 1);
            } else {
                ans[i] = new Program(Math.min(r1, r2), Math.max(r1, r2));
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int programSize = 12;
        int timeMax = 20;
        int timeTimes = 100000;
        for (int i = 0; i < timeTimes; ++i) {
            Program[] programs = generatePrograms(programSize, timeMax);
            if (bestArrange(programs) != test(programs)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "test success" : "test failed");
        System.out.println("test end");
    }
}
