
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

package package2;

import java.util.HashMap;

public class DLARegistry {
    private HashMap<String, DLA> dlas;

    public DLARegistry() {
        this.dlas = new HashMap<>();
    }

    public void addDLA(DLA dla) {
        dlas.put(dla.getName(), dla);
    }

    public DLA getDLA(String name) {
        return dlas.get(name);
    }

    public void displayDLAs() {
        System.out.println("DLA Registry:");
        for (DLA dla : dlas.values()) {
            System.out.println(dla);
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
package package2;

public class User {
    private String name;

    public User(String name) {
        this.name = name;
    }

    public void verifyDLA(DLARegistry registry, String name) {
        DLA dla = registry.getDLA(name);
        if (dla != null) {
            if (dla.isVerified()) {
                System.out.println("DLA " + name + " is verified.");
            } else {
                System.out.println("DLA " + name + " is not verified.");
            }
        } else {
            System.out.println("DLA " + name + " not found in the registry.");
        }
    }

    public void reportFraud(DLARegistry registry, String name) {
        DLA dla = registry.getDLA(name);
        if (dla != null) {
            dla.reportFraud();
            System.out.println("Fraud reported for DLA " + name + ".");
        } else {
            System.out.println("DLA " + name + " not found in the registry.");
        }
    }
}
