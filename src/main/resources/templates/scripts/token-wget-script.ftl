#!/bin/sh
<#list fileDownloadModels as fileDownloadModel>
wget -O '${fileDownloadModel.name}' '${fileDownloadModel.downloadURI}'
</#list>
