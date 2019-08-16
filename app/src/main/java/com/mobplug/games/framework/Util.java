package com.mobplug.games.framework;

/**
 * General utility class
 *
 * @author andreban
 */
public class Util {
    private Util() {}

    /**
     * This method uses System.nanoTime() internally
     * @return the current time in milles
     */
    public static long getMillis() {
        return System.nanoTime() / 1000000;
    }
}
