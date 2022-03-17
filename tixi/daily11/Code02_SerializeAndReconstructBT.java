package tixi.daily11;
import sun.awt.image.ImageWatched;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Code02_SerializeAndReconstructBT {
    /*
     * 二叉树可以通过先序、后序或者按层遍历的方式序列化和反序列化，
     * 以下代码全部实现了。
     * 但是，二叉树无法通过中序遍历的方式实现序列化和反序列化
     * 因为不同的两棵树，可能得到同样的中序序列，即便补了空位置也可能一样。
     * 比如如下两棵树
     *         __2
     *        /
     *       1
     *       和
     *       1__
     *          \
     *           2
     * 补足空位置的中序遍历结果都是{ null, 1, null, 2, null}
     *
     * */
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Queue<String> preSerialize(Node head) {
        Queue<String> ans = new LinkedList<>();
        preSer(head, ans);
        return ans;
    }

    private static void preSer(Node head, Queue<String> ans) {
        if (head == null) {
            ans.add(null);
            return;
        }

        ans.add(String.valueOf(head.value));
        preSer(head.left, ans);
        preSer(head.right, ans);
    }

    public static Node preDeserialize(Queue<String> pre_list) {
        String val = pre_list.poll();
        if (val == null) {
            return null;
        }
        Node node = new Node(Integer.valueOf(val));
        node.left = preDeserialize(pre_list);
        node.right = preDeserialize(pre_list);
        return node;
    }

    public static Queue<String> postSerialize(Node head) {
        Queue<String> ans = new LinkedList<>();
        postSer(head, ans);
        return ans;
    }

    private static void postSer(Node head, Queue<String> ans) {
        if (head == null) {
            ans.add(null);
            return;
        }

        postSer(head.left, ans);
        postSer(head.right, ans);
        ans.add(String.valueOf(head.value));
    }

    public static Node postDeserialize(Queue<String> post_list) {
        if (post_list == null || post_list.size() == 0) {
            return null;
        }
        Stack<String> stack = new Stack<>();
        // 左右中 --> 中左右
        while (!post_list.isEmpty()) {
            stack.push(post_list.poll());
        }
        return postDe(stack);
    }

    public static Node postDe(Stack<String> stack) {
        String val = stack.pop();
        if (val == null) {
            return null;
        }

        Node node = new Node(Integer.valueOf(val));
        node.right = postDe(stack);
        node.left = postDe(stack);
        return node;
    }

    public static Queue<String> levelSerialize(Node head) {
        Queue<String> ans = new LinkedList<>();
        if (head == null) {
            ans.add(null);
            return ans;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        ans.add(String.valueOf(head.value));
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node.left != null) {
                ans.add(String.valueOf(node.left.value));
                queue.add(node.left);
            } else {
                ans.add(null);
            }

            if (node.right != null) {
                ans.add(String.valueOf(node.right.value));
                queue.add(node.right);
            } else {
                ans.add(null);
            }
        }

        return ans;
    }

    public static Node levelDeserialize(Queue<String> level_list) {
        if (level_list == null || level_list.size() == 0) {
            return null;
        }

        Node head = generateNode(level_list.poll());
        Queue<Node> queue = new LinkedList<>();
        if (head != null) {
            queue.add(head);
        }

        Node node = null;
        while (!queue.isEmpty()) {
            node = queue.poll();
            node.left = generateNode(level_list.poll());
            node.right = generateNode(level_list.poll());
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }

        return head;
    }

    private static Node generateNode(String val) {
        if (val == null) {
            return null;
        }

        return new Node(Integer.valueOf(val));
    }

    /*
        for test
     */
    public static Node generateRandomBT(int max_level, int max_val) {
        return generateBT(1, max_level, max_val);
    }

    private static Node generateBT(int cur_level, int max_level, int max_val) {
        if (cur_level > max_level || Math.random() > 0.5) {
            return null;
        }

        Node node = new Node(randomVal(max_val));
        node.left = generateBT(cur_level + 1, max_level, max_val);
        node.right = generateBT(cur_level + 1, max_level, max_val);
        return node;
    }

    private static int randomVal(int max_val) {
        return (int)(Math.random() * (max_val + 1));
    }

    public static boolean isEqual(Node root1, Node root2) {
        if (root1 == null && root2 == null) {
            return true;
        }

        if ((root1 != null && root2 == null)
                || (root1 == null && root2 != null)) {
            return false;
        }

        if (root1.value != root2.value) {
            return false;
        }

        return  root1.value == root2.value &&
                isEqual(root1.left, root2.left) && isEqual(root1.right, root2.right);
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int test_times = 100000;
        int max_level = 20;
        int max_val = 30;
        for (int i = 0; i < test_times; i++) {
            Node tree = generateRandomBT(max_level, max_val);
            Queue<String> pre_list = preSerialize(tree);
            Queue<String> post_list = postSerialize(tree);
            Queue<String> level_list = levelSerialize(tree);

            Node pre_tree = preDeserialize(pre_list);
            Node post_tree = postDeserialize(post_list);
            Node level_tree = levelDeserialize(level_list);

            if (!isEqual(tree, pre_tree)) {
                System.out.println("-------------------- pre error");
                success = false;
                break;
            }

            if (!isEqual(tree, post_tree)) {
                System.out.println("-------------------- post error");
                success = false;
                break;
            }

            if (!isEqual(tree, level_tree)) {
                System.out.println("-------------------- level error");
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
