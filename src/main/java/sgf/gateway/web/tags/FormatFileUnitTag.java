package sgf.gateway.web.tags;

import sgf.gateway.utils.FileSizeUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;


/**
 * The Class FormatFileSizeTag.
 */
public class FormatFileUnitTag extends TagSupport {

    /**
     * The file size.
     */
    private long fileSize;

    /**
     * Sets the file size.
     *
     * @param fileSize the new file size
     */
    public void setFileSize(String fileSize) {

        this.fileSize = Long.valueOf(fileSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int doStartTag() throws JspException {

        try {

            this.pageContext.getOut().println(FileSizeUtils.getUnit(fileSize));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }

        return Tag.EVAL_PAGE;
    }
}
