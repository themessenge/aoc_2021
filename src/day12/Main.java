package day12;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.stream.Collectors;

public class Main {


    public static void main(String[] args) throws IOException {
        File inputFile = new File("./src/day12/input.txt");
        //File inputFile = new File("./src/day12/example.txt");
        Scanner scanner = new Scanner(inputFile).useDelimiter("\r\n");
        List<String> temps = new ArrayList<>();
        while (scanner.hasNext()) {
            temps.add(scanner.nextLine());
        }
        scanner.close();


        int result1 = solve1(temps);
        System.out.println("Result 1:" + result1);
        long result2 = solve2(temps);
        System.out.println("Result 2:" + result2);
    }


    private static int solve1(List<String> inputs) {
        int sum = 0;
        for (String line : inputs) {
            Stack<Character> currStack = new Stack<>();
            for (Character c : line.toCharArray()) {
                if (c == '(' || c == '[' || c == '{' || c == '<') {
                    currStack.push(c);
                } else {
                    if (c == ')' || c == ']' || c == '}' || c == '>') {
                        Character top = currStack.pop();
                        if (c == ')' && top != '(') {
                            sum += 3;
                            break;
                        } else if (c == ']' && top != '[') {
                            sum += 57;
                            break;
                        } else if (c == '}' && top != '{') {
                            sum += 1197;
                            break;
                        } else if (c == '>' && top != '<') {
                            sum += 25137;
                            break;
                        }
                    }
                }
            }
        }
        return sum;
    }

    private static long solve2(List<String> inputs) {
        List<String> notCorruptLines = new ArrayList<>();
        for (String input : inputs) {
            Stack<Character> currStack = new Stack<>();
            boolean isCorrupt = false;
            for (Character c : input.toCharArray()) {
                if (c == '(' || c == '[' || c == '{' || c == '<') {
                    currStack.push(c);
                } else {
                    if (c == ')' || c == ']' || c == '}' || c == '>') {
                        Character top = currStack.pop();
                        if (c == ')' && top != '(') {
                            isCorrupt = true;
                            break;
                        } else if (c == ']' && top != '[') {
                            isCorrupt = true;
                            break;
                        } else if (c == '}' && top != '{') {
                            isCorrupt = true;
                            break;
                        } else if (c == '>' && top != '<') {
                            isCorrupt = true;
                            break;
                        }
                    }
                }
            }
            if (!isCorrupt) {
                notCorruptLines.add(input);
            }
        }
        List<Long> scores = new ArrayList<>();
        for (String notCorrupt : notCorruptLines) {
            Stack<Character> currStack = new Stack<>();
            List<Character> addChars = new ArrayList<>();
            for (Character c : notCorrupt.toCharArray()) {
                if (c == '(' || c == '[' || c == '{' || c == '<') {
                    currStack.push(c);
                } else {
                    if (c == ')' || c == ']' || c == '}' || c == '>') {
                        Character top = currStack.pop();
                    }
                }
            }
            while (!currStack.empty()) {
                addChars.add(currStack.pop());
            }
            long score = 0;
            for (Character c : addChars) {
                score *= 5;
                if (c == '(') {
                    score += 1;
                } else if (c == '[') {
                    score += 2;
                } else if (c == '{') {
                    score += 3;
                } else if (c == '<') {
                    score += 4;
                }
            }
            scores.add(score);
        }

        scores = scores.stream().sorted().collect(Collectors.toList());
        return scores.get(scores.size() / 2);
    }


}
