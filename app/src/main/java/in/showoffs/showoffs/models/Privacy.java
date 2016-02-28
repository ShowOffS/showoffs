
package in.showoffs.showoffs.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Generated("org.jsonschema2pojo")
public class Privacy {

    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("friends")
    @Expose
    private String friends;
    @SerializedName("allow")
    @Expose
    private String allow;
    @SerializedName("deny")
    @Expose
    private String deny;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Privacy() {
    }

    /**
     * 
     * @param friends
     * @param allow
     * @param deny
     * @param description
     * @param value
     */
    public Privacy(String value, String description, String friends, String allow, String deny) {
        this.value = value;
        this.description = description;
        this.friends = friends;
        this.allow = allow;
        this.deny = deny;
    }

    /**
     * 
     * @return
     *     The value
     */
    public String getValue() {
        return value;
    }

    /**
     * 
     * @param value
     *     The value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The friends
     */
    public String getFriends() {
        return friends;
    }

    /**
     * 
     * @param friends
     *     The friends
     */
    public void setFriends(String friends) {
        this.friends = friends;
    }

    /**
     * 
     * @return
     *     The allow
     */
    public String getAllow() {
        return allow;
    }

    /**
     * 
     * @param allow
     *     The allow
     */
    public void setAllow(String allow) {
        this.allow = allow;
    }

    /**
     * 
     * @return
     *     The deny
     */
    public String getDeny() {
        return deny;
    }

    /**
     * 
     * @param deny
     *     The deny
     */
    public void setDeny(String deny) {
        this.deny = deny;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(value).append(description).append(friends).append(allow).append(deny).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Privacy) == false) {
            return false;
        }
        Privacy rhs = ((Privacy) other);
        return new EqualsBuilder().append(value, rhs.value).append(description, rhs.description).append(friends, rhs.friends).append(allow, rhs.allow).append(deny, rhs.deny).isEquals();
    }

}
