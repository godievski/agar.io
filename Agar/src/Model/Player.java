/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Graphics;
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
    
    public String getID(){
        return this.id;
    }
    
    public double getCenterX(){
        if (cells.size() >= 1)
            return this.cells.get(0).getCenterX();
        else
            return -1;
    }
    public double getCenterY(){
        if (cells.size() >= 1)
            return this.cells.get(0).getCenterY();
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
        int last = this.cells.size() - 1;
        if (this.getCell(last).getMass() > 2*Cell.INIT_MASS){
            Cell oldCell = this.getCell(last);
            int newMass = oldCell.getMass() / 2;
            oldCell.setMass(newMass);
            Cell newCell = new Cell(0,0,oldCell.getColor(),newMass);
            int rad = newCell.getRadio();
            newCell.setCenterX(oldCell.getCenterX() + oldCell.getRadio() + rad);
            newCell.setCenterY(oldCell.getCenterY());
            this.cells.add(newCell);
        }
    }
    
    public void mover(double x, double y){
        this.calcularVelocidad();
        this.calcularVector(x, y);
        for(Cell cell: cells){
            cell.move(this.vectorX, this.vectorY);
        }
    }
    
    public int getMass(){
        int totalMass = 0;
        for(Cell cell : cells){
            totalMass += cell.getMass();
        }
        return totalMass;
    }
    
    public void calcularVelocidad(){
        int totalMass = this.getMass();
        this.vel = INIT_VEL - 1/Math.log10(totalMass);
    }
    
    private void calcularVector(double xFinal, double yFinal){
        double y = yFinal - this.getCenterY();
        double x = xFinal - this.getCenterX();
        double r = Math.sqrt(y*y + x*x);
        if(this.getCell(0).getRadio() > r){
            y = 0;
            x = 0;
        }
        this.vectorY = vel * y/r;
        this.vectorX = vel * x/r;
    }
    
    public int countCells(){
        return this.cells.size();
    }
    
    public boolean checkCollision(Player other){
        for(Cell cellSelf: this.cells){
            for(Cell cellOther: other.cells){
                int collision = cellSelf.checkCollision(cellOther);
                if (collision == 1){
                    cellSelf.setMass(cellSelf.getMass() + cellOther.getMass());
                    other.cells.remove(cellOther);
                } else if (collision == -1){
                    cellOther.setMass(cellSelf.getMass() + cellOther.getMass());
                    this.cells.remove(cellSelf);
                }
            }
        }
        
        return false;
    }
    
    public void render(Graphics g,double scale){
        for(Cell cell: this.cells){
            cell.render(g, scale);
        }
    }
}
