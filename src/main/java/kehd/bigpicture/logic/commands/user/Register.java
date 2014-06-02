package kehd.bigpicture.logic.commands.user;

import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.UserAlreadyExists;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.model.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Map;

import static argo.jdom.JsonNodeBuilders.aStringBuilder;

/**
 * Created by jakob on 6/2/14.
 */
public class Register implements Command {
    private EntityManagerFactory entityManagerFactory;

    public Register(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public JsonNodeBuilder execute(Map<String, String> params) throws UserAlreadyExists {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        String username = params.get("username");
        String password = params.get("password");

        String passHash = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User();
        user.setName(username);
        user.setPassword(passHash);

        entityManager.getTransaction().begin();
        try {
            entityManager.persist(user);
        } catch (EntityExistsException eee) {
            throw new UserAlreadyExists(eee);
        }
        entityManager.getTransaction().commit();

        JsonNodeBuilder result = aStringBuilder("Okay!"); // TODo, maybe there is a
                                                          // a better positive response
        return result;
    }
}
