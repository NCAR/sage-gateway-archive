<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Search FAQ"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Search FAQ"/>

    <tiles:putAttribute name="body">

        <style type="text/css">

            .question {
                font-weight: bold;
                padding-top: 10px;
                padding-bottom: 10px;
            }

            .answer {
                line-height: 150%;
                padding-left: 25px;
            }

            .example {
                background-color: rgb(237, 245, 255);
                padding-top: 8px;
                padding-bottom: 8px;
                padding-left: 50px;
            }

        </style>

        <ul>
            <li><a href="#searchTargets">What can I search for using the Search box?</a></li>
            <li><a href="#requiredTerms">How do I specify required search terms?</a></li>
            <li><a href="#optionalTerms">How do I specify optional search terms?</a></li>
            <li><a href="#requiredAndOptionalTerms">How do I mix required and optional terms?</a></li>
            <li><a href="#grammar">Will grammatical variations of a term constitute a match?</a></li>
            <li><a href="#phrase">Can I search on a phrase?</a></li>
            <li><a href="#partial">Can I search on partial terms?</a></li>
            <li><a href="#asterisk">Can I search using the asterisk character for parts of the term I do not know?</a>
            </li>
            <li><a href="#nonAlphaNum">Are non-alphanumeric characters recognized when searching?</a></li>
            <li><a href="#case">Do I have to match case to find a result?</a></li>
            <li><a href="#termOrder">Does the order of search terms matter?</a></li>
            <li><a href="#resultOrder">How are the search results ordered?</a></li>
            <li><a href="#contactUs">I didn't find my answer. How can I get more help?</a></li>
        </ul>

        <a name="searchTargets"></a>

        <div class="question">What can I search for using the Search box?</div>
        <div class="answer">
            Datasets<br/>
            Projects
        </div>

        <a name="requiredTerms"></a>

        <div class="question">How do I specify required search terms?</div>
        <div class="answer">
            List all terms that you require separated by spaces:
            <div class="example">sea ice atlantic</div>
            The default search behavior is all listed terms must be found to produce a match. You may also separate
            each required term with "and":
            <div class="example">sea and ice and atlantic</div>
        </div>

        <a name="optionalTerms"></a>

        <div class="question">How do I specify optional search terms?</div>
        <div class="answer">
            Separate each optional term with "or":
            <div class="example">sea or ice or atlantic</div>
        </div>

        <a name="requiredAndOptionalTerms"></a>

        <div class="question">How do I mix required and optional terms?</div>
        <div class="answer">
            Use parentheses to group optional terms with required terms appropriately.
            <div class="example">(sea or ice) and atlantic</div>
        </div>

        <a name="grammar"></a>

        <div class="question">Will grammatical variations of a term constitute a match?</div>
        <div class="answer">
            Yes, you can specify a term in a given grammatical form and variations will match. For example, the term
            <div class="example">mapping</div>
            will match "map", "maps", and "mapped".
        </div>

        <a name="phrase"></a>

        <div class="question">Can I search for a phrase?</div>
        <div class="answer">
            Yes, specify a phrase or a number of terms required consecutively using double quotes.
            <div class="example">"sea ice"</div>
        </div>

        <a name="partial"></a>

        <div class="question">Can I search on partial terms?</div>
        <div class="answer">
            No, the search implementation is term or word based and only whole words are identifiable in the search
            index.
        </div>

        <a name="asterisk"></a>

        <div class="question">Can I search using the asterisk character for parts of the term I do not know?</div>
        <div class="answer">
            No, the search implementation does not process wild card characters like the asterisk or question mark.
        </div>

        <a name="nonAlphaNum"></a>

        <div class="question">Are non-alphanumeric characters recognized when searching?</div>
        <div class="answer">
            No, only alphanumeric characters are recognized.
        </div>

        <a name="case"></a>

        <div class="question">Do I have to match case to find a result?</div>
        <div class="answer">
            No, case is not considered when finding a match to your search term(s). The following terms will produce
            the same results:
            <div class="example">BERING SEA<br/>
                bering sea<br/>
                Bering Sea
            </div>
        </div>

        <a name="termOrder"></a>

        <div class="question">Does the order of search terms matter?</div>
        <div class="answer">
            No.
        </div>

        <a name="resultOrder"></a>

        <div class="question">How are the search results ordered?</div>
        <div class="answer">
            The results are ranked so the closest matches are at the top of the list.
        </div>

        <a name="contactUs"></a>

        <div class="question">I didn't find my answer. How can I get more help?</div>
        <div class="answer">
            Contact <a href="mailto:<spring:message code="contactus.mailaddress"/>"><spring:message
                code="contactus.mailaddress"/></a>
        </div>
        <br>

    </tiles:putAttribute>

</tiles:insertDefinition>
