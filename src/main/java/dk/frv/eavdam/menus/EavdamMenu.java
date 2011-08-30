package dk.frv.eavdam.menus;

import com.bbn.openmap.gui.AbstractOpenMapMenu;
import com.bbn.openmap.gui.OpenMapFrame;

/**
 * This class represents a menu containing eavdam related items.
 */
public class EavdamMenu extends AbstractOpenMapMenu {

    public static final long serialVersionUID = 3L;

    private String defaultText = "Eavdam";
    private int defaultMnemonic = 'E';
    
    private OpenMapFrame openMapFrame;

    public EavdamMenu() {
        super();
        setText(defaultText);
        setMnemonic(defaultMnemonic);

        add(new UserInformationMenuItem(this));
        add(new StationInformationMenuItem(this));
        // add(new JSeparator());
    }

    public OpenMapFrame getOpenMapFrame() {
        return openMapFrame;
    }

	@Override
	public void findAndInit(Object obj) {
		if (obj instanceof OpenMapFrame) {
			this.openMapFrame = (OpenMapFrame) obj;
		}
	}

}