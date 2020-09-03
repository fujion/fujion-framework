/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2020 Fujion Framework
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

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.fujion.client.ExecutionContext;
import org.fujion.common.Assert;
import org.fujion.common.MiscUtil;
import org.fujion.common.StrUtil;
import org.fujion.servlet.DynamicResourceRegistry;
import org.fujion.servlet.WebAppConfiguration;
import org.fujion.webjar.WebJarResourceResolver;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Utility methods for accessing and manipulating web resources and settings.
 */
public class WebUtil {

    public static final String FUJION_VERSION = WebUtil.class.getPackage().getImplementationVersion();

    public static final String DEFAULT_ETAG = generateETag(FUJION_VERSION);

    /**
     * Returns the debug state of the web application. See additional information
     * {@link org.fujion.servlet.WebAppConfiguration#isDebugEnabled here}.
     *
     * @return The debug state.
     */
    public static boolean isDebugEnabled() {
        return WebAppConfiguration.isDebugEnabled();
    }

    /**
     * Converts the queryString to a map.
     *
     * @param queryString The query string (leading "?" is optional)
     * @return A map containing the parameters from the query string. This may return null if the
     *         queryString is empty. Multiple values for a parameter are separated by commas.
     */
    public static Map<String, String> queryStringToMap(String queryString) {
        return queryStringToMap(queryString, ",");
    }

    /**
     * Converts the queryString to a map.
     *
     * @param queryString The query string (leading "?" is optional)
     * @param valueDelimiter String to use to delimit multiple values for a parameter. May be null.
     * @return A map containing the arguments from the query string. This may return null if the
     *         queryString is empty.
     */
    public static Map<String, String> queryStringToMap(String queryString, String valueDelimiter) {
        if (queryString == null || queryString.isEmpty()) {
            return null;
        }

        try {
            valueDelimiter = valueDelimiter == null ? "" : valueDelimiter;
            URI uri = new URI(queryString.startsWith("?") ? queryString : ("?" + queryString));
            List<NameValuePair> params = URLEncodedUtils.parse(uri, StrUtil.UTF8);

            Map<String, String> result = new HashMap<>();

            for (NameValuePair nvp : params) {
                String value = result.get(nvp.getName());
                result.put(nvp.getName(), (value == null ? "" : value + valueDelimiter) + nvp.getValue());
            }

            return result;
        } catch (URISyntaxException e) {
            return null;
        }
    }

    /**
     * Adds the specified query string to the url.
     *
     * @param url url to receive the query string.
     * @param queryString Query string to add.
     * @return The updated url.
     * @throws IllegalArgumentException if url is null.
     */
    public static String addQueryString(String url, String queryString) {
        Assert.notNull(url, () -> "The url must not be null");

        if (!StringUtils.isEmpty(queryString)) {
            if (url.endsWith("?")) {
                url += queryString;
            } else if (url.contains("?")) {
                url += "&" + queryString;
            } else {
                url += "?" + queryString;
            }
        }

        return url;
    }

    /**
     * Returns the query parameter string from the request url.
     *
     * @return The query parameter string or empty string if none.
     */
    public static String getQueryParams() {
        String requestUrl = getRequestUrl();
        int i = requestUrl == null ? -1 : requestUrl.indexOf("?");
        return i == -1 ? "" : requestUrl.substring(i + 1);
    }

    /**
     * Returns the original request url from the execution context.
     *
     * @return The request url of the browser document.
     */
    public static String getRequestUrl() {
        return ExecutionContext.getPage().getRequestUrl();
    }

    /**
     * Returns the base url from the execution context.
     *
     * @return The base url.
     */
    public static String getBaseUrl() {
        String url = getRequestUrl();
        String path = ExecutionContext.getServletContext().getContextPath();
        int i = url.indexOf(path);
        return url.substring(0, i + path.length()) + "/";
    }
    
    /**
     * Returns the named cookie from the current request.
     *
     * @param cookieName Name of cookie.
     * @see #getCookie(String, HttpServletRequest)
     * @return A cookie, or null if not found.
     */
    public static Cookie getCookie(String cookieName) {
        return getCookie(cookieName, RequestUtil.getRequest());
    }

