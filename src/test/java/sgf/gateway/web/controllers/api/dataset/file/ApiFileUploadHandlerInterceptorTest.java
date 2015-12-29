package sgf.gateway.web.controllers.api.dataset.file;

import org.apache.commons.fileupload.FileItem;
import org.junit.Test;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.*;

public class ApiFileUploadHandlerInterceptorTest {

    @Test
    public void testPrehandle() throws Exception {

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);

        FileItem mockFileItem = mock(FileItem.class);

        PutServletFileUpload mockFileUpload = mock(PutServletFileUpload.class);
        when(mockFileUpload.parseSingleFileRequest(mockRequest)).thenReturn(mockFileItem);

        ApiFileUploadHandlerInterceptor interceptor = new ApiFileUploadHandlerInterceptor(mockFileUpload) {

            public boolean isPutRequest(HttpServletRequest request) {
                return true;
            }
        };

        interceptor.preHandle(mockRequest, null, null);

        verify(mockRequest).setAttribute(matches("file"), any(CommonsMultipartFile.class));
    }

    @Test
    public void testAfterCompletion() throws Exception {

        FileItem mockFileItem = mock(FileItem.class);

        CommonsMultipartFile multipartFile = new CommonsMultipartFile(mockFileItem);

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getAttribute("file")).thenReturn(multipartFile);

        ApiFileUploadHandlerInterceptor interceptor = new ApiFileUploadHandlerInterceptor(null) {

            public boolean isPutRequest(HttpServletRequest request) {
                return true;
            }
        };

        interceptor.afterCompletion(mockRequest, null, null, null);

        verify(mockFileItem).delete();
    }


    @Test
    public void testIsPutRequestUpperCase() {

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getMethod()).thenReturn("PUT");

        ApiFileUploadHandlerInterceptor interceptor = new ApiFileUploadHandlerInterceptor(null);

        boolean isPutRequest = interceptor.isPutRequest(mockRequest);

        assertThat(true, equalTo(isPutRequest));
    }

    @Test
    public void testIsPutRequestLowerCase() {

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getMethod()).thenReturn("put");

        ApiFileUploadHandlerInterceptor interceptor = new ApiFileUploadHandlerInterceptor(null);

        boolean isPutRequest = interceptor.isPutRequest(mockRequest);

        assertThat(true, equalTo(isPutRequest));
    }

    @Test
    public void testIsPutRequestMixedCase() {

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getMethod()).thenReturn("PuT");

        ApiFileUploadHandlerInterceptor interceptor = new ApiFileUploadHandlerInterceptor(null);

        boolean isPutRequest = interceptor.isPutRequest(mockRequest);

        assertThat(true, equalTo(isPutRequest));
    }

    @Test
    public void testIsPutRequestAsPost() {

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getMethod()).thenReturn("POST");

        ApiFileUploadHandlerInterceptor interceptor = new ApiFileUploadHandlerInterceptor(null);

        boolean isPutRequest = interceptor.isPutRequest(mockRequest);

        assertThat(false, equalTo(isPutRequest));
    }
}
