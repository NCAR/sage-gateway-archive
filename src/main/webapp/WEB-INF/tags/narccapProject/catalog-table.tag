<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<table id="narccapCatalog-table" class="table table-condensed table-bordered table-striped">
    <thead>
        <tr class="active">
            <th></th>
            <th colspan="5">Driving Model</th>
        </tr>
        <tr>
            <th>RCM</th>
            <th>NCEP</th>
            <th>CCSM</th>
            <th>CGCM3</th>
            <th>GFDL</th>
            <th>HadCM3</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td><b>CRCM</b></td>
            <td><a href="<c:url value="/dataset/id/${datasetMap.crcmNCEP.identifier}.html" />">data</a></td>
            <td><a href="<c:url value="/dataset/id/${datasetMap.crcmCCSM.identifier}.html" />">data</a></td>
            <td><a href="<c:url value="/dataset/id/${datasetMap.crcmCGM3.identifier}.html" />">data</a></td>
            <td></td>
            <td></td>
        </tr>

        <tr>
            <td><b>ECP2</b></td>
            <td><a href="<c:url value="/dataset/id/${datasetMap.ecp2NCEP.identifier}.html" />">data</a></td>
            <td></td>
            <td></td>
            <td><a href="<c:url value="/dataset/id/${datasetMap.ecp2GFDL.identifier}.html" />">data</a></td>
            <td><a href="<c:url value="/dataset/id/${datasetMap.ecp2HADCM3.identifier}.html" />">data</a></td>
        </tr>

        <tr>
            <td><b>HRM3</b></td>
            <td><a href="<c:url value="/dataset/id/${datasetMap.hrm3NCEP.identifier}.html" />">data</a></td>
            <td></td>
            <td></td>
            <td><a href="<c:url value="/dataset/id/${datasetMap.hrm3GFDL.identifier}.html" />">data</a></td>
            <td><a href="<c:url value="/dataset/id/${datasetMap.hrm3HADCM3.identifier}.html" />">data</a></td>
        </tr>

        <tr>
            <td><b>MM5I</b></td>
            <td><a href="<c:url value="/dataset/id/${datasetMap.mm5iNCEP.identifier}.html" />">data</a></td>
            <td><a href="<c:url value="/dataset/id/${datasetMap.mm5iCCSM.identifier}.html" />">data</a></td>
            <td></td>
            <td></td>
            <td><a href="<c:url value="/dataset/id/${datasetMap.mm5iHADCM3.identifier}.html" />">data</a></td>
        </tr>

        <tr>
            <td><b>RCM3</b></td>
            <td><a href="<c:url value="/dataset/id/${datasetMap.rcm3NCEP.identifier}.html" />">data</a></td>
            <td></td>
            <td><a href="<c:url value="/dataset/id/${datasetMap.rcm3CGM3.identifier}.html" />">data</a></td>
            <td><a href="<c:url value="/dataset/id/${datasetMap.rcm3GFDL.identifier}.html" />">data</a></td>
            <td></td>
        </tr>

        <tr>
            <td><b>WRFG</b></td>
            <td><a href="<c:url value="/dataset/id/${datasetMap.wrfgNCEP.identifier}.html" />">data</a></td>
            <td><a href="<c:url value="/dataset/id/${datasetMap.wrfgCCSM.identifier}.html" />">data</a></td>
            <td><a href="<c:url value="/dataset/id/${datasetMap.wrfgCGM3.identifier}.html" />">data</a></td>
            <td></td>
            <td></td>
        </tr>

        <tr>
            <td><b>Timeslice</b></td>
            <td></td>
            <td><a href="<c:url value="/dataset/id/${datasetMap.tsCCSM.identifier}.html" />">data</a></td>
            <td></td>
            <td><a href="<c:url value="/dataset/id/${datasetMap.tsGFDL.identifier}.html" />">data</a></td>
            <td></td>
        </tr>

        <tr>
            <td><b>ECPC</b></td>
            <td><a href="<c:url value="/dataset/id/${datasetMap.ecpcNCEP.identifier}.html" />">data</a></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>

        <tr>
            <td><b>WRFP</b></td>
            <td><a href="<c:url value="/dataset/id/${datasetMap.wrfpNCEP.identifier}.html" />">data</a></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
    </tbody>
</table>