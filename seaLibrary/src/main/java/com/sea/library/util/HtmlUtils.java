package com.sea.library.util;

import android.text.Html;
import android.text.Spanned;

/**
 * Created by lhy on 2018/2/8.
 */

public class HtmlUtils {


    public static Spanned fromHtml(String htmlStr) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(htmlStr, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(htmlStr);
        }
    }

}
