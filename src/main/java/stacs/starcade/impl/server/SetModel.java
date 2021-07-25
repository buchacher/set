package stacs.starcade.impl.server;

import java.util.ArrayList;
import java.util.List;

import stacs.starcade.*;

/**
 * Defines the model for a game of set.
 */
public class SetModel implements ISetModel {

    public static final int NUM_CARDS = 12;
    public static final int NUM_CARDS_IN_SET = 3;

    private String playerName;
    private List<ICard> cards;
    private List<List<ICard>> sets;
    private int maxSets;
    private boolean isFinished;
    private ITimer timer;

    public SetModel(String playerName) {
        this.playerName = playerName;
        this.sets = new ArrayList<>();
        this.isFinished = false;
        timer = new Timer();
        this.cards = CardGenerator.generateCards();
    }

    @Override
    public ISetModel toggleCard(int cardIndex) {

        if (cardIndex > NUM_CARDS - 1 || cardIndex < 0) throw new IllegalStateException();
        ICard card = cards.get(cardIndex);

        if (card.getStatus() == ICard.Status.UNSELECTED) triggerCardSelectionActions(card);
        else if (card.getStatus() == ICard.Status.SELECTED) unselectCard(card);
        else if (card.getStatus() == ICard.Status.BLOCKED) throw new IllegalStateException();

        return this;
        
    }

    /**
     * Triggers numerous actions conditionally to select a card,
     * add it to the working set, validate the set, and take further actions
     * @param card
     */
    private void triggerCardSelectionActions(ICard card) {

        setCardStatusToSelected(card);
        addCardToWorkingSet(card);

        // if working set now has three cards, trigger validation
        List<ICard> lastSet = sets.get(sets.size() - 1);

        if (lastSet.size() >= NUM_CARDS_IN_SET) {
            triggerSetValidationActions(lastSet);
        }

    }

    private void setCardStatusToSelected(ICard card) {
        card.setStatus(ICard.Status.SELECTED);
    }

    /**
     * Adds the card to the working set.
     */
    private void addCardToWorkingSet(ICard card) {

        List<ICard> lastSet;

        if (sets.isEmpty() || sets.get(sets.size() - 1).size() >= NUM_CARDS_IN_SET) {
            List<ICard> newList = new ArrayList<>();
            sets.add(newList);
            lastSet = newList;
        }
        else lastSet = sets.get(sets.size() - 1);

        lastSet.add(card);

    }

    /**
     * This method triggers actions based on the validity of the set.
     */
    private void triggerSetValidationActions(List<ICard> cards) {
        if (SetValidator.isValidSet(cards)) recordValidSet(cards);
        else removeInvalidSet(cards);
    }

    /**
     * Sets the status of all cards within the set to "BLOCKED".
     * It also finishes the game if the set is valid.
     * @param cards
     */
    private void recordValidSet(List<ICard> cards) {
        for (ICard card : cards) card.setStatus(ICard.Status.BLOCKED);
        if (sets.size() >= maxSets) finishGame();
    }

    /**
     * Sets all cards within the candidate cards to be unselected and also removes
     * the candidate set from the list of sets.
     * @param cards
     */
    private void removeInvalidSet(List<ICard> cards) {
        for (ICard card : cards) card.setStatus(ICard.Status.UNSELECTED); 
        sets.remove(cards);
    }

    /**
     * Sets the game 'finished' field to true, and adds an entry to the league table
     */
    private void finishGame() {
        this.isFinished = true;
        
        LeagueTableEntry entry = new LeagueTableEntry(this.playerName, this.getSecondsElapsed());
        LeagueTable.addEntry(entry);
    }

    /**
     * Removes a card from the working set and sets the status to "unselected".
     * @param card
     */
    private void unselectCard(ICard card) {
        // remove the card from the last set (where it must be)
        List<ICard> lastSet = sets.get(sets.size() - 1);
        lastSet.remove(card);
        card.setStatus(ICard.Status.UNSELECTED);
    }

    /**
     * Removes the set or working set from the list of sets, and sets all 
     * member cards to "unselected" status.
     */
    @Override
    public ISetModel unselectSet(int setIndex) {
        // If a set is already complete, this method will release all cards within that set and change them all from 'blocked' to 'accepted'.
        if (setIndex > sets.size() - 1) throw new IllegalStateException();
        
        List<ICard> set = sets.get(setIndex);
        for (ICard card : set) card.setStatus(ICard.Status.UNSELECTED);
        sets.remove(set);

        return this;
    }

    @Override
    public List<List<ICard>> getSets() {
        return sets;
    }

    @Override
    public void setCards(List<ICard> cards) {
        this.cards = cards;
    }

    @Override
    public void setMaxSets(int maxSets) {
        this.maxSets = maxSets;
    }

    @Override
    public ICard getCard(int cardIndex) {
        return cards.get(cardIndex);
    }

    @Override
    public List<ICard> getCards() {
        return this.cards;
    }

    @Override
    public int getSecondsElapsed() {
        return timer.getSecondsElapsed();
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void setTimer(ITimer timer) {
        this.timer = timer;
    }

}
