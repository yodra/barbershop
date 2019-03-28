package es.leanmind.barbershop.domain;

public class Owners {

    private final String username;
    private final String passwordHash;

    public Owners(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
