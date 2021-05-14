public class Code21_PaperFolding {

    public static void printAllFolders(int N) {
        printProcess(1, N, true);
    }

    public static void printProcess(int i , int N, boolean down) {
        if (i > N) {
            return;
        }

        printProcess(i + 1, N, true);
        System.out.print(down ? "凹 " : "凸 ");
        printProcess(i + 1, N, false);
    }

    public static void main(String[] args) {
        printAllFolders(2);
        System.out.println();
        printAllFolders(3);
        System.out.println();
        printAllFolders(4);
        System.out.println();
    }
}
