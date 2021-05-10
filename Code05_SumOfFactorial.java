public class Code05_SumOfFactorial {
    static long factorial(int N) {
        long ans = 0;
        long cur = 1;
        for (int i = 1; i <= N; ++i) {
            cur = cur * i;
            ans += cur;
        }

        return ans;
    }

    public static void main(String[] args) {
        System.out.println(factorial(10));
    }
}
