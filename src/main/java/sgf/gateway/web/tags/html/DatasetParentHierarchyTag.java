package sgf.gateway.web.tags.html;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import sgf.gateway.model.metadata.ContainerType;
import sgf.gateway.model.metadata.Dataset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

/**
 * The Class DatasetParentHierarchyTag.
 */
public class DatasetParentHierarchyTag extends TagSupport {

    private static final long serialVersionUID = 1L;

    /**
     * The parent list.
     */
    private List<Dataset> parentList;

    /**
     * {@inheritDoc}
     */
    @Override
    public int doStartTag() {

        StringBuffer stringBuffer = new StringBuffer();

        if (!parentList.isEmpty()) {

            int rowCount = 0;

            for (Dataset dataset : parentList) {

                if (dataset.getContainerType().equals(ContainerType.PROJECT)) {

                    printProjectLink(dataset, rowCount, stringBuffer);

                } else {

                    printDatasetLink(dataset, rowCount, stringBuffer);
                }

                rowCount++;

                // Don't print last divider above header
                if (rowCount < parentList.size()) {
                    printDivider(rowCount, stringBuffer);
                } else {
                    printBlankLine(stringBuffer);
                }

            }
        }

        stringBuffer.append("</td></tr></table>\n");

        try {

            pageContext.getOut().println(stringBuffer.toString());

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
        return SKIP_BODY;
    }

    private void printDatasetLink(Dataset dataset, int rowCount, StringBuffer stringBuffer) {

        UriComponents uc = ServletUriComponentsBuilder.fromContextPath((HttpServletRequest) pageContext.getRequest()).path("/dataset/{dataset}.html").build().expand(dataset.getShortName()).encode();

        String datasetURI = uc.toUriString();

        printIndent(stringBuffer, rowCount);
        stringBuffer.append("<td>");
        stringBuffer.append("<a href=\"" + datasetURI + "\">" + dataset.getTitle() + "</a>");
        stringBuffer.append("</td>\n");
    }

    private void printProjectLink(Dataset dataset, int rowCount, StringBuffer stringBuffer) {

        UriComponents uc = ServletUriComponentsBuilder.fromContextPath((HttpServletRequest) pageContext.getRequest()).path("/project/{project}.html").build().expand(dataset.getShortName()).encode();

        String projectURI = uc.toUriString();

        stringBuffer.append("<table><tr>");
        printIndent(stringBuffer, rowCount);
        stringBuffer.append("<td>");
        stringBuffer.append("<a href=\"" + projectURI + "\">" + dataset.getTitle() + "</a>");
        stringBuffer.append("</td></tr></table>\n");
    }

    private void printDivider(int rowCount, StringBuffer stringBuffer) {

        HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
        String path = req.getContextPath().toString();

        stringBuffer.append("<table><tr>");
        printIndent(stringBuffer, rowCount);

        stringBuffer.append("<td>" + "<img src='" + path + "/images/left-up-arrow-icon.png'/>" + "</td>");
    }


    /**
     * Adds the indent.
     *
     * @param stringBuffer the string buffer
     * @param indentDepth  the indent depth
     */
    private void printIndent(StringBuffer stringBuffer, final int indentDepth) {

        for (int i = 0; i < indentDepth; i++) {

            stringBuffer.append("<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>");
        }
    }

    private void printBlankLine(StringBuffer stringBuffer) {

        stringBuffer.append("<tr><td>&nbsp;</td></tr>");
    }

    /**
     * Sets the parent list.
     *
     * @param parentList the new parent list
     */
    public void setParentList(final List<Dataset> parentList) {

        this.parentList = parentList;
    }
}
