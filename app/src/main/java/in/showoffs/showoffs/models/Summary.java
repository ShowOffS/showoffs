
package in.showoffs.showoffs.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Generated("org.jsonschema2pojo")
public class Summary {

    @SerializedName("total_count")
    @Expose
    private long totalCount;
    @SerializedName("can_like")
    @Expose
    private boolean canLike;
    @SerializedName("has_liked")
    @Expose
    private boolean hasLiked;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Summary() {
    }

    /**
     * 
     * @param totalCount
     * @param canLike
     * @param hasLiked
     */
    public Summary(long totalCount, boolean canLike, boolean hasLiked) {
        this.totalCount = totalCount;
        this.canLike = canLike;
        this.hasLiked = hasLiked;
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
     *     The canLike
     */
    public boolean isCanLike() {
        return canLike;
    }

    /**
     * 
     * @param canLike
     *     The can_like
     */
    public void setCanLike(boolean canLike) {
        this.canLike = canLike;
    }

    /**
     * 
     * @return
     *     The hasLiked
     */
    public boolean isHasLiked() {
        return hasLiked;
    }

    /**
     * 
     * @param hasLiked
     *     The has_liked
     */
    public void setHasLiked(boolean hasLiked) {
        this.hasLiked = hasLiked;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(totalCount).append(canLike).append(hasLiked).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Summary) == false) {
            return false;
        }
        Summary rhs = ((Summary) other);
        return new EqualsBuilder().append(totalCount, rhs.totalCount).append(canLike, rhs.canLike).append(hasLiked, rhs.hasLiked).isEquals();
    }

}
