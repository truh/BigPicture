package kehd.bigpicture.logic.commands.events;

import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.logic.commands.Command;

import javax.persistence.EntityManagerFactory;
import java.util.Map;

public class GetInvitations implements Command {
    private EntityManagerFactory entityManagerFactory;

    public GetInvitations(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    @Override
    public JsonNodeBuilder execute(Map<String, String> params) {
        return null;
    }
}
