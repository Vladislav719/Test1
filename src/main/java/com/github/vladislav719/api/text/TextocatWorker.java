package com.github.vladislav719.api.text;

import com.github.vladislav719.help.Request;
import org.apache.http.HttpException;
import org.apache.http.impl.client.RoutedRequest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vladislav on 09.11.14.
 */
public class TextocatWorker {

    public Map<String, String> addQueue(String text) throws HttpException, IOException, URISyntaxException {
        Map<String, String> urlParam = new HashMap<>();
        urlParam.put("auth_token", "r5wncjwc4p7i2fxa0ice");

        String url = Request.URLHelper("http://api.textocat.com:80/api/entity/queue", urlParam);
        return Request.post(url, text);
    }
}
