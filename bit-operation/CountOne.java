import java.util.Arrays;
import java.util.Set;

/**
 * 数出一个数中的二进制1的个数
 */
public class CountOne {
    public static void main(String[] args) {
        System.out.println(countOne(Integer.parseInt("0010110111000", 2)));
    }

    private static int countOne(int num) {
        int count = 0;
        while (num != 0) {
            int rightOne = num & (~num + 1);
            ++count;
            num ^= rightOne;
            // num -= rightOne; 只有num是正数的时候可以减
        }
        return count;
    }
}