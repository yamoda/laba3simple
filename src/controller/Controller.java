package controller;

import model.LinearFunction;
import model.SortFunction;
import view.MainFrame;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class Controller {
    public MainFrame window;
    public double xBeg;
    public double xEnd;
    private LinearFunction linearFunction;
    private SortFunction sortFunction;
    private Lock lock;
    private List<Thread> threads;

    public Controller(MainFrame window, Lock lock) {
        this.window = window;
        this.lock = lock;
        this.linearFunction = new LinearFunction(lock, window);
        this.sortFunction = new SortFunction(1, 2, lock, window);
        this.threads = new ArrayList<>();
    }


    public void startLinearFunctionThread() {
        this.linearFunction = new LinearFunction(lock, window);
        Thread LinearThread = new Thread(linearFunction);
        threads.add(LinearThread);
        LinearThread.start();
    }

    public void startSortFunctionThread(int n, int k) {
        this.sortFunction = new SortFunction(n, k, lock, window);
        Thread sortThread = new Thread(sortFunction);
        threads.add(sortThread);
        sortThread.start();
    }

    public void stopThreads() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
        threads.clear();
    }



    public void setXBeg(double xBeg) {
        this.xBeg = xBeg;
    }

    public void setXEnd(double xEnd) {
        this.xEnd = xEnd;
    }


    public double getXBeg() {
        return xBeg;
    }

    public double getXEnd() {
        return xEnd;
    }


}
