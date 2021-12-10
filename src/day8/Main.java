package day8;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        File inputFile = new File("./src/day8/input.txt");
        //File inputFile = new File("./src/day8/example.txt");
        Scanner scanner = new Scanner(inputFile).useDelimiter(",");
        List<Display> temps = new ArrayList<>();
        while (scanner.hasNext()) {
            String[] s = scanner.nextLine().split(Pattern.quote(" | "));
            //sort both so they match
            temps.add(new Display(Arrays.stream(s[0].split(" ")).map(Main::sort).collect(Collectors.toList()), Arrays.stream(s[1].split(" ")).map(Main::sort).collect(Collectors.toList())));
        }
        scanner.close();

        int result1 = solve1(temps);
        System.out.println("Result 1:" + result1);
        long result2 = solve2(temps);
        System.out.println("Result 2:" + result2);
    }

    private static String sort(String s) {
        char[] sc = s.toCharArray();
        Arrays.sort(sc);
        return String.valueOf(sc);
    }

    private static int solve1(List<Display> inputs) {
        int sum = 0;
        for (Display display : inputs) {
            for (String v : display.values) {
                if (v.length() == 2 || v.length() == 4 || v.length() == 3 || v.length() == 7) {
                    sum++;
                }
            }
        }
        return sum;
    }

    private static long solve2(List<Display> inputs) {
        int sum = 0;
        for (Display display : inputs) {
            List<String> combinations = display.combinations;
            Map<String, Integer> patternMap = new HashMap<>();
            int indexOfSix = 0;
            combinations.sort(Comparator.comparingInt(String::length)); //lengths: 2, 3, 4, 5, 5, 5, 6, 6, 6, 7
            patternMap.put(combinations.get(0), 1); // one
            patternMap.put(combinations.get(1), 7); // seven
            patternMap.put(combinations.get(2), 4); // four
            patternMap.put(combinations.get(9), 8); // eight
            //find ones with length 6 (nine, zero and six)
            for (int i = 6; i < 9; i++) {
                String combination = combinations.get(i);
                //if combination contains all segments of one, four and seven, it is nine
                if (contains(combination, combinations.get(0)) && contains(combination, combinations.get(1)) && contains(combination, combinations.get(2))) {
                    patternMap.put(combination, 9);
                }
                //if combination contains all segments of one and seven, it is zero
                else if (contains(combination, combinations.get(0)) && contains(combination, combinations.get(1))) {
                    patternMap.put(combination, 0);
                }
                //it must be six then
                else {
                    patternMap.put(combination, 6);
                    indexOfSix = i;
                }
            }
            //find ones with length 5 (two, three, five)
            for (int i = 3; i < 6; i++) {
                String combination = combinations.get(i);
                //if combination contains all segments of one and seven, it is three
                if (contains(combination, combinations.get(0)) && contains(combination, combinations.get(1))) {
                    patternMap.put(combination, 3);
                }
                //if six contains all segments of combination, it is five
                else if (contains(combinations.get(indexOfSix), combination)) {
                    patternMap.put(combination, 5);
                }
                //it must be two then
                else {
                    patternMap.put(combination, 2);
                }
            }
            //decode
            StringBuilder number = new StringBuilder();
            for (String value : display.values) {
                number.append(patternMap.get(value));
            }
            sum += Integer.parseInt(number.toString());
        }
        return sum;
    }

    public static boolean contains(String current, String comparison) {
        for (char c : comparison.toCharArray()) {
            if (!current.contains(c + "")) {
                return false;
            }
        }
        return true;
    }


    private static class Display {
        List<String> combinations;
        List<String> values;

        public Display(List<String> c, List<String> v) {
            combinations = c;
            values = v;
        }
    }

}
