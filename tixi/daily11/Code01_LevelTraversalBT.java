package tixi.daily11;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Code01_LevelTraversalBT {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            List<Integer> curAns = new ArrayList<>();
            int curLevelSize = queue.size();
            for (int i = 0; i < curLevelSize; ++i) {
                TreeNode node = queue.poll();
                curAns.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            ans.add(curAns);
        }

        return ans;
    }

    /*
        for test
     */
    public static List<List<Integer>> levelOrderTest(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }
        levelOrderHelper(root, 0, ans);
        return ans;
    }

    private static void levelOrderHelper(TreeNode node, int level, List<List<Integer>> ans) {
        if (node == null) {
            return;
        }

        // If we reach a new level, add a new list for that level
        if (ans.size() == level) {
            ans.add(new ArrayList<>());
        }

        // Add the current node value to its corresponding level
        ans.get(level).add(node.val);

        // Recursively call for left and right children
        levelOrderHelper(node.left, level + 1, ans);
        levelOrderHelper(node.right, level + 1, ans);
    }

    public static TreeNode generateRandomBT(int maxVal, int maxLevel) {
        return generate(1, maxLevel, maxVal);
    }

    public static TreeNode generate(int level, int maxLevel, int maxVal) {
        if (level > maxLevel || Math.random() > 0.5) {
            return null;
        }

        TreeNode node = new TreeNode(randomVal(maxVal));
        node.left = generate(level + 1, maxLevel, maxVal);
        node.right = generate(level + 1, maxLevel, maxVal);
        return node;
    }

    public static int randomVal(int maxVal) {
        return (int)(Math.random() * (maxVal + 1));
    }

    public static boolean isEqual(List<List<Integer>> ans1, List<List<Integer>> ans2) {
        if (ans1 == null && ans2 == null) {
            return true;
        }

        if (ans1 == null || ans2 == null) {
            return false;
        }

        if (ans1.size() != ans2.size()) {
            return false;
        }

        for (int i = 0; i < ans1.size(); i++) {
            List<Integer> list1 = ans1.get(i);
            List<Integer> list2 = ans2.get(i);

            if (list1.size() != list2.size()) {
                return false;
            }

            for (int j = 0; j < list1.size(); j++) {
                if (!list1.get(j).equals(list2.get(j))) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int testTimes = 1000000;
        int maxLevel = 20;
        int maxVal = 100;

        for (int i = 0; i < testTimes; ++i) {
            TreeNode tree = generateRandomBT(maxVal, maxLevel);
            List<List<Integer>> ans1 = levelOrder(tree);
            List<List<Integer>> ans2 = levelOrderTest(tree);
            if (!isEqual(ans1, ans2)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
