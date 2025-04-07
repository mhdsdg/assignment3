package model.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum StoreMenuCommands {
    AddProduct("^\\s*add\\s+product\\s+-n\\s+\"(?<name>.+?)\"\\s+-pc\\s+(?<producerCost>.+?)\\s+-p\\s+(?<price>.+?)\\s+-about\\s+\"?(?<aboutThisItem>.+?)\"?\\s+-np\\s+(?<NumberOfProductToSell>.+?)\\s*$"),
    ApplyDiscount("\\s*apply\\s+discount\\s+-p\\s+(?<productID>.+?)\\s+-d\\s+(?<discountPercentage>.+?)\\s+-q\\s+(?<quantity>.+?)\\s*$"),
    ShowProfit("\\s*show\\s+profit\\s*"),
    ShowProductList("\\s*show\\s+list\\s+of\\s+products\\s*"),
    AddToStock("\\s*add\\s+stock\\s+-product\\s+(?<productId>.+?)\\s+-amount\\s+(?<amount>.+?)\\s*$"),
    UpdatePrice("\\s*update\\s+price\\s+-product\\s+(?<productId>.+?)\\s+-price\\s+(?<newPrice>.+?)\\s*$"),
    GoBack("\\s*go\\s+back\\s*"),;

    private final String pattern;
    StoreMenuCommands(String pattern) {
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
