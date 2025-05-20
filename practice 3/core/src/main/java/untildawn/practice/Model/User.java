package untildawn.practice.Model;

public class User {
    private int id ;
    private static int idCounter;
    private String username;
    private String password;
    private boolean guest;
    private String securityQuestion;
    private String securityAnswer;
    private int score;
    private int kills;
    private int survivalTime;

    public User( String username, String password) {
        this.id = idCounter++;
        this.username = username;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public boolean isGuest() {
        return guest;
    }

    public void setGuest(boolean guest) {
        this.guest = guest;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getSurvivalTime() {
        return survivalTime;
    }

    public void setSurvivalTime(int survivalTime) {
        this.survivalTime = survivalTime;
    }
}
