package kehd.bigpicture.test.logic.commands.notifications;

import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.logic.commands.notifications.GetNotifications;
import kehd.bigpicture.mock.EntityManagerAdapter;
import kehd.bigpicture.model.Event;
import kehd.bigpicture.model.Notification;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashMap;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class GetNotifications_Test {
    @Test
    public void execution() {
        EntityManagerFactory emf = mock(EntityManagerFactory.class);
        doReturn(new EntityManagerAdapter() {
            @Override
            public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
                TypedQuery<T> query = mock(TypedQuery.class);

                ArrayList<Notification> notifications = new ArrayList<Notification>(){{
                    Event e1 = new Event();
                    e1.setId(1);
                    e1.setTitle("Event1");

                    Event e2 = new Event();
                    e1.setId(2);
                    e1.setTitle("Event2");

                    Notification n1 = new Notification();
                    n1.setId(1);
                    n1.setMessage("Message1");
                    n1.setEvent(e1);
                    add(n1);

                    Notification n2 = new Notification();
                    n2.setId(2);
                    n2.setMessage("Message2");
                    n2.setEvent(e1);
                    add(n2);

                    Notification n3 = new Notification();
                    n3.setId(3);
                    n3.setMessage("Message3");
                    n3.setEvent(e2);
                    add(n3);

                    Notification n4 = new Notification();
                    n4.setId(4);
                    n4.setMessage("Message4");
                    n4.setEvent(e2);
                    add(n4);
                }};

                doReturn(notifications).when(query).getResultList();

                return query;
            }
        })
                .when(emf)
                .createEntityManager();
        GetNotifications getNotifications = new GetNotifications(emf);

        JsonNodeBuilder nodeBuilder = getNotifications.execute(new HashMap<String, String>());
    }
}