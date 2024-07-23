package tixi.daily14;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 *  如果两个user，a字段一样，或者b字段一样，或者c字段一样，就认为是一个人
    将合并users，返回合并之后的用户数量
 */
public class Code06_MergeUsers {
    public static class User {
        public String a;
        public String b;
        public String c;

        public User(String a, String b, String c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    public static class UnionFind {
        private Map<String, String> parent;

        public UnionFind() {
            parent = new HashMap<>();
        }

        public String find(String s) {
            if (!parent.containsKey(s)) {
                parent.put(s, s);
            }
            if (!s.equals(parent.get(s))) {
                parent.put(s, find(parent.get(s)));
            }
            return parent.get(s);
        }

        public void union(String s1, String s2) {
            String root1 = find(s1);
            String root2 = find(s2);
            if (!root1.equals(root2)) {
                parent.put(root1, root2);
            }
        }
    }

    public static int mergeUsers(List<User> users) {
        UnionFind uf = new UnionFind();
        for (User user : users) {
            uf.union(user.a, user.b);
            uf.union(user.b, user.c);
            uf.union(user.a, user.c);
        }

        Set<String> uniqueUsers = new HashSet<>();
        for (User user : users) {
            uniqueUsers.add(uf.find(user.a));
            uniqueUsers.add(uf.find(user.b));
            uniqueUsers.add(uf.find(user.c));
        }

        return uniqueUsers.size();
    }

    public static void main(String[] args) {
        List<User> users = Arrays.asList(
            new User("a1", "b1", "c1"),
            new User("a2", "b2", "c2"),
            new User("a1", "b3", "c3"),
            new User("a4", "b1", "c4")
        );

        int result = mergeUsers(users);
        System.out.println("Merged user count: " + result);
    }

}
