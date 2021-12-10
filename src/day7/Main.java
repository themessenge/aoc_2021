package day7;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        File inputFile = new File("./src/day7/input.txt");
        //File inputFile = new File("./src/day7/example.txt");
        Scanner scanner = new Scanner(inputFile).useDelimiter(",");
        List<Integer> temps = new ArrayList<>();
        while (scanner.hasNext()) {
            temps.add(scanner.nextInt());
        }
        scanner.close();

        int result1 = solve1(temps);
        System.out.println("Result 1:" + result1);
        long result2 = solve2(temps);
        System.out.println("Result 2:" + result2);
    }

    private static int solve1(List<Integer> inputs) {
        int min = Collections.min(inputs);
        int max = Collections.max(inputs);
        int minSum = Integer.MAX_VALUE;
        for (int i = min; i <= max; i++) {
            int sum = 0;
            for (int j : inputs) {
                sum += Math.abs(j - i);
            }
            if (sum < minSum) {
                minSum = sum;
            }
        }
        return minSum;
    }

    private static long solve2(List<Integer> inputs) {
        int min = Collections.min(inputs);
        int max = Collections.max(inputs);
        int minSum = Integer.MAX_VALUE;
        for (int i = min; i <= max; i++) {
            int sum = 0;
            for (int j : inputs) {
                int distance = Math.abs(j - i);
                sum += getTriangular(distance);
            }
            if (sum < minSum) {
                minSum = sum;
            }
        }
        return minSum;
    }

    private static int getTriangular(int num){
        int triangular = 0;
        for(int i = 1;i<=num;i++) {
            triangular = triangular + i;
        }
        return triangular;
    }


}
