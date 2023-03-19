package lintcode;

import java.util.*;

public class MinimumSpanningTree {
    /**
     * @param connections given a list of connections include two cities and cost
     * @return a list of connections from results
     */
    // 比较函数
    static Comparator<Connection> cmp = new Comparator<Connection>() {
        public int compare(Connection a, Connection b) {
            if (a.cost != b.cost) {
                return a.cost - b.cost;
            }
            if (a.city1.equals(b.city1)) {
                return a.city2.compareTo(b.city2);
            }
            return a.city1.compareTo(b.city1);
        }
    };

    // 寻找根节点
    static int Find(int x, int[] pre) {
        // 若x为根结点则返回x，否则继续递归寻找根结点,同时进行路径压缩
        if (x == pre[x]){
            return x;
        }
        else{
            return pre[x] = Find(pre[x], pre);
        }
    }

    public List<Connection> lowestCost(List<Connection> connections) {
        // 按边的权值大小进行升序排序
        Collections.sort(connections, cmp);
        List<Connection> mst = new ArrayList();
        int n = connections.size();
        // 用一个哈希表记录某城市对应的序号
        HashMap<String, Integer> mp = new HashMap<>();
        int cnt = 0;
        // 处理所有的不同城市，每个城市对应一个编号
        for (int i = 0; i < n; i++) {
            String u = connections.get(i).city1;
            String v = connections.get(i).city2;
            if (!mp.containsKey(u)) {
                mp.put(u,cnt++);
            }
            if (!mp.containsKey(v)) {
                mp.put(v,cnt++);
            }
        }
        int[] pre = new int[cnt];
        // 初始化
        for (int i = 0; i < cnt; i++) {
            pre[i] = i;
        }
        for (int i = 0; i < n; i++) {
            String u = connections.get(i).city1;
            String v = connections.get(i).city2;
            int numU = mp.get(u), numV = mp.get(v);
            // 判断两端点是否连通
            if (Find(numU, pre) == Find(numV, pre)) {
                continue;
            }
            mst.add(connections.get(i));
            pre[pre[numU]] = pre[numV];
        }
        // 判断是否全部连通
        int root = Find(0, pre);
        for (int i = 1; i < cnt; i++) {
            if (Find(i, pre) != root) {
                return new ArrayList<Connection>();
            }
        }
        return mst;
    }

    public static class Connection {
      public String city1, city2;
      public int cost;
      public Connection(String city1, String city2, int cost) {
          this.city1 = city1;
          this.city2 = city2;
          this.cost = cost;
      }
    }
    // version: 高频题班
    /**
        @param connections given a list of connections include two cities and cost
        @return a list of connections from results
     */
    int n = 0;

    public List<Connection> lowestCost1(List<Connection> connections) {
        // Write your code here
        List<Connection> ans = new ArrayList<>();
        UFS ufs = new UFS(connections.size()*2);

        Collections.sort(connections, new Comparator<Connection>() {
            public int compare(Connection a, Connection b) {
                if (a.cost != b.cost) {
                    return a.cost - b.cost;
                }
                if (a.city1.equals(b.city1)) {
                    return a.city2.compareTo(b.city2);
                }
                return a.city1.compareTo(b.city1);
            }
        });

        for (Connection item : connections) {
            int c1 = getID(item.city1);
            int c2 = getID(item.city2);
            if (ufs.find(c1) != ufs.find(c2)) {
                ans.add(item);
                ufs.union(c1, c2);
            }
        }
        if (ans.size() == n - 1) {
            return ans;
        } else {
            return new ArrayList<>();
        }
    }


    Map<String, Integer> name2ID = new HashMap<>();

    public int getID(String name) {
        if (name2ID.containsKey(name)) {
            return name2ID.get(name);
        } else {
            name2ID.put(name, n++);
            return n - 1;
        }
    }

    public static class UFS {
        int[] f;          // father

        public UFS(int n) {
            f = new int[n];
            for (int i = 0; i < n; i++) {
                f[i] = -1;
            }
        }

        public void union(int a, int b) {
            a = find(a);
            b = find(b);
            if (f[a] < f[b]) {
                f[a] += f[b];
                f[b] = a;
            } else {
                f[b] += f[a];
                f[a] = b;
            }
        }

        public int find(int x) {
            if (f[x] < 0) {
                return x;
            }
            f[x] = find(f[x]);
            return f[x];
        }
    }
}
