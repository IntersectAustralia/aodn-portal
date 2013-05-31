
<%@ page import="au.org.emii.portal.Metadata" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'metadata.label', default: 'Metadata')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            %{--<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>--}%
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
                            <g:sortableColumn property="id" title="${message(code: 'metadata.id.label', default: 'Id')}" />
                            <g:sortableColumn property="datasetName" title="${message(code: 'metadata.datasetName.label', default: 'Dataset Name')}" />
                            <g:sortableColumn property="dataType" title="${message(code: 'metadata.dataType.label', default: 'Data Type')}" />
                            <g:sortableColumn property="embargo" title="${message(code: 'metadata.embargo.label', default: 'Embargo?')}" />
                            <g:sortableColumn property="dataAccess" title="${message(code: 'metadata.dataAccess.label', default: 'Dataset Access')}" />
                            <g:sortableColumn property="studentOwned" title="${message(code: 'metadata.studentOwned.label', default: 'Owned By Student?')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${metadataInstanceList}" status="i" var="metadataInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="show" id="${metadataInstance.id}">${fieldValue(bean: metadataInstance, field: "id")}</g:link></td>
                            <td>${fieldValue(bean: metadataInstance, field: "datasetName")}</td>
                            <td>${fieldValue(bean: metadataInstance, field: "dataType")}</td>
                            <td>${fieldValue(bean: metadataInstance, field: "embargo")}</td>
                            <td>${fieldValue(bean: metadataInstance, field: "dataAccess")}</td>
                            <td>${fieldValue(bean: metadataInstance, field: "studentOwned")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${metadataInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
