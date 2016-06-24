package Controller;

import Model.Cell;
import Model.Player;
import View.WindowGame;
import java.util.ArrayList;
import java.awt.Graphics;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Godievski
 */
public class GestorVirus extends UnicastRemoteObject implements IGestorVirus{
    private final int MAX_VIRUS = 100;
    private ArrayList<Cell> virus;
    private int counter;
    
    public GestorVirus() throws RemoteException{
        this.virus = new ArrayList<>();
        this.counter = 0;
    }
    
    public void createVirus() throws RemoteException{
        if (this.virus.size() < MAX_VIRUS){
            Cell newVirus = new Cell(WindowGame.WINDOW_WIDTH, WindowGame.WINDOW_HEIGHT,true);
            newVirus.setID(counter);
            this.counter += 1;
            this.virus.add(newVirus);
        }
    }
    
    public int size() throws RemoteException{
        return this.virus.size();
    }
    
    public void deleteVirus(int id) throws RemoteException{
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
    
    public void render(Graphics g, double scale) throws RemoteException{
        for(int i = 0; i < virus.size(); i++){
            Cell v = virus.get(i);
            v.render(g, scale);
        }
    }

    public void checkCollisions(Player p)  throws RemoteException{
        for(int i = 0; i < virus.size(); i++){
            Cell v = virus.get(i);
            boolean collision = p.checkCollision(v);
            if (collision){
                virus.remove(i);
                break;
            }
        }
    }
    
    public Cell getVirus(int index) throws RemoteException{
        return this.virus.get(index);
    }
}
