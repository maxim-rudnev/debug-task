package plugin;

import core.IPlugin;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class TestPlugin implements IPlugin {
    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String encoding = "iso-8859-1";//"UTF-8";
        String answer = "test answer";


        //throw  new IOException("hello");

        String type = "application/x-www-form-urlencoded";
        response.setContentType(type);
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
        response.setCharacterEncoding(encoding);

        // !!!! здесь надо поймать точку останова
        response.getOutputStream().write(answer.getBytes(encoding));
        response.getOutputStream().close();


    }
}
