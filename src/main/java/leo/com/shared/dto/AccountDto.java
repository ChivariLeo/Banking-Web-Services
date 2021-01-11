package leo.com.shared.dto;



import java.io.Serializable;
import java.time.LocalDate;


public class AccountDto implements Serializable {

    public static  final  long serialVersionUID = -1414230258148769489L;
    private long id;
    private String accountId;
    private double balance;
    private double openingBalance;
    private String accountName;
    private LocalDate accountCreationDate;
    private double interest;
    private UserDto userDetails;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public UserDto getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDto userDetails) {
        this.userDetails = userDetails;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(double openingBalance) {
        this.openingBalance = openingBalance;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public LocalDate getAccountCreationDate() {
        return accountCreationDate;
    }

    public void setAccountCreationDate(LocalDate accountCreationDate) {
        this.accountCreationDate = accountCreationDate;
    }
}



