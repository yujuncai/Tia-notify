import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJionTest {

    static class Fibonacci  extends RecursiveTask<Integer> {

        int n;
        public Fibonacci (int n) {
            this.n = n;
        }
        @Override
        protected Integer compute() {
            if(n<=1){
                return n;
            }else{
                Fibonacci f1=new Fibonacci(n-1);
                f1.fork();
                Fibonacci f2=new Fibonacci(n-2);
                f2.fork();
                return f1.join() + f2.join();
            }

        }
    }


    public static void main(String[] args) throws Exception {
        System.out.println("CPU核数：" + Runtime.getRuntime().availableProcessors());

        ForkJoinPool pool=new ForkJoinPool();
        long start = System.currentTimeMillis();
        Fibonacci f=new Fibonacci(40);

        ForkJoinTask<Integer> submit = pool.submit(f);

        System.out.println(submit.get());
        long end = System.currentTimeMillis();
        System.out.println(String.format("耗时：%d millis", end - start));

    }
}
