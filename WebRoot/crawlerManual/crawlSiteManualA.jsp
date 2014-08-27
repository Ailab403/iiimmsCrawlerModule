<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<title>手动配置及启动页</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta name="save" content="history">
		<link rel="stylesheet" href="style/style.css" type="text/css" />
		<link rel="stylesheet" href="style/ui.tabs.css" type="text/css"
			media="print,projection,screen" />
		<link rel="stylesheet" href="style/pikaday.css" type="text/css" />
		<script src="js/pikaday.min.js" type="text/javascript">
</script>
		<script src="js/jquery-1.2.4b.js" type="text/javascript">
</script>
		<script src="js/ui.core.js" type="text/javascript">
</script>
		<script src="js/ui.tabs.js" type="text/javascript">
</script>
		<script type="text/javascript">

var isClick = false; //这是一个标识符 用来对是否保存按钮有没有被点击进行判断
//一加载就执行的函数 主要是从数据库中获得siteGrabable为1时 站点就选中，为0就不选		
function saveCheck() {
	var list = ${siteIdUnChoose};
	var id = document.getElementsByName('site');
	for ( var i = 0; i < list.length; i++) {
		var a = list[i];
		var b = a.toString();
		for ( var j = 0; j < id.length; j++) {
			if (id[j].value == b) {
				// alert(id[j].value);
				id[j].checked = false;
			}
		}
	}
	
	fastSelect();
	// $('checkOther').onclick = checkTest;   

	
}




//在快速选择与时间段之间选择按钮的设置
function fastSelect(){
	if (document.getElementById('radio1').checked) {
		//设置为只可读 不知为何 readonly在这没有用  只能用disabled
	 document.getElementById('beginTime').disabled = true;
	  document.getElementById('endTime').disabled = true;
	   document.getElementById('selectCrawlTime').disabled = false;
}
	}

function timeRange(){
	if (document.getElementById('radio2').checked) {
		 document.getElementById('selectCrawlTime').disabled = true;
		 document.getElementById('beginTime').disabled = false;
	     document.getElementById('endTime').disabled = false;
		}
	}

//样式的函数
$(function() {
	$('#rotate > ul').tabs( {
		fx : {
			opacity : 'toggle'
		}
	}).tabs('rotate');
});


//保存方案的按钮函数
function save() {
	isClick = true;
	var unselectSiteId = new Array();
    var selectSiteId = new Array();
	var id = document.getElementsByName('site');
	
	for ( var i = 0; i < id.length; i++) {
		if (id[i].checked == false) {
			unselectSiteId.push(id[i].value);
		}else{
			selectSiteId.push(id[i].value);
		}
	}
	
	//判断是否没有选中站点
	if(selectSiteId.length==0){
		alert("您没有选中站点，不能运行！请选择站点！");
	}
	else{
		//把相应的值传回到后台  unselectSiteId是复选框没有选中的siteId的值   selectSiteId是选中了的值
	window.location = "<%=basePath%>crawlerManual/siteId!saveAllInfo.action?unselectSiteId=" + unselectSiteId.toString()+"&selectSiteId="+selectSiteId.toString();
	}
	
	//把form表单的值传回
	var postData = $("#crawlSiteManual").serialize();
		$.post("<%=basePath%>crawlerManual/siteId.action",postData,
					function(data) {
						var addTip = data.addTip;
						if (1 == addTip) {
							alert("保存站点成功！");
							
						} else if (0 == addTip) {
							alert("保存站点失败，请重试！");
						}
						document.getElementById('btnAdd').disabled = false;
						
					}, "json");
}


//开始采集的按钮函数
function run() {
	
	    if(isClick == false)
    {
          if(confirm("是否保存您选择的站点")){ 
               save(); 
        }else {
        	window.location.href = "SiteChooseCrawlB.jsp";
        } 
    
    }else
    {
 window.location.href = "SiteChooseCrawlB.jsp";

}
}

//实时采集任务监测
function monitor() {
          if(confirm("是否要开始采集任务")){ 
        	  window.location.href = "SiteChooseCrawlB.jsp";
        }
          else {
        	window.location.href = "<%=basePath%>crawlerManual/siteAction.action";
       } 
}

//全选事件
function checkAll() {
	var checklist = document.getElementsByName('site');
	 var checkCategoryId = document.getElementsByName('checkOther');
	if (document.getElementById("checkAll").checked) {
		//如果全选被选中，那么其他的复选框全都不选
	    for(var i=0;i<checkCategoryId.length;i++){
	     	 checkCategoryId[i].checked=false;
	     }
		for ( var i = 0; i < checklist.length; i++) {
			checklist[i].checked = 1;
		}
	} else {
		for ( var j = 0; j < checklist.length; j++) {
			checklist[j].checked = 0;
		}
	}
}

  //触发其他复选框的事件
