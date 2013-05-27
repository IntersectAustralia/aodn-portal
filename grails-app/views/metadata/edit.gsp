

<%@ page import="au.org.emii.portal.Metadata" %>
<%@ page import="au.org.emii.portal.Publication"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <g:set var="entityName" value="${message(code: 'metadata.label', default: 'Metadata')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
        <g:render template="/common_includes"></g:render>
        <g:if env="development">
            <script src="${resource(dir: 'js', file: 'ext-3.3.1/adapter/ext/ext-base-debug.js')}" type="text/javascript"></script>
            <script src="${resource(dir: 'js', file: 'ext-3.3.1/ext-all-debug.js')}" type="text/javascript"></script>
            <script src="${resource(dir: 'js', file: 'OpenLayers-2.10/OpenLayers.js')}" type="text/javascript"></script>
            <!--- GeoExt (Has to be after Openlayers and ExJS) -->
            <script src="${resource(dir: 'js', file: 'GeoExt1.1/lib/GeoExt.js')}" type="text/javascript"></script>
        </g:if>
        <g:else>
            <script src="${resource(dir: 'js', file: 'ext-3.3.1/adapter/ext/ext-base.js')}" type="text/javascript"></script>
            <script src="${resource(dir: 'js', file: 'ext-3.3.1/ext-all.js')}" type="text/javascript"></script>
            <script src="${resource(dir: 'js', file: 'OpenLayers-2.10/OpenLayers.js')}" type="text/javascript"></script>
            <!--- GeoExt (Has to be after Openlayers and ExJS) -->
            <script src="${resource(dir: 'js', file: 'GeoExt1.1/script/GeoExt.js')}" type="text/javascript"></script>
        </g:else>
        <script src="${resource(dir: 'js', file: 'ext-ux/SuperBoxSelect/SuperBoxSelect.js')}" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="${resource(dir: 'js', file: 'ext-ux/SuperBoxSelect/superboxselect.css')}"/>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${metadataInstance}">
            <div class="errors">
                <g:renderErrors bean="${metadataInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${metadataInstance?.id}" />
                <g:hiddenField name="version" value="${metadataInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                            <g:render template="form"></g:render>
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
