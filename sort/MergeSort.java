import java.util.Arrays;
import java.util.function.Consumer;

/**
 * 归并排序递归版
 */
public class MergeSort {
    public static void main(String[] args) {
        // int[] nums = {2, 1, 3, 2, 2, 6, 5, 7};
        int[] nums = SortDemo.generateRandomArray(100000000, 100);   // 随机生成一亿个数
        System.out.println("mergeSort: " + SortDemo.test(nums, (tmp) -> mergeSort(tmp, 0, tmp.length - 1)) + "ms"); // 一个线程排序
        System.out.println("concurrentMergeSort: " + SortDemo.test(nums, (tmp) -> concurrentMergeSort(tmp, 0, tmp.length - 1)) + "ms"); // 两个线程排序
        System.out.println("customConcurrentMergeSort: " + SortDemo.test(nums, (tmp) -> customConcurrentMergeSort(tmp, 0, tmp.length - 1, 6)) + "ms"); // 六个线程排序
    }

    // 归并排序
    private static void mergeSort(int[] nums, final int L, final int R) {
        if (L == R) {   // 递归基，如果 L==R 说明区间只有1个数，则直接返回，不需要进行排序
            return;
        }
        final int M = L + (R - L) / 2;  // 从中间划分
        mergeSort(nums, L, M);  // 排序左半段
        mergeSort(nums, M + 1, R);  // 排序右半段
        merge(nums, L, M, R);   // 合并两个已经排序好的有序数组
    }

    // 合并两个有序数组
    private static void merge(int[] nums, final int L,final int M, final int R) {
        int[] tmp = new int[R - L + 1]; // R - L + 1 为排序区间长度
        int l = L, r = M + 1, p = 0;
        while (l <= M && r <= R) {
            tmp[p++] = nums[l] < nums[r] ? nums[l++] : nums[r++];
        }
        while (l <= M) {
            tmp[p++] = nums[l++];
        }
        while (r <= R) {
            tmp[p++] = nums[r++];
        }
        System.arraycopy(tmp, 0, nums, L,  tmp.length);
    }

    // 多线程归并排序
    private static void concurrentMergeSort(int[] nums, final int L, final int R) {
        if (L == R) {   // 递归基，如果 L==R 说明区间只有1个数，则直接返回，不需要进行排序
            return;
        }
        final int M = L + (R - L) / 2;  // 从中间划分
        Thread leftSort = new Thread(() -> mergeSort(nums, L, M)); // 排序左半段
        Thread rightSort = new Thread(() -> mergeSort(nums, M + 1, R)); // 排序右半段
        leftSort.start();
        rightSort.start();
        try {
            leftSort.join();
            rightSort.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        merge(nums, L, M, R);   // 合并两个已经排序好的有序数组
    }

    // 自定义线程数归并排序
    private static void customConcurrentMergeSort(int[] nums, final int L, final int R, int threadNumber) {
        if (L == R) {   // 递归基，如果 L==R 说明区间只有1个数，则直接返回，不需要进行排序
            return;
        }
        final int M = L + (R - L) / 2;  // 从中间划分
        Thread leftSort = new Thread(() -> {
            if (threadNumber / 2 == 1) {
                mergeSort(nums, L, M);
            } else {
                customConcurrentMergeSort(nums, L, M, threadNumber - 1);
            }
        }); // 排序左半段
        Thread rightSort = new Thread(() -> {
            if (threadNumber / 2 == 1) {
                mergeSort(nums, M + 1, R);
            } else {
                customConcurrentMergeSort(nums, M + 1, R, threadNumber - 1);
            }
        }); // 排序右半段
        leftSort.start();
        rightSort.start();
        try {
            leftSort.join();
            rightSort.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        merge(nums, L, M, R);   // 合并两个已经排序好的有序数组
    }
}

class SortDemo {
    public static long test(int[] nums, boolean outputFlag, Consumer<int[]> sort) {
        long start = System.currentTimeMillis();
        int[] tmp = Arrays.copyOf(nums, nums.length);
        sort.accept(tmp);
        if (outputFlag) {
            System.out.println(Arrays.toString(tmp));
        }
        long end = System.currentTimeMillis();
        return end - start;

    }

    public static long test(int[] nums, Consumer<int[]> sort) {
        long start = System.currentTimeMillis();
        int[] tmp = Arrays.copyOf(nums, nums.length);
        sort.accept(tmp);
        long end = System.currentTimeMillis();
        return end - start;
    }

    public static int[] generateRandomArray(int len, int max){
        int[] arr = new int[len];
        for(int i = 0; i < arr.length; i++){
            arr[i] = (int)(Math.random() * max);
        }
        return arr;
    }
}