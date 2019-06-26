package your.package;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @author Swezed.
 * Last updated: 26/06/2019
 */
public class AccountInfo {

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
        Gson gson = new Gson();
        String info = connect("http://api.thealtening.com/v1/generate?token=" + apiToken + "&info=true");

        /* The API Token was not correct. */
        if(info.contains("api-invalid")) { error = "Invalid API Token."; return; }

        /* The account doesn't have Basic plan or any other plan purchased. */
        if(info.contains("ForbiddenOperationException")) { error = "You must have Basic rank or higher in order to use this feature."; return; }

        JsonObject jsonObject = gson.fromJson(info, JsonObject.class);

        /* The json object could not fetch or find any json data. */
        if(jsonObject == null) { error = "Couldn't fetch data from the api."; return; }

        /* The account has reached the limit amount of accounts generated. */
        if(jsonObject.get("limit").getAsBoolean() == true) { error = "You reached the limit of accounts generated today."; return; }

        /* Sets the token, username and expiry to the received information from the API. */
        this.token = jsonObject.get("token").getAsString();
        this.username = jsonObject.get("username").getAsString();
        this.expiry = jsonObject.get("expires").getAsString();


        JsonObject json = gson.fromJson(info, JsonObject.class);

        /* Level */
        if(json.getAsJsonObject("info").has("hypixel.lvl"))
        {
            this.hypixelLvl = json.getAsJsonObject("info").get("hypixel.lvl").toString();
        }
        if(json.getAsJsonObject("info").has("mineplex.lvl"))
        {
            this.mineplexLvl = json.getAsJsonObject("info").get("mineplex.lvl").toString();
        }

        /* Ranks */
        if(json.getAsJsonObject("info").has("hypixel.rank"))
        {
            this.hypixelRank = json.getAsJsonObject("info").get("hypixel.rank").toString();
        }
        if(json.getAsJsonObject("info").has("mineplex.rank"))
        {
            this.mineplexRank = json.getAsJsonObject("info").get("mineplex.rank").toString();
        }

        /* Capes */
        if(json.getAsJsonObject("info").has("labymod.cape"))
        {
            this.hasLabymodCape = true;
        }
        if(json.getAsJsonObject("info").has("mineplex.rank"))
        {
            this.has5zigCape = true;
        }

    }

    public String getError() {
        return error;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getExpiry() {
        return expiry;
    }

    public String getHypixelLvl() {
        return hypixelLvl;
    }

    public String getMineplexLvl() {
        return mineplexLvl;
    }

    public String getHypixelRank() {
        return hypixelRank;
    }

    public String getMineplexRank() {
        return mineplexRank;
    }

    public boolean hasLabymodCape() {
        return hasLabymodCape;
    }

    public boolean has5zigCape() {
        return has5zigCape;
    }

    private String connect(String link) {
        try {
            HttpGet httpGet = new HttpGet(link);
            return EntityUtils.toString(HttpClients.createDefault().execute(httpGet).getEntity());
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

}
