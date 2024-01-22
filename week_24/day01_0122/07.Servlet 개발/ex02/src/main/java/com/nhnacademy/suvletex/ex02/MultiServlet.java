package com.nhnacademy.suvletex.ex02;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class MultiServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(MultiServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] values = req.getParameterValues("class");
        try(PrintWriter out = resp.getWriter()){
            out.println(String.join(",", values));
        }catch (IOException ex){
            log.info(ex.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            out.println("<html>");
            out.println("<head><title>Multi Value Form</title></head>");
            out.println("<body>");
            out.println("<form method='post'>");
            out.println("Class: <input type='text' name='class'><br>");
            out.println("Class: <input type='text' name='class'><br>");
            out.println("Class: <input type='text' name='class'><br>");
            out.println("<input type='submit' value='Submit'>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
    }
}