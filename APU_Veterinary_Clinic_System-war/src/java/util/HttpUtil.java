package util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HttpUtil {

    public static void invalidateSessions(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();
    }

}
