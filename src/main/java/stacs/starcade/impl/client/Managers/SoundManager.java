package stacs.starcade.impl.client.Managers;

import javafx.scene.media.AudioClip;
import stacs.starcade.impl.client.Interfaces.ISoundManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * The Class SoundManager.
 */
public class SoundManager implements ISoundManager {

    /** The background note. */
    AudioClip backgroundNote;

    /**
     * Play background.
     */
    public void playBackground() {
        String track = this.getClass().getClassLoader().getResource("sounds/background.mp3").toString();
        backgroundNote = new AudioClip(track);
        backgroundNote.play();
    }

    /**
     * Stop background.
     */
    public void stopBackground() {
        backgroundNote.stop();
    }

    /**
     * Resume background.
     */
    public void resumeBackground() {
        backgroundNote.play();
    }

    /**
     * Play audio.
     *
     * @param track the track
     */
    public void playAudio(String track) {
        AudioClip note = new AudioClip(track);
        note.play();
    }

    /**
     * Play random note.
     */
    public void playRandomNote() {
        playAudio(getTrack(Sound.randomSound()));
    }


    /**
     * The Enum Sound.
     */
    public enum Sound {
        
        /** The set found 1. */
        setFound1,
/** The set found 2. */
setFound2,
/** The set found 3. */
setFound3,
/** The set found 4. */
setFound4,
/** The set found 5. */
setFound5,
/** The set found 6. */
setFound6;

        /** The Constant VALUES. */
        private static final List<Sound> VALUES =
                Collections.unmodifiableList(Arrays.asList(values()));
        
        /** The Constant SIZE. */
        private static final int SIZE = VALUES.size();
        
        /** The Constant RANDOM. */
        private static final Random RANDOM = new Random();

        /**
         * Random sound.
         *
         * @return the sound
         */
        public static Sound randomSound()  {
            return VALUES.get(RANDOM.nextInt(SIZE));
        }
    }

    /**
     * Gets the track.
     *
     * @param sound the sound
     * @return the track
     */
    public String getTrack(Sound sound) {
        String resource = "";
        switch (sound) {
            case setFound1:
                resource = "sounds/set-found-1.mp3";
                break;
            case setFound2:
                resource = "sounds/set-found-2.mp3";
                break;
            case setFound3:
                resource = "sounds/set-found-3.mp3";
                break;
            case setFound4:
                resource = "sounds/set-found-4.mp3";
                break;
            case setFound5:
                resource = "sounds/set-found-5.mp3";
                break;
            case setFound6:
                resource = "sounds/set-found-6.mp3";
                break;

        }
        return this.getClass().getClassLoader().getResource(resource).toString();
    }
}
