package com.diemdt.literaturemuseum.service;

import com.diemdt.literaturemuseum.dto.AuthorDTO;
import com.diemdt.literaturemuseum.dto.AuthorDTO;
import com.diemdt.literaturemuseum.entity.Author;
import com.diemdt.literaturemuseum.entity.Author;
import com.diemdt.literaturemuseum.entity.Award;
import com.diemdt.literaturemuseum.entity.File;
import com.diemdt.literaturemuseum.exception.ResourceNotFoundException;
import com.diemdt.literaturemuseum.mapper.AuthorMapper;
import com.diemdt.literaturemuseum.repository.AuthorRepository;
import com.diemdt.literaturemuseum.repository.AuthorRepository;
import com.diemdt.literaturemuseum.repository.AwardRepository;
import com.diemdt.literaturemuseum.repository.FileRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AwardRepository awardRepository;
    private final AuthorMapper authorMapper;

    private final FileRepo fileRepo;

    @Transactional
    public AuthorDTO createAuthor ( AuthorDTO authorDTO ){
        Author author = authorMapper.toEntity(authorDTO);

        Award award = awardRepository.findById(authorDTO.getAwardId())
                .orElseThrow(()-> new ResourceNotFoundException("Award not found"));
        author.setAward(award);

        Author savedAuthor = authorRepository.save(author);
        return authorMapper.toDTO(savedAuthor);
    }

    @Transactional
    public AuthorDTO updateAuthor ( Long id , AuthorDTO authorDTO) {
        Author existingAuthor = authorRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Author not found"));
        existingAuthor.setContent(authorDTO.getContent());
        existingAuthor.setName(authorDTO.getName());
        existingAuthor.setBirthYear(authorDTO.getBirthYear());
        existingAuthor.setDeathYear(authorDTO.getDeathYear());
        existingAuthor.setType(authorDTO.getType());

        Award award = awardRepository.findById(authorDTO.getAwardId())
                .orElseThrow(()-> new ResourceNotFoundException("Award not found"));
        existingAuthor.setAward(award);

        Author updatedAuthor = authorRepository.save(existingAuthor);

        return authorMapper.toDTO(updatedAuthor);
    }

    @Transactional
    public void deleteAuthor (Long id){
        if(!authorRepository.existsById(id)){
            throw new ResourceNotFoundException("Author not found");
        }

        authorRepository.deleteById(id);
    }

    @Transactional
    public AuthorDTO getAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));

        AuthorDTO dto = authorMapper.toDTO(author);

        List<File> files = fileRepo.findByTargetTypeAndTargetId(File.Type.AUTHOR, author.getId());
        dto.setFiles(files);

        return dto;
    }


    public Page<AuthorDTO> getAllAuthors (Pageable pageable, String search){
        Page<Author> authors;
        if (search == null || search.isBlank()) {
             authors =  authorRepository.findAll(pageable);
        } else {
            authors    = authorRepository.findByNameContainingIgnoreCase(pageable, search);
        }
        return authors.map(author -> {
            AuthorDTO dto = authorMapper.toDTO(author);

            List<File> files = fileRepo.findByTargetTypeAndTargetId(File.Type.AUTHOR, author.getId());
            dto.setFiles(files);

            return dto;
        });
    }

    public Page<AuthorDTO> getAllAuthorsByAward (Pageable pageable, String awardId){

        Page<Author> authors;

        if (awardId == null ||awardId.isEmpty()  ) {
            throw new RuntimeException("Award id is required");
        } else {
            try {
                Long id = Long.parseLong(awardId);
                Award award = awardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Award not found"));
                authors = authorRepository.findByAwardId(pageable, id);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Award ID must be a valid number");
            }
        }
        return authors.map(author -> {
            AuthorDTO dto = authorMapper.toDTO(author);

            List<File> files = fileRepo.findByTargetTypeAndTargetId(File.Type.AUTHOR, author.getId());
            dto.setFiles(files);

            return dto;
        });
    }
}
