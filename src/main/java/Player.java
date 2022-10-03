package src.main.java;

import java.io.PrintWriter;

public class Player {
    private Card[] hand = new Card[21];
    private float money = 10;
    private float wager = 0;
    private float insurance = 0;

    public Player(boolean dealer) {
        if (dealer) {
            this.money = 0;
        }
    }

    public float getMoney() {
        return this.money;
    }

    public float getWager() {
        return this.wager;
    }

    public float setWager(float wager) {
        return this.wager = wager;
    }

    public float getInsurance() {
        return this.insurance;
    }

    public void addCard(Card card) {
        if (this.hand[hand.length - 1] != null) {
            return;
        }

        for (int i = 0; i < hand.length; i++) {
            if (this.hand[i] == null) {
                this.hand[i] = card;
                break;
            }
        }
    }

    public void printHand() {
        String output = "";

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < hand.length; j++) {
                if (this.hand[j] == null) {
                    break;
                } else if (i == 3) {
                    CardSuitEnum suit = this.hand[j].getSuit();
                    output += "|" + suit.toString().substring(0, 5) + "| ";
                } else if (i == 1 || i == 5) {
                    int rank = this.hand[j].getRankInteger();

                    String content = (rank < 10) ? "    " : "   ";
                    content =  (i == 1) ? rank + content : content + rank;

                    output += "|" + content + "| ";
                } else if (i == 0 || i == 6) {
                    output += "+-----+ ";
                } else {
                    output += "|     | ";
                }
            }

            output += "\n";
        }

        System.out.println(output);
    }

    public void emptyHand() {
        this.hand = new Card[21];
    }

    public void reset() {
        this.emptyHand();
        this.wager = 0;
        this.insurance = 0;
    }
}