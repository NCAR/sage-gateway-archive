package sgf.gateway.web.controllers.download;

import org.apache.commons.io.FilenameUtils;
import org.springframework.util.FileCopyUtils;
import sgf.gateway.model.security.User;
import sgf.gateway.service.metadata.FileNotFoundException;
import sgf.gateway.web.HttpHeaderConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;

public class FileDownloadHandler implements FileHandler {

    public void handle(HttpServletRequest request, HttpServletResponse response, File file, User user) {

        this.checkIfFileExists(file);

        this.setResponseHeaders(response, file);

        this.streamFile(response, file);
    }

    void checkIfFileExists(File file) {

        if (!file.exists()) {

            throw new FileNotFoundException(file);
        }
    }

    void setResponseHeaders(HttpServletResponse response, File file) {

        response.setContentType(getFileContentType(file.getName()));

        // FIXME: temporary work-around for inaccurate LogicalFile.getSize(); see SGF-907
        // FIXME: to be removed once TDS catalogs reflect correct size.

        Long fileSize = file.length();

        // TODO Why not just remove the first part of the if statement and only use the HttpHeaderConstants.CONTENT_LENGTH code?
        if ((fileSize <= Integer.MAX_VALUE) && (fileSize >= 0)) {

            response.setContentLength(fileSize.intValue());

        } else {

            response.addHeader(HttpHeaderConstants.CONTENT_LENGTH, fileSize.toString());
        }

        String fileName = file.getName();

        // The extra quotes are due to the possiblity of having spaces in the filename:
        // http://kb.mozillazine.org/Filenames_with_spaces_are_truncated_upon_download
        response.addHeader(HttpHeaderConstants.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
    }

    /**
     * Gets the file content type. Called from the <code>setResponseHeaders</code> method.
     *
     * @param fileName the file name
     * @return the file content type
     */
    String getFileContentType(String fileName) {

        // Grab the configured mapping from the tomcat container...
        String contentType = null; // getServletContext().getMimeType(fileName);

        // TODO - Do something about this.
        // If null, use a default or lookup from the DB?
        if (null == contentType) {
            contentType = "data/." + FilenameUtils.getExtension(fileName);
        }

        return contentType;
    }

    /**
     * Stream file. Called from the <code>handleDownload</code> method.
     *
     * @param response        the response
     * @param file            the file
     */
    void streamFile(HttpServletResponse response, File file) {

        // FIXME: We should be throwing an application specific exception here while avoiding catching a client raised exception.
        // See: https://vets.development.ucar.edu/jira/browse/SGF-860
        try {

            FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());

        } catch (FileNotFoundException e) {

            throw new RuntimeException(e);

        } catch (MalformedURLException e) {

            throw new RuntimeException(e);

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }
}
