package com.github.vladislav719.help;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vladislav on 09.11.14.
 */
public class Request {

    public static String USER_AGENT = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:33.0) Gecko/20100101 Firefox/33.0";

    public static Map<String, String> post(String url, String jsonString) throws URISyntaxException, IOException, HttpException {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        post.setHeader("User_Agent", USER_AGENT);
        post.setHeader("Content-Type", "application/json");

        StringEntity stringEntity = new StringEntity(jsonString);
        stringEntity.setContentType("application/json");
        post.setEntity(stringEntity);

//        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
//        for (Map.Entry<String,String> entry : param.entrySet()) {
//            urlParameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//        }

//        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);

        HashMap<String, String> postReposne = new HashMap<>();
        postReposne.put("code", response.getStatusLine().getStatusCode()+"");

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent())
        );

        StringBuffer result = new StringBuffer();
        String line = "";

        while ( (line = bufferedReader.readLine()) != null) {
            result.append(line);
        }

        postReposne.put("content", result.toString());

        return postReposne;
    }

    public static Map<String, String> get(String url) throws URISyntaxException, IOException, HttpException {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);

        request.addHeader("User-Agent", USER_AGENT);

        HttpResponse response = client.execute(request);

        BufferedReader rd = new BufferedReader
                (new InputStreamReader(response.getEntity().getContent()));

        StringBuffer bodyRes  = new StringBuffer();

        String line = "";
        while ( (line = rd.readLine()) != null) {
            bodyRes.append(line);
        }

        Map<String, String> mapResult = new HashMap<>();

        mapResult.put("content", bodyRes.toString());
        mapResult.put("code", response.getStatusLine().getStatusCode() + "");
//        mapResult.put("headers", response.getEntity().getContentEncoding())


        return mapResult;
    }

    /**
     * example
     * @param domain http://example.com
     * @param params q=test
     * @return http://example.com/api/test
     */
    public static String URLHelper(String domain, Map<String, String> params) {

        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String > entry : params.entrySet()) {
            qparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return domain+"?"+URLEncodedUtils.format(qparams, "UTF-8");
    }
}
