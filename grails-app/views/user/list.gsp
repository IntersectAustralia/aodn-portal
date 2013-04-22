<%--

 Copyright 2012 IMOS

 The AODN/IMOS Portal is distributed under the terms of the GNU General Public License

--%>

<%@ page import="au.org.emii.portal.Organization; au.org.emii.portal.UserRole; au.org.emii.portal.User" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>

        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>

            <div class="list">
                <g:form action="search">
                    <div>
                        <label style="width:150px;">Keyword search</label>
                        <span>
                        <input type="text" name="keyword" value="${params.keyword}" />
                        Search for users by name, email address, or AAF token
                        </span>
                    </div>

                    <div>
                        <label style="width:150px;">Role filter</label>
                        <span>
                            <g:select name="role"
                              from="${UserRole.list()}"
                              noSelection="${['null':'Select One Role']}"
                              optionKey="id"
                              value="${params.role}"
                            />
                            Limit search by role
                        </span>
                    </div>

                    <div>
                        <label style="width:150px;">Organisation filter</label>
                        <span>
                            <g:select name="organization"
                              from="${Organization.list()}"
                              noSelection="${['null':'Select One Organization']}"
                              optionKey="id"
                              value="${params.organization}"
                            />
                            Limit search by organization
                        </span>
                    </div>

                    <div>
                        <label style="width:150px;"></label>
                        <span>
                            <input type="submit" value="Search" />
                        </span>
                    </div>
                </g:form>
            </div>

            <br/>

            <div class="list">
                <table border="0">
                    <thead>
                        <tr>
                            <g:sortableColumn property="id" title="${message(code: 'user.id.label', default: 'Id')}" />

                            <g:sortableColumn property="openIdUrl" title="${message(code: 'user.AAF.label', default: 'AAF Token')}" />

                            <g:sortableColumn property="organization" title="${message(code: 'user.organization.label', default: 'Organisation')}" />

                            <g:sortableColumn property="emailAddress" title="${message(code: 'user.emailAddress.label', default: 'Email Address')}" />

                            <g:sortableColumn property="fullName" title="${message(code: 'user.fullName.label', default: 'Name')}" />

                            <g:sortableColumn property="role" title="${message(code: 'user.role.label', default: 'Role')}" />
                        </tr>
                    </thead>
                    <tbody>
                        <g:each in="${userInstanceList}" status="i" var="userInstance">
                            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                                <td><g:link action="show" id="${userInstance.id}">${fieldValue(bean: userInstance, field: "id")}</g:link></td>

                                <td>${fieldValue(bean: userInstance, field: "openIdUrl")}</td>

                                <td>${fieldValue(bean: userInstance, field: "organization")}</td>

                                <td>${fieldValue(bean: userInstance, field: "emailAddress")}</td>

                                <td>${fieldValue(bean: userInstance, field: "fullName")}</td>

                                %{--<td>${fieldValue(bean: userInstance, field: "permissions")}</td>--}%

                                <td>${fieldValue(bean: userInstance, field: "role")}</td>
                            </tr>
                        </g:each>
                    </tbody>
                </table>
            </div>

            <div class="pagination">
                <g:paginate total="${userInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
