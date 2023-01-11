package com.yudian.common.web.filter;

import com.yudian.common.web.utils.EscapeUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] escapseValues = new String[length];
            for (int i = 0; i < length; i++) {

                escapseValues[i] = EscapeUtil.clean(values[i]).trim();
            }
            return escapseValues;
        }
        return super.getParameterValues(name);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        if (!isJsonRequest()) {
            return super.getInputStream();
        }


        String json = IOUtils.toString(super.getInputStream(), "utf-8");
        if (StringUtils.isEmpty(json)) {
            return super.getInputStream();
        }


        json = EscapeUtil.clean(json).trim();
        final ByteArrayInputStream bis = new ByteArrayInputStream(json.getBytes("utf-8"));
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return true;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }

            @Override
            public int read() throws IOException {
                return bis.read();
            }
        };
    }

    
    public boolean isJsonRequest() {
        String header = super.getHeader(HttpHeaders.CONTENT_TYPE);
        return MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(header);
    }
}