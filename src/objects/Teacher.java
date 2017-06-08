package objects;
import java.util.ArrayList;
/**
 * Created by 17aelbashir on 23-Feb-17.
 */
public class Teacher {
    public String name;

    public String teachingClass;
    public int number;
    ArrayList<String> dialogue = new ArrayList<String>();
    public Teacher (String n, int num, String tC)
    {
        name = n;
        number = num;
        teachingClass = tC;
        genDialogue();
    }
    public void genDialogue()
    {
        switch (number)
        {
            case 1:
                dialogue.add("I'm the Math teacher.");
                break;
            case 2:
                dialogue.add("I'm the Chemistry teacher.");
                break;
            case 3:
                dialogue.add("I'm the English teacher.");
                break;
            case 4:
                dialogue.add("I'm the History teacher.");
                break;
            case 5:
                dialogue.add("I'm the Music teacher.");
                break;
            case 6:
                dialogue.add("I'm the Physics teacher.");
                break;
            case 7:
                dialogue.add("I'm the PE teacher.");
                break;
            case 8:
                dialogue.add("I'm the Computer Science teacher.");
                break;
        }

    }
    public void returnDialogue(int dialogueNum)
    {

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}