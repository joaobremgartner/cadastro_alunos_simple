package br.com.caelum.cadastro.util;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by joaofaro on 08/07/15.
 */
public class WebClient {

    private String url;

    public WebClient(String url) {
        this.url = url;
    }

    public String post(String json){

        try {
            HttpPost post = new HttpPost(url);
            post.setEntity(new StringEntity(json));
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");

            DefaultHttpClient client = new DefaultHttpClient();
            HttpResponse resposta = client.execute(post);

            String jsonResposta = EntityUtils.toString(resposta.getEntity());

            return jsonResposta;

        } catch (UnsupportedEncodingException e) {
            Log.e("WebCliente", e.getMessage());
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            Log.e("WebClient", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Web", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
