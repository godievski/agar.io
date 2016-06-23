package agar;

import View.WindowGame;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Godievski
 */
public class Agar {
    public static void main(String[] args) {
        try {
            //WindowGame w = new WindowGame(args[0], args[1]);
            WindowGame w = new WindowGame("localhost", "1099");
            w.play();
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(Agar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
