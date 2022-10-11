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
            System.out.println("\nShuffling deck...");
            this.deck.shuffle();
            System.out.println("Deck shuffled!\n");

            // Allow player to place bets
            this.placeBet();

            // Initial dealing of cards
            this.dealCards();

            // Check for naturals
            boolean containsNaturals = this.checkNaturals();

            if (!containsNaturals) {
                // Player's Play
                boolean playersPlay = this.playersPlay();

                if (!playersPlay) {
                    // Insurance
                    this.insurance();

                    // Dealer's Play
                    this.dealersPlay();

                    // Settlement
                    this.settlement();
                }
            }

            this.promptContinue();
        }
    }

    private void placeBet() {
        float wager = 11;

        while (wager > this.player.getMoney() || wager <= 0) {
            System.out.printf("How much do you want to bet (balance: $" + player.getMoney() + ")? ");
            wager = scanner.nextFloat();
            scanner.nextLine();
        }

        player.setWager(wager);
        System.out.println("You have set $" + this.player.getWager() + " as your wager!\n");
    }

    private void dealCards() {
        System.out.println("Dealing cards...\n");

        for (int i = 0; i < 2; i++) {
            Card playerCard = deck.drawCard();
            Card dealerCard = deck.drawCard();

            if (i == 1) {
                dealerCard.setFaceUp(false);
            }

            this.player.addCard(playerCard);
            this.dealer.addCard(dealerCard);
        }

        this.printHands();

        if (this.player.handContainsAce()) {
            this.player.promptAceValue(0);
            System.out.print("\n");
        }
    }

    private boolean checkNaturals() {
        boolean dealerNatural = this.dealer.getTotalRank(0) == 21;
        boolean playerNatural = this.player.getTotalRank(0) == 21;

        System.out.println("Checking for naturals...");

        if (dealerNatural && playerNatural) {
            // player gets 1x of their bet
            System.out.print("Both dealer and player have naturals!\n");
            this.player.awardWager(1);
            System.out.print("You won $" + this.player.getWager() + ".\n");
        } else if (dealerNatural) {
            // player gets -1x of their bet
            System.out.print("Dealer has a natural!\n");
            System.out.print("You lost $" + this.player.getWager() + ".\n");
        } else if (playerNatural) {
            // player gets 1.5x of their bet
            System.out.print("Player has a natural!\n");
            this.player.awardWager(1.5F);
            System.out.print("You won $" + this.player.getWager() * 1.5 + ".\n");
        } else {
            System.out.println("No naturals.\n");
            return false;
        }

        return true;
    }

    private void promptDoubleDown() {
        String input;
        System.out.print("Would you like to double down (y/n)? ");
        input = scanner.nextLine().toLowerCase();

        if (input.equals("y")) {
            this.player.doubleDown();
        }
    }

    private void promptSplitPairs() {
        String input;
        System.out.print("Would you like to split pairs (y/n)? ");
        input = scanner.nextLine().toLowerCase();

        if (input.equals("y")) {
            this.player.splitPairs();
        }
    }

    private boolean promptStandHit() {
        boolean result = false;

        for (int i = 0; i < 2; i++) {
            if (!this.player.getSplitPairs() && i == 1) {
                break;
            } else {
                result = false;
            }

            String input = "";

            while (this.player.getTotalRank(i) <= 21 && !input.equals("stand")) {
                input = "";

                while (!input.equals("stand") && !input.equals("hit")) {
                    System.out.print("Would you like to stand or hit? ");
                    input = scanner.nextLine().toLowerCase();
                }

                if (input.equals("hit")) {
                    Card card = this.deck.drawCard();
                    this.player.addCard(card, i);
                    System.out.println("\nPlayer's Hand (Total: " + this.player.getTotalRank(i) +  ")");
                    this.player.printHand(i);

                    if (card.getRank().equals(CardRankEnum.ACE) && !this.player.handContainsAce()) {
                        this.player.promptAceValue(0);
                    }
                }
            }

            int total = this.player.getTotalRank(i);

            if (total > 21) {
                System.out.println("Bust! Your hand totalled up to " + total + ".");
                System.out.println("You lost $" + this.player.getWager(i) + ".");
                result = true;
            }
        }

        return result;
    }

    private boolean playersPlay() {
        int total = this.player.getTotalRank(0);

        if (this.player.sameRank(0, 1)) {
            if (total == 10) {
                this.promptDoubleDown();
            }
            if (!this.player.getDoubledDown()) {
                this.promptSplitPairs();
            }
        } else if (total >= 9 && total <= 11) {
            this.promptDoubleDown();
        }

        return this.promptStandHit();
     }

    private void insurance() {
        Card[] cards = this.dealer.getHand().getCards();

        if (cards[0].getRank().equals(CardRankEnum.ACE)) {
            System.out.println("Dealer's face-up card is an ACE! ");
            System.out.print("How much would you like to set for insurance? ");
            this.player.setInsurance(scanner.nextFloat());
            System.out.println("You have set $" + this.player.getInsurance() + " as your side bet!\n");
        }
    }

    private void dealersPlay() {
        Card[] cards = this.dealer.getHand().getCards();
        cards[1].setFaceUp(true);

        if (this.dealer.getTotalRank(0) < 17) {
            System.out.println("\nDealer is drawing cards...\n");
        }

        while (this.dealer.getTotalRank(0) < 17) {
            Card card = this.deck.drawCard();
            this.dealer.addCard(card);
        }
    }

    private void settlement() {
        int dealerTotal = this.dealer.getTotalRank(0);

        this.printHands();

        for (int i = 0; i < 2; i++) {
            if (this.player.getHand(i) == null) {
                continue;
            }

            int playerTotal = this.player.getTotalRank(i);

            if (dealerTotal > 21) {
                System.out.println("Dealer busts!");
                System.out.println("You won $" + this.player.getWager(i) * 2 + "!");
                this.player.awardWager(2, i);
            } else if (dealerTotal == playerTotal) {
                System.out.println("Dealer and Player have the same total!");
                System.out.println("You won back your $" + this.player.getWager(i) + "!");
                this.player.awardWager(1, i);
            } else if (dealerTotal > playerTotal) {
                System.out.println("Dealer has a greater total!");
                System.out.println("You lost your $" + this.player.getWager(i) + "!");
            } else {
                System.out.println("Player has a greater total!");
                System.out.println("You won $" + this.player.getWager(i) * 2 + "!");
                this.player.awardWager(2, i);
            }
        }
    }

    private void promptContinue() {
        String input = "";

        while (!input.equals("y") && !input.equals("n")) {
            System.out.print("\nKeep playing (y/n)? ");
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
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < cardHeight; i++) {
            if (i == 0) {
                int dealerCards = this.dealer.getTotalCards();
                String spacer = new String(new char[3 + cardWidth * (dealerCards - 1)]).replace("\0", " ");
                output.append("Dealer ").append(spacer).append("Player\n");
            }

            for (int j = 0; j < ((this.player.getSplitPairs()) ? 3 : 2); j++) {
                Hand hand = (j == 0) ? this.dealer.getHand() : this.player.getHand(j - 1);
                String edge = (i == 0 || i == cardHeight - 1) ? "+" : "|";
                String symbol = (i == 0 || i == cardHeight - 1) ? "-" : " ";
                String spacer = new String(new char[cardWidth - 2]).replace("\0", symbol);

                for (Card card : hand.getCards()) {
                    if (card == null) {
                        break;
                    }

                    if (!card.getFaceUp()) {
                        output.append(edge).append(spacer).append(edge).append(" ");
                    } else {
                        String content;

                        if (i == 1 || i == cardHeight - 2) {
                            int rank = (card.getRank().equals(CardRankEnum.ACE)) ? hand.getAceValue() : card.getRankInteger();
                            String sign = (rank > 10 || rank == 0) ? card.getRank().toString().substring(0, 1) : card.getRankInteger().toString();
                            int length = cardWidth - 2 - sign.length();
                            String spaces = new String(new char[length]).replace("\0", " ");
                            content = (i == 1) ? sign + spaces : spaces + sign;
                        } else if (i == 3) {
                            CardSuitEnum suit = card.getSuit();
                            content = suit.toString().substring(0, cardWidth - 2);
                        } else {
                            content = spacer;
                        }

                        output.append(edge).append(content).append(edge).append(" ");
                    }
                }

                output.append(" ");
            }

            output.append("\n");
        }

        System.out.println(output);
    }
}
