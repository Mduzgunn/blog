package com.md.blog.util;

public class TimeUtil {

    public static String getTimeString(int duration) {
        return (duration / 60) + "h " + (duration % 60) + "m";
    }
}
