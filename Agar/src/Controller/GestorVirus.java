package Controller;

import Model.Cell;
import Model.Player;
import View.WindowGame;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;

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
            Cell newVirus = new Cell(WindowGame.WINDOW_WIDTH, WindowGame.WINDOW_HEIGHT,true);
            newVirus.setID(counter);
            this.counter += 1;
            this.virus.add(newVirus);
        }
    }
    public int size(){
        return this.virus.size();
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
    
    public void render(Graphics g, double scale){
        for(int i = 0; i < virus.size(); i++){
            Cell v = virus.get(i);
            v.render(g, scale);
        }
    }

    public void checkCollisions(Player p) {
        for(int i = 0; i < virus.size(); i++){
            Cell v = virus.get(i);
            boolean collision = p.checkCollision(v);
            if (collision){
                virus.remove(i);
                break;
            }
        }
    }
    
}
