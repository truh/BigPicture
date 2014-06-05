package kehd.bigpicture.logic.commands.user;

import argo.jdom.JsonArrayNodeBuilder;
import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.FieldMissing;
import kehd.bigpicture.logic.commands.Command;
import kehd.bigpicture.model.Appointment;
import kehd.bigpicture.model.Comment;
import kehd.bigpicture.model.Event;
import kehd.bigpicture.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static argo.jdom.JsonNodeBuilders.aStringBuilder;
import static argo.jdom.JsonNodeBuilders.anArrayBuilder;
import static argo.jdom.JsonNodeBuilders.anObjectBuilder;

/**
 *
 */
public class FindUser implements Command {
    private EntityManagerFactory entityManagerFactory;

    public FindUser(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public JsonNodeBuilder execute(String username, Map<String, String> params)
            throws FieldMissing {
        String userPatternString = params.get("userPattern");

        if(userPatternString == null) {
            throw new FieldMissing("userPattern");
        }

        Pattern userPattern = null;
        try {
            userPattern = Pattern.compile(userPatternString);
        } catch (PatternSyntaxException pse) {/* no problem */}

        EntityManager manager = entityManagerFactory.createEntityManager();

        List<User> allUsers = manager.createQuery("SELECT User FROM User", User.class)
                .getResultList();

        // uebertragen der matchenden usern
        List<User> matchingUsers = new ArrayList<>();
        for (User user: allUsers) {
            boolean matched = false;
            if(userPattern != null) {
                Matcher matcher = userPattern.matcher(user.getName());
                matched = matcher.matches();
            }
            if(!matched) {
                if (user.getName().contains(userPatternString)) {
                    matched = true;
                }
            }
            if(matched && !matchingUsers.contains(user)) {
                matchingUsers.add(user);
            }
        }

        // erstellen des json
        JsonArrayNodeBuilder arrayNodeBuilder = anArrayBuilder();
        for(User user: matchingUsers) {
            // appointments
            JsonArrayNodeBuilder appointmentsBuilder = anArrayBuilder();
            for(Appointment appointment: user.getAppointments()) {
                appointmentsBuilder.withElement(
                    anObjectBuilder()
                        .withField("eventName", aStringBuilder(appointment.getEvent().getTitle()))
                        .withField("timestamp", aStringBuilder(DATE_FORMAT.format(appointment.getTimestamp())))
                );
            }
            // comments
            JsonArrayNodeBuilder commentsBuilder = anArrayBuilder();
            for(Comment comment: user.getComments()) {
                commentsBuilder.withElement(
                        anObjectBuilder()
                                .withField("timestamp", aStringBuilder(DATE_FORMAT.format(comment.getTimestamp())))
                                .withField("comment", aStringBuilder(comment.getComment()))
                                .withField("author", aStringBuilder(comment.getAuthor().getName()))
                );
            }
            // events
            JsonArrayNodeBuilder eventsBuilder = anArrayBuilder();
            for(Event event: user.getEvents()) {
                eventsBuilder.withElement(anObjectBuilder()
                    .withField("title", aStringBuilder(event.getTitle()))
                    .withField("owner", aStringBuilder(event.getOrganisator().getName()))
                );
            }
            // organised
            JsonArrayNodeBuilder organisedEventsBuilder = anArrayBuilder();
            for(Event event: user.getEvents()) {
                organisedEventsBuilder.withElement(anObjectBuilder()
                    .withField("title", aStringBuilder(event.getTitle()))
                    .withField("owner", aStringBuilder(event.getOrganisator().getName()))
                );
            }
            // user zusammenbauen
            arrayNodeBuilder.withElement(
                    anObjectBuilder()
                            .withField("username", aStringBuilder(user.getName()))
                            .withField("appointments", appointmentsBuilder)
                            .withField("comments", commentsBuilder)
                            .withField("events", eventsBuilder)
                            .withField("organisedEvents", organisedEventsBuilder)
            );
        }

        return arrayNodeBuilder;
    }
}
