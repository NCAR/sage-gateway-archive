<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="OAI-PMH"/>
    <tiles:putAttribute type="string" name="pageTitle" value="OAI-PMH"/>

    <tiles:putAttribute name="body">

        <p>
            OAI-PMH is a protocol for exposing the structured metadata of a data repository.
        </p>

        <p>
            <b>NOTE:</b> We do not supply information about deleted records.
        </p>

        <p><a href="<c:url value="/oai/repository?verb=Identify"/>">Identify</a></p>

        <p><a href="<c:url value="/oai/repository?verb=ListSets"/>">ListSets</a></p>

        <p><a href="<c:url value="/oai/repository?verb=ListMetadataFormats"/>">ListMetadataFormats</a></p>

        <p><a href="<c:url value="/oai/repository?verb=ListIdentifiers&metadataPrefix=dif"/>">ListIdentifiers as DIF</a>
        </p>

        <p><a href="<c:url value="/oai/repository?verb=ListIdentifiers&metadataPrefix=ISO19139"/>">ListIdentifiers as
            ISO 19139</a></p>

        <p><a href="<c:url value="/oai/repository?verb=ListIdentifiers&metadataPrefix=oai_dc"/>">ListIdentifiers as
            Dublin Core (oai_dc)</a></p>

        <p><a href="<c:url value="/oai/repository?verb=ListRecords&metadataPrefix=dif"/>">ListRecords as DIF</a></p>

        <p><a href="<c:url value="/oai/repository?verb=ListRecords&metadataPrefix=ISO19139"/>">ListRecords as ISO
            19139</a></p>

        <p><a href="<c:url value="/oai/repository?verb=ListRecords&metadataPrefix=oai_dc"/>">ListRecords as Dublin Core (oai_dc)</a></p>

    </tiles:putAttribute>

</tiles:insertDefinition>