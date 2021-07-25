package stacs.starcade.server;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import stacs.starcade.impl.server.*;

public class LeagueTableUnitTests {

    @Test
    public void addOneEntry() {
        LeagueTable.clearEntries();
        String playerName = "TestNameOne";
        int duration = 700;
        LeagueTableEntry entry = new LeagueTableEntry(playerName, duration);
        LeagueTable.addEntry(entry);

        assertEquals(1, LeagueTable.getEntries().size());
        assertEquals(playerName, LeagueTable.getEntries().get(0).getName());
        assertEquals(duration, LeagueTable.getEntries().get(0).getDuration());
    }

    @Test
    public void addSecondEntryLowerThanFirst() {
        LeagueTable.clearEntries();

        String playerNameOne = "TestNameOne";
        int durationOne = 700;
        LeagueTableEntry entryOne = new LeagueTableEntry(playerNameOne, durationOne);
        LeagueTable.addEntry(entryOne);

        String playerNameTwo = "TestNameTwo";
        int durationTwo = 699;
        LeagueTableEntry entryTwo = new LeagueTableEntry(playerNameTwo, durationTwo);
        LeagueTable.addEntry(entryTwo);

        assertEquals(playerNameTwo, LeagueTable.getEntries().get(0).getName());
        assertEquals(durationTwo, LeagueTable.getEntries().get(0).getDuration());

        assertEquals(playerNameOne, LeagueTable.getEntries().get(1).getName());
        assertEquals(durationOne, LeagueTable.getEntries().get(1).getDuration());
    }

    @Test
    public void addThirdEntryLongerThanFirstTwo() {
        LeagueTable.clearEntries();

        String playerNameOne = "TestNameOne";
        int durationOne = 700;
        LeagueTableEntry entryOne = new LeagueTableEntry(playerNameOne, durationOne);
        LeagueTable.addEntry(entryOne);

        String playerNameTwo = "TestNameTwo";
        int durationTwo = 699;
        LeagueTableEntry entryTwo = new LeagueTableEntry(playerNameTwo, durationTwo);
        LeagueTable.addEntry(entryTwo);

        String playerNameThree = "TestNameThree";
        int durationThree = 701;
        LeagueTableEntry entryThree = new LeagueTableEntry(playerNameThree, durationThree);
        LeagueTable.addEntry(entryThree);

        assertEquals(playerNameTwo, LeagueTable.getEntries().get(0).getName());
        assertEquals(durationTwo, LeagueTable.getEntries().get(0).getDuration());

        assertEquals(playerNameOne, LeagueTable.getEntries().get(1).getName());
        assertEquals(durationOne, LeagueTable.getEntries().get(1).getDuration());

        assertEquals(playerNameThree, LeagueTable.getEntries().get(2).getName());
        assertEquals(durationThree, LeagueTable.getEntries().get(2).getDuration());
    }

}
