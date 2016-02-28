
package in.showoffs.showoffs.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Generated("org.jsonschema2pojo")
public class Paging_ {

    @SerializedName("previous")
    @Expose
    private String previous;
    @SerializedName("next")
    @Expose
    private String next;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Paging_() {
    }

    /**
     * 
     * @param previous
     * @param next
     */
    public Paging_(String previous, String next) {
        this.previous = previous;
        this.next = next;
    }

    /**
     * 
     * @return
     *     The previous
     */
    public String getPrevious() {
        return previous;
    }

    /**
     * 
     * @param previous
     *     The previous
     */
    public void setPrevious(String previous) {
        this.previous = previous;
    }

    /**
     * 
     * @return
     *     The next
     */
    public String getNext() {
        return next;
    }

    /**
     * 
     * @param next
     *     The next
     */
    public void setNext(String next) {
        this.next = next;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(previous).append(next).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Paging_) == false) {
            return false;
        }
        Paging_ rhs = ((Paging_) other);
        return new EqualsBuilder().append(previous, rhs.previous).append(next, rhs.next).isEquals();
    }

}
