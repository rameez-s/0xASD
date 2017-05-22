package objects;

import engine.math.Vector3f;

/**
 * Created by 18iwahlqvist on 5/21/2017.
 */

public class Path {
    public PathPoint current;

    public Path(PathPoint start){
        current = start;
    }
    public Path(){
        PathPoint p0 = new PathPoint(new Vector3f(0, 1000, 0), null, 80);
        PathPoint p1 = new PathPoint(new Vector3f(0, 0, 0), null, 100);
        PathPoint p2 = new PathPoint(new Vector3f(400, 1000, 0), null, 75);
        PathPoint p3 = new PathPoint(new Vector3f(-400, 1000, 0), null, 75);
        PathPoint p4 = new PathPoint(new Vector3f(400, 0, 0), null, 75);
        PathPoint p5 = new PathPoint(new Vector3f(-400, 0, 0), null, 75);
        PathPoint p6 = new PathPoint(new Vector3f(800, 0, 0), null, 40);
        PathPoint p7 = new PathPoint(new Vector3f(-800, 0, 0), null, 40);
        PathPoint p9 = new PathPoint(new Vector3f(800, 1000, 0), null, 20);
        PathPoint p8 = new PathPoint(new Vector3f(-800, 1000, 0), null, 20);
        p0.connected = new PathPoint[]{p1, p2, p3};
        p1.connected = new PathPoint[]{p0, p4, p5};
        p2.connected = new PathPoint[]{p0, p4, p8};
        p3.connected = new PathPoint[]{p0, p5, p9};
        p4.connected = new PathPoint[]{p1, p2, p6};
        p5.connected = new PathPoint[]{p1, p3, p7};
        p7.connected = new PathPoint[]{p5, p9};
        p6.connected = new PathPoint[]{p4, p8};
        p9.connected = new PathPoint[]{p7, p3};
        p8.connected = new PathPoint[]{p6, p2};
        current = p0;

    }
    public void chooseNextPath(){
        if(current.connected.length != 0){
            int total = 0;
            for(PathPoint p : current.connected){
                total += p.weight;
            }
            int chosen = (int)(Math.random() * total);
            for(PathPoint p : current.connected){
                chosen -= p.weight;
                if(chosen < 0){
                    current = p;
                    break;
                }
            }
        }else{
            System.out.println("No path Connected");
        }
    }
}
