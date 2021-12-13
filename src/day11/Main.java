package day11;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static Octopus[][] inputs;
    public static Octopus[][] inputs2;

    public static void main(String[] args) throws IOException {
        File inputFile = new File("./src/day11/input.txt");
        //File inputFile = new File("./src/day11/example.txt");
        Scanner scanner = new Scanner(inputFile).useDelimiter("\r\n");
        List<String> temps = new ArrayList<>();
        while (scanner.hasNext()) {
            temps.add(scanner.nextLine());
        }
        scanner.close();

        inputs = new Octopus[temps.size()][temps.get(0).length()];
        inputs2 = new Octopus[temps.size()][temps.get(0).length()];
        for (int i = 0; i < temps.size(); i++) {
            for (int j = 0; j < temps.get(0).length(); j++) {
                inputs[i][j] = new Octopus(Integer.parseInt(temps.get(i).charAt(j) + ""), false);
                inputs2[i][j] = new Octopus(Integer.parseInt(temps.get(i).charAt(j) + ""), false);
            }
        }

        int result1 = solve1();
        System.out.println("Result 1:" + result1);
        long result2 = solve2();
        System.out.println("Result 2:" + result2);
    }


    private static int solve1() {
        int sum = 0;
        for (int round = 0; round < 100; round++) {
            //System.out.println("Round " + round);
            //printGridValue(inputs);
            for (Octopus[] input : inputs) {
                for (int j = 0; j < inputs[0].length; j++) {
                    input[j].value++;
                }
            }

            for (int i = 0; i < inputs.length; i++) {
                for (int j = 0; j < inputs[0].length; j++) {
                    if (inputs[i][j].value > 9 && !inputs[i][j].hasFlashedThisRound) {
                        sum += 1;
                        inputs[i][j].hasFlashedThisRound = true;
                        int countFlashes = setSurroundedValues(i, j, inputs);
                        sum += countFlashes;
                    }
                }
            }
            for (Octopus[] input : inputs) {
                for (int j = 0; j < inputs[0].length; j++) {
                    if (input[j].value > 9) {
                        input[j].value = 0;
                        input[j].hasFlashedThisRound = false;
                    }
                    if (input[j].hasFlashedThisRound) {
                        System.out.println("ERROR");
                    }
                }
            }
        }
        return sum;
    }

    private static long solve2() {
        int firstFlash = 0;
        int round = 0;
        while (firstFlash == 0) {
            //System.out.println("Round " + round);
            //printGridValue(inputs);
            for (Octopus[] input : inputs2) {
                for (int j = 0; j < inputs2[0].length; j++) {
                    input[j].value++;
                }
            }

            for (int i = 0; i < inputs2.length; i++) {
                for (int j = 0; j < inputs2[0].length; j++) {
                    if (inputs2[i][j].value > 9 && !inputs2[i][j].hasFlashedThisRound) {
                        inputs2[i][j].hasFlashedThisRound = true;
                        setSurroundedValues(i, j, inputs2);
                    }
                }
            }
            for (Octopus[] input : inputs2) {
                for (int j = 0; j < inputs2[0].length; j++) {
                    if (input[j].value > 9) {
                        input[j].value = 0;
                        input[j].hasFlashedThisRound = false;
                    }
                    if (input[j].hasFlashedThisRound) {
                        System.out.println("ERROR");
                    }
                }
            }
            if (isAllZeros(inputs2)) {
                firstFlash = round + 1;
            }
            round++;
        }
        return firstFlash;
    }

    public static int setSurroundedValues(int i, int j, Octopus[][] currInputs) {
        int count = 0;
        count += increaseAndFlashIfHasNotFlashed(i - 1, j - 1, currInputs);
        count += increaseAndFlashIfHasNotFlashed(i - 1, j, currInputs);
        count += increaseAndFlashIfHasNotFlashed(i - 1, j + 1, currInputs);
        count += increaseAndFlashIfHasNotFlashed(i, j - 1, currInputs);
        count += increaseAndFlashIfHasNotFlashed(i, j + 1, currInputs);
        count += increaseAndFlashIfHasNotFlashed(i + 1, j - 1, currInputs);
        count += increaseAndFlashIfHasNotFlashed(i + 1, j, currInputs);
        count += increaseAndFlashIfHasNotFlashed(i + 1, j + 1, currInputs);
        return count;
    }

    public static int increaseAndFlashIfHasNotFlashed(int i, int j, Octopus[][] currInputs) {
        if (i < 0 || j < 0 || i >= currInputs.length || j >= currInputs[0].length) {
            return 0;
        }
        currInputs[i][j].value++;
        if (currInputs[i][j].value > 9 && !currInputs[i][j].hasFlashedThisRound) {
            currInputs[i][j].hasFlashedThisRound = true;
            return 1 + setSurroundedValues(i, j, currInputs);
        }
        return 0;
    }

    public static class Octopus {
        public int value;
        public boolean hasFlashedThisRound;

        public Octopus(int v, boolean f) {
            value = v;
            hasFlashedThisRound = f;
        }
    }

    public static void printGridValue(Octopus[][] inputs) {
        for (int i = 0; i < inputs.length; i++) {
            for (int j = 0; j < inputs[0].length; j++) {
                System.out.print(inputs[i][j].value + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static boolean isAllZeros(Octopus[][] inputs) {
        for (int i = 0; i < inputs.length; i++) {
            for (int j = 0; j < inputs[0].length; j++) {
                if (inputs[i][j].value != 0) {
                    return false;
                }
            }
        }
        return true;
    }


}
