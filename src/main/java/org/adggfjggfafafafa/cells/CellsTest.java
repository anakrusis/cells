package org.adggfjggfafafafa.cells;

import processing.core.PApplet;
import processing.core.PFont;

import java.util.HashMap;
import java.util.UUID;

public class CellsTest extends PApplet {

    private PFont font;

    private HashMap<UUID, Node> nodes = new HashMap<>();
    private Camera camera = new Camera( 0 , 0 , 1);
    private int mapWidth = 400; private int mapHeight = 400;
    private int nodeCount = 100;

    public void settings() {
        size(1000,600);
        noSmooth();
    }

    public double distance( double x1, double y1, double x2, double y2 ) {
        return Math.hypot(x2 - x1, y2 - y1);
    }

    private void generateNodes(){
        for (int i = 0; i < nodeCount; i++){

            // Try 100 times to find a land spot, and if you can't find it after that, then just give up lol
            for (int j = 0; j < 100; j++){
                int randomX = (int) (noise( i, j ) * mapWidth);
                int randomY = (int) (noise( j, i ) * mapHeight);

                // if on land (map value is over 0.5)
                float val = noise( randomX / 50f, randomY / 50f );
                if (val > 0.5f){

                    // iterates through all other nodes and makes sure it isn't too close to any
                    double nearestDistance = 10000;
                    for (Node node : nodes.values()){
                        double nodeDist = distance( node.x, node.y, randomX, randomY );
                        if (nodeDist < nearestDistance ) {
                            nearestDistance = nodeDist;
                        }
                    }
                    if (nearestDistance > 10){
                        Node node = new Node(randomX, randomY);
                        node.setUuid( UUID.randomUUID() );
                        nodes.put(node.getUuid(), node);

                        break;
                    }
                }
            }
        }
    }

    private void calculateNeighbors(){
        for (Node node : nodes.values()){
            for (Node otherNode : nodes.values()){

                if (node != otherNode) {
                    double distance = distance(node.x, node.y, otherNode.x, otherNode.y);
                    if (distance < 20){
                        node.neighborUUIDs.add( otherNode.uuid );
                    }
                }
            }
        }
    }

    public void setup() {

        noiseSeed( 12345 );
        font = createFont("Arial",16,true);

        generateNodes();
        calculateNeighbors();
    }

    public void draw() {

        background(204);

        if (keyPressed){
            if (key == 'w') {
                camera.y-=2;
            } else if (key == 's') {
                camera.y+=2;
            } else if (key == 'a') {
                camera.x-=2;
            } else if (key == 'd') {
                camera.x+=2;
            } else if (key == 'q') {
                camera.zoom += 0.1;
            } else if (key == 'e') {
                camera.zoom -= 0.1;
            }
        }
        camera.zoom = Math.max( camera.zoom, 1 );

        pushMatrix();
        translate(width/2f, height/2f);
        scale( (float) camera.zoom );
        translate( (float)-camera.x, (float)-camera.y );

        float squareSize = (float) (8 / camera.zoom);
        for (float j = 0; j < mapHeight; j += squareSize) {
            for (float i = 0; i < mapWidth; i += squareSize) {

                float traX = (float) (( i - camera.x ) * camera.zoom ) + width/2f;
                float traY = (float) (( j - camera.y ) * camera.zoom ) + height/2f;

                if (0 <= traX && traX <= width && 0 <= traY && traY <= height) {
                    float val = noise( i / 50f, j / 50f );
                    if (val > 0.5){
                        stroke(0, 128, 128); fill(0, 128, 128);
                    }else{
                        stroke(0,0,128); fill(0, 0, 128);
                    }
                    rect( i, j, squareSize, squareSize );
                }
            }
        }

        ellipseMode( CENTER );
        fill(255); stroke(0);

        for (Node cell : nodes.values()){
            for ( UUID uuid : cell.neighborUUIDs ){
                Node neighbor = nodes.get(uuid);
                line( (float)cell.x, (float)cell.y, (float)neighbor.x, (float)neighbor.y );
            }
        }

        for (Node cell : nodes.values()){
            ellipse( (float)cell.getX(), (float)cell.getY(), 3, 3 );
        }

        popMatrix();
        textFont(font, 36);
        float untraX = (float) (((mouseX - width/2f) / camera.zoom) + camera.x);
        float untraY = (float) (((mouseY - height/2f) / camera.zoom) + camera.y);
        float val = noise( untraX / 50f, untraY / 50f ) * 255;
        fill( val );
        text(val,0,36);
        text(squareSize,0,72);
    }

    public static void main(String... args){
        String[] processingArgs = {"CellsTest"};
        CellsTest applet = new CellsTest();
        PApplet.runSketch(processingArgs, applet);
    }
}
