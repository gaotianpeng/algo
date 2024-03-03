import java.util.PriorityQueue;
import java.util.TreeMap;

public class test {
    public static void main(String[] args) {
        PriorityQueue<Integer> minQueue = new PriorityQueue<>();
        minQueue.add(1);
        System.out.println(minQueue.size());
        minQueue.poll();
        System.out.println(minQueue.size());
    }
}
