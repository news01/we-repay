/**
 * 模块下index页面
 */
(function(){
	
	function bamboo(){};
	
	/**
	 * 执行入口
	 */
    bamboo.start = function(){
    	
    	//初始化框架
    	bamboo.initLayout();
    	
    	//初始化页面信息
    	$("#details_menu li").click(function(){
    		
    		$("#details_menu li").removeClass("active");
    		$(this).addClass("active");
    		
    		$("#Subpage").attr("src",$(this).attr("data-url")); 
    	});
    }
    
    /**
     * 初始化框架结构
     */
    bamboo.initLayout = function(){
    	
    	var win = IUSP.getInnerSize();
    	
    	//$("#container_center").width(win.width - $("#module_index_menu").width()-32)
    }
	/**
	 * 赋值给BAMBOO
	 */
	window.BAMBOO = window.BAMBOO ? $.extend(window.BAMBOO ,bamboo):bamboo
})();