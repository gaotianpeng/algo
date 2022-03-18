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
        // 当前层，最右节点是谁
        TreeNode cur_end = head;
        // 下一层，最右节点是谁
        TreeNode next_end = null;
        int max = 0;
        int cur_level_nodes = 0;
        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            if (cur.left != null) {
                queue.add(cur.left);
                next_end = cur.left;
            }
            if (cur.right != null) {
                queue.add(cur.right);
                next_end = cur.right;
            }
            cur_level_nodes++;
            if (cur == cur_end) {
                max = Math.max(max, cur_level_nodes);
                cur_level_nodes = 0;
                cur_end = next_end;
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
        HashMap<TreeNode, Integer> level_map = new HashMap<>();
        level_map.put(head, 1);
        // 当前你正在统计哪一层的宽度
        int cur_level = 1;
        // cur_level层的宽度目前是多少
        int cur_level_nodes = 0;
        int max = 0;
        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            int cur_node_level = level_map.get(cur);
            if (cur.left != null) {
                level_map.put(cur.left, cur_node_level + 1);
                queue.add(cur.left);
            }
            if (cur.right != null) {
                level_map.put(cur.right, cur_node_level + 1);
                queue.add(cur.right);
            }
            if (cur_node_level == cur_level) {
                cur_level_nodes++;
            } else {
                max = Math.max(max, cur_level_nodes);
                cur_level++;
                cur_level_nodes = 1;
            }
        }
        max = Math.max(max, cur_level_nodes);
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
        int max_level = 20;
        int max_val = 30;
        int test_times = 1000000;
        for (int i = 0; i < test_times; i++) {
            TreeNode root = generateRandomBT(max_level, max_val);
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
