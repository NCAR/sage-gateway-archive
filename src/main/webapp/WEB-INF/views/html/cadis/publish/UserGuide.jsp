<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="ACADIS Data Provider's Guide: How to Contribute Your Data"/>
    <tiles:putAttribute type="string" name="pageTitle"
                        value="ACADIS Data Provider's Guide: How to Contribute Your Data"/>

    <tiles:putAttribute name="body">

        <h2>The Data Provider's Guide contains the following sections:</h2>

        <ul>
            <li><a href="#FAQ">Frequently Asked Questions</a></li>
            <li><a href="#Structure">Structure of the ACADIS Data Collection</a></li>
            <li><a href="#Registration">Registration</a></li>
            <li><a href="#Login">Login</a></li>
            <li><a href="#Dataset">Creating a Dataset</a></li>
            <li><a href="#AddFiles">Add Data Files to a Dataset</a></li>
            <li><a href="#UpdateFiles">Updating Data Files for a Dataset</a></li>
            <li><a href="#AddResponsibleParty">Adding Responsible Parties (Contacts) to a Dataset</a></li>
            <li><a href="#ResponsiblePartyFAQ">Responsible Party Frequently Asked Questions</a></li>
            <li><a href="#AddAwards">Adding Award Numbers to a Dataset</a></li>
            <li><a href="#AddRelatedLinks">Adding Related Links to a Dataset</a></li>
            <li><a href="#Metadata">Metadata Field Definitions</a></li>
            <li><a href="#Appendix A">Appendix A: ACADIS Metadata Record Examples</a></li>
            <li><a href="#Appendix B">Appendix B: Creating and Submitting Data Documentation</a></li>
        </ul>

        <h2><a name="FAQ">Frequently Asked Questions</a></h2>

        <h3>What is the Purpose of the Data Provider's Guide?</h3>

        <p>The Data Provider's Guide is a reference to help you through the process of submitting data to ACADIS. This
            guide provides step-by-step instructions for uploading data and offers recommendations for providing
            accurate and descriptive metadata.</p>

        <h3>Who is a Data Provider?</h3>

        <p>A Data Provider is anyone who submits metadata and/or data files to the ACADIS Gateway. A Data Provider may
            have many titles including: Principal Investigator, Field Scientist, Graduate Student, Co-Investigator,
            Project Manager, Data Manager, Curator, Librarian, Intern, etc...</p>

        <h3>Who will assist you in updating or submitting data?</h3>

        <p>ACADIS is built on a self-publishing model. This document is the first place to get assistance. If further
            help is needed, the ACADIS Community Support Team is here to assist Data Providers with both data and
            metadata submission needs. Data Providers can contact ACADIS Support by emailing <a
                    href="mailto:support@aoncadis.org">support@aoncadis.org</a> or calling (720) 443-1409. A team member
            will respond within one business day. Requests for assistance should include any deadlines for submitting
            data.</p>

        <h2><a name="Structure">Structure of the ACADIS Data Collection</a></h2>

        <h3>Projects</h3>

        <p>Projects represent scientific endeavors that have been awarded funding by the NSF or other federal funding
            agencies.</p>

        <p>A project is the highest level container for organizing data collections.</p>

        <p>Projects can be created or edited by ACADIS Data Curators only. If Data Providers would like the metadata for
            a project to be updated or changed, they should contact ACADIS Support for assistance.</p>

        <h3>Datasets</h3>

        <p>Datasets represent specific work done for a project.</p>

        <p>Like projects, datasets are also containers for organizing data; each dataset is associated with a specific
            project.</p>

        <p>Data Providers will create datasets themselves and add all metadata and data files as needed.</p>

        <p>Datasets may contain data files or other datasets.</p>

        <h3>Data Files</h3>

        <p>Data Files are the data that were collected or produced during the lifetime of a project.</p>

        <p>Data Files are placed within a dataset.</p>

        <h2><a name="Registration">Registration</a></h2>

        <p>Data providers may add or update metadata and data files. Registration is required to make additions or
            updates.</p>

        <p>Registration is not required to download data files.</p>

        <p>In order to register, Data Providers should do the following:</p>
        <ol>
            <li>Click the 'Registration' link in the upper right hand corner of the webpage.</li>
            <li>Fill out all the requested fields.</li>
            <li>Click the 'Register' button.</li>
        </ol>
        <p>After the form is successfully submitted:</p>
        <ul>
            <li>You will have instant access to the ACADIS website and may login.</li>
            <li>Data Providers will receive an email requesting you to submit your project's information to the ACADIS
                Support Team.
            </li>
            <li>If you need a new project created, Please promptly reply with all requested information.</li>
            <li>After your new project is created by the ACADIS Support Team, you may start to create your datasets.
            </li>
        </ul>

        <h2><a name="Login">Login</a></h2>
        <ol>
            <li>Click the 'Login' link in the upper right hand corner of the webpage.</li>
            <li>Enter your username and password from Registration.</li>
            <li>Click the 'Login' button.</li>
        </ol>

        <h2><a name="Dataset">Creating a Dataset</a></h2>

        <p>Data Providers should follow these steps to create a new dataset within a project:</p>
        <ol>
            <li>If you haven't already, login to the website.</li>
            <li>Click the 'Contribute Data' link at the top of the web page.</li>
            <li>Click the 'Create New Dataset' link under the project to which you would like to add a new dataset.</li>
            <li>Click to select the enclosing (parent) project or dataset (Step 1).</li>
            <li>Note, some Data Providers often have very similar datasets. There is the ability to copy the metadata
                from an existing dataset into a new dataset if desired.
            </li>
            <li>Click either the 'Blank, no copied Metadata' link, or click to select the dataset whose metadata you
                would like to copy for your new dataset (Step 2).
            </li>
            <li>Enter or edit the dataset's metadata (Step 3).</li>
            <li>Click the 'Save Metadata' button at the bottom of the web page.</li>
        </ol>

        <h2><a name="AddFiles">Add Data Files to a Dataset</a></h2>

        <p>Data Providers should follow these steps to add new data files to an existing dataset:</p>
        <ol>
            <li>If you haven't already, login to the website.</li>
            <li>Click the 'Contribute Data' link at the top of the web page.</li>
            <li>Click the Show Datasets link to view all datasets.</li>
            <li>Click the "Upload Files" link under the dataset to which you would like to add files.</li>
            <li>Click the 'Upload Files' link.</li>
            <li>Select the data files you wish to upload using "Choose Files".</li>
            <li>In most recent web browsers, it is possible to select several files at once.</li>
            <li>Click the 'Upload Files' button.</li>
        </ol>
        <p>Note that some older web browsers will not support the selection of multiple files at once. For web browsers
            that do not support this feature, it is still possible to use the '��File Upload' web page from Step 8.</p>

        <h2><a name="UpdateFiles">Updating Data Files for a Dataset</a></h2>

        <p>Sometimes data files change and need to be updated. To stop accidental overwriting of previously uploaded
            data files, it is necessary to first delete the out-dated version of a data file. Then the new updated data
            file may be uploaded.</p>

        <p>Special Note: files that are deleted from the website cannot be restored; they are permanently removed.</p>

        <p>The following steps describe how a Data Provider can update one or more data files:</p>
        <ol>
            <li>If you haven't already, login to the website.</li>
            <li>Click the 'Contribute Data' link at the top of the web page.</li>
            <li>Click the Show Datasets link to view all datasets.</li>
            <li>Click the "Upload Files" link under the dataset to which you would like to update files.</li>
            <li>Click the 'Mass Delete Files' link for the file that needs to be updated.</li>
            <li>Click the 'Confirm Delete' button.</li>
            <li>Repeat for all files being updated.</li>
            <li>Click the 'Upload Files' link.</li>
            <li>Select the data files you wish to upload.</li>
            <li>Note, you may select several files at once.</li>
            <li>Click the 'Upload Files' button.</li>
        </ol>
        <p>Note that some older web browsers will not support the selection of multiple files at once. For web browsers
            that do not support this feature, it is still possible to use the 'File Upload' web page from Step 8.</p>

        <h2><a name="AddResponsibleParty">Adding Responsible Parties (Contacts) to a Dataset</a></h2>

        <p>Responsible Parties (also known as Contacts) are the who contribute to a dataset. The following steps
            describe how to add a Responsible Party reference to a Dataset:</p>
        <ol>
            <li>If you haven't already, Login to the website.</li>
            <li>Click the 'Contribute Data' link at the top of the web page.</li>
            <li>Click the Show Datasets link to view all datasets.</li>
            <li>Click the "Add/Edit Responsible Parties" link under the dataset to which you would like to add a
                responsible party.
            </li>
            <li>Click the 'Add New Responsible Party' link.</li>
            <li>Enter the required form fields. If the Individual Name already exists, you may begin typing in the field
                and a select list of names containing those characters will appear for selection. Click on the desired
                name.
            </li>
            <li>You may also enter a new name.</li>
            <li>Click the 'Add Responsible Party' button.</li>
            <li>Responsible parties may also be ordered. Click the "Order Responsible Parties" link.</li>
            <li>Click and hold on a name, move to a new position, then release (drag and drop).</li>
            <li>Click the 'Save' button.
        </ol>
        <p>Responsible Party Individual Names should be in the following format: first_name (optional) middle_initial.
            last_name
        </p>

        <p>
            Examples:
        <ul>
            <li>Joe Smith</li>
            <li>Joe R. Smith</li>
        </ul>
        </p>

        <h2><a name="ResponsiblePartyFAQ">Responsible Party FAQ</a></h2>

        <h3>What is a Responsible Party?</h3>

        <p>
            A responsible party is a person or organization which serves as a contact for a Project or Dataset.
        </p>

        <h3>Do I need to enter a Responsible Party for every Role listed?</h3>

        <p>
            No. Author is the most important Role to enter. It is recommended that you fill in as many of the other
            Roles as you can for metadata completeness.
        </p>

        <h3>What are the definitions of the listed Roles?</h3>

        <p>
            The list of Roles comes from the ISO 19115 Metadata Definition and the Roles have the following
            meanings:<br/><br/>

            Author - Party who authored the resource.<br/>
            Principal Investigator - Key party responsible for gathering information and conducting research.<br/>
            Collaborating Principal Investigator - A PI collaboration on the project from another institution and not
            directly funded by the project award.<br/>
            CO Principal Investigator - Another project PI, who is not the Lead PI.<br/>
            Resource Provider - Party that supplies the resource.<br/>
            Custodian - Party that accepts accountability and responsibility for the data and ensures appropriate care
            and maintenance of the resource.</br>
            Owner - Party that owns the resource.<br/>
            User - Party that uses the resource.<br/>
            Distributor - Party that distributes the resource.<br/>
            Originator - Party that created the resource.<br/>
            Point of Contact - Party that can be contacted for acquiring knowledge about or acquisition of the resource.<br/>
            Processor - Party that has processed the data in a manner such that the resource has been modified.<br/>
            Publisher - Party that published the resource.<br/>
        </p>

        <h2><a name="AddAwards">Adding Award Numbers to a Dataset</a></h2>

        <p>NSF Program Directors have shown a preference for finding datasets by Award Number. It is highly recommended
            that all relevant award numbers be associated with their respective Datasets. The following steps describe
            how to add an Award Number to a Dataset:</p>
        <ol>
            <li>If you haven't already, login to the website.</li>
            <li>Click the 'Contribute Data' link at the top of the web page.</li>
            <li>Click the "View" link under the project to which you would like to add awards.</li>
            <li>Click the 'Edit' tab at the top of the dataset metadata.</li>
            <li>Click the 'Add/Delete Award Numbers' link.</li>
            <li>Click the 'Add new Award Number to this Project' link.</li>
            <li>Enter the required form fields.</li>
            <li>Click the 'Add Award' button.</li>
            <li>Award numbers can be individually deleted by clicking the 'Delete' link next to each Award Number.
        </ol>

        <h2><a name="AddRelatedLinks">Adding Related Links to a Dataset</a></h2>

        <p></p>
        <ol>
            <li>If you haven't already, login to the website.</li>
            <li>Click the 'Contribute Data' link at the top of the web page.</li>
            <li>Click the Show Datasets link to view all datasets.</li>
            <li>Click the "View" link under the dataset to which you would like to add related links.</li>
            <li>Click the 'Edit' tab at the top of the dataset metadata.</li>
            <li>Click the 'Add/Edit Related Links' link.</li>
            <li>Click the 'Add New Related Link' link.</li>
            <li>Enter the required form fields.</li>
            <li>Click the 'Add Related Link' button.</li>
            <li>A related link can also be edited or deleted by clicking the 'Edit' or 'Delete' link next to each
                related link.
            </li>
        </ol>

        <h2><a name="Metadata">Metadata Field Definitions</a></h2>

        <p>Note that any fields with asterisks (*) are required. Any comments that are <em>Italicized</em> are those you
            should keep in mind when completing the metadata entry form.</p>

        <p><a name="Title"></a><strong>*Title</strong>: This is the title of the dataset. Please make this title
            descriptive of the data content. Put the most important words first and do not use only dates for titles.
            Make sure that the dataset title is consistent with your other documentation. If the title doesn't fit in
            the entry window it is probably too long. <em>Keep in mind that, like the title of a research paper, the
                title of a dataset may be used in a citation.</em></p>

        <p><a name="ShortName"></a><strong>*Short Name</strong>: This is the url of your dataset and must be unique.</p>

        <p><a name="Description"></a><strong>*Description</strong>: This is a paragraph that describes the 'who, what,
            where, when, and why' of the dataset you are submitting. Remember, this text should describe the specific
            dataset, not the project as a whole. <em>Beware that copying/pasting from some documents may result in
                embedded control characters which may or may not be supported by the ACADIS Gateway</em>. <em>Also,
                consider referring to published documents when more detail is required to describe a dataset adequately.
                Be careful to exclude email addresses and other contact information in the dataset summary.</em></p>

        <p><a name="LocationKeyword"></a><strong>*Location Keyword(s)</strong>: Choose one or more location names that
            describe where your data were collected. </p>

        <p><a name="PlatformKeyword"></a><strong>*Platform Keyword(s)</strong>: Choose the platform or platforms for the
            instruments that acquired these data. Please contact ACADIS Community Support if your data cannot be
            accurately described by the contents of this field.</p>

        <p><a name="InstrumentName"></a><strong>Instrument Name(s):</strong> Choose one or more instruments from the
            NASA Global Change Master Directory (GCMD) terms.</p>

        <p><a name="ScienceKeyword"></a><strong>*Science Keyword(s)</strong>: Choose one or more from the NASA Global
            Change Master Directory (GCMD) terms.</p>

        <p><a name="ISOTopic"></a><strong>*ISO Topic(s)</strong>: Please select at least one ISO Topic from this list.
            ISO Topic is included in the ACADIS metadata profile to be compliant with international metadata standards.
        </p>

        <p><a name="DistributionFormat"></a><strong>*Distribution Format(s)</strong>: Choose the file format(s) of your
            data.</p>

        <p><a name="MinMaxLatLong"></a><strong>*Northernmost and Southernmost Latitude/Westernmost and Easternmost
            Longitude</strong>: Supply the values for spatial coverage. Enter this metadata as you would portray a box
            around your dataset in term of Southernmost and Northernmost latitude, Westernmost and Easternmost
            longitude. If the data are at one geographical location only, enter the latitude and longitude of that
            single point as both extents. For a moving platform, use a box that includes the track of the platform.
            Position to three decimals is usually sufficient. This field is required. The following guidelines from the
            GCMD may be useful:
        <ul>
            <li><strong>Northernmost_Latitude</strong>: The northernmost geographic latitude covered by the data. From:
                0 to 90 deg for northern latitude or 0 to -90 deg for southern latitude. For example, 60 will be 60
                degrees north, -60 will be 60 degrees south.
            </li>
            <li><strong>Southernmost_Latitude</strong>: The southernmost geographic latitude covered by the data. From:
                0 to 90 deg for northern latitude or 0 to -90 deg for southern latitude. For example, 60 will be 60
                degrees north, -60 will be 60 degrees south.
            </li>
            <li><strong>Westernmost_Longitude</strong>: The westernmost geographic longitude covered by the data. From:
                0 to 180 deg or 0 to -180 deg. The Prime Meridian (PM) is 0 degrees, measured positive (+) eastwards of
                the PM and negative (-) westward of the PM. For example, 45 will be 45 degrees east and -45 will be 45
                degrees west.
            </li>
            <li><strong>Easternmost_Longitude</strong>: The easternmost geographic longitude covered by the data. From:
                0 to 180 deg or 0 to -180 deg. The Prime Meridian (PM) is 0 degrees, measured positive (+) eastwards of
                the PM and negative (-) westward of the PM. For example, 45 will be 45 degrees east and -45 will be 45
                degrees west.
            </li>
        </ul>
        </p>
        <p><a name="Progress"></a><strong>Progress</strong>: This is meant to describe the state of the data being
            submitted. &lsquo;Planned&rsquo; refers to datasets to be collected in the future and are thus unavailable
            at the present time. &lsquo;In Work&rsquo; means the data are preliminary or data collection is
            ongoing. &lsquo;Completed&rsquo; refers to a dataset for which no updates or further data collection will be
            made.</p>

        <p><a name="BeginAndEndDate"></a><strong>Begin and End date</strong>: Choose from the drop down menus to provide
            the temporal coverage of the dataset. If hour and day of month are known, please include them. </p>

        <p><a name="Frequency"></a><strong>Frequency(ies)</strong>: Choose one or more options from the list that best
            describe your dataset. Choose the frequency(ies) that best approximate(s) the reporting frequency for your
            data.</p>

        <p><a name="SpatialType"></a><strong>Spatial Type(s)</strong>: Choose the data type(s) that most closely
            match(es) your data.</p>

        <p><a name="Resolution"></a><strong>Resolution(s)</strong>: Choose from the spatial resolution ranges given by
            the GCMD keywords. For some datasets (gridded satellite image data, for example), resolution can be harder
            to define. Choose the resolution(s) that best describe(s) the data. You can include more detailed
            information in the additional documentation or readme files you provide. </p>

        <p><a name="DatasetLanguage"></a><strong>Dataset Language</strong>: If the dataset (data and/or documentation)
            is in a language other than English, please enter the language.</p>

        <h2><a name="Appendix A"> Appendix A: ACADIS Metadata Record Examples</a></h2>

        <p>Project level information (project metadata) is illustrated in Figure 2. Figure 3 shows the metadata for a
            dataset that falls under the project from Figure 2. </p>

        <p><img src="<c:url value="/themes/cadis/images/AcadisProjectMetadataExample.jpg"/>"/><br>
            Figure 2. Project level information (project metadata). ACADIS support staff create this metadata based on
            NSF grant information.</p>

        <p><img src="<c:url value="/themes/cadis/images/AcadisDatasetMetadataExample.jpg"/>"/><br>
            Figure 3. A dataset metadata example.</p>

        <h2><a name="Appendix B"> Appendix B: Creating and Submitting Data Documentation</a></h2>

        <p>The &lsquo;readme&rsquo; file is a critical part of dataset documentation. It should contain enough
            information for a researcher who is unfamiliar with the project under which the data were acquired to use
            the dataset. It can begin with the same summary paragraph that is used in the metadata. The following are
            headings and a suggested outline for dataset documentation.</p>

        <h3>Dataset Title [This is also a metadata field.]</h3>

        <p>Please give your dataset a descriptive title that is less than 220 characters. It should be descriptive
            enough so that when a user is presented with a list of titles the general content of the dataset can be
            determined. For example, <em>Aerosols </em>would not be an adequate dataset title, but <em>Aerosol
                characterization and snow chemistry at Terra Nova Bay </em>would<em>. </em>So, if it can be done without
            making the title too long, include parameters measured, geographic location, instrument, investigator,
            project, and temporal coverage. </p>

        <p>Examples:
        <ul>
            <li>National Solar Radiation Data Base Hourly Solar Data from Alaska, 1961-1990</li>
            <li>Comprehensive Ocean - Atmosphere Dataset (COADS) LMRF Arctic Subset</li>
            <li>Daily Precipitation Sums at Coastal and Island Russian Arctic Stations, 1940-1990</li>
        </ul>
        </p>
        <h3>Summary Description [This is also a metadata field]</h3>
        <em>An &lsquo;above the fold&rsquo; overview of everything someone might need to know to decide if the dataset
            is something they can use., about &frac12; page long at maximum.</em></p>
        <p>This is a paragraph that describes the 'who, what, where, when, and why' of the dataset you are submitting
            with this metadata. Also, consider referring to published documents or web sites when more detail is
            required to describe a dataset adequately.</p>

        <h3>Contacts</h3>
        Any relevant people, with their titles, and role.</p>
        <h3>Background</h3>
        Any contextual information, for example, is the dataset part of a larger experiment or collection? Were certain aspects of the data acquisition procedure notable or unusual?</p>
        <h3>Detailed Data Description</h3>
        Here are some example subcategories. Use what seems logical, putting yourself in the place of a researcher outside your field who would like to use your data.</p>
        <ul>
            <li>Parameters</li>
            <li>Data File Format</li>
            <li>Sample Data Record</li>
            <li>File Naming Convention</li>
            <li>File Size</li>
            <li>Spatial and Temporal Coverage and Resolution</li>
            <li>Quality Assessment</li>
        </ul>
        <h3>Data Acquisition and Processing</h3>
        Be as descriptive as possible, with references to instrument manuals, standards, or other works where applicable.
        <p>Examples:
        <ul>
            <li>Snow depth on sea ice measurements were acquired according to the method detailed in Chapter 3.1 (Strum,
                2009) of <em>Field Techniques for Sea Ice Research </em>(Eicken, 2009)&rsquo;.
            </li>
            <li>Sea ice optics measurement sites were selected following the guidelines set forth in Chapter 3.6,
                Section 3.6.3, <em>Methods and Protocols </em>(Perovich, 2009) of <em>Field Techniques for Sea Ice
                    Research </em>(Eicken, et al., eds., 2009).
            </li>
        </ul>
        </p>
        <h3>References and Related Publications </h3>

        <p>These can include papers that use these data or like data. References that describe how measurements are
            taken are especially valuable.</p>

        <p>Examples:
        <ul>
            <li>Strum, M., 2009. Field Techniques for Snow Observations on Sea Ice, in <em>Field Techniques for Sea-Ice
                Research</em>, Edited by H. Eicken et al., University of Alaska Press, Fairbanks, 588pp; ISBN
                978-1-6022230-59-0,
            </li>
            <li>Perovich, D. 2009. Sea Ice Optics Measurement , in <em>Field Techniques for Sea-Ice Research</em>,
                Edited by H. Eicken et al., University of Alaska Press, Fairbanks, 588pp; ISBN 978-1-6022230-59-0
            </li>
            <li>H. Eicken, R. Gradinger, M. Salganek, K. Shirasawa, D. Perovich And M. Leppa&uml; Ranta, <em>eds</em>.
                2009. <em>Field techniques for sea ice research. </em>Fairbanks, AK, University of Alaska Press. 588pp.
                ISBN-10: 1-602230-59-5, ISBN-13: 978-1-602-23059-0,
            </li>
        </ul>
        </p>
        <h3>Acknowledgments</h3>
        As a rule, include the grant number or numbers and funding agencies that supported the work.</p>
        <h3>Document Information</h3>
        Here name the document author(s), date and the date it was created or revised. Including the date is important.</p>
        <p>If a revision was made, say when it was made and briefly, what the nature of the revision was. For
            example, &lsquo;This readme documentation file was revised on 4 Feb 2011 to add information about a new
            instrument used to measure snow density, and about the resulting new additional data files.&rsquo;</p>
    </tiles:putAttribute>
</tiles:insertDefinition>