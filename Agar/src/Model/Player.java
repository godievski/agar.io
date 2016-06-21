/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Godievski
 */
public class Player {
    private static final double INIT_VEL = 3.5;
    
    private String id;
    private ArrayList<Cell> cells;
    private double vel;
    private double vectorX, vectorY;
    
    public Player(String id,int xMax, int yMax){
        this.id = id;
        Cell cell = new Cell(xMax, yMax);
        cell.setVirus(false);
        this.cells = new ArrayList();
        this.cells.add(cell);
        this.vectorX = this.vectorY = this.vel = 0;
    }
    
    public double getCenterX(){
        if (cells.size() >= 1)
            return this.cells.get(1).getCenterX();
        else
            return -1;
    }
    public double getCenterY(){
        if (cells.size() >= 1)
            return this.cells.get(1).getCenterY();
        else
            return -1;
    }
    public Cell getCell(int index){
        if (cells.size() > 0 && index < cells.size())
            return this.cells.get(index);
        else 
            return null;
    }
    public void split(){
        if (this.getCell(0).getMass() > 2*Cell.INIT_MASS){
            Cell oldCell = this.getCell(0);
            int newMass = oldCell.getMass() / 2;
            oldCell.setMass(newMass);
            Cell newCell = new Cell(0,0,oldCell.getColor(),newMass);
            int rad = newCell.getRadio();
            newCell.setCenterX(oldCell.getCenterX() + oldCell.getRadio() + rad);
            this.cells.add(newCell);
        }
    }
    
    public void mover(double x, double y){
        this.calcularVelocidad();
        this.calcularVector(x, y);
        for(Cell cell: cells){
            double xCell = cell.getCenterX();
            double yCell = cell.getCenterY();
            cell.setCenterX(xCell + this.vectorX);
            cell.setCenterY(yCell + this.vectorY);
        }
    }
    
    public int getMass(){
        int totalMass = 0;
        for(Cell cell : cells){
            totalMass += cell.getMass();
        }
        return totalMass;
    }
    
    private void calcularVelocidad(){
        int totalMass = this.getMass();
        this.vel = INIT_VEL - 1/Math.log10(totalMass);
    }
    
    private void calcularVector(double xFinal, double yFinal){
        double y = yFinal - this.getCenterX();
        double x = xFinal - this.getCenterY();
        double r = Math.sqrt(y*y + x*x);
        this.vectorY = vel * y/r;
        this.vectorX = vel * x/r;
    }
}
