import java.util.TreeMap;

public class test {
    public static void main(String[] args) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(1, 2);
        if (map.get(2) == map.get(2)) {
            System.out.println("-----------");;
        }
    }
}
