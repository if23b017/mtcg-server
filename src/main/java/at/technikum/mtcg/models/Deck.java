package at.technikum.mtcg.models;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        if (cards.size() < 4) {
            cards.add(card);
        }
        else {
            throw new IllegalStateException("Deck cannot contain more than 4 cards!");
        }
    }

    public List<Card> getCards() {
        return cards;
    }

    public boolean isValid() {
        return cards.size() == 4;
    }
}
