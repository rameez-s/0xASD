package objects;

import engine.math.Vector3f;

/**
 * Created by 18iwahlqvist on 5/21/2017.
 */
public class PathPoint {
    public Vector3f position;
    public PathPoint[] connected;
    public int weight = 50;//1-100

    public PathPoint(Vector3f position, PathPoint[] connectedPaths){
        this.position = position;
        connected = connectedPaths;
    }

    public PathPoint(Vector3f position, PathPoint[] connectedPaths, int weight){
        this.position = position;
        connected = connectedPaths;
        this.weight = weight;
    }
}
