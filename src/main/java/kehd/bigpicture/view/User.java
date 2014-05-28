package kehd.bigpicture.view;

import javax.jws.WebService;

/**
 * Created by jakob on 5/28/14.
 */
@WebService
public class User {
    public String register(String username, String password) {
        return "User::register";
    }
    public String login(String username, String password) {
        return "User::login";
    }
    public String logout() {
        return "User::logout";
    }
}
