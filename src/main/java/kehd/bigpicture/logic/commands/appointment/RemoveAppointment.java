package kehd.bigpicture.logic.commands.appointment;

import argo.jdom.JsonNodeBuilder;
import kehd.bigpicture.exceptions.FieldMissing;
import kehd.bigpicture.exceptions.NotAuthentificated;
import kehd.bigpicture.logic.commands.Command;

import javax.persistence.EntityManagerFactory;
import java.util.Map;

import static argo.jdom.JsonNodeBuilders.aStringBuilder;

/**
 *
 */
public class RemoveAppointment implements Command {
    private EntityManagerFactory entityManagerFactory;

    public RemoveAppointment(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public JsonNodeBuilder execute(String username, Map<String, String> params)
            throws NotAuthentificated, FieldMissing {
        if(username == null) {
            throw new NotAuthentificated();
        }

        // authentifizierung ueberpruefen
        if(username == null) {
            throw new NotAuthentificated();
        }

        // parameter ueberpruefen
        String timestamp = params.get("timestamp");
        if(timestamp == null) {
            throw new FieldMissing("timestamp");
        }

        String eventName = params.get("eventName");
        if(eventName == null) {
            throw new FieldMissing("eventName");
        }

        // event object holen
        // event object ueberpruefen
        // ueberpruefen: username == event.organisator
        // Appointment entfernen
        // persistieren
        // notification erzeugen und persistieren

        return aStringBuilder("Okay!");
    }
}
