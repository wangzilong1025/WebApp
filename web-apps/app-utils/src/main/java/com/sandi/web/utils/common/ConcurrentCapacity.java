package com.sandi.web.utils.common;

import java.io.Serializable;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Created by lury on 2015/7/21.
 */
public class ConcurrentCapacity implements Serializable {
    private Semaphore sem;
    private int capacity;
    private int seconds;

    public ConcurrentCapacity(int capacity, int seconds) {
        sem = null;
        this.capacity = 0;
        this.seconds = 0;
        sem = new Semaphore(capacity);
        this.capacity = capacity;
        this.seconds = seconds;
    }

    public boolean acquire()
            throws Exception {
        boolean rtn = sem.tryAcquire(seconds, TimeUnit.SECONDS);
        if (!rtn)
            throw new Exception((new StringBuilder()).append("semaphore acquire timeout ").append(seconds).append(" seconds,capacity limit:").append(capacity).toString());
        else
            return rtn;
    }

    public void release() {
        sem.release();
    }
}
