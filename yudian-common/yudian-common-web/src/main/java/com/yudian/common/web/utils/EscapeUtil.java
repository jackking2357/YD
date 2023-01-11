package com.yudian.common.web.utils;

import com.yudian.common.web.filter.HTMLFilter;
import org.apache.commons.lang3.StringUtils;

public class EscapeUtil {
    public static final String RE_HTML_MARK = "(<[^<]*?>)|(<[\\s]*?/[^<]*?>)|(<[^<]*?/[\\s]*?>)";

    private static final char[][] TEXT = new char[64][];

    static {
        for (int i = 0; i < 64; i++) {
            TEXT[i] = new char[]{(char) i};
        }


        TEXT['\''] = "&#039;".toCharArray();
        TEXT['"'] = "&#34;".toCharArray();
        TEXT['&'] = "&#38;".toCharArray();
        TEXT['<'] = "&#60;".toCharArray();
        TEXT['>'] = "&#62;".toCharArray();
    }

    
    public static String escape(String text) {
        return encode(text);
    }

    
    public static String unescape(String content) {
        return decode(content);
    }

    
    public static String clean(String content) {
        return new HTMLFilter().filter(content);
    }

    
    private static String encode(String text) {
        int len;
        if ((text == null) || ((len = text.length()) == 0)) {
            return StringUtils.EMPTY;
        }
        StringBuilder buffer = new StringBuilder(len + (len >> 2));
        char c;
        for (int i = 0; i < len; i++) {
            c = text.charAt(i);
            if (c < 64) {
                buffer.append(TEXT[c]);
            } else {
                buffer.append(c);
            }
        }
        return buffer.toString();
    }

    
    public static String decode(String content) {
        if (StringUtils.isEmpty(content)) {
            return content;
        }

        StringBuilder tmp = new StringBuilder(content.length());
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < content.length()) {
            pos = content.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (content.charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt(content.substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                } else {
                    ch = (char) Integer.parseInt(content.substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            } else {
                if (pos == -1) {
                    tmp.append(content.substring(lastPos));
                    lastPos = content.length();
                } else {
                    tmp.append(content, lastPos, pos);
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();
    }











}
