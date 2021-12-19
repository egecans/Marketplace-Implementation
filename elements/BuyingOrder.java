package elements;

import java.util.ArrayList;

/** This is child class of Order classes it also implements comparable interface.
 * 
 * @author egecans
 *
 */
public class BuyingOrder extends Order implements Comparable<BuyingOrder> {
	
	/** this constructor is the same with parent class (Order.java).
	 *  it called the parent class.
	 * @param traderID  ID of trader
	 * @param amount	amount of purchasing coins
	 * @param price		price of each coin
	 */
	public BuyingOrder (int traderID, double amount, double price) {
		super(traderID, amount, price );
		
	}
	
	/** This method is for PriorityQueue to sort objects as wished order.
	 * The wished order for Buying order is max price is for peek,
	 *  if prices are equal then max amount order is for peek,
	 *  if both prices and amounts are equal then lower traderID is for head of the queue.
	 */
	public int compareTo (BuyingOrder other) {
			
			if (super.price == other.price) {
				
				if (super.amount == other.amount) {
					
					if (super.traderID == other.traderID) {
						return 0;	
					}
					else if (super.traderID  > other.traderID) {	
						return 1;
					}
					else {
						return -1;
					}
				}
				else if (super.amount > other.amount) {
					return -2;
				}
				else {
					return 2;
				}
			}
			else if (super.price > other.price) {
				return -3;
			}
			else {
				return 3;
			}
		}
	
	/** it checks validity with comparing whether traders money is adequate for purchasing or not.
	 * 
	 */
	public boolean checkvalidty(Trader trader) {
		if (trader.getWallet().getDollars() >= (amount*price) ) {
			return true;
		}
		else {
			return false;
		}
	}


	
	
	
}
