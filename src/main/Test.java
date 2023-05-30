package main;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        Random r = new Random();

        int[] v = new int[10];
        int[] w = new int[10];

        for (int x = 0 ; x < v.length ; x++) {
            v[x] = r.nextInt(10);
        }
        for (int y = 0 ; y < w.length ; y++) {
            w[y] = r.nextInt(10);
        }


        System.out.println(Arrays.toString(v));
        System.out.println(Arrays.toString(w));

    }
}
