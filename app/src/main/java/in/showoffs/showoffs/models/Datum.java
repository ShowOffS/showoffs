
package in.showoffs.showoffs.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Generated("org.jsonschema2pojo")
public class Datum {

    @SerializedName("full_picture")
    @Expose
    private String fullPicture;
    @SerializedName("story")
    @Expose
    private String story;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("updated_time")
    @Expose
    private String updatedTime;
    @SerializedName("privacy")
    @Expose
    private Privacy privacy;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("likes")
    @Expose
    private Likes likes;
    @SerializedName("comments")
    @Expose
    private Comments comments;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("place")
    @Expose
    private Place place;
    @SerializedName("application")
    @Expose
    private Application application;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Datum() {
    }

    /**
     * 
     * @param message
     * @param id
     * @param story
     * @param application
     * @param fullPicture
     * @param likes
     * @param name
     * @param link
     * @param privacy
     * @param place
     * @param type
     * @param comments
     * @param updatedTime
     */
    public Datum(String fullPicture, String story, String link, String type, String name, String updatedTime, Privacy privacy, String id, Likes likes, Comments comments, String message, Place place, Application application) {
        this.fullPicture = fullPicture;
        this.story = story;
        this.link = link;
        this.type = type;
        this.name = name;
        this.updatedTime = updatedTime;
        this.privacy = privacy;
        this.id = id;
        this.likes = likes;
        this.comments = comments;
        this.message = message;
        this.place = place;
        this.application = application;
    }

    /**
     * 
     * @return
     *     The fullPicture
     */
    public String getFullPicture() {
        return fullPicture;
    }

    /**
     * 
     * @param fullPicture
     *     The full_picture
     */
    public void setFullPicture(String fullPicture) {
        this.fullPicture = fullPicture;
    }

    /**
     * 
     * @return
     *     The story
     */
    public String getStory() {
        return story;
    }

    /**
     * 
     * @param story
     *     The story
     */
    public void setStory(String story) {
        this.story = story;
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
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
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
     *     The updatedTime
     */
    public String getUpdatedTime() {
        return updatedTime;
    }

    /**
     * 
     * @param updatedTime
     *     The updated_time
     */
    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    /**
     * 
     * @return
     *     The privacy
     */
    public Privacy getPrivacy() {
        return privacy;
    }

    /**
     * 
     * @param privacy
     *     The privacy
     */
    public void setPrivacy(Privacy privacy) {
        this.privacy = privacy;
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

    /**
     * 
     * @return
     *     The likes
     */
    public Likes getLikes() {
        return likes;
    }

    /**
     * 
     * @param likes
     *     The likes
     */
    public void setLikes(Likes likes) {
        this.likes = likes;
    }

    /**
     * 
     * @return
     *     The comments
     */
    public Comments getComments() {
        return comments;
    }

    /**
     * 
     * @param comments
     *     The comments
     */
    public void setComments(Comments comments) {
        this.comments = comments;
    }

    /**
     * 
     * @return
     *     The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 
     * @return
     *     The place
     */
    public Place getPlace() {
        return place;
    }

    /**
     * 
     * @param place
     *     The place
     */
    public void setPlace(Place place) {
        this.place = place;
    }

    /**
     * 
     * @return
     *     The application
     */
    public Application getApplication() {
        return application;
    }

    /**
     * 
     * @param application
     *     The application
     */
    public void setApplication(Application application) {
        this.application = application;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(fullPicture).append(story).append(link).append(type).append(name).append(updatedTime).append(privacy).append(id).append(likes).append(comments).append(message).append(place).append(application).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Datum) == false) {
            return false;
        }
        Datum rhs = ((Datum) other);
        return new EqualsBuilder().append(fullPicture, rhs.fullPicture).append(story, rhs.story).append(link, rhs.link).append(type, rhs.type).append(name, rhs.name).append(updatedTime, rhs.updatedTime).append(privacy, rhs.privacy).append(id, rhs.id).append(likes, rhs.likes).append(comments, rhs.comments).append(message, rhs.message).append(place, rhs.place).append(application, rhs.application).isEquals();
    }

}
