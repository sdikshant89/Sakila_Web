Ext.application({
	name: 'Fiddle',
    launch: function () {
    	
        Ext.define('spefeat_model', {
            extend: 'Ext.data.Model',
            fields: ['spefeat']
        });
        
        var special_fet = Ext.create('Ext.data.Store', {
        	model: 'spefeat_model',
    	    autoLoad:true,
    	    proxy: {
    	                type: 'ajax',
    	                url : 'http://localhost:8080/SlimFastDemo/spefeat.do',
    	                reader:{
    	                    type:'json'
    	                }
    	            }
        });
    	
    	var lang_store = Ext.create('Ext.data.Store', {
    		fields: ['abbr', 'name'],
            data: [{
                "name": "English",
                "abbr":1
            }, {
                "name": "Italian",
                "abbr":2
            }, {
                "name": "Japanese",
                "abbr":3
            }, {
                "name": "Mandarin",
                "abbr":4
            }, {
                "name": "French",
                "abbr":5
            }, {
                "name": "German",
                "abbr":6
            }, {
                "name": "Mongolian",
                "abbr":7
            }]
        });

        Ext.define('rating_model', {
            extend: 'Ext.data.Model',
            fields: ['rating']
        });
       
        var ratings = Ext.create('Ext.data.Store', {
        	model: 'rating_model',
    	    autoLoad:true,
    	    proxy: {
    	                type: 'ajax',
    	                url : 'http://localhost:8080/SlimFastDemo/rating.do',
    	                reader:{
    	                    type:'json'
    	                }
    	            }
        });


    	var rowsPerPage = 10;
    	
    	var data_store = Ext.create('Ext.data.Store', {
        	fields: [ 'film_id', 'isdel', 'title', 'desc', 'relyear', 'lang', 'director', 'rating', 'spefeat', 'langname'],
        	pageSize:10,
            proxy: {
                type: 'ajax',
                url : 'http://localhost:8080/Strutsmp/show_data',
                reader:{
                    type:'json',
                    rootProperty: 'jsonData.pojo',
                    totalProperty: 'jsonData.total_count'
                }
            }
        });
        data_store.load({
            params:{
                start:0,    
                limit: 10
            }
        });
    	
    	Ext.create('Ext.panel.Panel', {
            renderTo: Ext.getBody(),
            width: 1055,
            height: 270,
            title: 'Movie Advance Search',
            bodyPadding: 50,
            layout: 'column',
            buttonAlign: 'center', //if not buttons would shift to right extreme
            items:[
                {
                    xtype: 'panel',
                    columnWidth: 0.5,
                    border: false,  //if not, outline(border) would appear in the output
                    layout: {
    			        type: 'vbox',
    			        align: 'middle'
    			    },
                    items: [
                        {
                            xtype: 'textfield',
                            fieldLabel: 'Movie Name:',
                            id: 'title_search'
                        }, 
                        {
                            xtype: 'numberfield',
                            fieldLabel: 'Release Year',
                            id: 'relyear_search',
                            maxValue: 2090,
                            minValue: 1960,
                            anchor: '100%'
                            
                        } 
                    ]
                }, 
                {
                    xtype: 'panel',
                    columnWidth: 0.5,
                    border: false,  //if not, outline(border) would appear in the output
                    layout: {
    			        type: 'vbox',
    			        align: 'middle'
    			    },
                    items: [
                        {
                            xtype: 'textfield',
                            fieldLabel: 'Director Name:',
                            id: 'dir_search'
                        }, 
                        {
                            xtype: 'combobox',
                            fieldLabel: 'Language:',
                            id:'lang_search',
                            store: lang_store,
                            queryMode: 'local',
                            displayField: 'name',
                            valueField: 'abbr',
                        }
                    ]
                }
            ],
            buttons: [
                {
                    text: 'Search',
                    listeners: {
                        click: function () {
	                        var name = Ext.getCmp('title_search').getValue();
                            var dir = Ext.getCmp('dir_search').getValue();
                            var lang = Ext.getCmp('lang_search').getValue();
                            var year = Ext.getCmp('relyear_search').getValue();
                            
                            if(year==null){year=0}
                            if(lang==null){lang=0}
                    
                        	var link = "http://localhost:8080/Strutsmp/show_data?"+"title="+name+"&relyear="+year+"&director="+dir+"&lang="+lang;                     
                            data_store.getProxy().url = link;                     
                            data_store.loadPage(1);
                        }
                    }
                }, 
                {
                    text: 'Reset',
                    listeners: {
                        click: function () {
                            Ext.getCmp('title_search').setValue("");
                            Ext.getCmp('relyear_search').setValue("");
                            Ext.getCmp('lang_search').setValue("");
                            Ext.getCmp('dir_search').setValue("");
                            var link = "http://localhost:8080/Strutsmp/show_data";                     
                            data_store.getProxy().url = link;                     
                            data_store.loadPage(1);
                        }
                    }
                }
            ]
        });
    	

        function add_reset_values(){
        	Ext.getCmp('title_add').setValue("");
            Ext.getCmp('relyear_add').setValue("");
            Ext.getCmp('sf_add').setValue("");
            Ext.getCmp('rt_add').setValue("");
            Ext.getCmp('lang_add').setValue("");
            Ext.getCmp('dir_add').setValue("");
            Ext.getCmp('des_add').setValue("");
        }
        
        function add_disenable(){
        	var title = Ext.getCmp('title_add').getValue();
    		var relyear = Ext.getCmp('relyear_add').getValue();
    		var spefeat = Ext.getCmp('sf_add').getValue();
    		var rating = Ext.getCmp('rt_add').getValue();
    		var lang = Ext.getCmp('lang_add').getValue();
    		var desc = Ext.getCmp('des_add').getValue();
    		
    		if(title===""||relyear===null||spefeat===null||rating===null||lang===null||desc===""){
    			Ext.getCmp('add_button').setDisabled(true);
    		}else
    			Ext.getCmp('add_button').setDisabled(false);
        }
        
        var add_window = Ext.create('Ext.window.Window',{
        	id: 'add_window',
            title: 'Add Film',
            height: 450,
            width: 490,
            floating: true,
            buttonAlign: 'center',
            closeAction:'close',
            bodyPadding: 10,
            //required fields are title, language and director
            items:[
                {
                    xtype: 'textfield',
                    fieldLabel: 'Title',
                    id: 'title_add',
                    width: 460,
                    allowBlank: false,
                    onChange: add_disenable
                },{
                    xtype: 'numberfield',
                    anchor: '100%',
                    // how much space is required for the options to display
                    id: 'relyear_add',
                    fieldLabel: 'Release Year',
                    allowBlank: false,
                    maxValue: 2090,
                    width: 460,
                    minValue: 1960,
                    onChange: add_disenable
                },{
                    xtype: 'combobox',
                    width: 460,
                    fieldLabel: 'Special Features:',
                    id: 'sf_add',
                    store: special_fet,
                    allowBlank: false,
                    multiSelect: true,
                    queryMode: 'local',
                    displayField: 'spefeat',
                    valueField: 'spefeat',
                    onChange: add_disenable
                },{
                    xtype: 'combobox',
                    width: 460,
                    fieldLabel: 'Rating:',
                    id:'rt_add',
                    store: ratings,
                    queryMode: 'local',
                    displayField: 'rating',
                    valueField: 'rating',
                    forceSelection:true,
                    onChange: add_disenable
                },{
                    xtype: 'combobox',
                    width: 460,
                    fieldLabel: 'Language:',
                    store: lang_store,
                    id:'lang_add',
                    queryMode: 'local',
                    displayField: 'name',
                    valueField: 'abbr',
                    forceSelection:true,
                    allowBlank: false,
                    onChange: add_disenable
                },{
                    xtype: 'textfield',
                    fieldLabel: 'Director',
                    id: 'dir_add',
                    width: 460
                },{
                    xtype: 'textarea',
                    fieldLabel: 'Description:',
                    id: 'des_add',
                    width: 460,
                    height: 90,
                    allowBlank: false,
                    onChange: add_disenable
                }],
                buttons: [{
                id: 'add_button',
                text: 'Add',
                disabled:true,
                listeners:{
                	click: function(){
                		var tit_add = Ext.getCmp('title_add').getValue();
                		var rely_add = Ext.getCmp('relyear_add').getValue();
                		var spefee_add = Ext.getCmp('sf_add').getValue().join(',');
                		var rat_add = Ext.getCmp('rt_add').getValue();
                		var bhasha_add = Ext.getCmp('lang_add').getValue();
                		var dire_add = Ext.getCmp('dir_add').getValue();
                		var destion_add = Ext.getCmp('des_add').getValue();
                		
                		Ext.Ajax.request({
                		     url: 'http://localhost:8080/Strutsmp/add_data',
                		     method: 'GET',
                		     headers: { 'Content-Type': 'application/json'},
                		     jsonData: true,
                		     params : {
                		    	 title:tit_add,
                              	 relyear:rely_add,
                              	 spefeat:spefee_add,
                              	 rating:rat_add,
                              	 lang:bhasha_add,
                              	 director:dire_add,
                              	 desc:destion_add
                		     },
                             success: function (){
	                             Ext.toast({
                  		             html: 'The movie '+tit_add+' is added to the database',
                  		             title: 'Information Added',
                  		             align: 't'
                  		         });
                		         add_reset_values();
                		         add_window.hide();
                		         Ext.getCmp('edit_button').setDisabled(true);
                		         Ext.getCmp('delete_button').setDisabled(true);
                                 data_store.reload();
                             }
                		});
                		//Ext.Msg.alert('Information Updated','The movie '+tit_add+' is added to the database');
                	}
                }
            }, {
                text: 'Reset',
                listeners: {
                    click: function () {
                    	add_reset_values();
                    }
                }
            }]
        })


    	var my_grid = Ext.create('Ext.grid.Panel', {
            id: 'my_grid',
        	title: 'Movie Grid',
            store: data_store,
            height: 300, 
            //before was exact 245 but the scroll bar appeared and the last row wasn't visible
            width: 1055,
            renderTo: Ext.getBody(),
            selModel: {
                allowDeselect : true,
                injectCheckbox: 'first',
                mode: 'MULTI',
                listeners: {
                    selectionchange: function(){
                        var len = my_grid.getSelectionModel().getSelection().length;
                        if(len != 1) Ext.getCmp('edit_button').setDisabled(true);
                        else Ext.getCmp('edit_button').setDisabled(false);
                        if(len < 1) Ext.getCmp('delete_button').setDisabled(true);
                        else Ext.getCmp('delete_button').setDisabled(false);
                    }
                }
            },
            
            selType: 'checkboxmodel',
            
            columns: [
            	{ text: 'ID', dataIndex: 'film_id', width: 50, hidden:true },
                { text: 'Delete', dataIndex: 'isdel', width: 50, hidden:true },
                { text: 'Lang ID', dataIndex: 'lang', width: 50, hidden:true },
                { text: 'Title', dataIndex: 'title', width: 130 },
                { text: 'Description', dataIndex: 'desc', width: 350 },
                { text: 'Release Year', dataIndex: 'relyear', width: 100 },
                { text: 'Language', dataIndex: 'langname', width: 100 },
                { text: 'Director', dataIndex: 'director', width: 100 },
                { text: 'Rating', dataIndex: 'rating', width: 100 },
                { text: 'Special Features', dataIndex: 'spefeat', width: 120 }
            ],
            // could've used bbar but it does not have any option for store
            
            // ********** Defining Dock **********
            dockedItems: [{
                xtype: 'pagingtoolbar',
                store: data_store,
                dock: 'top',
                displayInfo: true,
                items: [
                	{
                        xtype: 'button',
                        iconCls: 'fa fa-exchange'
                    },{
                        xtype: 'button',
                        text: 'Add',
                        iconCls:'fa fa-plus-circle',
                        handler:function(){
                            add_window.show();
                        }
                    },'-', {
                        xtype: 'button',
                        id: 'edit_button',
                        text: 'Edit',
                        disabled:true,
                        iconCls:'fa fa-pencil-square-o',
                        listeners:{
                        	click: function(){
                        		console.log(my_grid.getSelectionModel().getSelection()[0].data);
                        		
                        		var tit_edit = my_grid.getSelectionModel().getSelection()[0].data.title;
                        		var rely_edit = my_grid.getSelectionModel().getSelection()[0].data.relyear;
                        		var spefee_edit = my_grid.getSelectionModel().getSelection()[0].data.spefeat.split(',');
                        		var rat_edit = my_grid.getSelectionModel().getSelection()[0].data.rating;
                        		var bhasha_edit = my_grid.getSelectionModel().getSelection()[0].data.lang;
                        		var dire_edit = my_grid.getSelectionModel().getSelection()[0].data.director;
                        		var destion_edit = my_grid.getSelectionModel().getSelection()[0].data.desc;
                        		
                        		Ext.getCmp('title_edit').setValue(tit_edit);
                        		Ext.getCmp('relyear_edit').setValue(rely_edit);
                        		Ext.getCmp('sf_edit').setValue(spefee_edit);
                        		Ext.getCmp('rt_edit').setValue(rat_edit);
                        		Ext.getCmp('lang_edit').setValue(bhasha_edit);
                        		Ext.getCmp('dir_edit').setValue(dire_edit);
                        		Ext.getCmp('des_edit').setValue(destion_edit);
                        		edit_window.show();
                        	}
                        }
                    },'-',{
                        xtype: 'button',
                        text: 'Delete',
                        id:'delete_button',
                        disabled:true,
                        iconCls: 'fa fa-eraser',
                        listeners:{
                        	click: function(){
                        		var id_set = [];
                        		selec_rw = my_grid.getSelectionModel().getSelection();
                        		selec_rw.forEach(function (element)
                        			{
                        			    id_set.push(element['data'].film_id);
                        		    }
                        		);
                        		var total_rows_store = data_store.getTotalCount();
                        		
                        		var cnt = data_store.data.length;
                        		
                        		console.log(total_rows_store);
                        		console.log(cnt);
                                console.log(id_set.length);
                        		Ext.Ajax.request({
                        			url:'http://localhost:8080/Strutsmp/del_data',
                        			jsonData: true,
                        			headers: { 'Content-Type': 'application/json'},
                        			params : {
                        				id_s:id_set.join(',')
                        			}
                        		});
                        		
                        		Ext.toast({
                       		     html: 'The selected record(s) are deleted from the database',
                       		     title: 'Record Deleted',
                       		     align: 't'
                       		 });
                        		
                        		if((total_rows_store - id_set.length)%rowsPerPage==0){
                        			data_store.load({
                        				params:{
                        					page: (total_rows_store - id_set.length)/ 10,
                        					limit: 10
                        				}
                        			});
                        		}
                        		else{
                        			data_store.reload();
                        		}
                        		
                        		Ext.getCmp('delete_button').setDisabled(true);
                        		Ext.getCmp('edit_button').setDisabled(true);
                        	}
                        }
                    },'-']
            }]
        });

    function add_disenable_edit(){
        	var title = Ext.getCmp('title_edit').getValue();
    		var relyear = Ext.getCmp('relyear_edit').getValue();
    		var spefeat = Ext.getCmp('sf_edit').getValue();
    		var rating = Ext.getCmp('rt_edit').getValue();
    		var lang = Ext.getCmp('lang_edit').getValue();
    		var director = Ext.getCmp('dir_edit').getValue();
    		var desc = Ext.getCmp('des_edit').getValue();
    		
    		if(title===""||relyear===null||spefeat===null||rating===null||lang===null||desc===""){
    			Ext.getCmp('add_button_edit').setDisabled(true);
    		}else
    			Ext.getCmp('add_button_edit').setDisabled(false);
        }
        
        var edit_window = Ext.create('Ext.window.Window',{
        	id: 'edit_window',
            title: 'Edit Film',
            height: 450,
            width: 490,
            floating: true,
            buttonAlign: 'center',
            closeAction:'close',
            bodyPadding: 10,
            items:[
                {
                    xtype: 'textfield',
                    fieldLabel: 'Title',
                    id: 'title_edit',
                    width: 460,
                    allowBlank: false,
                    onChange: add_disenable_edit
                },{
                    xtype: 'numberfield',
                    anchor: '100%',
                    id: 'relyear_edit',
                    fieldLabel: 'Release Year',
                    allowBlank: false,
                    maxValue: 2090,
                    width: 460,
                    minValue: 1960,
                    onChange: add_disenable_edit
                },{
                    xtype: 'combobox',
                    width: 460,
                    fieldLabel: 'Special Features:',
                    id: 'sf_edit',
                    store: special_fet,
                    allowBlank: false,
                    multiSelect: true,
                    queryMode: 'local',
                    displayField: 'spefeat',
                    valueField: 'spefeat',
                    onChange: add_disenable_edit
                },{
                    xtype: 'combobox',
                    width: 460,
                    fieldLabel: 'Rating:',
                    id:'rt_edit',
                    store: ratings,
                    queryMode: 'local',
                    displayField: 'rating',
                    valueField: 'rating',
                    forceSelection:true,
                    onChange: add_disenable_edit
                },{
                    xtype: 'combobox',
                    width: 460,
                    fieldLabel: 'Language:',
                    store: lang_store,
                    id:'lang_edit',
                    queryMode: 'local',
                    displayField: 'name',
                    valueField: 'abbr',
                    allowBlank: false,
                    forceSelection:true,
                    onChange: add_disenable_edit
                },{
                    xtype: 'textfield',
                    fieldLabel: 'Director',
                    id: 'dir_edit',
                    width: 460
                },{
                    xtype: 'textarea',
                    fieldLabel: 'Description:',
                    id: 'des_edit',
                    width: 460,
                    height: 90,
                    allowBlank: false,
                    onChange: add_disenable_edit
                }],
                buttons: [{
                id: 'add_button_edit',
                text: 'Edit',
                disabled:true,
                listeners:{
                	click: function(){
                		var tit_e = Ext.getCmp('title_edit').getValue();
                		var rely_e = Ext.getCmp('relyear_edit').getValue();
                		var spefee_e = Ext.getCmp('sf_edit').getValue().join(',');
                		var rat_e = Ext.getCmp('rt_edit').getValue();
                		var bhasha_e = Ext.getCmp('lang_edit').getValue();
                		var dire_e = Ext.getCmp('dir_edit').getValue();
                		var destion_e = Ext.getCmp('des_edit').getValue();
                		var film_id_edit = my_grid.getSelectionModel().getSelection()[0].data.film_id;
                		
                		Ext.Ajax.request({
                		     url: 'http://localhost:8080/Strutsmp/edit_data',
                		     method: 'GET',
                		     headers: { 'Content-Type': 'application/json'},
                		     jsonData: true,
                		     params : {
                		    	 title:tit_e,
                              	 relyear:rely_e,
                              	 spefeat:spefee_e,
                              	 rating:rat_e,
                              	 lang:bhasha_e,
                              	 director:dire_e,
                              	 desc:destion_e,
                              	 id_s: film_id_edit
                		     }
                		});
                		//Ext.Msg.alert('Information Updated','The details of '+tit_e+' movie is updated in the database');
                		Ext.toast({
                		     html: 'The details of '+tit_e+' movie is updated in the database',
                		     title: 'Information Updated',
                		     align: 't'
                		 });
                		console.log(tit_e);
                		console.log(rely_e);
                		console.log(spefee_e);
                		console.log(rat_e);
                		console.log(bhasha_e);
                		console.log(dire_e);
                		console.log(destion_e);
                		console.log(film_id_edit);
                		data_store.reload();
                		edit_window.hide();
                		Ext.getCmp('edit_button').setDisabled(true);
                		Ext.getCmp('delete_button').setDisabled(true);
                	}
                }
            }, {
                text: 'Reset',
                listeners: {
                    click: function () {
                        Ext.getCmp('title_edit').setValue("");
                        Ext.getCmp('relyear_edit').setValue("");
                        Ext.getCmp('sf_edit').setValue("");
                        Ext.getCmp('rt_edit').setValue("");
                        Ext.getCmp('lang_edit').setValue("");
                        Ext.getCmp('dir_edit').setValue("");
                        Ext.getCmp('des_edit').setValue("");
                    }
                }
            }]
        })

    }
})