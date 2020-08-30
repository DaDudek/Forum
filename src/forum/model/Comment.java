package forum.model;

import java.sql.Timestamp;
import java.util.Objects;

public class Comment {
    private int commentId;
    private int postId;
    private int userId;
    private Timestamp date;
    private String message;
    private int positiveVote;
    private int negativeVote;

    public Comment() {
    }

    public Comment(Comment comment){
        this.commentId = comment.getCommentId();
        this.postId = comment.getPostId();
        this.userId = comment.getUserId();
        this.date = new Timestamp(comment.getDate().getTime());
        this.message = comment.getMessage();
        this.positiveVote = comment.getPositiveVote();
        this.negativeVote = comment.getNegativeVote();
    }


    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPositiveVote() {
        return positiveVote;
    }

    public void setPositiveVote(int positiveVote) {
        this.positiveVote = positiveVote;
    }

    public int getNegativeVote() {
        return negativeVote;
    }

    public void setNegativeVote(int negativeVote) {
        this.negativeVote = negativeVote;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return commentId == comment.commentId &&
                postId == comment.postId &&
                userId == comment.userId &&
                positiveVote == comment.positiveVote &&
                negativeVote == comment.negativeVote &&
                Objects.equals(date, comment.date) &&
                Objects.equals(message, comment.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, postId, userId, date, message, positiveVote, negativeVote);
    }

    @Override
    public String
    toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", postId=" + postId +
                ", userId=" + userId +
                ", date=" + date +
                ", message='" + message + '\'' +
                ", positiveVote=" + positiveVote +
                ", negativeVote=" + negativeVote +
                '}';
    }
}
