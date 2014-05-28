package kehd.bigpicture.view;

import javax.jws.WebService;
import java.util.Date;

/**
 * Created by jakob on 5/28/14.
 */
@WebService
public class Events {
    public String create(String eventName, String eventTyp) {
        return "Events::create";
    }
    public String invite(String eventName, Date date, String user) {    // todo change request
        return "Events::invite";                                                        // only 1 user
    }
    public String replyInvitation(String eventName, boolean accept) {
        return "Events::replyInvitation";
    }
    public String getInvitations(String eventName) {
        return "Events::getInvitations";
    }
    public String vote(String eventName, Date date) {
        return "Events::vote";
    }
    public String getEvents() {
        return "Events::getEvents";
    }
    public String getInvitedUsers(String eventName) {
        return "Events::getInvitedUsers";
    }
    public String getVotes(String eventName) {
        return "Events::getVotes";
    }
    public String getComments(String eventName) {
        return "Events::getComments";
    }
    public String addComment(String eventName, String title, String content) {
        return "Events::addComment";
    }
}
