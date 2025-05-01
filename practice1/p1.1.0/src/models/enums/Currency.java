package models.enums;

/*
Explanation:
- We need to define a currency enum.
- currencies in out app are some constants that we need to define them in our code once and use them in our code.
- each currency has some data, put them here and use some methods to work with currencies so simply.
 */

public enum Currency {
    GalacticCoin("GTC", 5),
    StellarDollar("SUD", 2.5),
    QuantumNote("QTR", 1);
    public String symbol;
    public double value;
    Currency(String symbol, double value) {
        this.symbol = symbol;
        this.value = value;
    }

}
