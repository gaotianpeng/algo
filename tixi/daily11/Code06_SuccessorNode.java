package tixi.daily11;
import java.util.ArrayList;
import java.util.List;

/*
    后继节点，即中序遍历某节点的下一个节点，即为某节点的后继节点

    二叉树结构如下定义：
    Class Node {
        V value;
        Node left;
        Node right;
        Node parent;
    }
    给你二叉树中的某个节点，返回该节点的后继节点
 */
public class Code06_SuccessorNode {
    public static class Node {
        public int value;
        public Node left;
        public Node right;
        public Node parent;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Node getSuccessorNode(Node node) {
        if (node == null) {
            return null;
        }

        if (node.right != null) {
            return getLeftMost(node.right);
        } else {
            Node parent = node.parent;
            while (parent != null && parent.right == node) {
                node = parent;
                parent = parent.parent;
            }

            return parent;
        }
    }

    public static Node getLeftMost(Node node) {
        if (node == null) {
            return null;
        }

        while (node.left != null) {
            node = node.left;
        }

        return node;
    }


    /*
        for test
     */
    public static Node test(Node node, Node root) {
        if (node == null) {
            return null;
        }

        List<Node> nodes = new ArrayList<>();
        inorder(root, nodes);
        int cnt = 0;
        Node ans = null;
        int i = 0;
        for(; i < nodes.size(); i++) {
            if (nodes.get(i) == node) {
                break;
            }
        }

        if (nodes.size() == 0 || nodes.size() == 1) {
            return null;
        }
        
        return i == nodes.size() - 1 ? null : nodes.get(i+1);
    }

    private static void inorder(Node node, List<Node> ans) {
        if (node == null) {
            return;
        }

        inorder(node.left, ans);
        ans.add(node);
        inorder(node.right, ans);
    }

    private static List<Node> getInorderListOfBT(Node root) {
        List<Node> ans = new ArrayList<>();
        inorder(root, ans);
        return ans;
    }

    private static Node copyBT(Node root) {
        if (root == null) {
            return null;
        }

        Node new_root = new Node(root.value);
        if (root.left != null) {
            new_root.left = copyBT(root.left);
        }
        if (root.right != null) {
            new_root.right = copyBT(root.right);
        }
        return new_root;
    }

    public static Node generateRandomBT(int max_level, int max_val) {
        return generate(0, max_level, max_val);
    }

    private static Node generate(int cur_level, int max_level, int max_val) {
        if (cur_level > max_level || Math.random() > 0.5) {
            return null;
        }

        Node node = new Node(randomValue(max_val));
        node.parent = null;
        node.left = generate(cur_level + 1, max_level, max_val);
        if (node.left != null) {
            node.left.parent = node;
        }
        node.right = generate( cur_level + 1, max_level, max_val);
        if (node.right != null) {
            node.right.parent = node;
        }
        return node;
    }

    private static int randomValue(int max_val) {
        return (int)(Math.random() * (max_val + 1));
    }

    private static boolean isBTEqual(Node root1, Node root2) {
        if (root1 == null && root2 == null) {
            return true;
        }

        if (root1 == null && root2 != null) {
            return false;
        }

        if (root1 != null && root2 == null) {
            return false;
        }

        return root1.value == root2.value &&
                isBTEqual(root1.left, root1.left) && isBTEqual(root1.right, root2.right);
    }

    private static boolean isNodeEqual(Node node1, Node node2) {
        if (node1 == null && node2 == null) {
            return true;
        }

        if (node1 != null && node2 == null) {
            return false;
        }

        if (node1 == null && node2 != null) {
            return false;
        }

        return node1.value == node2.value;
    }

    private static void print(List<Node> tree) {
        for (Node node: tree) {
            System.out.print(node.value + " ");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int maxLevel = 20;
        int maxVal = 30;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node root1 = generateRandomBT(maxLevel, maxVal);
            Node root2 = copyBT(root1);

            List<Node> list1 = getInorderListOfBT(root1);
            List<Node> list2 = getInorderListOfBT(root2);
            if (root1 != null) {
                int random = (int)(Math.random()*list1.size());
                Node node1 = getSuccessorNode(list1.get(random));
                Node node2 = test(list2.get(random), root2);
                if (!isNodeEqual(node1, node2)) {
                    System.out.println(random);
                    print(list1);
                    print(list2);
                    System.out.println(node1);
                    System.out.println(node2);
                    if (node1 != null) {
                        System.out.println("node1 " + node1.value);
                    } else {
                        System.out.println("node1 null" );
                    }
                    if (node2 != null) {
                        System.out.println("node2 " + node2.value);
                    } else {
                        System.out.println("node2 null" );
                    }
                    success = false;
                    break;
                }
            }
        }
        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }

}
