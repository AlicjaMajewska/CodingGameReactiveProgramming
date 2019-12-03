class User {
    static final User SAUL = new User("Saul", "Pico", "SAUL");
    static final User JESSE = new User("Jesse", "Pico", "JESSE");
    private final String firstname;
    private final String lastname;
    private final String username;

    User(String firstname, String lastname, String username) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
    }

    String getFirstname() {
        return firstname;
    }

    String getLastname() {
        return lastname;
    }

    String getUsername() {
        return username;
    }

}
