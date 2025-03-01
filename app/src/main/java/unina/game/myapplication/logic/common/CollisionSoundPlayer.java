package unina.game.myapplication.logic.common;

import com.badlogic.androidgames.framework.Sound;

import unina.game.myapplication.core.BehaviourComponent;
import unina.game.myapplication.core.physics.RigidBody;

/**
 * Component that plays a sound when a collision occurs.
 */
public class CollisionSoundPlayer extends BehaviourComponent {

    private Sound sound;
    private float volume = 1;
    private float minVelocitySqrMagnitude = 20f;

    @Override
    public void onRemove() {
        super.onRemove();

        sound = null;
        volume = 1;
    }

    @Override
    public void onCollisionEnter(RigidBody other, float relativeVelocityX, float relativeVelocityY) {
        super.onCollisionEnter(other, relativeVelocityX, relativeVelocityY);
        float sqrMagnitude = (relativeVelocityX * relativeVelocityX) + (relativeVelocityY * relativeVelocityY);

        if (sound != null && sqrMagnitude > minVelocitySqrMagnitude) {
            sound.play(volume);
        }
    }

    @Override
    public int getComponentPoolSize() {
        return 2;
    }

    /**
     * Sets the sound to play when a collision occurs.
     *
     * @param sound collision sound
     */
    public void setSound(Sound sound) {
        this.sound = sound;
    }

    /**
     * Sets the volume of the sound.
     *
     * @param volume volume of the sound
     */
    public void setVolume(float volume) {
        this.volume = volume;
    }

    /**
     * Sets the minimum velocity threshold to play the sound.
     * Sound will not be played when the collision velocity is lower than this value.
     *
     * @param minVelocitySqrMagnitude minimum velocity squared
     */
    public void setMinVelocitySqrMagnitude(float minVelocitySqrMagnitude) {
        this.minVelocitySqrMagnitude = minVelocitySqrMagnitude;
    }

}
