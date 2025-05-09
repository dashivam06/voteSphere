package com.voteSphere.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {
	

	public static Cookie createCookie(String name, String value) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		return cookie;
	}

	public static Cookie createCookie(String name, String value, Integer maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		cookie.setSecure(true);
		return cookie;
	}

	public static void addUserRoleCookie(HttpServletResponse response, String role) {
        CookieUtil.addCookie(response, CookieUtil.createCookie("user_role", role, 24 * 60 * 60));
    }

    public static void addLoginTimeCookie(HttpServletResponse response) {
        String loginTime = String.valueOf(System.currentTimeMillis());
        CookieUtil.addCookie(response, CookieUtil.createCookie("login_time", loginTime, 24 * 60 * 60));
    }

    public static void addRememberMeCookie(HttpServletRequest request, HttpServletResponse response, String userId) {
        String rememberMe = request.getParameter("remember_me");
        System.out.print(rememberMe);
        if ("on".equals(rememberMe)) {
            Cookie rememberCookie = CookieUtil.createCookie("remember_me", userId, 7 * 24 * 60 * 60); // 7 days
            CookieUtil.addCookie(response, rememberCookie);
        }
    }
    
    
	public static Cookie createCookie(String name, String value, Integer maxAge, boolean secure) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		cookie.setSecure(secure);
		return cookie;
	}

	public static Cookie createSecureCookie(String name, String value) {
		Cookie cookie = createCookie(name, value);
		cookie.setSecure(true);
		return cookie;
	}

	public static Cookie createPersistentCookie(Cookie cookie, int maxAge) {
		cookie.setMaxAge(maxAge);
		return cookie;
	}

	public static String getCookieValue(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					return cookie;
				}
			}
		}
		return null;
	}

	public static void addCookie(HttpServletResponse response, Cookie cookie) {
		response.addCookie(cookie);
	}

	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		Cookie cookie = getCookie(request, name);
		if (cookie != null) {
			cookie.setValue("");
			cookie.setPath("/");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
	}

	public static boolean cookieExists(HttpServletRequest request, String name) {
		return getCookie(request, name) != null;
	}

	public static Cookie setDomain(Cookie cookie, String domain) {
		cookie.setDomain(domain);
		return cookie;
	}

	public static Cookie setPath(Cookie cookie, String path) {
		cookie.setPath(path);
		return cookie;
	}

	public static Cookie setMaxAge(Cookie cookie, int maxAge) {
		cookie.setMaxAge(maxAge);
		return cookie;
	}

	public static Cookie setHttpOnly(Cookie cookie, boolean httpOnly) {
		cookie.setHttpOnly(httpOnly);
		return cookie;
	}

	public static Cookie setSecure(Cookie cookie, boolean secure) {
		cookie.setSecure(secure);
		return cookie;
	}

	public static void deleteAllCookies(HttpServletRequest request, HttpServletResponse response) {

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				Cookie cleared = new Cookie(cookie.getName(), "");
				cleared.setMaxAge(0);
				cleared.setPath("/");
				cleared.setHttpOnly(cookie.isHttpOnly());
				cleared.setSecure(cookie.getSecure());
				response.addCookie(cleared);
			}
		}
	}
	
	
}