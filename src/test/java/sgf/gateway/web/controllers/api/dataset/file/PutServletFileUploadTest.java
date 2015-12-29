package sgf.gateway.web.controllers.api.dataset.file;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PutServletFileUploadTest {

    @Test
    public void testGetFileName() {

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getRequestURI()).thenReturn("http://acadis.ucar.edu/dataset/hydro-chemical_data_for_npeo_2010/NPEO2010BottleData.xlsx");

        PutServletFileUpload fileUpload = new PutServletFileUpload(null);

        String fileName = fileUpload.getFileName(mockRequest);

        assertThat(fileName, equalTo("NPEO2010BottleData.xlsx"));
    }

    @Test
    public void testGetFileNameWithSpaces() {

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getRequestURI()).thenReturn("http://acadis.ucar.edu/dataset/hydro-chemical_data_for_npeo_2010/NPEO%202010%20Bottle%20Data.xlsx");

        PutServletFileUpload fileUpload = new PutServletFileUpload(null);

        String fileName = fileUpload.getFileName(mockRequest);

        assertThat(fileName, equalTo("NPEO 2010 Bottle Data.xlsx"));
    }

    @Test
    public void testGetFileNameWithTrailingSpaces() {

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getRequestURI()).thenReturn("http://acadis.ucar.edu/dataset/hydro-chemical_data_for_npeo_2010/NPEO%202010%20Bottle%20Data.xlsx%20%20");

        PutServletFileUpload fileUpload = new PutServletFileUpload(null);

        String fileName = fileUpload.getFileName(mockRequest);

        assertThat(fileName, equalTo("NPEO 2010 Bottle Data.xlsx"));
    }
}
