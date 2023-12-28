package com.example.blogsecurity.Repository;

import com.example.blogsecurity.Model.Blog;
import com.example.blogsecurity.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog,Integer> {
    Blog findBlogById(Integer id);

    List<Blog> findAllByUser(User user);

    Blog findBlogByTitle(String title);
}
