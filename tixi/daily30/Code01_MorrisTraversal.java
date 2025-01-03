package tixi.daily30;

public class Code01_MorrisTraversal {
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

    public static void inTraverse(Node root) {
        if (root == null) {
            return;
        }
        inTraverse(root.left);
        System.out.print(root.value + " ");
        inTraverse(root.right);
    }

    public static void posTraverse(Node root) {
        if (root == null) {
            return;
        }
        posTraverse(root.left);
        posTraverse(root.right);
        System.out.print(root.value + " ");
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
        Node mostRight = null;  // 左树最右节点
        while (cur != null) {
            // cur 有没有左树?
            mostRight = cur.left;
            if (mostRight != null) { // cur左树不为空
                // 找到cur.left 真实的最右节点
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                // 从while出来，mostRight是cur左树上的最右节点
                // 第一次
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else { // 第二次，mostRight.right = cur;
                    mostRight.right = null;
                    // cur = cur.right;
                    // continue;
                }
            }

            cur = cur.right; // cur左树为空
        }
    }

    // morris序第一次到来就打印
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

    // morris序列第二次打印，只有一次的第一次打印
    public static void inMorris(Node head) {
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
            System.out.print(cur.value + " ");
            cur = cur.right;
        }
        System.out.println();
    }

    public static void posMorris(Node head) {
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
                    printEdge(cur.left);
                }
            }

            cur = cur.right;
        }

        printEdge(head);
        System.out.println();
    }

    public static void printEdge(Node head) {
        Node tail = reverseEdge(head);
        Node cur = tail;
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = cur.right;
        }
        reverseEdge(tail);
    }

    public static Node reverseEdge(Node from) {
        Node pre = null;
        Node next = null;
        while (from != null) {
            next = from.right;
            from.right = pre;
            pre = from;
            from = next;
        }
        return pre;
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
        inTraverse(head);
        System.out.println();
        inMorris(head);
        posTraverse(head);
        System.out.println();
        posMorris(head);
    }
}
