package Model;

import View.WindowGame;
import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.Random;

/**
 *
 * @author Godievski
 */
public class Cell implements Serializable {
    public static final int INIT_MASS = 150;
    public static final int MAX_RANGE_COLOR = 256;
    private static final Color COLOR_VIRUS = Color.GREEN;
    
    private double centerX, centerY;
    private int mass;
    private Color color;
    private static Random rand = new Random();
    private boolean virus;
    private int id; //PARA CUANDO SEAN VIRUS
    private int timeCreation;
    private boolean principal;
    
    public Cell(){
        this.centerX = rand.nextInt(3*WindowGame.WINDOW_WIDTH/4)+ WindowGame.WINDOW_WIDTH/8;
        this.centerY = rand.nextInt(3*WindowGame.WINDOW_HEIGHT/4)+ WindowGame.WINDOW_HEIGHT/8;
        int r = rand.nextInt(MAX_RANGE_COLOR);
        int g = rand.nextInt(MAX_RANGE_COLOR);
        int b = rand.nextInt(MAX_RANGE_COLOR);
        this.color = new Color(r,g,b);
        this.mass = INIT_MASS;
        this.timeCreation = 0;
    }
    public Cell(int xMax, int yMax){
        this.centerX = rand.nextInt(3*xMax/4)+ xMax/8;
        this.centerY = rand.nextInt(3*yMax/4)+ yMax/8;
        int r = rand.nextInt(MAX_RANGE_COLOR);
        int g = rand.nextInt(MAX_RANGE_COLOR);
        int b = rand.nextInt(MAX_RANGE_COLOR);
        this.color = new Color(r,g,b);
        this.mass = INIT_MASS;
        this.timeCreation = 0;
    }
    
    public Cell(int xMax, int yMax,boolean virus){
        //ONLY FOR VIRUS
        this.centerX = rand.nextInt(xMax-4)+4;
        this.centerY = rand.nextInt(yMax-4)+4;
        int r = rand.nextInt(MAX_RANGE_COLOR);
        int g = rand.nextInt(MAX_RANGE_COLOR);
        int b = rand.nextInt(MAX_RANGE_COLOR);
        this.color = new Color(r,g,b);
        this.mass = INIT_MASS/2;
        this.virus = virus;
        this.timeCreation = 0;
    }
    public void setPrincipal(boolean value){
        this.principal = value;
    }
    public boolean getPrincipal(){
        return this.principal;
    }
    public int getTimeCreation(){
        return this.timeCreation;
    }
    public void setTimeCreation(int time){
        this.timeCreation = time;
    }
    public void incrementTimeCreation(int time){
        this.timeCreation += time;
    }
    public void setID(int id){
        this.id = id;
    }
    public int getID(){
        return this.id;
    }
    public void setColor(Color color){
        this.color = color;
    }
    public Color getColor(){
        return this.color;
    }
    public Cell(double x, double y, Color color, int mass){
        this.centerX = x;
        this.centerY = y;
        this.color = color;
        this.mass = mass;
    }

    public void setVirus(boolean virus){
        this.virus = virus;
    }
    public boolean getVirus(){
        return this.virus;
    }
    
    public double getCenterX() {
        return centerX;
    }
    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public double getCenterY() {
        return centerY;
    }
    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }
    public void move(double x, double y){
        this.centerX += x;
        this.centerY += y;
    }
    
    public int getMass() {
        return mass;
    }
    public void setMass(int mass) {
        this.mass = mass;
    }
    public void incrementMass(int value){
        this.mass += value;
    }
    
    public int getRadio(){
        return (int)Math.sqrt(this.mass / Math.PI );
    }
    public void render(Graphics g, double scale){
        int r = this.getRadio();
        g.setColor(this.color);
        g.fillOval((int) (this.centerX - r), (int) (this.centerY - r), 2*r, 2*r);
        g.setColor(Color.BLACK);
        g.drawOval((int) (this.centerX - r), (int) (this.centerY - r), 2*r, 2*r);    
    }
    
    public int checkCollision(Cell other){
        double d = distance(this.centerX,this.centerY,other.centerX, other.centerY);
        if (d < this.getRadio() + other.getRadio()){
            if (this.mass > other.mass){
                return 1;
            } else if (this.mass < other.mass){
                return -1;
            } else
                return 0;
        } else{
            return 0;
        }
    }
    
    private double distance(double xi, double yi, double xf, double yf){
        return Math.sqrt((yf-yi)*(yf-yi) + (xf-xi)*(xf-xi));
    }
}
