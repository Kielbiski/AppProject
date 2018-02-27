package quest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Stack;

import static org.junit.Assert.*;

public class PlayerTest {

    Player jay = new Player("Jay");
    Player tom = new Player("Tom");
    private ArrayList<AdventureCard> cardsInHandTest = new ArrayList<>();

    @Test
    public void getCardsInHand()
    {

        assertEquals(cardsInHandTest, jay.getCardsInHand());

        Saxons saxons = new Saxons();

        BlackKnight blackKnight = new BlackKnight();

        jay.addCardToHand(saxons);

        jay.addCardToHand(blackKnight);

        cardsInHandTest.add(saxons);

        cardsInHandTest.add(blackKnight);

        assertEquals(cardsInHandTest, jay.getCardsInHand());

    }

    @Test
    public void getPlayerName()
    {
        assertEquals("Jay", jay.getPlayerName());

        assertEquals("Tom", tom.getPlayerName());
    }

    @Test
    public void getShields()
    {

        assertEquals(0, jay.getShields());

        jay.setShields(25);

        assertEquals(25, jay.getShields());

        assertEquals(0, tom.getShields());

        tom.setShields(8);

        assertEquals(8, tom.getShields());

    }

    @Test
    public void getPlayerRank()
    {

        assertEquals(Rank.SQUIRE,  jay.getPlayerRank());

        jay.setShields(7);

        jay.confirmRank();

        assertEquals(Rank.KNIGHT,  jay.getPlayerRank());

        assertEquals(Rank.SQUIRE,  tom.getPlayerRank());

        tom.setPlayerRank(Rank.CHAMPION_KNIGHT);

        tom.setShields(10);

        tom.confirmRank();

        assertEquals(Rank.KNIGHT_OF_THE_ROUND_TABLE,  tom.getPlayerRank());

    }

    @Test
    public void getNumCardsInHand()
    {
        assertEquals(0,jay.getNumCardsInHand());

        Saxons saxons = new Saxons();

        BlackKnight blackKnight = new BlackKnight();

        Boar boar = new Boar();

        jay.addCardToHand(saxons);

        jay.addCardToHand(blackKnight);

        assertEquals(2,jay.getNumCardsInHand());

        tom.addCardToHand(saxons);

        tom.addCardToHand(blackKnight);

        tom.addCardToHand(boar);

        assertEquals(3 , tom.getNumCardsInHand());

    }

    @Test
    public void addBonusPoint()
    {

        assertEquals(5, jay.calculateBattlePoints());

        jay.resetBattlePoints();

        jay.addBonusPoint(5);

        assertEquals(10, jay.calculateBattlePoints());

    }

    @Test
    public void resetBattlePoints()
    {

        jay.setPlayerRank(Rank.KNIGHT);

        assertEquals(10, jay.calculateBattlePoints());

        jay.resetBattlePoints();

        jay.setPlayerRank(Rank.SQUIRE);

        assertEquals(5, jay.calculateBattlePoints());

    }

    @Test
    public void setShields()
    {

        jay.setShields(25);

        assertEquals(0, tom.getShields());

        tom.setShields(jay.getShields());

        assertEquals(25, tom.getShields());
    }

    @Test
    public void setPlayerRank()
    {

        tom.setPlayerRank(Rank.KNIGHT);

        assertEquals(Rank.KNIGHT,  tom.getPlayerRank());

        tom.setPlayerRank(Rank.SQUIRE);

        assertEquals(Rank.SQUIRE,  tom.getPlayerRank());

        tom.setPlayerRank(Rank.KNIGHT_OF_THE_ROUND_TABLE);

        assertEquals(Rank.KNIGHT_OF_THE_ROUND_TABLE,  tom.getPlayerRank());

    }

    @Test
    public void addCardToHand()
    {

        assertEquals(0,jay.getNumCardsInHand());

        Saxons saxons = new Saxons();

        BlackKnight blackKnight = new BlackKnight();

        jay.addCardToHand(saxons);

        jay.addCardToHand(blackKnight);

        assertEquals(2,jay.getNumCardsInHand());

        tom.addCardToHand(saxons);

        tom.addCardToHand(blackKnight);

        assertEquals(tom.getCardsInHand(), jay.getCardsInHand());

    }

    @Test
    public void removeCardFromHand()
    {

        assertEquals(0,jay.getNumCardsInHand());

        Saxons saxons = new Saxons();

        BlackKnight blackKnight = new BlackKnight();

        jay.addCardToHand(saxons);

        jay.addCardToHand(blackKnight);

        assertEquals(2,jay.getNumCardsInHand());

        jay.removeCardFromHand(saxons);

        assertEquals(1,jay.getNumCardsInHand());

        tom.addCardToHand(blackKnight);

        assertEquals(tom.getCardsInHand(), jay.getCardsInHand());

    }

    @Test
    public void tooManyCards()
    {

        assertEquals(0,jay.getNumCardsInHand());

        Saxons saxons = new Saxons();

        BlackKnight blackKnight = new BlackKnight();

        jay.addCardToHand(saxons);

        jay.addCardToHand(blackKnight);

        assertEquals(2,jay.getNumCardsInHand());

        jay.removeCardFromHand(saxons);

        assertEquals(1,jay.getNumCardsInHand());

        tom.addCardToHand(blackKnight);

        assertEquals(tom.getCardsInHand(), jay.getCardsInHand());

    }

    @Test
    public void calculateBattlePoints()
    {

        assertEquals(5, jay.calculateBattlePoints());

        jay.resetBattlePoints();

        jay.setPlayerRank(Rank.KNIGHT);

        assertEquals(10, jay.calculateBattlePoints());

        jay.resetBattlePoints();

        jay.setPlayerRank(Rank.CHAMPION_KNIGHT);

        assertEquals(20, jay.calculateBattlePoints());

        jay.resetBattlePoints();

        jay.setPlayerRank(Rank.SQUIRE);

        assertEquals(5, jay.calculateBattlePoints());

    }

    @Test
    public void confirmRank()
    {
        jay.calculateBattlePoints();

        jay.confirmRank();

        assertEquals(Rank.SQUIRE,jay.getPlayerRank());

        jay.resetBattlePoints();

        jay.setPlayerRank(Rank.KNIGHT);

        jay.calculateBattlePoints();

        jay.confirmRank();

        assertEquals(Rank.KNIGHT,jay.getPlayerRank());

    }

}