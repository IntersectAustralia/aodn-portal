<script language="javascript">
	Ext.onReady(function() {
		new Ext.ux.form.SuperBoxSelect({
			transform: 'researchCodes',
            allowBlank: true,
			msgTarget: 'title',
            id:'researchCodesSelector',
            fieldLabel: 'Research Codes',
            resizable: true,
            name: 'researchCodes',
            width:400,
            displayField: 'text',
            valueField: 'value',
            classField: 'cls',
            styleField: 'style',
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
            extraItemStyle: 'border-width:2px',
            stackItems: true,
            emptyText: 'Enter name of user here'
         });
		new Ext.ux.form.SuperBoxSelect({
			transform: 'collectors',
            allowBlank: false,
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
            extraItemStyle: 'border-width:2px',
            stackItems: true,
            emptyText: 'Enter name of the collector here'
         });
		licenceList = ${au.org.emii.portal.Metadata.licenceList().encodeAsJSON()};
		$('.embargoParams').hide();
		$('.studentDataOwner').hide();
		$('.principalInvestigator').hide();
		$('.principalInvestigator').hide();
		initLicenceHint();
	});

	function initLicenceHint() {
		var index = $('#licenceSelector').val();

		if (index) {
			$('#licenceHint').html(licenceList[index].text + "<br><a href='" + licenceList[index].url + "'>" + licenceList[index].url + "</a>");
		}
	}

	function toggleEmbargoParams(cb) {
		if (cb.checked) {
			$('.embargoParams').show();
		}
		else {
			//$('#embargoExpiryDate').val('');
			Ext.getCmp('grantedUsersSelector').reset();
			$('.embargoParams').hide();
		}
	}

	function toggleStudentDataOwner(cb) {
		if (cb.checked) {
			$('.studentDataOwner').show();
		}
		else {
			$('#studentDataOwner').val('');
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
			$('.principalInvestigator').hide();
			$('.manager').hide();
			$('.collectors').show();
		}
		else if (index == 1) {
			$('.collectors').hide();
			$('.manager').hide();
			$('.principalInvestigator').show();
		}
		else {
			$('.collectors').hide();
			$('.principalInvestigator').hide();
			$('.manager').show();
		}
	}

	function addPublication() {
		var publications = $('#publications');
		var data = {
			publications: publications.val(),
			identifierType: $('#identifierType').val(),
			identifier: $('#identifier').val(),
			title: $('#title').val(),
			notes: $('#notes').val()
		};
		
		$.ajax({
			async: false,
			type: 'POST',
			contentType: 'application/json; charset=utf-8',
			url: "${createLink(controller:'publication', action:'add')}",
			data: JSON.stringify(data),
			dataType: 'json',
			success: function(data) {
				publications.append(new Option(data.publication.title, data.publication.id, true, true));
			},
			error: function(result) {
			}
		});
	}

</script>

<g:hiddenField name="id" value="${metadataInstance.id}" />

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
		<g:formatDate format="yyyy-MM-dd'T'HH:mm:ssZZ" date="${metadataInstance?.dateCreated}" />
	</td>
</tr>

<tr class="prop">
	<td valign="top" class="name"><label><g:message
				code="config.lastUpdated.label" default="Updated at" /></label>
	</td>
	<td valign="top" class="value">
		<g:formatDate format="yyyy-MM-dd'T'HH:mm:ssZZ" date="${metadataInstance?.lastUpdated}" /> 
	</td>
</tr>

<tr class="prop">
	<td valign="top" class="name"><label><g:message
				code="config.collectionPeriod.label" default="Collection period" /></label>
	</td>
	<td valign="top" class="value">
		<g:formatDate format="yyyy-MM-dd" date="${metadataInstance?.collectionPeriodFrom}" /> to <g:formatDate format="yyyy-MM-dd" date="${metadataInstance?.collectionPeriodTo}" />
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
	<td valign="top" class="value">
			${metadataInstance?.dataType}
	</td>
</tr>

