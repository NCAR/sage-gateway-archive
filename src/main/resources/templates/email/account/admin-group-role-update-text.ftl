The memberships for a member of your group have been updated:

User Id:    ${user.identifier}
OpenID:     ${user.openid}
First Name: ${user.firstName}
Last Name:  ${user.lastName}
Email:      ${user.email}
Group:      ${groupName}
<#list memberships as membership>
Role: ${membership.role.description}     Status: ${membership.status.description}
</#list>




