import pathfinder.Gridmap;
import pathfinder.Pathfinder;
import processing.core.*;

import java.util.ArrayList;


public class Main extends PApplet {


    int cols, rows, cellsize;
    Gridmap gridmap;
    Pathfinder pathfinder;
    PVector start1;
    PVector target1;
    //1 - set start, 2 - set end, 3 - set walkable
    int mode = 1;
    boolean searching = false;
    boolean fastsearch=false;
    public void setup() {
        surface.setTitle("Pathfinder Demo");
        cellsize = 20;
        cols = width / cellsize;
        rows = height / cellsize;
        start1 = new PVector(0, 0);
        target1 = new PVector(10, 10);
        gridmap = new Gridmap(this, cols, rows, cellsize);
        pathfinder = new Pathfinder(this, start1, target1, gridmap);
        for (int i = 0; i < 16; i++) {
            gridmap.setWalkable(3, i + 3, false);
        }
        for (int i = 0; i < 10; i++) {
            gridmap.setWalkable(i + 3, 3, false);
        }

    }

    public void draw() {
        gridmap.draw();
        pathfinder.draw();
        if (searching) {
            if (fastsearch){
                pathfinder.search();
            }
            else {
                pathfinder.searchPath();
            }

        }

    }

    public void mouseDragged() {
        // Get the mouse coordinates
        int x = mouseX;
        int y = mouseY;

        // Determine which cell was selected
        int selectedCellX = floor(x / cellsize);
        int selectedCellY = floor(y / cellsize);

        // Check if the selected cell is within the gridmap bounds
        if (selectedCellX >= 0 && selectedCellX < cols && selectedCellY >= 0 && selectedCellY < rows) {


            if (mode == 3) {
                if (mouseButton == LEFT) {
                    gridmap.setWalkable(selectedCellX, selectedCellY, false);
                } else {
                    gridmap.setWalkable(selectedCellX, selectedCellY, true);
                }
            }
        }
    }

    public void mousePressed() {

        // Get the mouse coordinates
        int x = mouseX;
        int y = mouseY;

        // Determine which cell was selected
        int selectedCellX = floor(x / cellsize);
        int selectedCellY = floor(y / cellsize);

        // Check if the selected cell is within the gridmap bounds
        if (selectedCellX >= 0 && selectedCellX < cols && selectedCellY >= 0 && selectedCellY < rows) {

            switch (mode) {
                case 1:

                    start1 = new PVector(selectedCellX, selectedCellY);
                    pathfinder.setStart(start1);
                    break;
                case 2:
                    target1 = new PVector(selectedCellX, selectedCellY);
                    pathfinder.setEnd(target1);

                    break;

            }
            if (mode == 3) {
                if (mouseButton == LEFT) {
                    gridmap.setWalkable(selectedCellX, selectedCellY, false);
                } else {
                    gridmap.setWalkable(selectedCellX, selectedCellY, true);
                }
            }
        }

    }

    public void keyPressed() {
        if (key == ENTER) {

            searching = true;
            pathfinder.start();

        } else if (key == '1') {

            mode = 1;
        } else if (key == '2') {
            mode = 2;

        } else if (key == '3') {
            mode = 3;
        } else if (key == 'r') {
            searching = false;
            reset();
        }
        else if (key == 'f') {
          fastsearch= !fastsearch;
        }

    }

    void reset() {
        gridmap.clearVisit();
        pathfinder = new Pathfinder(this, start1, target1, gridmap);

    }

    public void settings() {
        size(500, 400);
    }

    static public void main(String[] passedArgs) {

        PApplet.main("Main");

    }
}
