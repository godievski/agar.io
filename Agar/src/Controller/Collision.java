package Controller;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Godievski
 */
public class Collision extends Thread{
    private static final int INTERVAL = 10;
    
    private GestorPlayer gp;
    private GestorVirus gv;
    public Collision(GestorPlayer gp, GestorVirus gv){
        this.gp = gp;
        this.gv = gv;
    }
    
    public void run(){
        while(true){
            gp.checkCollisions();
            try {
                Thread.sleep(INTERVAL);
            } catch (InterruptedException ex) {
                Logger.getLogger(Collision.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
