package dk.frv.eavdam.menus;

import com.bbn.openmap.gui.AbstractOpenMapMenu;
import com.bbn.openmap.gui.OpenMapFrame;
import dk.frv.eavdam.io.EmailSender;
import dk.frv.eavdam.layers.StationLayer;
import javax.swing.JSeparator;

/**
 * This class represents a menu containing eavdam related items.
 */
public class EavdamMenu extends AbstractOpenMapMenu {

    public static final long serialVersionUID = 3L;

    private String defaultText = "Eavdam";
    private int defaultMnemonic = 'E';
    
    private OpenMapFrame openMapFrame;
    private StationLayer stationLayer;

    public EavdamMenu() {
        super();
        setText(defaultText);
        setMnemonic(defaultMnemonic);

        add(new UserInformationMenuItem(this));
        //add(new AddStationMenuItem(this));
        add(new StationInformationMenuItem(this));         
        add(new OptionsMenuItem(this));
        // add(new JSeparator());
    }

    public OpenMapFrame getOpenMapFrame() {
        return openMapFrame;
    }
    
    public StationLayer getStationLayer() {
        return stationLayer;
    }

	@Override
	public void findAndInit(Object obj) {
		if (obj instanceof OpenMapFrame) {
			this.openMapFrame = (OpenMapFrame) obj;
		} else if (obj instanceof StationLayer) {
		    this.stationLayer = (StationLayer) obj;
		}
	}

}