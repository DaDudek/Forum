package forum.model;

import java.sql.Timestamp;
import java.util.Objects;

public class Post {
    private int postId;
    private User user;
    private String title;
    private String description;
    private String message;
    private Timestamp date;
    private int positiveVote;
    private int negativeVote;

    public Post() {
    }

    public Post(Post post){
        this.postId = post.getPostId();
        this.user = new User(post.getUser());
        this.title = post.getTitle();
        this.description = post.getDescription();
        this.message = post.getMessage();
        this.date = new Timestamp(post.getDate().getTime());
        this.positiveVote = post.getPositiveVote();
        this.negativeVote = post.getNegativeVote();
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
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
        Post post = (Post) o;
        return postId == post.postId &&
                positiveVote == post.positiveVote &&
                negativeVote == post.negativeVote &&
                Objects.equals(user, post.user) &&
                Objects.equals(title, post.title) &&
                Objects.equals(description, post.description) &&
                Objects.equals(message, post.message) &&
                Objects.equals(date, post.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, user, title, description, message, date, positiveVote, negativeVote);
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", user=" + user +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", message='" + message + '\'' +
                ", date=" + date +
                ", positiveVote=" + positiveVote +
                ", negativeVote=" + negativeVote +
                '}';
    }
}
