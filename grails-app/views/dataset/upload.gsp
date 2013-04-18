
<%--

 Copyright 2012 IMOS

 The AODN/IMOS Portal is distributed under the terms of the GNU General Public License

--%>

<html>
  <head>
    <meta name="layout" content="main" />      
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
    
    <script src="${resource(dir:'js',file:'ext-3.3.1/adapter/ext/ext-base.js')}"  type="text/javascript"></script>          
    <script src="${resource(dir:'js',file:'ext-3.3.1/ext-all-debug.js')}"   type="text/javascript"></script>
    <script src="${resource(dir:'js',file:'portal/data/LayerDataPanelStore.js')}"  type="text/javascript"></script>
    <script src="${resource(dir:'js',file:'portal/ui/LayerGridPanel.js')}"  type="text/javascript"></script>
    <script src="${resource(dir:'js',file:'portal/config/grid2treedrag.js')}" type="text/javascript"></script>
    <script src="${resource(dir:'js',file:'portal/config/treeSerializer.js')}"  type="text/javascript"></script>
    <script src="${resource(dir:'js',file:'portal/data/MenuItemToNodeBuilder.js')}"  type="text/javascript"></script>

  <g:set var="entityName" value="Menu" />
  <title><g:message code="default.create.label" args="[entityName]" /></title>
     <script>

      Ext.onReady(function() {
          initMenu(undefined, '${resource(dir:'/')}'); // grid2treedrag
      });

      </script>


</head>
<body>
${params}
</body>
</html>
