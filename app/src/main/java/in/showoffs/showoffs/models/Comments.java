
package in.showoffs.showoffs.models;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Generated("org.jsonschema2pojo")
public class Comments {

    @SerializedName("data")
    @Expose
    private List<Object> data = new ArrayList<Object>();
    @SerializedName("summary")
    @Expose
    private Summary_ summary;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Comments() {
    }

    /**
     * 
     * @param summary
     * @param data
     */
    public Comments(List<Object> data, Summary_ summary) {
        this.data = data;
        this.summary = summary;
    }

    /**
     * 
     * @return
     *     The data
     */
    public List<Object> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<Object> data) {
        this.data = data;
    }

    /**
     * 
     * @return
     *     The summary
     */
    public Summary_ getSummary() {
        return summary;
    }

    /**
     * 
     * @param summary
     *     The summary
     */
    public void setSummary(Summary_ summary) {
        this.summary = summary;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(data).append(summary).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Comments) == false) {
            return false;
        }
        Comments rhs = ((Comments) other);
        return new EqualsBuilder().append(data, rhs.data).append(summary, rhs.summary).isEquals();
    }

}
