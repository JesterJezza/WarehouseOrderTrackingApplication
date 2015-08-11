package warehouseOrderTrackingApplication;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.BorderLayout;

import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

import java.awt.Color;

import javax.swing.border.LineBorder;

public class newGUI extends JFrame {
	private JTable tblPurchaseOrders;
	private JTable tblCustomerOrders;
	private JTable tblCheckOutOrder;
	private JTextField txtSupplierName;
	private JTextField txtSupplierID;
	private JTextField txtTotalPurchCost;
	private JScrollPane scrollPaneCustomer;
	private JScrollPane scrollPanePurchase;
	private ArrayList<PurchaseOrder> purchaseList;
	private ArrayList<CustomerOrder> customerList;
	
	public newGUI() {
		setTitle("Warehouse Order Tracking Application");
		getContentPane().setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(4, 12, 459, 154);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		JButton btnDisplayCustOrder = new JButton("Display Customer Orders");
		btnDisplayCustOrder.setBounds(6, 16, 447, 23);
		panel_1.add(btnDisplayCustOrder);
		scrollPaneCustomer = new JScrollPane();
		scrollPaneCustomer.setBounds(6, 47, 447, 100);
		panel_1.add(scrollPaneCustomer);
		tblCustomerOrders = new JTable();
		scrollPaneCustomer.setViewportView(tblCustomerOrders);
		btnDisplayCustOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WarehouseJDBC jdbc = new WarehouseJDBC();
				customerList = jdbc.returnCustOrder();
				String[] columnNames = {"Order ID", "Customer ID", "Delivery Address", "Order Status","isCheckedOut", "Order Total"};
				int size = customerList.size();
				Object[][] rowData = new Object[size][6];
				for (int i = 0; i < size; i++)
				{
					CustomerOrder c = customerList.get(i);
					rowData[i][0] = c.getCustOrderID();
					rowData[i][1] = c.getCustID();
					rowData[i][2] = c.getDeliveryAddress();
					rowData[i][3] = c.geteOrderStatus();
					rowData[i][4] = c.isCheckedOut();
					rowData[i][5] = c.getOrderTotal();
				}
				tblCustomerOrders = new JTable(rowData, columnNames);
				//tblCustomerOrders.set
				scrollPaneCustomer.setViewportView(tblCustomerOrders);
				tblCustomerOrders.repaint();
				
			}
		});
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setBounds(4, 177, 459, 157);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnDisplayPurchaseOrders = new JButton("Display Purchase Orders");
		btnDisplayPurchaseOrders.setBounds(6, 16, 447, 23);
		panel_2.add(btnDisplayPurchaseOrders);
		scrollPanePurchase = new JScrollPane();
		scrollPanePurchase.setBounds(6, 50, 447, 100);
		panel_2.add(scrollPanePurchase);
		tblPurchaseOrders = new JTable();
		scrollPanePurchase.setViewportView(tblPurchaseOrders);
		btnDisplayPurchaseOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WarehouseJDBC jdbc = new WarehouseJDBC();
				purchaseList = jdbc.returnPurchOrder();
				String[] columnNames = {"Purchase Order ID", "Supplier ID", "Supplier Name", "Order Total"};
				int size = purchaseList.size();
				Object[][] rowData = new Object[size][4];
				for (int i=0;i<size;i++)
				{
					PurchaseOrder p = purchaseList.get(i);
					rowData[i][0] = p.getPurOrderID();
					rowData[i][1] = p.getSupplierID();
					rowData[i][2] = p.getSupplierName();
					rowData[i][3] = p.getOrderTotal();
				}
				tblPurchaseOrders = new JTable(rowData, columnNames);
				scrollPanePurchase.setViewportView(tblPurchaseOrders);
				tblPurchaseOrders.repaint();
			}
		});
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(466, 397, 419, 154);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnAddStockDelivery = new JButton("Add Stock Delivery");
		btnAddStockDelivery.setBounds(6, 109, 407, 38);
		panel.add(btnAddStockDelivery);
		
		JLabel lblSupplierName = new JLabel("Supplier Name:");
		lblSupplierName.setBounds(6, 16, 96, 20);
		panel.add(lblSupplierName);
		
		txtSupplierName = new JTextField();
		txtSupplierName.setBounds(103, 16, 301, 20);
		panel.add(txtSupplierName);
		txtSupplierName.setColumns(10);
		
		JLabel lblSupplierId = new JLabel("Supplier ID:");
		lblSupplierId.setBounds(6, 47, 96, 20);
		panel.add(lblSupplierId);
		
		txtSupplierID = new JTextField();
		txtSupplierID.setBounds(103, 47, 301, 20);
		panel.add(txtSupplierID);
		txtSupplierID.setColumns(10);
		
		JLabel lblTotalCost = new JLabel("Total cost:");
		lblTotalCost.setBounds(6, 78, 96, 20);
		panel.add(lblTotalCost);
		
		txtTotalPurchCost = new JTextField();
		txtTotalPurchCost.setBounds(103, 78, 301, 20);
		panel.add(txtTotalPurchCost);
		txtTotalPurchCost.setColumns(10);
		btnAddStockDelivery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton btnCheckoutAnOrder = new JButton("Checkout Selected Order");
		btnCheckoutAnOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = tblCustomerOrders.getSelectedRow();
				if (index == -1)
				{
					JOptionPane.showMessageDialog(btnCheckoutAnOrder, "Please select an Order from the Customer Order table first!");
				}
				//System.out.println(index);
				CustomerOrder c = customerList.get(index);
				//System.out.println(c.toString());
				c.checkOutOrder(c.getOrderItemList(), c.getCustOrderID());
			}
		});
		btnCheckoutAnOrder.setBounds(485, 12, 400, 57);
		getContentPane().add(btnCheckoutAnOrder);;
		
		JScrollPane scrollPaneCheckOut = new JScrollPane();
		scrollPaneCheckOut.setBounds(485, 79, 400, 204);
		getContentPane().add(scrollPaneCheckOut);
		
		tblCheckOutOrder = new JTable();
		scrollPaneCheckOut.setViewportView(tblCheckOutOrder);
		
		getContentPane().setVisible(true);
	}
}
