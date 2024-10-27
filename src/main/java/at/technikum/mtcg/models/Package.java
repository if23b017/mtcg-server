package at.technikum.mtcg.models;

import java.util.List;
import java.util.ArrayList;

public class Package {
    private List<Card> cards;

    public Package() {
        this.cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        if (cards.size() < 5) {
            cards.add(card);
        }
        else {
            throw new IllegalStateException("Package cannot contain more then 5 cards!");
        }
    }

    public List<Card> getCards() {
        return cards;
    }
}
