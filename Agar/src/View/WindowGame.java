/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Collision;
import Controller.GestorPlayer;
import Controller.GestorVirus;
import Controller.Infecting;
import Controller.Moving;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Godievski
 */
public class WindowGame extends JFrame{
    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 700;
    public static final int WINDOW_POS_X = 100;
    public static final int WINDOW_POS_Y = 50;
    
    private GestorPlayer players;
    private GestorVirus virus;
    private String id;
    private Login loginWindow;
    private DrawingSpace ds;
    
    private Moving movPlayer;
    private Collision collision;
    private Infecting infecting;
    
    public WindowGame(){
        initComponents();
        this.loginWindow = new Login(this, false);
        this.loginWindow.setVisible(true);
    }
    
    
    public void play(){
        //ESPERA A QUE INGRESE SU NICKNAME
        while(!this.loginWindow.getState()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(WindowGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        this.createBufferStrategy(2);
        this.setLocationRelativeTo(null);
        this.setIgnoreRepaint(false);
        
        
        
        //ADD PLAYER
        this.id = this.loginWindow.getNickname();
        this.players.addNewPlayer(id, this.getWidth(), this.getHeight());
        
        this.players.addNewPlayer("xxx", this.getWidth()*2, this.getHeight()*2);
        this.players.getPlayer("xxx").getCell(0).setCenterX(this.getWidth()/2);
        this.players.getPlayer("xxx").getCell(0).setCenterY(this.getHeight()/2);
        
        this.players.getPlayer(id).getCell(0).setMass(500);
        this.players.getPlayer(id).split();
        
        //LOCAL
        this.movPlayer = new Moving(id,players,this);
        this.movPlayer.start();
        
        //SERVER
        this.collision = new Collision(players,virus);
        this.collision.start();
        this.infecting = new Infecting(virus);
        this.infecting.start();
        
        //PLAY
        while(this.players.isAlive(id)){
            this.ds.repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(WindowGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //DEAD
        System.exit(0);
    }
    
    private void initComponents(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(false);
        this.setBounds(WINDOW_POS_X, WINDOW_POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setResizable(false);
        this.setFocusable(true);
        this.setLocationRelativeTo(null);
        
        //SERVER
        this.virus = new GestorVirus();
        this.players = new GestorPlayer(this.virus);
        
        this.ds = new DrawingSpace(this.players,this.virus, new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.ds.setFocusable(false);
        this.ds.setIgnoreRepaint(false);
        this.add((Component)this.ds);
        
        this.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                formKeyPressed(e);
            }
        });
    }
    
    @Override
    public void paint(Graphics g){
        //this.loginWindow.paint(g);
        if(this.ds != null){
            this.ds.paint(g);
        }
    }
    
    int contador = 0;
    
    public void formKeyPressed(KeyEvent e){
        //SPLIT
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_X){
            this.players.addNewPlayer(""+contador, this.getWidth()*2, this.getHeight()*2);
            contador += 1;
        }
    }
}
