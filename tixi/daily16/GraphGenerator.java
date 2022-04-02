package tixi.daily16;

public class GraphGenerator {
    public Graph graphGenerator(int[][] matrix) {
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
                Node from_node = graph.nodes.get(from);
                Node to_node = graph.nodes.get(to);
                Edge new_edge = new Edge(weight, from_node, to_node);
                from_node.nexts.add(to_node);
                from_node.out++;
                from_node.in++;
                to_node.in++;
                from_node.edges.add(new_edge);
                graph.edges.add(new_edge);
            }
        }
        return graph;
    }
}
