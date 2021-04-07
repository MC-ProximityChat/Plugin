package com.proximitychat.plugin.util;

public class StringUtil {

    public static String repeat(char character, int times) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < times; i++) {
            builder.append(character);
        }
        return builder.toString();
    }

    public static String appendIfNotExists(String str, String toAppend) {
        return str.endsWith(toAppend) ? str : str + toAppend;
    }
}
