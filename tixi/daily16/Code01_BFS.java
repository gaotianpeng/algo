package tixi.daily16;

import java.util.*;

public class Code01_BFS {
    // 从node出发, 进行宽度优先遍历
    public static List<Integer> bfs(Node start) {
        if (start == null) {
            return null;
        }

        List<Integer> ans = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> set = new HashSet<>();
        queue.add(start);
        set.add(start);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            ans.add(cur.value);
            for (Node next : cur.nexts) {
                if (!set.contains(next)) {
                    set.add(next);
                    queue.add(next);
                }
            }
        }

        return ans;
    }
}
