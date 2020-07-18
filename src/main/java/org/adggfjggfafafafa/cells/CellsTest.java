package org.adggfjggfafafafa.cells;

import processing.core.PApplet;

import java.util.HashMap;
import java.util.UUID;

public class CellsTest extends PApplet {

    HashMap<UUID, Node> cells = new HashMap<>();
    Camera camera = new Camera( 0 , 0 , 1);

    public void settings() {
        size(420,420);
    }

    public void setup() {

        Node cell = new Node(20, 20);
        cell.setUuid( UUID.randomUUID() );
        cells.put(cell.getUuid(), cell);
    }

    public void draw() {

        background(204);

        if (keyPressed){
            if (key == 'w') {
                camera.y--;
            } else if (key == 's') {
                camera.y++;
            } else if (key == 'a') {
                camera.x--;
            } else if (key == 'd') {
                camera.x++;
            } else if (key == 'q') {
                camera.zoom += 0.1;
            } else if (key == 'e') {
                camera.zoom -= 0.1;
            }
        }
        camera.zoom = Math.max( camera.zoom, 1 );

        translate(width/2f, height/2f);
        scale( (float) camera.zoom );
        translate( (float)-camera.x, (float)-camera.y );

        ellipseMode( CENTER );
        for (Node cell : cells.values()){
            ellipse( (float)cell.getX(), (float)cell.getY(), 5, 5 );
        }
    }

    public static void main(String... args){
        String[] processingArgs = {"CellsTest"};
        CellsTest applet = new CellsTest();
        PApplet.runSketch(processingArgs, applet);
    }
}
