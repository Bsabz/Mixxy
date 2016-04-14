package www.purple.mixxy.models;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

@Entity
@Index
public class Article {
    
    @Id
    public Long id;

    public String title;
    
    public Date postedAt;
    
    @Unindex
    public String content;
    
    public List<Long> authorIds;
    
    public Article() { /* needed by Objectify */ }
    
    public Article(final User author, final String title, final String content) {
        this.authorIds = Lists.newArrayList(author.id);
        this.title = title;
        this.content = content;
        this.postedAt = new Date();
    }
 
}