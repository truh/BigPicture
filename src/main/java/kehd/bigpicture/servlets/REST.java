package kehd.bigpicture.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet zum Empfang der der REST Anfragen.
 */
public class REST extends HttpServlet {
    private boolean test;

    @Override
    public void init() {
        test = false;
        if(System.getProperty("bp-test") != null) {
            test = System.getProperty("bp-test").toLowerCase().equals("true");
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
                            "       \"2014-06-03T13:00:00\"," +
                            "   ]," +
                            "   \"participants\":[]," +
                            "   \"comments\"    :[]" +
                            "}," +
                            "{" +
                            "   \"title\"       :\"[GGP] Geschichte Test\"," +
                            "   \"owner\"       :\"Martin\"," +
                            "   \"appointments\":[" +
                            "       \"2014-06-04T12:00:00\"," +
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
            out.println("REST!");
        }
    }

    @Override
    public void destroy()
    {
    }
}
