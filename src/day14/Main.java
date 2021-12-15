package day14;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Main {

    public static void main(String[] args) throws IOException {
        File inputFile = new File("./src/day14/input.txt");
        //File inputFile = new File("./src/day14/example.txt");
        Scanner scanner = new Scanner(inputFile).useDelimiter("\r\n");
        List<Instruction> temps = new ArrayList<>();
        String input = scanner.nextLine();
        scanner.nextLine();
        while (scanner.hasNext()) {
            String[] line = scanner.nextLine().split(" -> ");
            temps.add(new Instruction(line[0], line[1]));
        }

        scanner.close();

        long result1 = solve1(temps, input);
        System.out.println("Result 1:" + result1);
        long result2 = solve2(temps, input);
        System.out.println("Result 2:" + result2);
    }


    private static long solve1(List<Instruction> instructions, String input) {
        String result = input;
        for (int i = 0; i < 10; i++) {
            result = doRound(instructions, result);
            System.out.println("Round " + i);
        }
        Long mostFound = result.chars().mapToObj(x -> (char) x)
            .collect(groupingBy(x -> x, counting()))
            .entrySet().stream()
            .max(comparingByValue())
            .get()
            .getValue();

        Long leastFound = result.chars().mapToObj(x -> (char) x)
            .collect(groupingBy(x -> x, counting()))
            .entrySet().stream()
            .min(comparingByValue())
            .get()
            .getValue();
        return mostFound - leastFound;
    }

    private static String doRound(List<Instruction> instructions, String input) {
        StringBuilder result = new StringBuilder();
        result.append(input.charAt(0));
        for (int i = 0; i < input.length() - 1; i++) {
            String current = input.charAt(i) + String.valueOf(input.charAt(i + 1));
            for (Instruction instr : instructions) {
                if (instr.before.equals(current)) {
                    result.append(instr.after);
                    result.append(input.charAt(i + 1));
                }
            }
        }
        return result.toString();
    }


    private static long solve2(List<Instruction> instructions, String input) {
        Map<String, Long> out = new HashMap<>();
        Map<String, String> bindings = new HashMap<>();
        instructions.forEach(p -> bindings.put(p.before, p.after));
        for (int i = 0; i < input.length() - 1; i++) {
            out.merge("" + input.charAt(i) + input.charAt(i + 1), 1L, Long::sum);
        }
        for (int i = 0; i < 40; i++) {
            Map<String, Long> temp = new HashMap<>();
            for (String key : out.keySet()) {
                temp.merge(key.charAt(0) + bindings.get(key), out.get(key), Long::sum);
                temp.merge(bindings.get(key) + key.charAt(1), out.get(key), Long::sum);
            }
            out = temp;
        }
        Map<Character, Long> temp = new HashMap<>();
        for (Map.Entry<String, Long> entry : out.entrySet()) {
            temp.merge(entry.getKey().charAt(0), entry.getValue(), Long::sum);
        }
        temp.merge(input.charAt(input.length() - 1), 1L, Long::sum);
        return temp.values().stream().max(Long::compareTo).get() - temp.values().stream().min(Long::compareTo).get();
    }

    public static class Instruction {

        public Instruction(String x0, String y0) {
            before = x0;
            after = y0;
        }

        public String before, after;
    }


}
