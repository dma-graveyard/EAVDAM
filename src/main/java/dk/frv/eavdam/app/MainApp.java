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

        JPanel mainPanel = new JPanel();        
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));             
              
        // choose maps title      
              
        JPanel chooseMapsPanel = new JPanel();  
        chooseMapsPanel.setLayout(new BorderLayout());
        chooseMapsPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        JLabel chooseMapsLabel = new JLabel("Choose Map(s)", null, JLabel.CENTER);
        chooseMapsLabel.setFont(new Font("Serif", Font.BOLD, 16));
        chooseMapsLabel.setVerticalTextPosition(JLabel.BOTTOM);
        chooseMapsLabel.setHorizontalTextPosition(JLabel.CENTER);               
        chooseMapsPanel.add(chooseMapsLabel);
        chooseMapsPanel.setPreferredSize(new Dimension(250, 40));
        chooseMapsPanel.setMaximumSize(new Dimension(250, 40));
        mainPanel.add(chooseMapsPanel);
                   
        // map names list           
                              
        String[] mapNames = { "Bornholm2W4mEu", "Bornholm2W4mEz", "Bornholm2W4mEz1", "Bornholm2W10mEu", "Bornholm2W10mEz" };
        JList<String> mapList = new JList<String>(mapNames);
        mapList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        mapList.setLayoutOrientation(JList.VERTICAL);
        mapList.setVisibleRowCount(4);
        JScrollPane mapListScroller = new JScrollPane(mapList);
        mapListScroller.setBorder(new CompoundBorder
            (BorderFactory.createEmptyBorder(0,10,10,10),
            BorderFactory.createLineBorder(new Color(122, 138, 153), 1)));
        mapListScroller.setPreferredSize(new Dimension(250, 90));
        mapListScroller.setMaximumSize(new Dimension(250, 90));
        mainPanel.add(mapListScroller);
     
        // add map and remove map buttons
     
        JPanel mapButtonsPanel = new JPanel();
        mapButtonsPanel.setBorder(BorderFactory.createEmptyBorder(0,10,10,10)); 
        GridLayout gridLayout = new GridLayout(1,2);               
        gridLayout.setHgap(10);
        mapButtonsPanel.setLayout(gridLayout);
        JButton addMapButton = new JButton("Add map", null);        
        addMapButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        addMapButton.setHorizontalTextPosition(AbstractButton.CENTER);
        addMapButton.setPreferredSize(new Dimension(100, 35));
        addMapButton.setMaximumSize(new Dimension(100, 35));
        mapButtonsPanel.add(addMapButton);
        JButton removeMapButton = new JButton("Remove map", null);        
        removeMapButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        removeMapButton.setHorizontalTextPosition(AbstractButton.CENTER);
        removeMapButton.setPreferredSize(new Dimension(130, 35));
        removeMapButton.setMaximumSize(new Dimension(130, 35));
        mapButtonsPanel.add(removeMapButton);        
        mapButtonsPanel.setMinimumSize(new Dimension(250, 35));
        mapButtonsPanel.setPreferredSize(new Dimension(250, 35));
        mapButtonsPanel.setMaximumSize(new Dimension(250, 35));        
        mainPanel.add(mapButtonsPanel);

        // empty textarea

        JTextArea textArea = new JTextArea("");
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        JScrollPane areaScrollPane = new JScrollPane(textArea);
        areaScrollPane.setBorder(new CompoundBorder
            (BorderFactory.createEmptyBorder(0,10,10,10),
            BorderFactory.createLineBorder(new Color(122, 138, 153), 1)));
        areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        areaScrollPane.setPreferredSize(new Dimension(250, 90));
        areaScrollPane.setMaximumSize(new Dimension(250, 90));
        mainPanel.add(areaScrollPane);
        
        // display maps button
        
        JPanel displayMapsButtonPanel = new JPanel();
        displayMapsButtonPanel.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));                
        displayMapsButtonPanel.setLayout(new GridLayout(1,1));
        JButton displayMapsButton = new JButton("Display map(s)", null);        
        displayMapsButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        displayMapsButton.setHorizontalTextPosition(AbstractButton.CENTER);
        displayMapsButtonPanel.add(displayMapsButton);        
        displayMapsButtonPanel.setPreferredSize(new Dimension(150, 35));
        displayMapsButtonPanel.setMaximumSize(new Dimension(150, 35));        
        mainPanel.add(displayMapsButtonPanel);        

        // base station parameters title

        JPanel baseStationParametersPanel = new JPanel();  
        baseStationParametersPanel.setLayout(new BorderLayout());
        baseStationParametersPanel.setBorder(BorderFactory.createEmptyBorder(15,10,10,10));
        JLabel baseStationParametersLabel = new JLabel("Base station parameters", null, JLabel.CENTER);
        baseStationParametersLabel.setFont(new Font("Serif", Font.BOLD, 16));
        baseStationParametersLabel.setVerticalTextPosition(JLabel.BOTTOM);
        baseStationParametersLabel.setHorizontalTextPosition(JLabel.CENTER);               
        baseStationParametersPanel.add(baseStationParametersLabel);
        baseStationParametersPanel.setPreferredSize(new Dimension(250, 40));
        baseStationParametersPanel.setMaximumSize(new Dimension(250, 40));
        mainPanel.add(baseStationParametersPanel);

        // base station parameters content        
        
        JEditorPane infoPane = new JEditorPane("text/html",
            "<table cellspacing=1 cellpadding=1><tr><td>Localization:</td><td>...</td></tr>" +
            "<tr><td>Frequency:</td><td>...</td></tr>" +
            "<tr><td>Height of the transmitting antenna:</td><td>...</td></tr>" +
            "<tr><td>Transmitter antenna's gain:</td><td>...</td></tr>" +
            "<tr><td>Receiver sensitivity:</td><td>...</td></tr>" +
            "<tr><td>Receiver antenna's gain:</td><td>...</td></tr>" +
            "<tr><td>Power of the transmitter:</td><td>...</td></tr>" +
            "<tr><td>Height of the receiving antenna:</td><td>...</td></tr></table>");
        infoPane.setBorder(new CompoundBorder
            (BorderFactory.createLineBorder(new Color(122, 138, 153), 1),
            BorderFactory.createEmptyBorder(0,10,10,10)));
        infoPane.setPreferredSize(new Dimension(230, 180));
        infoPane.setMaximumSize(new Dimension(230, 180));
        mainPanel.add(infoPane);        
        
        // efficiensea logo
        
        URL efficienSeaImgURL = getClass().getResource("/share/data/images/efficiensea.png");        
        if (efficienSeaImgURL != null) {
            ImageIcon icon = new ImageIcon(efficienSeaImgURL, "");           
            JLabel iconLabel = new JLabel(icon);
            JPanel iconPanel = new JPanel();  
            iconPanel.setLayout(new BorderLayout());
            iconPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));             
            iconPanel.add(iconLabel);
            iconPanel.setMinimumSize(new Dimension(250, 75));
            iconPanel.setPreferredSize(new Dimension(250, 75));
            iconPanel.setMaximumSize(new Dimension(250, 75));
            mainPanel.add(iconPanel);
        } else {
            System.err.println("Couldn't find efficiensea.png file");
        }     
        
        // eu baltic logo
        
        URL euBalticImgURL = getClass().getResource("/share/data/images/euBaltic.png");        
        if (euBalticImgURL != null) {
            ImageIcon icon = new ImageIcon(euBalticImgURL, "");           
            JLabel iconLabel = new JLabel(icon);
            JPanel iconPanel = new JPanel();  
            iconPanel.setLayout(new BorderLayout());
            iconPanel.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));             
            iconPanel.add(iconLabel);
            iconPanel.setMinimumSize(new Dimension(250, 50));
            iconPanel.setPreferredSize(new Dimension(250, 50));
            iconPanel.setMaximumSize(new Dimension(250, 50));
            mainPanel.add(iconPanel);
        } else {
            System.err.println("Couldn't find euBaltic.png file");
        }             
        
        omf.getContentPane().add(mainPanel, BorderLayout.EAST); 

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