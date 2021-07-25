package stacs.starcade.impl.client.Interfaces;

import stacs.starcade.impl.client.Managers.SoundManager;

/**
 * The Interface ISoundManager.
 */
public interface ISoundManager {
    
    /**
     * Play background.
     */
    void playBackground();

    /**
     * Stop background.
     */
    void stopBackground();

    /**
     * Resume background.
     */
    void resumeBackground();

    /**
     * Play audio.
     *
     * @param track the track
     */
    void playAudio(String track);

    /**
     * Play random note.
     */
    void playRandomNote();

    /**
     * Gets the track.
     *
     * @param sound the sound
     * @return the track
     */
    String getTrack(SoundManager.Sound sound);
}
