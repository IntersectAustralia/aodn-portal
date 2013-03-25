Ext.namespace('Portal.cart');

Portal.cart.DownloadList = Ext.extend(Ext.DataView, {




    constructor: function (cfg) {

        this.downloadItemsStore = new Ext.data.JsonStore({
            // store configs
            autoDestroy: true,
            url: 'downloadCart/getCartRecords',
            storeId: 'myStore',
            // reader configs
            root: 'records',
            idProperty: 'title',
            fields: ['title', 'uuid','downloads']
        });
        this.downloadItemsStore.load();

        var template = new Ext.XTemplate(
            '<tpl for=".">',
            '<div class="cart-row">',
            '<div class="cart-title">{title}</div>',
            '<div class="cart-files" >{[ this.getFiles(values) ]}</div>',
            '</div>',
            '</tpl>',
            {
                getFiles: function(values) {
                    var html = "";
                    Ext.each(values.downloads, function(f) {
                        html += subFilesTemplate.apply(f);
                    });
                    return html;
                }
            }
        );
        var subFilesTemplate = new Ext.XTemplate(
            '<div class="cart-file-row" >' +
                '<a href="{href}" target="_blank" title="{title}" >{title} ({type})</a></div>'
        );


        var config = Ext.apply({
            id: "downloadList",
            store: this.downloadItemsStore,
            //autoShow: true,
            tpl: template,
            autoScroll: true//,
            //overClass:'x-grid3-row-over',
            //minHeight: 600
        }, cfg);


        Portal.cart.DownloadList.superclass.constructor.call(this, config);


        Ext.MsgBus.subscribe("downloadCart.cartContentsUpdated", function() {
            this.downloadItemsStore.load();
            //console.log("UPDATED");
        }, this);

        console.log(this.downloadItemsStore);
    }
});
