package day15;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        File inputFile = new File("./src/day15/input.txt");
        //File inputFile = new File("./src/day15/example.txt");
        Scanner scanner = new Scanner(inputFile).useDelimiter("\r\n");
        List<String> temps = new ArrayList<>();
        while (scanner.hasNext()) {
            temps.add(scanner.nextLine());
        }
        scanner.close();

        int[][] inputs = new int[temps.size()][temps.get(0).length()];
        for (int i = 0; i < temps.size(); i++) {
            for (int j = 0; j < temps.get(0).length(); j++) {
                inputs[i][j] = Integer.parseInt(temps.get(i).charAt(j) + "");
            }
        }

        long result1 = solve1(inputs);
        System.out.println("Result 1:" + result1);
        long result2 = solve2(inputs);
        System.out.println("Result 2:" + result2);
    }


    private static long solve1(int[][] inputs) {
        int[][] result = new int[inputs.length][inputs[0].length];
        for (int i = 0; i < inputs.length; i++) {
            for (int j = 0; j < inputs[0].length; j++) {
                //was lucky that this worked
                if (i == 0 && j == 0) { // start
                    result[i][j] = 0;
                } else if (i == 0) { // top row
                    result[i][j] = result[i][j - 1] + inputs[i][j];
                } else if (j == 0) { // left column
                    result[i][j] = result[i - 1][j] + inputs[i][j];
                } else { //rest
                    result[i][j] = Math.min(result[i][j - 1], result[i - 1][j]) + inputs[i][j];
                }
            }
        }
        //printArray(result);
        return result[result.length - 1][result[0].length - 1];
    }

    private static long solve2(int[][] inputs) {
        int rows = inputs.length;
        int cols = inputs[0].length;
        int[][] fiveTimesInput = new int[rows * 5][cols * 5];
        makeBiggerMatrix(inputs, rows, cols, fiveTimesInput);
        //printArray(fiveTimesInput);
        int[][] result = new int[fiveTimesInput.length][fiveTimesInput[0].length];
        for (int[] r : result) {
            Arrays.fill(r, Integer.MAX_VALUE);
        }
        boolean done = false;
        while (!done) {
            done = true;
            for (int i = 0; i < fiveTimesInput.length; i++) {
                for (int j = 0; j < fiveTimesInput[0].length; j++) {
                    if (calcNewValue(i, j, result) + fiveTimesInput[i][j] != result[i][j]) {
                        result[i][j] = calcNewValue(i, j, result) + fiveTimesInput[i][j];
                        done = false;
                    }
                }
            }
        }
        printArray(result);
        return result[result.length - 1][result[0].length - 1] - +fiveTimesInput[0][0];
    }

    private static void makeBiggerMatrix(int[][] inputs, int rows, int cols, int[][] fiveTimesInput) {
        for (int i = 0; i < inputs.length; i++) {
            for (int j = 0; j < inputs[0].length; j++) {
                for (int r = 0; r < 5; r++) {
                    for (int c = 0; c < 5; c++) {
                        int x = i + r * rows;
                        int y = j + c * cols;
                        int v = inputs[i][j] + r + c;
                        if (v != 9) {
                            v = v % 9;
                        }
                        //System.out.println("[" + x + ", " + y + "] = " + v);
                        fiveTimesInput[x][y] = v;
                    }
                }
            }
        }
    }

    public static int calcNewValue(int i, int j, int[][] result) {
        int rows = result.length - 1;
        int cols = result[0].length - 1;
        if (i == 0 && j == 0) { // top left
            return 0;
        } else if (i == 0 && j == cols) { // top right
            return Math.min(result[i][j - 1], result[i + 1][j]);
        } else if (i == rows && j == 0) { //bottom left
            return Math.min(result[i][j + 1], result[i - 1][j]);
        } else if (i == rows && j == cols) { // bottom righ[
            return Math.min(result[i][j - 1], result[i - 1][j]);
        } else if (i == 0) { // top row
            return Math.min(result[i + 1][j], Math.min(result[i][j - 1], result[i][j + 1]));
        } else if (i == rows) { // bottom row
            return Math.min(result[i - 1][j], Math.min(result[i][j - 1], result[i][j + 1]));
        } else if (j == 0) { // left column
            return Math.min(result[i][j + 1], Math.min(result[i - 1][j], result[i + 1][j]));
        } else if (j == cols) { // left column
            return Math.min(result[i][j - 1], Math.min(result[i - 1][j], result[i + 1][j]));
        } else { //rest
            return Math.min(Math.min(result[i][j - 1], result[i - 1][j]), Math.min(result[i][j + 1], result[i + 1][j]));
        }
    }

    public static void printArray(int[][] inputs) {
        for (int i = 0; i < inputs.length; i++) {
            for (int j = 0; j < inputs[0].length; j++) {
                System.out.print(inputs[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
