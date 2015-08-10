package warehouseOrderTrackingApplication;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class SwingAppGUI extends JFrame {

	private JFrame mainFrame;
	private JFrame frame2;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;
	private JTable custTable;
	
	public SwingAppGUI(){prepareGUI();}
	
	public void swing() {
		// TODO Auto-generated method stub
		
		SwingAppGUI sD = new SwingAppGUI();
		sD.showEvent();
	}
	
	private void createPage2()
	{
		frame2 = new JFrame("Printing Customer Order Details");
		frame2.setSize(800, 800);
		frame2.setLayout(new GridLayout(3,1));
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		headerLabel = new JLabel("", JLabel.CENTER);
		statusLabel = new JLabel("", JLabel.CENTER);
		statusLabel.setSize(400, 400);
		
		frame2.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent windowEvent)
			{
				System.exit(0);
			}
		});
		
		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		frame2.add(headerLabel);
		frame2.add(controlPanel);
		frame2.add(statusLabel);
		frame2.setVisible(true);
		showEventPage2();
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
	
	private void showEventPage2()
	{
		headerLabel.setText("Customer Orders");
		JButton displayCurrent = new JButton("View orders currently checked out");
		
		String[] columnNames = {"Customer Order ID", "Customer ID", "Order Status", "Is Checked Out","Order Total"};
		WarehouseJDBC jdbc = new WarehouseJDBC();
		ArrayList<CustomerOrder> custList = jdbc.returnCustOrder();
		int size = custList.size();
		Object[][] rowData = new Object[size][5];
		
		for (int i = 0; i < size; i++)
		{
			CustomerOrder c = custList.get(i);
			rowData[i][0] = c.getCustOrderID();
			rowData[i][1] = c.getCustID();
			rowData[i][2] = c.geteOrderStatus();
			rowData[i][3] = c.isCheckedOut();
			rowData[i][4] = c.getOrderTotal();
		}
		
		custTable = new JTable(rowData, columnNames);
		JScrollPane scrollPane = new JScrollPane(custTable);
		custTable.setFillsViewportHeight(true);
		getContentPane().add(scrollPane);
		displayCurrent.setActionCommand("CustomerOrder");
		displayCurrent.addActionListener(new BCL());
		controlPanel.add(displayCurrent);
		controlPanel.add(custTable);
		frame2.setVisible(true);
		
		
	}
	
	private class BCL implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent ae)
		{
			String command = ae.getActionCommand();
			switch (command) {
				case "Display": 
					mainFrame.setVisible(false);
					statusLabel.setText("Displaying Customer Orders and Purchase Orders.");
					createPage2();
					break;
				case "CustomerOrder":
					frame2.setVisible(true);
					CustomerOrder c = new CustomerOrder();
					//String result = c.printOrdersGUI();
					//statusLabel.setText(result);
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
