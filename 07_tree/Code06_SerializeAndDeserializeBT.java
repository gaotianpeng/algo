package tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

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
 *
 * */
public class Code06_SerializeAndDeserializeBT {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Queue<String> preSerial(Node head) {
        Queue<String> ans = new LinkedList<>();
        pres(head, ans);
        return ans;
    }

    public static void pres(Node head, Queue<String> ans) {
        if (head == null) {
            ans.add(null);
        } else {
            ans.add(String.valueOf(head.value));
            pres(head.left, ans);
            pres(head.right, ans);
        }
    }

    public static Queue<String> posSerial(Node head) {
        Queue<String> ans = new LinkedList<>();
        poss(head, ans);
        return ans;
    }

    public static void poss(Node head, Queue<String> ans) {
        if (head == null) {
            ans.add(null);
        } else {
            poss(head.left, ans);
            poss(head.right, ans);
            ans.add(String.valueOf(head.value));
        }
    }

    public static Node deserializeByPre(Queue<String> prelist) {
        if (prelist == null || prelist.size() == 0) {
            return null;
        }

        return deserializePre(prelist);
    }

    public static Node deserializePre(Queue<String> prelist) {
        String value = prelist.poll();
        if (value == null) {
            return null;
        }

        Node head = new Node(Integer.valueOf(value));
        head.left = deserializePre(prelist);
        head.right = deserializePre(prelist);
        return head;
    }

    public static Node deserializeByPos(Queue<String> poslist) {
        if (poslist == null || poslist.size() == 0) {
            return null;
        }
        // 左、右、中   -->  中、左、右
        Stack<String> stack = new Stack<>();
        while (!poslist.isEmpty()) {
            stack.push(poslist.poll());
        }

        return deserializePos(stack);
    }

    public static Node deserializePos(Stack<String> posstack) {
        String value = posstack.pop();
        if (value == null) {
            return null;
        }
        Node head = new Node(Integer.valueOf(value));
        head.right = deserializePos(posstack);
        head.left = deserializePos(posstack);
        return head;
    }

    public static Queue<String> levelSerial(Node head) {
        Queue<String> ans = new LinkedList<>();
        if (head == null) {
            ans.add(null);
        } else {
            ans.add(String.valueOf(head.value));
            Queue<Node> queue = new LinkedList<>();
            queue.add(head);
            while (!queue.isEmpty()) {
                head = queue.poll();
                if (head.left != null) {
                    ans.add(String.valueOf(head.left.value));
                    queue.add(head.left);
                } else {
                    ans.add(null);
                }

                if (head.right != null) {
                    ans.add(String.valueOf(head.right.value));
                    queue.add(head.right);
                } else {
                    ans.add(null);
                }
            }
        }

        return ans;
    }

    public static Node deserializeByLevel(Queue<String> levellist) {
        if (levellist == null || levellist.size() == 0) {
            return null;
        }

        Node head = generateNode(levellist.poll());
        Queue<Node> queue = new LinkedList<>();
        if (head != null) {
            queue.add(head);
        }
        Node node = null;
        while (!queue.isEmpty()) {
            node = queue.poll();
            node.left = generateNode(levellist.poll());
            node.right = generateNode(levellist.poll());
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }

        return head;
    }

    public static Node generateNode(String val) {
        if (val == null) {
            return null;
        }

        return new Node(Integer.valueOf(val));
    }

    /*
        for test
     */
    public static Node generateRandomBST(int max_level, int max_value) {
        return generate(1, max_level, max_value);
    }

    public static Node generate(int level, int max_level, int max_value) {
        if (level > max_level || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int)(Math.random()*max_value));
        head.left = generate(level+1, max_level, max_value);
        head.right = generate(level+1, max_level, max_value);
        return head;
    }

    public static boolean isSameValueBST(Node head1, Node head2) {
        if (head1 == null && head2 != null) {
            return false;
        }

        if (head1 != null && head2 == null) {
            return false;
        }

        if (head1 == null && head2 == null) {
            return true;
        }

        if (head1.value != head2.value) {
            return false;
        }

        return isSameValueBST(head1.left, head2.left) && isSameValueBST(head1.right, head2.right);
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int max_level = 30;
        int max_value = 100;
        int test_times = 1000000;
        boolean success = true;
        for (int i = 0; i < test_times; i++) {
            Node head = generateRandomBST(max_level, max_value);
            Queue<String> pre = preSerial(head);
            Queue<String> pos = posSerial(head);
            Queue<String> level = levelSerial(head);
            Node pre_de = deserializeByPre(pre);
            Node pos_de = deserializeByPos(pos);
            Node level_de = deserializeByLevel(level);
            if (!isSameValueBST(pre_de, pos_de) || !isSameValueBST(pos_de, level_de)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
