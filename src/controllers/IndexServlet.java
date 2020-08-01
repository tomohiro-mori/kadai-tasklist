package controllers;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.tasks;
import utils.DBUtil;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // DB接続
        EntityManager em = DBUtil.createEntityManager();

        // tasksクラス(DTO)のクエリーを実行して結果をリスト格納
        List<tasks> tasks = em.createNamedQuery("getAllTasks", tasks.class).getResultList();

        // データ数を取得してレスポンス
        response.getWriter().append(Integer.valueOf(tasks.size()).toString());

        // DB切断
        em.close();

    }

}
