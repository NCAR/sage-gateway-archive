package sgf.gateway.web.controllers.oai;

import org.springframework.web.util.UriUtils;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;
import java.io.UnsupportedEncodingException;

public class RemoveExtraEncodingPropertyEditorWrapper implements PropertyEditor {

    private final PropertyEditor wrappedPropteryEditor;

    public RemoveExtraEncodingPropertyEditorWrapper(final PropertyEditor wrappedPropteryEditor) {

        this.wrappedPropteryEditor = wrappedPropteryEditor;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

        this.wrappedPropteryEditor.addPropertyChangeListener(listener);
    }

    @Override
    public String getAsText() {

        return this.wrappedPropteryEditor.getAsText();
    }

    @Override
    public Component getCustomEditor() {

        return this.wrappedPropteryEditor.getCustomEditor();
    }

    @Override
    public String getJavaInitializationString() {

        return this.wrappedPropteryEditor.getJavaInitializationString();
    }

    @Override
    public String[] getTags() {

        return this.wrappedPropteryEditor.getTags();
    }

    @Override
    public Object getValue() {

        return this.wrappedPropteryEditor.getValue();
    }

    @Override
    public boolean isPaintable() {

        return this.wrappedPropteryEditor.isPaintable();
    }

    @Override
    public void paintValue(Graphics gfx, Rectangle box) {

        this.wrappedPropteryEditor.paintValue(gfx, box);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {

        this.wrappedPropteryEditor.removePropertyChangeListener(listener);
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        String decodedText;

        try {

            decodedText = this.decode(text);

        } catch (UnsupportedEncodingException e) {

            throw new RuntimeException(e);
        }

        this.wrappedPropteryEditor.setAsText(decodedText);
    }

    protected String decode(String text) throws UnsupportedEncodingException {

        String decodedText = UriUtils.decode(text, "UTF-8");

        if (!decodedText.equals(text)) {

            decodedText = this.decode(decodedText);
        }

        return decodedText;
    }

    @Override
    public void setValue(Object value) {

        this.wrappedPropteryEditor.setValue(value);
    }

    @Override
    public boolean supportsCustomEditor() {

        return this.wrappedPropteryEditor.supportsCustomEditor();
    }
}
