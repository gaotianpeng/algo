package tixi.daily30;

public class MorrisTraversal {
    public static class Node {
        public int value;
        Node left;
        Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static void preTraverse(Node root) {
        if (root == null) {
            return;
        }
        System.out.print(root.value + " ");
        preTraverse(root.left);
        preTraverse(root.right);
    }
    /*
        Morris 遍历
            假设来到当前节点 cur, 开始时，cur 来到头节点位置
            1）如果 cur 没有左孩子，cur 向右移动(cur = cur.right)
            2）如果 cur 有左孩子，找到左子树上最右的节点 mostRight
                a. 如果 mostRight 的右指针指向为空，让其指向 cur，然后cur向左移动(cur = cur.left)
                b. 如果 mostRight 的右指针指向cur, 让其指向 null，然后 cur 向右移动 (cur = cur.right)
            3）cur 为空时遍历停止

     */
    public static void morris(Node head) {
        if (head == null) {
            return;
        }

        Node cur = head;
        Node mostRight = null;

        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }

                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            }
            cur = cur.right;
        }
    }

    public static void preMorris(Node head) {
        if (head == null) {
            return;
        }

        Node cur = head;
        Node mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    System.out.print(cur.value + " ");
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            } else {
                System.out.print(cur.value + " ");
            }
            cur = cur.right;
        }
        System.out.println();
     }


    public static void main(String[] args) {
        Node head = new Node(4);
        head.left = new Node(2);
        head.right = new Node(6);
        head.left.left = new Node(1);
        head.left.right = new Node(3);
        head.right.left = new Node(5);
        head.right.right = new Node(7);

        preTraverse(head);
        System.out.println();
        preMorris(head);
    }
}
