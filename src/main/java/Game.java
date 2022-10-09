package src.main.java;

import java.util.Scanner;

public class Game {
    private final Player player = new Player(false);
    private final Player dealer = new Player(true);
    private final Deck deck = new Deck();
    private boolean status = true;
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("+-----------------------------------------------------+");
        System.out.println("|                      Blackjack                      |");
        System.out.println("+-----------------------------------------------------+");

        while (this.status) {
            // Shuffle Deck
            System.out.println("Shuffling deck...");
            this.deck.shuffle();
            System.out.println("Deck shuffled!\n");

            // Allow player to place bets
            this.placeBet();

            // Initial dealing of cards
            System.out.println("Dealing cards...\n");
            this.dealCards();

            // Check for naturals
            System.out.println("Checking for naturals...\n");
            boolean containsNaturals = this.checkNaturals();

            if (!containsNaturals) {
                // Player's Play
                this.playersPlay();

                // Insurance
                this.insurance();

                // Dealer's Play
                this.dealersPlay();

                // Settlement
                this.settlement();
            }

            this.promptContinue();
        }
    }

    private void placeBet() {
        float wager = 11;

        while (wager > this.player.getMoney()) {
            System.out.printf("How much do you want to bet (balance: $" + player.getMoney() + ")? ");
            wager = scanner.nextFloat();
            scanner.nextLine();
        }

        player.setWager(wager);
        System.out.println("You have set $" + this.player.getWager() + " as your bet!\n");
    }

    private void dealCards() {
        for (int i = 0; i < 2; i++) {
            Card playerCard = deck.drawCard();
            Card dealerCard = deck.drawCard();

            if (i == 1) {
                dealerCard.flipCard();
            }

            this.player.addCard(playerCard);
            this.dealer.addCard(dealerCard);
        }

        this.printHands();

        // todo: set ace value depending on total

        if (this.player.handContainsAce()) {
            while (this.player.getAceValue() != 1 && this.player.getAceValue() != 11) {
                System.out.print("How would you like to value your ace (1 or 11)? ");
                this.player.setAceValue(scanner.nextInt());
            }
        }
    }

    private boolean checkNaturals() {
        boolean dealerNatural = this.dealer.getTotalRank() == 21;
        boolean playerNatural = this.player.getTotalRank() == 21;

        if (dealerNatural && playerNatural) {
            // player gets 1x of their bet
            System.out.print("Both dealer and player have naturals.\n");
        } else if (dealerNatural) {
            // player gets -1x of their bet
            System.out.print("Dealer has a natural.\n");
        } else if (playerNatural) {
            // player gets 1.5x of their bet
            System.out.print("Player has a natural.\n");
        } else {
            System.out.print("No naturals.\n");
            return false;
        }

        return true;
    }

    private void playersPlay() {

    }

    private void insurance() {

    }

    private void dealersPlay() {

    }

    private void settlement() {

    }

    private void promptContinue() {
        String input = "";

        while (!input.equals("y") && !input.equals("n")) {
            System.out.print("Keep playing (y/n)? ");
            input = scanner.nextLine().toLowerCase();
        }

        if (input.equals("n")) {
            this.status = false;
            scanner.close();
        }

        this.dealer.reset();
        this.player.reset();
    }

    private void printHands() {
        int cardHeight = 7;
        int cardWidth = 7;
        String output = "";

        for (int i = 0; i < cardHeight; i++) {
            if (i == 0) {
                int dealerCards = this.dealer.getTotalCards();
                String spacer = new String(new char[3 + cardWidth * (dealerCards - 1)]).replace("\0", " ");
                output += "Dealer " + spacer + "Player\n";
            }

            for (int j = 0; j < 2; j++) {
                Card[] hand = (j == 0) ? this.dealer.getHand() : this.player.getHand();
                String edge = (i == 0 || i == cardHeight - 1) ? "+" : "|";
                String symbol = (i == 0 || i == cardHeight - 1) ? "-" : " ";
                String spacer = new String(new char[cardWidth - 2]).replace("\0", symbol);

                for (Card card : hand) {
                    if (card == null) {
                        break;
                    }

                    boolean faceUp = card.getSide();

                    if (!faceUp) {
                        output += edge + spacer + edge + " ";
                    } else {
                        String inbetween;

                        if (i == 1 || i == cardHeight - 2) {
                            int rank = card.getRankInteger();
                            String sign = (rank > 10 || rank == 0) ? card.getRank().toString().substring(0, 1) : card.getRankInteger().toString();
                            int length = cardWidth - 2 - sign.length();
                            String spaces = new String(new char[length]).replace("\0", " ");
                            inbetween = (i == 1) ? sign + spaces : spaces + sign;
                        } else if (i == 3) {
                            CardSuitEnum suit = card.getSuit();
                            inbetween = suit.toString().substring(0, cardWidth - 2);
                        } else {
                            inbetween = spacer;
                        }

                        output += edge + inbetween + edge + " ";
                    }
                }

                output += " ";
            }

            output += "\n";
        }

        System.out.println(output);
    }
}
