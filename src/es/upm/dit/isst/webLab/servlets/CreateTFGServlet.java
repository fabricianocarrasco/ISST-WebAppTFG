package es.upm.dit.isst.webLab.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.crypto.hash.Sha256Hash;

import es.upm.dit.isst.webLab.dao.ProfessorDAO;
import es.upm.dit.isst.webLab.dao.ProfessorDAOImplementation;
import es.upm.dit.isst.webLab.dao.TFGDAO;
import es.upm.dit.isst.webLab.dao.TFGDAOImplementation;
import es.upm.dit.isst.webLab.model.Professor;
import es.upm.dit.isst.webLab.model.TFG;

@WebServlet("/CreateTFGServlet")
public class CreateTFGServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		String title = req.getParameter("title");
		String advisorEmail = req.getParameter("advisor");
		int status = 0;
		
		ProfessorDAO pdao = ProfessorDAOImplementation.getInstance();
		Professor advisor = pdao.read(advisorEmail);

		TFG tfg = new TFG();
		tfg.setName(name);
		tfg.setEmail(email);
		tfg.setTitle(title);
		tfg.setStatus(status);
		tfg.setAdvisor(advisor);

		tfg.setPassword(new Sha256Hash(password).toString());

		TFGDAO tfgdao = TFGDAOImplementation.getInstance();
		tfgdao.create(tfg);

		resp.sendRedirect(req.getContextPath() + "/LoginServlet");
	}
}
