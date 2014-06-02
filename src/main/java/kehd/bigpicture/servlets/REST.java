package kehd.bigpicture.servlets;

import kehd.bigpicture.Main;
import kehd.bigpicture.logic.Executor;
import kehd.bigpicture.logic.commands.events.*;
import kehd.bigpicture.logic.commands.notifications.DeleteNotification;
import kehd.bigpicture.logic.commands.notifications.GetNotifications;
import kehd.bigpicture.logic.commands.user.Register;
import org.apache.log4j.Logger;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet zum Empfang der der REST Anfragen.
 */
public class REST extends HttpServlet {
    private static final Logger log = Logger.getLogger(REST.class);

    private boolean test;
    private Executor executor;

    @Override
    public void init() {
        log.info("Initialising REST Servlet");

        // check if servlet should be started in testmode
        test = false;
        if(System.getProperty("bp-test") != null) {
            test = System.getProperty("bp-test").toLowerCase().equals("true");
        }

        if(!test) {
            executor = new Executor(); 
            registerCommands(executor); 
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws
            ServletException,
            IOException
    {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws
            ServletException,
            IOException
    {
        processRequest(request, response);
    }

    /**
     * Answers both get and post requests
     *
     * @param request request
     * @param response  response callback
     * @throws IOException
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws
            IOException
    {
        log.info("AuthType: " + request.getAuthType());
        log.info("REST#processRequest");

        String methodName = request.getParameter("method");
        methodName = methodName==null?"":methodName;

        response.setContentType("application/json");

        PrintWriter out = response.getWriter();
        if(test) {
            switch (methodName) {
                case "getEvents":
                    out.write("{\"error\":\"None\", \"result\":[" +
                            "{" +
                            "   \"title\"       :\"BigPicture Coden\"," +
                            "   \"owner\"       :\"Martin\"," +
                            "   \"appointments\":[" +
                            "       \"2014-05-29T10:00:00\"," +
                            "       \"2014-05-31T10:00:00\"," +
                            "       \"2014-06-01T10:00:00\"" +
                            "   ]," +
                            "   \"participants\":[" +
                            "       {\"username\":\"Martin\"," +
                            "        \"date\":\"2014-05-29T10:00:00\"}," +
                            "       {\"username\":\"Matthias\"," +
                            "        \"date\":\"2014-05-29T10:00:00\"}," +
                            "       {\"username\":\"Daniel\"," +
                            "        \"date\":\"2014-05-29T10:00:00\"}," +
                            "       {\"username\":\"Jakob\"," +
                            "        \"date\":\"2014-05-29T10:00:00\"}" +
                            "   ]," +
                            "   \"comments\"    :[" +
                            "       {\"timestamp\":\"2014-05-28T10:00:00\"," +
                            "        \"author\":\"Matthias\"," +
                            "        \"content\":\"Blulluuub\"}" +
                            "   ]" +
                            "}," +
                            "{" +
                            "   \"title\"       :\"[ITP] Test\"," +
                            "   \"owner\"       :\"wkristufek\"," +
                            "   \"appointments\":[" +
                            "       \"2014-06-03T10:40:00\"" +
                            "   ]," +
                            "   \"participants\":[" +
                            "   ]," +
                            "   \"comments\"    :[" +
                            "   ]" +
                            "}," +
                            "{" +
                            "   \"title\"       :\"[D] Test Epochen\"," +
                            "   \"owner\"       :\"Martin\"," +
                            "   \"appointments\":[" +
                            "       \"2014-06-03T13:00:00\"" +
                            "   ]," +
                            "   \"participants\":[]," +
                            "   \"comments\"    :[]" +
                            "}," +
                            "{" +
                            "   \"title\"       :\"[GGP] Geschichte Test\"," +
                            "   \"owner\"       :\"Martin\"," +
                            "   \"appointments\":[" +
                            "       \"2014-06-04T12:00:00\"" +
                            "   ]," +
                            "   \"participants\":[]," +
                            "   \"comments\"    :[]" +
                            "}" +
                            "]}");
                    break;
                default:
                    out.println("{\"error\":{" +
                            "\"message\":\"Method not found\"" +
                            "\"code\":0" +
                            "}, \"result\":\"None\"}");
            }
        } else {
            if(executor.getCommandNames().contains(methodName)) {
                Map param = new HashMap(request.getParameterMap());
                String result;
                result = executor.execute(methodName, request.getParameterMap());
                out.println(result);
            } else {
                out.println("{\"error\":{" +
                            "\"message\":\"Method not found\"" +
                            "\"code\":0" +
                            "}, \"result\":\"None\"}");    
            }
        }
    }

    @Override
    public void destroy()
    {
    }

    private void registerCommands(Executor executor) {
        EntityManagerFactory entityManagerFactory = Main.getEntityManagerFactory();

        //                      _
        //  _____   _____ _ __ | |_ ___
        // / _ \ \ / / _ \ '_ \| __/ __|
        //|  __/\ V /  __/ | | | |_\__ \
        // \___| \_/ \___|_| |_|\__|___/

        // AddComment
        AddComment addComment = new AddComment(entityManagerFactory);
        executor.registerCommand(addComment, "addComment");

        // Create
        Create create = new Create(entityManagerFactory);
        executor.registerCommand(create, "create");

        // GetComments
        GetComments getComments = new GetComments(entityManagerFactory);
        executor.registerCommand(getComments, "getComments");

        // GetEvents
        GetEvents getEvents = new GetEvents(entityManagerFactory);
        executor.registerCommand(getEvents, "getEvents");

        // GetInvitations
        GetInvitations getInvitations = new GetInvitations(entityManagerFactory);
        executor.registerCommand(getInvitations, "getInvitations");

        // GetInvitedUsers
        GetInvitedUsers getInvitedUsers = new GetInvitedUsers(entityManagerFactory);
        executor.registerCommand(getInvitedUsers, "getInvitedUsers");

        // GetVotes
        GetVotes getVotes = new GetVotes(entityManagerFactory);
        executor.registerCommand(getVotes, "getVotes");

        // Invite
        Invite invite = new Invite(entityManagerFactory);
        executor.registerCommand(invite, "invite");

        // ReplyInvitation
        ReplyInvitation replyInvitation = new ReplyInvitation(entityManagerFactory);
        executor.registerCommand(replyInvitation, "replyInvitation");

        // Vote
        Vote vote = new Vote(entityManagerFactory);
        executor.registerCommand(vote, "vote");

        //             _   _  __ _           _   _
        // _ __   ___ | |_(_)/ _(_) ___ __ _| |_(_) ___  _ __  ___
        //| '_ \ / _ \| __| | |_| |/ __/ _` | __| |/ _ \| '_ \/ __|
        //| | | | (_) | |_| |  _| | (_| (_| | |_| | (_) | | | \__ \
        //|_| |_|\___/ \__|_|_| |_|\___\__,_|\__|_|\___/|_| |_|___/

        // DeleteNotification
        DeleteNotification deleteNotification = new DeleteNotification(entityManagerFactory);
        executor.registerCommand(deleteNotification, "deleteNotification");

        // GetNotifications
        GetNotifications getNotifications = new GetNotifications(entityManagerFactory);
        executor.registerCommand(getNotifications, "getNotifications");

        // _   _ ___  ___ _ __
        //| | | / __|/ _ \ '__|
        //| |_| \__ \  __/ |
        // \__,_|___/\___|_|

        // Register
        Register register = new Register(entityManagerFactory);
        executor.registerCommand(register, "register");
    }
}
