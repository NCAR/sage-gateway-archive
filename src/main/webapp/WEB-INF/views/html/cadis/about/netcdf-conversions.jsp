<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="ASCII to netCDF Conversion Guide"/>
    <tiles:putAttribute type="string" name="pageTitle" value="ASCII to netCDF Conversion Guide"/>

    <tiles:putAttribute name="body">

        <h3>UNIDATA: The definitive source for information regarding netCDF</h3>

        <ul style="height:50%">

            <li><a href="http://www.unidata.ucar.edu/software/netcdf/"> Unidata netCDF Homepage</a></li>
            <li><a href="http://rosetta.unidata.ucar.edu/createAcadis">Rosetta Data Translation Tool</a></li>
        </ul>

        <h3>Other References</h3>

        <p>The following sites will be helpful in researching how to translate
            your project's data into netCDF. We suggest you first read the
            information on NetCDF from SEDNA. The 2007 Sea ice Experiment -
            Dynamic Nature of the Arctic (SEDNA) is using netCDF as the archive
            format for time series and model data, such as data from ice mass
            balance buoys. The guide they provide gives a quick and accessible
            overview of ASCII to netCDF conversion.</p>
        <ul>

            <li><a href="http://badc.nerc.ac.uk/help/formats/netcdf/nc_utilities.html"> British Atmospheric Data
                Center</a> use of ncdump and ncgen to go between ASCII and netCDF (Windows and Unix)
            </li>

            <li><a href="http://geon.unavco.org/unavco/IDV_for_GEON_data_access.html"> GEON IDV</a> (this will work the
                same for the Unidata IDV as the GEON IDV is the same core program)
            </li>

            <li><a href="http://cf-convention.github.io/">CF-Conventions</a> and CF Compliance Checker</li>
        </ul>

    </tiles:putAttribute>

</tiles:insertDefinition>
