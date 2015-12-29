package sgf.gateway.web.controllers.api.dataset.file;

import org.apache.commons.fileupload.FileItem;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ApiFileUploadHandlerInterceptor extends HandlerInterceptorAdapter {

    private final PutServletFileUpload putServletFileUpload;

    private String fileAttributeName = "file";

    public ApiFileUploadHandlerInterceptor(PutServletFileUpload putServletFileUpload) {

        this.putServletFileUpload = putServletFileUpload;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (isPutRequest(request)) {

            FileItem fileItem = putServletFileUpload.parseSingleFileRequest(request);

            CommonsMultipartFile file = new CommonsMultipartFile(fileItem);

            request.setAttribute(this.fileAttributeName, file);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        if (isPutRequest(request)) {

            CommonsMultipartFile file = (CommonsMultipartFile) request.getAttribute(this.fileAttributeName);

            if (file != null) {

                file.getFileItem().delete();
            }
        }
    }

    public boolean isPutRequest(HttpServletRequest request) {

        boolean isPut = false;

        if ("put".equals(request.getMethod().toLowerCase())) {

            isPut = true;
        }

        return isPut;
    }

    public void setFileAttributeName(String fileAttributeName) {

        this.fileAttributeName = fileAttributeName;
    }
}
