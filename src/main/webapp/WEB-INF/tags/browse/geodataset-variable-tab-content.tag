<%--
    Display ESG variables.

    Variables can't be entered directly through the UI,
    but can be added to the gateway via other means.
--%>

<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<%@ attribute name="variables" required="false" type="java.util.Collection" %>

<p>${fn:length(variables)} entries</p>
<dl>
    <c:forEach var="variable" items="${variables}" >
        <dt>
            ${variable.name}
        </dt>
        <dd>
            <ul>
                <li>
                    <span style="width: 120px; display: inline-block;">Description: </span>
                    ${variable.description}
                </li>
                <li>
                    <span style="width: 120px; display: inline-block;">Units: </span>
                        ${variable.units.name}  (${variable.units.symbol})
                </li>
                <li>
                    <ul style="margin-top: 8px;">
                        <c:forEach var="standardName" items="${variable.standardNames}">
                            <li>
                                <span style="width: 120px; display: inline-block;">Standard Name: </span>
                                    ${standardName.name}
                            </li>
                            <li>
                                <span style="width: 120px; display: inline-block;">Description: </span>
                                    ${standardName.description}
                            </li>
                            <li>
                                <span style="width: 120px; display: inline-block;">Units: </span>
                                    ${standardName.units.name}  (${standardName.units.symbol})
                            </li>
                            <li>
                                <span style="width: 120px; display: inline-block;">Type: </span>
                                    ${standardName.type}
                            </li>
                        </c:forEach>
                    </ul>
                </li>
            </ul>

        </dd>
        <br>
    </c:forEach>
</dl>
