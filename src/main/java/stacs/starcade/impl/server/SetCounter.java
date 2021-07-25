package stacs.starcade.impl.server;

import java.util.ArrayList;
import java.util.List;

import stacs.starcade.ICard;

/**
 * Contains static methods for counting the number of sets contained in
 * a list of cards.
 */
public class SetCounter {

    /**
     * Returns the maximum number of non-conflicting sets that can be found
     * in the current game model 
     * @param cards All cards to be searched for sets.
     */
    public static int getMaxNumSets(List<ICard> cards) {
        // find all possible sets
        // find all possible sets that are not in conflict
        List<List<ICard>> sets = new ArrayList<>();

        for (int i = 0 ; i < cards.size() ; i++) {
            for (int j = i + 1 ; j < cards.size() ; j++) {
                for (int k = j + 1 ; k < cards.size() ; k++) {
                    // if they are a valid set, add to the list of sets
                    List<ICard> potentialSet = new ArrayList<>();
                    potentialSet.add(cards.get(i));
                    potentialSet.add(cards.get(j));
                    potentialSet.add(cards.get(k));
                    if (SetValidator.isValidSet(potentialSet))
                        sets.add(potentialSet);
                }
            }
        }

        printAllSetsForTesting(sets);
        
        return maxNumNonConflictingSets(sets);
    }

    /**
     * Searches a list of possible sets to determine the maximum number that can
     * be selected such that no sets share any cards.
     * @param sets The sets to be searched.
     * @return The maximum number of sets that can be selected that do not share any cards
     */
    private static int maxNumNonConflictingSets(List<List<ICard>> sets) {
        List<List<ICard>> selectedSets = new ArrayList<>();
        return recurseBranchSearchForMaxNumberNonConflictingSets(sets, selectedSets);
    }

    /**
     * Recursively searches through a list of sets to determine The maximum number of
     * sets that can be selected that do not share any cards.
     * @param allSets All sets to be considered
     * @param selectedSets A recursively updated list of sets that have already been selected
     * @return The maximum number of sets that can be selected that do not share any cards
     */
    private static int recurseBranchSearchForMaxNumberNonConflictingSets(List<List<ICard>> allSets, List<List<ICard>> selectedSets) {

        int maxSetsUsed = selectedSets.size();

        for (List<ICard> set : allSets) {
            if (shareCommonCard(selectedSets, set)) continue;
            List<List<ICard>> updatedUsedSets = new ArrayList<>(selectedSets);
            updatedUsedSets.add(set);
            // sets the maxSetsUsed to the max selectedSets field from each recursive branch created in the iterations
            // of this for-loop
            maxSetsUsed = Math.max(maxSetsUsed, recurseBranchSearchForMaxNumberNonConflictingSets(allSets, updatedUsedSets));
        }

        return maxSetsUsed;
    }

    /**
     * Determines whether a set shares a common card with any set in a list of sets.
     * @param sets The list of sets to be compared to
     * @param set The set to be compared with the list
     * @return True if any set within sets shares at least one card with set
     */
    private static boolean shareCommonCard(List<List<ICard>> sets, List<ICard> set) {
        for (List<ICard> usedSet : sets) {
            for (ICard usedSetCard : usedSet) {
                for (ICard newSetCard : set) {
                    if (usedSetCard == newSetCard) return true;
                }
            }
        }
        return false;
    }

    /**
     * Prints all possible sets within the current game HOWEVER, it does not account
     * for conflicting sets. Some of the sets printed will be conflicting.
     * @param sets
     */
    private static void printAllSetsForTesting(List<List<ICard>> sets) {
        int setNumber = 0;
        for (List<ICard> set : sets) {
            System.out.println("\nSet: " + ++setNumber);
            for (ICard card : set) {
                System.out.println("Color: " + card.getColor());
                System.out.println("Shape: " + card.getShape());
                System.out.println("LineStyle: " + card.getLineStyle());
                System.out.println("Number: " + card.getNumber());
                System.out.println();
            }
        }
    }

}
