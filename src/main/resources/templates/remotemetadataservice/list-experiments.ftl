<?xml version="1.0" encoding="UTF-8"?>
<esg:ESG xmlns:esg="http://www.earthsystemgrid.org/">
<#if experiments??>
    <#list experiments as experiment>
        <esg:experiment name="${experiment.name}">
            <esg:description>${experiment.description}</esg:description>
        </esg:experiment>
    </#list>
</#if>
</esg:ESG>