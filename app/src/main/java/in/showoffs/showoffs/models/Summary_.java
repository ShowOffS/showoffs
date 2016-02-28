
package in.showoffs.showoffs.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Generated("org.jsonschema2pojo")
public class Summary_ {

    @SerializedName("order")
    @Expose
    private String order;
    @SerializedName("total_count")
    @Expose
    private long totalCount;
    @SerializedName("can_comment")
    @Expose
    private boolean canComment;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Summary_() {
    }

    /**
     * 
     * @param order
     * @param canComment
     * @param totalCount
     */
    public Summary_(String order, long totalCount, boolean canComment) {
        this.order = order;
        this.totalCount = totalCount;
        this.canComment = canComment;
    }

    /**
     * 
     * @return
     *     The order
     */
    public String getOrder() {
        return order;
    }

    /**
     * 
     * @param order
     *     The order
     */
    public void setOrder(String order) {
        this.order = order;
    }

    /**
     * 
     * @return
     *     The totalCount
     */
    public long getTotalCount() {
        return totalCount;
    }

    /**
     * 
     * @param totalCount
     *     The total_count
     */
    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 
     * @return
     *     The canComment
     */
    public boolean isCanComment() {
        return canComment;
    }

    /**
     * 
     * @param canComment
     *     The can_comment
     */
    public void setCanComment(boolean canComment) {
        this.canComment = canComment;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(order).append(totalCount).append(canComment).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Summary_) == false) {
            return false;
        }
        Summary_ rhs = ((Summary_) other);
        return new EqualsBuilder().append(order, rhs.order).append(totalCount, rhs.totalCount).append(canComment, rhs.canComment).isEquals();
    }

}
