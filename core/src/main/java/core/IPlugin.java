package core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface IPlugin {
    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException;
}