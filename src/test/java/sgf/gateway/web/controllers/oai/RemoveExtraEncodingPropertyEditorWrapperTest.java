package sgf.gateway.web.controllers.oai;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class RemoveExtraEncodingPropertyEditorWrapperTest {

    @Test
    public void testAlreadyUnencodedText() throws UnsupportedEncodingException {

        String text = "Already-Un_encoded";

        RemoveExtraEncodingPropertyEditorWrapper propertyEditor = new RemoveExtraEncodingPropertyEditorWrapper(null);

        String decodedText = propertyEditor.decode(text);

        assertThat(decodedText, equalTo(text));
    }

    @Test
    public void testEncodedJSONText() throws UnsupportedEncodingException {

        String text = "{%22from%22:null,%22until%22:null,%22set%22:null,%22metadataPrefix%22:%22dif%22,%22offset%22:100}";

        RemoveExtraEncodingPropertyEditorWrapper propertyEditor = new RemoveExtraEncodingPropertyEditorWrapper(null);

        String decodedText = propertyEditor.decode(text);

        assertThat(decodedText, equalTo("{\"from\":null,\"until\":null,\"set\":null,\"metadataPrefix\":\"dif\",\"offset\":100}"));
    }

    @Test
    public void testDoubleEncodedJSONText() throws UnsupportedEncodingException {

        String actualTextFromException = "[%7B%22from%22%3Anull%2C%22until%22%3Anull%2C%22set%22%3Anull%2C%22metadataPrefix%22%3A%22dif%22%2C%22offset%22%3A100%7D]";

        RemoveExtraEncodingPropertyEditorWrapper propertyEditor = new RemoveExtraEncodingPropertyEditorWrapper(null);

        String decodedText = propertyEditor.decode(actualTextFromException);

        assertThat(decodedText, equalTo("[{\"from\":null,\"until\":null,\"set\":null,\"metadataPrefix\":\"dif\",\"offset\":100}]"));
    }
}
