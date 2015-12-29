package sgf.gateway.web.controllers.oai;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RepositoryControllerTest {

    private RepositoryController repositoryController;
    private HttpServletRequest mockHttpServletRequest;


    @Before
    public void setUp() throws Exception {

        this.repositoryController = new RepositoryController(null, null, null, 100, "");
        this.mockHttpServletRequest = mock(HttpServletRequest.class);

        when(this.mockHttpServletRequest.getRemoteHost()).thenReturn("127.0.0.1");
    }

    @Test
    public void testUnrecognizedVerb() {

        OAIRequest mockOAIRequest = mock(OAIRequest.class);
        when(mockOAIRequest.getResponseDate()).thenReturn(new Date());

        ModelAndView result = this.repositoryController.unrecognizedVerb(mockOAIRequest, this.mockHttpServletRequest);

        assertThat(result.getViewName(), is(equalToIgnoringCase("error")));
        assertThat(result.getModel(), hasEntry(equalTo("errorCode"), hasToString(equalTo("badVerb"))));
    }

    @Test
    public void testListIdentifiersWithBindErrors() throws Exception {

        BindingResult mockBindingResult = mock(BindingResult.class);

        when(mockBindingResult.hasErrors()).thenReturn(true);

        ModelAndView result = this.repositoryController.listIdentifiers(null, mockBindingResult, this.mockHttpServletRequest);

        assertThat(result.getViewName(), is(equalToIgnoringCase("error")));

    }

}
