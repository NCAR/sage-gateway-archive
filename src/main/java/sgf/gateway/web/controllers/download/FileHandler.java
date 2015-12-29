package sgf.gateway.web.controllers.download;

import sgf.gateway.model.security.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

public interface FileHandler {

    void handle(HttpServletRequest request, HttpServletResponse response, File file, User user);
}
