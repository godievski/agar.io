/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    private ArrayList<Player> players;
    private static final Random ran = new Random();
    
    public GestorPlayer(){
        this.players = new ArrayList();
    }
    
    public Player getPlayer(String id){
        for(Player p: players){
            if (p.getID() == id)
                return p;
        }
        return null;
    }
    
    public boolean exits(String id){
        return getPlayer(id) != null;
    }
    
    public boolean addNewPlayer(String id,int xMax, int yMax){
        if (!exits(id)){
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
        int size = this.players.size();
        for(int i = 0; i < size;i++){
            Player p1 = this.players.get(i);
            for(int j = i+1; j < size; j++){
                Player p2 = this.players.get(j);
                p1.checkCollision(p2);
            }
        }
    }
    
    public void render(Graphics g, double scale){
        for(Player p: this.players){
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
}
