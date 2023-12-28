package com.example.blogsecurity.Service;

import com.example.blogsecurity.Exception.ApiException;
import com.example.blogsecurity.Model.Blog;
import com.example.blogsecurity.Model.User;
import com.example.blogsecurity.Repository.AuthRepository;
import com.example.blogsecurity.Repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final AuthRepository authRepository;
    public List<Blog> getAllBlogs(){
        return blogRepository.findAll();
    }


    public List getMyBlogs(Integer auth) {
        User user=authRepository.findUserById(auth);

        List<Blog> blogs=blogRepository.findAllByUser(user);

        return blogs;
    }

    public void addToMyBlog(Blog blog,Integer auth){
        User user=authRepository.findUserById(auth);

        blog.setUser(user);
        blogRepository.save(blog);
    }
    public void updateBlog(Integer id , Blog newBlog , Integer auth){
        Blog oldBlog=blogRepository.findBlogById(id);
        User user=authRepository.findUserById(auth);
        if (oldBlog==null){
            throw new ApiException("Blog not found");
        }else if(oldBlog.getUser().getId()!=auth){
            throw new ApiException("Sorry , You do not have the authority to update this Blog!");
        }

        newBlog.setId(id);
        newBlog.setUser(user);

        blogRepository.save(newBlog);
    }

    public void deleteBlog(Integer id, Integer auth){
        Blog blog=blogRepository.findBlogById(id);
        if (blog==null){
            throw new ApiException("Blog not found");
        }else if(blog.getUser().getId()!=auth){
            throw new ApiException("Sorry , You do not have the authority to delete this Blog!");
        }

        blogRepository.delete(blog);
    }

    public Blog findBlogByTitle(String title){
        Blog blog=blogRepository.findBlogByTitle(title);
        if(blog==null){
            throw new ApiException("blog not found");
        }
        return blog;
    }
}
