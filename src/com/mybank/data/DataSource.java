package com.mybank.data;

import com.mybank.domain.Bank;
import com.mybank.domain.CheckingAccount;
import com.mybank.domain.Customer;
import com.mybank.domain.SavingsAccount;

import java.io.File;
import java.io.IOException;

import java.util.Scanner;

public class DataSource {

    private File dataFile;

    public DataSource(String dataFilePath) {
        this.dataFile = new File(dataFilePath);
    }

    public void loadData() throws IOException {
        try (Scanner input = new Scanner(dataFile)) {
	        int numOfCustomers = input.nextInt();
	        
	        for (int idx = 0; idx < numOfCustomers; idx++) {
	            String firstName = input.next();
	            String lastName = input.next();
	            
	            Bank.addCustomer(firstName, lastName);
	            
	            Customer customer = Bank.getCustomer(Bank.getNumberOfCustomers() - 1);
	            
	            int numOfAccounts = input.nextInt();
	            for (int i = 0; i < numOfAccounts; i++) {
	                char accountType = input.next().charAt(0);
	                switch (accountType) {
	                    case 'S': { // Savings account
	                        float initBalance = Float.parseFloat(input.next());
	                        float interestRate = Float.parseFloat(input.next());
	                        
	                        customer.addAccount(new SavingsAccount(initBalance, interestRate));
	                        break;
	                    }
	                    case 'C': { // Checking account
	                        float initBalance = Float.parseFloat(input.next());
	                        float overdraftProtection = Float.parseFloat(input.next());
	                        
	                        customer.addAccount(new CheckingAccount(initBalance, overdraftProtection));
	                        break;
	                    }
	                }
	            }
	        }
    	}
    }
}
