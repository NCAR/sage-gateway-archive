package sgf.gateway.web.tags;

import org.apache.commons.lang.StringUtils;
import sgf.gateway.utils.FileSizeUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * The Class FormatFileSizeTag.
 */
public class FormatFileSizeUnitTag extends TagSupport {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The file size.
     */
    private long fileSize;

    /**
     * The precision
     */
    private int precision = 2;

    /**
     * Sets the file size.
     *
     * @param fileSize the new file size
     */
    public void setFileSize(String fileSize) {

        if (StringUtils.isEmpty(fileSize)) {

            this.fileSize = 0;

        } else {

            this.fileSize = Long.valueOf(fileSize);
        }
    }

    public void setPrecision(String precision) {

        this.precision = Integer.valueOf(precision);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int doStartTag() throws JspException {

        try {

            this.pageContext.getOut().println(FileSizeUtils.getSize(this.fileSize, this.precision) + " " + FileSizeUtils.getUnit(this.fileSize));

        } catch (IOException e) {

            throw new RuntimeException(e);
        }

        return Tag.EVAL_PAGE;
    }
}
