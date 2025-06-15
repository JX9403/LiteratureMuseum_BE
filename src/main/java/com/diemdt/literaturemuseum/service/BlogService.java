package com.diemdt.literaturemuseum.service;

import com.diemdt.literaturemuseum.dto.BlogDTO;
import com.diemdt.literaturemuseum.dto.BlogDTO;
import com.diemdt.literaturemuseum.dto.BlogDTO;
import com.diemdt.literaturemuseum.dto.BlogDTO;
import com.diemdt.literaturemuseum.entity.*;
import com.diemdt.literaturemuseum.entity.User;
import com.diemdt.literaturemuseum.exception.ResourceNotFoundException;
import com.diemdt.literaturemuseum.mapper.BlogMapper;
import com.diemdt.literaturemuseum.repository.FileRepo;
import com.diemdt.literaturemuseum.repository.UserRepository;
import com.diemdt.literaturemuseum.repository.BlogRepository;
import com.diemdt.literaturemuseum.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final BlogMapper blogMapper;
    private final FileRepo fileRepo;

    @Transactional
    public BlogDTO createBlog ( BlogDTO blogDTO ){
        Blog blog = blogMapper.toEntity(blogDTO);
        blog.setView(0);
        blog.setStatus(Blog.Status.PENDING);
        User user = userRepository.findById(blogDTO.getUserId())
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));
        blog.setUser(user);

        Blog savedBlog = blogRepository.save(blog);
        return blogMapper.toDTO(savedBlog);
    }

    @Transactional
    public BlogDTO updateBlog ( Long id , BlogDTO blogDTO) {
        Blog existingBlog = blogRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Blog not found"));
        existingBlog.setContent(blogDTO.getContent());
        existingBlog.setName(blogDTO.getName());
        existingBlog.setStatus(blogDTO.getStatus());
        User user = userRepository.findById(blogDTO.getUserId())
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));
        existingBlog.setUser(user);

        
        Blog updatedBlog = blogRepository.save(existingBlog);

        return blogMapper.toDTO(updatedBlog);
    }

    @Transactional
    public void deleteBlog (Long id){
        if(!blogRepository.existsById(id)){
            throw new ResourceNotFoundException("Blog not found");
        }

        blogRepository.deleteById(id);
    }

    @Transactional
    public BlogDTO getBlog(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found"));

        blog.setView(blog.getView() + 1);

        BlogDTO dto = blogMapper.toDTO(blog);

        List<File> files = fileRepo.findByTargetTypeAndTargetId(File.Type.BLOG, blog.getId());
        dto.setFiles(files);

        return dto;
    }


    public Page<BlogDTO> getAllBlogs(Pageable pageable, String search, Long userId) {
        Page<Blog> blogs;

        boolean hasSearch = search != null && !search.isBlank();
        boolean hasUserId = userId != null;

        if (hasUserId && hasSearch) {
            blogs = blogRepository.findByUserIdAndNameContainingIgnoreCase(pageable, userId, search);
        } else if (hasUserId) {
            blogs = blogRepository.findByUserId(pageable, userId);
        } else if (hasSearch) {
            blogs = blogRepository.findByNameContainingIgnoreCase(pageable, search);
        } else {
            blogs = blogRepository.findAll(pageable);
        }

        return blogs.map(blog -> {
            BlogDTO dto = blogMapper.toDTO(blog);
            List<File> files = fileRepo.findByTargetTypeAndTargetId(File.Type.BLOG, blog.getId());
            dto.setFiles(files);
            return dto;
        });
    }


    public Page<BlogDTO> getAllBlogsByUser (Pageable pageable, String userId){
        Page<Blog> blogs;
        if (userId == null ||userId.isEmpty()  ) {
            throw new RuntimeException("User id is required");
        } else {
            try {
                Long id = Long.parseLong(userId);
                User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
                blogs = blogRepository.findByUserId(pageable, id);
            } catch (NumberFormatException e) {
                throw new RuntimeException("User ID must be a valid number");
            }
        }
        return blogs.map(blog -> {
            BlogDTO dto = blogMapper.toDTO(blog);

            List<File> files = fileRepo.findByTargetTypeAndTargetId(File.Type.BLOG, blog.getId());
            dto.setFiles(files);

            return dto;
        });
    }
}
