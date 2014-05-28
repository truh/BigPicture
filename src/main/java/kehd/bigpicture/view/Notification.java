package kehd.bigpicture.view;

import javax.jws.WebService;

/**
 * Created by jakob on 5/28/14.
 */
@WebService
public class Notification {
    public String getNotifications() {
        return "Notification::getNotifications";
    }
    public String deleteNotification(String notificationId) {
        return "Notification::deleteNotification";
    }
}
