package kehd.bigpicture.logic;

import kehd.bigpicture.exceptions.NotAuthentificated;
import kehd.bigpicture.model.User;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Created by jakob on 6/3/14.
 */
public class Authentificator {
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
            String auth = StringUtils.newStringUtf8(
                    Base64.decodeBase64(
                            basicAuthorisationString.split(" ")[1]
                    )
            );
            String[] auths = auth.split(":");
            String username = auths[0];
            String password = auths[1];

            // User Datensatz aus Datenbank holen falls vorhanden.
            EntityManager manager = entityManagerFactory.createEntityManager();
            manager.getTransaction().begin();
            User user = manager.createQuery(
                    "SELECT DISTINCT User " +
                            "FROM User " +
                            "WHERE User.name = :userName", User.class)
                    .setParameter("userName", username)
                    .getSingleResult();

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
