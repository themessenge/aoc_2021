package day13;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static int[][] array;

    public static void main(String[] args) throws IOException {
        //File inputFile = new File("./src/day13/input.txt");
        File inputFile = new File("./src/day13/example.txt");
        Scanner scanner = new Scanner(inputFile).useDelimiter("\r\n");
        List<Coordinate> temps = new ArrayList<>();
        while (scanner.hasNext()) {
            String[] line = scanner.nextLine().split(",");
            if (line[0].equals("")) {
                break;
            }
            temps.add(new Coordinate(Integer.parseInt(line[1]), Integer.parseInt(line[0])));
        }

        List<Instruction> foldInstructions = new ArrayList<>();
        while (scanner.hasNext()) {
            String[] line = scanner.nextLine().split("=");
            foldInstructions.add(new Instruction(line[0].charAt(line[0].length() - 1), Integer.parseInt(line[1])));
        }
        scanner.close();

        int maxRows = temps.stream().max(Comparator.comparingInt(o -> o.x)).get().x;
        int maxCols = temps.stream().max(Comparator.comparingInt(o -> o.y)).get().y;

        array = new int[maxRows + 1][maxCols + 1];
        for (Coordinate c : temps) {
            array[c.x][c.y] = 1;
        }

        int result1 = solve1(array, foldInstructions);
        System.out.println("Result 1:" + result1);
        long result2 = solve2(foldInstructions);
        System.out.println("Result 2:" + result2);
    }


    private static int solve1(int[][] inputs, List<Instruction> instructions) {
        int sum = 0;
        if (instructions.get(0).dir == 'x') {
            //vertical
            int[][] result = new int[inputs.length][inputs[0].length / 2];
            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < result[0].length; j++) {
                    if (inputs[i][j] == 1 || inputs[i][inputs[0].length - 1 - j] == 1) {
                        result[i][j] = 1;
                        sum += 1;
                    }
                }
            }
        } else if (instructions.get(0).dir == 'y') {
            //horizontal
            int[][] result = new int[inputs.length / 2][inputs[0].length];
            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < result[0].length; j++) {
                    if (inputs[i][j] == 1 || inputs[inputs.length - 1 - i][j] == 1) {
                        result[i][j] = 1;
                        sum += 1;
                    }
                }
            }
        }
        return sum;
    }

    private static long solve2(List<Instruction> instructions) {
        int sum = 0;
        for (Instruction i : instructions) {
            doInstruction(i);
        }
        printArray(array);
        return sum;
    }

    public static void doInstruction(Instruction instr) {
        if (instr.dir == 'x') {
            //vertical
            int[][] result = new int[array.length][array[0].length / 2];
            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < result[0].length; j++) {
                    if (array[i][j] == 1 || array[i][array[0].length - 1 - j] == 1) {
                        result[i][j] = 1;
                    }
                }
            }
            array = result.clone();
        } else if (instr.dir == 'y') {
            //horizontal
            int[][] result = new int[array.length / 2][array[0].length];
            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < result[0].length; j++) {
                    if (array[i][j] == 1 || array[array.length - 1 - i][j] == 1) {
                        result[i][j] = 1;
                    }
                }
            }
            array = result.clone();
        }
    }

    public static class Coordinate {

        public Coordinate(int x0, int y0) {
            x = x0;
            y = y0;
        }

        public int x, y;
    }

    public static class Instruction {

        public Instruction(char d, int v) {
            dir = d;
            val = v;
        }

        public char dir;

        public int val;
    }

    public static void printArray(int[][] inputs) {
        for (int i = 0; i < inputs.length; i++) {
            for (int j = 0; j < inputs[0].length; j++) {
                if (inputs[i][j] == 1) {
                    System.out.print("# ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

}
