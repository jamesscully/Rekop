package evaluators;

import cards.Card;
import cards.TexasHand;
import enums.Face;
import enums.Suit;
import enums.TexasResults;
import game.TexasTable;

import java.util.*;

public class TexasEvaluator {

    TexasHand player = null;
    TexasHand table  = null;

    TexasHand overall = null;


    Map<Suit, Integer> suitCountMap = new HashMap<>();
    Map<Face, Integer> cardCountMap = new HashMap<>();;

    ArrayList<Card> cards = new ArrayList<>();

    public TexasEvaluator(TexasHand player, TexasTable table) {
        this.player = player;

//        if(!table)
//            throw new IllegalArgumentException("TexasEvaluator: second argument must be have IS_TABLE flag enabled.");

        this.table  = table.tableCards;


        cards.addAll(player.getCards());
        cards.addAll(this.table.getCards());

        Collections.sort(cards);

        getCardSuits();
        getCardValues();
    }

    public TexasEvaluator(String debugStringToHand) {
        if(debugStringToHand.length() != 20)
            System.exit(1);

        String[] strCards = debugStringToHand.split("\\s");

        for(String s : strCards) {
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
            }

            switch (s.charAt(1)) {
                case 'D': suit = Suit.DIAMONDS; break;
                case 'S': suit = Suit.SPADES;   break;
                case 'C': suit = Suit.CLUBS;    break;
                case 'H': suit = Suit.HEARTS;   break;
            }

            cards.add(new Card(suit, face));
        }

        Collections.sort(cards);

        getCardSuits();
        getCardValues();
    }



    public TexasResults evaluate() {

        return TexasResults.HIGH_CARD;
    }


    /**
     * Gets the highest valued card in the hand
     * @return {@link Card} of highest value in hand
     */
    public Card getHighestCard() {

        Card highest = cards.get(0);

        for(Card c : cards) {
            if(c.getValue() > highest.getValue())
                highest = c;
        }

        return highest;
    }

    /**
     * Determines whether the hand has a flush
     * @return True if all cards are the same suit
     */
    public boolean isFlush() {
        for(Map.Entry<Suit, Integer> entry : suitCountMap.entrySet()) {
            if(entry.getValue() == 5)
                return true;
        }
        return false;
    }

    /**
     * Determines whether the hand is a royal flush
     * @return True if hand is a royal flush
     */
    public boolean isRoyalFlush() {

        return false;
    }

    /**
     * This determines whether the hand is in numerical order.
     * It also relies on the {@link TexasHand} array, as this should not change the card positions.
     * @return Whether the hand is classed as a straight
     */
    public boolean isStraight() {
        System.out.println("Sorted :   " + cards);
        System.out.println("Value map: " + cardCountMap);

        ArrayList<Card> sorted = new ArrayList<>(cards);
        Collections.sort(sorted, Collections.reverseOrder());

        System.out.println(sorted);

        int streak = 0;

        // our previous value going in should be the first in the sorted array
        int previousVal = sorted.get(0).getValue();

        for(int i = 1; i < 7; i++)  {
            Card cCard = sorted.get(i);
            int value  = cCard.getValue();

            // if we have a previous card of same value, just skip over.
            if(previousVal == value)
                continue;

            // if the previous card was higher than the current, then add to streak
            if(previousVal == value + 1) {
                streak++;
            } else {
                streak = 0;
            }

            // if we've already managed a straight, then return true.
            // note that this should return the highest STRAIGHT, as we're descending down.
            // todo make this function return points where there is a straight beginning, to determine straight flushes.
            if(streak == 4)
                return true;

            previousVal = value;
        }

        return false;
    }

    /**
     * Determines whether there is any kinds in the hand.
     * Note, this must be checked for null - a highcard/straight/flush would return null.
     * @return {@link TexasResults#FULL_HOUSE}, {@link TexasResults#FOUR_OF_KIND}, {@link TexasResults#THREE_OF_KIND}, {@link TexasResults#TWO_PAIR}, {@link TexasResults#PAIR} or null if none found.
     *
     */
    public TexasResults getKinds() {
        return null;
    }

    private void getCardSuits() {
        for(Card c : cards) {
            Integer count = suitCountMap.get(c.getSuit());
            suitCountMap.put(c.getSuit(), count == null ? 1 : count + 1);
        }
    }

    private void getCardValues() {
        for(Card c : cards) {
            Integer count = cardCountMap.get(c.getFace());
            cardCountMap.put(c.getFace(), count == null ? 1 : count + 1);
        }
    }





}