package brorica.gather.config;

import javax.servlet.http.HttpSession;

public class SessionConst {

    public static final String LOGIN_MEMBER = "loginMember";

    public static Long getLoginMemberId(HttpSession session) {
        return Long.parseLong(session.getAttribute(SessionConst.LOGIN_MEMBER).toString());
    }
}
