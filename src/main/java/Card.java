package src.main.java;

public class Card {
    private CardSuitEnum suit;
    private CardRankEnum rank;

    public Card(CardSuitEnum suit, CardRankEnum rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public CardSuitEnum getSuit() {
        return suit;
    }

    public CardRankEnum getRank() {
        return rank;
    }

    public Integer getRankInteger() {
        return rank.ordinal();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Card)) {
            return false;
        } else {
            Card cardToCompare = (Card) obj;
            return rank.equals(cardToCompare.getRank()) && suit.equals(cardToCompare.getSuit());
        }
    }

    @Override
    public String toString() {
        return "Suit: " + suit.toString() + ", Rank: " + rank.toString();
    }
}
