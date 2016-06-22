/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 *
 * @author Godievski
 */
public class Cell {
    public static final int INIT_MASS = 100;
    
    private double centerX, centerY;
    private int mass;
    private Color color;
    private static Random rand = new Random();
    private boolean virus;
    
    public Cell(int xMax, int yMax){
        this.centerX = rand.nextInt(xMax/2 - 4)+4;
        this.centerY = rand.nextInt(yMax/2 - 4)+4;
        int r = rand.nextInt(256);
        int g = rand.nextInt(256);
        int b = rand.nextInt(256);
        this.color = new Color(r,g,b);
        this.color = Color.BLUE;
        this.mass = INIT_MASS;
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
    
    public int getMass() {
        return mass;
    }
    public void setMass(int mass) {
        this.mass = mass;
    }
    
    public int getRadio(){
        return (int)Math.sqrt(this.mass / Math.PI );
    }
    public void render(Graphics g, double scale){
        int r = this.getRadio();
        g.setColor(this.color);
        g.fillOval((int) (this.centerX - r), (int) (this.centerY - r), 2*r, 2*r);
    }
    
    public int checkCollision(Cell other){
        double d = distance(this.centerX,this.centerY,other.centerX, other.centerY);
        if (d > this.getRadio() + other.getRadio()){
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
