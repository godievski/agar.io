package Controller;

import Model.Player;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Godievski
 */
public class GestorPlayer {
    //ONLY TO CHECK COLLISIONS
    private GestorVirus gv;
    
    private ArrayList<Player> players;
    private static final Random ran = new Random();
    
    public GestorPlayer(GestorVirus gv){
        this.gv = gv;
        this.players = new ArrayList();
    }
    
    public boolean checkID(String id){
        //IF IT'S AVAILABLE
        return getPlayer(id) == null;
    }
    
    public Player getPlayer(String id){
        for(Player p: players){
            if (p.getID() == id)
                return p;
        }
        return null;
    }

    public boolean addNewPlayer(String id,int xMax, int yMax){
        if (checkID(id)){
            this.players.add(new Player(id, xMax, yMax));
            return true;
        } else{
            return false;
        }
    }
    
    public int size(){
        return this.players.size();
    }
    
    public boolean isAlive(String id){
        Player p = this.getPlayer(id);
        if (p != null){
            return p.countCells() > 0;
        }
        return false;
    }
    
    public void checkCollisions(){
        for(int i = 0; i < this.players.size();i++){
            Player p1 = this.players.get(i);
            for(int j = i+1; j < this.players.size(); j++){
                Player p2 = this.players.get(j);
                p1.checkCollision(p2);
                if(p1.getMustDie()){
                    this.players.remove(i);
                    i-=1;
                    break;
                }
                if(p2.getMustDie()){
                    this.players.remove(j);
                    j-=1;
                }
            }
        }
    }
    
    public void render(Graphics g, double scale){
        for(int i = 0; i < players.size(); i++){
            Player p = players.get(i);
            p.render(g, scale);
        }
    }
    
    public boolean mover(String id,double x, double y){
        Player p = this.getPlayer(id);
        if(p!=null){
            p.mover(x, y);
            return true;
        }else{
            return false;
        }
    }
    
    public void checkCollisionVirus(String id){
        Player p = this.getPlayer(id);
        if(p!=null){
            gv.checkCollisions(p);
        }
    }
}
