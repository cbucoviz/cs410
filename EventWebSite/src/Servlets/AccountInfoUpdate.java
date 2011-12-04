package Servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Database.DatabaseManager.UserType;
import Models.User;

/**
 * Servlet implementation class ReportAbuse
 */
@WebServlet("/AccountInfoUpdate")
public class AccountInfoUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountInfoUpdate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String oldPassword = request.getParameter("oldpassword");
		String newPassword1 = request.getParameter("newpassword1");
		String newPassword2 = request.getParameter("newpassword2");
		
		RequestDispatcher dispatcher = null;
		
		boolean updateError = false;
		
		boolean oldPasswordEmpty = oldPassword.isEmpty();
		boolean newPassword1Empty = newPassword1.isEmpty();
		boolean newPassword2Empty = newPassword2.isEmpty();
		
		request.setAttribute("missingOldPassword", oldPasswordEmpty);
		request.setAttribute("missingNewPassword1", newPassword1Empty);
		request.setAttribute("missingNewPassword2", newPassword2Empty);
		
		boolean missingParam = 	oldPasswordEmpty ||
				newPassword1Empty ||
				newPassword2Empty;

		updateError = missingParam;
		
		HttpSession session = request.getSession();
		String currentPassword = (String) session.getAttribute(SessionVariables.PASSWORD);
		
		if (!oldPasswordEmpty)
		{
			//If the old password field is not empty
			if (!currentPassword.equals(oldPassword))
			{
				//If input old password is incorrect
				request.setAttribute("oldPasswordMatch", false);
				updateError = true;
			}
		}
		
		if (!newPassword1.equals(newPassword2))
		{
			//new passwords don't match
			request.setAttribute("newPasswordMatch", false);
			updateError = true;
		}
		
		if(!updateError)
		{
			int userId = (Integer) session.getAttribute(SessionVariables.USER_ID);
			boolean result = User.changePassword(userId, newPassword1);
			
			if(!result)
			{
				updateError = true;
				request.setAttribute("updateError", true);
			}
		}
		
		if(updateError)
		{
			dispatcher = request.getRequestDispatcher("profilepage.jsp");
			dispatcher.forward(request, response);
			return;
		}
		else
		{
			response.sendRedirect("profilepage.jsp");
		}
	}

}
