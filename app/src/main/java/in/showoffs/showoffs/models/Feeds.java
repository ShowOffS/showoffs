
package in.showoffs.showoffs.models;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Generated("org.jsonschema2pojo")
public class Feeds {

    @SerializedName("data")
    @Expose
    private List<Datum> data = new ArrayList<Datum>();
    @SerializedName("paging")
    @Expose
    private Paging_ paging;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Feeds() {
    }

    /**
     * 
     * @param data
     * @param paging
     */
    public Feeds(List<Datum> data, Paging_ paging) {
        this.data = data;
        this.paging = paging;
    }

    /**
     * 
     * @return
     *     The data
     */
    public List<Datum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<Datum> data) {
        this.data = data;
    }

    /**
     * 
     * @return
     *     The paging
     */
    public Paging_ getPaging() {
        return paging;
    }

    /**
     * 
     * @param paging
     *     The paging
     */
    public void setPaging(Paging_ paging) {
        this.paging = paging;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(data).append(paging).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Feeds) == false) {
            return false;
        }
        Feeds rhs = ((Feeds) other);
        return new EqualsBuilder().append(data, rhs.data).append(paging, rhs.paging).isEquals();
    }

}
