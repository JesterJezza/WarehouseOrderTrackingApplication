package warehouseOrderTrackingApplication;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class newGUI extends JFrame {
	private JTable tblPurchaseOrders;
	private JTable tblCustomerOrders;
	private JTable tblCheckOutOrder;
	private JTextField txtSupplierName;
	private JTextField txtSupplierID;
	private JTextField txtTotalPurchCost;
	private JScrollPane scrollPaneCustomer;
	
	public newGUI() {
		setTitle("Warehouse Order Tracking Application");
		scrollPaneCustomer = new JScrollPane();
		JButton btnDisplayCustOrder = new JButton("Display Customer Orders");
		btnDisplayCustOrder.setBounds(10, 11, 400, 23);
		btnDisplayCustOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WarehouseJDBC jdbc = new WarehouseJDBC();
				ArrayList<CustomerOrder> customerList = jdbc.returnCustOrder();
				String[] columnNames = {"Order ID", "Customer ID", "Delivery Address", "Order Status", "Order Total (£)"};
				int size = customerList.size();
				Object[][] rowData = new Object[size][5];
				for (int i = 0; i < size; i++)
				{
					CustomerOrder c = customerList.get(i);
					rowData[i][0] = c.getCustOrderID();
					rowData[i][1] = c.getCustID();
					rowData[i][2] = c.geteOrderStatus();
					rowData[i][3] = c.isCheckedOut();
					rowData[i][4] = c.getOrderTotal();
				}
				tblCustomerOrders = new JTable(rowData, columnNames);
				//tblCustomerOrders.set
				scrollPaneCustomer.setViewportView(tblCustomerOrders);
				tblCustomerOrders.repaint();
				
			}
		});
		getContentPane().setLayout(null);
		getContentPane().add(btnDisplayCustOrder);
		
		JButton btnDisplayPurchaseOrders = new JButton("Display Purchase Orders");
		btnDisplayPurchaseOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDisplayPurchaseOrders.setBounds(10, 149, 400, 23);
		getContentPane().add(btnDisplayPurchaseOrders);
		
		JButton btnAddStockDelivery = new JButton("Add Stock Delivery");
		btnAddStockDelivery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAddStockDelivery.setBounds(476, 494, 398, 57);
		getContentPane().add(btnAddStockDelivery);
		
		JButton btnCheckoutAnOrder = new JButton("Checkout Selected Order");
		btnCheckoutAnOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCheckoutAnOrder.setBounds(467, 11, 398, 57);
		getContentPane().add(btnCheckoutAnOrder);
		
		JScrollPane scrollPanePurchase = new JScrollPane();
		scrollPanePurchase.setBounds(10, 183, 400, 100);
		getContentPane().add(scrollPanePurchase);
		
		tblPurchaseOrders = new JTable();
		scrollPanePurchase.setViewportView(tblPurchaseOrders);
		
		//JScrollPane scrollPaneCustomer = new JScrollPane();
		scrollPaneCustomer.setBounds(10, 45, 400, 100);
		tblCustomerOrders = new JTable();
		scrollPaneCustomer.setViewportView(tblCustomerOrders);
		getContentPane().add(scrollPaneCustomer);
		
		JScrollPane scrollPaneCheckOut = new JScrollPane();
		scrollPaneCheckOut.setBounds(467, 79, 400, 200);
		getContentPane().add(scrollPaneCheckOut);
		
		tblCheckOutOrder = new JTable();
		scrollPaneCheckOut.setViewportView(tblCheckOutOrder);
		
		JLabel lblSupplierName = new JLabel("Supplier Name:");
		lblSupplierName.setBounds(467, 303, 96, 20);
		getContentPane().add(lblSupplierName);
		
		txtSupplierName = new JTextField();
		txtSupplierName.setBounds(564, 303, 301, 20);
		getContentPane().add(txtSupplierName);
		txtSupplierName.setColumns(10);
		
		JLabel lblSupplierId = new JLabel("Supplier ID:");
		lblSupplierId.setBounds(467, 334, 96, 20);
		getContentPane().add(lblSupplierId);
		
		txtSupplierID = new JTextField();
		txtSupplierID.setBounds(564, 334, 301, 20);
		getContentPane().add(txtSupplierID);
		txtSupplierID.setColumns(10);
		
		JLabel lblTotalCost = new JLabel("Total cost:");
		lblTotalCost.setBounds(467, 365, 96, 20);
		getContentPane().add(lblTotalCost);
		
		txtTotalPurchCost = new JTextField();
		txtTotalPurchCost.setBounds(564, 365, 301, 20);
		getContentPane().add(txtTotalPurchCost);
		txtTotalPurchCost.setColumns(10);
		
		getContentPane().setVisible(true);
	}
}
