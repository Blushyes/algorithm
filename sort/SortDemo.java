import java.util.Arrays;
import java.util.function.Consumer;

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

    public static int[] generateSortedArray(int len){
        int[] arr = new int[len];
        for(int i = 0; i < arr.length; i++){
            arr[i] = i;
        }
        return arr;
    }

    public static void demo(Runnable runnable) {
        System.out.println("----------demo开始----------");
        runnable.run();
        System.out.println("----------demo结束----------\n");
    }

    public static void demo(String title, Runnable runnable) {
        System.out.println("-".repeat((31 - title.length() * 2) / 2) + title + "-".repeat((31 - title.length() * 2) / 2));
        runnable.run();
        System.out.println("----------demo结束----------\n");
    }

    public static boolean isSorted(int[] nums) {
        for (int i = 0; i < nums.length - 1; ++i) {
            if (nums[i] > nums[i + 1]) {
                return false;
            }
        }
        return true;
    }
}