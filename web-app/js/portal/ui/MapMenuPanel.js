/*
 * Copyright 2012 IMOS
 *
 * The AODN/IMOS Portal is distributed under the terms of the GNU General Public License
 *
 */

Ext.namespace('Portal.ui');

Portal.ui.MapMenuPanel = Ext.extend(Ext.TabPanel, {

    constructor:function (cfg) {

        var itemsToAdd = [];

        this.defaultMenuTree = new Portal.ui.MenuPanel({
            menuId:cfg.menuId
        });
        itemsToAdd.push(this.defaultMenuTree);

        if (appConfigStore.getById('facetedSearch.enabled').data.value) {

            this.selectionPanel = new Portal.ui.SelectionPanel({
                title:'Faceted Search',
                appConfig:Portal.app.config,
                split:true,
                searchRestriction:{
                    protocols:Portal.app.config.metadataLayerProtocols.split("\n").join(' or ')
                },
                searchTabTitle:OpenLayers.i18n('layerSearchTabTitle')
            });
            itemsToAdd.push(this.selectionPanel);
        }

        this.userDefinedWMSPanel = new Portal.ui.UserDefinedWMSPanel({});
        itemsToAdd.push(this.userDefinedWMSPanel);

        var datasetsPanel = new Ext.form.FormPanel({
            title: 'Add Dataset',
            layout: 'form',
            cls: '',
            fileUpload: true,
            autoHeight: true,
            items: [
                {
                    html: '<h4>Add new dataset</h4>'
                },
                {
                    xtype: 'combo',
                    fieldLabel: 'Date type',
                    typeAhead: true,
                    triggerAction: 'all',
                    lazyRender: true,
                    mode: 'local',
                    store: new Ext.data.ArrayStore({
                        id: 0,
                        fields: [
                            'id',
                            'displayText'
                        ],
                        data: [[1, 'Sonde survey'], [2, 'Nutrient sample'], [3, 'Carbon Dioxide survey']]
                    }),
                    valueField: 'id',
                    displayField: 'displayText'
                },
                {
                    xtype: 'fileuploadfield',
                    id: 'dataset-file',
                    emptyText: 'Browse',
                    fieldLabel: 'Data file',
                    name: 'dataset-path',
                    buttonText: 'Browse'
                },
                {
                    xtype: 'fileuploadfield',
                    id: 'metadata-file',
                    emptyText: 'Browse',
                    fieldLabel: 'Metadata file(optional)',
                    name: 'metadata-path',
                    buttonText: 'Browse'
                }
            ],
            buttons: [{
                text: 'Add dataset',
                handler: function(){
                    if(datasetsPanel.getForm().isValid()){
                        datasetsPanel.getForm().submit({
                            url: 'dataset/upload',
                            waitMsg: 'Uploading your datasets...',
                            success: function(datasetsPanel, o){
                                msg('Success', 'Processed file "' + o.result.file + '" on the server');
                            }
                        });
                    }
                }
            }]
        });
	this.datasetsPanel = datasetsPanel;
        itemsToAdd.push(this.datasetsPanel);

        var config = Ext.apply({
            defaults:{
                //padding: 5,
                autoScroll:true
            },
            stateful:false,
            flex:1,
            border:false,
            enableTabScroll:true,
            activeTab:0,
            items:itemsToAdd
        }, cfg);

        Portal.ui.MapMenuPanel.superclass.constructor.call(this, config);

        Ext.MsgBus.subscribe('selectedLayerChanged', function(subject,openLayer) {
            if (openLayer) {
                // so now parse tree and deactivate layers create from search
                var rootNode = this.defaultMenuTree.defaultNode();
                this.defaultMenuTree.toggleLayerNodes(openLayer.grailsLayerId,false,rootNode);
            }
        }, this);
        Ext.MsgBus.subscribe('removeLayer', function(subject, message) {
            this.removeLayer(message);
        }, this);
        Ext.MsgBus.subscribe('removeAllLayers', function(subject, message) {
            this.resetTree();
        }, this);
        Ext.MsgBus.subscribe('reset', function(subject, message) {
            this.resetTree(true);
        }, this);

        this.addEvents('addlayerclicked');

        this.relayEvents(this.defaultMenuTree, ['click', 'serverloaded']);
        this.registerMonitoringEvents();
    },

    toggleNodeBranch:function (enable, node) {
        this.defaultMenuTree.toggleNodeBranch(enable, node);
    },

    toggleLayerNodes:function (id, enable, node) {
        this.defaultMenuTree.toggleLayerNodes(id, enable, node);
    },

    resetTree:function (collapse) {
        this.defaultMenuTree.resetTree(collapse);
    },

    registerMonitoringEvents: function() {
        this.mon(this, 'click', this.onMenuNodeClick, this);
    },

    onMenuNodeClick: function(node) {
        if (node.attributes.grailsLayerId) {
            this.fireEvent('addlayerclicked');

            Ext.MsgBus.publish('addLayerUsingServerId', { id: node.attributes.grailsLayerId});
        }
    },

    removeLayer: function(openLayer) {
        this.toggleLayerNodes(openLayer.grailsLayerId, true);
    }

});

Ext.reg('portal.ui.mapmenupanel', Portal.ui.MapMenuPanel);
