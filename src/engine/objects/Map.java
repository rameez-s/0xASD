package engine.objects;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Isak Wahlqvist
 */
//Test
//and alex
//mostly Isak
public class Map {
    public byte[][] mapData;
    public ArrayList<Sprite> collidablePixels = new ArrayList<Sprite>();

    public Map(String fileName){
        getMapData(fileName);
    }

    private void getMapData(String fileName){
        try {
            BufferedImage bi = ImageIO.read(new File("maps/" + fileName));
            int height = bi.getHeight();
            int width = bi.getWidth();
            System.out.println("Width: " + width + "\n" + "Height: " + height);
            mapData = new byte[width][height];
            for(int x = 0; x < width; x++){
                for(int y = 0; y < height; y++){
                    int pixel = bi.getRGB(x, y);
                    switch (pixel){
                        //Floors
                        case -1:
                            mapData[x][y] = 0;
                            break;

                        //Walls
                        case -16777216:
                            mapData[x][y] = 1;
                            break;
                        case -16711936:
                            mapData[x][y] = 2;
                            break;
                        case -65536:
                            mapData[x][y] = 3;
                            break;
                        case 16776961:
                            mapData[x][y] = 4;
                            break;
                        default:
                            mapData[x][y] = 1;
                            break;
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        int red = 255;
        int green = 0;
        int blue = 0;
        int rgb = red;
        rgb = (rgb << 8) + green;
        rgb = (rgb << 8) + blue;
        System.out.println(rgb);
        rgb = -1;
        int redb = (rgb >> 16) & 0xFF;
        int greenb = (rgb >> 8) & 0xFF;
        int blueb = rgb & 0xFF;
        System.out.println(redb + ", " + greenb + ", " + blueb);
    }
}
