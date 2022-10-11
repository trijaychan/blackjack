package src.main.java;

public class Hand {
    private Card[] cards = new Card[21];
    private int aceValue = 0;
    private float wager = 0;

    public Card[] getCards() {
        return this.cards;
    }

    public float getWager() {
        return this.wager;
    }

    public void setWager(float wager) {
        this.wager = wager;
    }

    public int getAceValue() {
        return this.aceValue;
    }

    public void setAceValue(int value) {
        this.aceValue = value;
    }

    public void addCard(Card card) {
        if (this.cards[this.cards.length - 1] != null) {
            return;
        }

        for (int i = 0; i < cards.length; i++) {
            if (this.cards[i] == null) {
                this.cards[i] = card;
                break;
            }
        }
    }

    public boolean containsAce() {
        for (Card card : this.cards) {
            if (card == null) {
                return false;
            } else if (card.getRank().equals(CardRankEnum.ACE)) {
                return true;
            }
        }

        return false;
    }

    public int getNumberOfCards() {
        int counter = 0;

        for (Card card : cards) {
            if (card == null) {
                break;
            }

            counter += 1;
        }

        return counter;
    }

    public int getTotalRank() {
        int total = 0;

        for (Card card : this.cards) {
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

    public boolean sameRank(int index1, int index2) {
        return this.cards[index1].getRank().equals(this.cards[index2].getRank());
    }

    public void empty() {
        for (Card card : this.cards) {
            if (card == null) {
                break;
            }

            card.setFaceUp(true);
        }

        this.cards = new Card[21];
        this.aceValue = 0;
    }

    public void print() {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < 7; i++) {
            for (Card card : cards) {
                if (card == null) {
                    break;
                } else if (i == 3) {
                    CardSuitEnum suit = card.getSuit();
                    output.append("|").append(suit.toString(), 0, 5).append("| ");
                } else if (i == 1 || i == 5) {
                    int rank = (card.getRank().equals(CardRankEnum.ACE)) ? this.aceValue : card.getRankInteger();

                    String content = (rank < 10) ? "    " : "   ";
                    content = (i == 1) ? rank + content : content + rank;

                    output.append("|").append(content).append("| ");
                } else if (i == 0 || i == 6) {
                    output.append("+-----+ ");
                } else {
                    output.append("|     | ");
                }
            }

            output.append("\n");
        }

        System.out.println(output);
    }
}
