package stacs.starcade.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import stacs.starcade.ISetModel;
import stacs.starcade.impl.server.Builder;
import stacs.starcade.impl.server.LeagueTable;
import stacs.starcade.impl.server.LeagueTableEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for a REST API for the Set game that defines REST endpoints.
 */
@RestController
public class SetAPI {

   private int lastGameId = 0;
   private Map<Integer, ISetModel> games = new HashMap<>();

    /**
     * Will return API description in future.
     */
   @GetMapping("/api")
   public String apiDescription() {
       return "Return a description of the API in the future";
   }

    /**
     * Builds the {@link ISetModel}.
     * @return The game ID.
     */
   @PostMapping("/game")
   public int createGame(@RequestParam(name = "playerName") String playerName) {
       ISetModel model = Builder.buildModel(playerName);

       games.put(++lastGameId, model);

       return lastGameId;
   }

    /**
     * Alteration of the createGame method above, instead mapping to a different endpoint, "/testgame", and calling
     * into the {@link Builder}'s buildTestModel. Builds a consistent mock {@link ISetModel} into which a set of
     * predefined cards is injected for testing purposes. Subsequently, all regular API methods can be called for game
     * simulation.
     * @param playerName The player's name, provided by the client.
     * @return The game ID.
     */
    @PostMapping("/testgame")
    public int createTestGame(@RequestParam(name = "playerName") String playerName) {
        ISetModel model = Builder.buildTestModel(playerName);

        games.put(++lastGameId, model);

        return lastGameId;
    }

    /**
     * Returns the model.
     * @param id The game ID.
     */
   @GetMapping("game/{id}")
   public ISetModel getGame(@PathVariable int id) {
       ISetModel model = games.get(id);
       if (model != null) {
           return model;
       }

       throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
   }

    /**
     * Toggles (selects, unselects) the card at cardIdx and returns the updated model. Responds 400 if card is blocked.
     * When three selected cards form a valid set, the are automatically checked in as such by the model.
     * @param id The game id.
     */
   @PostMapping("/game/{id}/card/{cardIdx}")
   public ISetModel toggleCard(@PathVariable int id, @PathVariable int cardIdx) {
       ISetModel model = this.games.get(id);
       if(model != null) {
           try {
               model.toggleCard(cardIdx);
           } catch(IllegalStateException e) {
               throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
           }
           return model;
       }
       throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found.");
   }

    /**
     * Unselects a valid set that has been checked in at setIdx and return the updated model.
     * @param id The game ID.
     * @param setIdx The set's index.
     */
   @PostMapping("/game/{id}/set/{setIdx}")
   public ISetModel unselectSet(@PathVariable int id, @PathVariable int setIdx) {
       ISetModel model = this.games.get(id);
       if(model != null) {
           try {
               model.unselectSet(setIdx);
           } catch(IllegalStateException e) {
               throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
           }
           return model;
       }
       throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found.");
   }

    /**
     * Returns the league table in the form of a list of {@link LeagueTableEntry}s.
     */
   @GetMapping("/leaguetable")
   public List<LeagueTableEntry> getLeagueTable() {
       return LeagueTable.getEntries();
   }
}
