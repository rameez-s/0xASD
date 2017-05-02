package engine.sound;

import java.util.ArrayList;

/**
 * Created by 18iwahlqvist on 4/30/2017.
 */
public class AudioManager {
    //Test

    private ArrayList<Sound> sounds = new ArrayList<>();

    public AudioManager(){

    }

    public void add(Sound s){
        sounds.add(s);
    }

    public void play(String soundName){
        for(Sound s : sounds){
            if(s.name.equalsIgnoreCase(soundName)){

            }
        }
    }
}
