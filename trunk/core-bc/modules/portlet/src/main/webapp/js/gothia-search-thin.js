AUI().add('gothia-search-thin',function(A) {
    var Lang = A.Lang,
        isArray = Lang.isArray,
        isFunction = Lang.isFunction,
        isNull = Lang.isNull,
        isObject = Lang.isObject,
        isString = Lang.isString,
        isUndefined = Lang.isUndefined,
        getClassName = A.ClassNameManager.getClassName,
        concat = function() {
            return Array.prototype.slice.call(arguments).join(SPACE);
        },
        
        NAME = 'gothia-search-thin',
        NS = 'gothia-search-thin',
        
        AUTOCOMPLETE_INPUT = 'autoCompleteInput',
        AUTOCOMPLETE_URL = 'autoCompleteUrl',
        AUTOCOMPLETE_WRAP = 'autoCompleteWrap',
        
        CSS_HIDDEN = 'aui-helper-hidden'
;
        
    var GothiaSearchThin = A.Component.create(
            {
                ATTRS: {
                	
                	autoCompleteInput: {
                		setter: A.one
                	},
                	
                	autoCompleteUrl: {
                		value: ''
                	},
                	
                	autoCompleteWrap: {
                		setter: A.one
                	}
                	
                },
                EXTENDS: A.Component,
                NAME: NAME,
                NS: NS,
                
                prototype: {
                    
                    initializer: function(config) {
                        var instance = this;
                    },
                    
                    renderUI: function() {
                        var instance = this;

                        // Autocomplete is deactivated due to an issue in ie7
                        // When autocomplete is turned on, ie7 will only display
                        // the page if javascript fast load is turned off
                        //instance._initAutoComplete();
                    },
    
                    bindUI: function() {
                        var instance = this;
                    },
                    
                    _initAutoComplete: function() {
                    	var instance = this;
                    	
                        var dataSource = new A.DataSource.IO({
                        	source: instance.get(AUTOCOMPLETE_URL)
                    	});
                        
                        dataSource.plug(A.Plugin.DataSourceJSONSchema, {
                            schema: {
                                resultListLocator: 'results',
                                resultFields: ['name', 'key']
                            }
                        });
                        
                        var searchAutoComplete = new A.AutoComplete({
                            button: false,
                            contentBox: instance.get(AUTOCOMPLETE_WRAP),
                            dataSource: dataSource,
                            delimChar: false,
                            forceSelection: false,
                            input: instance.get(AUTOCOMPLETE_INPUT),
                            matchKey: 'name',
                            minQueryLength: 2,
                            queryDelay: 0.2,
                            typeAhead: true
                         });

                         searchAutoComplete.render();
                    },
                    
                    
                    _someFunction: function() {
                        var instance = this;
                    }

                }
            }
    );

    A.GothiaSearchThin = GothiaSearchThin;
        
    },1, {
        requires: [
           'aui-autocomplete',
           'aui-base',
           'aui-io',
           'json'
      ]
    }
);
