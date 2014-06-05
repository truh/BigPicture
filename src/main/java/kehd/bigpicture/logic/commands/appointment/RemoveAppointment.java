package kehd.bigpicture.logic.commands.appointment;

import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.logic.commands.Command;

import javax.persistence.EntityManagerFactory;
import java.util.Map;

import static argo.jdom.JsonNodeBuilders.aStringBuilder;

/**
 * Created by jakob on 6/5/14.
 */
public class RemoveAppointment implements Command {
    private EntityManagerFactory entityManagerFactory;

    public RemoveAppointment(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public JsonNodeBuilder execute(String username, Map<String, String> params) {
        return aStringBuilder("Okay!");
    }
}
