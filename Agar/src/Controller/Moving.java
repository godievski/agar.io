package Controller;

import Model.Player;
import View.WindowGame;
import java.awt.MouseInfo;
import java.awt.Point;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Godievski
 */
public class Moving extends Thread{
    private static final int INTERVALO = 20;
    
    private int id;
    private IGestorPlayer gp;
    private WindowGame wg;
    private double xFinal,yFinal;
    
    public Moving(int id, IGestorPlayer gp, WindowGame wg){
        this.gp = gp;
        this.id = id;
        this.wg = wg;
        this.xFinal = this.yFinal = 0;
    }
    
    @Override
    public void run(){
        while(true){
            try {
                this.updatePositionMouse();
                gp.mover(id, xFinal, yFinal);
                gp.checkCollisionVirus(id);
                Thread.sleep(INTERVALO);
            } catch (RemoteException | InterruptedException ex) {
                Logger.getLogger(Moving.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void updatePositionMouse(){
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        Point window = wg.getLocationOnScreen();
        this.xFinal = mouse.x - window.x;
        this.yFinal = mouse.y - window.y;
    }
}
