package com.scully.cards;

import com.scully.enums.Face;
import com.scully.enums.Suit;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

/**
 * Data class that represents a Card
 */
public class Card implements Comparable<Card>, Serializable {

    /**
     *  Suit of this card, i.e. Diamonds
     */
    private final Suit suit;

    /**
     * Face of this card, i.e. Ace
     */
    public final Face face;

    public Card(Suit s, Face v) {
        this.suit = s;
        this.face = v;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return suit == card.suit &&
                face == card.face;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, face);
    }

    /**
     * @return Face of this card
     */
    public Face getFace() {
        return face;
    }

    /**
     * @return Integer value of the Face
     */
    public int getValue() {
        return face.getValue();
    }

    /**
     * @return Integer value of the Face
     */
    public Integer getValueInteger() {
        return face.getValue();
    }

    /**
     * @return Suit of this card
     */
    public Suit getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return "" + face.name() + " of " + suit.name;
    }

    /**
     * @return Short string of this card, i.e. 5 of Diamonds: "5D"
     */
    public String toShortString() {
        String val = Integer.toString(face.getValue());
        String f = (face.getValue() <= 10) ? val : face.name().substring(0, 1);
        return f + suit.name().substring(0,1);
    }

    /**
     * Used to fuzz-test functions, where a null value could be returned
     * @return a randomly generated card
     */
    public static Card getRandomCard() {
        Random rand = new Random();

        Suit s = Suit.values()[rand.nextInt(Suit.values().length)];
        Face v = Face.values()[rand.nextInt(Face.values().length)];

        return new Card(s, v);
    }

    @Override
    public int compareTo(Card card) {
        return getValueInteger().compareTo(card.getValueInteger());
    }

    /**
     * Factory method, parses cards from (0-9) or (J-A) + suit.
     * @param s String to parse
     * @return New instance of a card
     */
    public static Card strToCard(String s) {
        s = s.toUpperCase();

        Face face = null;
        Suit suit = null;

        switch (s.charAt(0)) {
            case 'A': face = Face.ACE;   break;
            case 'K': face = Face.KING;  break;
            case 'Q': face = Face.QUEEN; break;
            case 'J': face = Face.JACK;  break;
            case '0': face = Face.TEN;   break;
            case '9': face = Face.NINE;  break;
            case '8': face = Face.EIGHT; break;
            case '7': face = Face.SEVEN; break;
            case '6': face = Face.SIX;   break;
            case '5': face = Face.FIVE;  break;
            case '4': face = Face.FOUR;  break;
            case '3': face = Face.THREE; break;
            case '2': face = Face.TWO;   break;

            default:
                System.err.println(s.charAt(0) + " is not a valid face!");
                System.exit(1);
                break;
        }

        switch (s.charAt(1)) {
            case 'D': suit = Suit.DIAMONDS; break;
            case 'S': suit = Suit.SPADES;   break;
            case 'C': suit = Suit.CLUBS;    break;
            case 'H': suit = Suit.HEARTS;   break;

            default:
                System.err.println(s.charAt(1) + " is not a valid suit!");
                System.exit(1);
                break;
        }

        return new Card(suit, face);
    }

}
