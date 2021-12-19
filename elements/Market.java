package elements;

import java.util.*;

/** this class is the most important class of the project, it has too many methods to implement
 * 
 * @author egecans
 *
 */
public class Market {
	
	private PriorityQueue<SellingOrder> sellingOrders = new PriorityQueue<SellingOrder> ();
	private PriorityQueue<BuyingOrder> buyingOrders = new PriorityQueue<BuyingOrder> () ;
	private ArrayList<Transaction> transactions = new ArrayList<Transaction> ();
	private ArrayList<Trader> traders = new ArrayList<Trader> ();
	private int fee;
	
	/** this constructor is determine the market fee with its parameters.
	 * 
	 * @param fee market fee
	 */
	public Market (int fee) {
		this.fee = fee;
	}
	
	/** it adds the traders into the traders arrayList in this class
	 * 
	 * @param trader
	 */
	public void addTraderstoMarket(Trader trader) {
		traders.add(trader.getId(), trader);
	}
	
	/** it is for giving a selling order, it also checks the order's validity.
	 *  at the end of the method, it checks the whether there is a transaction or not
	 * @param order  selling order
	 */
	public void giveSellOrder(SellingOrder order) {
		if (order.checkvalidty(traders.get(order.traderID))) {
			sellingOrders.add(order);
			traders.get(order.traderID).getWallet().addBlockedCoins(order.getAmount());
		}
		else {
			order.invalidQuery();
		}
		checkTransactions(traders);
	}
	
	/** it is for giving a buying order, it also checks the order's validity.
	 *  at the end of the method, it checks the whether there is a transaction or not
	 * @param order buying order 
	 */
	public void giveBuyOrder (BuyingOrder order) {
		if (order.checkvalidty(traders.get(order.traderID))) {
			buyingOrders.add(order);
			traders.get(order.traderID).getWallet().addBlockedDollars(order.getAmount() , order.getPrice());
		}
		else {
			order.invalidQuery();
		}
		checkTransactions(traders);
	}
	
	/** this method is similar to giveBuyOrder, it differences with this because of price is defined by current market price for buying.
	 * 
	 * @param order buying order 
	 */
	public void currentBuyOrder ( BuyingOrder order ) {
		if (sellingOrders.size() != 0) {
			giveBuyOrder(order);
		}
		else {
			order.invalidQuery();
		}
	}
	
	/** this method is similar to giveSellOrder, it differences with this because of price is defined by current market price for selling.
	 * 
	 * @param order
	 */
	public void currentSellOrder ( SellingOrder order ) {
		if ( buyingOrders.size() != 0 ) {
			giveSellOrder(order);
		}
		else {
			order.invalidQuery();
		}
	}
	
	/** this method is for cancel the selling order. Even if it is not necessary for inputs, it is necessary for dividing the orders with 2 pieces to implement
	 * 
	 * @param order  canceling selling order.
	 */
	public void cancelSellingOrder (SellingOrder order) {
		traders.get(order.getTraderID()).getWallet().addBlockedCoins( - (order.getAmount() ) );
		sellingOrders.remove(order);
	}
	
	/** this method is for cancel the buying order. Even if it is not necessary for inputs, it is necessary for dividing the orders with 2 pieces to implement
	 * 
	 * @param order canceling buying order 
	 */
	public void cancelBuyingOrder (BuyingOrder order) {
		traders.get(order.getTraderID()).getWallet().addBlockedDollars( - (order.getAmount()), order.getPrice());
		buyingOrders.remove(order);
	}
	
	
	/** this method is for checkTransaction method. 
	 *  I did this since I don't want to write a very long code for checkTransaction.
	 *  it divides orders to 2 suitable pieces. One of them equals to other order, other is the remaining part of the first order.
	 */
	public void equalAmountsforPeeks() {
		double sellPrice =	sellingOrders.peek().getPrice();
		double sellAmount = sellingOrders.peek().getAmount();
		int sellTraderID = sellingOrders.peek().getTraderID();
		double buyPrice = buyingOrders.peek().getPrice();
		double buyAmount = buyingOrders.peek().getAmount();
		int buyTraderID = buyingOrders.peek().getTraderID();
		if (sellAmount > buyAmount) {
			cancelSellingOrder(sellingOrders.peek());
			double remainAmount = sellAmount - buyAmount;
			giveSellOrder(new SellingOrder(sellTraderID, buyAmount , sellPrice )) ;
			giveSellOrder(new SellingOrder(sellTraderID, remainAmount , sellPrice )) ;
			
		}
		
		else if ( buyAmount > sellAmount ) {
			cancelBuyingOrder(buyingOrders.peek());
			double remainAmount = buyAmount - sellAmount;
			giveBuyOrder ( new BuyingOrder(buyTraderID, sellAmount, buyPrice  ) ) ;
			giveBuyOrder ( new BuyingOrder(buyTraderID, remainAmount, buyPrice  ) ) ;
		}
	}
	
	

