<style type="text/css">
	/*
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
    }*/
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
            allowBlank: true,
			msgTarget: 'title',
            id:'researchCodeSelector',
            fieldLabel: 'Research Code',
            resizable: true,
            name: 'researchCode',
            width:400,
            displayField: 'text',
            valueField: 'value',
            classField: 'cls',
            styleField: 'style',
			//extraItemCls: 'x-flag',
            extraItemStyle: 'border-width:2px',
            stackItems: true
         });
		new Ext.ux.form.SuperBoxSelect({
			transform: 'grantedUsers',
            allowBlank: true,
			msgTarget: 'title',
            id:'grantedUsersSelector',
            fieldLabel: 'Granted Users',
            resizable: true,
            name: 'grantedUsers',
            width:200,
            displayField: 'text',
            valueField: 'value',
            classField: 'cls',
            styleField: 'style',
			//extraItemCls: 'x-flag',
            extraItemStyle: 'border-width:2px',
            stackItems: true,
            emptyText: 'Enter name of user here'
         });
		new Ext.ux.form.SuperBoxSelect({
			transform: 'studentDataOwner',
            allowBlank: true,
			msgTarget: 'title',
            id:'studentDataOwnerSelector',
            fieldLabel: 'Student Owner',
            resizable: true,
            name: 'studentDataOwner',
            width:250,
            displayField: 'text',
            valueField: 'value',
            classField: 'cls',
            styleField: 'style',
			//extraItemCls: 'x-flag',
            extraItemStyle: 'border-width:2px',
            stackItems: true,
            emptyText: 'Enter name of the student data owner'
         });
		new Ext.ux.form.SuperBoxSelect({
			transform: 'collectors',
            allowBlank: true,
			msgTarget: 'title',
            id:'collectorsSelector',
            fieldLabel: 'Collectors',
            resizable: true,
            name: 'collectors',
            width:250,
            displayField: 'text',
            valueField: 'value',
            classField: 'cls',
            styleField: 'style',
			//extraItemCls: 'x-flag',
            extraItemStyle: 'border-width:2px',
            stackItems: true,
            emptyText: 'Enter name of the collector here'
         });
		new Ext.ux.form.SuperBoxSelect({
			transform: 'principalInvestigators',
            allowBlank: true,
			msgTarget: 'title',
            id:'principalInvestigatorsSelector',
            fieldLabel: 'Principal Investigators',
            resizable: true,
            name: 'principalInvestigators',
            width:300,
            displayField: 'text',
            valueField: 'value',
            classField: 'cls',
            styleField: 'style',
			//extraItemCls: 'x-flag',
            extraItemStyle: 'border-width:2px',
            stackItems: true,
            emptyText: 'Enter name of the principal investigator here'
         });
		licenceList = ${au.org.emii.portal.Metadata.licenceList().encodeAsJSON()};
		$('.embargoParams').hide();
		$('.studentDataOwner').hide();
		$('.principalInvestigators').hide();
		initLicenceHint();
	});

	function initLicenceHint() {
		var index = $('#licenceSelector').value || 0;
		$('#licenceHint').html(licenceList[index].text + "<br><a href='" + licenceList[index].url + "'>" + licenceList[index].url + "</a>");
	}

	function toggleEmbargoParams(cb) {
		if (cb.checked) {
			$('.embargoParams').show();
		}
		else {
			$('.embargoParams').hide();
		}
	}

	function toggleStudentDataOwner(cb) {
		if (cb.checked) {
			$('.studentDataOwner').show();
		}
		else {
			$('.studentDataOwner').hide();
		}
	}

	function changeLicenceHint(s) {
		var index = s.value;
		$('#licenceHint').html(licenceList[index].text + "<br><a href='" + licenceList[index].url + "'>" + licenceList[index].url + "</a>");
	}

	function changeRelatedPartyType(s) {
		var index = s.value;

		if (index == 0) {
			$('.principalInvestigators').hide();
			$('.collectors').show();
		}
		else {
			$('.collectors').hide();
			$('.principalInvestigators').show();
		}
	}
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

<tr class="prop">
	<td valign="top" class="name"><label for="embargo"><g:message
				code="config.embargo.label"
				default="Embargo" /></label></td>
	<td valign="top"
		class="value ${hasErrors(bean: metadataInstance, field: 'embargo', 'errors')}">
		<g:checkBox name="embargo" value="${metadataInstance?.embargo}" onChange="toggleEmbargoParams(this);" />
		Embargo the dataset
	</td>
</tr>

