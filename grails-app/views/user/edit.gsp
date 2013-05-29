<%--

 Copyright 2012 IMOS

 The AODN/IMOS Portal is distributed under the terms of the GNU General Public License

--%>



<%@ page import="au.org.emii.portal.UserRole; au.org.emii.portal.Organization; au.org.emii.portal.User" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${userInstance}">
            <div class="errors">
                <g:renderErrors bean="${userInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${userInstance?.id}" />
                <g:hiddenField name="version" value="${userInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="openIdUrl"><g:message code="user.AAF.label" default="AAF Token" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'openIdUrl', 'errors')}">
                                    <g:textField name="openIdUrl" value="${userInstance?.openIdUrl}" disabled="true" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="organization"><g:message code="user.organization.label" default="Organisation" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'organization', 'errors')}">
                                    <g:select name="organization"
                                              from="${Organization.list()}"
                                              optionKey="id"
                                              value="${userInstance?.organization?.id}"
                                    />
                                </td>
                            </tr>


                        <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="emailAddress"><g:message code="user.emailAddress.label" default="Email Address" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'emailAddress', 'errors')}">
                                    <g:textField name="emailAddress" value="${userInstance?.emailAddress}" />
                                </td>
                            </tr>



                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fullName"><g:message code="user.fullName.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'fullName', 'errors')}">
                                    <g:textField name="fullName" value="${userInstance?.fullName}" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label><g:message code="user.permissions.label" default="Permissions" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'permissions', 'errors')}">
                                    
                                </td>
                            </tr>


                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="role"><g:message code="user.role.label" default="Role" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'role', 'errors')}">
                                    <g:select name="role"
                                              from="${visibleUserRoles}"
                                              optionKey="id"
                                              value="${userInstance?.role?.id}"
                                    />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <user:loggedInUserInRole role="Administrator,Data Custodian">
                        <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    </user:loggedInUserInRole>

                    %{--<user:loggedInUserInRole role="Administrator">--}%

                        %{--<g:if test="${userInstance?.active}">--}%
                            %{--<span class="button"><g:actionSubmit class="delete" action="activateAndDeactivate" value="Deactivate" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>--}%
                        %{--</g:if>--}%
                        %{--<g:else>--}%
                            %{--<span class="button"><g:actionSubmit class="save" action="activateAndDeactivate" value="Activate" /></span>--}%
                        %{--</g:else>--}%
                    %{--</user:loggedInUserInRole>--}%
                </div>
            </g:form>
        </div>
    </body>
</html>
