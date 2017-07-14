//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.io;

class MaskingThread extends Thread {
    private volatile boolean stop;
    private char echochar = 42;

    MaskingThread() {
    }

    public void run() {
        int var1 = Thread.currentThread().getPriority();
        Thread.currentThread().setPriority(10);

        try {
            this.stop = true;

            while(this.stop) {
                System.out.print("\b" + this.echochar);

                try {
                    Thread.sleep(1L);
                } catch (InterruptedException var6) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }

        } finally {
            Thread.currentThread().setPriority(var1);
        }
    }

    public void stopMasking() {
        this.stop = false;
    }
}
