package day4;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        File inputFile = new File("./src/day4/input.txt");
        //File inputFile = new File("./src/day4/example.txt");
        Scanner scanner = new Scanner(inputFile).useDelimiter("\r\n");
        List<List<CardNumber[]>> allCards = new ArrayList<>();
        Integer[] bingoNumbers = Arrays.stream(scanner.nextLine().split(",")).map(Integer::parseInt).toArray(Integer[]::new);
        scanner.nextLine(); //empty line
        List<CardNumber[]> currentCard = new ArrayList<>();
        while (scanner.hasNext()) {
            String currentLine = scanner.nextLine();
            if (currentLine.equals("")) {
                allCards.add(currentCard);
                currentCard = new ArrayList<>();
            } else {
                currentCard.add(Arrays.stream(currentLine.split(" "))
                    .filter(c -> !c.equals(""))
                    .map(Integer::parseInt)
                    .map(c -> new CardNumber(c, false))
                    .toArray(CardNumber[]::new));
            }
        }
        allCards.add(currentCard);
        scanner.close();

        int result1 = solve1(bingoNumbers, allCards);
        System.out.println("Result 1:" + result1);
        int result2 = solve2(bingoNumbers, allCards);
        System.out.println("Result 2:" + result2);
    }

    private static int solve1(Integer[] bingoNumbers, List<List<CardNumber[]>> allCards) {
        List<List<CardNumber[]>> currentCards = allCards;
        int firstCardDone = -1;
        int lastBingoNumber = -1;
        for (int i : bingoNumbers) {
            lastBingoNumber = i;
            currentCards = setAllCards(currentCards, i);
            firstCardDone = isAnyCardDone(currentCards);
            if (firstCardDone != -1) {
                break;
            }
        }
        return calculateScore(currentCards.get(firstCardDone), lastBingoNumber);
    }

    private static int solve2(Integer[] bingoNumbers, List<List<CardNumber[]>> allCards) {
        List<List<CardNumber[]>> currentCards = allCards;
        int lastBingoNumber = -1;
        for (int i : bingoNumbers) {
            lastBingoNumber = i;
            currentCards = setAllCards(currentCards, i);
            List<List<CardNumber[]>> cardsAfterThisIsRemoved = currentCards.stream().filter(c -> !isRowOrColDone(c)).collect(Collectors.toList());
            if (cardsAfterThisIsRemoved.size() == 0) {
                break;
            } else {
                currentCards = cardsAfterThisIsRemoved;
            }
        }
        return calculateScore(currentCards.get(0), lastBingoNumber);
    }

    private static int calculateScore(List<CardNumber[]> winningCard, int lastBingoNumber) {
        int score = 0;
        for (CardNumber[] row : winningCard) {
            for (CardNumber num : row) {
                if (!num.wasChosen) {
                    score += num.number;
                }
            }
        }
        return score * lastBingoNumber;
    }

    private static int isAnyCardDone(List<List<CardNumber[]>> allCards) {
        for (int i = 0; i < allCards.size(); i++) {
            if (isRowOrColDone(allCards.get(i))) {
                return i;
            }
        }
        return -1;
    }

    private static boolean isRowOrColDone(List<CardNumber[]> card) {
        //rows
        for (CardNumber[] row : card) {
            if (Arrays.stream(row).allMatch(n -> n.wasChosen)) {
                return true;
            }
        }
        //cols
        for (int i = 0; i < card.get(0).length; i++) {
            List<CardNumber> col = new ArrayList<>();
            for (int j = 0; j < card.size(); j++) {
                col.add(card.get(j)[i]);
            }
            if (col.stream().allMatch(n -> n.wasChosen)) {
                return true;
            }
        }
        return false;
    }

    private static List<List<CardNumber[]>> setAllCards(List<List<CardNumber[]>> allCards, int bingoNumber) {
        List<List<CardNumber[]>> allCardsSet = new ArrayList<>();
        for (List<CardNumber[]> card : allCards) {
            allCardsSet.add(setCard(card, bingoNumber));
        }
        return allCardsSet;
    }

    private static List<CardNumber[]> setCard(List<CardNumber[]> card, int bingoNumber) {
        for (CardNumber[] row : card) {
            for (CardNumber num : row) {
                if (num.number == bingoNumber) {
                    num.setWasChosen(true);
                }
            }
        }
        return card;
    }

    public static class CardNumber {

        public CardNumber(int num, boolean chos) {
            number = num;
            wasChosen = chos;
        }

        public int number;
        public boolean wasChosen;

        public void setWasChosen(boolean wasChosen) {
            this.wasChosen = wasChosen;
        }
    }

}
