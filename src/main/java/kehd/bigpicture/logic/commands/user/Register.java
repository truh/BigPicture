package kehd.bigpicture.logic.commands.user;

import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.logic.commands.Command;

import javax.persistence.EntityManagerFactory;
import java.util.Map;

/**
 * Created by jakob on 6/2/14.
 */
public class Register implements Command {
    private EntityManagerFactory entityManagerFactory;

    public Register(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public JsonNodeBuilder execute(Map<String, String> params) {
        return null;
    }
}
