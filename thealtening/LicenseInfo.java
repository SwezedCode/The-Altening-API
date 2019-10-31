package thealtening;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class LicenseInfo extends TheAltening {

    private String apiToken;

    private String username;
    private boolean premium;
    private String plan;
    private String expiry;

    private String error;

    public LicenseInfo(String apiToken) 
    { 
        this.apiToken = apiToken; 
    }

    /* Initializes to get the details */
    public void initialize()
    {
        Gson gson = new Gson();
        String info = receive("http://api.thealtening.com/v1/license?token=" + apiToken);

        /* The API Token was not correct. */
        if (info.contains("api-invalid")) 
        {
            error = "Invalid API Token.";
            return;
        }

        JsonObject jsonObject = gson.fromJson(info, JsonObject.class);

        /* The json object could not fetch or find any json data. */
        if (jsonObject == null) 
        {
            error = "Couldn't fetch data from the api.";
            return;
        }

        /* Sets the username, premium state, plan and expiry to the received information from the API. */
        this.username = jsonObject.get("username").getAsString();
        this.premium = jsonObject.get("premium").getAsBoolean();
        this.plan = jsonObject.get("premium_name").getAsString();
        this.expiry = jsonObject.get("expires").getAsString();
    }

    public String getError() 
    { 
        return error; 
    }

    public String getUsername() 
    { 
        return username; 
    }

    public boolean isPremium() 
    { 
        return premium; 
    }

    public String getPlan() 
    { 
        return plan; 
    }

    public String getExpiry() 
    { 
        return expiry; 
    }

}
