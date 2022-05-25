<%@ page pageEncoding="utf-8"%>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12 col-12">
			<nav
				class="navbar navbar-expand-lg navbar-light bg-light justify-content-between">
				<a class="navbar-brand"
					href="<%=request.getContextPath()%>/homepage.action"><span
					class="text-info">网络点餐系统</span></a>
				<ul class="navbar-nav mr-auto">
					<li
						class="nav-item <%="user_m".equals(request.getParameter("param_fun")) ? "active" : ""%>">
						<a class="nav-link"
						href="<%=request.getContextPath()%>/admin/admin_list_user.action">用户管理</a>
					</li>
					<li
						class="nav-item <%="food_t_m".equals(request.getParameter("param_fun")) ? "active" : ""%>">
						<a class="nav-link" href="#">菜品分类管理</a>
					</li>
					<li
						class="nav-item <%="food_m".equals(request.getParameter("param_fun")) ? "active" : ""%>">
						<a class="nav-link"
						href="<%=request.getContextPath()%>/admin/admin_list_food.action">菜品管理</a>
					</li>
					<li
						class="nav-item <%="dc_s".equals(request.getParameter("param_fun")) ? "active" : ""%>">
						<a class="nav-link"
						href="<%=request.getContextPath()%>/admin/admin_show_dc.action">查看用户点餐情况</a>
					</li>
				</ul>
				<ul class="nav justify-content-end">
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">
							<%=session.getAttribute("loginName")%></a>
						<ul class="dropdown-menu dropdown-menu-right">
							<li><a class="nav-link"
								href="<%=request.getContextPath()%>/logout.action">退出登录</a></li>
						</ul></li>
				</ul>
			</nav>
		</div>
	</div>
</div>