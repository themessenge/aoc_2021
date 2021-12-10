package day3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        File inputFile = new File("./src/day3/input.txt");
        //File inputFile = new File("./src/day3/example.txt");
        Scanner scanner = new Scanner(inputFile).useDelimiter("\r\n");
        List<String> temps = new ArrayList<>();
        while (scanner.hasNext()) {
            String token1 = scanner.nextLine();
            temps.add(token1);
        }
        scanner.close();

        String[] inputs = temps.toArray(new String[0]);

        int result1 = solve1(inputs);
        System.out.println("Result 1:" + result1);
        int result2 = solve2(inputs);
        System.out.println("Result 2:" + result2);
    }

    private static int solve1(String[] input) {
        int length = input[0].length();
        StringBuilder gamma = new StringBuilder();
        StringBuilder epsilon = new StringBuilder();
        for (int j = 0; j < length; j++) {
            int zeroes = 0;
            int ones = 0;
            for (int i = 0; i < input.length - 1; i++) {
                if (input[i].charAt(j) == '0') {
                    zeroes++;
                } else if (input[i].charAt(j) == '1') {
                    ones++;
                } else {
                    System.out.println("ERROR");
                }
            }
            if (zeroes > ones) {
                gamma.append('0');
                epsilon.append('1');
            } else if (ones > zeroes) {
                epsilon.append('0');
                gamma.append('1');
            } else {
                System.out.println("ERROR 2");
            }
        }
        int gammaInt = Integer.parseInt(gamma.toString(), 2);
        int epsilonInt = Integer.parseInt(epsilon.toString(), 2);
        return gammaInt * epsilonInt;
    }

    private static int solve2(String[] input) {
        return findOxygen(Arrays.asList(input), 0) * findCo2(Arrays.asList(input), 0);
    }

    private static int findOxygen(List<String> input, int charAt) {
        List<String> currentList;
        if (input.size() == 1) {
            //System.out.println(input.get(0));
            return Integer.parseInt(input.get(0), 2);
        }
        List<String> zeroes = new ArrayList<>();
        List<String> ones = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i).charAt(charAt) == '0') {
                zeroes.add(input.get(i));
            } else if (input.get(i).charAt(charAt) == '1') {
                ones.add(input.get(i));
            } else {
                System.out.println("ERROR");
            }
        }
        if (zeroes.size() > ones.size()) {
           currentList = zeroes;
        } else if (zeroes.size() < ones.size()) {
            currentList = ones;
        } else {
            currentList = ones;
        }
        return findOxygen(currentList, charAt+1);
    }

    private static int findCo2(List<String> input, int charAt) {
        List<String> currentList;
        if (input.size() == 1) {
            //System.out.println(input.get(0));
            return Integer.parseInt(input.get(0), 2);
        }
        List<String> zeroes = new ArrayList<>();
        List<String> ones = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i).charAt(charAt) == '0') {
                zeroes.add(input.get(i));
            } else if (input.get(i).charAt(charAt) == '1') {
                ones.add(input.get(i));
            } else {
                System.out.println("ERROR");
            }
        }
        if (zeroes.size() < ones.size()) {
            currentList = zeroes;
        } else if (zeroes.size() > ones.size()) {
            currentList = ones;
        } else {
            currentList = zeroes;
        }
        return findCo2(currentList, charAt+1);
    }
}