<tr class="prop">
	<td valign="top" class="name"><label for="researchCodes"><g:message
				code="config.researchCodes.label"
				default="ANZSRC Field of Research Code(s)" /></label></td>
	<td valign="top"
		class="value ${hasErrors(bean: metadataInstance, field: 'researchCodes', 'errors')}">
		<g:select style="width: 400px;" name="researchCodes" id="researchCodes"
			from="${au.org.emii.portal.Metadata.researchCodeList()}"
			value="${metadataInstance?.researchCodes}"
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
			id="embargoExpiryDate" noSelection="['':'--']" default="none" />
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
			from="${au.org.emii.portal.Metadata.dataAccessList()}" optionKey="name"
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
		<g:checkBox name="studentOwned" value="${metadataInstance?.studentOwned}" onChange="toggleStudentDataOwner(this);" />
		This is student owned data
	</td>
</tr>

<tr class="prop studentDataOwner">
	<td valign="top" class="name"></td>
	<td valign="top"
		class="value ${hasErrors(bean: metadataInstance, field: 'studentDataOwner', 'errors')}">
		<g:select style="width: 250px;" name="studentDataOwner.id" id="studentDataOwner"
			optionValue="fullName" optionKey="id"
			from="${au.org.emii.portal.User.list()}"
			value="${metadataInstance?.studentDataOwner}"
			noSelection="${['': 'Select one...']}" /><br>
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

<tr class="prop principalInvestigator">
	<td valign="top" class="name"></td>
	<td valign="top"
		class="value ${hasErrors(bean: metadataInstance, field: 'principalInvestigator', 'errors')}">
		<g:select style="width: 300px;" name="principalInvestigator.id" id="principalInvestigator"
			optionValue="fullName" optionKey="id"
			from="${au.org.emii.portal.User.list()}"
			value="${metadataInstance?.principalInvestigator}" />
	</td>
</tr>

<tr class="prop manager">
	<td valign="top" class="name"></td>
	<td valign="top"
		class="value ${hasErrors(bean: metadataInstance, field: 'manager', 'errors')}">
		<g:select style="width: 300px;" name="manager.id" id="manager"
			optionValue="fullName" optionKey="id"
			from="${au.org.emii.portal.User.list()}"
			value="${metadataInstance?.manager}" />
	</td>
</tr>

<tr class="prop">
	<td valign="top" class="name"><label for="publications"><g:message
				code="config.publications.label"
				default="Related publications" /></label></td>
	<td valign="top"
		class="value ${hasErrors(bean: metadataInstance, field: 'publications', 'errors')}">
		<g:select style="width: 300px;" name="publications" id="publications"
			optionValue="title" optionKey="id"
			from="${au.org.emii.portal.Publication.list()}"
			value="${metadataInstance?.publications}"
			multiple="true" />
	</td>
</tr>

<tr class="prop">
	<td valign="top" class="name">
		<input type="button" name="create" class="save"
			value="${message(code: 'default.button.add.label', default: 'Add publication')}"
			onClick="addPublication();" /></td>
	<td valign="top" class="value">
		<g:select style="width: 300px;" name="identifierType" id="identifierType"
			optionValue="name" optionKey="name"
			from="${au.org.emii.portal.Publication.identifierTypeList()}" />
	</td>
</tr>

<tr class="prop">
	<td valign="top" class="name"></td>
	<td valign="top" class="value">
		<g:textField style="width: 300px;" name="identifier" id="identifier"
			placeholder="What is publication URI or identifier?" escapeHtml="false" />
	</td>
</tr>

<tr class="prop">
	<td valign="top" class="name"></td>
	<td valign="top" class="value">
		<g:textField style="width: 300px;" name="title" id="title"
			placeholder="What is the title of the publication?" escapeHtml="false" />
	</td>
</tr>

<tr class="prop">
	<td valign="top" class="name"></td>
	<td valign="top" class="value">
		<g:textArea rows="4" style="width: 300px;" name="notes" id="notes"
			placeholder="Enter additional information in here" escapeHtml="false" />
		<br>
		<font class="hint">
			Hint: for local publication types use the additional information text box to record
			the publications full citation information!			
		</font>
	</td>
</tr>

