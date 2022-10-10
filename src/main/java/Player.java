package src.main.java;

import java.util.Scanner;

public class Player {
    private Hand[] hands = new Hand[2];
    private float money = 10;
    private float insurance = 0;
    private boolean splitPairs = false;
    private boolean doubledDown = false;

    public Player(boolean dealer) {
        this.hands[0] = new Hand();

        if (dealer) {
            this.money = 0;
        }
    }

    public Hand[] getHands() {
        return this.hands;
    }

    public Hand getHand() {
        return this.hands[0];
    }
    public Hand getHand(int index) {
        return this.hands[index];
    }

    public float getMoney() {
        return this.money;
    }

    public void awardWager(float multiplier) {
        this.money += multiplier * this.hands[0].getWager();
    }

    public void awardWager(float multiplier, int index) {
        this.money += multiplier * this.hands[index].getWager();
    }

    public float getWager() {
        return this.hands[0].getWager();
    }

    public float getWager(int index) {
        return this.hands[index].getWager();
    }

    public void setWager(float wager) {
        this.money -= wager;
        this.hands[0].setWager(wager);
    }

    public void setWager(float wager, int index) {
        this.money -= wager;
        this.hands[1].setWager(wager);
    }

    public float getInsurance() {
        return this.insurance;
    }

    public void setInsurance(float insurance) {
        this.insurance = insurance;
    }

    public void addCard(Card card) {
        this.hands[0].addCard(card);
    }

    public void addCard(Card card, int index) {
        this.hands[index].addCard(card);
        int aceValue = this.hands[index].getAceValue();
        if (card.getRank().equals(CardRankEnum.ACE) && aceValue != 1 && aceValue != 11) {
            this.promptAceValue(index);
        }
    }

    public void promptAceValue(int index) {
        // todo: set ace value depending on total
        Scanner scanner = new Scanner(System.in);
        Hand hand = this.hands[index];

        while (hand.getAceValue() != 1 && hand.getAceValue() != 11) {
            System.out.print("How would you like to value your ace (1 or 11)? ");
            hand.setAceValue(scanner.nextInt());
        }
    }

    public boolean handContainsAce() {
        return this.hands[0].containsAce();
    }

    public int getTotalCards() {
        return this.hands[0].getNumberOfCards();
    }

    public int getTotalRank(int index) {
        return this.hands[index].getTotalRank();
    }

    public boolean sameRank(int index1, int index2) {
        return this.hands[0].sameRank(index1, index2);
    }

    public void doubleDown() {
        if (this.hands[0].getWager() * 2 > this.money) {
            System.out.println("You don't have enough money to double down!");
        } else {
            this.doubledDown = true;
            this.money -= this.hands[0].getWager();
            this.hands[0].setWager(this.hands[0].getWager() * 2);

            System.out.println("Your wager has become $" + this.hands[0].getWager() + "!\n");
        }
    }

    public void splitPairs() {
        if (this.hands[0].getWager() * 2 > this.money) {
            System.out.println("You don't have enough money to split your pairs!");
        } else {
            Card[] cards = this.hands[0].getCards();
            Card card = cards[1];
            cards[1] = null;

            this.hands[1] = new Hand();
            this.hands[1].addCard(card);
            this.setWager(this.getWager(0), 1);
            this.splitPairs = true;

            System.out.println("You have split your hand!");
            System.out.println("Now cycling through your hands...\n");
        }
    }

    public boolean getSplitPairs() {
        return this.splitPairs;
    }

    public boolean getDoubledDown() {
        return this.doubledDown;
    }

    public void printHand() {
        this.hands[0].print();
    }

    public void printHand(int index) {
        this.hands[index].print();
    }

    public void reset() {
        this.hands[0].empty();
        this.insurance = 0;

        if (this.splitPairs) {
            this.hands[1].empty();
        }
    }
}