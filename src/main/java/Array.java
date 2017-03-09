
import java.util.Random;
import java.util.Arrays;

import static java.util.Arrays.stream;

public class Array {


    public static void main(String[] args) throws InterruptedException {
        //создаем массив
        final int[] t = new int[8*1024];
        Random r = new Random();
        for (int i1=0; i1 < t.length; i1++ ) {
            t[i1]= r.nextInt(1000);
        }

        int sum = 0;
        class CalculatorThread extends Thread {
            int accumulator = 0;
            int start;
            int end;
            public CalculatorThread(int start, int end ) {
                this.start = start;
                this.end = end;
            }



            @Override
            public void run() {
                for (int i=start; i < end; i++ ) {
                    accumulator += t[i];
                }
            }
            public int getAccumulator() {
                return accumulator;
            }

        }


        //создаем 8 потоков
        CalculatorThread[] threads = new CalculatorThread[8];
        int chaunkSize = t.length / threads.length;
        for (int i=0; i < threads.length; i++ ) {
            threads[i] = new CalculatorThread(i*chaunkSize , (i + 1)*chaunkSize);
            threads[i].start();
        }
        for (CalculatorThread thread : threads) {
            thread.join();
            thread.getAccumulator();
        }

        //считаем сумму в 8 потоках
        int actual = Arrays.stream(t).sum();
        if (actual==sum) {
            System.out.println("(OK)");


        }
        else {
            System.out.println("(FAIL):actual" +sum+ ", expected" +actual);
            System.out.println(Arrays.toString(t));
        }

    }

}