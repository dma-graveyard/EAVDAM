package dk.frv.eavdam.menus;

import com.bbn.openmap.gui.AbstractOpenMapMenu;

public class SimpleMenu extends AbstractOpenMapMenu {

    public static final long serialVersionUID = 3L;

    private String defaultText = "Test";
    private int defaultMnemonic = 'T';

    public SimpleMenu() {
        super();
        setText(defaultText);
        setMnemonic(defaultMnemonic);

        add(new SimpleMenuItem());
        // add(new JSeparator());
    }

}