package stacs.starcade.impl.client.Controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import stacs.starcade.ICard;
import stacs.starcade.ISetModel;
import stacs.starcade.impl.client.Interfaces.IAPIManager;
import stacs.starcade.impl.client.Interfaces.ICardManager;
import stacs.starcade.impl.client.Interfaces.ISoundManager;
import stacs.starcade.impl.client.Interfaces.ITimeManager;
import stacs.starcade.impl.client.Managers.APIManager;
import stacs.starcade.impl.client.Managers.CardManager;
import stacs.starcade.impl.client.Managers.SoundManager;
import stacs.starcade.impl.client.Managers.TimeManager;
import stacs.starcade.impl.server.*;

import animatefx.animation.*;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * The Class MainController.
 */
public class MainController implements Initializable {

    /** The pane master. */
    @FXML private AnchorPane pane_master;
    
    /** The pane cards. */
    @FXML private AnchorPane pane_cards;

    /** The pane finished. */
    @FXML private AnchorPane pane_finished;
    
    /** The lbl timer. */
    @FXML private Label lbl_timer;
    
    /** The lbl server status. */
    @FXML private Label lbl_server_status;
    
    /** The lbl sets found. */
    @FXML private Label lbl_sets_found;
    
    /** The lbl all sets found. */
    @FXML private Label lbl_all_sets_found;
    
    /** The btn start game. */
    @FXML private Button btn_start_game;

    /** The pane settings. */
    @FXML private AnchorPane pane_settings;
    
    /** The btn sound. */
    @FXML private AnchorPane btn_sound;
    
    /** The btn music. */
    @FXML private AnchorPane btn_music;
    
    /** The btn settings. */
    @FXML private AnchorPane btn_settings;
    
    /** The btn exit. */
    @FXML private AnchorPane btn_exit;
    
    /** The theme 1. */
    @FXML private AnchorPane theme1;
    
    /** The theme 2. */
    @FXML private AnchorPane theme2;
    
    /** The theme 3. */
    @FXML private AnchorPane theme3;
    
    /** The btn settings ok. */
    @FXML private Button btn_settings_ok;

    /** The set container 1. */
    @FXML private AnchorPane setContainer1;
    
    /** The set container 2. */
    @FXML private AnchorPane setContainer2;
    
    /** The set container 3. */
    @FXML private AnchorPane setContainer3;
    
    /** The set container 4. */
    @FXML private AnchorPane setContainer4;

    /** The txt name. */
    @FXML private TextField txt_name;
    
    /** The clear set 1. */
    @FXML private AnchorPane clear_set1;
    
    /** The clear set 2. */
    @FXML private AnchorPane clear_set2;
    
    /** The clear set 3. */
    @FXML private AnchorPane clear_set3;
    
    /** The clear set 4. */
    @FXML private AnchorPane clear_set4;

    @FXML private TableView<LeagueTableEntry> leaderTable;

    @FXML private TableColumn nameColumn;

    @FXML private TableColumn scoreColumn;


    /** The settings controller. */
    SettingsController settingsController;
    
    /** The time manager. */
    ITimeManager timeManager = new TimeManager();
    
    /** The sound manager. */
    ISoundManager soundManager = new SoundManager();
    
    /** The card manager. */
    ICardManager cardManager = new CardManager();
    
    /** The api manager. */
    IAPIManager apiManager = new APIManager();

    /** The game model. */
    ISetModel gameModel;
    
    /** The game id. */
    int gameId;

    /** The cards. */
    ICard[] cards = new Card[12];

