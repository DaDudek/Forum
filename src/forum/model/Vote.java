package forum.model;

import java.sql.Timestamp;
import java.util.Objects;

public class Vote {
    private int voteId;
    private int postId;
    private int userId;
    private Timestamp date;
    private boolean isPositive;
    private VoteType voteType;

    public Vote() {
    }

    public Vote(Vote vote) {
        this.voteId = vote.getVoteId();
        this.postId = vote.getPostId();
        this.userId = vote.getUserId();
        this.date = new Timestamp(vote.getDate().getTime());
        this.isPositive = vote.isPositive();
        this.voteType = vote.getVoteType();

    }


    public int getVoteId() {
        return voteId;
    }

    public void setVoteId(int voteId) {
        this.voteId = voteId;
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

    public boolean isPositive() {
        return isPositive;
    }

    public void setPositive(boolean positive) {
        isPositive = positive;
    }

    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote = (Vote) o;
        return voteId == vote.voteId &&
                postId == vote.postId &&
                userId == vote.userId &&
                isPositive == vote.isPositive &&
                Objects.equals(date, vote.date) &&
                voteType == vote.voteType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(voteId, postId, userId, date, isPositive, voteType);
    }

    @Override
    public String toString() {
        return "Vote{" +
                "voteId=" + voteId +
                ", postId=" + postId +
                ", userId=" + userId +
                ", date=" + date +
                ", isPositive=" + isPositive +
                ", voteType=" + voteType +
                '}';
    }
}
