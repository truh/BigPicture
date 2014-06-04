package kehd.bigpicture.logic.commands.user;

import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.UserAlreadyExists;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.model.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
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
    public JsonNodeBuilder execute(String username, Map<String, String> params) throws UserAlreadyExists {
        EntityManager manager = entityManagerFactory.createEntityManager();

        String password = params.get("password");

        String passHash = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User();
        user.setName(username);
        user.setPassword(passHash);

        manager.getTransaction().begin();

        boolean userExists = true;
        try {
            User user2 = manager.createQuery(
                "SELECT DISTINCT User " +
                        "FROM User " +
                        "WHERE User.name = :userName", User.class)
                .setParameter("userName", username)
                .getSingleResult();
        } catch (NoResultException nre) {
            userExists = false;
        }

        if(userExists) {
            throw new UserAlreadyExists();
        }

        try {
            manager.persist(user);
        } catch (EntityExistsException eee) {
            throw new UserAlreadyExists(eee);
        }
        manager.getTransaction().commit();

        JsonNodeBuilder result = aStringBuilder("Okay!");

        return result;
    }
}
