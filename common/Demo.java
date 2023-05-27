public class Demo {
    public static long measure(Runnable runnable) {
        long start = System.currentTimeMillis();
        runnable.run();
        long end = System.currentTimeMillis();
        return end - start;
    }

    public static void measureAndPrint(Runnable runnable) {
        System.out.println("time: " + measure(runnable) + "ms");
    }
}
