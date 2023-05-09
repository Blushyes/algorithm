import java.util.Arrays;
import java.util.Set;

/**
 * 一个数组中有两种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这两种数
 */
public class FindTwoNumberAppearOddTimes {
    public static void main(String[] args) {
        int[] nums = {1, 1, 2, 3, 3, 3, 2, 3, 5, 5, 3, 3, 2, 6, 7, 7, 6, 6};
        System.out.println(find(nums));
    }

    private static Set<Integer> find(int[] nums) {
        // 将所有的数全部异或 eor = ans1 ^ ans2
        int eor = Arrays.stream(nums).reduce(0, (counter, num) -> counter ^ num);

        // 提取最右边的第一个1
        int onlyOne = eor & (~eor + 1);

        // 提取一个数
        int ans1 = Arrays.stream(nums).reduce(0, (counter, num) -> (num & onlyOne) == 0 ? counter ^ num : counter);

        // 提取另一个数
        int ans2 = eor ^ ans1;

        // 返回
        return Set.of(ans1, ans2);
    }
}