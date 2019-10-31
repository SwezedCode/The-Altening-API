package thealtening;

import thealtening.service.AccountService;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class TheAltening {

    private AccountInfo accountInfo;
    private LicenseInfo licenseInfo;
    private AccountService accountService = new AccountService();

    /** Generates an account with the api token */
    public boolean generateAccount(String apiToken)
    {
        accountInfo = new AccountInfo(apiToken);
        return accountInfo.generate();
    }

    /** Privates the latest generated account */
    public boolean privateCurrentAccount()
    {
        if(accountInfo == null)
        {
            System.out.println("You need to generate an account before getting the details.");
            return false;
        }
        return accountInfo.privateAccount();
    }

    /** Favourites the latest generated account */
    public boolean favouriteCurrentAccount()
    {
        if(accountInfo == null)
        {
            System.out.println("You need to generate an account before getting the details.");
            return false;
        }
        return accountInfo.favouriteAccount();
    }

    /** Privates the latest generated account */
    public boolean privateAccount(String apiToken, String accountToken)
    {
        accountUtils = new AccountUtils(apiToken);
        return accountUtils.privateAccount(accountToken, apiToken);
    }

    /** Favourites the latest generated account */
    public boolean favouriteAccount(String apiToken, String accountToken)
    {
        accountUtils = new AccountUtils(apiToken);
        return accountUtils.favouriteAccount(accountToken, apiToken);
    }

    /** Gets the info from the account generated */
    public AccountInfo getAccount()
    {
        if(accountInfo == null)
        {
            System.out.println("You need to generate an account before getting the details.");
            return null;
        }
        return accountInfo;
    }

    /** Initializes the information needed for the license info */
    public boolean initializeLicenseInfo(String apiToken)
    {
        licenseInfo = new LicenseInfo(apiToken);
        return licenseInfo.initialize();
    }

    /** Gets info from the license initialized */
    public LicenseInfo getLicenseInfo()
    {
        if(licenseInfo == null)
        {
            System.out.println("You need to initialize the account info before getting the details.");
            return null;
        }
        return licenseInfo;
    }

    /** Switches the authentication service to x service type
     *  Default: MOJANG
     * */
    public void setAuthService(AccountService.ServiceType serviceType) throws NoSuchFieldException, IllegalAccessException 
    {
        accountService.verify();
        accountService.switchService(serviceType);
    }

    /** Used for getting the json string */
    public String receive(String URL)
    {
        try
        {
            HttpGet httpGet = new HttpGet(URL);
            return EntityUtils.toString(HttpClients.createDefault().execute(httpGet).getEntity());
        }catch(IOException e) 
        {
            System.out.println("Error requesting string from " + URL);
        }
        return "";
    }

}
