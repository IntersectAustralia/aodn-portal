<script>
	$(document).ready(function() { $("#researchCode").select2(); });
</script>

<tr class="prop">
	<td valign="top" class="name"><label><g:message
				code="config.serviceKey.label" default="Service key" /></label></td>
	<td valign="top" class="value">
		${metadataInstance?.serviceKey}
	</td>
</tr>

<tr class="prop">
	<td valign="top" class="name"><label><g:message
				code="config.key.label" default="Key" /></label>
	</td>
	<td valign="top" class="value">
		${metadataInstance?.key}
	</td>
</tr>

<tr class="prop">
	<td valign="top" class="name"><label><g:message
				code="config.datasetName.label" default="Dataset name" /></label>
	</td>
	<td valign="top" class="value">
		${metadataInstance?.datasetName}
	</td>
</tr>

<tr class="prop">
	<td valign="top" class="name"><label><g:message
				code="config.dateCreated.label" default="Created at" /></label>
	</td>
	<td valign="top" class="value">
		${metadataInstance?.dateCreated}
	</td>
</tr>

<tr class="prop">
	<td valign="top" class="name"><label><g:message
				code="config.lastUpdated.label" default="Updated at" /></label>
	</td>
	<td valign="top" class="value">
		${metadataInstance?.lastUpdated}
	</td>
</tr>

<tr class="prop">
	<td valign="top" class="name"><label><g:message
				code="config.collectionPeriod.label" default="Collection period" /></label>
	</td>
	<td valign="top" class="value">
		${metadataInstance?.collectionPeriodFrom} to ${metadataInstance?.collectionPeriodTo}
	</td>
</tr>

<tr class="prop">
	<td valign="top" class="name"><label for="description"><g:message
				code="config.description.label"
				default="Description" /></label></td>
	<td valign="top"
		class="value ${hasErrors(bean: metadataInstance, field: 'description', 'errors')}">
		<g:textArea rows="4" style="width: 400px;" name="description"
			value="${metadataInstance?.description}" escapeHtml="false" />
	</td>
</tr>

<tr class="prop">
	<td valign="top" class="name"><label for="dataType"><g:message
				code="config.dataType.label"
				default="Data type" /></label></td>
	<td valign="top"
		class="value ${hasErrors(bean: metadataInstance, field: 'dataType', 'errors')}">
		<g:select name="dataType" optionValue="name"
			from="${au.org.emii.portal.Metadata.dataTypeList()}" optionKey="id"
			value="${metadataInstance?.dataType}" />
	</td>
</tr>

<tr class="prop">
	<td valign="top" class="name"><label for="researchCode"><g:message
				code="config.researchCode.label"
				default="ANZSRC Field of Research Code(s)" /></label></td>
	<td valign="top"
		class="value ${hasErrors(bean: metadataInstance, field: 'researchCode', 'errors')}">
		<g:select name="researchCode" id="researchCode"
			from="${au.org.emii.portal.Metadata.researchCodeList()}"
			value="${metadataInstance?.researchCode}" />
	</td>
</tr>