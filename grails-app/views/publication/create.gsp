

<%@ page import="au.org.emii.portal.Publication" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'publication.label', default: 'Publication')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${publicationInstance}">
            <div class="errors">
                <g:renderErrors bean="${publicationInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="identifier"><g:message code="publication.identifier.label" default="Identifier" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: publicationInstance, field: 'identifier', 'errors')}">
                                    <g:textField name="identifier" value="${publicationInstance?.identifier}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="identifierType"><g:message code="publication.identifierType.label" default="Identifier Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: publicationInstance, field: 'identifierType', 'errors')}">
                                    <g:textField name="identifierType" value="${publicationInstance?.identifierType}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="notes"><g:message code="publication.notes.label" default="Notes" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: publicationInstance, field: 'notes', 'errors')}">
                                    <g:textField name="notes" value="${publicationInstance?.notes}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="title"><g:message code="publication.title.label" default="Title" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: publicationInstance, field: 'title', 'errors')}">
                                    <g:textField name="title" value="${publicationInstance?.title}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
