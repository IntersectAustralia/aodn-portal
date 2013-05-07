
<%@ page import="au.org.emii.portal.Metadata" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'metadata.label', default: 'Metadata')}" />
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
                            <td valign="top" class="name"><g:message code="metadata.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: metadataInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="metadata.collectionPeriodFrom.label" default="Collection Period From" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${metadataInstance?.collectionPeriodFrom}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="metadata.collectionPeriodTo.label" default="Collection Period To" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${metadataInstance?.collectionPeriodTo}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="metadata.collectors.label" default="Collectors" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${metadataInstance.collectors}" var="c">
                                    <li><g:link controller="user" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="metadata.dataAccess.label" default="Data Access" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: metadataInstance, field: "dataAccess")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="metadata.dataOwner.label" default="Data Owner" /></td>
                            
                            <td valign="top" class="value"><g:link controller="user" action="show" id="${metadataInstance?.dataOwner?.id}">${metadataInstance?.dataOwner?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="metadata.dataType.label" default="Data Type" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: metadataInstance, field: "dataType")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="metadata.datasetName.label" default="Dataset Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: metadataInstance, field: "datasetName")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="metadata.dateCreated.label" default="Date Created" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${metadataInstance?.dateCreated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="metadata.description.label" default="Description" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: metadataInstance, field: "description")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="metadata.embargo.label" default="Embargo" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${metadataInstance?.embargo}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="metadata.embargoExpiryDate.label" default="Embargo Expiry Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${metadataInstance?.embargoExpiryDate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="metadata.grantedUsers.label" default="Granted Users" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${metadataInstance.grantedUsers}" var="g">
                                    <li><g:link controller="user" action="show" id="${g.id}">${g?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="metadata.key.label" default="Key" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: metadataInstance, field: "key")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="metadata.lastUpdated.label" default="Last Updated" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${metadataInstance?.lastUpdated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="metadata.licence.label" default="Licence" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: metadataInstance, field: "licence")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="metadata.principalInvestigators.label" default="Principal Investigators" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${metadataInstance.principalInvestigators}" var="p">
                                    <li><g:link controller="user" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="metadata.publications.label" default="Publications" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${metadataInstance.publications}" var="p">
                                    <li><g:link controller="publication" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="metadata.researchCode.label" default="Research Code" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: metadataInstance, field: "researchCode")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="metadata.serviceKey.label" default="Service Key" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: metadataInstance, field: "serviceKey")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${metadataInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
