/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.LoginBean;
import Beans.Usuario;
import DAO.UsuarioDao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static util.ConnectionFactory.status;

/**
 *
 * @author LucasMello
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
           
            String login = request.getParameter("login");
            String senha = request.getParameter("senha");

            UsuarioDao userDao = new UsuarioDao();
            Boolean retorno = userDao.carregarPessoas(login, senha);
            
            HttpSession session = request.getSession();
            if (retorno) {
                
                Usuario user = userDao.carregarUsuario(login, senha);
                
                
                
                LoginBean loginBean = new LoginBean();
                
                loginBean.setId(user.getId());
                loginBean.setNome(user.getNome());
                
                session.setAttribute("login", loginBean);

                
                
                session.setAttribute(senha, out);
                
                session.setAttribute("nome", login);
                session.setAttribute("senha", senha);

                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet LoginServlet</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Logado com sucesso </h1>");
                out.println("<p><a href=PortalServlet>PORTAL</a></p>");
                out.println("</body>");
                out.println("</html>");
            } else {
               request.setAttribute("page", "index.html");

               request.setAttribute("msg", "Login incorreto");
               request.getRequestDispatcher("erroServlet").forward(request, response); 

                
//                out.println("<!DOCTYPE html>");
//                out.println("<html>");
//                out.println("<head>");
//                out.println("<title>Servlet LoginServlet</title>");            
//                out.println("</head>");
//                out.println("<body>");
//                out.println("<h1>Usuario nao cadastrado </h1>");
//                out.println("<p><a href=index.html>LOGIN</a></p>");
//                out.println("</body>");
//                out.println("</html>");
            }
            /* TODO output your page here. You may use following sample code. */

        } catch (Exception ex) {
            //Tentei fazer aparecer o erro do SQL mas não consegui
            PrintWriter out = response.getWriter();
            out.println(status);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
