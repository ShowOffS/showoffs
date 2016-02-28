
package in.showoffs.showoffs.models;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Generated("org.jsonschema2pojo")
public class Likes {

    @SerializedName("data")
    @Expose
    private List<Datum_> data = new ArrayList<Datum_>();
    @SerializedName("paging")
    @Expose
    private Paging paging;
    @SerializedName("summary")
    @Expose
    private Summary summary;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Likes() {
    }

    /**
     * 
     * @param summary
     * @param data
     * @param paging
     */
    public Likes(List<Datum_> data, Paging paging, Summary summary) {
        this.data = data;
        this.paging = paging;
        this.summary = summary;
    }

    /**
     * 
     * @return
     *     The data
     */
    public List<Datum_> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<Datum_> data) {
        this.data = data;
    }

    /**
     * 
     * @return
     *     The paging
     */
    public Paging getPaging() {
        return paging;
    }

    /**
     * 
     * @param paging
     *     The paging
     */
    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    /**
     * 
     * @return
     *     The summary
     */
    public Summary getSummary() {
        return summary;
    }

    /**
     * 
     * @param summary
     *     The summary
     */
    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(data).append(paging).append(summary).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Likes) == false) {
            return false;
        }
        Likes rhs = ((Likes) other);
        return new EqualsBuilder().append(data, rhs.data).append(paging, rhs.paging).append(summary, rhs.summary).isEquals();
    }

}
