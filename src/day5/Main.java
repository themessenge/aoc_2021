package day5;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static Map<Coordinate, Integer> currentCoords = new HashMap<>();
    public static Map<Coordinate, Integer> currentCoords2 = new HashMap<>();

    public static void main(String[] args) throws IOException {
        File inputFile = new File("./src/day5/input.txt");
        //File inputFile = new File("./src/day5/example.txt");
        Scanner scanner = new Scanner(inputFile).useDelimiter("\r\n");
        List<Line> temps = new ArrayList<>();
        while (scanner.hasNext()) {
            Line line = new Line(Arrays.stream(scanner.nextLine().split(" -> ")).map(x -> new Coordinate(x.split(","))).toArray(Coordinate[]::new));
            temps.add(line);
        }
        scanner.close();

        Line[] inputs = temps.toArray(new Line[0]);

        int result1 = solve1(inputs);
        System.out.println("Result 1:" + result1);
        int result2 = solve2(inputs);
        System.out.println("Result 2:" + result2);
    }

    private static int solve1(Line[] inputs) {
        for (Line line : inputs) {
            createCoordinateFromLine(line);
        }
        int sum = 0;
        for (Map.Entry<Coordinate, Integer> entry : currentCoords.entrySet()) {
            if (entry.getValue() > 1) {
                sum++;
            }
        }
        return sum;
    }

    private static int solve2(Line[] inputs) {
        for (Line line : inputs) {
            createCoordinateFromLineDiagonal(line);
        }
        int sum = 0;
        for (Map.Entry<Coordinate, Integer> entry : currentCoords2.entrySet()) {
            if (entry.getValue() > 1) {
                sum++;
            }
        }
        return sum;
    }

    public static void createCoordinateFromLine(Line line) {
        //vertical line
        if (line.c1.x == line.c2.x) {
            int x = line.c1.x;
            for (int i = Math.min(line.c1.y, line.c2.y); i <= Math.max(line.c1.y, line.c2.y); i++) {
                Coordinate c = new Coordinate(x, i);
                if (currentCoords.containsKey(c)) {
                    currentCoords.put(c, currentCoords.get(c) + 1);
                } else {
                    currentCoords.put(c, 1);
                }
            }
            //horizontal line
        } else if (line.c1.y == line.c2.y) {
            int y = line.c1.y;
            for (int i = Math.min(line.c1.x, line.c2.x); i <= Math.max(line.c1.x, line.c2.x); i++) {
                Coordinate c = new Coordinate(i, y);
                if (currentCoords.containsKey(c)) {
                    currentCoords.put(c, currentCoords.get(c) + 1);
                } else {
                    currentCoords.put(c, 1);
                }
            }
        } else {
            //System.out.println("ERROR");
        }
    }

    public static void createCoordinateFromLineDiagonal(Line line) {
        //vertical line
        if (line.c1.x == line.c2.x) {
            int x = line.c1.x;
            for (int i = Math.min(line.c1.y, line.c2.y); i <= Math.max(line.c1.y, line.c2.y); i++) {
                Coordinate c = new Coordinate(x, i);
                if (currentCoords2.containsKey(c)) {
                    currentCoords2.put(c, currentCoords2.get(c) + 1);
                } else {
                    currentCoords2.put(c, 1);
                }
            }
            //horizontal line
        } else if (line.c1.y == line.c2.y) {
            int y = line.c1.y;
            for (int i = Math.min(line.c1.x, line.c2.x); i <= Math.max(line.c1.x, line.c2.x); i++) {
                Coordinate c = new Coordinate(i, y);
                if (currentCoords2.containsKey(c)) {
                    currentCoords2.put(c, currentCoords2.get(c) + 1);
                } else {
                    currentCoords2.put(c, 1);
                }
            }
            //diagonal 1 (top left to bottom right)
        } else if ((line.c1.x > line.c2.x && line.c1.y > line.c2.y) || (line.c1.x < line.c2.x && line.c1.y < line.c2.y)) {
            int j = Math.min(line.c1.y, line.c2.y);
            for (int i = Math.min(line.c1.x, line.c2.x); i <= Math.max(line.c1.x, line.c2.x); i++) {
                Coordinate c = new Coordinate(i, j);
                if (currentCoords2.containsKey(c)) {
                    currentCoords2.put(c, currentCoords2.get(c) + 1);
                } else {
                    currentCoords2.put(c, 1);
                }
                j++;
            }
            //diagonal 2 (bottom left to top right)
        } else {
            int j = Math.max(line.c1.y, line.c2.y);
            for (int i = Math.min(line.c1.x, line.c2.x); i <= Math.max(line.c1.x, line.c2.x); i++) {
                Coordinate c = new Coordinate(i, j);
                if (currentCoords2.containsKey(c)) {
                    currentCoords2.put(c, currentCoords2.get(c) + 1);
                } else {
                    currentCoords2.put(c, 1);
                }
                j--;
            }
        }
    }

    public static class Line {

        public Line(Coordinate[] coordinates) {
            c1 = coordinates[0];
            c2 = coordinates[1];
        }

        public Coordinate c1, c2;
    }

    public static class Coordinate {

        public Coordinate(String[] c) {
            x = Integer.parseInt(c[0]);
            y = Integer.parseInt(c[1]);
        }

        public Coordinate(int x0, int y0) {
            x = x0;
            y = y0;
        }

        public int x, y;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Coordinate that = (Coordinate) o;

            if (x != that.x) return false;
            return y == that.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }

}
