package tixi.daily16;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
    拓扑排序
        1）在图中找到所有入度为0的点输出
        2）把所有入度为0的点在图中删掉，继续找入度为0的点输出，周而复始
        3）图的所有点都被删除后，依次输出的顺序就是拓扑排序

    要求：有向图且其中没有环
    应用：事件安排、编译顺序

 */
public class Code06_TopologySort {
    public static List<Node> sortedTopology(Graph graph) {
        if (graph == null) {
            return null;
        }

        HashMap<Node, Integer> in_map = new HashMap<>();
        Queue<Node> zero_in_queue = new LinkedList<>();
        for (Node cur: graph.nodes.values()) {
            in_map.put(cur, cur.in);
            if (cur.in == 0) {
                zero_in_queue.add(cur);
            }
        }

        List<Node> ans = new LinkedList<>();
        while (!zero_in_queue.isEmpty()) {
            Node res = zero_in_queue.poll();
            ans.add(res);
            for (Node next: res.nexts) {
                in_map.put(next, in_map.get(next) - 1);
                if (in_map.get(next) == 0) {
                    ans.add(next);
                }
            }
        }

        return ans;
    }
}
