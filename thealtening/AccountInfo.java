package thealtening;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Created by Swezed.
 */
public class AccountInfo extends TheAltening {

    private String apiToken;

    private String token;
    private String username;
    private String expiry;

    private String hypixelLvl;
    private String hypixelRank;
    private String mineplexLvl;
    private String mineplexRank;
    private boolean hasLabymodCape;
    private boolean has5zigCape;

    private String error;

    public AccountInfo(String apiToken) 
    {
        this.apiToken = apiToken;
    }

    /** Generates the account */
    public boolean generate()
    {
        Gson gson = new Gson();
        String info = receive("http://api.thealtening.com/v1/generate?token=" + apiToken + "&info=true");

        /* The API Token was not correct. */
        if (info.contains("api-invalid"))
        {
            error = "Invalid API Token.";
            return false;
        }

        /* The account doesn't have Basic plan or any other plan purchased. */
        if (info.contains("ForbiddenOperationException"))
        {
            error = "You must have Basic rank or higher in order to use this feature.";
            return false;
        }

        JsonObject jsonObject = gson.fromJson(info, JsonObject.class);

        /* The json object could not fetch or find any json data. */
        if (jsonObject == null)
        {
            error = "Couldn't fetch data from the api.";
            return false;
        }

        /* The account has reached the limit amount of accounts generated. */
        if (jsonObject.get("limit").getAsBoolean() == true)
        {
            error = "You reached the limit of accounts generated today.";
            return false;
        }

        /* Sets the token, username and expiry to the received information from the API. */
        this.token = jsonObject.get("token").getAsString();
        this.username = jsonObject.get("username").getAsString();
        this.expiry = jsonObject.get("expires").getAsString();


        JsonObject json = gson.fromJson(info, JsonObject.class);

        /* Level */
        if (json.getAsJsonObject("info").has("hypixel.lvl"))
        {
            this.hypixelLvl = json.getAsJsonObject("info").get("hypixel.lvl").toString();
        }
        if (json.getAsJsonObject("info").has("mineplex.lvl"))
        {
            this.mineplexLvl = json.getAsJsonObject("info").get("mineplex.lvl").toString();
        }

        /* Ranks */
        if (json.getAsJsonObject("info").has("hypixel.rank"))
        {
            this.hypixelRank = json.getAsJsonObject("info").get("hypixel.rank").toString();
        }
        if (json.getAsJsonObject("info").has("mineplex.rank"))
        {
            this.mineplexRank = json.getAsJsonObject("info").get("mineplex.rank").toString();
        }

        /* Capes */
        if (json.getAsJsonObject("info").has("labymod.cape"))
        {
            this.hasLabymodCape = true;
        }
        if (json.getAsJsonObject("info").has("mineplex.rank"))
        {
            this.has5zigCape = true;
        }

        return true;
    }

    /** Privates the specified account by the account token */
    public boolean privateAccount()
    {
        Gson gson = new Gson();
        String info = receive("http://api.thealtening.com/v1/private?token=" + apiToken + "&acctoken=" + token);

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
    public boolean favouriteAccount()
    {
        Gson gson = new Gson();
        String info = receive("http://api.thealtening.com/v1/favorite?token=" + apiToken + "&acctoken=" + token);

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

    public String getError() 
    {
        return error;
    }

    public String getToken() 
    {
        return token;
    }

    public String getUsername() 
    {
        return username;
    }

    public String getExpiry() 
    {
        return expiry;
    }

    public String getHypixelLvl() 
    {
        return hypixelLvl;
    }

    public String getMineplexLvl() 
    {
        return mineplexLvl;
    }

    public String getHypixelRank() 
    {
        return hypixelRank;
    }

    public String getMineplexRank() 
    {
        return mineplexRank;
    }

    public boolean hasLabymodCape() 
    {
        return hasLabymodCape;
    }

    public boolean has5zigCape() 
    {
        return has5zigCape;
    }
}