    /**
     * Returns the named cookie from the specified request. When values are retrieved, they should
     * be decoded.
     *
     * @see #decodeCookieValue(String)
     * @param cookieName Name of cookie
     * @param request Request containing cookie.
     * @return A cookie, or null if not found.
     * @throws IllegalArgumentException if any argument is null.
     */
    public static Cookie getCookie(String cookieName, HttpServletRequest request) {
        Assert.notNull(cookieName, () -> "The cookie name must not be null");
        Assert.notNull(request, () -> "The request must not be null");
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }

        return null;
    }

    /**
     * Returns the value from the named cookie from the specified request. The value is decoded.
     *
     * @see #getCookieValue(String, HttpServletRequest)
     * @param cookieName Name of cookie
     * @return A cookie value, or null if not found.
     */
    public static String getCookieValue(String cookieName) {
        return getCookieValue(cookieName, RequestUtil.getRequest());
    }

    /**
     * Returns the value from the named cookie from the specified request. The value is decoded with
     * for security and consistency (Version 0+ of Cookies and web containers)
     *
     * @see #getCookie(String, HttpServletRequest)
     * @see #decodeCookieValue(String)
     * @param cookieName Name of cookie
     * @param request Request containing cookie.
     * @return A cookie value, or null if not found.
     */
    public static String getCookieValue(String cookieName, HttpServletRequest request) {
        Cookie cookie = getCookie(cookieName, request);
        return cookie == null ? null : decodeCookieValue(cookie.getValue());
    }

    /**
     * <p>
     * Encodes a plain text cookie value.
     * </p>
     * <i>Note: The direction to use a two-phase encode/decode process (i.e. instead of the Base64
     * class URL_SAFE option) was intentional</i>
     *
     * @see URLEncoder#encode(String, String)
     * @see Base64#encodeBase64String(byte[])
     * @param cookieValuePlainText The plain text to encode
     * @return encoded cookie value
     * @throws IllegalArgumentException If the argument is null.
     */
    public static String encodeCookieValue(String cookieValuePlainText) {
        Assert.notNull(cookieValuePlainText, () -> "The cookieValuePlainText must not be null");

        try {
            return URLEncoder.encode(Base64.encodeBase64String(cookieValuePlainText.getBytes()), StrUtil.UTF8_STR);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception occurred encoding cookie value", e);
        }
    }

    /**
     * <p>
     * Decodes an encoded cookie value
     * </p>
     * <i>Note: The direction to use a two-phase encode/decode process (i.e. instead of the Base64
     * class URL_SAFE option) was intentional</i>
     *
     * @see URLDecoder#decode(String, String)
     * @see Base64#decode(String)
     * @param encodedCookieValue The encoded cookie value
     * @return decoded cookie value
     * @throws IllegalArgumentException If the argument is null.
     */
    public static String decodeCookieValue(String encodedCookieValue) {
        Assert.notNull(encodedCookieValue, () -> "The encodedCookieValue must not be null");

        try {
            return new String(Base64.decodeBase64(URLDecoder.decode(encodedCookieValue, StrUtil.UTF8_STR)));
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception occurred decoding cookie value", e);
        }
    }

    /**
     * Sets a cookie into the response. Cookies are URLEncoded for consistency (Version 0+ of
     * Cookies)
     *
     * @param cookieName Name of cookie.
     * @param value Value of cookie. If null, the cookie is removed from the client if it exists.
     * @param response Response object.
     * @param request Request object.
     * @return Newly created cookie.
     * @throws IllegalArgumentException if cookieName, response, or request arguments are null.
     */
    public static Cookie setCookie(String cookieName, String value, HttpServletResponse response,
                                   HttpServletRequest request) {
        Assert.notNull(response, () -> "The response must not be null");
        Cookie cookie = getCookie(cookieName, request);

        if (value != null) {
            value = encodeCookieValue(value);
        }

        if (cookie == null) {
            if (value == null) {
                return null;
            }
            cookie = new Cookie(cookieName, value);
        } else if (value == null) {
            cookie.setMaxAge(0);
        } else {
            cookie.setValue(value);
        }

        if (request.isSecure()) {
            cookie.setSecure(true);
        }

        response.addCookie(cookie);
        return cookie;
    }

    /**
     * Returns the resource corresponding to the source URL.
     *
     * @param src The URL of the source.
     * @return The corresponding resource.
     */
    public static Resource getResource(String src) {
        return getResource(src, ExecutionContext.getServletContext());
    }

    /**
     * Returns the resource corresponding to the source URL.
     *
     * @param src The URL of the source.
     * @param ctx The active servlet context.
     * @return The corresponding resource.
     */
    public static Resource getResource(String src, ServletContext ctx) {
        try {
            Resource resource;

            if (src.startsWith("dynamic/") || src.startsWith("/dynamic/")) {
                String path = StringUtils.substringAfter(src, "dynamic/");
                resource = DynamicResourceRegistry.getInstance().getResource(path);
            } else if (src.startsWith("web/") || src.startsWith("/web/")) {
                resource = new ClassPathResource(src);
            } else if (src.matches("^.*:/.*")) {
                resource = new UrlResource(src);
            } else {
                src = src.startsWith("/") ? src : "/" + src;

                if (src.startsWith("/webjars/")) {
                    src = "/webjars/" + WebJarResourceResolver.getResourcePath(src.substring(9));
                }

                URL url = ctx == null ? null : ctx.getResource(src);
                resource = url == null ? null : new UrlResource(url);
            }

            if (resource == null || !resource.exists()) {
                throw new FileNotFoundException(src);
            }

            return resource;

        } catch (Exception e) {
            throw MiscUtil.toUnchecked(e);
        }
    }

    /**
     * Add headers to suppress browser caching.
     *
     * @param response HTTP response to receive headers.
     */
    public static void disableCache(HttpServletResponse response) {
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        response.setHeader(HttpHeaders.PRAGMA, "no-cache");
        response.setDateHeader(HttpHeaders.EXPIRES, 0);
    }

    /**
     * Formats an ETag into form suitable for storing in a header. If null or already formatted,
     * returns the original value.
     *
     * @param etag The ETag to format.
     * @param weak If true, mark ETag as weak.
     * @return The formatted ETag.
     */
    public static String formatETag(String etag, boolean weak) {
        etag = StringUtils.trimToNull(etag);

        if (etag == null || etag.startsWith(StrUtil.DQT) || etag.startsWith("W/\"")) {
            return etag;
        }

        return (weak ? "W/" : "") + StrUtil.enquoteDouble(etag);
    }

    /**
     * Add ETag to response.
     *
     * @param response HTTP response to receive ETag.
     * @param etag ETag to add.
     * @param weak If true, mark ETag as weak.
     * @return The ETag value that was added.
     */
    public static String addETag(HttpServletResponse response, String etag, boolean weak) {
        response.setHeader(HttpHeaders.ETAG, formatETag(etag, weak));
        return getETag(response);
    }

    /**
     * Returns an ETag from an HTTP request.
     *
     * @param request HTTP request.
     * @return The ETag value, or null if none.
     */
    public static String getETag(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.IF_NONE_MATCH);
    }

    /**
     * Returns an ETag from an HTTP response.
     *
     * @param response HTTP response.
     * @return The ETag value, or null if none.
     */
    public static String getETag(HttpServletResponse response) {
        return response.getHeader(HttpHeaders.ETAG);
    }

    /**
     * Returns true if the request and response ETags match.
     *
     * @param request The HTTP request.
     * @param response The HTTP response.
     * @return True if the ETags match.
     */
    public static boolean sameETag(HttpServletRequest request, HttpServletResponse response) {
        return sameETag(getETag(request), getETag(response));
    }

    /**
     * Returns true if the request and response ETags match.
     *
     * @param requestETag The request ETag.
     * @param responseETag The response ETag.
     * @return True if the ETags match.
     */
    public static boolean sameETag(String requestETag, String responseETag) {
        requestETag = StringUtils.removeStart(requestETag, "W/");
        responseETag = StringUtils.removeStart(responseETag, "W/");
        return requestETag != null && ("*".equals(requestETag) || requestETag.equals(responseETag));
    }

    /**
     * Converts a value to an ETag value. If the value is null, empty or contains the text
     * "SNAPSHOT", returns a random ETag. Otherwise, computes an ETag by calculating the MD5
     * checksum on the value.
     *
     * @param value Value from which an ETag will be generated.
     * @return An ETag value (never null).
     */
    public static String generateETag(String value) {
        value = StringUtils.trimToNull(value);
        return value == null || value.contains("SNAPSHOT") ? randomETag() : DigestUtils.md5Hex(value);
    }

    /**
     * Returns a random ETag value.
     *
     * @return A random ETag value.
     */
    public static String randomETag() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * Enforce static class.
     */
    private WebUtil() {
    }
}
