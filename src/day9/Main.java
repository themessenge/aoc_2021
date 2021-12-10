package day9;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static int[][] inputs2;

    public static void main(String[] args) throws IOException {
        File inputFile = new File("./src/day9/input.txt");
        //File inputFile = new File("./src/day9/example.txt");
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
        inputs2 = inputs.clone();

        int result1 = solve1(inputs);
        System.out.println("Result 1:" + result1);
        int result2 = solve2();
        System.out.println("Result 2:" + result2);
    }


    private static int solve1(int[][] inputs) {
        int sum = 0;
        for (int i = 0; i < inputs.length; i++) {
            for (int j = 0; j < inputs[0].length; j++) {
                int current = inputs[i][j];
                if (i != 0 && inputs[i - 1][j] <= current) { //left of current
                    continue;
                }
                if (i != inputs.length - 1 && inputs[i + 1][j] <= current) {//right of current
                    continue;
                }
                if (j != 0 && inputs[i][j - 1] <= current) { //above current
                    continue;
                }
                if (j != inputs[0].length - 1 && inputs[i][j + 1] <= current) { //below current
                    continue;
                }
                //is low point
                System.out.println("Found low point at [" + i + ", " + j + "] : " + current);
                sum += 1 + current;
            }
        }
        return sum;
    }

    private static int solve2() {
        int sum = 0;
        List<Coordinate> lowPoints = new ArrayList<>();
        for (int i = 0; i < inputs2.length; i++) {
            for (int j = 0; j < inputs2[0].length; j++) {
                int current = inputs2[i][j];
                if (i != 0 && inputs2[i - 1][j] <= current) { //left of current
                    continue;
                }
                if (i != inputs2.length - 1 && inputs2[i + 1][j] <= current) {//right of current
                    continue;
                }
                if (j != 0 && inputs2[i][j - 1] <= current) { //above current
                    continue;
                }
                if (j != inputs2[0].length - 1 && inputs2[i][j + 1] <= current) { //below current
                    continue;
                }
                //is low point
                lowPoints.add(new Coordinate(i, j));
                System.out.println("Found low point at [" + i + ", " + j + "] : " + current);
            }
        }

        List<Integer> basinSizes = new ArrayList<>();

        for (Coordinate lowPoint : lowPoints) {
            basin = new ArrayList<>();
            basin.add(lowPoint);
            while (true) {
                int x = basin.size();
                List<Coordinate> currBasin = new ArrayList<>(basin);
                for (Coordinate c : currBasin) {
                    addNeighborsToBasin(c);
                }
                if (basin.size() == x) { //no neighbors added
                    break;
                }
            }
            basinSizes.add(basin.size());
        }
        basinSizes = basinSizes.stream().sorted().collect(Collectors.toList());
        sum = basinSizes.get(basinSizes.size() - 1) * basinSizes.get(basinSizes.size() - 2) * basinSizes.get(basinSizes.size() - 3);

        return sum;
    }

    public static List<Coordinate> basin;

    public static boolean addNeighborsToBasin(Coordinate current) {
        int i = current.x;
        int j = current.y;
        boolean r = true;
        if (i != 0 && inputs2[i - 1][j] != 9 && !basin.contains(new Coordinate(i - 1, j))) { //left of current
            basin.add(new Coordinate(i - 1, j));
            r = false;
        }
        if (i != inputs2.length - 1 && inputs2[i + 1][j] != 9 && !basin.contains(new Coordinate(i + 1, j))) {//right of current
            basin.add(new Coordinate(i + 1, j));
            r = false;
        }
        if (j != 0 && inputs2[i][j - 1] != 9 && !basin.contains(new Coordinate(i, j - 1))) { //above current
            basin.add(new Coordinate(i, j - 1));
            r = false;
        }
        if (j != inputs2[0].length - 1 && inputs2[i][j + 1] != 9 && !basin.contains(new Coordinate(i, j + 1))) { //below current
            basin.add(new Coordinate(i, j + 1));
            r = false;
        }
        return r;
    }

    private static class Coordinate {
        public int x, y;

        public Coordinate(int x0, int y0) {
            x = x0;
            y = y0;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Coordinate) {
                Coordinate c = (Coordinate) obj;
                return c.x == this.x && c.y == this.y;
            } else {
                return false;
            }
        }
    }

}
