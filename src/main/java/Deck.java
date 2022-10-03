package src.main.java;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Deck {
    private Card[] cards = new Card[52];
    private Queue<Card> queue;

    public Deck() {
        this.initializeCards();
    }

    public void initializeCards() {
        int counter = 0;

        for (CardSuitEnum suit : CardSuitEnum.values()) {
            for (CardRankEnum rank : CardRankEnum.values()) {
                cards[counter] = new Card(suit, rank);
            }
        }
    }

    // using Fisher-Yates shuffle algorithm
    // https://www.geeksforgeeks.org/shuffle-a-given-array-using-fisher-yates-shuffle-algorithm/
    public void shuffle() {
        queue = new LinkedList<>();
        Random rand = new Random();

        for (int i = cards.length; i > 0; i--) {
            int j = rand.nextInt(i + 1);

            Card temp = cards[i];
            cards[i] = cards[j];
            cards[j] = temp;
        }

        for (int i = 0; i < cards.length; i++) {
            queue.add(cards[i]);
        }
    }

    public Card dealCard() {
        return queue.remove();
    }

    public boolean isEmpty() {
        return queue.size() > 0;
    }
}