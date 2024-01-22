package com.nhnacademy.suvletex.ex0;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;
import org.jsoup.Jsoup;

@WebServlet(name = "beautifyServlet", value = "/beautify")
public class BeautifyServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(BeautifyServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, UnsupportedEncodingException {
        req.setCharacterEncoding("utf-8");
        String html = req.getParameter("html");
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("utf-8");

        try(PrintWriter out = resp.getWriter()){
            out.println(Jsoup.parse(html));
        }catch (Exception ex){
            log.info(ex.getMessage());
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 응답의 컨텐츠 타입을 HTML로 설정
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        // PrintWriter를 사용하여 HTML 폼을 생성
        try (PrintWriter out = resp.getWriter()) {
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>정리되지 않은 Html 내용을 입력받아 beautify 해서 응답</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<form method='post' action='beautify'>");
            out.println("<textarea name='html' rows='10' cols='200'></textarea>");
            out.println("<p><button type='submit'>전송</button></p>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
    }
}