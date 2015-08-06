package warehouseOrderTrackingApplication;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SwingAppGUI extends JFrame {

	private JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;
	
	public SwingAppGUI(){prepareGUI();}
	
	public void swing() {
		// TODO Auto-generated method stub
		
		SwingAppGUI sD = new SwingAppGUI();
		sD.showEvent();
	}
	
	private void prepareGUI() 
	{
		mainFrame = new JFrame("NBGardens Warehouse Order Tracking Application");
		mainFrame.setSize(800, 800);
		mainFrame.setLayout(new GridLayout(3,1));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		headerLabel = new JLabel("",JLabel.CENTER);
		statusLabel = new JLabel("",JLabel.CENTER);
		statusLabel.setSize(550, 300);
		
		mainFrame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent windowEvent)
			{
				System.exit(0);
			}
		});
		
		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		mainFrame.add(headerLabel);
		mainFrame.add(controlPanel);
		mainFrame.add(statusLabel);
		mainFrame.setVisible(true);
	}
	
	private void showEvent()
	{
		headerLabel.setText("Press Button");
		JButton orderButton = new JButton("Display Orders");
		JButton submitButton = new JButton("Submit");
		JButton cancelButton = new JButton("Cancel");
		
		orderButton.setActionCommand("Display");
		submitButton.setActionCommand("Submit");
		cancelButton.setActionCommand("Cancel");
		
		orderButton.addActionListener(new BCL());
		submitButton.addActionListener(new BCL());
		cancelButton.addActionListener(new BCL());
		
		controlPanel.add(orderButton);
		controlPanel.add(submitButton);
		controlPanel.add(cancelButton);
		mainFrame.setVisible(true);
	}
	
	private class BCL implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent ae)
		{
			String command = ae.getActionCommand();
			switch (command) {
				case "Display": 
					//CustomerOrder cust = new CustomerOrder();
					statusLabel.setText("Displaying Customer Orders and Purchase Orders.");
					
					break;
				case "Submit":
					statusLabel.setText("Submitted!");
					break;
				case "Cancel":
					statusLabel.setText("Cancel not possible");
					break;
			}
		}
	}

}
