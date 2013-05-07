<style type="text/css">
	body {
		font:13px/1.3 arial,helvetica,clean,sans-serif;
		*font-size:small;
		*font:x-small;
		padding: 20px !important;
	}
	#page {
		width: 700px;
	}
	p {
		color: #333;
		margin-bottom: 7px;
		font-size: 14px;
	}
	form p {
		margin-top: 7px;
	}
	code {
		color: #000;
	}
	#intro p {
		color: #000;
		font-size: 15px;
		margin-bottom: 20px;
	}
	h1 {
		font-size: 1.6em;
		line-height: 2.0em;
		margin-bottom: 0.8em;
	}
	h2 {
		font-size: 1.2em;
		line-height: 1.6em;
		margin-bottom: 0.6em;
	}
	.exForm{
		padding: 20px 20px 20px 0px;
		font-size: 12px;
	}
	.x-tag {
		color: #693;
		background-image: url(tag_green.gif);
		background-repeat: no-repeat;
		background-position:  2px center;
		padding-left: 17px !important;
		*text-indent: 17px !important;
    }
	.x-flag{
        background-image: url("${resource(dir: 'js', file: 'ext-3.3.1/resources/images/default/s.gif')}");
        background-repeat: no-repeat;
        background-position:  2px center;
        text-indent: 17px !important;
    }
	body.ext-ie6 .x-flag .x-superboxselect-item-close {
		top: 4px;
		right: 2px;
	}
    .x-flag-au{
        background-image: url(flags/Australia.png);
		
    }
    .x-flag-at{
        background-image: url(flags/Austria.png);
    }
    .x-flag-ca{
        background-image: url(flags/Canada.png);
    }
    .x-flag-fr{
        background-image: url(flags/France.png);
    }
    .x-flag-it{
        background-image: url(flags/Italy.png);
    }
    .x-flag-jp{
        background-image: url(flags/Japan.png);
    }
    .x-flag-nz{
        background-image: url("flags/New Zealand.png");
    }
    .x-flag-us{
        background-image: url(flags/USA.png);
    }

	.small {
		font-size: small;
	}
	
	#f2Form .x-superboxselect-item-focus {
		color: #fff;
	}
	
</style>
<script language="javascript">
	Ext.onReady(function() {
		new Ext.ux.form.SuperBoxSelect({
			transform: 'researchCode',
            allowBlank:false,
			msgTarget: 'title',
            id:'researchCodeSelector',
            fieldLabel: 'States',
            resizable: true,
            name: 'researchCode',
            width:400,
            displayField: 'text',
            valueField: 'value',
            classField: 'cls',
            styleField: 'style',
			extraItemCls: 'x-flag',
            extraItemStyle: 'border-width:2px',
            stackItems: true
         });
	});
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
		<g:select style="width: 400px;" name="researchCode" id="researchCode"
			from="${au.org.emii.portal.Metadata.researchCodeList()}"
			value="${metadataInstance?.researchCode}"
			multiple="true" />
	</td>
</tr>