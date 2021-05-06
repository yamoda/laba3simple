package model;

import controller.Controller;
import view.MainFrame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;

public class SortFunction implements Runnable {
    public static final int FUNCTION_ID = 1;
    private static final int STRAP_DIGIT = 10;
    private int n;
    private int k;
    private Controller controller;
    private Lock lock;
    private List<GraphicPoint> data;
    private MainFrame frame;
    private int sleepTime;
    private int peakLimit;


    public SortFunction(int n, int k, Lock lock, MainFrame frame) {
        this.n = n;
        this.k = k;
        this.lock = lock;
        data = new ArrayList<>();
        this.frame = frame;
        sleepTime = 500;
        peakLimit = 2000;
    }

    @Override
    public void run() {
        for (int currentSize = 2; currentSize < n; currentSize++) {
                int commonTime = 0;
                for (int currentArrayCount = 1; currentArrayCount < k; currentArrayCount++) {
                    commonTime += sortTime(generateRandomArray(currentSize));
                }
                int averageTime = commonTime / k;
            lock.lock();
            try {
                GraphicPoint point = new GraphicPoint(currentSize, averageTime);
                frame.getGraphic().addValue(FUNCTION_ID, point);
                frame.getMainPointsTable().addValue(point);
                frame.getGraphic().repaint();
            } finally {
                lock.unlock();
            }

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                currentSize = n;
            }
        }
    }


    private int[] countingSort(int[] array) {
        int[] aux = new int[array.length];

        int min = array[0];
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            } else if (array[i] > max) {
                max = array[i];
            }
        }

        int[] counts = new int[max - min + 1];

        for (int i = 0; i < array.length; i++) {
            counts[array[i] - min]++;
        }

        counts[0]--;
        for (int i = 1; i < counts.length; i++) {
            counts[i] = counts[i] + counts[i - 1];
        }

        for (int i = array.length - 1; i >= 0; i--) {
            aux[counts[array[i] - min]--] = array[i];
        }

        return aux;
    }

    private long sortTime(int[] arrayToSort) {
        long startTime = System.nanoTime() / STRAP_DIGIT;
        countingSort(arrayToSort);
        long endTime = System.nanoTime() / STRAP_DIGIT;
        long result = endTime - startTime;
        if (result > peakLimit) {
            result = sortTime(arrayToSort);
        }
        return result;
    }

    private int[] generateRandomArray(int currentArraySize) {
        int[] result = new int[currentArraySize];
        Random random = new Random();
        for (int i = 0; i < result.length; i++) {
            result[i] = random.nextInt(currentArraySize);
        }
        return result;
    }
}
