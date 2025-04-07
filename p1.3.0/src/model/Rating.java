package model;

public class Rating {
    private final int rating;
    private final String comment;
    private User user;

    public Rating(int rating, String comment, User user) {
        this.rating = rating;
        this.comment = comment;
        this.user = user;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
