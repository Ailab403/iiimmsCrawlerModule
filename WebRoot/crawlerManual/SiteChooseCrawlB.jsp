<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="java.util.Date,java.text.SimpleDateFormat" %>
<%SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");String dq = sdf.format(new Date());%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
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
	function mod()
	{
		if( confirm("您确定要修改该站点吗?") )
	    {
			var siteName     = document.editForm.siteName.value;
			var nickName     = document.editForm.nickName.value;
			var seedUrl      = document.editForm.seedUrl.value;
			var enCode       = document.editForm.enCode.value;
			var siteHotNum   = document.editForm.siteHotNum.value;
			var siteExp      = document.editForm.siteExp.value;
			var regExp       = new RegExp("[a-zA-z]+://[^\s]*","ig");
			var intRegExp    = new RegExp("^[1-9][0-9]*$","g");
			
			if (siteName.length < 1) {
				alert("请输入站点名称！");
				return false;
			}
	
			if (nickName.length < 1) {
				alert("请输入站点英文（拼音）名称!");
				return false;
			}
			
			if ( seedUrl.length < 1 ) {
				alert("种子链接(URL)不能为空");
				return false;
			}else if(!seedUrl.match(regExp)){
				alert("种子链接格式不正确，请重新以提示格式输入！");
					return false;
			}
			
			if (enCode.length < 1) {
				alert("请输入站点中文编码!");
				return false;
			}
			
			if (1 > siteHotNum.length){
				alert("请输入信息热度 !");	
				return false;
			}else if (!siteHotNum.match(intRegExp)){
				alert("信息热度 必须是正整数，请重新输入！");
				return false;
			}else if ( siteHotNum <= 0 || siteHotNum >= 10000){
				alert("信息热度 必须是 1-9999中的正整数!");
				return false;
			}
			
			if (siteExp.length < 1) {
				alert("链接说明不能为空!");
				return false;
			}
			
			document.getElementById('btnEdit').disabled  = true;
			document.getElementById('btnReset').disabled = true;
			document.getElementById('btnBack').disabled  = true;
			
			var postData = $("#crawlSiteManual").serialize();
			$.post("<%=basePath%>crawlerManual/siteId.action",postData,
			function(data)
			{
				var myTip = data.modTip;
				if ( 1== myTip )
				{
					alert("修改站点成功！");
					window.location = "<%=basePath%>site/siteAction!siteList.action?date="+new Date() ;
				}
				else if (0==myTip)
				{
					alert("修改站点失败，请重试！");
				}
				document.getElementById('btnEdit').disabled  = false;
				document.getElementById('btnReset').disabled = false;
				document.getElementById('btnBack').disabled  = false;
				
			},"json");
		}
		else
		{
			return false;	
		}
		
		return true;
	}
	
	function back(form)
	{
		var cid = ${sessionScope.displaySite.siteId};
		window.location = "<%=basePath%>site/siteAction!siteList.action?siteId="+cid;
	}	
</script>
</head>
  <body onload=saveCheck()>
	<s:form id="crawlSiteManual" name="crawlSiteManual" theme="simple" >
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
						<img src="image/save.png" id="pause" />
					</td>


				</tr>
			</table>
		</div>
		
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
						//System.out.println(session.getAttribute("beginTime"));
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


</script>
  <s:select id="selectCrawlTime" name="selectCrawlTime" list="#{1:'近三天',2:'近一周',3:'三个月',4:'近半年',5:'近一年'}" 
							  listKey="key" listValue="value" theme="simple" >
							  </s:select>
<%--<input type="text" id="txt" />
									<select name="time" size="1" onchange="select(this.options[this.options.selectedIndex].value)"
										id="timeFastSelect">
										<option value="0">
											近三天
										</option>
										<option value="1">
											近一周
										</option>
										<option value="2">
											三个月
										</option>
										<option value="3">
											近半年
										</option>
										<option value="4">
											近一年
										</option>

									</select>--%>
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
								<s:textfield name="beginTime"  id="beginTime" size="10">
						</s:textfield>
——		<s:textfield name="endTime"  id="endTime" size="10">
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

								<td>
									<strong>上次采集时间:2014</strong>
								</td>
							</tr>

						</table>

						<table id="check1">

							<tr>
								<td width="150">

									<input type="radio" name="site1" onclick="checkAll()" id="checkAll" />
									全选
								</td>
								<td width="150">
									<input type="radio" name="site1" onclick="checkForum()" id="checkForum" />
									全选论坛
								</td>
								<td width="150">
									<input type="radio" name="site1" onclick="checkBlog()" id="checkBlog" />
									全选博客
								</td>
								<td width="380">
									<input type="radio" name="site1" onclick="checkPostBar()" id="checkPostBar" />
									全选贴吧
								</td>
								<td>
									<span class="STYLE1">(共有${forumNum}个论坛，已选15个；${blogNum}个博客，已选10个；${barNum}个贴吧，已选12个)</span>
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
											<a href="<s:property value="seedUrl" />"><s:property
													value="siteName" /> </a>
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
				<span class="saveSite" onclick="return mod();">【保存方案】</span>
			</div>
		</div>
		</s:form>
  </body>
</html>
