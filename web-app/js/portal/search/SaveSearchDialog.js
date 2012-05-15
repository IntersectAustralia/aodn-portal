Ext.namespace('Portal.search');

Portal.search.SaveSearchDialog = Ext.extend(Portal.common.SaveDialog, {
	
	constructor: function(cfg) {
		
		var config = Ext.apply({
			title: OpenLayers.i18n('saveSearchDialogTitle'),
			nameFieldLabel: OpenLayers.i18n('saveSearchName'),
			failedSaveErrMsg: OpenLayers.i18n('saveSearchFailureErrMsg')
		}, cfg);
		
		Portal.search.SaveSearchDialog.superclass.constructor.call(this, config);
		
		this.addEvents('savesearch');
	},
	
    onSave: function() {
    	
    	this.searchController.saveSearch(this.nameField.getValue());
    	this.close();
	}
});
