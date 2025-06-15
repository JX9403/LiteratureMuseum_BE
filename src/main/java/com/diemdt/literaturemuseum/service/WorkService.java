package com.diemdt.literaturemuseum.service;

import com.diemdt.literaturemuseum.dto.ArtifactDTO;
import com.diemdt.literaturemuseum.dto.AuthorDTO;
import com.diemdt.literaturemuseum.dto.WorkDTO;
import com.diemdt.literaturemuseum.entity.Author;
import com.diemdt.literaturemuseum.entity.Award;
import com.diemdt.literaturemuseum.entity.File;
import com.diemdt.literaturemuseum.entity.Work;
import com.diemdt.literaturemuseum.exception.ResourceNotFoundException;
import com.diemdt.literaturemuseum.mapper.WorkMapper;
import com.diemdt.literaturemuseum.repository.AuthorRepository;
import com.diemdt.literaturemuseum.repository.FileRepo;
import com.diemdt.literaturemuseum.repository.WorkRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkService {
    private final WorkRepository workRepository;
    private final AuthorRepository authorRepository;
    private final WorkMapper workMapper;
    private final FileRepo fileRepo;

    @Transactional
    public WorkDTO createWork ( WorkDTO workDTO ){
        Work work = workMapper.toEntity(workDTO);
        work.setView(0);
        Author author = authorRepository.findById(workDTO.getAuthorId())
                .orElseThrow(()-> new ResourceNotFoundException("Author not found"));
        work.setAuthor(author);
        
        Work savedWork = workRepository.save(work);
        return workMapper.toDTO(savedWork);
    }

    @Transactional
    public WorkDTO updateWork ( Long id , WorkDTO workDTO) {
        Work existingWork = workRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Work not found"));
        existingWork.setContent(workDTO.getContent());
        existingWork.setName(workDTO.getName());
        Author author = authorRepository.findById(workDTO.getAuthorId())
                .orElseThrow(()-> new ResourceNotFoundException("Author not found"));
        existingWork.setAuthor(author);
        
        Work updatedWork = workRepository.save(existingWork);

        return workMapper.toDTO(updatedWork);
    }

    @Transactional
    public void deleteWork (Long id){
        if(!workRepository.existsById(id)){
            throw new ResourceNotFoundException("Work not found");
        }

        workRepository.deleteById(id);
    }

    @Transactional
    public WorkDTO getWork(Long id) {
        Work work = workRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Work not found"));

        work.setView(work.getView() + 1);
        WorkDTO dto = workMapper.toDTO(work);

        List<File> files = fileRepo.findByTargetTypeAndTargetId(File.Type.WORK, work.getId());
        dto.setFiles(files);

        return dto;
    }


    public Page<WorkDTO> getAllWorks(Pageable pageable, String search, Long authorId) {
        Page<Work> works;

        boolean hasSearch = search != null && !search.isBlank();
        boolean hasAuthor = authorId != null;

        if (hasSearch && hasAuthor) {
            works = workRepository.findByNameContainingIgnoreCaseAndAuthorId(search, authorId, pageable);
        } else if (hasSearch) {
            works = workRepository.findByNameContainingIgnoreCase(search, pageable);
        } else if (hasAuthor) {
            works = workRepository.findByAuthorId(authorId, pageable);
        } else {
            works = workRepository.findAll(pageable);
        }

        return works.map(work -> {
            WorkDTO dto = workMapper.toDTO(work);
            List<File> files = fileRepo.findByTargetTypeAndTargetId(File.Type.WORK, work.getId());
            dto.setFiles(files);
            return dto;
        });
    }


    public Page<WorkDTO> getAllWorksByAuthor (Pageable pageable, String authorId){

        Page<Work> works;

        if (authorId == null ||authorId.isEmpty()  ) {
            throw new RuntimeException("Author id is required");
        } else {
            try {
                Long id = Long.parseLong(authorId);
                Author author = authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author not found"));
                works = workRepository.findByAuthorId(id, pageable);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Author ID must be a valid number");
            }
        }
        return works.map(work -> {
            WorkDTO dto = workMapper.toDTO(work);

            List<File> files = fileRepo.findByTargetTypeAndTargetId(File.Type.WORK, work.getId());
            dto.setFiles(files);

            return dto;
        });
    }
}
