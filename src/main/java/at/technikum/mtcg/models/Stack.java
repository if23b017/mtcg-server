package at.technikum.mtcg.models;

import java.util.List;
import java.util.ArrayList;

public class Stack
{
    private List<Card> cards;

    public Stack() {
        this.cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public List<Card> getCards() {
        return cards;
    }
}
