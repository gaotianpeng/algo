package tixi.daily10;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/*
    非递归先序遍历二叉树
 */
public class Code03_UnRecursivePreTraverseBT {
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int v) {
            val = v;
            left = null;
            right = null;
        }
    }

    public static List<Integer> preOrder(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root != null) {
            Stack<TreeNode> stack = new Stack<>();
            stack.add(root);
            while (!stack.isEmpty()) {
                TreeNode node = stack.pop();
                ans.add(node.val);
                if (node.right != null) {
                    stack.add(node.right);
                }
                if (node.left != null) {
                    stack.add(node.left);
                }
            }
        }
        return ans;
    }

    /*
        for test
     */
    public static List<Integer> test(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        process(root, ans);
        return ans;
    }

    public static void process(TreeNode root, List<Integer> ans) {
        if (root == null) {
            return;
        }

        ans.add(root.val);
        process(root.left, ans);
        process(root.right, ans);
    }

    public static TreeNode generateRandomBT(int max_val, int max_level) {
        return generate(1, max_level, max_val);
    }

    public static TreeNode generate(int level, int max_level, int max_val) {
        if (level > max_level || Math.random() > 0.5) {
            return null;
        }

        TreeNode node = new TreeNode(randomVal(max_val));
        node.left = generate(level + 1, max_level, max_val);
        node.right = generate(level + 1, max_level, max_val);
        return node;
    }

    public static int randomVal(int max_val) {
        return (int)(Math.random() * (max_val + 1));
    }

    public static boolean isEqual(List<Integer> order1, List<Integer> order2) {
        if (order1 == null && order2 == null) {
            return true;
        }

        if ((order1 != null && order2 == null) || (order1 == null && order2 != null)) {
            return false;
        }

        if (order1.size() != order2.size()) {
            return false;
        }

        for (int i = 0; i < order1.size(); i++) {
            if (order1.get(i) != order2.get(i)) {
                return false;
            }
        }
        return true;
    }

    public static void print(List<Integer> order) {
        for (Integer i: order) {
            System.out.print(" " + i);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int test_times = 1000000;
        int max_level = 20;
        int max_val = 100;
        for (int i = 0; i < test_times; i++) {
            TreeNode root = generateRandomBT(max_val, max_level);
            List<Integer> order1 = preOrder(root);
            List<Integer> order2 = test(root);
            if (!isEqual(order1, order2)) {
                print(order1);
                print(order2);
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
