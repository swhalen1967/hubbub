
package edu.acc.j2ee.hubbub.models;

public class HubbubPost implements java.io.Serializable {
    private String content;
    private String postDate;
    private HubbubUser user;

    public HubbubPost() {
    }

    public HubbubPost(String content, String postDate, HubbubUser user) {
        this.content = content;
        this.postDate = postDate;
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public HubbubUser getUser() {
        return user;
    }

    public void setUser(HubbubUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "HubbubPost{" + "content=" + content + ", postDate=" + 
                postDate + ", user=" + user + '}';
    }
    
}
