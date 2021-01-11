package leo.com.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "users") //database table name
public class UserEntity implements Serializable {

    public static  final  long serialVersionUID = 6686672963754674064L;
    @Id
    @GeneratedValue
    private long id;  //primary key

    @NotNull
    @Column// column proprieties
    private String userId;

    @Column (length = 50)
    private String firstName;

    @NotNull
    @Column( length = 50)
    private String lastName;

    @NotNull
    @Column( length = 50,unique = true)
    private String email;


    @Column(nullable = false)
    private String encryptedPassword;

    private String emailVerificationToken;
    @Column
    @NotNull
    private boolean emailVerificationStatus=false;

    @OneToMany(mappedBy = "userDetails",cascade = CascadeType.ALL)
    private List<AccountEntity> accounts;




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getEmailVerificationToken() {
        return emailVerificationToken;
    }

    public void setEmailVerificationToken(String emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }

    public boolean getEmailVerificationStatus() {
        return emailVerificationStatus;
    }

    public void setEmailVerificationStatus(boolean emailVerificationStatus) {
        this.emailVerificationStatus = emailVerificationStatus;
    }

    public boolean isEmailVerificationStatus() {
        return emailVerificationStatus;
    }

    public List<AccountEntity> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountEntity> accounts) {
        this.accounts = accounts;
    }



}