function checkTest(){
    var checkCategoryId = document.getElementsByName('checkOther');
    var id = document.getElementsByName('site');
     var strJson=${categoryOfSiteIdJson};//得到后台传来的JSON 
     //对checkOther的复选框进行遍历
    for(var i=0;i<checkCategoryId.length;i++){
    	//如果它被选中
    	if(checkCategoryId[i].checked==true){
    		document.getElementById("checkAll").checked=false;
    		    //遍历strJson串获取其属性  
         for(var item in strJson){  
        if(item==checkCategoryId[i].value){  //item 表示Json串中的属性，如'1'  
            var jValue=strJson[item];//key所对应的value ,即要选中的siteId值 
         //  alert(jValue); 
            //选中jValue中的值
		for ( var b = 0; b < jValue.length; b++) {
			for ( var c = 0; c < id.length; c++) {
				if (id[c].value == jValue[b].toString()) {
				//	alert(jValue[b].toString());
					id[c].checked = 1;
				
				}
				
			}
		}
        }  
    }
    	}else{
    	     //遍历strJson串获取其属性  
         for(var item in strJson){  
        if(item==checkCategoryId[i].value){  //item 表示Json串中的属性，如'1'  
            var jValue=strJson[item];//key所对应的value ,即要选中的siteId值 
            //使jValue中的值没有被选中
		for ( var b = 0; b < jValue.length; b++) {
			for ( var c = 0; c < id.length; c++) {
				if (id[c].value == jValue[b].toString()) {
					id[c].checked = 0;
				
				}
				
			}
		}
        }  
    }
    	}
    	
    }
   
   
  }

</script>
	</head>

	<body onload=saveCheck()>
		<div id="header">
			<table id="button">
				<tr>
					<td>

						开始采集
					</td>
					<td width="200">
						<img src="image/Play.png" onclick="run()"></img>
					</td>
					<td>
						保存方案
					</td>
					<td>
						<img src="image/save.png" id="pause" onclick="save()" />
					</td>


				</tr>
			</table>
		</div>
		<div id="checkChoose">
			<div id="rotate">
				<ul>
					<li>
						<a href="#fragment-1"><span>选择采集站点</span> </a>
					</li>
					<li>
						<a href="#fragment-2"><span onclick="monitor()">采集状态实时监测</span>
						</a>
					</li>

				</ul>
				<div id="fragment-1">
					<%
						//System.out.println(session.getAttribute("timeRangeLimit"));
						//System.out.println(session.getAttribute("beginTime"));
						//System.out.println(session.getAttribute("endTime"));
						// System.out.println(session.getAttribute("postStartTimeLimit"));
						// System.out.println(session.getAttribute("categoryOfSiteIdJson"));
					%>

					<div id="timeChoose">
						<strong>选择采集时间范围</strong>
						<s:form id="crawlSiteManual" name="crawlSiteManual" theme="simple"
							method="post">
							<table>
								<tr>
									<td>
										<input type="radio" name="radio" id="radio1"
											onclick="fastSelect()" checked />
									</td>
									<td width="100">
										快速选择：
									</td>
									<td width="550">
										<s:select id="selectCrawlTime" name="selectCrawlTime"
											list="#{1:'近三天',2:'近一周',3:'三个月',4:'近半年',5:'近一年'}"
											listKey="key" listValue="value">
										</s:select>
										(近一年、近半年、近三个月、近一周、近三天)
									</td>


									<td>
										<strong>配置人：admin</strong>
									</td>

								</tr>


								<tr>
									<td>
										<input type="radio" name="radio" onclick="timeRange()"
											id="radio2" />
									</td>
									<td>
										选择时间段：
									</td>
									<td width="950">
										<s:textfield name="beginTime" id="beginTime" size="10">
										</s:textfield>
										——
										<s:textfield name="endTime" id="endTime" size="10">
										</s:textfield>
										<script type="text/javascript">

var beginTime = new Pikaday( {
	field : document.getElementById('beginTime'),
	firstDay : 1,
	minDate : new Date('2010-01-01'),
	maxDate : new Date('2020-12-31'),
	yearRange : [ 2000, 2020 ]
});

var endTime = new Pikaday( {
	field : document.getElementById('endTime'),
	firstDay : 1,
	minDate : new Date('2010-01-01'),
	maxDate : new Date('2020-12-31'),
	yearRange : [ 2000, 2020 ]
});
</script>
									</td>


									<td width="400">
										<strong>上次采集时间:${taskStartTime}——${taskEndTime}</strong>
									</td>
								</tr>

							</table>
						</s:form>
						
						<table id="check1">

							<tr>
								<td width="150">

									<input type="checkbox" name="site1" onclick="checkAll()" id="checkAll" />
									全选
								</td>
								<s:iterator value="categories" id="id">
								<td width="150">
									<input type="checkbox" name="checkOther" onclick="checkTest()" id="<s:property value="categoryId" />" 
									value="<s:property value="categoryId" />" />
									全选<s:property value="categoryName" />
								</td>
								</s:iterator>
							
							</tr>
						</table>
					<s:iterator value="siteNameMap.keySet()" id="id">
						<table id="check2">
							
								<tr>
									<td class="categoryName">
										<strong><s:property value="#id" /> </strong>
									</td>
								</tr>
								<tr>
									<s:iterator value="siteNameMap.get(#id)">
										<td>
											<input type="checkbox" name="site" checked
												value="<s:property value="siteId"/>" onclick="countTotal()" />
											<a href="<s:property value="seedUrl" />"><s:property
													value="siteName" /> </a>
										</td>

									</s:iterator>
								</tr>
							
						</table>
       </s:iterator>
					</div>

					<div id="fragment-2">
						站点监测页
					</div>

				</div>
				<span class="saveSite" onclick="save()"></span>
				<input type="button" value="保存方案" onclick="save()" id="saveSite"
					name="saveSite" />
			</div>
		</div>

	</body>
</html>
