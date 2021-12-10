package day2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        File inputFile = new File("./src/day2/input.txt");
        //File inputFile = new File("./src/day2/example.txt");
        Scanner scanner = new Scanner(inputFile).useDelimiter("\r\n");
        List<Movement> temps = new ArrayList<>();
        while (scanner.hasNext()) {
            String[] token1 = scanner.nextLine().split(" ");
            temps.add(new Movement(token1[0], Integer.parseInt(token1[1])));
        }
        scanner.close();

        Movement[] inputs = temps.toArray(new Movement[0]);

        int result1 = solve1(inputs);
        System.out.println("Result 1:" + result1);
        int result2 = solve2(inputs);
        System.out.println("Result 2:" + result2);
    }

    private static int solve1(Movement[] input) {
        int length = 0;
        int depth = 0;
        for (Movement movement : input) {
            switch (movement.getDir()) {
                case "forward":
                    length += movement.getValue();
                    break;
                case "down":
                    depth += movement.getValue();
                    break;
                case "up":
                    depth -= movement.getValue();
                    break;
                default:
                    System.out.println("Error");
                    break;
            }
        }
        return length * depth;
    }

    private static int solve2(Movement[] input) {
        int length = 0;
        int depth = 0;
        int aim = 0;
        for (Movement movement : input) {
            switch (movement.getDir()) {
                case "forward":
                    length += movement.getValue();
                    depth += movement.getValue() * aim;
                    break;
                case "down":
                    aim += movement.getValue();
                    break;
                case "up":
                    aim -= movement.getValue();
                    break;
                default:
                    System.out.println("Error");
                    break;
            }
        }
        return length * depth;
    }

    private static class Movement {
        String dir;
        int value;

        public Movement(String dir, int value) {
            setDir(dir);
            setValue(value);
        }

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
