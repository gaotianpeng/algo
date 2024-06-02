package tixi.daily11;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomTreeGenerator {

    public static class Node {
        public int val;
        public List<Node> children;

        public Node() {
            this.children = new ArrayList<>();
        }

        public Node(int _val) {
            this.val = _val;
            this.children = new ArrayList<>();
        }

        public Node(int _val, List<Node> _children) {
            this.val = _val;
            this.children = _children;
        }
    }

    private static Random random = new Random();

    // Generate a random multiway tree
    public static Node generateRandomTree(int maxDepth, int maxChildren) {
        return generateRandomTree(0, maxDepth, maxChildren);
    }

    // Recursive helper method to generate a tree
    private static Node generateRandomTree(int currentDepth, int maxDepth, int maxChildren) {
        if (currentDepth > maxDepth) {
            return null;
        }

        Node node = new Node(random.nextInt(100));
        int numChildren = random.nextInt(maxChildren + 1);

        for (int i = 0; i < numChildren; i++) {
            Node child = generateRandomTree(currentDepth + 1, maxDepth, maxChildren);
            if (child != null) {
                node.children.add(child);
            }
        }

        return node;
    }

    // Print the tree for visualization
    public static void printTree(Node node, int level) {
        if (node == null) {
            return;
        }
        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }
        System.out.println(node.val);
        for (Node child : node.children) {
            printTree(child, level + 1);
        }
    }

    public static void main(String[] args) {
        int maxDepth = 3; // Maximum depth of the tree
        int maxChildren = 3; // Maximum number of children for each node

        Node root = generateRandomTree(maxDepth, maxChildren);
        printTree(root, 0);
    }
}
