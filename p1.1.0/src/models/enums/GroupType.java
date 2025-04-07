package models.enums;

/*
Explanation:
- In our app, groups have some types that are constants.
- In these cases, we use enums to define them and use them in our code.
- put those types here and use them in your code.
 */
public enum GroupType {
    Home("Home"),
    Trip("Trip"),
    Zan_o_Bache("Zan-o-Bache"),
    Other("Other");
    private String name;
    private GroupType(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

}
