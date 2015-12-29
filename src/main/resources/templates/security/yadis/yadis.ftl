<?xml version="1.0" encoding="UTF-8"?>
<xrds:XRDS xmlns:xrds="xri://$xrds" xmlns="xri://$xrd*($v*2.0)">
    <XRD>
        <Service priority="0">
            <Type>http://specs.openid.net/auth/2.0/signon</Type>
            <Type>http://openid.net/signon/1.0</Type>
            <Type>http://openid.net/srv/ax/1.0</Type>
        <#--<URI>${gateway.idpEndpoint}</URI>-->
        <#--<LocalID>${user.openid}</LocalID>-->
        </Service>

        <Service priority="0">
            <Type>esg:attribute-service</Type>
        <#--<URI>${gateway.attrributeService}}</URI>-->
        </Service>

        <Service priority="0">
            <Type>esg:myproxy-service</Type>
        <#--<URI>${gateway.myproxyService}}</URI>-->
        </Service>

    </XRD>
</xrds:XRDS>