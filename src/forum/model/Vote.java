package forum.model;

import java.sql.Timestamp;
import java.util.Objects;

public class Vote {
    private int voteId;
    private Post post;
    private User user;
    private Timestamp date;
    private boolean isPositive;

    public Vote() {
    }

    public Vote(Vote vote) {
        this.voteId = vote.getVoteId();
        System.out.println("test1");
        this.post = new Post(vote.getPost());
        System.out.println("test1");
        this.user = new User(vote.getUser());
        System.out.println("test1");
        this.date = new Timestamp(vote.getDate().getTime());
        this.isPositive = vote.isPositive();
    }

    public Vote(int postId, int userId, boolean isPositive, Timestamp timestamp){
        this.post = new Post(postId);
        this.user = new User(userId);
        this.isPositive = isPositive;
        this.date = timestamp;
    }

    public int getVoteId() {
        return voteId;
    }

    public void setVoteId(int voteId) {
        this.voteId = voteId;
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
        Vote vote = (Vote) o;
        return voteId == vote.voteId &&
                isPositive == vote.isPositive &&
                Objects.equals(post, vote.post) &&
                Objects.equals(user, vote.user) &&
                Objects.equals(date, vote.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voteId, post, user, date, isPositive);
    }

    @Override
    public String toString() {
        return "Vote{" +
                "voteId=" + voteId +
                ", post=" + post +
                ", user=" + user +
                ", date=" + date +
                ", isPositive=" + isPositive +
                '}';
    }
}
