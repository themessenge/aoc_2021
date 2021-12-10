package day1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        File inputFile = new File("./src/day1/input.txt");
        //File inputFile = new File("./src/day1/example.txt");
        Scanner scanner = new Scanner(inputFile).useDelimiter("\r\n");
        List<Integer> temps = new ArrayList<>();
        while (scanner.hasNext()) {
            int token1 = scanner.nextInt();
            temps.add(token1);
        }
        scanner.close();

        Integer[] inputs = temps.toArray(new Integer[0]);

        int result1 = solve1(inputs);
        System.out.println("Result 1:" + result1);
        int result2 = solve2(inputs);
        System.out.println("Result 2:" + result2);
    }

    private static int solve1(Integer[] input) {
        int count = 0;
        for (int i = 0; i < input.length - 1; i++) {
            if (input[i] < input[i + 1]) {
                count++;
            }
        }
        return count;
    }

    private static int solve2(Integer[] input) {
        int count = 0;
        for (int i = 0; i < input.length - 3; i++) {
            int sum1 = input[i] + input[i + 1] + input[i + 2];
            int sum2 = input[i + 1] + input[i + 2] + input[i + 3];
            if (sum1 < sum2) {
                count++;
            }
        }
        return count;
    }
}
