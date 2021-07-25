package stacs.starcade.impl.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This implements an in-memory league table. It utilizes the 'LeagueTableEntry' helper class.
 */
public class LeagueTable {

    private static List<LeagueTableEntry> entries = new ArrayList<>();

    public static void addEntry(LeagueTableEntry entry) throws IllegalArgumentException {
        if (entry == null) throw new IllegalArgumentException();
        entries.add(entry);
        Collections.sort(entries);
    }

    public static List<LeagueTableEntry> getEntries() {
        return entries;
    }

    public static void clearEntries() {
        entries.clear();
    }

}