    /**
     * Initialize.
     *
     * @param url the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settingsController = new SettingsController(pane_master, soundManager, pane_settings, btn_sound, btn_music, btn_settings, btn_exit, btn_settings_ok, theme1, theme2, theme3);
        soundManager.playBackground();

    }

    /**
     * Setup clear set.
     */
    private void setupClearSet() {
        String path = "game/" + this.gameId + "/set/";
        clear_set1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (setContainer1.getChildren().size() > 0) {
                    try {
                        updateCards(path + 0, "POST");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        clear_set2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (setContainer2.getChildren().size() > 0) {
                    try {
                        updateCards(path + 1, "POST");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        clear_set3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (setContainer3.getChildren().size() > 0) {
                    try {
                        updateCards(path + 2, "POST");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        clear_set4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (setContainer4.getChildren().size() > 0) {
                    try {
                        updateCards(path + 3, "POST");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Handle start game button clicked.
     *
     * @param event the event
     * @throws InterruptedException the interrupted exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    private void handleStartGameButtonClicked(MouseEvent event) throws InterruptedException, IOException {
        String name = txt_name.getText();
        this.createGame(name);

        if(settingsController.isMusicEnabled()) {
            soundManager.stopBackground();
        }
        if(settingsController.isSoundsEnabled()) {
            soundManager.playAudio(soundManager.getTrack(SoundManager.Sound.setFound1));
        }
        if(settingsController.isMusicEnabled()) {
            soundManager.resumeBackground();
        }
        timeManager.stopTimer();
        timeManager.startTimer(lbl_timer, 0);
        lbl_sets_found.setText("0");
        lbl_all_sets_found.setText("NO");
        setContainer1.getChildren().clear();
        setContainer2.getChildren().clear();
        setContainer3.getChildren().clear();
        setContainer4.getChildren().clear();
        pane_finished.setVisible(false);
        clear_set1.setVisible(true);
        clear_set2.setVisible(true);
        clear_set3.setVisible(true);
        clear_set4.setVisible(true);
    }

    /**
     * Removes the cards.
     */
    private void removeCards() {
        pane_cards.getChildren().clear();

        cards = new Card[12];
    }

    /**
     * Creates the game.
     *
     * @param playerName the player name
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void createGame(String playerName) throws IOException {
        try {
            int gameId = Integer.parseInt((apiManager.callAPI("game?playerName=" + playerName, "POST")).toString());
            this.gameId = gameId;
            setupClearSet();
            fetchGame(gameId);
            lbl_server_status.setVisible(false);
        } catch (Exception error) {
            lbl_server_status.setVisible(true);
        }

    }

    /**
     * Fetch game.
     *
     * @param id the id
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void fetchGame(int id) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String json = apiManager.callAPI("game/" + id, "GET").toString();
        JsonNode actualObj = mapper.readTree(json);

        String playerName = actualObj.get("playerName").asText();
//        int sets = Integer.parseInt(actualObj.get("sets").asText());
        Boolean finished = actualObj.get("finished").asBoolean();
        int secondsElaped = actualObj.get("secondsElapsed").asInt();
        JsonNode jsonCards = actualObj.get("cards");
        removeCards();
        pane_cards.setVisible(true);
        for (int i = 0; i < 12; i++) {
            JsonNode jsonCard = jsonCards.get(i);
            ICard.Color color = ICard.Color.valueOf(jsonCard.get("color").asText());
            ICard.Shape shape = ICard.Shape.valueOf(jsonCard.get("shape").asText());
            ICard.LineStyle lineStyle = ICard.LineStyle.valueOf(jsonCard.get("lineStyle").asText());
            ICard.Status status = ICard.Status.valueOf(jsonCard.get("status").asText());
            int number = jsonCard.get("number").asInt();
            ICard card = new Card(color, shape, lineStyle, number, status, i);
            createCard(card, i);
        }
    }

    /**
     * Creates the card.
     *
     * @param card the card
     * @param index the index
     */
    private void createCard(ICard card, int index) {

        AnchorPane pane = new AnchorPane();
        pane.setLayoutX(cardManager.getXCoordinate(index));
        pane.setLayoutY(cardManager.getYCoordinate(index));
        pane.setPrefHeight(cardManager.getCard_height());
        pane.setPrefWidth(cardManager.getCard_width());
        pane.setStyle("-fx-background-color: white");

        createShape(card.getShape(), card.getColor(), pane, card.getLineStyle(), card.getNumber());
        card.setPane(pane);
        new BounceInLeft(pane).play();
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    if (settingsController.isSoundsEnabled()) {
                        soundManager.playRandomNote();
                    }

                    selectCard(card);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        cards[index] = card;
        pane_cards.getChildren().add(cards[index].getPane());

    }

    /**
     * Update cards.
     *
     * @param path the path
     * @param method the method
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void updateCards(String path, String method) throws IOException {
        setContainer1.getChildren().clear();
        setContainer2.getChildren().clear();
        setContainer3.getChildren().clear();
        setContainer4.getChildren().clear();
        ObjectMapper mapper = new ObjectMapper();
        String json = apiManager.callAPI(path, method).toString();
        JsonNode actualObj = mapper.readTree(json);
        Boolean finished = actualObj.get("finished").asBoolean();

        if(finished) {
            showLeague();
        }

        JsonNode jsonCards = actualObj.get("cards");
        for (int i = 0; i < 12; i++) {
            JsonNode jsonCard = jsonCards.get(i);
            ICard.Status status = ICard.Status.valueOf(jsonCard.get("status").asText());
            cards[i].setStatus(status);

            String css = "";
            switch (cards[i].getStatus()) {
                case SELECTED:
                    css = "-fx-background-color: lightGray; -fx-border-color: black; -fx-border-style: solid; -fx-border-width: 2px";
                    cards[i].getPane().setStyle(css);
                    cards[i].getPane().setDisable(false);
                    new Wobble(cards[i].getPane()).play();
                    break;
                case UNSELECTED:
                    cards[i].getPane().setDisable(false);
                    css = "-fx-background-color: white";
                    cards[i].getPane().setStyle(css);
                    break;
                case BLOCKED:
                    css = "-fx-background-color: gray";
                    cards[i].getPane().setDisable(true);
                    cards[i].getPane().setStyle(css);
                    break;
            }
        }

        JsonNode jsonSets = actualObj.get("sets");
        int setsFound = 0;

        for (int i = 0; i < jsonSets.size(); i++) {
            JsonNode jsonSet = jsonSets.get(i);
            if (jsonSet.size() == 3) {
                setsFound += 1;
            }
            for (int j = 0; j < jsonSet.size(); j++) {
                JsonNode jsonCard = jsonSet.get(j);
                System.out.println(jsonCard);
                ICard.Color color = ICard.Color.valueOf(jsonCard.get("color").asText());
                ICard.Shape shape = ICard.Shape.valueOf(jsonCard.get("shape").asText());
                ICard.LineStyle lineStyle = ICard.LineStyle.valueOf(jsonCard.get("lineStyle").asText());
                ICard.Status status = ICard.Status.valueOf(jsonCard.get("status").asText());
                int number = jsonCard.get("number").asInt();
                ICard card = new Card(color, shape, lineStyle, number, status, i);
                System.out.println(i);
                placeSet(card, i, j);
            }

        }
        lbl_sets_found.setText(setsFound + "");
    }

    /**
     * Place set.
     *
     * @param card the card
     * @param setIndex the set index
     * @param cardIndex the card index
     */
    public void placeSet(ICard card, int setIndex, int cardIndex) {
        AnchorPane pane = new AnchorPane();

        pane.setLayoutX(cardManager.getXCoordinateForSet(cardIndex));
        pane.setLayoutY(cardManager.getYCoordinateForSet());
        pane.setPrefHeight(cardManager.getCardSetHeight());
        pane.setPrefWidth(cardManager.getCardSetWidth());
        pane.setStyle("-fx-background-color: white");
        card.setPane(pane);
        placeSetShape(card);
        switch (setIndex) {
            case 0:
                setContainer1.getChildren().add(pane);
                break;
            case 1:
                setContainer2.getChildren().add(pane);
                break;
            case 2:
                setContainer3.getChildren().add(pane);
                break;
            case 3:
                setContainer4.getChildren().add(pane);
                break;
        }
    }

    /**
     * Place set shape.
     *
     * @param card the card
     */
    public void placeSetShape(ICard card) {
        for (int i = 0; i < card.getNumber(); i++) {

            AnchorPane circle = new AnchorPane();
            String css = "-fx-background-color: white;";

            css += "-fx-border-color: "  + card.getColor().toString().toLowerCase() + ";";
            css += "-fx-border-width: 2px;";
            css += "-fx-border-style: "  + card.getLineStyle().toString().toLowerCase() + ";";

            if (card.getShape() == ICard.Shape.CIRCLE) {
                css += "-fx-border-radius: 25;";
                css += "-fx-background-radius: 25;";
            }

            if (card.getShape() == ICard.Shape.TRIANGLE) {

                circle.setRotate(45);
            }

            circle.setStyle(css);
            circle.setLayoutX(cardManager.getShapeSetXCoordinate(i));
            circle.setLayoutY(cardManager.getShapeSetYCoordinate(i, card.getNumber()));
            circle.setPrefHeight(cardManager.getShapeSetHeight());
            circle.setPrefWidth(cardManager.getShapeSetHeight());
            card.getPane().getChildren().add(circle);

        }
    }

    /**
     * Select card.
     *
     * @param card the card
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void selectCard(ICard card) throws IOException {
        updateCards("game/" + this.gameId + "/card/" + card.getIndex(), "POST");
    }

    /**
     * Creates the shape.
     *
     * @param shape the shape
     * @param color the color
     * @param pane the pane
     * @param linestyle the linestyle
     * @param number the number
     */
    private void createShape(ICard.Shape shape, ICard.Color color, AnchorPane pane, ICard.LineStyle linestyle, int number) {

        for (int i = 0; i < number; i++) {

            AnchorPane circle = new AnchorPane();
            String css = "-fx-background-color: white;";

            css += "-fx-border-color: "  + color.toString().toLowerCase() + ";";
            css += "-fx-border-width: 2px;";
            css += "-fx-border-style: "  + linestyle.toString().toLowerCase() + ";";

            if (shape == ICard.Shape.CIRCLE) {
                css += "-fx-border-radius: 25;";
                css += "-fx-background-radius: 25;";
            }

            if (shape == ICard.Shape.TRIANGLE) {

                circle.setRotate(45);
            }

            circle.setStyle(css);
            circle.setLayoutX(cardManager.getShapeXCoordinate(i));
            circle.setLayoutY(cardManager.getShapeYCoordinate(i, number));
            circle.setPrefHeight(cardManager.getShapeHeight());
            circle.setPrefWidth(cardManager.getShapeHeight());
            pane.getChildren().add(circle);

        }
    }

    /**
     * Shows the leaderboard.
     */
    public void showLeague() throws IOException {
        timeManager.stopTimer();
        lbl_all_sets_found.setText("YES");
        pane_finished.setVisible(true);
        pane_finished.toFront();
        clear_set1.setVisible(false);
        clear_set2.setVisible(false);
        clear_set3.setVisible(false);
        clear_set4.setVisible(false);

        ObjectMapper mapper = new ObjectMapper();

        String json = apiManager.callAPI("leaguetable", "GET").toString();
        JsonNode actualObj = mapper.readTree(json);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

        ObservableList<Map<String, Object>> items =
                FXCollections.<Map<String, Object>>observableArrayList();
        leaderTable.getItems().clear();
        for (int i = 0; i <actualObj.size() ; i++) {

            JsonNode entry = actualObj.get(i);
            String name = entry.get("name").asText();
            int duration = entry.get("duration").asInt();
            System.out.println(name);
            System.out.println(duration);
            System.out.println("-------");
            leaderTable.getItems().add(new LeagueTableEntry(name,duration));
        }
    }
}