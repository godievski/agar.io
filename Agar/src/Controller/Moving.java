package Controller;

import Model.Player;
import View.WindowGame;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Godievski
 */
public class Moving extends Thread{
    private static final int INTERVALO = 20;
    
    private String id;
    private GestorPlayer gp;
    private WindowGame wg;
    private double xFinal,yFinal;
    public Moving(String id, GestorPlayer gp, WindowGame wg){
        this.gp = gp;
        this.id = id;
        this.wg = wg;
        this.xFinal = this.yFinal = 0;
    }
    
    @Override
    public void run(){
        while(true){
            this.updatePositionMouse();
            gp.mover(id, xFinal, yFinal);
            gp.checkCollisionVirus(id);
            try {
                Thread.sleep(INTERVALO);
            } catch (InterruptedException ex) {
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
