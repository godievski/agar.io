package Controller;

import Model.Player;
import java.awt.Graphics;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Godievski
 */
public class GestorPlayer extends UnicastRemoteObject implements IGestorPlayer{
    //ONLY TO CHECK COLLISIONS
    private GestorVirus gv;
    private int counter;
    private ArrayList<Player> players;
    private static final Random ran = new Random();
    
    public GestorPlayer(GestorVirus gv) throws RemoteException{
        this.gv = gv;
        this.players = new ArrayList<>();
        this.counter = 0;
    }
    
    public Player getPlayerID(int id) throws RemoteException{
        for(Player p: players){
            if (p.getID() == id)
                return p;
        }
        return null;
    }

    public int addNewPlayer(String nickname,int xMax, int yMax) throws RemoteException{
        this.players.add(new Player(this.counter,nickname, xMax, yMax));
        this.counter += 1;
        return this.counter - 1;
    }
    
    public int size() throws RemoteException{
        return this.players.size();
    }
    
    
    public void checkCollisions() throws RemoteException{
        for(int i = 0; i < this.players.size();i++){
            Player p1 = this.players.get(i);
            for(int j = i+1; j < this.players.size(); j++){
                Player p2 = this.players.get(j);
                p1.checkCollision(p2);
                if(p1.getMustDie()){
                    p1.reset();
                }
                if(p2.getMustDie()){
                    p2.reset();
                }
            }
        }
    }
    
    public void render(Graphics g, double scale) throws RemoteException{
        for(int i = 0; i < players.size(); i++){
            Player p = players.get(i);
            p.render(g, scale);
        }
    }
    
    public boolean mover(int id,double x, double y) throws RemoteException{
        Player p = this.getPlayerID(id);
        if(p!=null){
            p.mover(x, y);
            return true;
        }else{
            return false;
        }
    }
    
    public void checkCollisionVirus(int id) throws RemoteException{ 
        Player p = this.getPlayerID(id);
        if(p!=null){
            gv.checkCollisions(p);
        }
    }

    public void split(int id) throws RemoteException{
        Player p = this.getPlayerID(id);
        if(p != null){
            p.split();
        }
    }
    
    public ArrayList getTop() throws RemoteException{
        ArrayList<Player> playersTop = new ArrayList<>();
        return playersTop;
    }
    public Player getPlayerIterator(int index) throws RemoteException{
        return this.players.get(index);
    }

}
