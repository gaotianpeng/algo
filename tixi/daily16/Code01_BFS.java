package tixi.daily16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class Code01_BFS {
    public static class Node {
        public int value;
        public int in;
        public int out;
        public ArrayList<Node> nexts;
        public ArrayList<Edge> edges;

        public Node(int value) {
            this.value = value;
            in = 0;
            out = 0;
            nexts = new ArrayList<>();
            edges = new ArrayList<>();
        }
    }

    public static class Edge {
        public int weight;
        public Node from;
        public Node to;

        public Edge(int weight, Node from, Node to) {
            this.weight = weight;
            this.from = from;
            this.to = to;
        }
    }

    public static class Graph {
        public HashMap<Integer, Node> nodes;
        public HashSet<Edge> edges;

        public Graph() {
            nodes = new HashMap<>();
            edges = new HashSet<>();
        }
    }

    public static Graph graphGenerator(int[][] matrix) {
        if (matrix == null) {
            return null;
        }

        Graph graph = new Graph();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    continue;
                }
                int weight = matrix[i][j];
                int from = i;
                int to = j;
                if (!graph.nodes.containsKey(from)) {
                    graph.nodes.put(from, new Node(from));
                }
                if (!graph.nodes.containsKey(to)) {
                    graph.nodes.put(to, new Node(to));
                }
                Node fromNode = graph.nodes.get(from);
                Node toNode = graph.nodes.get(to);
                Edge newEdge = new Edge(weight, fromNode, toNode);
                fromNode.nexts.add(toNode);
                fromNode.out++;
                fromNode.in++;
                toNode.in++;
                fromNode.edges.add(newEdge);
                graph.edges.add(newEdge);
            }
        }
        return graph;
    }

    // 从node出发, 进行宽度优先遍历
    // 返回节点值，及次数
    public static HashMap<Integer, Integer> bfs(Node start) {
        if (start == null) {
            return null;
        }

        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> set = new HashSet<>();
        HashMap<Integer, Integer> ans = new HashMap<>();
        queue.add(start);
        set.add(start);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            // 访问
            if (ans.containsKey(cur.value)) {
                ans.put(cur.value, ans.get(cur.value) + 1);
            } else {
                ans.put(cur.value, 1);
            }

            for (Node node: cur.nexts) {
                if (!set.contains(node)) {
                    set.add(node);
                    queue.add(node);
                }
            }
        }

        return ans;
    }


    // for test
    public static int[][] randomMatrixGenerator(int maxM, int maxN, int minVal, int maxVal) {
        Random rand = new Random();
        int rows = rand.nextInt(maxM) + 1; // 随机生成 1 到 maxM 之间的行数
        int cols = rand.nextInt(maxN) + 1; // 随机生成 1 到 maxN 之间的列数

        int[][] matrix = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = rand.nextInt(maxVal - minVal + 1) + minVal; // 随机填充 minVal 到 maxVal 之间的值
            }
        }

        return matrix;
    }

    // 返回节点值，及次数
    static HashMap<Integer, Integer> dfs(Node start) {
        if (start == null) {
            return null;
        }

        Stack<Node> stack = new Stack<>();
        HashSet<Node> set = new HashSet<>();
        HashMap<Integer, Integer> ans = new HashMap<>();
        stack.push(start);
        set.add(start);
        // 访问
        ans.put(start.value, 1);
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            for (Node next: cur.nexts) {
                if (!set.contains(next)) {
                    stack.push(cur);
                    stack.push(next);
                    set.add(next);

                    // 访问
                    if (ans.containsKey(next.value)) {
                        ans.put(next.value, ans.get(next.value) + 1);
                    } else {
                        ans.put(next.value, 1);
                    }

                    break;
                }
            }
        }

        return ans;
    }

    static boolean isEqual(HashMap<Integer, Integer> map1, HashMap<Integer, Integer> map2) {
        if (map1 == null &&  map2 == null) {
            return true;
        }

        if (map1 == null || map2 == null) {
            return false;
        }

        if (map1.size() != map2.size()) {
            return false;
        }

        for (Integer key : map1.keySet()) {
            if (!map2.containsKey(key) || !map1.get(key).equals(map2.get(key))) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println("test start");
        boolean success = true;
        int testTimes = 100000;
        int maxM = 30;
        int maxN = 30;
        int minVal = 1;
        int maxVal = 100;

        for (int i = 0; i < testTimes; ++i) {
            int[][] matrix = randomMatrixGenerator(maxM, maxN, minVal, maxVal);
            Graph graph = graphGenerator(matrix);
            HashMap<Integer, Integer> ans1 = bfs(graph.nodes.get(0));
            HashMap<Integer, Integer> ans2 = dfs(graph.nodes.get(0));
            if (!isEqual(ans1, ans2)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
