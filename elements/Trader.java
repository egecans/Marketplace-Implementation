package elements;

/** this class is for traders in the market
 * 
 * @author egecans
 *
 */
public class Trader {
	
	private int id;
	private Wallet wallet;
	public static int numberOfUsers = 0;
	
	/** Construction with 2 parameters, it initializes wallet with these parameters in this constructor 
	 *  and also increasing number of users by calling this constructor
	 * @param dollars  dollars in traders wallet
	 * @param coins  coins in traders wallet
	 */
	public Trader(double dollars, double coins) {
		this.wallet = new Wallet (dollars, coins);
		this.id = numberOfUsers;
		numberOfUsers++;
	}
	
	/** this method is for buying operation
	 *  it calls the successfulbuyingoperation method from the trader's wallet 
	 * @param amount  amount of coins that ordered
	 * @param price   price of coins 
	 * @param market  market at which the trader is shopping
	 * @return nothing significant 
	 */
	public int buy(double amount, double price, Market market) {
		wallet.successfulBuyingOperation(amount, price);
		return 0;
	}
	
	/** this method is for selling operation 
	 *  it calls the successfulsellingoperation method from the trader's wallet
	 * @param amount   amount of coins that selling
	 * @param price    price of coins 
	 * @param market   market at which the trader is shopping
	 * @return nothing important 
	 */
	public int sell(double amount, double price, Market market) {
		wallet.successfulSellingOperation(amount,  amount * price * (1.000 - ( (double)  market.getFee()/1000 ) ) );
		return 0;
	}
	
	
	public int getId() {
		return id;
	}

	public Wallet getWallet() {
		return wallet;
	}
	
	/** this method is for printing the file easily
	 * 
	 * @return  line that printing the file whenever input file called 5 or 555
	 */
	public String printWallet() {
		String line = "Trader " + id + ": " + String.format("%.5f" , wallet.getTotalDollars()) + "$ " + String.format("%.5f", wallet.getTotalCoins()) + "PQ" ;
		return line;
	}

	
	
	
	
	
}
