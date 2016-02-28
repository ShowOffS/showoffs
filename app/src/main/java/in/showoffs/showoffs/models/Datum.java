
package in.showoffs.showoffs.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Generated("org.jsonschema2pojo")
public class Datum {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("full_picture")
    @Expose
    private String fullPicture;
    @SerializedName("story")
    @Expose
    private String story;
    @SerializedName("place")
    @Expose
    private Place place;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("likes")
    @Expose
    private Likes likes;
    @SerializedName("comments")
    @Expose
    private Comments comments;
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Datum() {
    }

    /**
     * 
     * @param id
     * @param message
     * @param story
     * @param fullPicture
     * @param name
     * @param likes
     * @param link
     * @param type
     * @param place
     * @param comments
     */
    public Datum(String message, String fullPicture, String story, Place place, String link, String type, String id, Likes likes, Comments comments, String name) {
        this.message = message;
        this.fullPicture = fullPicture;
        this.story = story;
        this.place = place;
        this.link = link;
        this.type = type;
        this.id = id;
        this.likes = likes;
        this.comments = comments;
        this.name = name;
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

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(message).append(fullPicture).append(story).append(place).append(link).append(type).append(id).append(likes).append(comments).append(name).toHashCode();
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
        return new EqualsBuilder().append(message, rhs.message).append(fullPicture, rhs.fullPicture).append(story, rhs.story).append(place, rhs.place).append(link, rhs.link).append(type, rhs.type).append(id, rhs.id).append(likes, rhs.likes).append(comments, rhs.comments).append(name, rhs.name).isEquals();
    }

}
