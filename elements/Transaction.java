package elements;

/** This class is for storing the data, it's not doing anything significant.
 * 
 * @author egecans
 *
 */
public class Transaction {
	
	private SellingOrder sellingOrder;
	private BuyingOrder buyingOrder;
	private static int numberOfTransactions = 0;
	int id;
	
	/** this is default constructor, it's only aim is increasing number of transactions whenever it is called.
	 * 
	 */
	public Transaction ( ) {
		this.id = numberOfTransactions;
		numberOfTransactions ++ ;
	}

	public int getId() {
		return id;
	}

	public static int getNumberOfTransactions() {
		return numberOfTransactions;
	}
	
	
}
