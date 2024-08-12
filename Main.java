
package package2;

public class Main{
    public static void main(String[] args) {
        Market market = new Market();
        Trader trader = new Trader("John Doe", 10000);

        market.addStock(new Stock("AAPL", 150));
        market.addStock(new Stock("GOOGL", 2800));
        market.addStock(new Stock("MSFT", 300));

        market.displayMarketData();

        trader.buyStock(market, "AAPL", 20);
        trader.buyStock(market, "GOOGL", 2);

        trader.getPortfolio().displayPortfolio();

        market.updateStockPrice("AAPL", 160);
        market.updateStockPrice("GOOGL", 2900);

        // Display updated market data
        market.displayMarketData();

        // Display updated portfolio value
        trader.getPortfolio().displayPortfolio();
    }


}
Market.java
import package2.*;
import java.util.HashMap;

public class Market {
    private HashMap<String, Stock> stocks;

    public Market() {
        this.stocks = new HashMap<>();
    }

    public void addStock(Stock stock) {
        stocks.put(stock.getSymbol(), stock);
    }

    public void updateStockPrice(String symbol, double newPrice) {
        if (stocks.containsKey(symbol)) {
            stocks.get(symbol).setPrice(newPrice);
        }
    }

    public Stock getStock(String symbol) {
        return stocks.get(symbol);
    }

    public void displayMarketData() {
        System.out.println("Market Data:");
        for (Stock stock : stocks.values()) {
            System.out.println(stock);
        }
        System.out.println();
    }
}

Portfolio.java
import package2.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Portfolio {
    private HashMap<String, Trade> trades;

    public Portfolio() {
        this.trades = new HashMap<>();
    }

    public void addTrade(Trade trade) {
        String symbol = trade.getStock().getSymbol();
        if (trades.containsKey(symbol)) {
            Trade existingTrade = trades.get(symbol);
            int newQuantity = existingTrade.getQuantity() + trade.getQuantity();
            trades.put(symbol, new Trade(trade.getStock(), newQuantity));
        } else {
            trades.put(symbol, trade);
        }
    }

    public double getTotalValue() {
        double total = 0;
        for (Trade trade : trades.values()) {
            total += trade.getTradeValue();
        }
        return total;
    }

    public void displayPortfolio() {
        System.out.println("Portfolio:");
        for (Trade trade : trades.values()) {
            System.out.println("Stock: " + trade.getStock().getSymbol() + " | Quantity: " + trade.getQuantity() + " | Value: $" + trade.getTradeValue());
        }
        System.out.println("Total Portfolio Value: $" + getTotalValue());
        System.out.println();
    }
}

Stock.java
import package2.*;

package package2;

public class DLA {
    private String name;
    private boolean isVerified;
    private int fraudReports;

    public DLA(String name, boolean isVerified) {
        this.name = name;
        this.isVerified = isVerified;
        this.fraudReports = 0;
    }

    public String getName() {
        return name;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public int getFraudReports() {
        return fraudReports;
    }

    public void reportFraud() {
        this.fraudReports++;
    }

    @Override
    public String toString() {
        return "DLA: " + name + " | Verified: " + isVerified + " | Fraud Reports: " + fraudReports;
    }
}


Trade.java
import package2.*;
public class Trade {
    private Stock stock;
    private int quantity;
    private double tradeValue;

    public Trade(Stock stock, int quantity) {
        this.stock = stock;
        this.quantity = quantity;
        this.tradeValue = stock.getPrice() * quantity;
    }

    public Stock getStock() {
        return stock;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTradeValue() {
        return tradeValue;
    }
}

Trader.java
import package2.*;
public class Trader {
    private String name;
    private double cashBalance;
    private Portfolio portfolio;

    public Trader(String name, double cashBalance) {
        this.name = name;
        this.cashBalance = cashBalance;
        this.portfolio = new Portfolio();
    }

    public void buyStock(Market market, String symbol, int quantity) {
        Stock stock = market.getStock(symbol);
        if (stock != null) {
            double totalPrice = stock.getPrice() * quantity;
            if (cashBalance >= totalPrice) {
                cashBalance -= totalPrice;
                Trade trade = new Trade(stock, quantity);
                portfolio.addTrade(trade);
                System.out.println("Bought " + quantity + " shares of " + symbol + " for $" + totalPrice);
            } else {
                System.out.println("Insufficient funds to buy " + quantity + " shares of " + symbol);
            }
        } else {
            System.out.println("Stock " + symbol + " not found in the market");
        }
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public double getCashBalance() {
        return cashBalance;
    }
}
