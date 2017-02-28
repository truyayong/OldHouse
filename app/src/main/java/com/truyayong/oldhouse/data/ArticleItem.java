package com.truyayong.oldhouse.data;

import cn.bmob.v3.BmobObject;

/**
 * Created by alley_qiu on 2017/2/28.
 */

public class ArticleItem extends BmobObject {
    /**
     * 所属文章
     */
    Article article;

    /**
     * 所属文章标题
     */
    String title;

    /**
     * 节点内容
     */
    String content;

    /**
     * 作者
     */
    User author;


    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
