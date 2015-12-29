Your group memberships have been updated:

Group:      ${groupName}
<#list memberships as membership>
Role: ${membership.role.description}     Status: ${membership.status.description}
</#list>