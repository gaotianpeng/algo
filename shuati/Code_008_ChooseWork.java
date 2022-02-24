package shuati;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;

/*
    给定数组hard和money，长度都为N
    hard[i]表示i号的难度， money[i]表示i号工作的收入
    给定数组ability，长度都为M，ability[j]表示j号人的能力
    每一号工作，都可以提供无数的岗位，难度和收入都一样
    但是人的能力必须>=这份工作的难度，才能上班
    返回一个长度为M的数组ans，ans[j]表示j号人能获得的最好收入
 */
public class Code_008_ChooseWork {
    public static class Job {
        public int money;
        public int hard;

        public Job(int m, int h) {
            money = m;
            hard = h;
        }
    }

    public static class JobComparator implements Comparator<Job> {
        @Override
        public int compare(Job o1, Job o2) {
            return o1.hard != o2.hard ? (o1.hard - o2.hard) : (o2.money - o1.money);
        }
    }

    public static int[] getMoneys(Job[] job, int[] ability) {
        Arrays.sort(job);
        // <hard, money>
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(job[0].hard, job[0].money);
        Job pre = job[0];
        for (int i = 1; i < job.length; i++) {
            if (job[i].hard != pre.hard && job[i].money > pre.money) {
                pre = job[i];
                map.put(pre.hard, pre.money);
            }
        }

        int[] ans = new int[ability.length];
        for (int i = 0; i < ability.length; i++) {
            Integer key = map.floorKey(ability[i]);
            ans[i] = key != null ? map.get(key) : 0;
        }

        return ans;
    }
}
