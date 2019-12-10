package banksy;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "users")
public class BankUser {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String username;

    @DatabaseField
    private String email;

    public BankUser() {
        // The ORMs (ORMLite/Hibernate/etc) need a no-arg constructor
    }

    public BankUser(String username, String email) {
        // constructor with arguments created for use in our program
        // now we don't need setters in this class
        this.id = -1;
        this.username = username;
        this.email = email;
    }

    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "id=" + id + ", username=" + username + ", email=" + email;
    }
}