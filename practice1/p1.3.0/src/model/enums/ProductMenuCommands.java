package model.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProductMenuCommands {
    ShowProducts("\\s*show\\s+products\\s+-sortBy\\s+(?<sortBy>(rate|higher price to lower|lower price to higher|number of sold))"),
    ShowProductDetails("\\s*show\\s+information\\s+of\\s+-id\\s+(?<productId>.+?)\\s*"),
    NextPage("\\s*show\\s+next\\s+10\\s+products\\s*"),
    PreviousPage("\\s*show\\s+past\\s+10\\s+products\\s*"),
    RateProduct("\\s*Rate\\s+product\\s+-r\\s+(?<number>.+?)\\s+(-m\\s+(?<message>.+?)\\s+)?-id\\s+(?<id>.+?)\\s*"),
    AddToCart("\\s*add\\s+to\\s+cart\\s+-product\\s+(?<productID>.+?)\\s+-quantity\\s+(?<amount>.+?)\\s*"),
    GoBack("\\s*go\\s+back\\s*");

    private final String pattern;
    ProductMenuCommands(String pattern) {
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
