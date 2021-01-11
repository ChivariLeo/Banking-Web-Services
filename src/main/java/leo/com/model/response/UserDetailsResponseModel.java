package leo.com.model.response;

import java.util.List;

public class UserDetailsResponseModel {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private List<AccountResponseModel> accounts;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<AccountResponseModel> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountResponseModel> accounts) {
        this.accounts = accounts;
    }
}
