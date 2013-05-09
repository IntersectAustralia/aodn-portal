
<%@ page import="au.org.emii.portal.Publication" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'publication.label', default: 'Publication')}" />
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
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'publication.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="identifier" title="${message(code: 'publication.identifier.label', default: 'Identifier')}" />
                        
                            <g:sortableColumn property="identifierType" title="${message(code: 'publication.identifierType.label', default: 'Identifier Type')}" />
                        
                            <g:sortableColumn property="notes" title="${message(code: 'publication.notes.label', default: 'Notes')}" />
                        
                            <g:sortableColumn property="title" title="${message(code: 'publication.title.label', default: 'Title')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${publicationInstanceList}" status="i" var="publicationInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${publicationInstance.id}">${fieldValue(bean: publicationInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: publicationInstance, field: "identifier")}</td>
                        
                            <td>${fieldValue(bean: publicationInstance, field: "identifierType")}</td>
                        
                            <td>${fieldValue(bean: publicationInstance, field: "notes")}</td>
                        
                            <td>${fieldValue(bean: publicationInstance, field: "title")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${publicationInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
