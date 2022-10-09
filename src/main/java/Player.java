package src.main.java;

import java.io.PrintWriter;

public class Player {
    private Card[] hand = new Card[21];
    private float money = 10;
    private float wager = 0;
    private float insurance = 0;
    private int aceValue = 0;

    public Player(boolean dealer) {
        if (dealer) {
            this.money = 0;
        }
    }

    public Card[] getHand() {
        return this.hand;
    }

    public float getMoney() {
        return this.money;
    }

    public float getWager() {
        return this.wager;
    }

    public void setWager(float wager) {
        this.money -= wager;
        this.wager = wager;
    }

    public int getAceValue() {
        return this.aceValue;
    }

    public void setAceValue(int value) {
        this.aceValue = value;
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

    public boolean handContainsAce() {
        for (Card card : this.hand) {
            if (card == null) {
                return false;
            } else if (card.getRank().equals(CardRankEnum.ACE)) {
                return true;
            }
        }

        return false;
    }

    public int getTotalCards() {
        int counter = 0;

        for (Card card : hand) {
            if (card == null) {
                break;
            }

            counter += 1;
        }

        return counter;
    }

    public int getTotalRank() {
        int total = 0;

        for (Card card : hand) {
            if (card == null) {
                break;
            }

            if (card.getRank() == CardRankEnum.ACE) {
                total += this.aceValue;
            } else {
                total += card.getRankInteger();
            }
        }

        return total;
    }

    public void printHand() {
        String output = "";

        for (int i = 0; i < 7; i++) {
            for (Card card : hand) {
                if (card == null) {
                    break;
                } else if (i == 3) {
                    CardSuitEnum suit = card.getSuit();
                    output += "|" + suit.toString().substring(0, 5) + "| ";
                } else if (i == 1 || i == 5) {
                    int rank = card.getRankInteger();

                    String content = (rank < 10) ? "    " : "   ";
                    content = (i == 1) ? rank + content : content + rank;

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

    public void reset() {
        this.hand = new Card[21];
        this.aceValue = 0;
        this.wager = 0;
        this.insurance = 0;
    }
}