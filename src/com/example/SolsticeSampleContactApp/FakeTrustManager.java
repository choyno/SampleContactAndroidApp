package com.example.SolsticeSampleContactApp;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by neel on 2/23/14.
 */

/*
                                            ATTENTION: ANYONE REVIEWING THIS CODE

    - THIS WAS ONLY DONE BECAUSE THE SSL CERTIFICATE FOR THE JSON ENDPOINT HAD EXPIRED AND I COULD NOT CONTACT ANYONE OVER THE WEEKEND

    - THIS IS NOT GOOD PRACTICE AND I ONLY IMPLEMENTED THIS SO MY REQUESTS WOULD RESULT IN JSON FROM THE ENDPOINT, ALSO I TRUSTED THE ENDPOINT PROVIDED TO ME
    AND HOPED THAT IT WOULD NOT CONTAIN ANY VIRUS AND MALWARE

    - AFTER GOOGLING THE ERROR ON STACK OVERFLOW I WAS LEAD TO IMPLEMENTING THIS SOLUTION

 */


public class FakeTrustManager implements X509TrustManager {

    private static TrustManager[] trustManagers;
    private static final X509Certificate[] _AcceptedIssuers = new
            X509Certificate[] {};

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String
            authType) throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String
            authType) throws CertificateException {
    }

    public boolean isClientTrusted(X509Certificate[] chain) {
        return true;
    }

    public boolean isServerTrusted(X509Certificate[] chain) {
        return true;
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return _AcceptedIssuers;
    }

    public static void allowAllSSL() {
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }

        });

        SSLContext context = null;
        if (trustManagers == null) {
            trustManagers = new TrustManager[] { new FakeTrustManager() };
        }

        try {
            context = SSLContext.getInstance("TLS");
            context.init(null, trustManagers, new SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }


        HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
    }
}

