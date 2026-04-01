package pathfinder;

import processing.core.PVector;

import java.util.ArrayList;

public class Node {
   int x,y;
    boolean walkable=true;
    Node parent=null;
    boolean visited=false;
    ArrayList<PVector> path = new ArrayList<>();

    public Node(int x1, int y1, Node parent1) {
        x=x1;
        y=y1;
        parent=parent1;
       if (parent!=null){
         for (int i=0;i<parent.path.size();i++){
             path.add(parent.path.get(i));
         }
         path.add(new PVector(parent.x,parent.y));
       }

    }
    public void clearVisit(){
        parent=null;
        visited=false;
       path = new ArrayList<>();
    }
    public void setParent(Node parent1){
        parent=parent1;
        if (parent!=null){
            for (int i=0;i<parent.path.size();i++){
                path.add(parent.path.get(i));
            }
            path.add(new PVector(parent.x,parent.y));
        }
    }
}
