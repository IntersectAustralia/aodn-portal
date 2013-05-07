

<%@ page import="au.org.emii.portal.Metadata" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'metadata.label', default: 'Metadata')}" />
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
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="collectionPeriodFrom"><g:message code="metadata.collectionPeriodFrom.label" default="Collection Period From" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: metadataInstance, field: 'collectionPeriodFrom', 'errors')}">
                                    <g:datePicker name="collectionPeriodFrom" precision="day" value="${metadataInstance?.collectionPeriodFrom}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="collectionPeriodTo"><g:message code="metadata.collectionPeriodTo.label" default="Collection Period To" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: metadataInstance, field: 'collectionPeriodTo', 'errors')}">
                                    <g:datePicker name="collectionPeriodTo" precision="day" value="${metadataInstance?.collectionPeriodTo}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="collectors"><g:message code="metadata.collectors.label" default="Collectors" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: metadataInstance, field: 'collectors', 'errors')}">
                                    <g:select name="collectors" from="${au.org.emii.portal.User.list()}" multiple="yes" optionKey="id" size="5" value="${metadataInstance?.collectors*.id}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="dataAccess"><g:message code="metadata.dataAccess.label" default="Data Access" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: metadataInstance, field: 'dataAccess', 'errors')}">
                                    <g:textField name="dataAccess" value="${metadataInstance?.dataAccess}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="dataOwner"><g:message code="metadata.dataOwner.label" default="Data Owner" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: metadataInstance, field: 'dataOwner', 'errors')}">
                                    <g:select name="dataOwner.id" from="${au.org.emii.portal.User.list()}" optionKey="id" value="${metadataInstance?.dataOwner?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="dataType"><g:message code="metadata.dataType.label" default="Data Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: metadataInstance, field: 'dataType', 'errors')}">
                                    <g:textField name="dataType" value="${metadataInstance?.dataType}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="datasetName"><g:message code="metadata.datasetName.label" default="Dataset Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: metadataInstance, field: 'datasetName', 'errors')}">
                                    <g:textField name="datasetName" value="${metadataInstance?.datasetName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="metadata.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: metadataInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${metadataInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="embargo"><g:message code="metadata.embargo.label" default="Embargo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: metadataInstance, field: 'embargo', 'errors')}">
                                    <g:checkBox name="embargo" value="${metadataInstance?.embargo}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="embargoExpiryDate"><g:message code="metadata.embargoExpiryDate.label" default="Embargo Expiry Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: metadataInstance, field: 'embargoExpiryDate', 'errors')}">
                                    <g:datePicker name="embargoExpiryDate" precision="day" value="${metadataInstance?.embargoExpiryDate}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="grantedUsers"><g:message code="metadata.grantedUsers.label" default="Granted Users" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: metadataInstance, field: 'grantedUsers', 'errors')}">
                                    <g:select name="grantedUsers" from="${au.org.emii.portal.User.list()}" multiple="yes" optionKey="id" size="5" value="${metadataInstance?.grantedUsers*.id}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="key"><g:message code="metadata.key.label" default="Key" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: metadataInstance, field: 'key', 'errors')}">
                                    <g:textField name="key" value="${metadataInstance?.key}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="licence"><g:message code="metadata.licence.label" default="Licence" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: metadataInstance, field: 'licence', 'errors')}">
                                    <g:textField name="licence" value="${metadataInstance?.licence}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="principalInvestigators"><g:message code="metadata.principalInvestigators.label" default="Principal Investigators" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: metadataInstance, field: 'principalInvestigators', 'errors')}">
                                    <g:select name="principalInvestigators" from="${au.org.emii.portal.User.list()}" multiple="yes" optionKey="id" size="5" value="${metadataInstance?.principalInvestigators*.id}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="publications"><g:message code="metadata.publications.label" default="Publications" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: metadataInstance, field: 'publications', 'errors')}">
                                    
<ul>
<g:each in="${metadataInstance?.publications?}" var="p">
    <li><g:link controller="publication" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="publication" action="create" params="['metadata.id': metadataInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'publication.label', default: 'Publication')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="researchCode"><g:message code="metadata.researchCode.label" default="Research Code" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: metadataInstance, field: 'researchCode', 'errors')}">
                                    <g:textField name="researchCode" value="${metadataInstance?.researchCode}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="serviceKey"><g:message code="metadata.serviceKey.label" default="Service Key" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: metadataInstance, field: 'serviceKey', 'errors')}">
                                    <g:textField name="serviceKey" value="${metadataInstance?.serviceKey}" />
                                </td>
                            </tr>
                        
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
