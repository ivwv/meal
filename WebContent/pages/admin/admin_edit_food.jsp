<%@ page pageEncoding="utf-8" import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/pages/head.jsp">
	<jsp:param value="修改菜品信息" name="title" />
</jsp:include>
</head>
<body>
	<jsp:include page="/pages/admin/admin_nav.jsp">
		<jsp:param value="food_m" name="fun" />
	</jsp:include>
	<%
		Map<String, String> food = (Map<String, String>) request.getAttribute("food");
		if (food != null) {
	%>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-2 col-0"></div>
			<div class="col-md-8 col-12">
				<div class="card border-warning">
					<div class="card-header border-warning bg-warning">
						<h5 class="text-center">修 改 菜 品</h5>
					</div>
					<form role="form" method="post" action="admin_edit_food_do.action"
						enctype="multipart/form-data">
						<input type="hidden" name="id" value="<%=food.get("id")%>">
						<div class="card-body">
							<div class="form-group">
								<label for="foodname">菜品名称</label> <input type="text"
									class="form-control" name="fn" id="foodname"
									required="required" value="<%=food.get("foodname")%>" />
							</div>
							<div class="form-group">
								<label for="feature">菜品特色</label>
								<textarea class="form-control" rows="3" name="fea" id="feature"
									required="required"><%=food.get("feature")%></textarea>
							</div>
							<div class="form-group">
								<label for="material">主要原料</label>
								<textarea class="form-control" rows="3" name="mat" id="material"
									required="required"><%=food.get("material")%></textarea>
							</div>
							<div class="form-group">
								<label for="type">所属分类</label> <select class="form-control"
									name="type" id="type">
									<%
										List<Map<String, String>> types = (List<Map<String, String>>) request.getAttribute("types");
											for (Map<String, String> type : types) {
									%>
									<option value="<%=type.get("id")%>"
										<%=type.get("id").equals(request.getParameter("s_type")) ? "selected" : ""%>>
										<%=type.get("typename")%></option>
									<%
										}
									%>
								</select>
							</div>
							<div class="form-group">
								<label for="price">菜品价格</label> <input type="number"
									class="form-control" name="price" id="price"
									required="required" value="<%=food.get("price")%>" />
								<p class="text-info">单位：元</p>
							</div>
							<div class="form-group">
								<label for="img">菜品图片</label> <input type="file" id="img"
									name="img" />
								<p class="text-info">请选择上传的菜品图片，大小应小于5M，扩展名为jpg,png或gif。</p>
								<img
									src="<%=request.getContextPath()%>/<%=food.get("picture")%>">
							</div>
							<div class="form-group">
								<label for="comment">菜品备注</label> <input type="text"
									class="form-control" name="com" id="comment"
									value="<%=food.get("comment")%>" required="required" />
								<p class="text-info">-1代表正常菜，0代表厨师推荐，正整数代表特价菜价格。</p>
							</div>
						</div>
						<div class="card-footer border-warning text-center">
							<button type="submit" class="btn btn-warning">确认修改</button>
							&nbsp;&nbsp; <a role="button" class="btn btn-default"
								href="javascript:history.back();">放弃返回</a>
						</div>
					</form>
				</div>
			</div>
			<div class="col-md-2 col-0"></div>
		</div>
		<!--ending 2th row-->
	</div>
	<%
		}
	%>
</body>
</html>