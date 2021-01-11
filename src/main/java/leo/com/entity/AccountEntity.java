package leo.com.entity;



import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity(name = "accounts")
public class AccountEntity implements Serializable {

    public static  final  long serialVersionUID = 8868312108244190612L;
    @Id
    @GeneratedValue
    private long id;

    @Column(length = 30,nullable = false)
    private String accountId;
    @Column(nullable = false)
    private double balance;
    @Column(nullable = false)
    private double openingBalance;
    @Column(length = 100,nullable = false)
    private String accountName;
    @Column(nullable = false)
    private final LocalDate accountCreationDate=LocalDate.now();
    @Column(nullable = false)
    private double interest;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private UserEntity userDetails;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public LocalDate getAccountCreationDate() {
        return accountCreationDate;
    }



    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public UserEntity getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserEntity userDetails) {
        this.userDetails = userDetails;
    }
}
