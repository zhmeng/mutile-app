package com.teamclub.routes;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by ilkkzm on 17-4-19.
 */
public class HTTPRequestAdapter {
    public static Logger logger = LoggerFactory.getLogger(HTTPRequestAdapter.class);
    static class Header {
        public String name;
        public List<String> values;
        public String value() {
            return values.get(0);
        }
    }
    public String host;
    public String path;
    public String contextPath;
    public String servletPath;
    public String queryString;
    public String url;
    public String method;
    public String domain;
    public String remoteAddress;
    public String contentType;
    public String controller;
    public String actionMethod;
    public Integer port;
    public Map<String, Header> headers;
    public Map<String, String> routeArgs;
    public String format;
    public String action;

    public transient Method invokeMethod;
    public transient Class<?> controllerClass;
    public Map<String, Object> args = new HashMap<String,Object>();
    public Date date = new Date();
    public Boolean secure = false;

    public HTTPRequestAdapter() {
        this.headers = new HashMap<String, Header>();
        this.routeArgs = new HashMap<String, String>();
    }

    public String getBase() {
        String protocol = null;
        if (secure)
            protocol = "https";
        else
            protocol = "http";
        if (port == 80 || port == 443)
            return String.format("%s://%s", protocol, domain).intern();
        else
            return String.format("%s://%s:%s", protocol, domain, port).intern();
    }

    public void resolveFormat() {
        if(StringUtils.isEmpty(format)){
            return;
        }
        if(headers.get("accept") == null) {
            format = "html";
            return ;
        }else {
            String accept = headers.get("accept").value();
            if(accept.contains("application/xhtml") || accept.contains("text/html") || accept.startsWith("*/*")) {
                format = "html";
                return ;
            }
            if(accept.contains("application/xml") || accept.contains("text/xml")) {
                format = "xml";
                return ;
            }
            if(accept.contains("text/plain")) {
                format = "txt";
                return ;
            }
            if(accept.contains("application/json") || accept.contains("text/javascript")) {
                format = "json";
            }
            if(accept.endsWith("*/*")) {
                format = "html";
            }
        }
    }

    public static HTTPRequestAdapter parseRequest(HttpServletRequest servletRequest) {
        HTTPRequestAdapter request = new HTTPRequestAdapter();
        request.method = servletRequest.getMethod().intern();
        if(servletRequest.getPathInfo() != null) {
            request.path = servletRequest.getPathInfo();
        }else {
            request.path = servletRequest.getServletPath();
        }
        if(servletRequest.getServletPath() != null) {
            request.servletPath = servletRequest.getServletPath();
        }else {
            request.servletPath = "";
        }
        if(servletRequest.getContextPath() != null) {
            request.contextPath = servletRequest.getContextPath();
        }else {
            request.contextPath = "";
        }
        if(servletRequest.getQueryString() != null) {
            request.queryString = servletRequest.getQueryString();
        }else {
            request.queryString = "";
        }
        logger.trace("contextPath: " + request.contextPath + " ,servletPath: " + request.servletPath);
        logger.trace("request path: " + request.path + " ,request queryString: " + request.queryString);
        if(servletRequest.getHeader("Content-Type") != null) {
            String[] contentType = servletRequest.getHeader("Content-Type").split(";");
            if(contentType.length > 0) {
                request.contentType = contentType[0].trim();
            }
        }
        if(request.contentType == null) {
            request.contentType = "text/html";
        }
        if(servletRequest.getHeader("X-HTTP-Method-Override") != null) {
            request.method = servletRequest.getHeader("X-HTTP-Method-Override");
        }
        request.secure = servletRequest.isSecure();
        request.url = servletRequest.getRequestURI();
        request.host = servletRequest.getHeader("host");
        if(request.host.contains(":")) {
            request.port = Integer.parseInt(request.host.split(":")[1]);
            request.domain = request.host.split(":")[0];
        } else {
            request.port = 80;
            request.domain = request.host;
        }
        request.remoteAddress = servletRequest.getRemoteAddr();
        Enumeration<String> headerNames = servletRequest.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            Header header = new Header();
            header.name = headerNames.nextElement();
            header.values = new ArrayList<String>();
            Enumeration<String> headers = servletRequest.getHeaders(header.name);
            while(headers.hasMoreElements()) {
                header.values.add(headers.nextElement());
            }
            request.headers.put(header.name.toLowerCase(), header);
        }
        request.resolveFormat();
        return request;
    }

    public static HTTPRequestAdapter current() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        Assert.notNull(requestAttributes, "Could not find current request via RequestContextHolder");
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        Assert.state(request != null, "Could not find current HttpServletRequest");
        return HTTPRequestAdapter.parseRequest(request);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getServletPath() {
        return servletPath;
    }

    public void setServletPath(String servletPath) {
        this.servletPath = servletPath;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getActionMethod() {
        return actionMethod;
    }

    public void setActionMethod(String actionMethod) {
        this.actionMethod = actionMethod;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Map<String, Header> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Header> headers) {
        this.headers = headers;
    }

    public Map<String, String> getRouteArgs() {
        return routeArgs;
    }

    public void setRouteArgs(Map<String, String> routeArgs) {
        this.routeArgs = routeArgs;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Method getInvokeMethod() {
        return invokeMethod;
    }

    public void setInvokeMethod(Method invokeMethod) {
        this.invokeMethod = invokeMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public Map<String, Object> getArgs() {
        return args;
    }

    public void setArgs(Map<String, Object> args) {
        this.args = args;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getSecure() {
        return secure;
    }

    public void setSecure(Boolean secure) {
        this.secure = secure;
    }
}