<tr class="prop embargoParams">
	<td valign="top" class="name"></td>
	<td valign="top"
		class="value ${hasErrors(bean: metadataInstance, field: 'embargoExpiryDate', 'errors')}">
		<label for="embargoExpiryDate"><g:message
				code="config.embargoExpiryDate.label"
				default="Embargo expiry" /></label>
		<g:datePicker name="embargoExpiryDate" precision="day" value="${metadataInstance?.embargoExpiryDate}"
			noSelection="['':'--']" />
	</td>
</tr>

<tr class="prop embargoParams">
	<td valign="top" class="name"></td>
	<td valign="top"
		class="value ${hasErrors(bean: metadataInstance, field: 'grantedUsers', 'errors')}">
		<g:select style="width: 200px;" name="grantedUsers" id="grantedUsers"
			optionValue="fullName" optionKey="id"
			from="${au.org.emii.portal.User.list()}"
			value="${metadataInstance?.grantedUsers}"
			multiple="true" />
		<font class="hint">
			Hint: for the embargo to never expire leave the embargo expiry empty<br/><br/>
			To grant access to this dataset, enter the users name in the text box above!
		</font>
	</td>
</tr>

<tr class="prop">
	<td valign="top" class="name"><label for="dataAccess"><g:message
				code="config.dataAccess.label"
				default="Data Access" /></label></td>
	<td valign="top"
		class="value ${hasErrors(bean: metadataInstance, field: 'dataAccess', 'errors')}">
		<g:select name="dataAccess" optionValue="name"
			from="${au.org.emii.portal.Metadata.dataAccessList()}" optionKey="id"
			value="${metadataInstance?.dataAccess}" />
		<font class="hint">
			Hint: Public access data must have a Creative Commons 3.0 licence
		</font>
	</td>
</tr>

<tr class="prop">
	<td valign="top" class="name"><label for="licence"><g:message
				code="config.licence.label"
				default="Licence" /></label></td>
	<td valign="top"
		class="value ${hasErrors(bean: metadataInstance, field: 'licence', 'errors')}">
		<g:select name="licence" optionValue="name" id="licenceSelector"
			from="${au.org.emii.portal.Metadata.licenceList()}" optionKey="id"
			value="${metadataInstance?.licence}"
			onChange="changeLicenceHint(this)" />
		<font class="hint" id="licenceHint"></font>
	</td>
</tr>

<tr class="prop">
	<td valign="top" class="name"><label for="studentOwned"><g:message
				code="config.studentOwned.label"
				default="Data Owner" /></label></td>
	<td valign="top"
		class="value ${hasErrors(bean: metadataInstance, field: 'studentOwned', 'errors')}">
		<g:checkBox name="dataOwner" value="${metadataInstance?.studentOwned}" onChange="toggleStudentDataOwner(this);" />
		This is student owned data
	</td>
</tr>

<tr class="prop studentDataOwner">
	<td valign="top" class="name"></td>
	<td valign="top"
		class="value ${hasErrors(bean: metadataInstance, field: 'studentDataOwner', 'errors')}">
		<g:select style="width: 250px;" name="studentDataOwner" id="studentDataOwner"
			optionValue="fullName" optionKey="id"
			from="${au.org.emii.portal.User.list()}"
			value="${metadataInstance?.studentDataOwner}" />
		<font class="hint">
			If this data is owned by a student please identify the student, it is important to seek formal permission
			from the student before using this system to manage their data!<br><br>
			If the data is not owned by a student the default owner for the data is The University of Sydney			
		</font>
	</td>
</tr>

<tr class="prop">
	<td valign="top" class="name"><label for="relatedPartyType"><g:message
				code="config.relatedPartyType.label"
				default="Related parties" /></label></td>
	<td valign="top" class="value">
		<g:select name="relatedPartyType" optionValue="name" id="relatedPartyTypeSelector"
			from="${au.org.emii.portal.Metadata.relatedPartyTypeList()}" optionKey="id"
			onChange="changeRelatedPartyType(this)" />
	</td>
</tr>

<tr class="prop collectors">
	<td valign="top" class="name"></td>
	<td valign="top"
		class="value ${hasErrors(bean: metadataInstance, field: 'collectors', 'errors')}">
		<g:select style="width: 250px;" name="collectors" id="collectors"
			optionValue="fullName" optionKey="id"
			from="${au.org.emii.portal.User.list()}"
			value="${metadataInstance?.collectors}"
			multiple="true" />
	</td>
</tr>

<tr class="prop principalInvestigators">
	<td valign="top" class="name"></td>
	<td valign="top"
		class="value ${hasErrors(bean: metadataInstance, field: 'principalInvestigators', 'errors')}">
		<g:select style="width: 300px;" name="principalInvestigators" id="principalInvestigators"
			optionValue="fullName" optionKey="id"
			from="${au.org.emii.portal.User.list()}"
			value="${metadataInstance?.principalInvestigators}"
			multiple="true" />
	</td>
</tr>

