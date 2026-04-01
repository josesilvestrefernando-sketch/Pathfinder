package pathfinder;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Pathfinder {

    PVector start;
    PVector end;
    PApplet pApplet;
    Gridmap gridmap;
    ArrayList<PVector> edges = new ArrayList<>();
    boolean searching=true;
    private ArrayList<PVector> path = new ArrayList<>();

    public Pathfinder(PApplet pApplet1, PVector start1, PVector end1, Gridmap gridmap1) {
        start = new PVector(start1.x, start1.y);
        end = new PVector(end1.x, end1.y);
        pApplet = pApplet1;
        gridmap = gridmap1;

    }


    public void start(){
        searching=true;
        edges.add(start);
    }
    public void draw() {
        pApplet.fill(200, 200, 0);
        pApplet.ellipse(start.x * gridmap.cellsize + gridmap.cellsize / 2,
                start.y * gridmap.cellsize + gridmap.cellsize / 2, gridmap.cellsize,
                gridmap.cellsize);
        pApplet.fill(10, 20, 200);
        for (PVector p : edges) {
            pApplet.ellipse(p.x * gridmap.cellsize + gridmap.cellsize / 2,
                    p.y * gridmap.cellsize + gridmap.cellsize / 2,
                    gridmap.cellsize / 2, gridmap.cellsize / 2);
        }
        pApplet.fill(200, 20, 10);
        pApplet.ellipse(end.x * gridmap.cellsize + gridmap.cellsize / 2,
                end.y * gridmap.cellsize + gridmap.cellsize / 2, gridmap.cellsize,
                gridmap.cellsize);

        pApplet.fill(100, 20, 210);
        for (PVector p : path) {
            pApplet.ellipse(p.x * gridmap.cellsize + gridmap.cellsize / 2,
                    p.y * gridmap.cellsize + gridmap.cellsize / 2,
                    gridmap.cellsize / 2, gridmap.cellsize / 2);
        }
    }

    public void searchPath() {

        if (edges.size() > 0 && searching) {
            PVector firstedge = edges.get(0);
            Node currentnode = gridmap.grid[(int) firstedge.y][(int) firstedge.x];
            checkEgdes(currentnode);
            edges.remove(firstedge);

        }

    }
    public void search(){
        start();
        while (edges.size() > 0 && searching){
            searchPath();
        }
    }
    public void setTarget(PVector start1, PVector end1){
        edges.clear();
        path.clear();
        start = new PVector(start1.x, start1.y);
        end = new PVector(end1.x, end1.y);
        edges.add(start1);
        searching=true;
    }
    void addEdge(PVector pos1) {
        edges.add(pos1);
    }
    // Helper function to reconstruct the path
    private void reconstructPath(Node current1) {
        path.clear();
        for (int i = 0; i < current1.path.size(); i++) {
            path.add(current1.path.get(i));
        }
        searching=false;
    }
    public void checkEgdes(Node current1) {
        ArrayList<Node> newneighbor = getNeighbors(current1);
        for (int i = 0; i < newneighbor.size(); i++) {
            Node nn1 = newneighbor.get(i);

            if (outOfBounds(new PVector(nn1.x, nn1.y)) || nn1.visited || nn1.walkable == false) {
                continue;
            }

            nn1.visited = true;
            nn1.setParent(current1);
            edges.add(new PVector(nn1.x, nn1.y));
            if (nn1.x==end.x && nn1.y==end.y){
                searching=false;
                edges.clear();
                reconstructPath(nn1);
                return;
            }
        }
    }

    ArrayList<Node> getNeighbors(Node base1) {
        ArrayList<Node> neighbors = new ArrayList<Node>();

        // Define the possible directions (up, down, left, right)
        PVector[] directions = {
                new PVector(0, -1), // Up
                new PVector(0, 1),  // Down
                new PVector(-1, 0), // Left
                new PVector(1, 0)   // Right
        };

        // Iterate over the possible directions
        for (PVector direction : directions) {
            // Calculate the position of the neighbor
            PVector neighborPosition = new PVector(base1.x + direction.x, base1.y + direction.y);
            if (outOfBounds(neighborPosition) == false) {

                neighbors.add(gridmap.grid[(int) neighborPosition.y][(int) neighborPosition.x]);
            }

        }

        return neighbors;
    }

    public boolean outOfBounds(PVector pos1) {
        return (pos1.x < 0 || pos1.x >= gridmap.w) || (pos1.y < 0 || pos1.y >= gridmap.h);
    }
  public void setStart(PVector start1){
        start=new PVector(start1.x,start1.y);
        edges.clear();
    }
    public void setEnd(PVector end1){
        end=new PVector(end1.x,end1.y);
        edges.clear();
    }
}
