package day12;

import org.w3c.dom.Node;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    private static final String START = "start";
    private static final String END = "end";

    public static void main(String[] args) throws IOException {
        File inputFile = new File("./src/day12/input.txt");
        //File inputFile = new File("./src/day12/example.txt");
        Scanner scanner = new Scanner(inputFile).useDelimiter("\r\n");
        List<String> temps = new ArrayList<>();
        while (scanner.hasNext()) {
            temps.add(scanner.nextLine());
        }

        Map<String, List<String>> map = new HashMap<>();
        temps.stream().map(line -> line.split("-"))
            .forEach(path -> {
                map.computeIfAbsent(path[0], key -> new ArrayList<>()).add(path[1]);
                map.computeIfAbsent(path[1], key -> new ArrayList<>()).add(path[0]);
            });

        scanner.close();

        int result1 = solve1(map);
        System.out.println("Result 1:" + result1);
        long result2 = solve2(map);
        System.out.println("Result 2:" + result2);
    }

    public static int solve1(Map<String, List<String>> input) {
        List<List<String>> paths = findPaths(input, START, END, Collections.emptyList(), false);
        return paths.size();
    }

    public static int solve2(Map<String, List<String>> input) {
        List<List<String>> paths = findPaths(input, START, END, Collections.emptyList(), true);
        return paths.size();
    }

    private static List<List<String>> findPaths(Map<String, List<String>> map, String start, String end, List<String> visited, boolean canDoubleVisitSmallCave) {
        if (start.equals(end)) {
            return Collections.singletonList(Collections.singletonList(start));
        }

        List<String> currentPath = new ArrayList<>(visited);
        currentPath.add(start);

        boolean isSecondSmallCaveVisit = start.toLowerCase().equals(start) && visited.contains(start);

        List<String> visitable = map.get(start).stream()
            .filter(cave -> !START.equals(cave))
            .filter(cave -> cave.toUpperCase().equals(cave) || !visited.contains(cave) || (!isSecondSmallCaveVisit && canDoubleVisitSmallCave))
            .collect(Collectors.toList());

        List<List<String>> paths = new ArrayList<>();

        visitable.forEach(cave -> paths.addAll(findPaths(map, cave, end, currentPath,
            !isSecondSmallCaveVisit && canDoubleVisitSmallCave)));

        return paths;
    }
}
