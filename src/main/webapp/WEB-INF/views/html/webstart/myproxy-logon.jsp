<%@ page contentType="application/x-java-jnlp-file" info="My JNLP" %><%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><?xml version="1.0" encoding="UTF-8"?>
<c:url var="thisURL" value="/webstart/myProxyLogon/" />
<jnlp codebase="${codebase}${thisURL}">
<!-- comment -->
  <information>
    <title>MyProxyLogon-ESG</title>
    <vendor>NCSA (Modified by ANL for ESG)</vendor>
    <description>MyProxyLogon-ESG</description>
    <description kind="short">MyProxyLogon-ESG</description>
    <description kind="tooltip">MyProxyLogon-ESG</description>
    <homepage href="."/>
    <offline-allowed />
    <shortcut online="false">
      <desktop/>
      <menu/>
    </shortcut>
  </information>
  <resources>
    <j2se version="1.5+"/>
    <jar href="MyProxyLogon-ESG.jar" main="true"/>
  </resources>
  <offline-allowed />
  <security>
    <all-permissions />
  </security>
  <application-desc main-class="org.globus.esg.myproxy.MyProxyLogonGUI">
  <c:if test="${not empty user}">
  	<argument>-u</argument>
  	<argument>${user}</argument>
  </c:if>	
  	<argument>-h</argument>
  	<argument>${host}</argument>
  	<argument>-p</argument>
  	<argument>${port}</argument>
  </application-desc>
</jnlp>
