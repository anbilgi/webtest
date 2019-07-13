package com.webtest.utils;

public class PageWaits {
    //Wait for Element/Page timeouts
    public static final long DEFAULT_PAGE_LOADING_TIMEOUT = 100;
    public static final long DEFAULT_ELEMENT_APPEAR_TIMEOUT = 75;
    public static final long DEFAULT_WINDOW_APPEAR_TIMEOUT = 50;
    public static final int DEFAULT_SMALL_WAIT=5;

    /**
     * Static wait
     * Used only when absolutely necessary to avoid flaky wait issues
     * @param wait_in_seconds
     */
    public static void wait(int wait_in_seconds){
        try {
            Thread.sleep(1000*wait_in_seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
