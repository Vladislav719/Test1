package com.github.vladislav719;


import com.github.vladislav719.api.text.TextocatWorker;
import com.github.vladislav719.help.Request;
import org.apache.http.HttpException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class HelloController {
	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
        Map<String, String> map = new HashMap<>();
        map.put("q", "test");
        Map map1 = null;
        try {
            map1 = Request.get(Request.URLHelper("http://google.com/search", map));

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (HttpException e) {
            e.printStackTrace();
        }

        try {
            map1 = new TextocatWorker().addQueue("[ { \"text\": \"Меня зовут Влад\", \"tag\": \"1\" } ]");
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        model.addAttribute("message", map1.get("content"));
		return "hello";
	}
}