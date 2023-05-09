import java.util.Arrays;

/**
 * 测试Arrays.binarySearch()
 */
public class JavaBinarySearchTest {
    public static void main(String[] args) {
        int[] arr = {0, 2, 2, 3, 5, 5, 7};

        // 搜存在，返回最小的索引
        System.out.println(Arrays.binarySearch(arr, 2));    // 1

        // 搜不存在，但在数组范围内返回 [-插入的位置]（从1开始，因为-1被小于所有元素的占用了）
        System.out.println(Arrays.binarySearch(arr, 1));    // -2

        // 搜不存在，小于最小值，返回-1
        System.out.println(Arrays.binarySearch(arr, -10));  // -1

        // 搜不存在， 大于最大值，返回 [-(length + 1)]
        System.out.println(Arrays.binarySearch(arr, 100));  // -8

        // 搜夹在后两个中间的，返回-length被比最大的小，比第二大的大的占用了，因为它要插入的是length位置
        System.out.println(Arrays.binarySearch(arr, 6));    // -7
    }
}
