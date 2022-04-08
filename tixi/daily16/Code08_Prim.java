package tixi.daily16;
/*
    最小生成树算法之Prim
        1）可以从任意节点出发来寻找最小生成树
        2）某个点加入到被选取的点中后，解锁这个点出发的所有新的边
        3）在所有解锁的边中选最小的边，然后看看这个边会不会形成环
        4）如果会，不要当前边，继续考察剩下解锁的边中最小的边，重复3）
        5）如果不会，要当前边，将该边的指向点加入到被选取的点中，重复2）
        6）当所有点都被选取，最小生成树就得到
 */

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Code08_Prim {
    public static class EdgeComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    public static Set<Edge> primMST(Graph graph) {
        // 解锁的边进入小根堆
        PriorityQueue<Edge> priority_queue = new PriorityQueue<>();
        // 哪些点被解锁出来了
        HashSet<Node> node_set = new HashSet<>();
        Set<Edge> result = new HashSet<>();
        for (Node node: graph.nodes.values()) {
            node_set.add(node);
            for (Edge edge: node.edges) {
                priority_queue.add(edge);
            }
            while (!priority_queue.isEmpty()) {
                Edge edge = priority_queue.poll();
                Node to_node = edge.to;
                if (!node_set.contains(to_node)) {
                    node_set.add(to_node);
                    result.add(edge);
                    for (Edge next_edge : to_node.edges) {
                        priority_queue.add(next_edge);
                    }
                }
            }
        }
        return result;
    }
}
