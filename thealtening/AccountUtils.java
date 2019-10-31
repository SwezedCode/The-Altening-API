package thealtening;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class AccountUtils extends TheAltening {

    private String apiToken;

    private String error;

    public AccountUtils(String apiToken)
    {
        this.apiToken = apiToken;
    }

    /** Privates the specified account by the account token */
    public boolean privateAccount(String accountToken, String apiToken)
    {
        Gson gson = new Gson();
        String info = receive("http://api.thealtening.com/v1/private?token=" + apiToken + "&acctoken=" + accountToken);

        /* The API Token was not correct. */
        if (info.contains("api-invalid"))
        {
            error = "Invalid API Token.";
            return false;
        }

        if(info.contains("Token expired"))
        {
            error = "The account token was not found or expired.";
            return false;
        }

        /* The account doesn't have Basic plan or any other plan purchased. */
        if (info.contains("ForbiddenOperationException"))
        {
            error = "You must have Premium rank in order to use this feature.";
            return false;
        }

        JsonObject jsonObject = gson.fromJson(info, JsonObject.class);

        /* The json object could not fetch or find any json data. */
        if (jsonObject == null)
        {
            error = "Couldn't fetch data from the api.";
            return false;
        }

        if (!jsonObject.get("success").getAsBoolean() == true)
        {
            error = "The Altening service was unable to private the account.";
            return false;
        }

        return true;
    }

    /** Privates the specified account by the account token */
    public boolean favouriteAccount(String accountToken, String apiToken)
    {
        Gson gson = new Gson();
        String info = receive("http://api.thealtening.com/v1/favorite?token=" + apiToken + "&acctoken=" + accountToken);

        /* The API Token was not correct. */
        if (info.contains("api-invalid"))
        {
            error = "Invalid API Token.";
            return false;
        }

        if(info.contains("Token expired"))
        {
            error = "The account token was not found or expired.";
            return false;
        }

        /* The account doesn't have Basic plan or any other plan purchased. */
        if (info.contains("ForbiddenOperationException"))
        {
            error = "You must have Premium rank in order to use this feature.";
            return false;
        }

        JsonObject jsonObject = gson.fromJson(info, JsonObject.class);

        /* The json object could not fetch or find any json data. */
        if (jsonObject == null)
        {
            error = "Couldn't fetch data from the api.";
            return false;
        }

        if (!jsonObject.get("success").getAsBoolean() == true)
        {
            error = "The Altening service was unable to favourite the account.";
            return false;
        }

        return true;
    }

    public String getError() {
        return error;
    }
}
