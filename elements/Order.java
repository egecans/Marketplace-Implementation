package elements;

import java.util.ArrayList;

/** This is an abstract class that has 2 children ( BuyingOrder and SellingOrder)
 * 
 * @author egecans
 *
 */
public abstract class Order{
	
	protected double amount;
	protected double price;
	protected int traderID;
	private static int invalidQueries = 0 ;
	
	
	/** Constructor with 3 parameters, it initializes these parameters in this constructor.
	 * 
	 * @param traderID  ID of trader
	 * @param amount    amount of order
	 * @param price		price of order
	 */
	public Order (int traderID, double amount, double price) {
		
		this.traderID=traderID;
		this.amount = amount;
		this.price = price;
	}
	
	/** This is an abstract method, it changes in child classes.
	 * 
	 * @param trader  Trader that we check whether his action valid or not.
	 * @return boolean
	 */
	public abstract boolean checkvalidty(Trader trader);
	
	
	/** it increases invalidQueries by one
	 * 
	 */
	public void invalidQuery() {
		invalidQueries++;
	}
	
	
	public int getTraderID() {
		return traderID;
	}

	public double getAmount() {
		return amount;
	}

	public double getPrice() {
		return price;
	}


	public static int getInvalidQueries() {
		return invalidQueries;
	}
	
	
	
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

