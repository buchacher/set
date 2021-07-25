package stacs.starcade.impl.client.Controllers;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import stacs.starcade.impl.client.Interfaces.ISoundManager;

/**
 * The Class SettingsController.
 */
public class SettingsController {

    /** The pane master. */
    @FXML AnchorPane pane_master;
    
    /** The pane settings. */
    @FXML AnchorPane pane_settings;
    
    /** The btn sound. */
    @FXML private AnchorPane btn_sound;
    
    /** The btn music. */
    @FXML private AnchorPane btn_music;
    
    /** The btn settings. */
    @FXML private AnchorPane btn_settings;
    
    /** The btn exit. */
    @FXML private AnchorPane btn_exit;
    
    /** The btn settings ok. */
    @FXML private Button btn_settings_ok;
    
    /** The theme 1. */
    @FXML private AnchorPane theme1;
    
    /** The theme 2. */
    @FXML private AnchorPane theme2;
    
    /** The theme 3. */
    @FXML private AnchorPane theme3;
    
    /** The music enabled. */
    private boolean musicEnabled = true;
    
    /** The sounds enabled. */
    private boolean soundsEnabled = true;
    
    /** The sound manager. */
    ISoundManager soundManager;

    /**
     * Instantiates a new settings controller.
     *
     * @param pane_master the pane master
     * @param soundManager the sound manager
     * @param pane_settings the pane settings
     * @param btn_sound the btn sound
     * @param btn_music the btn music
     * @param btn_settings the btn settings
     * @param btn_exit the btn exit
     * @param btn_settings_ok the btn settings ok
     * @param theme1 the theme 1
     * @param theme2 the theme 2
     * @param theme3 the theme 3
     */
    public SettingsController(AnchorPane pane_master, ISoundManager soundManager, AnchorPane pane_settings, AnchorPane btn_sound, AnchorPane btn_music, AnchorPane btn_settings, AnchorPane btn_exit, Button btn_settings_ok, AnchorPane theme1, AnchorPane theme2, AnchorPane theme3) {
        this.pane_master = pane_master;
        this.soundManager = soundManager;
        this.pane_settings = pane_settings;
        this.btn_sound = btn_sound;
        this.btn_music = btn_music;
        this.btn_settings = btn_settings;
        this.btn_exit = btn_exit;
        this.btn_settings_ok = btn_settings_ok;
        this.theme1 = theme1;
        this.theme2 = theme2;
        this.theme3 = theme3;

        this.pane_settings.setVisible(false);

        String theme1css = "-fx-background-image: url('https://i.imgur.com/iLb0tm1.jpg');-fx-background-size: 200px auto;";
        String theme2css = "-fx-background-image: url('https://i.imgur.com/URyDfeJ.jpg');-fx-background-size: 200px auto;";
        String theme3css = "-fx-background-image: url('https://i.imgur.com/pS9QR9o.jpg');-fx-background-size: 200px auto;";

        String highlightCss = theme1css;
        highlightCss += "-fx-border-color: yellow; -fx-border-width: 1px; -fx-border-style: solid;";
        theme1.setStyle(highlightCss);

        btn_settings.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                openSettings();
            }
        });

        btn_sound.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setSoundsEnabled(!soundsEnabled);
            }
        });

        btn_music.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setMusicEnabled(!musicEnabled);
            }
        });

        btn_settings_ok.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                closeSettings();
            }
        });

        theme1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String css = theme1css;
                css += "-fx-border-color: yellow; -fx-border-width: 1px; -fx-border-style: solid;";
                theme1.setStyle(css);
                theme2.setStyle(theme2css);
                theme3.setStyle(theme3css);
                pane_master.setStyle("-fx-background-image: url('https://i.imgur.com/iLb0tm1.jpg'); -fx-background-size: auto 700px");
            }
        });

        theme2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String css = theme2css;
                css += "-fx-border-color: yellow; -fx-border-width: 1px; -fx-border-style: solid;";
                theme1.setStyle(theme1css);
                theme2.setStyle(css);
                theme3.setStyle(theme3css);
                pane_master.setStyle("-fx-background-image: url('https://i.imgur.com/URyDfeJ.jpg'); -fx-background-size: auto 700px");
            }
        });

        theme3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String css = theme3css;
                css += "-fx-border-color: yellow; -fx-border-width: 1px; -fx-border-style: solid;";
                theme1.setStyle(theme1css);
                theme2.setStyle(theme2css);
                theme3.setStyle(css);
                pane_master.setStyle("-fx-background-image: url('https://i.imgur.com/pS9QR9o.jpg'); -fx-background-size: auto 700px");
            }
        });

        btn_exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    /**
     * Open settings.
     */
    public void openSettings() {
        pane_settings.setVisible(true);
    }

    /**
     * Close settings.
     */
    public void closeSettings() {
        pane_settings.setVisible(false);
    }

    /**
     * Checks if is music enabled.
     *
     * @return true, if is music enabled
     */
    public boolean isMusicEnabled() {
        return musicEnabled;
    }

    /**
     * Sets the music enabled.
     *
     * @param musicEnabled the new music enabled
     */
    public void setMusicEnabled(boolean musicEnabled) {
        if(isMusicEnabled()) {
            String css = "-fx-background-image: url('https://i.imgur.com/gi8U4ve.png');-fx-background-size: 50px 50px;";
            btn_music.setStyle(css);
            soundManager.stopBackground();
        } else {
            String css = "-fx-background-image: url('https://i.imgur.com/3Key5eq.png');-fx-background-size: 50px 50px;";
            btn_music.setStyle(css);
            soundManager.playBackground();
        }
        this.musicEnabled = musicEnabled;
    }

    /**
     * Checks if is sounds enabled.
     *
     * @return true, if is sounds enabled
     */
    public boolean isSoundsEnabled() {
        return soundsEnabled;
    }

    /**
     * Sets the sounds enabled.
     *
     * @param soundsEnabled the new sounds enabled
     */
    public void setSoundsEnabled(boolean soundsEnabled)
    {
        if(isSoundsEnabled()) {
            String css = "-fx-background-image: url('https://i.imgur.com/zgyz02A.png');-fx-background-size: 50px 50px;";
            btn_sound.setStyle(css);
        } else {
            String css = "-fx-background-image: url('https://i.imgur.com/do3RrcZ.png');-fx-background-size: 50px 50px;";
            btn_sound.setStyle(css);
        }
        this.soundsEnabled = soundsEnabled;
    }
}