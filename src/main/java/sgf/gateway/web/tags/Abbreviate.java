package sgf.gateway.web.tags;

import org.apache.commons.lang.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * The Class FormatFileSizeTag.
 */
public class Abbreviate extends TagSupport {

    private int maxWidth;

    private String value;

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int doStartTag() throws JspException {

        try {

            String result = this.value;

            if (StringUtils.isNotBlank(result) && (maxWidth <= result.length())) {

                String trimmedValue = this.value.substring(0, maxWidth - 1);

                result = trimmedValue.substring(0, trimmedValue.lastIndexOf(" "));

                result += "...";
            }

            this.pageContext.getOut().println(result);

        } catch (IOException e) {

            throw new RuntimeException(e);
        }

        return Tag.EVAL_PAGE;
    }
}
