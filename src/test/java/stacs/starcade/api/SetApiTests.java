package stacs.starcade.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import stacs.starcade.impl.server.LeagueTable;

public class SetApiTests {

    WebTestClient client;

    @BeforeEach
    public void setup() {
        client = WebTestClient.bindToController(new SetAPI()).build();
        LeagueTable.clearEntries();
    }

    @Test
    public void createGame() {
        client.post().uri("/testgame?playerName=Gertrude")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json("1");
    }

    @Test
    public void mustReturnBadRequestIfPlayerNameNotProvided() {
        client.post().uri("/game")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody();
    }

    @Test
    public void mustGetGameRespresentation() {
        client.post().uri("/testgame?playerName=Gertrude")
                .accept(MediaType.APPLICATION_JSON)
                .exchange();
        client.get().uri("/game/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody();
    }

    @Test
    public void mustReturnNotFoundForNonExistingGame() {
        client.get().uri("/game/2")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void initialGameRepresentationMustContainExpectedValues() {
        client.post().uri("/testgame?playerName=Gertrude")
                .accept(MediaType.APPLICATION_JSON)
                .exchange();
        client.get().uri("/game/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.playerName").isEqualTo("Gertrude")
                .jsonPath("$.cards.length()").isEqualTo(12)
                .jsonPath("$.sets").isEmpty()
                .jsonPath("$.finished").isEqualTo(false)
                .jsonPath("$.secondsElapsed").isEqualTo(0);
    }

    @Test
    public void togglingCardMustSelectCard() {
        client.post().uri("/testgame?playerName=Gertrude")
                .accept(MediaType.APPLICATION_JSON)
                .exchange();
        client.post().uri("/game/1/card/0")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.cards[0].status").isEqualTo("SELECTED");
    }

    @Test
    public void togglingCardAgainMustUnselectCard() {
        client.post().uri("/testgame?playerName=Gertrude")
                .accept(MediaType.APPLICATION_JSON)
                .exchange();
        client.post().uri("/game/1/card/0")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.cards[0].status").isEqualTo("SELECTED");
        client.post().uri("/game/1/card/0")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.cards[0].status").isEqualTo("UNSELECTED");
    }

    @Test
    public void togglingBlockedCardMustReturnBadRequest() {
        client.post().uri("/testgame?playerName=Gertrude")
                .accept(MediaType.APPLICATION_JSON)
                .exchange();
        client.post().uri("/game/1/card/0")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
        client.post().uri("/game/1/card/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
        client.post().uri("/game/1/card/2")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
        client.post().uri("/game/1/card/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void unselectingSetMustUnselectSet() {
        client.post().uri("/testgame?playerName=Gertrude")
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        // Assert sets is empty to begin with
        client.get().uri("/game/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.sets").isEmpty();

        // Select a valid set
        client.post().uri("/game/1/card/0")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
        client.post().uri("/game/1/card/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
        client.post().uri("/game/1/card/2")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();

        // Assert sets is of length 1
        client.get().uri("/game/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.sets.length()").isEqualTo(1);

        // Unselect set 0 (set 1 on UI)
        client.post().uri("/game/1/set/0")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();

        // Assert sets is now empty
        client.get().uri("/game/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.sets").isEmpty();
    }

    @Test
    public void unselectingNonExistentSetMustReturnBadRequest() {
        client.post().uri("/testgame?playerName=Gertrude")
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        // Assert sets is empty to begin with
        client.get().uri("/game/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.sets").isEmpty();

        // Try unselecting the set
        client.post().uri("/game/1/set/0")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void entireGame() {
        client.post().uri("/testgame?playerName=Gertrude")
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        // Select 12 cards to make sets to complete and entire game
        client.post().uri("/game/1/card/0")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
        client.post().uri("/game/1/card/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
        client.post().uri("/game/1/card/2")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
        client.post().uri("/game/1/card/3")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
        client.post().uri("/game/1/card/4")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
        client.post().uri("/game/1/card/5")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
        client.post().uri("/game/1/card/6")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
        client.post().uri("/game/1/card/7")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
        client.post().uri("/game/1/card/8")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
        client.post().uri("/game/1/card/9")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
        client.post().uri("/game/1/card/10")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
        client.post().uri("/game/1/card/11")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();

        // Get game and assert that finished is equal to true
        client.get().uri("/game/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.finished").isEqualTo(true);
    }

    @Test
    public void mustGetLeagueTable() {
        client.get().uri("/leaguetable")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody();
    }

    /**
     * Buggy; will pass when run alone but not when all tests are run.
     */
    @Test
    public void playerMustBePlacedOnLeagueTable() {
        client.post().uri("/testgame?playerName=Gertrude")
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        // Select 12 cards to make sets to complete and entire game
        client.post().uri("/game/1/card/0")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
        client.post().uri("/game/1/card/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
        client.post().uri("/game/1/card/2")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
        client.post().uri("/game/1/card/3")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
        client.post().uri("/game/1/card/4")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
        client.post().uri("/game/1/card/5")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
        client.post().uri("/game/1/card/6")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
        client.post().uri("/game/1/card/7")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
        client.post().uri("/game/1/card/8")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
        client.post().uri("/game/1/card/9")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
        client.post().uri("/game/1/card/10")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
        client.post().uri("/game/1/card/11")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();

        // Get leader board and assert that the player was placed
        client.get().uri("/leaguetable")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$..name").isEqualTo("Gertrude");
    }

    @Test
    public void mustGetApiDescription() {
        client.get().uri("/api")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody();
    }
}
