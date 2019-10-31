package thealtening.service.utils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Trol. A little modified by Swezed.
 */
public class SSL {

    private boolean verified = false;

    /** Verifies/bypasses the https on the altening service, */
    public void verify() 
    {
        if (!verified) 
        {
            activate();
            whitelistTheAltening();
            verified = true;
        }
    }

    /** The function to verify/bypass the altening service */
    private void activate() 
    {
        TrustManager[] trustAllCerts = new TrustManager[]
                {
                new X509TrustManager() 
                {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() 
                    {
                        return null;
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) 
                    {
                        
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) 
                    {
                        
                    }
                }
        };
        
        try 
        {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    private void whitelistTheAltening() 
    {
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
                (hostname, sslSession) -> hostname.equals("authserver.thealtening.com") || hostname.equals("sessionserver.thealtening.com"));
    }

}
