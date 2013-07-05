

<%@ page import="au.org.emii.portal.Metadata" %>
<%@ page import="au.org.emii.portal.Publication"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
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
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            %{--<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>--}%
        </div>

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

            <g:form action="update" >
                %{--<g:hiddenField name="id" value="${metadataInstance?.id}" />--}%
                <g:hiddenField name="version" value="${metadataInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                            <g:render template="form"></g:render>
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
					<span class="button"><input type="button" name="update" class="update" value="${message(code: 'default.button.update.label', default: 'Update')}" onClick="validate(this.form);" /></span>
				</div>
            </g:form>
        </div>
    </body>
</html>
