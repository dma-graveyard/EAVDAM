package dk.frv.eavdam.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

public class SimpleMenuItem extends JMenuItem {

    public static final long serialVersionUID = 3L;

    public SimpleMenuItem() {
        super("A menu item that does nothing");
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {}
        });
    }
    
}