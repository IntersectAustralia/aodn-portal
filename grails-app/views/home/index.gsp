<%--

 Copyright 2012 IMOS

 The AODN/IMOS Portal is distributed under the terms of the GNU General Public License

--%>

<html>
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="Content-type" content="text/html;charset=UTF-8">
    <meta http-equiv="content-script-type" content="text/javascript"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    ${buildInfo}

    <!--link rel="stylesheet" media="print" type="text/css"  href="${resource(dir: 'css', file: 'mapprint.css')}" /-->
    <link rel="stylesheet" type="text/css"
          href="${resource(dir: 'js', file:'GeoExt1.1/resources/css/geoext-all.css')}"/>
    <!-- User extensions -->
    <link rel="stylesheet" type="text/css"
          href="${resource(dir: 'js', file: 'ext-ux/SuperBoxSelect/superboxselect.css')}"/>
    <link rel="stylesheet" type="text/css" href="${resource(dir: 'js', file: 'ext-ux/Hyperlink/hyperlink.css')}"/>
    <!-- Portal classes-->
    <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'portal-search.css')}"/>

<!-- common styles and JavaScript for the map page and Grails admin pages -->
    <g:render template="/common_includes"></g:render>

    <g:if env="development">
        <script src="${resource(dir: 'js', file: 'ext-3.3.1/adapter/ext/ext-base-debug.js')}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'ext-3.3.1/ext-all-debug.js')}" type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'OpenLayers-2.10/OpenLayers.js')}" type="text/javascript"></script>
        <!--- GeoExt (Has to be after Openlayers and ExJS) -->
        <script src="${resource(dir: 'js', file: 'GeoExt1.1/lib/GeoExt.js')}" type="text/javascript"></script>
    </g:if>
    <g:else>
        <script src="${resource(dir: 'js', file: 'ext-3.3.1/adapter/ext/ext-base.js')}" type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'ext-3.3.1/ext-all.js')}" type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'OpenLayers-2.10/OpenLayers.js')}" type="text/javascript"></script>
        <!--- GeoExt (Has to be after Openlayers and ExJS) -->
        <script src="${resource(dir: 'js', file: 'GeoExt1.1/script/GeoExt.js')}" type="text/javascript"></script>
    </g:else>

