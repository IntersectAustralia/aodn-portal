<%--

 Copyright 2012 IMOS

 The AODN/IMOS Portal is distributed under the terms of the GNU General Public License

--%>



<%@ page import="au.org.emii.portal.UserRole" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'userRole.label', default: 'UserRole')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>

<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </span>
    <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label"
                                                                           args="[entityName]"/></g:link></span>
    <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label"
                                                                               args="[entityName]"/></g:link></span>
</div>

<div class="body">
    <h1><g:message code="default.edit.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${userRoleInstance}">
        <div class="errors">
            <g:renderErrors bean="${userRoleInstance}" as="list"/>
        </div>
    </g:hasErrors>
    <g:form method="post">
        <g:hiddenField name="id" value="${userRoleInstance?.id}" style="width:50px;"/>
        <g:hiddenField name="version" value="${userRoleInstance?.version}"/>
        <div class="dialog">
            <table>
                <tbody>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="name"><g:message code="userRole.name.label" default="Name"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: userRoleInstance, field: 'name', 'errors')}">
                        <g:textField name="name" value="${userRoleInstance?.name}" style="width:200px;"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="permissions"><g:message code="userRole.permissions.label"
                                                            default="Permissions"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: userRoleInstance, field: 'permissions', 'errors')}">
                        <g:each in="${userRoleInstance?.permissions}">
                            <g:textField name="permissions" value="${it}" style="width:200px;"/>  <p/>
                        </g:each>
                    %{--<g:textField name="permissions" value="Add permissions at here" autofocus="true" style="width:200px;"/>--}%
                        <g:textArea name="permissions" value="Add permissions at here" autofocus="true"
                                    style="width:200px; height:50px" onscroll="true"/>
                    </td>
                </tr>
                </tbody>
            </table>

        </div>

        <div class="buttons">
            <span class="button"><g:actionSubmit class="save" action="update"
                                                 value="${message(code: 'default.button.update.label', default: 'Update')}"/></span>
            <span class="button"><g:actionSubmit class="delete" action="delete"
                                                 value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                                 onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/></span>
        </div>
    </g:form>

    <hr>
    <h2>Permission Token References</h2>
    <label style="width:150px;"><b>Permission Format</b></label>
    <span>
        <a href="http://shiro.apache.org/permissions.html">http://shiro.apache.org/permissions.html</a>
    </span>
    <br/>
    <h3>Existing Controller/Action List</h3>
    <div>
        <table>
            <thead>
            <tr>
                <g:sortableColumn property="controller" title="Controller" style="width:150px;"/>

                <g:sortableColumn property="actions" title="Actions"/>
            </tr>
            </thead>

            <tbody>
            <g:each in="${controllerActions}" status="i" var="controllerAction">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                    <td>${controllerAction.getKey()}</td>

                    <td>${controllerAction.getValue()}</td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>
