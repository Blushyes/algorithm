/**
 * 一个数组中有一种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这种数
 */
public class FindNumberAppearOddTimes {
    public static void main(String[] args) {
        int[] nums = {1, 1, 2, 3, 3, 3, 2, 3, 5, 5, 3, 3, 2};
        System.out.println(find(nums)); // 2
    }

    private static int find(int[] nums) {
        int ans = 0;
        for (int num : nums) {
            ans ^= num;
        }
        return ans;
    }
}