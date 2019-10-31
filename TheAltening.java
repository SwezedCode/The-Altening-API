package com.teaclient.tea.api.thealtening;

import yourpackage;
import yourpackage.utils.AccountService;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class TheAltening {

    private AccountInfo accountInfo;
    private LicenseInfo licenseInfo;
    private AccountService accountService = new AccountService();

    /** Generates an account with the api token */
    public void generateAccount(String apiToken)
    {
        accountInfo = new AccountInfo(apiToken);
        accountInfo.generate();
    }

    /** Gets the info from the account generated */
    public AccountInfo getAccount() {
        if(accountInfo == null)
        {
            System.out.println("You need to generate an account before getting the details.");
            return null;
        }
        return accountInfo;
    }

    /** Initializes the information needed for the license info */
    public void initializeLicenseInfo(String apiToken)
    {
        licenseInfo = new LicenseInfo(apiToken);
        licenseInfo.initialize();
    }

    /** Gets info from the license initialized */
    public LicenseInfo getLicenseInfo() {
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
    public void setAuthService(AccountService.ServiceType serviceType) throws NoSuchFieldException, IllegalAccessException {
        accountService.switchService(serviceType);
    }

    /** Verifies/bypasses the https on the altening service */
    public void verifySSL()
    {
        accountService.verify();
    }

    /** Used for getting the json string */
    public String receive(String URL)
    {
        try
        {
            HttpGet httpGet = new HttpGet(URL);
            return EntityUtils.toString(HttpClients.createDefault().execute(httpGet).getEntity());
        }catch(IOException e) {
            System.out.println("Error requesting string from " + URL);
        }
        return "";
    }

}
