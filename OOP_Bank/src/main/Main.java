package main;

import bankServices.Account;
import bankServices.Bank;
import bankServices.InvalidCode;
import bankServices.InvalidValue;

public class Main {
    public static void main(String[] args) {
	Bank b1 = new Bank("Uncle-$crooge");
	int c1 = b1.createAccount("John", 5, 500.5F);
	int c2 = b1.createAccount("Mary", 10, 1000.F);
	int c3 = b1.createAccount("John", 20, 800.F);
	int c4 = b1.createAccount("Paul", 30, 252.4F);
	Account a1 = null, a3 = null;
	try {
	    b1.deposit(c1, 7, 360.0F);
	    b1.deposit(c4, 35, 270.0F);
	    b1.withdraw(c3, 28, 350.0F);
	    b1.withdraw(c2, 19, 350.0F);
	    b1.withdraw(c3, 41, 158.0F);
	    b1.transfer(c1, c3, 8, 400.0F);
	    a1 = b1.getAccount(c1);
	    a3 = b1.deleteAccount(c3, 50);
	} catch (InvalidCode ic) {
	    ic.printStackTrace();
	} catch (InvalidValue iv) {
	    iv.printStackTrace();
	}

	System.out.println("total deposit in the " + b1.getName() + " bank: " + b1.getTotalDeposit());
	System.out.println("accounts with balance higher than 500: " + b1.getPerCentHigher(500) + " %");
	System.out.println("accounts with balance in range 500..700 :");
	for (Account a : b1.getAccountsByBalance(500, 700))
	    System.out.println(a);
    }

}
