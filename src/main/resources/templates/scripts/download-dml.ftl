endpoint=${downloadScriptModel.myProxyServer}:${downloadScriptModel.myProxyServerPort}
<#list downloadScriptModel.fileDownloadModels as downloadFile>
${downloadFile.downloadURI}
</#list>