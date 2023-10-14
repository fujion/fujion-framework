/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2023 Fujion Framework
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * #L%
 */
package org.fujion.core;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.fujion.common.Assert;
import org.fujion.common.Logger;
import org.fujion.common.MiscUtil;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.URL;
import java.util.Enumeration;

/**
 * Utility methods for dealing with servlet requests.
 */
public class RequestUtil {

    private static final Logger log = Logger.create(RequestUtil.class);

    /**
     * Return current HttpServletRequest. Note that this will return null when invoked outside the
     * scope of an execution/request.
     *
     * @see RequestContextHolder#currentRequestAttributes()
     * @return HttpServletRequest, null when invoked outside the scope of an
     *         Execution/ServletRequest
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttrs == null ? null : requestAttrs.getRequest();
    }

    /**
     * Return current HttpSession
     *
     * @return HttpSession, null when invoked outside the scope of a servlet request
     */
    public static HttpSession getSession() {
        return getSession(getRequest());
    }

    /**
     * Return current HttpSession given request.
     *
     * @param request Servlet request object.
     * @return HttpSession or null if none present.
     */
    public static HttpSession getSession(ServletRequest request) {
        return request instanceof HttpServletRequest ? ((HttpServletRequest) request).getSession(false) : null;
    }

    /**
     * Logs at trace level the request headers
     */
    public static void logHeaderNames() {
        HttpServletRequest request = getRequest();
        
        if (request == null) {
            log.debug("logHeaderNames() invoked outside the scope of a servlet request");
        } else if (log.isTraceEnabled()) {
            Enumeration<?> enumeration = request.getHeaderNames();
            
            while (enumeration.hasMoreElements()) {
                String headerName = (String) enumeration.nextElement();
                log.trace(() -> String.format("HeaderName: %s", headerName));
            }
        }
    }

    /**
     * Return server name.
     *
     * @see HttpServletRequest#getServerName()
     * @return server name
     */
    public static String getServerName() {
        HttpServletRequest request = getRequest();
        return request == null ? null : request.getServerName();
    }

    /**
     * Return local host IP. Note: HttpServletRequest#getLocalAddr() doesn't seem to be consistent.
     * This method uses java.net.InetAddress.
     *
     * @see InetAddress#getHostAddress()
     * @return server IP
     */
    public static String getLocalHostAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            log.debug("Exception occurred obtaining localhost IP address", e);
            return null;
        }
    }

    /**
     * Return client's ip address. Returns null if invoked outside scope of a servlet request.
     * <p>
     * This considers header X-FORWARDED-FOR (i.e. useful if behind a proxy)
     *
     * @return the client's IP
     */
    public static String getRemoteAddress() {
        HttpServletRequest request = getRequest();
        String ipAddress = null;

        if (request != null) {
            ipAddress = request.getHeader("x-forwarded-for");
            boolean ipFromHeader = true;

            if (isEmpty(ipAddress)) {
                ipAddress = request.getHeader("X_FORWARDED_FOR");

                if (isEmpty(ipAddress)) {
                    ipFromHeader = false;
                    ipAddress = request.getRemoteAddr();
                }

                logHeaderNames();
            }
            //log headers in case we find a case where above logic doesn't return correct ip
            if (log.isTraceEnabled()) {
                logHeaderNames();
                log.trace(
                    String.format("Remote address: %s; obtained from X-FORWARDED_FOR header: %b", ipAddress, ipFromHeader));
            }
        }
        return ipAddress;
    }

    /**
     * Returns the full base URL for the servlet.
     *
     * @param request The servlet request.
     * @return The full base URL.
     */
    public static String getBaseURL(HttpServletRequest request) {
        return "http" + (request.isSecure() ? "s" : "") + "://" + request.getServerName() + ":" + request.getServerPort()
        + request.getContextPath() + "/";
    }
    
    /**
     * Returns the resolved URL of the servlet request path.
     *
     * @param request The servlet request.
     * @return The resolved URL.
     */
    public static URL getResourceURL(HttpServletRequest request) {
        return getResourceURL(request.getPathInfo());
    }

    /**
     * Returns the resolved URL for the specified path.
     *
     * @param path Path to resolve.
     * @return The resolved URL.
     */
    public static URL getResourceURL(String path) {
        try {
            return ResourceUtils.getURL(path.startsWith("/web/") ? "classpath:" + path : path);
        } catch (FileNotFoundException e) {
            throw MiscUtil.toUnchecked(e);
        }
    }

    /**
     * Get current request's session id or null if session has not yet been created or if invoked
     * outside the scope of an Execution/ServletRequest.
     *
     * @return String representing session id or null if session has not yet been created
     */
    public static String getSessionId() {
        HttpSession session = getSession(getRequest());
        return session == null ? null : session.getId();
    }

    /**
     * Return request, throwing IllegalStateException if invoked outside the scope of an
     * Execution/ServletRequest
     *
     * @return HttpServletRequest
     * @throws IllegalStateException if called outside scope of an HttpServletRequest
     */
    public static HttpServletRequest assertRequest() {
        HttpServletRequest request = getRequest();
        Assert.state(request != null, () -> "Method must be invoked within the scope of a servlet request");
        return request;
    }

    private static boolean isEmpty(String s) {
        return StringUtils.isEmpty(s) || "unknown".equalsIgnoreCase(s);
    }

    /**
     * Enforce static class.
     */
    private RequestUtil() {
    }
}
