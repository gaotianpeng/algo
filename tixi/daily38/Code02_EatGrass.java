package tixi.daily38;

/*
    给定一个正整数N，表示有N份青草统一堆放在仓库里
    有一只牛和一只羊，牛先吃，羊后吃，它俩轮流吃草
    不管是牛还是羊，每一轮能吃的草量必须是：
    1，4，16，64…(4的某次方)
    谁最先把草吃完，谁获胜
    假设牛和羊都绝顶聪明，都想赢，都会做出理性的决定
    根据唯一的参数N，返回谁会赢
 */
public class Code02_EatGrass {
    public static String whoWin(int n) {
        if (n < 5) {
            return (n == 0 || n == 2) ? "后手" : "先手";
        }
        int base = 1;
        while (base <= n) {
            if (winner1(n - base).equals("后手")) {
                return "先手";
            }
            if (base > n / 4) { // 防止base*4之后溢出
                break;
            }
            base *= 4;
        }
        return "后手";
    }

    public static String winner1(int n) {
        if (n % 5 == 0 || n % 5 == 2) {
            return "后手";
        } else {
            return "先手";
        }
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int testTimes = 100000;
        boolean success = true;
        int maxN = 100000;
        for (int i = 0; i < testTimes; ++i) {
            int apple = (int)Math.random()*(maxN + 1);
            if (whoWin(apple) != winner1(apple)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" :"failed");
        System.out.println("test end");
    }
}
