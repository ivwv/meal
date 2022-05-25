package ctrl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import model.*;
import util.FileUploadUtil;

/**
 * Servlet implementation class CenterController
 */
@WebServlet("*.action")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
public class CenterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CenterController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String path = request.getServletPath();
		path = path.substring(path.lastIndexOf('/') + 1, path.indexOf(".action"));
		if (path.equals("homepage")) {
			// 首页
			FoodService food = new FoodService();
			request.setAttribute("hot", food.getHotFoods());
			request.setAttribute("special", food.getSpecialFoods());
			request.setAttribute("recomm", food.getRecommFoods());
			request.getRequestDispatcher("/pages/homepage.jsp").forward(request, response);
		} else if (path.equals("show_detail")) {
			// 菜品详细信息
			FoodService food = new FoodService();
			String id = request.getParameter("id");
			request.setAttribute("food", food.getFood(id));
			request.getRequestDispatcher("/pages/show_detail.jsp").forward(request, response);
		} else if (path.equals("login")) {
			// 用户登录
			// 读取请求参数un和pw
			String un = request.getParameter("un");
			String pw = request.getParameter("pw");
			// 判断用户名和密码是否正确
			if (un == null || pw == null || un.equals("") || pw.equals("")) {
				// 用户未登录
				request.setAttribute("msg", "请先登录！");
				request.setAttribute("href", request.getContextPath() + "/homepage.action");
				request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
			} else {
				// 调用模型进行身份验证
				UserService us = new UserService();
				Map<String, String> r = us.login(un, pw);
				if (r != null) {
					// 登录成功
					String ident = r.get("ident");
					// 用session保存用户的登录信息
					session.setAttribute("loginID", r.get("id"));
					session.setAttribute("loginName", un);
					session.setAttribute("ident", ident);
					if (ident.equals("1")) {
						// 管理员
						response.sendRedirect(request.getContextPath() + "/admin/admin_index.action");
					} else {
						// 普通用户
						response.sendRedirect(request.getContextPath() + "/user/user_index.action");
					}
				} else {
					// 登录失败
					// 不合法用户
					request.setAttribute("msg", "用户名或密码错误！");
					request.setAttribute("href", request.getContextPath() + "/homepage.action");
					request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
				}
			}
		} else if (path.equals("user_index")) {
			// 在进行用户其他功能之前，前提是用户已经登录，并且session中已经存储了用户信息。
			// session保存用户的登录信息的语句可以参考如下
//			session.setAttribute("loginID", **));		//存储用户id
			// session.setAttribute("loginName", **); //存储用户名
//			session.setAttribute("ident", **);		//存储用户身份

			FoodService f = new FoodService();
			String s_fn = request.getParameter("s_fn");
			String s_type = request.getParameter("s_type");
			request.setAttribute("foods", f.getFoods(s_fn, s_type));
			FoodTypeService ft = new FoodTypeService();
			request.setAttribute("types", ft.getAllTypes());
			request.getRequestDispatcher("/pages/user/user_index.jsp").forward(request, response);
		} else if (path.equals("user_add_dc")) {
			String[] ids = request.getParameterValues("ids");
			String userid = (String) session.getAttribute("loginID");
			DiningCarService dc = new DiningCarService();
			int r = dc.addToDC(userid, ids);
			request.setAttribute("msg", "成功将" + r + "个菜品加入点餐车！");
			request.setAttribute("href", request.getContextPath() + "/user/user_show_dc.action");
			request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
		} else if (path.equals("user_show_dc")) {
			String userid = (String) session.getAttribute("loginID");
			DiningCarService dc = new DiningCarService();
			request.setAttribute("dc", dc.showDC(userid));
			request.getRequestDispatcher("/pages/user/user_show_dc.jsp").forward(request, response);
		} else if (path.equals("user_del_dc")) {
			String[] ids = request.getParameterValues("ids");
			DiningCarService dc = new DiningCarService();
			int r = dc.delFromDC(ids);
			request.setAttribute("msg", "成功将" + r + "个菜品从点餐车删除！");
			request.setAttribute("href", request.getContextPath() + "/user/user_show_dc.action");
			request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
		} else if (path.equals("admin_index")) {
			request.getRequestDispatcher("/pages/admin/admin_index.jsp").forward(request, response);
		} else if (path.equals("admin_show_dc")) {
			// 读取所有用户的点餐情况
			DiningCarService dc = new DiningCarService();
			request.setAttribute("dcs", dc.showAllDC());
			request.getRequestDispatcher("/pages/admin/admin_show_dc.jsp").forward(request, response);
		} else if (path.equals("admin_list_food")) {
			String s_fn = request.getParameter("s_fn");
			String s_type = request.getParameter("s_type");
			FoodService f = new FoodService();
			request.setAttribute("foods", f.getFoods(s_fn, s_type));
			FoodTypeService ft = new FoodTypeService();
			request.setAttribute("types", ft.getAllTypes());
			request.getRequestDispatcher("/pages/admin/admin_list_food.jsp").forward(request, response);
		} else if (path.equals("admin_edit_food")) {
			String id = request.getParameter("id");
			FoodTypeService ft = new FoodTypeService();
			request.setAttribute("types", ft.getAllTypes());
			request.setAttribute("food", new FoodService().getFood(id));
			request.getRequestDispatcher("/pages/admin/admin_edit_food.jsp").forward(request, response);
		} else if (path.equals("admin_edit_food_do")) {
			try {
				String id = request.getParameter("id");
				String foodname = request.getParameter("fn");
				String feature = request.getParameter("fea");
				String material = request.getParameter("mat");
				String type = request.getParameter("type");
				String price = request.getParameter("price");
				String comment = request.getParameter("com");
				String picture = null;
				Part img = request.getPart("img");
				// 判断上传文件的扩展名是否符合要求
				String fileExtName = FileUploadUtil.getFileExtName(img);
				if (!fileExtName.equals("") && !fileExtName.equalsIgnoreCase(".jpg")
						&& !fileExtName.equalsIgnoreCase(".png") && !fileExtName.equalsIgnoreCase(".gif")) {
					request.setAttribute("msg", "上传文件的扩展名应为jpg,png或gif！");
					request.setAttribute("href", "javascript:history.back()");
					request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
				} else {
					picture = FileUploadUtil.uploadSingleFile(img, request);
					// 调用模型插入数据库
					FoodService f = new FoodService();
					int r = f.editFood(foodname, feature, material, price, type, picture, comment, id);
					request.setAttribute("msg", r == 1 ? "修改菜品成功！" : "修改菜品失败！");
					request.setAttribute("href", request.getContextPath() + "/admin/admin_list_food.action");
					request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
				}
			} catch (IllegalStateException e) {
				request.setAttribute("msg", "上传文件大小应小于5M！");
				request.setAttribute("href", "javascript:history.back()");
				request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
			}
		}
	}

}
