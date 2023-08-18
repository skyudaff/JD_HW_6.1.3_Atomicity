package org.example;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {
    static AtomicInteger countLength3 = new AtomicInteger(0);
    static AtomicInteger countLength4 = new AtomicInteger(0);
    static AtomicInteger countLength5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread threadLength3 = new Thread(() -> countBeautiful(texts, 3));
        Thread threadLength4 = new Thread(() -> countBeautiful(texts, 4));
        Thread threadLength5 = new Thread(() -> countBeautiful(texts, 5));

        threadLength3.start();
        threadLength4.start();
        threadLength5.start();

        threadLength3.join();
        threadLength4.join();
        threadLength3.join();

        System.out.println("Красивых слов с длиной 3: " + countLength3 + " шт");
        System.out.println("Красивых слов с длиной 4: " + countLength4 + " шт");
        System.out.println("Красивых слов с длиной 5: " + countLength5 + " шт");
    }


    public static void countBeautiful(String[] nicks, int length) {
        for (String nick : nicks) {
            if (isBeautiful(nick, length)) {
                switch (length) {
                    case 3 -> countLength3.incrementAndGet();
                    case 4 -> countLength4.incrementAndGet();
                    case 5 -> countLength5.incrementAndGet();
                    default -> {
                    }
                }
            }
        }
    }

    public static boolean isPalindrome(String nick) {
        StringBuilder sb = new StringBuilder(nick);
        return sb.reverse().toString().equals(nick);
    }

    public static boolean isSingleChar(String nick) {
        char first = nick.charAt(0);
        for (int i = 1; i < nick.length(); i++) {
            if (nick.charAt(i) != first)
                return false;
        }
        return true;
    }

    public static boolean isAscendingOrder(String nick) {
        for (int i = 0; i < nick.length() - 1; i++) {
            if (nick.charAt(i) >= nick.charAt(i + 1))
                return false;
        }
        return true;
    }


    public static boolean isBeautiful(String nick, int length) {
        if (nick.length() != length) {
            return false;
        }
        return isPalindrome(nick) || isSingleChar(nick) || isAscendingOrder(nick);
    }


    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}