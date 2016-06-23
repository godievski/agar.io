package View;

import Controller.GestorPlayer;
import Controller.GestorVirus;
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

/**
 *
 * @author Godievski
 */
public class DrawingSpace extends Canvas{
    private Image dibujoAux;
    private Graphics gAux;
    private Dimension dimAux;
    private final Dimension dimPanel;
    
    private GestorPlayer players;
    private GestorVirus virus;
    
    public DrawingSpace(GestorPlayer players,GestorVirus virus, Dimension d){
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
        
        // se hace esto porque mantiene la interfaz antigua
        Graphics2D gAux2 = (Graphics2D) gAux;
        gAux2.setStroke(new BasicStroke(2));
        
        int espacio = 40;
        for (int i=0; i < dimPanel.width; i+=espacio)
            gAux2.drawLine(i, 0, i, dimPanel.height);                    
        for (int j=0; j < dimPanel.height; j+=espacio)
            gAux2.drawLine(0, j, dimPanel.width, j);
        
        
        // pa q las bolitas saldrian gruesas
//        gAux2.setStroke(new BasicStroke(2));
        
        
        //DIBUJA JUGADORES
        
        if (this.players != null) {
            this.players.render(gAux, 1);
        } else {
            System.out.println("Players null");
        }
        if (this.virus != null){
            this.virus.render(gAux, 1);
        }else {
            System.out.println("Virus null");
        }

        g.drawImage(this.dibujoAux, 0, 0, this);
        g.dispose();
    }
    
}
