package stacs.starcade;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.List;

/**
 * Defines the interface for a model of the game of Set.
 */
public interface ISetModel {

   /**
    * If the card at this index is unselected, it will be "selected". That means that
    * it will be added to the incomplete set. At any time, there is at most one incomplete set.
    * if there is no incomplete set, then a new incomplete set is created, and this card
    * is added to it. If this card is already part of the working set, then it 
    * will be removed from the working set. If this card is part of a complete set (and is thus blocked),
    * this method should throw an exception because such cards can no longer be toggled individually.
    * If selecting this card completes the incomplete set, then the set is checked for validity.
    * If the set is valid, all cards within the completed set, including this one, become 'blocked'. 
    * If the set is invalid, all cards within the set are set to 'unselected'.
    * this method will also trigger the logic for the player winning the game if this card completes a set,
    * and the number of completed sets equals the max number of sets within the current 12-card hand.
    * @param cardIndex The index of the card within the SetModel's list of 12 cards (between 0 and 11)
    * @return The modified model after toggling the card
    */
   public ISetModel toggleCard(int cardIndex) throws IllegalStateException;

   /**
    * If a set is already complete, this method will release all cards within that set
    * and change them all from 'blocked' to 'accepted'. If the index is out of the range of the
    * length of the SetModel's list of sets, then this method should throw an out of bounds exception.
    * @param setIndex
    * @return
    */
   public ISetModel unselectSet(int setIndex) throws IllegalStateException;

   /**
    * This is a method for testing
    * @return this games list of cards making up sets
    */
   @JsonGetter("sets")
   public List<List<ICard>> getSets();

   /**
    * Sets this game's list of cards
    */
   public void setCards(List<ICard> cards);


   /**
    * Sets the max number of sets that can be found in this model
    */
   public void setMaxSets(int maxSets);

   /**
    * @return The card object at this index in the game's list of 12 cards
    */
   public ICard getCard(int cardIndex);

   /**
    * @return A list of 12 cards
    */
   public List<ICard> getCards();

   /**
    * @param timer The timer within this SetModel which keeps track of elapsed seconds
    */
   public void setTimer(ITimer timer);

   /**
    * @return The seconds elapsed since this game started
    */
   @JsonGetter("secondsElapsed")
   public int getSecondsElapsed();

   /**
    * @return The name of the player playing this game
    */
   @JsonGetter("playerName")
   public String getPlayerName();

   /**
    * @return Whether or not this game is finished
    */
   @JsonGetter("finished")
   public boolean isFinished();

}
