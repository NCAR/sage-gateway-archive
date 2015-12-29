package sgf.gateway.web.controllers.api.dataset.file;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.util.Streams;
import org.springframework.core.io.Resource;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


public class PutServletFileUpload extends FileUpload implements ServletContextAware {

    private DiskFileItemFactory fileItemFactory;

    private boolean uploadTempDirSpecified = false;

    public PutServletFileUpload(DiskFileItemFactory fileItemFactory) {

        this.fileItemFactory = fileItemFactory;
    }

    // This code is lifted from the org.apache.commons.fileupload.FileUploadBase.parseRequest(RequestContext ctx) method.
    public FileItem parseSingleFileRequest(HttpServletRequest request) throws FileUploadException {

        String fileName = this.getFileName(request);

        FileItem fileItem = fileItemFactory.createItem(null, null, false, fileName);

        this.tryFileCopy(request, fileItem);

        return fileItem;
    }

    public void tryFileCopy(HttpServletRequest request, FileItem fileItem) throws FileUploadException {

        try {

            Streams.copy(request.getInputStream(), fileItem.getOutputStream(), true);

        } catch (FileUploadIOException e) {

            throw (FileUploadException) e.getCause();

        } catch (IOException e) {

            throw new IOFileUploadException("Processing of PUT request failed. " + e.getMessage(), e);
        }
    }

    public String getFileName(HttpServletRequest request) {

        String uri = request.getRequestURI();

        uri = this.tryDecode(uri);

        String fileName = uri.substring(uri.lastIndexOf("/") + 1, uri.length());

        fileName = fileName.trim();

        return fileName;
    }

    public String tryDecode(String uri) {

        String result;

        try {

            result = URLDecoder.decode(uri, "UTF-8");

        } catch (UnsupportedEncodingException e) {

            throw new RuntimeException(e);
        }

        return result;
    }

    // The following code is lifted from the org.springframework.web.multipart.commons.CommonsMultipartResolver and org.springframework.web.multipart.commons.CommonsFileUploadSupport classes.

    /**
     * Set the temporary directory where uploaded files get stored.
     * Default is the servlet container's temporary directory for the web application.
     *
     * @see org.springframework.web.util.WebUtils#TEMP_DIR_CONTEXT_ATTRIBUTE
     */
    public void setUploadTempDir(Resource uploadTempDir) throws IOException {

        if (!uploadTempDir.exists() && !uploadTempDir.getFile().mkdirs()) {

            throw new IllegalArgumentException("Given uploadTempDir [" + uploadTempDir + "] could not be created");
        }

        this.fileItemFactory.setRepository(uploadTempDir.getFile());
        this.uploadTempDirSpecified = true;
    }

    protected boolean isUploadTempDirSpecified() {

        return this.uploadTempDirSpecified;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {

        if (!isUploadTempDirSpecified()) {

            File repository = WebUtils.getTempDir(servletContext);

            this.fileItemFactory.setRepository(repository);
        }
    }
}