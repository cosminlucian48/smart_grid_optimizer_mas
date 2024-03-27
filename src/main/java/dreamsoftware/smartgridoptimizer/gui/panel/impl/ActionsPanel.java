package dreamsoftware.smartgridoptimizer.gui.panel.impl;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionsPanel extends JPanel {
	
	private Logger logger = LoggerFactory.getLogger(ActionsPanel.class);
	
	private static final long serialVersionUID = 1L;
	
	private ActionsListener listener;
	
	public ActionsPanel(ActionsListener listener) {
		this.listener = listener;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setupSelectPowerConsumptionFileChooser();
	}
	
	private void setupSelectPowerConsumptionFileChooser() {
		JButton btnSelectPowerConsumptionFile = new JButton("Select Power Consumption File ...");
		btnSelectPowerConsumptionFile.setAlignmentX(Component.CENTER_ALIGNMENT);
		final JLabel powerConsumptionLabel = new JLabel("No file loaded", JLabel.CENTER);
		powerConsumptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSelectPowerConsumptionFile.setBounds(10, 2, 89, 23);
        btnSelectPowerConsumptionFile.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files (*csv)", "csv");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(ActionsPanel.this);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					List<Double> csvLoads = new ArrayList<Double>();
					BufferedReader br;
					try {
						br = new BufferedReader(new FileReader(file));
						String line = "";
			            while ((line = br.readLine()) != null) {
			            	// check if is a double value.
			            	if(Pattern.matches("([0-9]*)\\.([0-9]*)", line)){
			            		csvLoads.add(Double.parseDouble(line));
			            	}
			            }
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (NumberFormatException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					powerConsumptionLabel.setText("File uploaded succesfully with " + csvLoads.size() + " entries");
					logger.info("Csv Loads size: " + csvLoads.size());
					if(listener != null)
						listener.onChangePowerConsumptionMeasures(csvLoads);
				}
			}
			
        });
		
		this.add(btnSelectPowerConsumptionFile);
		this.add(powerConsumptionLabel);
	}
	
	
	public interface ActionsListener {
		void onChangePowerConsumptionMeasures(List<Double> measures);
	}
	

}
