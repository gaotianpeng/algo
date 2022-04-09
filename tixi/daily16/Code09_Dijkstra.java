package tixi.daily16;

/*
    Dijkstra最短路径算法
        1）Dijkstra算法必须指定一个源点
        2）生成一个源点到各个点的最小距离表，一开始只有一条记录，即原点到自己的最小距离为0，源点到其他所有点的最小距离都为正无穷大
        3）从距离表中拿出没拿过记录里的最小记录，通过这个点发出的边，更新源点到各个点的最小距离表，不断重复这一步
        4）源点到所有的点记录如果都被拿过一遍，过程停止，最小距离表得到了
 */
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

public class Code09_Dijkstra {
    public static HashMap<Node, Integer> dijkstra(Node from) {
        HashMap<Node, Integer> distance_map = new HashMap<>();
        distance_map.put(from, 0);
        HashSet<Node> selected_nodes = new HashSet<>();
        Node min_node = getMinDistanceAndUnselectedNode(distance_map, selected_nodes);
        while (min_node != null) {
            int distance = distance_map.get(min_node);
            for (Edge edge: min_node.edges) {
                Node to_node = edge.to;
                if (!distance_map.containsKey(to_node)) {
                    distance_map.put(to_node, distance + edge.weight);
                } else {
                    distance_map.put(edge.to, Math.min(distance_map.get(to_node), distance + edge.weight));
                }
            }
        }
        return distance_map;
    }

    public static Node getMinDistanceAndUnselectedNode(HashMap<Node, Integer> distance_map,
                                                       HashSet<Node> touched_nodes) {
        Node min_node = null;
        int min_distance = Integer.MAX_VALUE;
        for (Entry<Node, Integer> entry: distance_map.entrySet()) {
            Node node = entry.getKey();
            int distance = entry.getValue();
            if (!touched_nodes.contains(node) && distance < min_distance) {
                min_node = node;
                min_distance = distance;
            }
        }

        return min_node;
    }

    public static class NodeRecord {
        public Node node;
        public int distance;

        public NodeRecord(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    public static class NodeHeap {
        private Node[] nodes;
        // <某一个node, 堆上的位置>
        private HashMap<Node, Integer> heap_index_map;
        // <某一个node，从源node出发到该节点的目前最小距离>
        private HashMap<Node, Integer> distance_map;
        private int size;

        public NodeHeap(int size) {
            nodes = new Node[size];
            heap_index_map = new HashMap<>();
            distance_map = new HashMap<>();
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void addOrUpdateOrIgnore(Node node, int distance) {
            if (inHeap(node)) {
                distance_map.put(node, Math.min(distance_map.get(node), distance));
                insertHeapify(node, heap_index_map.get(node));
            }
            if (!isEntered(node)) {
                nodes[size] = node;
                heap_index_map.put(node, size);
                distance_map.put(node, distance);
                insertHeapify(node, size++);
            }
        }

        public NodeRecord pop() {
            NodeRecord node_record = new NodeRecord(nodes[0], distance_map.get(nodes[0]));
            swap(0, size - 1);
            heap_index_map.put(nodes[size - 1], -1);
            distance_map.remove(nodes[size - 1]);
            // free C++同学还要把原本堆顶节点析构，对java同学不必
            nodes[size - 1] = null;
            heapify(0, --size);
            return node_record;
        }

        private void insertHeapify(Node node, int index) {
            while (distance_map.get(nodes[index]) < distance_map.get(nodes[(index - 1) / 2])) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        private void heapify(int index, int size) {
            int left = index * 2 + 1;
            while (left < size) {
                int smallest = left + 1 < size && distance_map.get(nodes[left + 1]) < distance_map.get(nodes[left])
                        ? left + 1
                        : left;
                smallest = distance_map.get(nodes[smallest]) < distance_map.get(nodes[index]) ? smallest : index;
                if (smallest == index) {
                    break;
                }
                swap(smallest, index);
                index = smallest;
                left = index * 2 + 1;
            }
        }

        private boolean isEntered(Node node) {
            return heap_index_map.containsKey(node);
        }

        private boolean inHeap(Node node) {
            return isEntered(node) && heap_index_map.get(node) != -1;
        }

        private void swap(int index1, int index2) {
            heap_index_map.put(nodes[index1], index2);
            heap_index_map.put(nodes[index2], index1);
            Node tmp = nodes[index1];
            nodes[index1] = nodes[index2];
            nodes[index2] = tmp;
        }
    }

    public static HashMap<Node, Integer> dijkstra2(Node head, int size) {
        NodeHeap node_heap = new NodeHeap(size);
        node_heap.addOrUpdateOrIgnore(head, 0);
        HashMap<Node, Integer> result = new HashMap<>();
        while (!node_heap.isEmpty()) {
            NodeRecord record = node_heap.pop();
            Node cur = record.node;
            int distance = record.distance;
            for (Edge edge: cur.edges) {
                node_heap.addOrUpdateOrIgnore(edge.to, edge.weight + distance);
            }
            result.put(cur, distance);
        }

        return result;
    }
}
