package dev.dex.fcpeuro.util;

import java.util.*;

public class StringUtil {
    public static String generateRandomString(int length) {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = upper.toLowerCase();
        String digits = "0123456789";
        String alphanum = upper + lower + digits;
        StringBuilder res = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            int randomInt = rand.nextInt(alphanum.length());
            res.append(alphanum.charAt(randomInt));
        }
        return res.toString();
    }
}
