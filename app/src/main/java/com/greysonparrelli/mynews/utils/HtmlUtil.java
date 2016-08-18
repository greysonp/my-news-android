package com.greysonparrelli.mynews.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlUtil {

    private static final Pattern IMG_PATTERN = Pattern.compile("<img.*src=\"([^\"\\s]*)[^<]*(\\/>|<\\/img>)");

    public static List<String> getImgUrls(String html) {
        Matcher matcher = IMG_PATTERN.matcher(html);

        List<String> urls = new ArrayList<>();
        while (matcher.find()) {
            urls.add(matcher.group(1));
        }
        return urls;
    }
}
