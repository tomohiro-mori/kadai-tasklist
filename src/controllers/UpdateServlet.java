package controllers;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.tasks;
import utils.DBUtil;

/**
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     // CSRF対策
        String _token = (String)request.getParameter("_token");
        if (_token != null &&  _token.equals(request.getSession().getId())){

            // DB接続
            EntityManager em = DBUtil.createEntityManager();

            // DBの各カラムに値セット
            tasks t = em.find(tasks.class, (Integer)request.getSession().getAttribute("task_id"));

            String content = request.getParameter("content"); // タスク内容
            t.setContent(content);

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            t.setUpdated_at(currentTime); // 更新日時のみ上書き

            // DBトランザクション
            em.getTransaction().begin();
            em.getTransaction().commit();
            em.close();

            // セッションスコープの値を削除
            request.getSession().removeAttribute("task_id");

            // indexページにリダイレクト
            response.sendRedirect(request.getContextPath() + "/index");


        }
    }

}
