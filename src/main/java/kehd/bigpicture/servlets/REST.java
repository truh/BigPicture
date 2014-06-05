package kehd.bigpicture.servlets;

import kehd.bigpicture.Main;
import kehd.bigpicture.exceptions.UnableToLogin;
import kehd.bigpicture.logic.Authentificator;
import kehd.bigpicture.logic.Executor;
import kehd.bigpicture.logic.commands.appointment.AddAppointment;
import kehd.bigpicture.logic.commands.appointment.GetAppointments;
import kehd.bigpicture.logic.commands.appointment.RemoveAppointment;
import kehd.bigpicture.logic.commands.events.*;
import kehd.bigpicture.logic.commands.notifications.DeleteNotification;
import kehd.bigpicture.logic.commands.notifications.GetNotifications;
import kehd.bigpicture.logic.commands.user.FindUser;
import kehd.bigpicture.logic.commands.user.Register;
import org.apache.log4j.Logger;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet zum Empfang der der REST Anfragen.
 */
public class REST extends HttpServlet {
    private static final Logger log = Logger.getLogger(REST.class);

    private Executor executor;
    private Authentificator authentificator;

    @Override
    public void init() {
        log.info("Initialising REST Servlet");


        executor = new Executor();
        registerCommands(executor);

        authentificator = new Authentificator(Main.getEntityManagerFactory());
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
        String methodName = request.getParameter("method");

        Enumeration enu = request.getParameterNames();
        Map<String, String> parameterMap = new HashMap<>();
        log.info("Parameter in Map uebertragen");
        while (enu.hasMoreElements()) {
            String key = (String) enu.nextElement();
            log.info(key + " : " + request.getParameter(key));
            parameterMap.put(key, request.getParameter(key));
        }

        methodName = methodName==null?"":methodName;

        log.info("methodName: " + methodName);

        response.setContentType("application/json");

        PrintWriter out = response.getWriter();

        if(executor.getCommandNames().contains(methodName)) {
            //Map param = new HashMap(request.getParameterMap());

            String username = null;

            if(!methodName.equals("register")) {
                Enumeration<String> enumeration;
                enumeration = request.getHeaders("Authorization");
                if(enumeration.hasMoreElements()) {
                    String authString = enumeration.nextElement();
                    try {
                        username = authentificator.authentificate(authString);
                    } catch (UnableToLogin unableToLogin) {
                        log.debug("Not authentificated user.");
                    }
                }
            }

            String result;

            result = executor.execute(methodName, username, parameterMap);
            out.println(result);
        } else {
            out.println("{\"error\":{" +
                        "\"message\":\"Method not found\"" +
                        "\"code\":0" +
                        "}, \"result\":\"None\"}");
        }
    }

    @Override
    public void destroy()
    {
    }

    private void registerCommands(Executor executor) {
        EntityManagerFactory entityManagerFactory = Main.getEntityManagerFactory();

        //                         _       _                        _
        //  __ _ _ __  _ __   ___ (_)_ __ | |_ _ __ ___   ___ _ __ | |_ ___
        // / _` | '_ \| '_ \ / _ \| | '_ \| __| '_ ` _ \ / _ \ '_ \| __/ __|
        //| (_| | |_) | |_) | (_) | | | | | |_| | | | | |  __/ | | | |_\__ \
        // \__,_| .__/| .__/ \___/|_|_| |_|\__|_| |_| |_|\___|_| |_|\__|___/
        //      |_|   |_|
        //

        // AddAppointment
        AddAppointment addAppointment = new AddAppointment(entityManagerFactory);
        executor.registerCommand(addAppointment, "addAppointment");

        // GetAppointments
        GetAppointments getAppointments = new GetAppointments(entityManagerFactory);
        executor.registerCommand(getAppointments, "getAppointments");

        // RemoveAppointment
        RemoveAppointment removeAppointment = new RemoveAppointment(entityManagerFactory);
        executor.registerCommand(removeAppointment, "removeAppointment");

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

        // GetVotes
        GetVotes getVotes = new GetVotes(entityManagerFactory);
        executor.registerCommand(getVotes, "getVotes");

        // Invite
        Invite invite = new Invite(entityManagerFactory);
        executor.registerCommand(invite, "invite");

        // RemoveUserFromEvent
        RemoveUserFromEvent removeUserFromEvent = new RemoveUserFromEvent(entityManagerFactory);
        executor.registerCommand(removeUserFromEvent, "removeUserFromEvent");

        // ReplyInvitation
        ReplyInvitation replyInvitation = new ReplyInvitation(entityManagerFactory);
        executor.registerCommand(replyInvitation, "replyInvitation");

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

        //
        FindUser findUser = new FindUser(entityManagerFactory);
        executor.registerCommand(findUser, "findUser");

        // Register
        Register register = new Register(entityManagerFactory);
        executor.registerCommand(register, "register");
    }
}
