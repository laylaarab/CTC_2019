package ca.ctc2019.backend;

import com.vaadin.flow.component.login.AbstractLogin;

import java.sql.SQLException;

//Class to emulate login control
public class LoginController {
    enum AccountType {
        COMPANY,
        USER
    }

    private static LoginController instance;
    private AccountType accountType;
    private Boolean loggedIn;
    private Account account;

    private LoginController() {
        accountType = null;
        loggedIn = false;
        account = null;
    }

//    create login
    public static LoginController getInstance()
    {
        if (instance == null)
            instance = new LoginController();
        return instance;
    }

    public Boolean loginUser(AbstractLogin.LoginEvent e) throws SQLException {
        accountType = AccountType.COMPANY;
        account = DatabaseController.getInstance().authenticateAccount(e.getUsername(), e.getPassword());
        if (account != null) {
            loggedIn = true;
        } else {
            loggedIn = false;
        }
        return loggedIn;
    }

    public void logOut() {
        accountType = null;
        loggedIn = false;
        account = null;
    }
    public AccountType getAccountType() {
        return accountType;
    }
    public Boolean isLoggedIn() {
        return loggedIn;
    }

}
