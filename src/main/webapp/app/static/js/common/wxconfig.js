/**
 * 微信通过config接口注入权限验证配置
 */
(function (jsSHA,iuspwx) {
	//如果微信浏览器
	var ua = navigator.userAgent.toLowerCase();  
	if(ua.match(/MicroMessenger/i)=="micromessenger") { 
		var noncestr = new Date().getTime()+"";//生成签名的随机串
		var timestamp = new Date().getTime();//生成签名的时间戳
		var url = window.location.href;//当前网页的URL，不包含#及其后面部分
		//对所有待签名参数按照字段名的 ASCII 码从小到大排序（字典序）后，使用 URL 键值对的格式（即key1=value1&key2=value2…）拼接成字符串 ss
		var buildSignatureStr = decodeURIComponent("jsapi_ticket="+IUSP.jsapiTicket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url).split("#")[0];
		var jsSHA = new jsSHA(buildSignatureStr,"TEXT");
		//获取签名
		var signature = jsSHA.getHash("SHA-1","HEX");
		
		wx.config({
		    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		    appId: IUSP.appid, // 必填，公众号的唯一标识
		    timestamp: timestamp, // 必填，生成签名的时间戳
		    nonceStr: noncestr, // 必填，生成签名的随机串
		    signature: signature,// 必填，签名，见附录1
		    jsApiList: ['scanQRCode','chooseImage','uploadImage','getLocation'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
		});
		wx.ready(function(){
		    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
		});
		wx.error(function(res){
		    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
		});
	}
	
	/**
	 * 显示不支持提示
	 */
	iuspwx.showNotSupportTips = function(){
		var $element = $('body>[name="WxNotSupportTips"]');
		if($element.length>0){
			$element.show();
		}else{
			var html = []; 
			html.push('<div name="WxNotSupportTips"  style="display:block;position: fixed; height: 100%;max-width: 16rem; width: 100%;top: 0px;left:0px; color: #333; z-index: 100;">');
			html.push('<div  onclick="window.IUSPWX.hideNotSupportTips()" style="background-color: black;width: 100%; height: 100%;filter: alpha(opacity=50); -moz-opacity: 0.5;opacity: 0.5;">');
			html.push('</div>');
			html.push('<div style="border-radius: 0.10417rem; position: absolute;top: 50%; left: 50%; height: 8.125rem;width: 11.6666rem;margin-left: -5.8333rem;margin-top: -5.3125rem; text-align: center;">');
			html.push('<div class="row-fluid" style="width:100%;height:8.125rem;padding:0px 1rem;border-radius:0.125rem;background-color: white;">');
			html.push('<div class="row-fluid">');
			html.push('<div class="row-fluid " style="height:2.9166rem;line-height:2.9166rem;font-size: 0.75rem;color:#10C55B;" >');
			html.push('<i class="reminder_icon_prompt"></i> 温馨提示');
			html.push('</div>');
			html.push('<div class="row-fluid " style="font-size: 0.7rem; height: 1.68rem; color:#333333;" >');
			html.push('只支持“微信客户端”调用');
			html.push('</div>');
			html.push('</div>');
			html.push('<div class="row-fluid" style="width:100%;height:1.4583rem;margin-top:1rem;font-size: 0.7rem;">');
			html.push('<div onclick="window.IUSPWX.hideNotSupportTips()" style="width:4.4583rem;height:1.4583rem;line-height:1.4583rem;background-color:#10C55B;color:white;margin:0px auto;border-radius:0.125rem;">');
			html.push('我知道了');
			html.push('</div>');
			html.push('</div>');
			html.push('</div>');
			html.push('<div class="row-fluid" onclick="window.IUSPWX.hideNotSupportTips()" style="margin-top:0.5rem;">');
			html.push('<!-- <i class="prompt_close"></i> -->');
			html.push('</div>');
			html.push('</div>');
			html.push('</div>');
			$("body").append(html.join(''));
		}
	}		
	
	/**
	 * 隐藏不支持提示
	 */
	iuspwx.hideNotSupportTips = function(){
		var $element = $('body>[name="WxNotSupportTips"]');
		if($element.length>0){
			$element.hide();
		}
	}
	
	/**
	 * 判断是否是微信客户端
	 */
	iuspwx.fnJudgeIsWXClient = function(){
		//如果微信浏览器
		var ua = navigator.userAgent.toLowerCase();  
		if(ua.match(/MicroMessenger/i)=="micromessenger") { 
			return true;
		}else{
			iuspwx.showNotSupportTips();
		}
		return false;
	}
	
	/**
	 * 扫一扫
	 */
	iuspwx.fnScanQRCode = function(option){
		var flag = iuspwx.fnJudgeIsWXClient();
		if(!flag){return false;}
		//默认参数
		var defaultOption = {
			    needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
			    scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
			    success: function (res) {// 当needResult 为 1 时，扫码返回的结果
			    	var result = res.resultStr;
			    	if(result.indexOf("/oldarchives/toolddetails.do")!=-1){
			    		window.location.href =  result;
			    	}else{
			    		IUSP.popupAlert.show("非正常二维码错误!");
			    	}
			    }
			}
		if(option && option!=null){
			$.extend(defaultOption,option);
		}
		wx.scanQRCode(defaultOption);
	}
	
	/**
	 * 调用摄像头
	 */
	iuspwx.fnChooseImage = function(option){
		var flag = iuspwx.fnJudgeIsWXClient();
		if(!flag){return false;}
		//默认参数
		var defaultOption = {
				count: 9, // 默认9
			    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
			    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
			    success: function (res) { // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
			    }
			}
		if(option && option!=null){
			$.extend(defaultOption,option);
		}
		wx.chooseImage(defaultOption);
	}
	/**
	 * 获取本地图片接口
	 */
	iuspwx.fnGetLocalImgData = function(option){
		var flag = iuspwx.fnJudgeIsWXClient();
		if(!flag){return false;}
		//默认参数
		var defaultOption = {
				 //localId: '必传', // 图片的localID
			     success: function (res) {
			        var localData = res.localData; // localData是图片的base64数据，可以用img标签显示
			    }
			}
		if(option && option!=null){
			$.extend(defaultOption,option);
		}
		wx.getLocalImgData(defaultOption);
	}
	
	/**
	 * 上传图片到微信临时素材管理中
	 */
	iuspwx.fnUploadImage = function(option){
		var flag = iuspwx.fnJudgeIsWXClient();
		if(!flag){return false;}
		//默认参数
		var defaultOption = {
			   // localId: "必传", // 需要上传的图片的本地ID，由chooseImage接口获得
	            isShowProgressTips: 1, // 默认为1，显示进度提示
			    success: function (res) {// 返回图片的服务器端ID
			    }
			}
		if(option && option!=null){
			$.extend(defaultOption,option);
		}
		wx.uploadImage(defaultOption);
	}
	
	/**
	 * 获取地理位置
	 */
	iuspwx.fnGetLocation = function(option){
		var flag = iuspwx.fnJudgeIsWXClient();
		if(!flag){return false;}
		//默认参数
		var defaultOption = {
				type: 'wgs84',// 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
				success: function (res) {
					var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
			        var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
			        var speed = res.speed; // 速度，以米/每秒计
			        var accuracy = res.accuracy; // 位置精度
				}
		}
		if(option && option!=null){
			$.extend(defaultOption,option);
		}
		wx.getLocation(defaultOption);
	}
	
	
	/**
	 * 发起一个微信支付请求
	 */
	iuspwx.fnChooseWXPay = function(option){
		var flag = iuspwx.fnJudgeIsWXClient();
		if(!flag){return false;}
		var defaultOption = {
			timestamp: '', // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
		    nonceStr: '', // 支付签名随机串，不长于 32 位
		    'package': '', // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
		    signType: 'MD5', // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5',注意后台签名和前台签名必须一致
		    paySign: '', // 支付签名
		    success: function (res) {
		    	// 支付成功后的回调函数
		    }
		}
		if(option && option!=null){
			$.extend(defaultOption,option);
		}
		wx.chooseWXPay(option);

	}
	
	
})(jsSHA,(function() {
	window.IUSPWX = {};
	return window.IUSPWX;
})());