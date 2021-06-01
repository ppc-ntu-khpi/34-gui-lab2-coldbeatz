package com.mybank.gui;

import com.mybank.data.DataSource;
import com.mybank.domain.Account;
import com.mybank.domain.Bank;
import com.mybank.domain.CheckingAccount;
import com.mybank.domain.Customer;
import com.mybank.domain.SavingsAccount;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Alexander 'Taurus' Babich
 */
public class SWINGDemo {
	
	private static final String DATA_FILE_PATH = "test.dat";
	
    private final JEditorPane log = new JEditorPane("text/html", "");
    private JScrollPane scroll = new JScrollPane(log);
    
    private final JButton show = new JButton("Show");
    private final JButton report = new JButton("Report");
    
    private final JComboBox<String> clients = new JComboBox<>();
    
    public SWINGDemo() {
    	scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    	log.setPreferredSize(new Dimension(250, 150));
    	
        for (int i = 0; i < Bank.getNumberOfCustomers(); i++) {
            clients.addItem(Bank.getCustomer(i).getLastName() + ", " + Bank.getCustomer(i).getFirstName());
        }   
    }
    
    private String getCustomerInfo(Customer customer) {
    	Account account = customer.getAccount(0);
    	
    	StringBuilder sb = new StringBuilder();
    	sb.append("<br>&nbsp;<b><span style=\"font-size:1.7em;\">")
    	  .append(customer.getFirstName())
    	  .append(" ")
    	  .append(customer.getLastName())
    	  .append("<br>&nbsp;<b>Acc Type: </b>")
    	  .append(account instanceof CheckingAccount ? "Checking" : "Savings")
    	  .append("<br>&nbsp;<b>Balance: <span style=\"color:red;\">$")
    	  .append(account.getBalance())
    	  .append("</span></b>");
    	
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
    
    private void launchFrame() {
        JFrame frame = new JFrame("MyBank clients");
        frame.setLayout(new BorderLayout());
        
        JPanel cpane = new JPanel();
        cpane.setLayout(new GridLayout(1, 2));
        
        cpane.add(clients);
        cpane.add(show);
        cpane.add(report);
        
        frame.add(cpane, BorderLayout.NORTH);
        frame.add(scroll, BorderLayout.CENTER);
        
        report.addActionListener(e -> log.setText(getReport()));
        show.addActionListener(e -> {
        	Customer current = Bank.getCustomer(clients.getSelectedIndex());
            log.setText(getCustomerInfo(current));
        });
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setResizable(false);
        frame.setVisible(true);        
    }
    
    public static void main(String[] args) {
        Bank.addCustomer("John", "Doe");
        Bank.addCustomer("Fox", "Mulder");
        Bank.addCustomer("Dana", "Scully");
        
        Bank.getCustomer(0).addAccount(new CheckingAccount(2000));
        Bank.getCustomer(1).addAccount(new SavingsAccount(1000, 3));
        Bank.getCustomer(2).addAccount(new CheckingAccount(1000, 500));
        
        try {
     		// Выгрузка из файла
     		DataSource data = new DataSource(DATA_FILE_PATH);
 			data.loadData();
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
        
        SWINGDemo demo = new SWINGDemo();        
        demo.launchFrame();
    }
}