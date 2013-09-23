/** 
 * iFrame panel 
 * 
 * @author    Steffen Kamper 
 */ 

Ext.ns('Sparks'); 

Sparks.iframePanel = Ext.extend(Ext.Panel, { 
    name: 'iframe', 
    iframe: null, 
    src: Ext.isIE && Ext.isSecure ? Ext.SSL_SECURE_URL : 'about:blank', 
    maskMessage: 'loading ...', 
    doMask: true, 
     
        // component build 
    initComponent: function() { 
        this.bodyCfg = { 
            tag: 'iframe', 
            frameborder: '0', 
            src: this.src, 
            name: this.name 
        } 
        Ext.apply(this, { 
         
        }); 
        Sparks.iframePanel.superclass.initComponent.apply(this, arguments); 
         
        // apply the addListener patch for 'message:tagging' 
        this.addListener = this.on; 
         
    }, 
     
    onRender : function() { 
        Sparks.iframePanel.superclass.onRender.apply(this, arguments); 
        this.iframe = Ext.isIE ? this.body.dom.contentWindow : window.frames[this.name];
        this.body.dom[Ext.isIE ? 'onreadystatechange' : 'onload'] = this.loadHandler.createDelegate(this); 
    }, 
     
    loadHandler: function() { 
        this.src = this.body.dom.src; 
        this.removeMask(); 
    }, 
     getIframeBody: function() {
        var b = this.iframe.document.getElementsByTagName('body');
		if (!Ext.isEmpty(b)){
			return b[0];
		} else {
			return '';
		}
    },
    getIframe: function() { 
        return this.iframe; 
    }, 
    getUrl: function() { 
        return this.body.dom.src; 
    }, 
     
    setUrl: function(source) { 
        this.setMask(); 
        this.body.dom.src = source; 
    }, 
     
    resetUrl: function() { 
        this.setMask(); 
        this.body.dom.src = this.src; 
    }, 
     
    refresh: function() { 
        if (!this.isVisible()) { 
            return; 
        } 
        this.setMask(); 
        this.body.dom.src = this.body.dom.src; 
    }, 

    /** @private */ 
    setMask: function() { 
        if (this.doMask) { 
            this.el.mask(this.maskMessage); 
        } 
    }, 
    removeMask: function() { 
        if (this.doMask) { 
            this.el.unmask(); 
        } 
    } 
}); 
Ext.reg('iframePanel', Sparks.iframePanel);  