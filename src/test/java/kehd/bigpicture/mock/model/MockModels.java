package kehd.bigpicture.mock.model;

import kehd.bigpicture.model.*;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockModels {

    public static Appointment appointment0;
    public static Appointment appointment1;
    public static Comment comment0;
    public static Comment comment1;
    public static Event event0;
    public static Event event1;
    public static Notification notification0;
    public static Notification notification1;
    public static User user0;
    public static String password0;
    public static User user1;
    public static String password1;

    static {
        // initialisieren

        appointment0 = new Appointment();
        appointment1 = new Appointment();

        comment0 = new Comment();
        comment1 = new Comment();

        event0 = new Event();
        event1 = new Event();

        notification0 = new Notification();
        notification1 = new Notification();

        user0 = new User();
        user1 = new User();

        // statische daten

        appointment0.setId(0);
        appointment0.setTimestamp(new Date());

        appointment1.setId(1);
        appointment1.setTimestamp(new Date());

        comment0.setComment("comment0");
        comment0.setId(0);
        comment0.setTimestamp(new Date());

        comment1.setComment("comment1");
        comment1.setId(1);
        comment1.setTimestamp(new Date());

        event0.setId(0);
        event0.setTitle("event0");
        event0.setType(EventType.MULTI);

        event1.setId(1);
        event1.setTitle("event1");
        event1.setType(EventType.SINGLE);

        notification0.setId(0);
        notification0.setMessage("notification0");
        notification0.setTimestamp(new Date());
        notification0.setType(NotificationType.ALL_USERS_HAVE_CHOSEN);

        notification1.setId(1);
        notification1.setMessage("notification1");
        notification1.setTimestamp(new Date());
        notification1.setType(NotificationType.DEL_INVITATION);

        user0.setId(0);
        user0.setName("user0");
        password0 = "password0";
        user0.setPassword(BCrypt.hashpw(password0, BCrypt.gensalt()));

        user1.setId(1);
        user1.setName("user1");
        password1 = "password1";
        user1.setPassword(BCrypt.hashpw(password1, BCrypt.gensalt()));

        // relationen

        appointment0.setEvent(event0);

        appointment1.setEvent(event1);

        comment0.setAuthor(user0);
        comment0.setEvent(event0);

        comment1.setAuthor(user1);
        comment1.setEvent(event1);

        event0.setAppointments(list(appointment0));
        event0.setComments(list(comment0));
        event0.setNotifications(list(notification0));
        event0.setOrganisator(user0);
        event0.setUsers(list(user1));

        event1.setAppointments(list(appointment1));
        event1.setComments(list(comment1));
        event1.setNotifications(list(notification1));
        event1.setOrganisator(user1);
        event1.setUsers(list(user0));

        notification0.setEvent(event0);
        notification0.setRecipients(list(user0));

        notification1.setEvent(event1);
        notification1.setRecipients(list(user1));

        user0.setAppointments(list(appointment0));
        user0.setComments(list(comment0));
        user0.setEvents(list(event1));
        user0.setOrganisedEvents(list(event0));

        user1.setAppointments(list(appointment1));
        user1.setComments(list(comment1));
        user1.setEvents(list(event0));
        user1.setOrganisedEvents(list(event1));
    }

    /** Ich bin genial **/
    public static <X> List<X> list(X... elements) {
        ArrayList<X> arrayList = new ArrayList<>();
        for(X element: elements) {
            arrayList.add(element);
        }
        return arrayList;
    }
}