<!-- GeoNetwork - required classes only -->
    <script src="${resource(dir: 'js', file: 'Geonetwork/lib/OpenLayers/addins/Format/GeoNetworkRecords.js')}"
            type="text/javascript"></script>
    <script src="${resource(dir: 'js', file: 'Geonetwork/lib/GeoNetwork/Util.js')}" type="text/javascript"></script>
    <script src="${resource(dir: 'js', file: 'Geonetwork/lib/GeoNetwork/lang/en.js')}" type="text/javascript"></script>
    <script src="${resource(dir: 'js', file: 'Geonetwork/lib/GeoNetwork/Catalogue.js')}"
            type="text/javascript"></script>
    <script src="${resource(dir: 'js', file: 'Geonetwork/lib/GeoNetwork/util/SearchTools.js')}"
            type="text/javascript"></script>
    <script src="${resource(dir: 'js', file: 'Geonetwork/lib/GeoNetwork/data/OpenSearchSuggestionReader.js')}"
            type="text/javascript"></script>
    <script src="${resource(dir: 'js', file: 'Geonetwork/lib/GeoNetwork/data/OpenSearchSuggestionStore.js')}"
            type="text/javascript"></script>
    <script src="${resource(dir: 'js', file: 'Geonetwork/lib/GeoNetwork/map/ExtentMap.js')}"
            type="text/javascript"></script>

    <script src="${resource(dir: 'js', file: 'ext-ux/SuperBoxSelect/SuperBoxSelect.js')}"
            type="text/javascript"></script>
    <script src="${resource(dir: 'js', file: 'ext-ux/Hyperlink/Hyperlink.js')}" type="text/javascript"></script>
    <script src="${resource(dir: 'js', file: 'ext-ux/util/MessageBus.js')}" type="text/javascript"></script>

    <g:if env="development">
        <script src="${resource(dir: 'js', file: 'portal/prototypes/Array.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/prototypes/OpenLayers.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/lang/en.js')}?${jsVerNum}" type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/common/LayerDescriptor.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/common/spin.min.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/common/BrowserWindow.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/common/ActionColumn.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/common/LoadMask.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/common/AppConfigStore.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/common/SaveDialog.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/common/MapPanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/common/Controller.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/data/LayerStore.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/data/MenuTreeLoader.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/data/SuggestionStore.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/data/CatalogResult.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/data/CatalogResultsStore.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/data/LinkStore.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/data/FacetStore.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/field/FreeText.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/field/DateRange.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/field/FacetedDateRange.js')}?${jsVerNum}"
            type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/field/BoundingBox.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/field/MultiSelectCombo.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/field/CheckBox.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/field/ValueCheckBox.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/field/MapLayersCheckBox.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/filter/FilterComboBox.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/filter/FiltersPanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/filter/FilterStore.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/FilterSelector.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/NewSearchLink.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/SaveSearchLink.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/MiniMapPanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/SavedSearchComboBox.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/SaveSearchDialog.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/SavedSearchPanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/SearchController.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/SearchForm.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/RightSearchTabPanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/SearchTabPanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/LinkSelectionWindow.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/LayerSelectionWindow.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/ResultsGrid.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/FacetedSearchResultsGrid.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/search/DateSelectionPanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/data/ServerNodeLayerDescriptorStore.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/data/MenuItemToNodeBuilder.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/snapshot/SnapshotProxy.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/snapshot/SnapshotController.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/snapshot/SaveSnapshotDialog.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/snapshot/SnapshotSaveButton.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/snapshot/SnapshotOptionsPanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/ui/ActiveLayersTreeNodeUI.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/ui/ActionsPanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/ui/ActiveLayersPanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/utils/TimeUtil.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/details/AnimationControlsPanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/filter/BaseFilter.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/filter/FilterCombo.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/filter/TimeFilter.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/filter/BoundingBoxFilter.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/filter/BooleanFilter.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/filter/FilterPanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/details/NCWMSColourScalePanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/details/StylePanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/details/DetailsPanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/details/AodaacPanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/details/DetailsPanelTab.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/details/InfoPanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/ui/openlayers/ClickControl.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/ui/openlayers/LayerOptions.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/ui/openlayers/LayerParams.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/ui/openlayers/MapActionsControl.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/ui/openlayers/MapOptions.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/ui/AnimationPanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/ui/RightDetailsPanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/ui/MapPanel.js')}?${jsVerNum}" type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/ui/HomePanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/ui/MapOptionsPanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/ui/MapMenuPanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/ui/PortalPanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/ui/LayerChooserPanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/ui/MainTabPanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/ui/UserDefinedWMSPanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/cart/DownloadCartConfirmationWindow.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/ui/FeatureInfoPopup.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/ui/Viewport.js')}?${jsVerNum}" type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/ui/SelectionPanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/ui/search/SearchPanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/ui/search/FreeTextSearchPanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/service/CatalogSearcher.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/ui/search/SearchFiltersPanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/data/TopTermStore.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/ui/TermSelectionPanel.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/data/ResultsStore.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/ui/EmptyDropZonePlaceholder.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/mainMap/map.js')}?${jsVerNum}" type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/mainMap/TransectControl.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/portal.js')}?${jsVerNum}" type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/common/GeoExt.ux.BaseLayerCombobox.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/common/LayerOpacitySliderFixed.js')}?${jsVerNum}"
                type="text/javascript"></script>
        <script src="${resource(dir: 'js', file: 'portal/ui/MenuPanel.js')}?${jsVerNum}"
                type="text/javascript"></script>

    <script src="${resource(dir: 'js', file: 'portal/cart/DownloadList.js')}?${jsVerNum}"
            type="text/javascript"></script>
    <script src="${resource(dir: 'js', file: 'portal/cart/DownloadCartStatus.js')}?${jsVerNum}"
            type="text/javascript"></script>
    <script src="${resource(dir: 'js', file: 'portal/cart/DownloadCartPanel.js')}?${jsVerNum}"
            type="text/javascript"></script>
    </g:if>
    <g:else>
        <script src="${resource(dir: 'js', file: 'portal-all.js')}?${jsVerNum}" type="text/javascript"></script>
    </g:else>

    <title>${configInstance?.name}</title>
</head>

<body>

<g:render template="/mainPortalHeader" model="['showLinks': true, 'configInstance': configInstance]"></g:render>

<%-- Display message from Grails app --%>
<g:if test="${flash.message}">
    <script type="text/javascript">
        Ext.Msg.alert("Message", "${flash.message.encodeAsHTML()}");
    </script>
</g:if>
</body>
</html>
