/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.GestorPlayer;
import Controller.Moving;
import Model.Cell;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
    private String id;
    private Login loginWindow;
    private DrawingSpace ds;
    private Moving movPlayer;
    
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
        this.movPlayer = new Moving(id,players,this);
        this.movPlayer.start();
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
        //DELETE PLAYER
        //System.exit(0);
    }
    
    private void initComponents(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(false);
        this.setBounds(WINDOW_POS_X, WINDOW_POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setResizable(false);
        this.setFocusable(true);
        this.setLocationRelativeTo(null);
        
        this.players = new GestorPlayer();
        this.ds = new DrawingSpace(this.players, new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
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
    
    public void formKeyPressed(KeyEvent e){
        //SPLIT
    }
}
