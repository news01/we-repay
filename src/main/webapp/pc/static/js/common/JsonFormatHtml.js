/**
 * JSON格式html模板数据格式化为HTML
 */
(function(window,iusp){
	
	/**
	 * 构造HTML
	 * @container_obj html生成到容器中
	 * @jsonData json格式模板html数据
	 */
	var buildHTML = function(container_obj,json_data){
		if (json_data) {
			//得到内容元素数组
			var element_array = json_data.element_array;
			
			//创建内容form
			var modal_div = createElement({
				type : "form",
				Parentclass : container_obj,
				classname : "health_module"
			});
			//设置表单基本数据内容
			modal_div.setAttribute('class_name',json_data['className']);
			modal_div.setAttribute('search_key',json_data['searchKey']);
			modal_div.setAttribute('type',json_data['type']);
			modal_div.setAttribute('element_length',element_array.length);
			modal_div.setAttribute('onsubmit',"return false");

			//创建内容标题
			var modal_header = createElement({
				type : "div",
				Parentclass : modal_div,
				classname : "health_module_title health_inspect_"+json_data['searchKey']
			});
			modal_header.innerHTML = json_data['type'];

		/*	//创建内容标题隐藏域存储名称
			var modal_header_input = createElement2({
				type : "input",
				Parentclass : modal_header,
				property_obj : [ {
					name : "type",
					value : "hidden"
				}, {
					name : "name",
					value: "label"
				}, {
					name : "value",
					value: json_data['type']
				}]
			});
			*/

			//创建内容待输入容器
			var modal_body = createElement({
				type : "div",
				Parentclass : modal_div,
				classname : "modal_body"
			});

			//创建内容具体指标项
			for (var i = 0; i < element_array.length; i++) {
				var index_element = element_array[i];
				//创建指标项录入框input select 等
				var classAttrName = index_element['classAttrName'];
				//创建指标项容器
				var row_div = createElement({
					type : "div",
					Parentclass : modal_body,
					classname : "health_module_li"
				});
				//创建指标项标题
				var left_div = createElement({
					type : "div",
					Parentclass : row_div,
					classname : "module_li_left"
				});
				var classAttrDesc = index_element["classAttrDesc"];
				left_div.innerHTML = classAttrDesc;
				left_div.setAttribute('class_attr_name',classAttrName);
				left_div.setAttribute('class_attr_name_desc',classAttrDesc);
				
				
				//创建指标项真实录入信息容器
				var right_div = createElement({
					type : "div",
					Parentclass : row_div,
					classname : "module_li_input"
				});
				
				
				sub_obj_create(right_div, index_element['sub_obj'],classAttrName);
				
			}
		}
	}
	
	
	/**
	 * 创建对象obj 创建类型（如div） 
	 * @obj 元素对象
	 * @obj.Parentclass   父类，由谁创建
	 * @obj.property_obj  所有属性
	 * @return 当前创建元素
	 */
	var createElement2 = function(obj){
		var element=document.createElement(obj['type']);
		if(obj.Parentclass){
			obj.Parentclass.appendChild(element);
		}
		$.each(obj.property_obj,function(i,item){
			element.setAttribute(item.name,item.value);
		});
		
		return element;
	}
	
	
	/**
	 * 创建对象obj 创建类型（如div） 
	 * @obj 元素对象
	 * @obj.classname  class 属性
	 * @obj.id     id属性
	 * @obj.style  设置css样式
	 * @obj.Parentclass   父类，由谁创建
	 * @return 当前创建元素
	 */
	var createElement = function(obj){
		var element=document.createElement(obj['type']);
		if(obj.classname){
			element['className']=obj['classname'];
		}
		if(obj.id){
			element['id']=obj['id'];
		}
		if(obj.style){
			element['style'].cssText=obj['style'];
		}
		if(obj.Parentclass){
			obj.Parentclass.appendChild(element);
		}
		return element;
	}
	
	/**
	 * 创建一组指标项 如：多个单选框行成一组表示一个指标项数据
	 * @Parent_obj 父元素对象
	 * @sub_obj_array 一组指标项选择
	 */
	function sub_obj_create(Parent_obj,sub_obj_array,classAttrName){

		for (var k = 0; k < sub_obj_array.length; k++) {
			var cssText = "";
			var sub_obj = sub_obj_array[k];
			if (sub_obj['cssText']) {
				cssText = sub_obj['cssText'];
			}
			
			var element_obj = createElement({
				type : sub_obj['type'],
				style : cssText,
				Parentclass : Parent_obj
			});
			//给输入框赋值名称待获取值的名称
			if(classAttrName && classAttrName!=null && sub_obj['type']!="span" && sub_obj['type']!="div"){ 
				element_obj.setAttribute('name',classAttrName);
			}
			
			var property_list = sub_obj['property_obj']
			if (property_list) {
				for (var j = 0; j < property_list.length; j++) {
					var property_obj = property_list[j];
					element_obj[property_obj['name']] = property_obj['value'];
				}
			}
			if (sub_obj['sub_obj']) {
				sub_obj_create(element_obj, sub_obj['sub_obj'],null);
			}
		}
	}

	/**
	 * @container_obj 生成对象存储容器
	 * @data 对象json格式模板html字符串数据
	 */
	iusp.JsonFormatHtml = function(container_obj,data){
		var jsonData = {};
		try{
			jsonData = JSON.parse(data);
		}catch(error){
		};
		buildHTML(container_obj,jsonData);
	};
	
	
})(window,(function() {
	if (!window.IUSP){
		return false;
	}
	return window.IUSP;
})());