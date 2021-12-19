package elements;

/** This class is for trader's wallet, it stores some data and has some methods.
 * 
 * @author egecans
 *
 */
public class Wallet {

	private double dollars;
	private double coins;
	private double blockedDollars = 0;
	private double blockedCoins = 0;
	private static int invalidQueries = 0 ;
	private static int successfulOperations = 0;
	
	
	/** Construction with 2 parameters, it initializes these parameters in this constructor.
	 * 
	 * @param dollars dollars at first in the wallet
	 * @param coins   coins at first in the wallet
	 */
	public Wallet(double dollars, double coins) {
		this.dollars = dollars;
		this.coins = coins;	
	}

	/** adding dollars to the wallet
	 * 
	 * @param amount  amount of dollars added
	 */
	public void addDollars (double amount) {
		dollars += amount;
	}
	
	/** withdrawing dollars from the wallet 
	 *  it checks the wallet whether it has enough dollars or not
	 * @param amount amount of dollars withdrawing
	 */
	public void withdraw (double amount) {
		if (amount > dollars) {
			invalidQueries++;
		}
		else {
			dollars -= amount;
		}
	}
	
	/** adding coins to the wallet for rewarding
	 * 
	 * @param amount amount of coins added
	 */
	public void rewardCoins(double amount) {
		coins += amount;
	}
	
	/** blocking some dollars because of giving an order
	 * 
	 * @param amount amount of coins ordering
	 * @param price	 price of coins 
	 */
	public void addBlockedDollars(double amount, double price ) {
		blockedDollars += price*amount;
		dollars -= price*amount;
	}
	
	/** blocking some coins for selling order
	 * 	
	 * @param amount  amount of coins for selling
	 */
	public void addBlockedCoins (double amount) {
		blockedCoins += amount;
		coins -= amount;
	}
	
	/** this method is successful transactions. it decreases block dollars and adding coins from transaction.
	 * 
	 * @param amount amount of coins buying
	 * @param price  price of coins purchasing 
	 */
	public void successfulBuyingOperation(double amount, double price) {
		blockedDollars -= price*amount;
		coins += amount;
		successfulOperations++ ;
	}
	
	/** this method is successful transactions. it decreases block coins and adding dollars from transaction.
	 * 
	 * @param amount  amount of coins selling 
	 * @param earning the earning from selling, it is less than selling amount because of market fee
	 */
	public void successfulSellingOperation(double amount, double earning) {  
		blockedCoins -= amount;
		dollars += earning;
		successfulOperations++;
	}
	
	
	public double getTotalDollars() {
		return dollars + blockedDollars;
	}
	
	public double getTotalCoins() {
		return coins + blockedCoins;
	}


	public double getDollars() {
		return dollars;
	}


	public double getCoins() {
		return coins;
	}


	public static int getInvalidQueries() {
		return invalidQueries;
	}


	public static int getSuccessfulOperations() {
		return successfulOperations;
	}
	
	
	
	
	
}
