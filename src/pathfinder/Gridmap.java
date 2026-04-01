package pathfinder;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Gridmap {
    PApplet pApplet;
    int w, h, cellsize;
    Node[][] grid;

    public Gridmap(PApplet pApplet1, int w1, int h1, int cellsize1) {
        w = w1;
        h = h1;
        cellsize = cellsize1;
        pApplet = pApplet1;
        grid = new Node[h][w];
        // draw walkable / blocked cells
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
              grid[y][x]=new Node(x,y,null);
            }
        }
    }

    public void clearVisit(){
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                grid[y][x].clearVisit();
            }
        }
    }
    public void draw() {
        pApplet.background(0);
        // draw walkable / blocked cells
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                if (grid[y][x].visited) {
                    pApplet.fill(100, 0, 0);
                } else if (grid[y][x].walkable==false) {
                    pApplet.fill(50);
                } else {
                    pApplet.fill(255);
                }

                pApplet.rect(x * cellsize, y * cellsize, cellsize, cellsize);
            }
        }
    }
    public void setWalkable(int x1, int y1,boolean walkable1){
        grid[y1][x1].walkable=walkable1;
    }
    public void toggleWalkable(int x1, int y1){
        grid[y1][x1].walkable= !grid[y1][x1].walkable;
    }


}
