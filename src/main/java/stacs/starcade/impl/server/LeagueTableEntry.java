package stacs.starcade.impl.server;

/**
 * Helper class for the league table to store name-duration pairs.
 */
public class LeagueTableEntry implements Comparable<LeagueTableEntry> {

    private String name;
    private int duration;

    public LeagueTableEntry(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    /**
     * Implementation of the comparable interface allows the league table
     * to be sorted.
     */
    @Override
    public int compareTo(LeagueTableEntry otherEntry) {
        if (otherEntry.getDuration() > this.duration) return -1;
        else if (otherEntry.getDuration() < this.duration) return 1;
        else return 0;
    }

    public int getDuration() {
        return this.duration;
    }

    public String getName() {
        return this.name;
    }

}

