package forum.logic;

import forum.model.Comment;
import forum.model.Post;
import forum.model.User;
import forum.service.VoteService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class PaginationHandler<T> {

    public static final int HOW_MANY_PUBLICATION_IN_ONE_PAGE = 5;


    public List<T> setPublicationOnPage(List<T> publication, int page){
        List<T> publicationList = new ArrayList<>();
        int startIndex = (page-1)*HOW_MANY_PUBLICATION_IN_ONE_PAGE;
        for (int i = 0; i < 5 ; i++) {
            if (startIndex + i < publication.size()) {
                publicationList.add(publication.get(startIndex + i));
            }
        }
        return publicationList;
    }

    public List<Integer> setPagesList(List<T> publications){
        int counter = 1;
        int size = publications.size();
        List<Integer> pages = new ArrayList<>();
        pages.add(counter);
        size = size - HOW_MANY_PUBLICATION_IN_ONE_PAGE;
        while (size > 0){
            counter++;
            pages.add(counter);
            size = size - HOW_MANY_PUBLICATION_IN_ONE_PAGE;
        }
        return pages;
    }

    public int initPageNumber(String pageParameter){
        if (pageParameter ==null){
            return 1;
        } else {
            return Integer.parseInt(pageParameter);
        }
    }

}
