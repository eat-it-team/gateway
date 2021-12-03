package ru.eatit.gateway.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import ru.eatit.gateway.service.api.EmotionService;
import ru.eatit.gateway.service.api.entity.EmotionResult;

import javax.annotation.PostConstruct;
import javax.net.ssl.*;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


/**
 * Сервис для сентимент-анализа текста и анализа эмоциональной окраски
 * <p>
 * Предполагалось, что сможем анализировать эмоциональную окраску текста.
 * Но не срослось. Тут есть возможность проверить https://komprehend.io/emotion-analysis
 * Но API с русским языком стоит 300$ в месяц.
 * <p>
 * We use Deep Learning powered algorithms to extract features from the textual data.
 * These features are used to classify the emotion attached to the data.
 * We have trained our classifier using Convolutional Neural Networks (Covnets) on a tagged dataset created by our team.
 * <p>
 * <p>
 * Возможные кейсы использоваения:
 * Проверяем наличие ключевого слова и эмоиональную окраску, тем самым можем узнать
 * отношение челвека к этому слову.
 */
public class EmotionServiceImpl implements EmotionService {
    private String api_key = "YwPwLsK0dxQ57HeTTh9R24hAWn6UanDNDxmtCkiKkC0";
    private String host = "https://apis.paralleldots.com/v4/";

    @Autowired
    public EmotionServiceImpl() {
        try {
            setUpCert("apis.paralleldots.com");
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    @PostConstruct
    public void init() throws Exception {
        //String emotion = this.emotion("описание как дергали апи вконтакте, чтобы определить как относятся поправки к Конституции Российской Федерации", "ru");
        //System.out.println(emotion);
    }

    private void setUpCert(String hostname) throws Exception {
        SSLSocketFactory factory = HttpsURLConnection.getDefaultSSLSocketFactory();

        SSLSocket socket = (SSLSocket) factory.createSocket(hostname, 443);
        try {
            socket.startHandshake();
            socket.close();
            //System.out.println("No errors, certificate is already trusted");
            return;
        } catch (SSLException e) {
            //System.out.println("cert likely not found in keystore, will pull cert...");
        }


        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        char[] password = "changeit".toCharArray();
        ks.load(null, password);

        SSLContext context = SSLContext.getInstance("TLS");
        TrustManagerFactory tmf =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ks);
        X509TrustManager defaultTrustManager = (X509TrustManager) tmf.getTrustManagers()[0];
        SavingTrustManager tm = new SavingTrustManager(defaultTrustManager);
        context.init(null, new TrustManager[]{tm}, null);
        factory = context.getSocketFactory();

        socket = (SSLSocket) factory.createSocket(hostname, 443);
        try {
            socket.startHandshake();
        } catch (SSLException e) {
            //we should get to here
        }
        X509Certificate[] chain = tm.chain;
        if (chain == null) {
            System.out.println("Could not obtain server certificate chain");
            return;
        }

        X509Certificate cert = chain[0];
        String alias = hostname;
        ks.setCertificateEntry(alias, cert);

        System.out.println("saving file paralleldotscacerts to working dir");
        //System.out.println("copy this file to your jre/lib/security folder");
        FileOutputStream fos = new FileOutputStream("paralleldotscacerts");
        ks.store(fos, password);
        fos.close();
    }

    @SneakyThrows
    @Override
    public EmotionResult analyse(String text) {
        String em = emotion(text, "ru");
        ObjectMapper om = new ObjectMapper();
        return om.readValue(em, EmotionResult.class);
    }

    private static class SavingTrustManager implements X509TrustManager {

        private final X509TrustManager tm;
        private X509Certificate[] chain;

        SavingTrustManager(X509TrustManager tm) {
            this.tm = tm;
        }

        public X509Certificate[] getAcceptedIssuers() {

            return new X509Certificate[0];
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            throw new UnsupportedOperationException();
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            this.chain = chain;
            tm.checkServerTrusted(chain, authType);
        }
    }

    /*  {
          "code":200,
              "emotion":{
                  "emotion":"Happy",
                  "probabilities":{
                      "Angry":0.0307068229,
                      "Bored":0.0672401217,
                      "Excited":0.2845767833,
                      "Fear":0.0947352492,
                      "Happy":0.489004654,
                      "Sad":0.0337363689
              }
         }
      }*/
    private String emotion(String text, String lang_code) throws Exception {
      /*  String url = host + "emotion";
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("api_key", this.api_key)
                .addFormDataPart("text", text)
                .addFormDataPart("lang_code", lang_code)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("cache-control", "no-cache")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();*/
        return null;
    }

    public String sentiment(String text, String lang_code) throws Exception {
       /* String url = host + "sentiment";
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("api_key", this.api_key)
                .addFormDataPart("text", text)
                .addFormDataPart("lang_code", lang_code)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("cache-control", "no-cache")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();*/
        return null;
    }
}