	/** This is checks whether there is a transaction or not, if there is it implements the transaction.
	 * 
	 * @param traders trader gets transaction
	 */
	public void checkTransactions(ArrayList<Trader> traders) {
		
		if (sellingOrders.size() != 0 && buyingOrders.size() != 0 ) {
		
			while ( sellingOrders.peek().getPrice() <= buyingOrders.peek().getPrice()) {
					
				
				equalAmountsforPeeks();
				
				if ( sellingOrders.peek().getPrice() == buyingOrders.peek().getPrice() ) {
					
					if ( sellingOrders.peek().getAmount() == buyingOrders.peek().getAmount() ) {
						
						transactions.add(new Transaction ( ));
						traders.get(sellingOrders.peek().getTraderID()).sell(sellingOrders.peek().getAmount(), sellingOrders.peek().getPrice(), this);    
						traders.get(buyingOrders.peek().getTraderID()).buy(buyingOrders.peek().getAmount(), buyingOrders.peek().getPrice(), this);
						sellingOrders.remove();
						buyingOrders.remove();
					}
					
				}
				
				if ( sellingOrders.peek().getPrice() < buyingOrders.peek().getPrice() ) {
					
					if ( sellingOrders.peek().getAmount() == buyingOrders.peek().getAmount() ) {
						
						transactions.add(new Transaction ( ) );
						traders.get(buyingOrders.peek().getTraderID()).buy(buyingOrders.peek().getAmount() , sellingOrders.peek().getPrice() , this) ;
						double priceDifference = sellingOrders.peek().getPrice() - buyingOrders.peek().getPrice() ;
						traders.get(buyingOrders.peek().getTraderID()).getWallet().addBlockedDollars(buyingOrders.peek().getAmount() , priceDifference);
						traders.get(sellingOrders.peek().getTraderID()).sell(sellingOrders.peek().getAmount(), sellingOrders.peek().getPrice(), this);
						sellingOrders.remove();
						buyingOrders.remove();
	
					}
	
				}
				
			}

			}
	
		
	}
	
	
	/** this method is market's operation to equalize the market prices with given price.
	 * 
	 * @param price  price that equalizing for the market
	 */
	public void makeOpenMarketOperation(double price) {
		
		if (sellingOrders.size() != 0 && buyingOrders.size() != 0 ) {
		
		traders.get(0).getWallet().addDollars(Math.pow(2, 20));
		traders.get(0).getWallet().rewardCoins(Math.pow(2, 20));
		
		
			
		
		if (price > currentPrice() ) {
			
			while ( ! (price < sellingOrders.peek().getPrice() ) ) {
				giveBuyOrder(new BuyingOrder(0, sellingOrders.peek().getAmount(), sellingOrders.peek().getPrice() ) );	

			}
		}
		
		if (price < currentPrice() )  {
			
			while ( ! (price > buyingOrders.peek().getPrice() ) ) {

				giveSellOrder(new SellingOrder(0, buyingOrders.peek().getAmount(), buyingOrders.peek().getPrice() ) );
				
			}
		}
	}
	
	}

	/** it determines the currentPrice for the market
	 * 
	 * @return currentprice
	 */
	public double currentPrice() {
		if ( buyingOrders.size() == 0 && sellingOrders.size() == 0 ) {
			return 0;
		}
		else if ( buyingOrders.size() == 0 && sellingOrders.size() != 0 ) {
			return sellingOrders.peek().getPrice();
		}
		else if ( buyingOrders.size() != 0 && sellingOrders.size() == 0 ) {
			return buyingOrders.peek().getPrice();
		}
		else {
			return ( buyingOrders.peek().getPrice() + sellingOrders.peek().getPrice() ) / 2 ;
		}	
	}
	
	/** it determines the current price for buying
	 * 
	 * @return
	 */
	public double buyingCurrentPrice() {
		if (! (sellingOrders.size() == 0 ) ) {
			return sellingOrders.peek().getPrice();
		}
		else {
			return 0;
		}
	}
	
	/** it determines the current price for selling 
	 * 
	 * @return
	 */
	public double sellingCurrentPrice() {
		if ( ! ( buyingOrders.size() == 0) ) {
			return buyingOrders.peek().getPrice();
		}
		else {
			return 0;
		}
	}
	
	/** it is for printing the line to the output file easily.
	 * 
	 * @return the line that printing the output file whenever 505 is called in the input file
	 */
	public String marketCurrentPrices() {
		String line = "Current prices: " + String.format("%.5f",sellingCurrentPrice())  + " " + String.format("%.5f",buyingCurrentPrice()) + " " + String.format("%.5f", currentPrice() ) ;                       
		return line;
	}
	
	/** it determines the total dollars in the market for buying
	 * 
	 * @return total dollars in the market for buying
	 */
	public double totalDollarsforBuying() {
		double sum = 0;
		Iterator<BuyingOrder> it = buyingOrders.iterator();
		while (it.hasNext()) {
			BuyingOrder thiss = it.next();
			sum += thiss.getPrice()*thiss.getAmount();
		}
		return sum;
	}
	
	/** it determines the total coins in the market for selling
	 * 
	 * @return total coins in the market for selling
	 */
	 double totalCoinsforSelling() {
			double sum = 0;
			Iterator<SellingOrder> it = sellingOrders.iterator();
			while (it.hasNext()) {
				SellingOrder thiss = it.next();
				sum += thiss.getAmount();
			}
			return sum;
		}
	
	/** it is for printing the line to the output file easily. 
	 * 
	 * @return the line that printing the output file whenever 500 is called in the input file
	 */
	public String currentMarketSize() {
		String line = "Current market size: " + String.format("%.5f" ,totalDollarsforBuying() ) + " " + String.format("%.5f" ,totalCoinsforSelling() ) ;
		return line;
	}
	
	public int getFee() {
		return fee;
	}

	public ArrayList<Trader> getTraders() {
		return traders;
	}

	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}
	
	
	
	
}
