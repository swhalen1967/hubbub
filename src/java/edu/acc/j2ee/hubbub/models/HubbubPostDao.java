package edu.acc.j2ee.hubbub.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HubbubPostDao {
    private final List<HubbubPost> REPOSITORY = new ArrayList<>();
    
    public String addPost(String content, HubbubUser user) {
        if (content == null || content.length() < 1)
            return "There is no content to post";
        if (content.length() > 200)
            return "Hubbub&trade; posts are limited to 200 characters";
        content = content.replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("'", "&apos;")
                .replace("\"", "&quo;");
        HubbubPost post = 
                new HubbubPost(content, LocalDateTime.now().toString() , user);
        synchronized (REPOSITORY) {
            if (!REPOSITORY.add(post))
                return "Unable to add post to repository";
        }
        return null;
    }
    
    public List<HubbubPost> getAllPostsByPostDateDescending() {
        List<HubbubPost> clone = new ArrayList<>(REPOSITORY);
        Collections.sort(clone, 
            (p1,p2) -> (p2.getPostDate().compareTo(p1.getPostDate())));
        return clone;
    }
    
    public synchronized boolean addPost(HubbubPost post) {
        return REPOSITORY.add(post);
    }
    
    public synchronized boolean removePost(HubbubPost post) {
        return REPOSITORY.remove(post);
    }
}
