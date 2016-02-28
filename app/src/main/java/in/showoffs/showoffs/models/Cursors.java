
package in.showoffs.showoffs.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Generated("org.jsonschema2pojo")
public class Cursors {

    @SerializedName("after")
    @Expose
    private String after;
    @SerializedName("before")
    @Expose
    private String before;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Cursors() {
    }

    /**
     * 
     * @param after
     * @param before
     */
    public Cursors(String after, String before) {
        this.after = after;
        this.before = before;
    }

    /**
     * 
     * @return
     *     The after
     */
    public String getAfter() {
        return after;
    }

    /**
     * 
     * @param after
     *     The after
     */
    public void setAfter(String after) {
        this.after = after;
    }

    /**
     * 
     * @return
     *     The before
     */
    public String getBefore() {
        return before;
    }

    /**
     * 
     * @param before
     *     The before
     */
    public void setBefore(String before) {
        this.before = before;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(after).append(before).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Cursors) == false) {
            return false;
        }
        Cursors rhs = ((Cursors) other);
        return new EqualsBuilder().append(after, rhs.after).append(before, rhs.before).isEquals();
    }

}
