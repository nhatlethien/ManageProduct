package edu.poly.shop.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Component
public class AdminAuthenticationInterceptor implements HandlerInterceptor{
	@Autowired
	private HttpSession session;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("Bạn phải xử lý yêu cầu trước" + request.getRequestURI());
		
		if (session.getAttribute("username") != null) {
			return true;
		}
		
		session.setAttribute("redirect-uri", request.getRequestURI());
		response.sendRedirect("/alogin");
		
		return false;
	} //phương thức xử lý đầu tiên trước khi các phương thức khác được đưa vào xử lý
	

}
