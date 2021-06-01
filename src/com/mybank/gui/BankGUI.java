package com.mybank.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JEditorPane;
import javax.swing.JButton;
import java.io.IOException;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.mybank.data.DataSource;
import com.mybank.domain.Account;
import com.mybank.domain.Bank;
import com.mybank.domain.CheckingAccount;
import com.mybank.domain.Customer;
import com.mybank.domain.SavingsAccount;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;

public class BankGUI extends JFrame {
	
	private static final String DATA_FILE_PATH = "test.dat";
	
	private JPanel contentPane;
	
	public static void main(String[] args) {
		try {
    		DataSource data = new DataSource(DATA_FILE_PATH);
			data.loadData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		EventQueue.invokeLater(() -> {
			BankGUI frame = new BankGUI();
			frame.setVisible(true);
		});
	}

	/**
	 * Create the frame.
	 */
	public BankGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 341);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JComboBox<Customer> comboBox = new JComboBox<>();
		for (int i = 0; i < Bank.getNumberOfCustomers(); i++) {
			Customer customer = Bank.getCustomer(i);
			comboBox.addItem(customer);
		}
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setContentType("text/html");
		
		JScrollPane scroll = new JScrollPane(editorPane);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		JButton btnNewButton = new JButton("Show");
		btnNewButton.addActionListener(e -> {
			editorPane.setText(getCustomerInfo((Customer) comboBox.getSelectedItem(), comboBox.getSelectedIndex()));
		});
		
		JButton btnReport = new JButton("Report");
		btnReport.addActionListener(e -> {
			editorPane.setText(getReport());
		});
		
		JButton btnAbout = new JButton("About");
		btnAbout.addActionListener(e -> {  
			JOptionPane.showMessageDialog(this, getAbout(), "Information", JOptionPane.INFORMATION_MESSAGE);
		});
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JScrollPane scrollPane = new JScrollPane();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED))
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addComponent(scroll, GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE))
						.addComponent(comboBox, 0, 273, Short.MAX_VALUE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnReport, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnAbout, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
				.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnReport, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnAbout, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(scroll, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 29, Short.MAX_VALUE))
		);
		
		JLabel lblNewLabel = new JLabel("Choose a client name and press 'Show' button");
		panel.add(lblNewLabel);
		contentPane.setLayout(gl_contentPane);
	}
	
	private String getAbout() {
        //System.getProperties().list(System.out);
		StringBuilder sb = new StringBuilder();
		
		sb.append("Bank — Ãðèöåíêî Âëàä\n\n")
		.append("System info: \n")
		.append("Current Java version is: ").append(System.getProperty("java.version"))
		.append("\nOS: ").append(System.getProperty("os.name"))
		.append("\nArch: ").append(System.getProperty("os.arch"));
		
		return sb.toString();
	}
	
    private String getCustomerInfo(Customer customer, int id) {
    	StringBuilder sb = new StringBuilder();
    	
    	sb.append("<b>")
    	.append(customer.toString())
    	.append("</b>, customer #")
    	.append(id)
    	.append("<br>--------------------------<br>")
    	.append("Accounts:<br>");
    	for (int i = 0; i < customer.getNumberOfAccounts(); i++) {
    		Account account = customer.getAccount(i);
    		
    		sb.append("#").append(i)
    		.append(" - ")
    		.append("<b>")
    		.append(account instanceof CheckingAccount ? "Checking" : "Savings")
    		.append("</b>: ")
    		.append(account.getBalance())
    		.append("<br>");
    	}
    	
    	return sb.toString();
    }
    
    private String getReport() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("<span style=\"color:red;\">CUSTOMERS REPORT</span><br>")
          .append("<span style=\"color:green;\">================</span><br>");
		
		for (int i = 0; i < Bank.getNumberOfCustomers(); i++) {
			Customer customer = Bank.getCustomer(i);
			sb.append("<b>Customer:</b> ")
			  .append(customer.getLastName())
			  .append(", ")
			  .append(customer.getFirstName())
			  .append("<br>");
			
			for (int j = 0; j < customer.getNumberOfAccounts(); j++) {
		    	Account account = customer.getAccount(j);
		        String accountType = "Unknown Account Type";
		        
		        if (account instanceof SavingsAccount) {
		        	accountType = "Savings Account";
		        } else if (account instanceof CheckingAccount) {
		        	accountType = "Checking Account";
		        }

		        sb.append("<span style=\"color:blue;\">")
		          .append(accountType)
		          .append("</span>")
		          .append(": current balance is <b>$")
		          .append(String.valueOf(account.getBalance()))
		          .append("</b><br>");
			}
		}
		
		return sb.toString();
    }
}
