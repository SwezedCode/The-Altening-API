In order to generate a new account you do the following steps:

private String token;

AccountInfo account;

public void generate()
{
    account = new AccountInfo(YOUR_API_TOKEN);
    
    /* Example */
    this.token = account.getToken();
}
