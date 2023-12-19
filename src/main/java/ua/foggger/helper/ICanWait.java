package ua.foggger.helper;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Provides an opportunity to wait for any condition, described as Callable function or lambda.
 */
public interface ICanWait {
    /**
     * Waits for function to return true with fixed timeout - 30 seconds.
     * Returns result of last function execution.
     *
     * @param func - wait function to execute
     * @return true if wait was successful
     */
    default Boolean waitFor(Callable<Boolean> func) {
        return waitFor(func, 30);
    }

    /**
     * Waits for function to return true with timeout.
     * Returns result of last function execution.
     *
     * @param func       - wait function to execute
     * @param timeOutSec - execution timeout (in seconds)
     * @return true if wait was successful
     */
    default Boolean waitFor(Callable<Boolean> func, int timeOutSec) {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + TimeUnit.SECONDS.toMillis(timeOutSec);
        while (System.currentTimeMillis() <= endTime) {
            try {
                Boolean res = func.call();
                if (res) {
                    return res;
                }
            } catch (Throwable e) {
                //do nothing
            }
        }
        return false;
    }
}