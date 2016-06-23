package View;

import Controller.GestorPlayer;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Godievski
 */
public class Login extends JDialog{
    private final JLabel jlblUsername = new JLabel("Nickname");
    private final JTextField jtfUsername = new JTextField(15);
    private final JButton jbtOk = new JButton("Play");
    private final JButton jbtCancel = new JButton("Cancel");
    

    private boolean state;
    private final GestorPlayer gp;
    
    public Login(GestorPlayer gp) {
        //this(null, true);
        this.state = false;
        this.gp = gp;
    }

    public Login(final JFrame parent, boolean modal, final GestorPlayer gp) {
        super(parent, modal);
        this.gp = gp;
        this.state = false;
        this.setTitle("Agar by Godievski");
        
        JPanel p3 = new JPanel(new GridLayout(2, 1));
        p3.add(jlblUsername);

        JPanel p4 = new JPanel(new GridLayout(2, 1));
        p4.add(jtfUsername);

        JPanel p1 = new JPanel();
        p1.add(p3);
        p1.add(p4);

        JPanel p2 = new JPanel();
        p2.add(jbtOk);
        p2.add(jbtCancel);

        JPanel p5 = new JPanel(new BorderLayout());
        p5.add(p2, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(p1, BorderLayout.CENTER);
        add(p5, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        addWindowListener(new WindowAdapter() {  
            @Override
            public void windowClosing(WindowEvent e) {  
                System.exit(0);  
            }  
        });


        jbtOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gp.checkID(jtfUsername.getText())){
                    parent.setVisible(true);
                    setVisible(false);
                    state = true;
                }
            }
        });
        jbtCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                setVisible(false);
                parent.dispose();
                System.exit(0);
                
            }
        });
    }
    public String getNickname(){
        return this.jtfUsername.getText();
    }
    public boolean getState(){
        return this.state;
    }
}
