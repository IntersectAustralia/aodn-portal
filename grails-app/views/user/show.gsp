<%--

 Copyright 2012 IMOS

 The AODN/IMOS Portal is distributed under the terms of the GNU General Public License

--%>


<%@ page import="au.org.emii.portal.User" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">

                <table>
                    <tbody>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "id")}</td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.AAF.label" default="AAF Token" /></td>

                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "openIdUrl")}</td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.organization.label" default="Organisation" /></td>

                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "organization")}</td>

                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.emailAddress.label" default="Email Address" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "emailAddress")}</td>
                        </tr>


                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.fullName.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "fullName")}</td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.permissions.label" default="Permissions" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "permissions")}</td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.role.label" default="Role" /></td>

                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "role")}</td>
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${userInstance?.id}" />
                    <user:loggedInUserInRole role="Administrator,Data Custodian">
                        <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    </user:loggedInUserInRole>

                    <user:loggedInUserInRole role="Administrator">
                        <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                    </user:loggedInUserInRole>
                </g:form>
            </div>
        </div>
    </body>
</html>
