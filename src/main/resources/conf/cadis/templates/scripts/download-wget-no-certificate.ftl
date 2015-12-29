#!/bin/sh
wget -O '${dataset.shortName}.xml' '${gateway.baseSecureURL}dataset/${dataset.shortName}.iso19139'
<#list downloadScriptModel.fileDownloadModels as fileDownload>
wget -O '${fileDownload.name}' '${fileDownload.downloadURI}'
</#list>