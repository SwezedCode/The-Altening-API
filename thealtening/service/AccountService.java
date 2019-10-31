package thealtening.service;

import thealtening.service.utils.ReflectionUtils;
import thealtening.service.utils.SSL;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Trol. A little modified by Swezed.
 */
public class AccountService extends SSL {

    private final ReflectionUtils userAuthentication = new ReflectionUtils("com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication");
    private final ReflectionUtils minecraftSession = new ReflectionUtils("com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService");
    private ServiceType currentService;

    /** Use this to switch the server service */
    public void switchService(ServiceType enumAltService) throws NoSuchFieldException, IllegalAccessException 
    {
        this.verify();

        if (currentService == enumAltService)
            return;

        reflectionFields(enumAltService.hostname);

        currentService = enumAltService;
    }

    /** Changes the server service routes */
    private void reflectionFields(String authServer) throws NoSuchFieldException, IllegalAccessException 
    {
        final HashMap<String, URL> userAuthenticationModifies = new HashMap();
        final String useSecureStart = authServer.contains("thealtening") ? "http" : "https";
        userAuthenticationModifies.put("ROUTE_AUTHENTICATE", constantURL(useSecureStart+"://authserver." + authServer + ".com/authenticate"));
        userAuthenticationModifies.put("ROUTE_INVALIDATE", constantURL(useSecureStart+"://authserver" + authServer + "com/invalidate"));
        userAuthenticationModifies.put("ROUTE_REFRESH", constantURL(useSecureStart+"://authserver." + authServer + ".com/refresh"));
        userAuthenticationModifies.put("ROUTE_VALIDATE", constantURL(useSecureStart+"://authserver." + authServer + ".com/validate"));
        userAuthenticationModifies.put("ROUTE_SIGNOUT", constantURL(useSecureStart+"://authserver." + authServer + ".com/signout"));

        userAuthenticationModifies.forEach((key, value) -> 
        {
            try {
                userAuthentication.setStaticField(key, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        userAuthentication.setStaticField("BASE_URL",useSecureStart+ "://authserver." + authServer + ".com/");
        minecraftSession.setStaticField("BASE_URL", useSecureStart+"://sessionserver." + authServer + ".com/session/minecraft/");
        minecraftSession.setStaticField("JOIN_URL", constantURL(useSecureStart+"://sessionserver." + authServer + ".com/session/minecraft/join"));
        minecraftSession.setStaticField("CHECK_URL", constantURL(useSecureStart+"://sessionserver." + authServer + ".com/session/minecraft/hasJoined"));
        minecraftSession.setStaticField("WHITELISTED_DOMAINS", new String[]{".minecraft.net", ".mojang.com", ".thealtening.com"});
    }

    private URL constantURL(final String url) 
    {
        try 
        {
            return new URL(url);
        } catch (final MalformedURLException ex) 
        {
            throw new Error("Couldn't create constant for " + url, ex);
        }
    }

    public enum ServiceType 
    {
        MOJANG("mojang"), THEALTENING("thealtening");
        String hostname;

        ServiceType(String host) {
            this.hostname = hostname;
        }
    }

}
