package elements;

import java.util.ArrayList;

/** This is child class of Order classes it also implements comparable interface.
 * 
 * @author egecans
 *
 */
public class SellingOrder extends Order implements Comparable<SellingOrder> {
	
	
	/**
	 * 
	 * @param traderID ID of trader
	 * @param amount	amount of selling coins
	 * @param price		price of each selling coins
	 */
	public SellingOrder (int traderID, double amount, double price) {
		super(traderID, amount, price );
		
	}
	
	
	/** This method is for PriorityQueue to sort objects as wished order.
	 * The wished order for Selling order is min price is for peek,
	 *  if prices are equal then max amount order is for peek,
	 *  if both prices and amounts are equal then lower traderID is for head of the queue.
	 */
	public int compareTo (SellingOrder other) {
		
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
			return 3;
		}
		else {
			return -3;
		}
	}
	
	/** it checks validity with comparing whether traders coins is adequate for selling or not.
	 * 
	 */
	public boolean checkvalidty(Trader trader) {
		if (trader.getWallet().getCoins() >= amount) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
}
