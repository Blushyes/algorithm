import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class QuickSort {
    public static void main(String[] args) {
        Consumer<int[]> print = (nums) -> {
            System.out.println("QuickSort: " + SortDemo.test(nums, (tmp) -> quickSort(tmp, 0, tmp.length - 1)) + "ms");
            System.out.println("ThreeWaysQuickSort: " + SortDemo.test(nums, (tmp) -> threeWaysQuickSort(tmp, 0, tmp.length - 1)) + "ms");
            System.out.println("RandomQuickSort: " + SortDemo.test(nums, (tmp) -> randomQuickSort(tmp, 0, tmp.length - 1)) + "ms");
            System.out.println("mergeSort: " + SortDemo.test(nums, (tmp) -> MergeSort.mergeSort(tmp, 0, tmp.length - 1)) + "ms");
        };

        SortDemo.demo("少量随机数组排序", () -> {
            int[] nums = SortDemo.generateRandomArray(10000, 10000);
            print.accept(nums);
        });

        SortDemo.demo("有序数组排序", () -> {
            int[] nums = SortDemo.generateSortedArray(100000);
            print.accept(nums);
        });

        SortDemo.demo("大量随机数组排序", () -> {
            int[] nums = SortDemo.generateRandomArray(100000000, 100000000);
            print.accept(nums);
        });
    }

    // 以 nums[R] 为哨兵的快排
    private static void quickSort(int[] nums, int L, int R) {
        if (L < R) {
            int pivot = partition(nums, L, R, () -> nums[R]);
            quickSort(nums, L, pivot - 1);
            quickSort(nums, pivot + 1, R);
        }
    }

    // 随机快排
    private static void randomQuickSort(int[] nums, int L, int R) {
        if (L < R) {
            int pivot = partition(nums, L, R, () -> new Random().nextInt(R - L + 1) + L);
            randomQuickSort(nums, L, pivot - 1);
            randomQuickSort(nums, pivot + 1, R);
        }
    }

    // 三路快排
    private static void threeWaysQuickSort(int[] nums, int L, int R) {
        if (L < R) {
            int[] pivots = threeWaysPartition(nums, L, R, () -> nums[R]);
            threeWaysQuickSort(nums, L, pivots[0] - 1);
            threeWaysQuickSort(nums, pivots[1] + 1, R);
        }
    }

    private static int partition(int[] nums, int L, int R, Supplier<Integer> getPivot) {
        int pivot = getPivot.get();
        int i = L - 1;
        for (int j = L; j < R; ++j) {
            if (nums[j] < pivot) {
                swap(nums, ++i, j);
            }
        }
        swap(nums, ++i, R);
        return i;
    }

    private static int[] threeWaysPartition(int[] nums, int L, int R, Supplier<Integer> getPivot) {
        int pivot = getPivot.get();
        int l = L - 1, r = R, i = L;
        while (i < r) {
            if (nums[i] < pivot) {
                swap(nums, ++l, i++);
            } else if(nums[i] > pivot) {
                // i不变，因为交换后还要继续判断
                swap(nums, --r, i);
            } else {
                ++i;
            }
        }
        swap(nums, r, R);
        return new int[]{l + 1, r};
    }

    private static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
