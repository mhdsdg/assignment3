package model.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum UserMenuCommands {
    ListMyOrders("\\s*list\\s+my\\s+orders\\s*"),
    ShowOrderDetails("^\\s*show\\s+order\\s+details\\s+-id\\s+(?<orderId>.+)\\s*$"),
    EditUsername("^\\s*edit\\s+name\\s+-fn\\s+(?<firstName>.+?)\\s+-ln\\s+(?<lastName>.+?)\\s+-p\\s+(?<password>.+?)\\s*$"),
    EditEmail("^\\s*edit\\s+email\\s+-e\\s+(?<email>.+?)\\s+-p\\s+(?<password>.+?)\\s*$"),
    EditPassword("^\\s*edit\\s+password\\s+-np\\s+(?<newPass>.+?)\\s+-op\\s+(?<oldPass>.+?)\\s*$"),
    ShowMyInfo("^\\s*show\\s+my\\s+info\\s*$"),
    AddAddress("^\\s*add\\s+address\\s+-country\\s+(?<country>.+?)\\s+-city\\s+(?<city>.+?)\\s+-street\\s+(?<street>.+?)\\s+-postal\\s+(?<postal>.+?)\\s*$"),
    DeleteAddress("\\s*delete\\s+address\\s+-id\\s+(?<id>.+?)\\s*"),
    ListMyAddresses("\\s*list\\s+my\\s+addresses\\s*"),
    AddCreditCard("^\\s*add\\s+a\\s+credit\\s+card\\s+-number\\s+(?<cardNumber>\\d+)\\s+-ed\\s+(?<expirationDate>.+?)\\s+-cvv\\s+(?<cvv>\\d+)\\s+-initialValue\\s+(?<initialValue>-?.+?)\\s*$"),
    ChargeCreditCard("^\\s*Charge\\s+credit\\s+card\\s+-a\\s+(?<amount>.+?)\\s+-id\\s+(?<id>.+?)\\s*$"),
    CheckCardBalance("^\\s*Check\\s+credit\\s+card\\s+balance\\s+-id\\s+(?<id>.+?)\\s*$"),
    ShowCart("\\s*show\\s+products\\s+in\\s+cart\\s*"),
    Checkout("^\\s*checkout\\s+-card\\s+(?<cardID>.+?)\\s+-address\\s+(?<addressId>.+?)\\s*$"),
    RemoveFromCart("^\\s*remove\\s+from\\s+cart\\s+-product\\s+(?<productID>.+?)\\s+-quantity\\s+(?<amount>.+?)\\s*$"),
    GoBack("\\s*go\\s+back\\s*");

    private final String pattern;
    UserMenuCommands(String pattern) {
        this.pattern = pattern;
    }
    public Matcher getMather(String input) {
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);

        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
