package kehd.bigpicture.logic;

import kehd.bigpicture.exceptions.NotAuthentificated;
import kehd.bigpicture.model.User;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.Base64;

/**
 * Created by jakob on 6/3/14.
 */
public class Authentificator {
    private static final Logger log = Logger.getLogger(Authentificator.class);
    private EntityManagerFactory entityManagerFactory;

    public Authentificator(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     *
     * @param basicAuthorisationString
     * @return Username
     */
    public String authentificate(String basicAuthorisationString) throws NotAuthentificated {
        try {
            String auth = new String(
                    Base64.getDecoder().decode(
                            basicAuthorisationString.split(" ")[1].getBytes()
                    )
            );

            String[] auths = auth.split(":");
            String username = auths[0];
            String password = auths[1];

            // User Datensatz aus Datenbank holen falls vorhanden.
            EntityManager manager = entityManagerFactory.createEntityManager();
            manager.getTransaction().begin();
            TypedQuery<User> query = manager.createQuery(
                    "SELECT DISTINCT User " +
                            "FROM User " +
                            "WHERE User.name = :userName", User.class);

            query = query.setParameter("userName", username);
            User user = query.getSingleResult();

            boolean valid = BCrypt.checkpw(password, user.getPassword());

            if(!valid) {
                throw new NotAuthentificated();
            }

            return valid?username:null;
        } catch (Throwable t) {
            throw new NotAuthentificated(t);
        }
    }
}
