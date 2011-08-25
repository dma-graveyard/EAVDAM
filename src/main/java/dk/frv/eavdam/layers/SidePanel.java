package dk.frv.eavdam.layers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.bbn.openmap.gui.MapPanelChild;



public class SidePanel extends JPanel implements MapPanelChild, ActionListener {

	private static final long serialVersionUID = 1L;

	private JLabel textLabel = new JLabel();
	private StationLayer layer;
	private OMAISBaseStationReachLayerA reachLayerA;
	private JButton addButton = new JButton("Add");
	private JTextField nameTF = new JTextField(20);
	private JTextField antennaHeightTF = new JTextField(20);
	private JTextField longitude = new JTextField(20);
	private JTextField latitude = new JTextField(20);
	private JPanel aisPanel = new JPanel();
	
	public SidePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
//		panel.add(new JLabel("Test"), BorderLayout.NORTH);
		this.textLabel.setPreferredSize(new Dimension(150,150));
		panel.add(this.textLabel, BorderLayout.NORTH);
		JScrollPane sp = new JScrollPane(panel);

		
		
		aisPanel.setLayout(new GridLayout(16, 1));
		aisPanel.add(new JLabel(""));
		aisPanel.add(new JLabel(""));
		aisPanel.add(new JLabel(""));
		aisPanel.add(new JLabel(""));
		aisPanel.add(new JLabel(""));
		aisPanel.add(new JLabel(""));
		aisPanel.add(new JLabel("Insert new AIS Base Station"));
		aisPanel.add(new JLabel("Name"));
		aisPanel.add(this.nameTF);
		aisPanel.add(new JLabel("Antenna Height (m)"));
		aisPanel.add(this.antennaHeightTF);
		aisPanel.add(new JLabel("Latitude"));
		aisPanel.add(this.latitude);
		aisPanel.add(new JLabel("Longitude"));
		aisPanel.add(this.longitude);
		aisPanel.add(this.addButton);

		this.addButton.addActionListener(this);

		this.setLayout(new BorderLayout());
		this.add(sp, BorderLayout.NORTH);
		this.add(aisPanel, BorderLayout.CENTER);
		
		aisPanel.setVisible(false);
		
		JPanel imagePanel = new JPanel();
		imagePanel.setLayout(new GridLayout(2,1));
//		Image effSea = (new ImageIcon("efficiensea.png")).getImage();
//		effSea = effSea.getScaledInstance(45, 45,  java.awt.Image.SCALE_SMOOTH);
//		imagePanel.add(new JLabel(new ImageIcon(effSea)));

		imagePanel.add(new JLabel(new ImageIcon("efficiensea.png")));
		imagePanel.add(new JLabel(new ImageIcon("euBaltic.png")));
		
		this.add(imagePanel, BorderLayout.SOUTH);
		
		
		this.setPreferredSize(new Dimension(200, 200));
		
	}

	public void setAISSPVisible(boolean visible){
		this.aisPanel.setVisible(visible);
	}
	
	public void setText(String s) {
		this.textLabel.setText(s);
	}

	public void setStationLayer(StationLayer layer) {
		this.layer = layer;
	}

	public void setStationReachLayerA(OMAISBaseStationReachLayerA layer) {
		this.reachLayerA = layer;
	}
	
	@Override
	public String getPreferredLocation() {
		return BorderLayout.EAST;
	}

	@Override
	public void setPreferredLocation(String arg0) {
	}

	@Override
	public String getParentName() {
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.addButton) {
			if (this.layer != null) {
				boolean ok = true;
				double lon = 0, lat = 0;
				try {
					String name = null;
					int antennaHeight = 0;

					try{
	            		//Create new OMBaseStation object...
	            		lat = Double.parseDouble(latitude.getText().replaceAll(",", "."));
	            	}catch(Exception ale){
	            		latitude.setBackground(Color.RED);
	            		ok = false;
	            	}
	            	
	            	try{
	            		//Create new OMBaseStation object...
	            		lon = Double.parseDouble(longitude.getText().replaceAll(",", "."));
	            	}catch(Exception ale){
	            		longitude.setBackground(Color.RED);
	            		ok = false;
	            	}

	            	name = nameTF.getText();
	            	if(name == null || name.trim().length() == 0){
	            		nameTF.setBackground(Color.RED);
	            		ok = false;
	            	}
	            	

	            	try{
	            		//Create new OMBaseStation object...
	            		antennaHeight = Integer.parseInt(antennaHeightTF.getText());
	            	}catch(Exception ale){
	            		antennaHeightTF.setBackground(Color.RED);
	            		ok = false;
	            	}
					
					if(ok){
						this.layer.addBaseStation(lat, lon, name, antennaHeight);
						antennaHeightTF.setText(null);
						antennaHeightTF.setBackground(Color.WHITE);
						nameTF.setText(null);
						nameTF.setBackground(Color.WHITE);
						longitude.setText(null);
						longitude.setBackground(Color.WHITE);
						latitude.setText(null);
						latitude.setBackground(Color.WHITE);
					}
				} catch (Exception ex) {
					System.out.println("Could not add base station: " + ex);
				}
				
				if(ok){
					
					this.setAISSPVisible(false);
					
					String text = 
							"<html><font color=\"green\">Base station added to "+lat+";"+lon+"</font><br>";
					
					this.setText(text);
				}
			} else {
				System.out.println("No layer connected");
			}
		}
	}
}
