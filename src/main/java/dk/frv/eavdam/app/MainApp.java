package dk.frv.eavdam.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.AbstractButton;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import com.bbn.openmap.MapHandler;
import com.bbn.openmap.PropertyHandler;
import com.bbn.openmap.gui.BasicMapPanel;
import com.bbn.openmap.gui.MapPanel;
import com.bbn.openmap.gui.OpenMapFrame;
import com.bbn.openmap.util.ArgParser;
import com.bbn.openmap.util.Debug;
import dk.frv.eavdam.layers.StationLayer;

/**
 * The OpenMap application framework. This class creates a PropertyHandler that
 * searches the classpath, config directory and user's home directory for an
 * openmap.properties file, and creates the application based on the contents of
 * the properties files. It also creates an MapPanel and an OpenMapFrame to be
 * used for the application and adds them to the MapHandler contained in the
 * MapPanel. All other components are added to that MapHandler as well, and they
 * use the MapHandler to locate, connect and communicate with each other.
 */
public class MainApp {

    protected MapPanel mapPanel;

    /**
     * Create a new OpenMap framework object - creates a MapPanel, OpenMapFrame,
     * and brings up the layer palettes that are being told to be open at
     * startup. The MapPanel will create a PropertiesHandler that will search
     * for an openmap.properties file.
     */
    public MainApp() {
        this(null);
    }

    /**
     * Create a new OpenMap framework object - creates a MapPanel, OpenMapFrame,
     * and brings up the layer palettes that are being told to be open at
     * startup. The properties in the PropertyHandler will be used to configure
     * the application. PropertyHandler may be null.
     */
    public MainApp(PropertyHandler propertyHandler) {
        mapPanel = new BasicMapPanel(propertyHandler, true);

        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // TODO There's something going on here with the progress
                // reporter and the swing thread that is causing the app to hang
                // in Leopard.
                showInFrame();
            }
        });
    }

    protected void showInFrame() {
        
        ((BasicMapPanel)mapPanel).create();
        OpenMapFrame omf = new OpenMapFrame();
        setWindowListenerOnFrame(omf);
        getMapHandler().add(omf);
        
        //omf.getContentPane().add(mainPanel, BorderLayout.EAST); 

        omf.setVisible(true);
        mapPanel.getMapBean().showLayerPalettes();
        Debug.message("basic", "OpenMap: READY");
    }

    /**
     * A method called internally to set the WindowListener behavior on an OpenMapFrame
     * used for the OpenMap application. By default, this method adds a
     * WindowAdapter that calls System.exit(0), killing java. You can extend
     * this to add a WindowListener to the OpenMapFrame that does nothing or
     * something else.
     */
    protected void setWindowListenerOnFrame(OpenMapFrame omf) {
        omf.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * Get the MapHandler used for the OpenMap object.
     */
    public MapHandler getMapHandler() {
        return mapPanel.getMapHandler();
    }

    /**
     * Get the MapPanel, the container for the OpenMap components.
     */
    public MapPanel getMapPanel() {
        return mapPanel;
    }

    /**
     * Create and return an OpenMap object that uses a standard PropertyHandler
     * to configure itself. The OpenMap object has a MapHandler that you can use
     * to gain access to all the components.
     * 
     * @return OpenMap
     * @see #getMapHandler
     */
    public static MainApp create() {
        return new MainApp(null);
    }

    /**
     * Create and return an OpenMap object that uses a standard PropertyHandler
     * to configure itself. The OpenMap object has a MapHandler that you can use
     * to gain access to all the components.
     * 
     * @return OpenMap
     * @see #getMapHandler
     */
    public static MainApp create(String propertiesFile) {
        Debug.init();

        PropertyHandler propertyHandler = null;

        if (propertiesFile != null) {
            try {
                propertyHandler = new PropertyHandler(propertiesFile);
            } catch (MalformedURLException murle) {
                Debug.error(murle.getMessage());
                murle.printStackTrace();
                propertyHandler = null;
            } catch (IOException ioe) {
                Debug.error(ioe.getMessage());
                ioe.printStackTrace();
                propertyHandler = null;
            }
        }

        return new MainApp(propertyHandler);
    }

    /**
     * The main OpenMap application.
     */
    static public void main(String args[]) {

        ArgParser ap = new ArgParser("OpenMap");
        String propArgs = null;
        ap.add("properties",
                "A resource, file path or URL to properties file\n Ex: http://myhost.com/xyz.props or file:/myhome/abc.pro\n See Java Documentation for java.net.URL class for more details",
                1);

        ap.parse(args);

        String[] arg = ap.getArgValues("properties");
        if (arg != null) {
            propArgs = arg[0];
        }

        MainApp.create(propArgs);
    }
}