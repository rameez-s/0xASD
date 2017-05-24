package engine.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;

/**
 * Created by 18iwahlqvist on 5/24/2017.
 */
public class Save implements Serializable{
    public boolean completedEnglish = false, completedGym = false, completedSocialStudies = false, completedMusic = false;
}
