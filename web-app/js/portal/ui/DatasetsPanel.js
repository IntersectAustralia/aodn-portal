
/*
 * Copyright 2012 IMOS
 *
 * The AODN/IMOS Portal is distributed under the terms of the GNU General Public License
 *
 */

Ext.namespace('Portal.ui');

Portal.ui.DatasetsPanel = Ext.extend(Ext.Panel, {
	
	constructor: function(cfg) {		
		
		this.datasetsInputBox = new Ext.form.TextField({
					
			ref: "datasetsInput",
			fieldClass: "datasetsInput",
			hideLabel: true,
			emptyText: OpenLayers.i18n('Filename'),
			width:600,
			anchor:'99%',
			name: 'filename',
			enableKeyEvents: true,
			listeners: {
				scope: this,
				specialkey: function(field,e) {
					if (e.getKey() == e.ENTER) {		
						var filename = field.getValue();
						if (filename.length > 0) {
							// Upload file
						}
					}
				}				
			}
		});
		
		this.spinnerPanel = new Ext.Panel({  
			ref: "spinnner",
			hidden: true,
			//cls: 'extAjaxLoading',
			html: '<div class="loading-indicator"> Loading...</div>'
		});
		
		this.hbox = new Ext.Container({
			layout: 'hbox',
			items: [{
				html: "<h4>Upload datasets here</h4>",
				flex: 5
				
			},
			{ 
				flex: 1,
				margins: {top:0, right:1, bottom:4, left:0},
				items: [{
						
					ref: 'uploadButton',				
					xtype: 'button',					
			
					text: "Upload",
					tooltip: "Upload datasets file",
					listeners:	{
						scope: this,
						'click': this.onSubmitClick
					}
				}]
			}]
		});
		
		this.statusPanel = new Ext.Panel({
			ref: "status",
			cls: 'addServerStatusPanel',
			padding: 10,
			title: "Note:",
			collapsible: true,
			titleCollapse: true,
			collapseFirst: true,
			html: 'Upload datasets help',
			listeners: {
				beforecollapse: function() {
					this.setTitle("");
				},
				beforeexpand: function(){
					this.setTitle("Note:");
					this.update(OpenLayers.i18n('Upload datasets help'));
				}
			}
			
		});
		
		this.proxyURL = "proxy/upload";
		
		var config = Ext.apply({
			title: 'Add Datasets',
			layout:'form',
			cls: '',
			items: [
				this.hbox,
				this.datasetsInputBox,
				this.spinnerPanel,
				this.statusPanel
			]
		}, cfg);
		
		Portal.ui.DatasetsPanel.superclass.constructor.call(this, config);
	},
	
	onSubmitClick: function() {
		var url = this.datasetsInput.getValue();
		if (url.length > 0) {
			// Upload file
		}
	},
	
	buildURL: function(url){
		
		// if URL has parameters then we need to send a different query to the proxy 
		var concatenator = (url.indexOf("?") > 0)? "&" : "?";
		// add protocol if user left it off
		url = (url.indexOf("http://") == 0)? url : "http://" + url;
		
		url = url+ concatenator + "SERVICE=WMS&request=GetCapabilities";
		return this.proxyWMSURL+encodeURIComponent(url)
	},

	addWMStoTree: function(url,statusField, spinnerPanel){
				
		var menu = this;
		
		menu.add(
			new Ext.tree.TreePanel({
				autoHeight: true,
				border: false,
				rootVisible: false,
				root: new Ext.tree.AsyncTreeNode({
					text: url,
					expanded: true,
					loader: new GeoExt.tree.WMSCapabilitiesLoader({
						
						url: this.buildURL(url),
						layerOptions: {
							buffer: 0, 
							singleTile: false, 
							ratio: 1
						},
						layerParams: {
							'TRANSPARENT': true,
							'queryable': true, //if not base layer, then might as well make it querayable, since
											   //GetFeatureInfo popup goes through proxy, which does NOT allow
											   //connections to untrusted servers
							'isBaseLayer': false
						},
						
						// customize the createNode method to add a checkbox to nodes
						createNode: function(attr) {
							//attr.checked = attr.leaf ? false : undefined;
							//attr.active=attr.leaf ? false : undefined;;
							//
							// add attributes we need latter to create a complete layer for our map
							attr.server= {};
							attr.server.url = url;
							attr.server.opacity = 50;
							attr.server.type = "WMS";
							
							return GeoExt.tree.WMSCapabilitiesLoader.prototype.createNode.apply(this, [attr]);
						},
						listeners: {
							
							scope: this,
							beforeload: function() {
								
								// check if loaded previously
								if (this.loadedServerURLS.indexOf(url) >= 0) {
									statusField.setTitle(OpenLayers.i18n('addYourURLDuplicate'));
									statusField.update(OpenLayers.i18n('addYourURLDuplicateBody'));
									return false;
								}
								else {
									spinnerPanel.show();
									setViewPortTab(1); // move to the map tab
									statusField.setTitle(OpenLayers.i18n('searching'));
									statusField.hide();
								}
							},
							
							load: function() {
								
								spinnerPanel.hide('slow');
								setViewPortTab(1); // move to the map tab
								statusField.setTitle(OpenLayers.i18n('addYourURLSuccessful'));
								// ADD TO LIST OF SUCCESSFULL SERVERS
								statusField.update("");
								statusField.show();
								// add to successfully loaded array
								this.loadedServerURLS.push(url);
							},
							loadexception: function(obj,node,e) {
								
								spinnerPanel.hide('slow');
								statusField.setTitle("ERROR: Server URL Invalid");
								if (e.responseText != undefined) {
									statusField.update( OpenLayers.i18n('addYourURLUnsuccessful',{
										url: e.responseText
										}));
								}
								else {
									statusField.update( OpenLayers.i18n('addYourURLUnsuccessfulNoResponse'));
								}								
								statusField.show().expand();
							}
						}
					})
				}),
				
				listeners: {
					// Add layers to the map when ckecked, remove when unchecked.
					// Note that this does not take care of maintaining the layer
					// order on the map.
					'click': function(node) {
						if (node.attributes.layer != null) {
							var layer = node.attributes.layer;	
							statusField.setTitle(OpenLayers.i18n('addYourLayerSuccess',{
								layerName: layer.name.replace(/_/gi, " ")
								}));
							statusField.update("");
							statusField.show();
							layer.server = node.attributes.server;
							
							Ext.MsgBus.publish('addLayerUsingOpenLayer', layer);
						}
						else {
							node.expand();
						}
					}
				}
			})
		);
		
		menu.doLayout();
	}
});
