package kehd.bigpicture.logic.commands.notifications;

import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.model.Appointment;
import kehd.bigpicture.model.Notification;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class GetNotifications implements Command {
    private EntityManagerFactory entityManagerFactory;

    public GetNotifications(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public JsonNodeBuilder execute(String username, Map<String, String> params) {
        EntityManager manager = entityManagerFactory.createEntityManager();

        manager.getTransaction().begin();
        List<Notification> notiList = manager.createQuery (
                "SELECT Notification FROM Notification ", Notification.class).getResultList();

        List<Notification> resultList = new ArrayList<>();
        for(Notification notification: notiList) {
            boolean allowed;
            if()
        }

        return null;
    }
}
