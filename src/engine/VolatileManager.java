package engine;

import engine.sound.Sound;

/**
 * Created by 18iwahlqvist on 5/21/2017.
 */
public class VolatileManager {
    //For AudioManager
    public static volatile Sound soundToPlay;
    public static volatile boolean play = false;
    public static volatile String location;
    public static volatile boolean create;
}