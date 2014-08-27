<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>手工配置站点</title>
		<script>

function saveCheck() {
	var list = ${siteIdUnChoose};
	// alert(list);
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
}
</script>
		<link rel="stylesheet" href="style/style.css" type="text/css" />
		<link rel="stylesheet" href="style/ui.tabs.css" type="text/css" media="print,projection,screen"/>
		<link rel="stylesheet" type="text/css" href="style/pikaday.css" />
		<script type="text/javascript" src="js/pikaday.min.js">
</script>
		<script src="js/jquery-1.2.4b.js" type="text/javascript">
</script>
		<script src="js/ui.core.js" type="text/javascript">
</script>
		<script src="js/ui.tabs.js" type="text/javascript">
</script>
		<script type="text/javascript">
$(function() {
	$('#rotate > ul').tabs( {
		fx : {
			opacity : 'toggle'
		}
	}).tabs('rotate');
});
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
						<script>
function run() {
	window.location.href = "SiteChooseCrawlB.jsp";
}
</script>
					</td>
					<td>
						保存方案
					</td>
					<td>
						<img src="image/save.png" id="pause" />
					</td>


				</tr>
			</table>
		</div>

		<script>
function save() {
	var id = document.getElementsByName('site');
	var value = new Array();
	for ( var i = 0; i < id.length; i++) {
		if (id[i].checked == false) {
			value.push(id[i].value);
		}
	}
	window.location = "<%=basePath%>site/siteId!getValues.action?siteIdString="+ value.toString();
}
</script>
		<div id="checkChoose">
			<div id="rotate">
				<ul>
					<li>
						<a href="#fragment-1"><span>站点配置页</span> </a>
					</li>
					<li>
						<a href="#fragment-2"><span onclick="run()">站点监测页</span> </a>
					</li>

				</ul>
				<div id="fragment-1">
					<%
						//System.out.println(session.getAttribute("crawlTime"));
					//System.out.println(session.getAttribute("time"));
					
					%>

					<div id="timeChoose">
						<strong>选择采集时间范围</strong>
						<table>
							<tr>
								<td>
									<input type="radio" name="radiobutton" value="radiobutton"
										checked="true" />
								</td>
								<td>
									快速选择：
								</td>
								<td width="950">
								<%--<s:form>
								 <s:action name="siteAction!listAll" namespace="/site" id="bean" />
								<s:select name = "selectCrawlTime" list="#{1:'近三天',2:'近一周',3:'三个月',4:'近半年',5:'近一年'}" 
								listKey="key" listValue="value" theme="simple">
</s:select>
								</s:form>
									--%>
									<script>

 function showtTypeKey(){
	 alert(12);
  var type = document.getElementById("timeFastSelect");
  var pindex  =  type.selectedIndex;
  // 获取选中的下拉框的值(value)
　　  var pValue  =  type.options[pindex].value;
  alert(pValue);
　　  // 获取选中的下拉框的选项(key)
　//　  var pText   =  type.options[pindex].text;
  //给隐藏域typeName赋值[next]
　//　  document.getElementById("typeName").value=pText;
 window.location = "<%=basePath%>site/siteAction!listAll.action?selectCrawlTime="+ pValue.toString();
 }
</script>
									<select name="time" size="1" onchange="showTypeKey()" id="timeFastSelect">
										<option value="1" selected="selected">
											近三天
										</option>
										<option value="2">
											近一周
										</option>
										<option value="3">
											三个月
										</option>
										<option value="4">
											近半年
										</option>
										<option value="5">
											近一年
										</option>

									</select>
									(近一年、近半年、近三个月、近一周、近三天)
								</td>

								<td width="150">
									<strong>上次配置时间:2014</strong>
								</td>

								<td>
									<strong>配置人：admin</strong>
								</td>
							</tr>


							<tr>
								<td>
									<input type="radio" name="radiobutton" value="radiobutton" />
								</td>
								<td>
									选择时间段：
								</td>
								<td width="950">
									<input type="text" id="beginTime" size="10" />
									——

									<input type="text" id="endTime" size="10" />

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

								<td>
									<strong>上次采集时间:2014</strong>
								</td>
							</tr>

						</table>
						<script>
						
function checkAll() {
	var checklist = document.getElementsByName("site");
	if (document.getElementById("checkAll").checked) {
		for ( var i = 0; i < checklist.length; i++) {
			checklist[i].checked = 1;
		}

	}else{
  for(var j=0;j<checklist.length;j++)
  {
     checklist[j].checked = 0;
  }
 }
}

function checkForum() {
	var list = ${selectSiteForum};
	var id = document.getElementsByName('site');
	if (document.getElementById("checkForum").checked) {
		for ( var j = 0; j < id.length; j++) {
			id[j].checked = 0;
		}
		for ( var i = 0; i < list.length; i++) {
			for ( var j = 0; j < id.length; j++) {
				if (id[j].value == list[i].toString()) {
					//alert(id[j].value);
					id[j].checked = 1;
				
				}
				
			}
		}
	}
}

function checkBlog(){
	var list = ${selectSiteBlog};
	var id = document.getElementsByName('site');
	if (document.getElementById("checkBlog").checked) {
		for ( var j = 0; j < id.length; j++) {
			id[j].checked = 0;
		}
		for ( var i = 0; i < list.length; i++) {
			for ( var j = 0; j < id.length; j++) {
				if (id[j].value == list[i].toString()) {
					id[j].checked = 1;
				
				}
				
			}
		}
	}
}
	

function checkPostBar(){
	var list = ${selectSitePostBar};
	var id = document.getElementsByName('site');
	if (document.getElementById("checkPostBar").checked) {
		for ( var j = 0; j < id.length; j++) {
			id[j].checked = 0;
		}
		for ( var i = 0; i < list.length; i++) {
			for ( var j = 0; j < id.length; j++) {
				if (id[j].value == list[i].toString()) {
					id[j].checked = 1;
				
				}
				
			}
		}
	}
}

</script>
						<table id="check1">

							<tr>
								<td width="150">
									
										<input type="radio"  name="site1" onclick="checkAll()"
										id="checkAll"  />
									全选
								</td>
								<td width="150">
									<input type="radio" name="site1" onclick="checkForum()"
										id="checkForum" />
									全选论坛
								</td>


								<td width="150">
									<input type="radio" name="site1" onclick="checkBlog()"
										id="checkBlog" />
									全选博客
								</td>
								<td width="380">
									<input type="radio" name="site1"
										onclick="checkPostBar()" id="checkPostBar" />
									全选贴吧
								</td>
								<td>
									<span class="STYLE1">(共有33个论坛，已选15个；20个博客，已选10个；微博30个，已选12个)</span>
								</td>

							</tr>
						</table>
						<table id="check2">
							<s:iterator value="siteNameMap.keySet()" id="id">
								<tr>
									<td class="categoryName">
										<strong><s:property value="#id" /> </strong>
									</td>
								</tr>
								<tr>
									<s:iterator value="siteNameMap.get(#id)">
										<td>
											<input type="checkbox" name="site" checked="true"
												value="<s:property value="siteId"/>" />
									<a href="<s:property value="seedUrl" />"><s:property value="siteName" /></a>	
										</td>

									</s:iterator>
								</tr>
							</s:iterator>
						</table>

					</div>

					<div id="fragment-2">
						站点监测页
					</div>

				</div>
				<span class="saveSite">【保存方案】</span>
			</div>
		</div>
	</body>
</html>

