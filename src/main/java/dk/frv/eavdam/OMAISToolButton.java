package dk.frv.eavdam;

import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Image;

import com.bbn.openmap.gui.OMGraphicDeleteTool;
import com.bbn.openmap.gui.OMToolComponent;
import com.bbn.openmap.gui.Tool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.bbn.openmap.I18n;
import com.bbn.openmap.InformationDelegator;
import com.bbn.openmap.MapBean;
import com.bbn.openmap.omGraphics.OMAction;
import com.bbn.openmap.omGraphics.OMGraphic;
import com.bbn.openmap.omGraphics.OMGraphicConstants;
import com.bbn.openmap.omGraphics.OMGraphicList;
import com.bbn.openmap.omGraphics.OMList;
import com.bbn.openmap.omGraphics.event.SelectionEvent;
import com.bbn.openmap.omGraphics.event.SelectionListener;
import com.bbn.openmap.omGraphics.event.SelectionProvider;
import com.bbn.openmap.tools.drawing.DrawingToolRequestor;
import com.bbn.openmap.tools.drawing.OMDrawingTool;
import com.bbn.openmap.util.Debug;

import dk.frv.eavdam.layers.OMBaseStation;
import dk.frv.eavdam.layers.SidePanel;
import dk.frv.eavdam.layers.StationLayer;

/**
 * AIS-Button that is shown in the toolbar. This opens the "Add AIS Base Station" side panel. 
 * 
 * 
*/
public class OMAISToolButton extends OMToolComponent implements ActionListener, Tool {

 /**
	 * 
	 */
	private static final long serialVersionUID = 6706092075829947101L;
	protected JButton aisToolButton = null;
	private OMGraphicList graphics = new OMGraphicList();
	private SidePanel sidePanel;
	protected JToolBar jToolBar;
	InformationDelegator informationDelegator;
 
 
	public OMAISToolButton() {
		super();
		setKey(defaultKey);
		Debug.message("AIS Tool Button", "OMGTL()");

		setLayout(new java.awt.GridLayout());
		jToolBar = new JToolBar();
		jToolBar.setFloatable(false);

		ImageIcon icon = new ImageIcon("ais_bs.png");
		if (icon != null || icon.getImage() == null) {
			aisToolButton = new JButton(icon);
		} else {
			aisToolButton = new JButton("AIS");
		}

		aisToolButton.addActionListener(this);
		// deleteButton.setToolTipText("Delete selected map graphic");
		aisToolButton.setToolTipText(i18n.get(OMGraphicDeleteTool.class,
             "AISToolButton",
             I18n.TOOLTIP,
             "Add AIS Base Station"));
		aisToolButton.setEnabled(true);

     
		jToolBar.add(aisToolButton);
		add(jToolBar);
	}

	 public void keyPressed(KeyEvent e) {
		 Debug.message("testbutton", "Test button works!");
		 
	 }

	 public void keyReleased(KeyEvent e) {
	
	 }
	
	 public void keyTyped(KeyEvent e) {
		 Debug.message("testbutton", "Test button typed?!");
		 
	 }
	
	 public void actionPerformed(ActionEvent ae) {
		if (this.sidePanel != null) {
			this.sidePanel.setText(null);
		}
		
//		System.out.println("AIS Base Station button clicked!");
		
		if (this.sidePanel != null) {
			this.sidePanel.setText("Add a new AIS Base Station");
			this.sidePanel.setAISSPVisible(true);
			
//			AISBaseStationDialog dialog = new AISBaseStationDialog();
		}
		
 }





 

 // /////////////////////////////////////////////////////////////////////////
 // // MapHandlerChild methods to make the tool work with
 // // the MapHandler to find any SelectionProviders.
 // /////////////////////////////////////////////////////////////////////////

	 public void findAndInit(Object obj) {
		
		if (obj instanceof InformationDelegator) {
//			this.infoDelegator = (InformationDelegator) obj;
		} else if (obj instanceof SidePanel) {
			this.sidePanel = (SidePanel) obj;
//			System.out.println("TestTool found " + obj.getClass());
		} else if(obj instanceof SidePanel){
			
			
		}

	 }

	 public void findAndUndo(Object obj) {
	
	 }

}

