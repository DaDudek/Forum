package forum.model;

import java.sql.Timestamp;
import java.util.Objects;

public class CommentVote {
    private int commentVoteId;
    private int commentId;
    private int userId;
    private Timestamp date;
    private boolean isPositive;
    private VoteType voteType;

    public int getCommentVoteId() {
        return commentVoteId;
    }

    public void setCommentVoteId(int commentVoteId) {
        this.commentVoteId = commentVoteId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
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
        CommentVote that = (CommentVote) o;
        return commentVoteId == that.commentVoteId &&
                commentId == that.commentId &&
                userId == that.userId &&
                isPositive == that.isPositive &&
                Objects.equals(date, that.date) &&
                voteType == that.voteType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentVoteId, commentId, userId, date, isPositive, voteType);
    }

    @Override
    public String toString() {
        return "CommentVote{" +
                "commentVoteId=" + commentVoteId +
                ", commentId=" + commentId +
                ", userId=" + userId +
                ", date=" + date +
                ", isPositive=" + isPositive +
                ", voteType=" + voteType +
                '}';
    }
}
