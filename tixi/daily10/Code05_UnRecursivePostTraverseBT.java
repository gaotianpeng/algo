package tixi.daily10;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Code05_UnRecursivePostTraverseBT {
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

    public static List<Integer> postOrder(TreeNode cur) {
        List<Integer> ans = new ArrayList<>();
        if (cur == null) {
            return ans;
        }

        Stack<TreeNode> s1 = new Stack<>();
        Stack<TreeNode> s2 = new Stack<>();
        s1.push(cur);
        while (!s1.empty()) {
            TreeNode node = s1.pop();
            s2.push(node);
            if (node.left != null) {
                s1.push(node.left);
            }
            if (node.right != null) {
                s1.push(node.right);
            }
        }

        while (!s2.empty()) {
            ans.add(s2.pop().val);
        }

        return ans;
    }

    public static List<Integer> postOrder2(TreeNode head) {
        List<Integer> ans = new ArrayList<>();
        if (head == null) {
            return ans;
        }

        // head 跟踪上次打印的节点
        // 如果上次打印的是cur的左孩子，说明该打印cur该右树了
        // 如果上次打印的是cur的右孩子，说明该打印cur左孩子也处理完了
		if (head != null) {
			Stack<TreeNode> stack = new Stack<TreeNode>();
			stack.push(head);
			TreeNode cur = null;
			while (!stack.isEmpty()) {
				cur = stack.peek();
                // 左右子树都没打印过，新到的，入栈
				if (cur.left != null && head != cur.left && head != cur.right) {
					stack.push(cur.left);
                // 左树处理过了，看看右树处理过没
				} else if (cur.right != null && head != cur.right) {
					stack.push(cur.right);
                // 左右两树都处理完了，打印cur
				} else {
                    ans.add(stack.pop().val);
					head = cur;
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

        process(root.left, ans);
        process(root.right, ans);
        ans.add(root.val);
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
        int testTimes = 1000000;
        int maxLevel = 20;
        int maxVal = 100;
        for (int i = 0; i < testTimes; i++) {
            TreeNode root = generateRandomBT(maxVal, maxLevel);
            List<Integer> order1 = postOrder(root);
            List<Integer> order2 = test(root);
            List<Integer> order3 = postOrder2(root);
            if (!isEqual(order1, order2) || !isEqual(order2, order3)) {
                print(order1);
                print(order2);
                print(order3);
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
