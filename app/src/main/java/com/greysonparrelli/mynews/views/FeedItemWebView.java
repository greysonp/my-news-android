package com.greysonparrelli.mynews.views;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * @author Greyson Parrelli (keybase.io/greyson)
 */
public class FeedItemWebView extends WebView {

    private static final String HTML_PREPEND =
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">" +
            "<style>" +
            "   html, body {" +
            "       margin: 0;" +
            "       padding: 0;" +
            "   }" +
            "   img {" +
            "       width: 100%;" +
            "       height: auto;" +
            "   }" +
            "</style>";


    public FeedItemWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setHtmlContent(String html) {
        loadData(HTML_PREPEND + html, "text/html", "utf8");
    }
}
