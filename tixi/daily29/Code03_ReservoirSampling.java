package tixi.daily29;

/*
	蓄水池算法
		假设有一个源源吐出不同球的机器，
		只有装下10个球的袋子，每一个吐出的球，要么放入袋子，要么永远扔掉
		如何做到机器吐出每一个球之后，所有吐出的球都等概率被放进袋子里

	leetcode  398. Random Pick Index
 */
public class Code03_ReservoirSampling {
	/*
	 * 	1 ~ 10号球完全入袋子
	 *  随机函数f(i)，随机等概率返回1~i的任一数
	 * 	对于k号球
	 * 		1）用10/k决定要不要入袋子， f(k) 在 1~10，入袋
	 * 		2）袋子中10个球等概率扔
	 */
	public static class RandomBox {
		private int[] bag;
		private int N;
		private int count;

		public RandomBox(int capacity) {
			bag = new int[capacity];
			N = capacity;
			count = 0;
		}

		private int rand(int max) {
			return (int) (Math.random() * max) + 1;
		}

		public void add(int num) {
			count++;
			if (count <= N) {
				bag[count - 1] = num;
			} else {
				if (rand(count) <= N) {
					bag[rand(N) - 1] = num;
				}
			}
		}

		public int[] choices() {
			int[] ans = new int[N];
			for (int i = 0; i < N; i++) {
				ans[i] = bag[i];
			}
			return ans;
		}

	}

	// 请等概率返回1~i中的一个数字
	public static int random(int i) {
		return (int) (Math.random() * i) + 1;
	}

	public static void main(String[] args) {
		System.out.println("hello");
		int test = 10000;
		int ballNum = 17;
		int[] count = new int[ballNum + 1];
		for (int i = 0; i < test; i++) {
			int[] bag = new int[10];
			int bagi = 0;
			for (int num = 1; num <= ballNum; num++) {
				if (num <= 10) {
					bag[bagi++] = num;
				} else { // num > 10
					if (random(num) <= 10) { // 一定要把num球入袋子
						bagi = (int) (Math.random() * 10);
						bag[bagi] = num;
					}
				}

			}
			for (int num : bag) {
				count[num]++;
			}
		}
		for (int i = 0; i <= ballNum; i++) {
			System.out.println(count[i]);
		}

		System.out.println("hello");
		int all = 100;
		int choose = 10;
		int testTimes = 50000;
		int[] counts = new int[all + 1];
		for (int i = 0; i < testTimes; i++) {
			RandomBox box = new RandomBox(choose);
			for (int num = 1; num <= all; num++) {
				box.add(num);
			}
			int[] ans = box.choices();
			for (int j = 0; j < ans.length; j++) {
				counts[ans[j]]++;
			}
		}

		for (int i = 0; i < counts.length; i++) {
			System.out.println(i + " times : " + counts[i]);
		}

	}
}
