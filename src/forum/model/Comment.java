package forum.model;

import java.sql.Timestamp;
import java.util.Objects;

public class Comment {
    private int commentId;
    private Post post;
    private User user;
    private Timestamp date;
    private boolean isPositive;

    public Comment() {
    }

    public Comment(Comment comment){
        this.commentId = comment.getCommentId();
        this.post = new Post(comment.getPost());
        this.user = new User(comment.getUser());
        this.date = new Timestamp(comment.getDate().getTime());
        this.isPositive = comment.isPositive();

    }


    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public void setPositive(boolean positive) {
        isPositive = positive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return commentId == comment.commentId &&
                isPositive == comment.isPositive &&
                Objects.equals(post, comment.post) &&
                Objects.equals(user, comment.user) &&
                Objects.equals(date, comment.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, post, user, date, isPositive);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", post=" + post +
                ", user=" + user +
                ", date=" + date +
                ", isPositive=" + isPositive +
                '}';
    }
}
