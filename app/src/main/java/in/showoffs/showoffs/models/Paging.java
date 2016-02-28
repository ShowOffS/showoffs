
package in.showoffs.showoffs.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Generated("org.jsonschema2pojo")
public class Paging {

    @SerializedName("cursors")
    @Expose
    private Cursors cursors;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Paging() {
    }

    /**
     * 
     * @param cursors
     */
    public Paging(Cursors cursors) {
        this.cursors = cursors;
    }

    /**
     * 
     * @return
     *     The cursors
     */
    public Cursors getCursors() {
        return cursors;
    }

    /**
     * 
     * @param cursors
     *     The cursors
     */
    public void setCursors(Cursors cursors) {
        this.cursors = cursors;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(cursors).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Paging) == false) {
            return false;
        }
        Paging rhs = ((Paging) other);
        return new EqualsBuilder().append(cursors, rhs.cursors).isEquals();
    }

}
