The following brokered datasets are in error on ${gateway.name}:

<#list brokeredDatasetsAuditFailureReport as reportEntry>
Dataset URI:  ${reportEntry.brokeredDatasetURI}
Authoritative URI:  ${reportEntry.brokeredDatasetsAuthoritativeSourceURI}
Error Message:  ${reportEntry.errorMessage}

</#list>