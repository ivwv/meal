<%@ page pageEncoding="utf-8" import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/pages/head.jsp">
	<jsp:param value="用户点餐列表" name="title" />
</jsp:include>
</head>
<body>
	<jsp:include page="/pages/admin/admin_nav.jsp">
		<jsp:param value="dc_s" name="fun" />
	</jsp:include>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-3 col-0"></div>
			<div class="col-md-6 col-12">
			<% 
				Map<String,List<Map<String,String>>> dcs = (Map<String,List<Map<String,String>>>)request.getAttribute("dcs");
				for(Map.Entry<String,List<Map<String,String>>> entry :dcs.entrySet()){
					String username = entry.getKey();
					List<Map<String,String>> dc = entry.getValue();
			%>
					<div class="card border-primary">
						<div class="card-body">
							<ul class="list-group">
								<li class="list-group-item active"><%=username %></li>
								<% int sum = 0; 
								   for(Map<String,String> food : dc){
								%>
									<li class="list-group-item"><%=food.get("foodname") %><span
										class="badge badge-pill badge-success p-2 float-right">￥<%=food.get("price") %></span>
									</li>
								<% 
									sum += Integer.parseInt(food.get("price"));
								   }
								%>
								<li class="list-group-item active">合计 <span
									class="badge badge-pill badge-success p-2 float-right">￥<%=sum %></span>
								</li>
							</ul>
						</div>
					</div>
			<%} %>
			</div>
			<div class="col-md-3 col-0"></div>
		</div>
	</div>
	<!--ending container-->
</body>
</html>