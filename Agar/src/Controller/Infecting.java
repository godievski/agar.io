package Controller;
 
import java.util.logging.Level;
import java.util.logging.Logger;
 
/**
 *
 * @author Godievski
 */
public class Infecting extends Thread {
    private static final int INTERVALO = 1000;  
   
    private GestorVirus gs;
   
    public Infecting(GestorVirus gs){
        this.gs = gs;
    }
   
    @Override
    public void run(){
        while(true){
            gs.createVirus();
            try {
                Thread.sleep(INTERVALO);
            } catch (InterruptedException ex) {
                Logger.getLogger(Moving.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}