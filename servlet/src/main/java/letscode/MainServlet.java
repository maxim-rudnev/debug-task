package letscode;

import core.IPlugin;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/cool-servlet", "/my-cool-servlet/*"})
public class MainServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        log("Method init =)");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("Method service enter\n");
        super.service(req, resp);
        resp.getWriter().write("Method service exit\n");
    }

    private static ClassLoader defaultLoader;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = req.getRequestURI();
        String params = formatParams(req);

        // загрузка плагина
        // !!!!! в этой строке укажите путь к плагину на вашем компьютере
        String pluginFilename = "C:\\Users\\admin\\IdeaProjects\\debug-task\\plugin\\target\\plugin-1.0-SNAPSHOT.jar";
        File pluginFile = new File(pluginFilename);
        URL[] urls = new URL[1];
        urls[0] = pluginFile.toURI().toURL();

        defaultLoader = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());

        String className = "plugin.TestPlugin";
        IPlugin plugin = null;

        // загрузка плагина
        try {
            plugin = (IPlugin)(defaultLoader.loadClass(className)).newInstance();
        }
        catch (Exception ex){
            throw new IOException("Ошибка при загрузке плагина");
        }

        // вызов метода плагина
        try {
            plugin.test(req,resp);
        }
        catch (Exception ex){
            throw ex;
        }

        resp.getWriter().write("Method doGet\nURI: " + uri + "\nParams:\n" + params + "\n");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String params = formatParams(req);

        resp.getWriter().write("Method doPost\nURI: " + uri + "\nParams:\n" + params + "\n");
    }

    private String formatParams(HttpServletRequest req) {
        return req.getParameterMap()
                .entrySet()
                .stream()
                .map(entry -> {
                    String param = String.join(" and ", entry.getValue());
                    return entry.getKey() + " => " + param;
                })
                .collect(Collectors.joining("\n"));
    }

    @Override
    public void destroy() {
        log("Method destroy =)");
    }
}
