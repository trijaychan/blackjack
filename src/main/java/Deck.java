package src.main.java;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Deck {
    private final Card[] cards = new Card[52];
    private Queue<Card> queue;

    public Deck() {
        int counter = 0;

        for (CardSuitEnum suit : CardSuitEnum.values()) {
            for (CardRankEnum rank : CardRankEnum.values()) {
                this.cards[counter] = new Card(suit, rank);
                counter++;
            }
        }
    }

    // using Fisher-Yates shuffle algorithm
    // https://www.geeksforgeeks.org/shuffle-a-given-array-using-fisher-yates-shuffle-algorithm/
    public void shuffle() {
        queue = new LinkedList<>();
        Random rand = new Random();

        for (int i = cards.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);

            Card temp = cards[i];
            cards[i] = cards[j];
            cards[j] = temp;
        }

        Collections.addAll(queue, cards);
    }

    public Card drawCard() {
        return queue.remove();
    }

    public boolean isEmpty() {
        return queue.size() > 0;
    }
}