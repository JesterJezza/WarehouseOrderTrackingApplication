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
import javax.swing.table.DefaultTableModel;

import jdk.nashorn.internal.scripts.JO;

public class newGUI extends JFrame {
	private JTable tblPurchaseOrders;
	private JTable tblCustomerOrders;
	private JTable tblCheckOutOrder;
	private JTextField txtSupplierName;
	private JTextField txtSupplierID;
	private JTextField txtTotalPurchCost;
	private JScrollPane scrollPaneCustomer;
	private JScrollPane scrollPanePurchase;
	private JScrollPane scrollPaneCurrentOrder;
	private ArrayList<PurchaseOrder> purchaseList;
	private ArrayList<CustomerOrder> customerList;
	private ArrayList<CustomerOrder> checkOutList;
	private JTable tblCurrentOrder;
	private JLabel lblOrderId;
	private JLabel lblCustomerId;
	private JLabel lblDeliveryAddress;
	private JLabel lblOrderTotal;
	private int currentOrderID;
	private CustomerOrder currentOrder = null;
	public PurchaseOrder addPurch = null;
	public ArrayList<OrderItem> addPurchItem = new ArrayList<OrderItem>();
	private JTextField txtNumberOfItems;
	private int items;

	public newGUI() {

		setTitle("Warehouse Order Tracking Application");
		getContentPane().setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "View Customer Orders", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(4, 12, 1142, 128);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		scrollPaneCustomer = new JScrollPane();
		scrollPaneCustomer.setBounds(10, 17, 1022, 100);
		panel_1.add(scrollPaneCustomer);
		tblCustomerOrders = new JTable();
		scrollPaneCustomer.setViewportView(tblCustomerOrders);
		JButton btnDisplayCustOrder = new JButton("<html><center>Display<br>Customer<br>Orders");
		btnDisplayCustOrder.setBackground(Color.white);
		btnDisplayCustOrder.setBounds(1042, 40, 90, 51);
		panel_1.add(btnDisplayCustOrder);
		
		btnDisplayCustOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WarehouseJDBC jdbc = new WarehouseJDBC();
				jdbc = new WarehouseJDBC();
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
		panel_2.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "View Purchase Orders", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(4, 144, 1142, 128);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		scrollPanePurchase = new JScrollPane();
		scrollPanePurchase.setBounds(10, 17, 1022, 100);
		panel_2.add(scrollPanePurchase);
		tblPurchaseOrders = new JTable();
		scrollPanePurchase.setViewportView(tblPurchaseOrders);
		
		JButton btnDisplayPurchaseOrders = new JButton("<html><center>Display<br>Purchase<br>Orders");
		btnDisplayPurchaseOrders.setBackground(Color.white);
		btnDisplayPurchaseOrders.setBounds(1042, 40, 90, 51);
		panel_2.add(btnDisplayPurchaseOrders);
		
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
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Add Stock Delivery", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(780, 276, 366, 475);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnAddStockDelivery = new JButton("Add Stock Delivery");
		btnAddStockDelivery.setBackground(Color.white);
		btnAddStockDelivery.setBounds(10, 176, 346, 31);
		panel.add(btnAddStockDelivery);
		
		JLabel lblSupplierName = new JLabel("Supplier Name:");
		lblSupplierName.setBounds(31, 16, 83, 20);
		panel.add(lblSupplierName);
		
		txtSupplierName = new JTextField();
		txtSupplierName.setBounds(116, 16, 240, 20);
		panel.add(txtSupplierName);
		txtSupplierName.setColumns(10);
		
		JLabel lblSupplierId = new JLabel("Supplier ID:");
		lblSupplierId.setBounds(48, 47, 66, 20);
		panel.add(lblSupplierId);
		
		txtSupplierID = new JTextField();
		txtSupplierID.setBounds(116, 47, 240, 20);
		panel.add(txtSupplierID);
		txtSupplierID.setColumns(10);
		
		JLabel lblTotalCost = new JLabel("Total cost:");
		lblTotalCost.setBounds(53, 78, 61, 20);
		panel.add(lblTotalCost);
		
		txtTotalPurchCost = new JTextField();
		txtTotalPurchCost.setBounds(116, 78, 240, 20);
		panel.add(txtTotalPurchCost);
		txtTotalPurchCost.setColumns(10);
		
		JLabel lblNumberOfItems = new JLabel("No. of Items:");
		lblNumberOfItems.setBounds(40, 112, 74, 14);
		panel.add(lblNumberOfItems);
		
		txtNumberOfItems = new JTextField();
		txtNumberOfItems.setBounds(116, 109, 240, 20);
		panel.add(txtNumberOfItems);
		txtNumberOfItems.setColumns(10);
		
		JLabel lblPhoto = new JLabel("<html><img src='https://upload.wikimedia.org/wikipedia/en/9/90/German_garden_gnome_cropped.jpg' height='234' width='346'");
		lblPhoto.setBounds(10, 230, 346, 234);
		panel.add(lblPhoto);
		btnAddStockDelivery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println(txtSupplierID.getText());
				//System.out.println(addPurch.getSupplierID());
				addPurch = new PurchaseOrder();
				addPurch.setSupplierID(Integer.parseInt(txtSupplierID.getText()));
				addPurch.setSupplierName(txtSupplierName.getText());
				addPurch.setOrderTotal(Float.parseFloat(txtTotalPurchCost.getText()));
				items = Integer.parseInt(txtNumberOfItems.getText());
				for (int i = 0;i<items;i++)
				{
					page2();
				}
			}
		});
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "View Order Backlog", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(4, 276, 766, 475);
		getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		JButton btnCheckoutAnOrder = new JButton("Checkout Selected Order");
		btnCheckoutAnOrder.setBackground(Color.white);
		btnCheckoutAnOrder.setBounds(343, 16, 184, 38);
		panel_3.add(btnCheckoutAnOrder);
		
		JScrollPane scrollPaneCheckOut = new JScrollPane();
		scrollPaneCheckOut.setBounds(6, 65, 753, 204);
		panel_3.add(scrollPaneCheckOut);
		
		tblCheckOutOrder = new JTable();
		scrollPaneCheckOut.setViewportView(tblCheckOutOrder);
		JButton btnGetBacklog = new JButton("View Order Backlog");
		btnGetBacklog.setBackground(Color.white);
		btnGetBacklog.setBackground(Color.white);
		
		
		ActionListener backLogListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				WarehouseJDBC jdbc = new WarehouseJDBC();
				checkOutList = jdbc.getBacklog();
				String[] columnNames = {"Order ID", "Customer ID", "Delivery Address", "Number of Items", "Order Total"};
				int size = checkOutList.size();
				Object[][] rowData = new Object[size][5];
				for (int i = 0; i<size; i++)
				{
					CustomerOrder c = checkOutList.get(i);
					rowData[i][0] = c.getCustOrderID();
					rowData[i][1] = c.getCustID();
					rowData[i][2] = c.getDeliveryAddress();
					rowData[i][3] = c.getOrderItemList().size();
					rowData[i][4] = c.getOrderTotal();
				}
				tblCheckOutOrder = new JTable(rowData, columnNames);
				scrollPaneCheckOut.setViewportView(tblCheckOutOrder);
				tblCheckOutOrder.repaint();
			}
		};
		btnGetBacklog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backLogListener.actionPerformed(e);	
			}
		});
		btnGetBacklog.setBounds(6, 16, 184, 38);
		panel_3.add(btnGetBacklog);
		
		JLabel lblCurrentOrder = new JLabel("Current Order");
		lblCurrentOrder.setBounds(6, 280, 160, 14);
		panel_3.add(lblCurrentOrder);
		
		lblOrderId = new JLabel("Order ID: ");
		lblOrderId.setBounds(6, 291, 160, 14);
		panel_3.add(lblOrderId);
		
		lblCustomerId = new JLabel("Customer ID: ");
		lblCustomerId.setBounds(173, 291, 160, 14);
		panel_3.add(lblCustomerId);
		
		lblDeliveryAddress = new JLabel("Delivery Address: ");
		lblDeliveryAddress.setBounds(6, 305, 521, 14);
		panel_3.add(lblDeliveryAddress);
		
		lblOrderTotal = new JLabel("Order Total: ");
		lblOrderTotal.setBounds(343, 291, 184, 14);
		panel_3.add(lblOrderTotal);
		
		JScrollPane scrollPaneCurrentOrder = new JScrollPane();
		scrollPaneCurrentOrder.setBounds(6, 322, 753, 101);
		panel_3.add(scrollPaneCurrentOrder);
		
		tblCurrentOrder = new JTable();
		scrollPaneCurrentOrder.setViewportView(tblCurrentOrder);
		
		JButton btnOrderCompleted = new JButton("<html><font color=#1A661A>Order Picked</font>");
		btnOrderCompleted.setBackground(Color.white);
		btnOrderCompleted.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentOrder == null)
				{
					JOptionPane.showMessageDialog(btnOrderCompleted, "Please check out an order first!");
				}
				else
				{
				currentOrder.updateStockLevel(currentOrder.getOrderItemList(), currentOrderID);
				currentOrder.updateOrderStatus(currentOrder.getOrderItemList(), currentOrderID);
				DefaultTableModel dataModel = new DefaultTableModel();
				tblCurrentOrder.setModel(dataModel);
				currentOrder = null;
				}
			}
		});
		btnOrderCompleted.setBounds(6, 434, 184, 23);
		panel_3.add(btnOrderCompleted);
		
		JLabel lblPickRoute = new JLabel("Pick Route: ");
		lblPickRoute.setBounds(200, 434, 327, 23);
		panel_3.add(lblPickRoute);
		btnCheckoutAnOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//METHOD FOR CHECKING OUT AN ORDER
				int index = tblCheckOutOrder.getSelectedRow();
				if (index == -1)
				{
					JOptionPane.showMessageDialog(btnCheckoutAnOrder, "Please select an Order from the table below!");
				}
				else
				{
				//System.out.println(index);
				CustomerOrder c = checkOutList.get(index);
				currentOrder = checkOutList.get(index);
				//System.out.println(c.toString());
				currentOrderID = c.getCustOrderID();
				c.checkOutOrder(c.getOrderItemList(), c.getCustOrderID());
				backLogListener.actionPerformed(e);
				lblOrderId.setText("Order ID: "+String.valueOf(c.getCustOrderID()));
				lblCustomerId.setText("Customer ID: "+String.valueOf(c.getCustID()));
				lblOrderTotal.setText("Order Total: £"+String.valueOf(c.getOrderTotal()));
				lblDeliveryAddress.setText("Delivery Address: "+c.getDeliveryAddress());
				String[] columnNames = {"Item ID", "Item Quantity", "Item Location", "Unit Cost", "Total Item Cost"};
				int size = c.getOrderItemList().size();
				Object[][] rowData = new Object[size][5];
				
				for (int i = 0; i<size; i++)
				{
					WarehouseJDBC jdbc = new WarehouseJDBC();
					
					rowData[i][0] = c.getOrderItemList().get(i).getItemID();
					rowData[i][1] = c.getOrderItemList().get(i).getOrderItemQuantity();
					rowData[i][2] = jdbc.getOrderLocation(c.getOrderItemList().get(i).getItemID());
					rowData[i][3] = c.getOrderItemList().get(i).getOrderItemCost();
					rowData[i][4] = c.getOrderItemList().get(i).getTotalItemCost();
				}
				TSAlgorithm al = new TSAlgorithm();
				lblPickRoute.setText("<html><font color=#FF0000>"+al.algorithmGUI(c.getOrderItemList())+"</font>");
				tblCurrentOrder = new JTable(rowData, columnNames);
				scrollPaneCurrentOrder.setViewportView(tblCurrentOrder);
				tblCurrentOrder.repaint();
				}
				
			}
		});;
		
		getContentPane().setVisible(true);
	}
	
	private void page2()
	{
		JFrame frame2 = new JFrame();
		frame2.setTitle("Purchase Order Item");
		frame2.setSize(350, 500);
		JLabel lblItemID = new JLabel("Enter Item ID:", JLabel.CENTER);
		lblItemID.setBounds(50,50,100,20);
		JLabel lblQuantity = new JLabel("Enter quantity:", JLabel.CENTER);
		lblQuantity.setBounds(50,72,100,20);
		JLabel lblUnitCost = new JLabel("Enter Unit Cost:", JLabel.CENTER);
		lblUnitCost.setBounds(50,94,100,20);
		JTextField txtItemID = new JTextField("", JTextField.CENTER);
		txtItemID.setBounds(150,50,80,20);
		JTextField txtQuantity = new JTextField("", JTextField.CENTER);
		txtQuantity.setBounds(150,72,80,20);
		JTextField txtUnitCost = new JTextField("", JTextField.CENTER);
		txtUnitCost.setBounds(150,94,80,20);
		JButton btnAddPurOrderItem = new JButton("Submit");
		btnAddPurOrderItem.setBounds(130,120,100,20);
		btnAddPurOrderItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				OrderItem i = new OrderItem();
				i.setCustomerID(addPurch.getSupplierID());
				i.setOrderID(0);
				i.setItemID(Integer.parseInt(txtItemID.getText()));
				i.setOrderItemQuantity(Integer.parseInt(txtQuantity.getText()));
				i.setOrderItemCost(Float.parseFloat(txtUnitCost.getText()));
				i.setTotalItemCost(Float.parseFloat(String.valueOf((Integer.parseInt(txtQuantity.getText())*Float.parseFloat(txtUnitCost.getText())))));
				//System.out.println(i.getTotalItemCost());
				addPurchItem.add(i);
				
				if (addPurchItem.size() == items)
				{
					addPurch.setOrderItemList(addPurchItem);
					WarehouseJDBC jdbc = new WarehouseJDBC();
					jdbc.createPurchaseOrderDB(addPurch);
					addPurchItem = null;
				}
				frame2.dispose();
			}
		});

		frame2.getContentPane().add(lblItemID);
		frame2.getContentPane().add(lblQuantity);
		frame2.getContentPane().add(lblUnitCost);
		frame2.getContentPane().add(txtUnitCost);
		frame2.getContentPane().add(txtQuantity);
		frame2.getContentPane().add(txtItemID);
		frame2.getContentPane().add(btnAddPurOrderItem);
		frame2.getContentPane().setLayout(null);
		frame2.setVisible(true);
	}
}
