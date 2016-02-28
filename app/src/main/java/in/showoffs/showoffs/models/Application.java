
package in.showoffs.showoffs.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Generated("org.jsonschema2pojo")
public class Application {

    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("namespace")
    @Expose
    private String namespace;
    @SerializedName("id")
    @Expose
    private String id;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Application() {
    }

    /**
     * 
     * @param id
     * @param name
     * @param link
     * @param namespace
     */
    public Application(String link, String name, String namespace, String id) {
        this.link = link;
        this.name = name;
        this.namespace = namespace;
        this.id = id;
    }

    /**
     * 
     * @return
     *     The link
     */
    public String getLink() {
        return link;
    }

    /**
     * 
     * @param link
     *     The link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The namespace
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * 
     * @param namespace
     *     The namespace
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(link).append(name).append(namespace).append(id).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Application) == false) {
            return false;
        }
        Application rhs = ((Application) other);
        return new EqualsBuilder().append(link, rhs.link).append(name, rhs.name).append(namespace, rhs.namespace).append(id, rhs.id).isEquals();
    }

}
