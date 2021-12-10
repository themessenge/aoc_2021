package day6;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        File inputFile = new File("./src/day6/input.txt");
        //File inputFile = new File("./src/day6/example.txt");
        Scanner scanner = new Scanner(inputFile).useDelimiter(",");
        List<Integer> temps = new ArrayList<>(1000000000);
        while (scanner.hasNext()) {
            temps.add(scanner.nextInt());
        }
        scanner.close();

        int result1 = solve1(temps);
        System.out.println("Result 1:" + result1);
        long result2 = solve2(temps);
        System.out.println("Result 2:" + result2);
    }

    private static int solve1(List<Integer> inputs) {
        List<Integer> currFish = new ArrayList<>(inputs);
        for (int j = 0; j < 80; j++) {
            for (int i = 0; i < currFish.size(); i++) {
                if (currFish.get(i) == 0) {
                    currFish.add(9);
                    currFish.set(i, 6);
                } else {
                    currFish.set(i, currFish.get(i) - 1);
                }
            }
        }
        return currFish.size();
    }

    private static long solve2(List<Integer> inputs) {
        List<Integer> currFish = new ArrayList<>(inputs);
        long[] fish = new long[10];
        int day = 0;
        for (int i: currFish){
            fish[i]++;
        }
        while(day < 256){
            fish[9] += fish[0];
            fish[7] += fish[0];
            fish[0] = 0;
            fish[0] = fish[1];
            fish[1] = fish[2];
            fish[2] = fish[3];
            fish[3] = fish[4];
            fish[4] = fish[5];
            fish[5] = fish[6];
            fish[6] = fish[7];
            fish[7] = fish[8];
            fish[8] = fish[9];
            fish[9] = 0;
            day++;
        }
        long sum = 0;
        for (int i = 0; i < fish.length; i++){
            sum+=fish[i];
        }
        return sum;
    }
}
