package com.prgrms.devcourse;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.runAsync;

public class ThreadLocalApp {

    final static ThreadLocal<Integer> threadLocalValue = new ThreadLocal<>();

    public static void main(String[] args) {
        System.out.println(getCurrentThreadMane() + " ### main set value = 1");
        threadLocalValue.set(1);

        a();
        b();

        CompletableFuture<Void> task = runAsync(() -> { // main thread가 아닌 다른 곳에서 실행되는 코드 블록
            a();
            b();
        });

        task.join();
    // ThreadLocal 변수는 Thread 마다 독립적인 변수이고 서로 다른 thread에서는 참조할 수 없다
    }

    public static void a() {
        Integer value = threadLocalValue.get();
        System.out.println(getCurrentThreadMane() + " ### a() get value = " + value);
    }

    public static void b() {
        Integer value = threadLocalValue.get();
        System.out.println(getCurrentThreadMane() + " ### b() get value = " + value);
    }

    public static String getCurrentThreadMane() {
        return Thread.currentThread().getName();
    }
}
