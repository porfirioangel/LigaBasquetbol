package com.icarus.ligabasquetbol.utils;

public class GeneradorClaves {
    public static String generarClave() {
        char[] chars = ("abcdefghijklmnopqrstuvwxyz" +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789").toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++)
            sb.append(chars[random(0, chars.length - 1)]);
        return sb.toString();
    }

    private static int random(int min, int max) {
        int range = Math.abs(max - min) + 1;
        return (int) (Math.random() * range) + (min <= max ? min : max);
    }
}
