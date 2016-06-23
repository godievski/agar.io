package Controller;
 
import java.rmi.RemoteException;
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
            try {
                gs.createVirus();
                Thread.sleep(INTERVALO);
            } catch (RemoteException | InterruptedException ex) {
                Logger.getLogger(Infecting.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}