/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agar;

import Controller.Collision;
import Controller.GestorPlayer;
import Controller.GestorVirus;
import Controller.Infecting;
import View.WindowGame;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Godievski
 */
public class Server {
    public static void main(String[] args) {
        try {
            GestorVirus gv = new GestorVirus();
            GestorPlayer gp = new GestorPlayer(gv);
            Collision collision = new Collision(gp,gv);
            Infecting infecting = new Infecting(gv);
            Naming.rebind("//localhost:1099/virus", gv);
            Naming.rebind("//localhost:1099/players", gp);
            collision.start();
            infecting.start();
            System.out.println("Server created");
            System.out.println(InetAddress.getLocalHost().getHostAddress());
        } catch (RemoteException | MalformedURLException | UnknownHostException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
