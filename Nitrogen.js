//return document.getElementById('thread_subject').getBoundingClientRect();
///html/body/div[1]/div[7]/div[1]/h1
var root = document.documentElement;
var array={};
var s=0;
   //选中HTML节点
    function forDom(root1){
		var stylenode=window.getComputedStyle(root1);
		var rectObject =root1.getBoundingClientRect();
		var scrollWidth = root1.scrollWidth;    // 返回元素的总宽度
		var scrollHeight = root1.scrollHeight;    // 返回元素的总高度
		var fullxpath=getPathTo(root1);
		var dominfo=GetDomInfo(rectObject);
		var fontSize=stylenode.getPropertyValue('font-size');
		var fontWeight =stylenode.getPropertyValue('fontWeight');
		dominfo.fontSize=fontSize;
		dominfo.fontWeight=fontWeight;
		dominfo.nodeName=root1.nodeName;
		fullxpath=fullxpath.replace('/HTML[1]','/HTML').replace('/BODY[1]','/BODY');
		dominfo.fullxpath=fullxpath;
		dominfo.scrollWidth=scrollWidth;
		dominfo.scrollHeight=scrollHeight;
		// console.log(dominfo);
		array[s++]=dominfo;
		 
        if(root1.children != undefined){
            for(var i = 0; root1.children[i] != undefined; i ++){
                forDom(root1.children[i]);
            }
        }
    }
	function GetDomInfo(rectObject){
		var top = rectObject.top;
		var left = rectObject.left;
		var bottom = rectObject.bottom;
		var width = rectObject.width;
		var x = rectObject.x;
		var y = rectObject.y;
		var rectWidth = rectObject.right;
		var height = rectObject.height;
		var json = {};
		json.top=top;
		json.left=left;
		json.bottom=bottom;
		json.width=width;
		json.x=x;
		json.y=y;
		json.rectWidth=rectWidth;
		json.height=height;
		return json;
	}
	function getPathTo(element) {
		//if (element.id!=='')
		//	return 'id("'+element.id+'")';
		//if (element===document.body)
		//	return element.tagName;
		if(element.parentNode==null)
		{
			return '';
		}
		var ix= 0;
		var siblings= element.parentNode.childNodes;
		for (var i= 0; i<siblings.length; i++) {
			var sibling= siblings[i];
			if (sibling===element)
				return getPathTo(element.parentNode)+'/'+element.tagName+'['+(ix+1)+']';
			if (sibling.nodeType===1 && sibling.tagName===element.tagName)
            ix++;
		}
	}
forDom(root);
return array;