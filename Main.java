Main.java
package package2;

public class Main {
    public static void main(String[] args) {
        DLARegistry registry = new DLARegistry();
        User user = new User("Jane Doe");

        registry.addDLA(new DLA("LoanQuick", true));
        registry.addDLA(new DLA("CashNow", false));
        registry.addDLA(new DLA("FastFunds", true));

        registry.displayDLAs();

        user.verifyDLA(registry, "LoanQuick");
        user.reportFraud(registry, "CashNow");

        registry.displayDLAs();
    }
}

DLARegistry.java


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
