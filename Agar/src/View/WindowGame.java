package View;

import Controller.IGestorPlayer;
import Controller.IGestorVirus;
import Controller.Moving;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
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
    
    private IGestorPlayer players;
    private IGestorVirus virus;
    private String nick;
    private int id;
    private Login loginWindow;
    private DrawingSpace ds;
    
    private Moving movPlayer;
    //private Collision collision;
    //private Infecting infecting;
    
    public WindowGame(String ip, String port) throws NotBoundException, MalformedURLException, RemoteException{
        initComponents(ip, port);
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
        this.nick = this.loginWindow.getNickname();
        try {
            this.id = this.players.addNewPlayer(nick, this.getWidth(), this.getHeight());
            this.ds.setID(this.id);
        } catch (RemoteException ex) {
            Logger.getLogger(WindowGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //LOCAL
        this.movPlayer = new Moving(id,players,this);
        this.movPlayer.start();
        
        /*
        //SERVER
        this.collision = new Collision(players,virus);
        this.collision.start();
        this.infecting = new Infecting(virus);
        this.infecting.start();
        */
        
        //PLAY
        while(true){
            this.ds.repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(WindowGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void initComponents(String ip, String port) throws NotBoundException, MalformedURLException, RemoteException{
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(false);
        this.setBounds(WINDOW_POS_X, WINDOW_POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setResizable(false);
        this.setFocusable(true);
        this.setLocationRelativeTo(null);
        
        //SERVER
        //this.virus = new GestorVirus();
        //this.players = new GestorPlayer(this.virus);
        this.virus = (IGestorVirus) Naming.lookup("//"+ip+":"+port+"/virus");
        this.players = (IGestorPlayer) Naming.lookup("//"+ip+":"+port+"/players");
        ///
        
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
        if(this.ds != null){
            this.ds.paint(g);
        }
    }
    
    int contador = 0;
    
    public void formKeyPressed(KeyEvent e){
        //SPLIT
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_SPACE){
            try {
                this.players.split(id);
            } catch (RemoteException ex) {
                Logger.getLogger(WindowGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
