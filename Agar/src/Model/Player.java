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
    
    private boolean mustDie;
    
    public Player(String id,int xMax, int yMax){
        this.id = id;
        Cell cell = new Cell(xMax, yMax);
        cell.setVirus(false);
        this.cells = new ArrayList();
        this.cells.add(cell);
        this.vectorX = this.vectorY = this.vel = 0;
        this.mustDie = false;
    }
    
    public String getID(){
        return this.id;
    }
    
    public boolean getMustDie(){
        return this.mustDie;
    }
    public void updateExistence(){
        this.mustDie = this.cells.isEmpty();
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
    
    public void recalculatePositionCells(){
        if(cells.size()>0){
            for(int i = 1; i < cells.size(); i++){
                Cell cellPrincipal = cells.get(i-1);
                Cell cell = cells.get(i);
                cell.setCenterX(cellPrincipal.getCenterX() + cell.getRadio() + cellPrincipal.getRadio());
                cell.setCenterY(cellPrincipal.getCenterY());
            }
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
        for(int i = 0; i < cells.size(); i++){
            Cell cellSelf = cells.get(i);
            for(int j = 0; j < other.cells.size(); j++){
                Cell cellOther = other.cells.get(j);
                int collision = cellSelf.checkCollision(cellOther);
                if (collision == 1){
                    cellSelf.incrementMass(cellOther.getMass());
                    this.recalculatePositionCells();
                    other.cells.remove(cellOther);
                    other.updateExistence();
                } else if (collision == -1){
                    cellOther.incrementMass(cellSelf.getMass());
                    other.recalculatePositionCells();
                    this.cells.remove(cellSelf);
                    this.updateExistence();
                }
            }
        }
        return true;
    }
    
    public boolean checkCollision(Cell cell){
        for(int i = 0; i < cells.size(); i++){
            Cell cellSelf = cells.get(i);
            int collision = cellSelf.checkCollision(cell);
            if (collision != 0 && cell.getVirus()){
                cellSelf.incrementMass(cell.getMass());
                this.recalculatePositionCells();
                return true;
            }
        }
        return false;
    }
    
    public void render(Graphics g,double scale){
        for(int i = 0; i < cells.size(); i++){
            cells.get(i).render(g, scale);
        }
    }
}
