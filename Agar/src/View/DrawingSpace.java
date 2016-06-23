package View;

import Controller.IGestorPlayer;
import Controller.IGestorVirus;
import Model.Cell;
import Model.Player;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Godievski
 */
public class DrawingSpace extends Canvas{
    private Image dibujoAux;
    private Graphics gAux;
    private Dimension dimAux;
    private final Dimension dimPanel;
    
    private IGestorPlayer players;
    private IGestorVirus virus;
    
    public DrawingSpace(IGestorPlayer players,IGestorVirus virus, Dimension d){
        this.players = players;
        this.virus = virus;
        this.setSize(d);
        this.dimPanel = d;
    }
    
    @Override
    public void update(Graphics g){
        paint(g);
    }
    
    @Override
    public void paint(Graphics g){
        if (gAux == null || dimAux == null || dimPanel.width != dimAux.width ||
                dimPanel.height != dimAux.height){
            dimAux = dimPanel;
            dibujoAux = createImage(dimAux.width,dimAux.height);
            gAux = dibujoAux.getGraphics();
        }

        //FONDO
        gAux.setColor(new Color(220, 220, 220));
        gAux.setColor(Color.WHITE);
        gAux.fillRect(0, 0, WindowGame.WINDOW_WIDTH, WindowGame.WINDOW_HEIGHT);
        
        gAux.setColor(new Color(220, 220, 220));
        

        int espacio = 40;
        for (int i=0; i < dimPanel.width; i+=espacio)
            gAux.drawLine(i, 0, i, dimPanel.height);                    
        for (int j=0; j < dimPanel.height; j+=espacio)
            gAux.drawLine(0, j, dimPanel.width, j);
        
        //DIBUJA JUGADORES
        
        if (this.players != null) {
            try {
                this.renderPlayers(this.players,gAux);
            } catch (RemoteException ex) {
                Logger.getLogger(DrawingSpace.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Players null");
        }
        if (this.virus != null){
            try {
                this.renderVirus(this.virus,gAux);
            } catch (RemoteException ex) {
                Logger.getLogger(DrawingSpace.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            System.out.println("Virus null");
        }
        
        //DRAW LADERBOARD
        try {
            ArrayList<Player> tops;
            tops = players.getTop();
            paintLaderBoard(tops, g);
        } catch (RemoteException ex) {
            Logger.getLogger(DrawingSpace.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        g.drawImage(this.dibujoAux, 0, 0, this);
        g.dispose();
    }
    
    
    private void paintLaderBoard(ArrayList<Player> tops, Graphics g){
        
    }
    
    private void renderPlayers(IGestorPlayer players,Graphics g) throws RemoteException{
        for(int i = 0; i < players.size(); i++){
            try{
                Player p = players.getPlayerIterator(i);
                p.render(g, 1);
            } catch(Exception ex){}
        }
    }
    
    private void renderVirus(IGestorVirus virus,Graphics g) throws RemoteException{
        for(int i = 0; i < virus.size(); i++){
            try{
                Cell c = virus.getVirus(i);
                c.render(g, 1);
            } catch(Exception ex){}
        }
    }
}
