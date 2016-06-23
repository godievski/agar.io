/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Cell;
import View.WindowGame;
import java.util.ArrayList;
import java.awt.Color;

/**
 *
 * @author Godievski
 */
public class GestorVirus {
    private static final int MAX_VIRUS = 100;
    private static final Color COLOR_VIRUS = Color.GREEN;
    private ArrayList<Cell> virus;
    private int counter;
    
    public GestorVirus(){
        this.virus = new ArrayList();
        this.counter = 0;
    }
    
    public void createVirus(){
        if (this.virus.size() < MAX_VIRUS){
            Cell newVirus = new Cell(WindowGame.WINDOW_WIDTH, WindowGame.WINDOW_HEIGHT);
            newVirus.setVirus(true);
            newVirus.setColor(COLOR_VIRUS);
            newVirus.setID(counter);
            this.counter += 1;
        }
    }
    
    public void deleteVirus(int id){
        Cell v = findVirus(id);
        if(v != null){
            this.virus.remove(v);
        }
    }
    
    private Cell findVirus(int id){
        for(int i = 0; i < virus.size(); i++){
            Cell v = this.virus.get(i);
            if (v.getID() == id)
                return v;
        }
        return null;
    }
    
}
