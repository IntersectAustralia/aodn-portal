/*
 * Copyright 2012 IMOS
 *
 * The AODN/IMOS Portal is distributed under the terms of the GNU General Public License
 *
 */

databaseChangeLog = {

	// Structural changes and changes for all instances
	include file: 'initialisation.groovy'

    include file: '20120326-DN-SelfRegisteredUserPermissions.groovy'

    include file: '20120329-CJ-AddSelfRegisteredUserSnapshotPermissions.groovy'

	include file: '20120329-DN-RemovingTestingServer.groovy'

	include file: '20120308-DN-OpenIdTesting3.groovy'

	include file: '20120411-DN-UpdateDownloadCartFilename.groovy'

	include file: '20120413-DN-UpdateDownloadCartMaxSize.groovy'

	include file: '20120418-DN-AddingLayerHierarchyPath.groovy'

	include file: '20120412-JB-save-search.groovy'

	include file: '20120419-DN-DownloadCartChangesToConfig.groovy'

	include file: '20120420-PBL-UpdateDefaultBBox.groovy'

    include file: '20120509-DN-AddingLayerMetadataUrl.groovy'

    include file:  '20120511-PM-Dimensions.groovy'

    include file: '20120515-DN-RemovingLayerRelationship.groovy'

    include file: '20120515-DN-UpdatingAODNDownloadCartMessage.groovy'

	include file: '20120518-DN-UpdatingAODNDownloadCartMessage.groovy'

    include file: '20120427-DN-AodaacAggregatorChanges.groovy'

    include file: '20120531-DN-UserAodaacJobsRelationship.groovy'

    include file: '20120619-DN-AodaacJobDataFileCheck.groovy'

    include file:  '20120615-PM-OverridemretadataURL.groovy'

	include file: '20120614-CJ-MakeSavedMapNamesUnique.groovy'

    include file: '20120618-PM-WMS-Auth.groovy'

	include file: '20120529-fix_spelling_in_footer.groovy'

	include file: '201207051133-TF-Rename-default-baselayer.groovy'

    include file: '20120619-DN-AodaacJobEmailAddress.groovy'

    include file: '20120705-DN-AodaacProductLink.groovy'

    include file: '20120719-DN-DefaultSearchBoundingBox.groovy'

    include file: '20120720-DN-DefaultSearchByBoundingBox.groovy'

	include file: '1344911233293-TF-search-table-constraint-changes.groovy'

	include file: '20120815-PB-NewServerAttribute-infoFormat.groovy'

    include file: '20120828-CJ-AddServerOperations.groovy'

    include file: '20120815-PB-UpdateServer-InfoFormat.groovy'

    include file: '20120914-PM-Filter.groovy'

    include file: '20120924-PM-FilterFix.groovy'

    include file: '20120928-PM-PersistFilter.groovy'

    include file: '20121001-PM-ServerOwner.groovy'

    include file: '20121002-PM-FilterValues.groovy'

    include file: '20121004-PM-FilterAsList.groovy'

    include file: '20121005-PM-AddServerAdminRole.groovy'

	include file: '20121126-DN-FooterLicenseUpdates.groovy'

    include file: '20121129-PM-LayerAvailabilityFlag.groovy'

	include file: '20121130-DN-AodaacJobExpiration.groovy'

    include file: '20121205-PB-AddedStyleDomainToLayer.groovy'

    include file: '20121211-PB-RenameStyles2Style.groovy'

	include file: '20121218-DN-StyleAbstractTextColumn.groovy'

    include file: '20130218-AM-ServerOwnerPermissions.groovy'
    
    include file: '20130301-PBL-DefaultLayerZoom.groovy'

    include file: '20130305-PM-enableFilterFlag.groovy'

    include file: '20130312-PM-WFSLayer.groovy'

	include file: '20130320-JB-AddLayerViewParameters.groovy'


	// Changes that apply to all instances must be included here, above the calls to instance-specific change logs

	// Instance-specific changes
	include file: 'imos-changelog.groovy'
	include file: 'wa-changelog.groovy'
	include file: 'soos-changelog.groovy'


	include file: '20130416-add-organization-to-user-changeRelationOf-user-to-userRole.groovy'
	include file: '20130417-Inserting-UserRoles-and-fixed-permissions.groovy'
	include file: '20130418-inserting-organization-data.groovy'

	// Inserts default user role permission data
	include file: '20130501-inserting-userRole-permission-data.groovy'

	include file: '20130501-SL-Create-Metadata-Publication.groovy'

	// add upload dataset permissions to User Roles. [1]
	include file: '20130506-add-dataset-upload-permission-to-userRoles.groovy'
	include file: '20130507-add-metadata-create-permission-to-userRoles.groovy'

	include file: '20130509-SL-ChangeMetadataAndPublication.groovy'

	include file: '20130509-add-givenname-familyname-to-user.groovy'

	include file: '20130513-SL-ChangeMetadataConstraints.groovy'

	include file: '20130513-SL-ChangeMetadata.groovy'

	include file: '20130513-SL-ChangeMetadataResearchCodes.groovy'

	// add upload dataset permissions to User Roles. [2]
	include file: '20130515-add-metadata-save-permission-to-userRoles.groovy'

	// add upload dataset permissions to User Roles. [3]
	include file: '20130515-insert-publication-add-extAdd-permissions-to-userRoles.groovy'

	include file: '20130515-SL-ChangeMetadataContraints.groovy'

	include file: '20130516-SL-AddManagerToMetadata.groovy'

	include file: '20130517-SL-DropManagerFromMetadata.groovy'

	include file: '20130521-SL-AddColumnsToMetadata.groovy'

	include file: '20130523-SL-MetadataChangeDescriptionToText.groovy'

	include file: '20130523-SL-CreateMetadataPointAssociation.groovy'

	include file: '20130527-add-a-new-filed-active-to-user.groovy'

	include file: '20130529-add-metadata-all-permissions-to-userRoles.groovy'

	include file: '20130531-update-config-table.groovy'
}

