package tixi.daily11;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Code05_TreeMaxWidth {
    public static class TreeNode {
        public TreeNode left;
        public TreeNode right;
        public int value;

        public TreeNode(int val) {
            value = val;
        }
    }

    public static int maxBinaryTreeWidth(TreeNode head) {
        if (head == null) {
            return 0;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(head);
        int max = 0;
        int curLevelNodes = 0;
        TreeNode curEnd = head;
        TreeNode nextEnd = null;

        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            if (cur.left != null) {
                nextEnd = cur.left;
                queue.add(cur.left);
            }
            if (cur.right != null) {
                nextEnd = cur.right;
                queue.add(cur.right);
            }

            ++curLevelNodes;
            if (cur == curEnd) {
                max = Math.max(max, curLevelNodes);
                curEnd = nextEnd;
                nextEnd = null;
                curLevelNodes = 0;
            }
        }

        return max;
    }

    /*
        for test
     */
    public static int test(TreeNode head) {
        if (head == null) {
            return 0;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(head);
        // key 在哪一层
        HashMap<TreeNode, Integer> levelMap = new HashMap<>();
        levelMap.put(head, 1);
        // 当前你正在统计哪一层的宽度
        int curLevel = 1;
        // cur_level层的宽度目前是多少
        int curLevelNodes = 0;
        int max = 0;
        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            int curNodeLevel = levelMap.get(cur);
            if (cur.left != null) {
                levelMap.put(cur.left, curNodeLevel + 1);
                queue.add(cur.left);
            }
            if (cur.right != null) {
                levelMap.put(cur.right, curNodeLevel + 1);
                queue.add(cur.right);
            }
            if (curNodeLevel == curLevel) {
                curLevelNodes++;
            } else {
                max = Math.max(max, curLevelNodes);
                curLevel++;
                curLevelNodes = 1;
            }
        }
        max = Math.max(max, curLevelNodes);
        return max;
    }

    public static TreeNode generateRandomBT(int max_level, int max_val) {
        return generate(0, max_level, max_val);
    }

    private static TreeNode generate(int cur_level, int max_level, int max_val) {
        if (cur_level > max_level || Math.random() > 0.5) {
            return null;
        }

        TreeNode node = new TreeNode(randomValue(max_val));
        node.left = generate(cur_level + 1, max_level, max_val);
        node.right = generate( cur_level + 1, max_level, max_val);
        return node;
    }

    private static int randomValue(int max_val) {
        return (int)(Math.random() * (max_val + 1));
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int maxLevel = 20;
        int maxVal = 30;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            TreeNode root = generateRandomBT(maxLevel, maxVal);
            int width1 = maxBinaryTreeWidth(root);
            int width2 = test(root);
            if (width1 != width2) {
                System.out.println(width2);
                System.out.println(width2);
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
