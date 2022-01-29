package java.services.model;

public class User {
    private String first;
    private String middle;
    private String last;
    private String email;

    public User() {
    }

    public User(String first, String middle, String last, String email) {
        this.first = first;
        this.middle = middle;
        this.last = last;
        this.email = email;
    }

    public User(String[] delims) {
        String[] userInfo = delims[1].split(" ");
        first = userInfo[0];
        if (userInfo.length == 2) {
            last = userInfo[1];
        } else if (userInfo.length == 3) {
            middle = userInfo[1];
            last = userInfo[2];
        }
        email = delims[2];
    }

    public String getFirst() {
        return first;
    }

    public User setFirst(String first) {
        this.first = first;
        return this;
    }

    public String getMiddle() {
        return middle;
    }

    public User setMiddle(String middle) {
        this.middle = middle;
        return this;
    }

    public String getLast() {
        return last;
    }

    public User setLast(String last) {
        this.last = last;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "first='" + first + '\'' +
                ", middle='" + middle + '\'' +
                ", last='" + last + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
