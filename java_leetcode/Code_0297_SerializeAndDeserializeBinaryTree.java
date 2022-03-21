package leetcode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/*
    297 二叉树的序列化与反序列化
        序列化是将一个数据结构或者对象转换为连续的比特位的操作，进而可以将转换后的数据存储在一个文件或者内存中，
        同时也可以通过网络传输到另一个计算机环境，采取相反方式重构得到原数据。
        请设计一个算法来实现二叉树的序列化与反序列化。这里不限定你的序列 / 反序列化算法执行逻辑，
        你只需要保证一个二叉树可以被序列化为一个字符串并且将这个字符串反序列化为原始的树结构。
        提示: 输入输出格式与 LeetCode 目前使用的方式一致，详情请参阅 LeetCode 序列化二叉树的格式。
        你并非必须采取这种方式，你也可以采用其他的方法解决这个问题。

    示例
        输入：root = [1,2,3,null,null,4,5]
        输出：[1,2,3,null,null,4,5]

        输入：root = []
        输出：[]

        输入：root = [1]
        输出：[1]

        输入：root = [1,2]
        输出：[1,2]
 */
public class Code_0297_SerializeAndDeserializeBinaryTree {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public String serialize(TreeNode root) {
        return rserialize(root, "");
    }

    public static String rserialize(TreeNode node, String str) {
        if (node == null) {
            str += "null,";
        } else {
            str += str.valueOf(node.val) + ",";
            str = rserialize(node.left, str);
            str = rserialize(node.right, str);
        }

        return str;
    }

    public TreeNode deserialize(String data) {
        String[] str_list = data.split(",");
        List<String> list = new LinkedList<>(Arrays.asList(str_list));
        return rdeserialize(list);
    }

    public static TreeNode rdeserialize(List<String> list) {
        if (list.get(0).equals("null")) {
            list.remove(0);
            return null;
        }

        TreeNode node = new TreeNode(Integer.valueOf(list.get(0)));
        list.remove(0);
        node.left = rdeserialize(list);
        node.right = rdeserialize(list);

        return node;
    }
}
// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));
