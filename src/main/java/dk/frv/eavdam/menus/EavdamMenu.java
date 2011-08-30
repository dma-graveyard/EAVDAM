package dk.frv.eavdam.menus;

import com.bbn.openmap.gui.AbstractOpenMapMenu;

/**
 * This class represents a menu containing eavdam related items.
 */
public class EavdamMenu extends AbstractOpenMapMenu {

    public static final long serialVersionUID = 3L;

    private String defaultText = "Eavdam";
    private int defaultMnemonic = 'E';

    public EavdamMenu() {
        super();
        setText(defaultText);
        setMnemonic(defaultMnemonic);

        add(new UserInformationMenuItem());
        add(new StationInformationMenuItem());
        // add(new JSeparator());
    }

}