package forum.controller;

import forum.model.Comment;
import forum.model.Post;
import forum.service.CommentService;
import forum.service.PostService;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/post")
public class PostViewController extends HttpServlet {
    public static final int HOW_MANY_COMMENTS_IN_ONE_PAGE = 5;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            PostService postService = new PostService();
            Post post = postService.readPost(Integer.parseInt(request.getParameter("post-id")));
            CommentService commentService = new CommentService();
            List<Comment> comments = commentService.readPostAllComment(Integer.parseInt(request.getParameter("post-id")));
            int pageNumber = 0;
            if (request.getParameter("page") ==null){
                pageNumber = 1;
            } else {
                pageNumber = Integer.parseInt(request.getParameter("page"));

            }
            request.setAttribute("pageNumber", pageNumber);
            List<Integer> pages = initPagesNumber(comments);
            if (pageNumber > pages.size() || pageNumber <= 0){
                response.sendError(404);
            } else {
                List<Comment> commentInPage = setCommentOnPage(comments, pageNumber);
                request.setAttribute("lastPageNumber",pages.size());
                request.setAttribute("post", post);
                request.setAttribute("comments", commentInPage);
                request.setAttribute("pages",pages);
                request.getSession().setAttribute("url","/post?post-id="+request.getParameter("post-id")+"&page="+pageNumber);
                request.getRequestDispatcher("WEB-INF/post.jsp").forward(request, response);
            }
        } catch (EmptyResultDataAccessException e){
            response.sendError(404);
        }
    }

    private List<Comment> setCommentOnPage(List<Comment> comments, int page){
        List<Comment> commentList = new ArrayList<>();
        int startIndex = (page-1)*HOW_MANY_COMMENTS_IN_ONE_PAGE;
        for (int i = 0; i < 5 ; i++) {
            if (startIndex + i < comments.size()) {
                commentList.add(comments.get(startIndex + i));
            }
        }
        return commentList;
    }

    private List<Integer> initPagesNumber(List<Comment> comments){
        int counter = 1;
        int size = comments.size();
        List<Integer> pages = new ArrayList<>();
        pages.add(counter);
        size = size - HOW_MANY_COMMENTS_IN_ONE_PAGE;
        while (size > 0){
            counter++;
            pages.add(counter);
            size = size - HOW_MANY_COMMENTS_IN_ONE_PAGE;
        }
        return pages;
    }
}
