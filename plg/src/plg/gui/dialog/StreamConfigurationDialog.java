package plg.gui.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import plg.gui.config.ConfigurationSet;
import plg.gui.controller.ApplicationController;
import plg.stream.configuration.StreamConfiguration;

/**
 * This class contains the dialog for the configuration of the parameters for
 * the generation of the stream
 *
 * @author Andrea Burattin
 */
public class StreamConfigurationDialog extends GeneralDialog {

	private static final long serialVersionUID = 4780160624995326441L;
	
	protected StreamConfiguration currentConfiguration = null;
	
	protected JSpinner portSpinner = null;
	protected JSpinner parallelInstancesSpinner = null;
	protected JSpinner timeFractionSpinner = null;
	protected JButton okButton = null;

	/**
	 * This constructor can be used to subclass the dialog
	 * 
	 * @param owner the owner of the dialog
	 * @param title the title of the dialog
	 * @param help a short text describing the dialog content
	 * @param configuration the configuration set to use for the dialog
	 */
	protected StreamConfigurationDialog(JFrame owner, String title, String help, ConfigurationSet configuration) {
		super(owner, title, help, configuration);
		
		portSpinner = new JSpinner(new SpinnerNumberModel(1234, 1, 9999, 1));
		parallelInstancesSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 30, 1));
		timeFractionSpinner = new JSpinner(new SpinnerNumberModel(0.5, 0, 10, 0.01));
		
		portSpinner.setEditor(new JSpinner.NumberEditor(portSpinner, "#"));
		
		bodyPanel.add(prepareFieldLabel("Network port"));
		bodyPanel.add(portSpinner);
		insertBodySeparator(10);
		
		bodyPanel.add(prepareFieldLabel("Maximum number of parallel instances"));
		bodyPanel.add(parallelInstancesSpinner);
		bodyPanel.add(prepareFieldLabel("Time fraction before new trace"));
		bodyPanel.add(timeFractionSpinner);

		bodyPanel.add(prepareFieldLabel(""));
		bodyPanel.add(Box.createVerticalGlue());
		
		// insert footer buttons
		okButton = new JButton("OK");
		
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentConfiguration = new StreamConfiguration();
				currentConfiguration.servicePort = (int) portSpinner.getValue();
				currentConfiguration.maximumParallelInstances = (int) parallelInstancesSpinner.getValue();
				currentConfiguration.timeFractionBeforeNewTrace = (double) timeFractionSpinner.getValue();
				returnedValue = RETURNED_VALUES.SUCCESS;
				StreamConfigurationDialog.this.dispose();
			}
		});
		
		addFooterButton(okButton, true);
		
		// layout everything
		layoutBody();
	}
	
	/**
	 * Dialog constructor
	 * 
	 * @param owner the frame owning the dialog
	 */
	public StreamConfigurationDialog(JFrame owner) {
		this(owner,
			"Stream Configuration",
			"Configuration of the stream parameters.",
			ApplicationController.instance().getConfiguration(NewProcessDialog.class.getCanonicalName()));
	}
	
	/**
	 * This method returns the configuration set up by the user
	 * 
	 * @return the configuration set
	 */
	public StreamConfiguration getConfiguredValues() {
		return currentConfiguration;
	}
}
