package com.diemdt.literaturemuseum.service;

import com.diemdt.literaturemuseum.dto.ExhibitDTO;
import com.diemdt.literaturemuseum.dto.ExhibitDTO;
import com.diemdt.literaturemuseum.dto.ExhibitDTO;
import com.diemdt.literaturemuseum.dto.ExhibitDTO;
import com.diemdt.literaturemuseum.entity.Exhibit;
import com.diemdt.literaturemuseum.entity.Exhibit;
import com.diemdt.literaturemuseum.entity.Exhibit;
import com.diemdt.literaturemuseum.entity.File;
import com.diemdt.literaturemuseum.exception.ResourceNotFoundException;
import com.diemdt.literaturemuseum.mapper.ExhibitMapper;
import com.diemdt.literaturemuseum.repository.ExhibitRepository;
import com.diemdt.literaturemuseum.repository.FileRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExhibitService {
    private final ExhibitRepository exhibitRepository;
    private final ExhibitMapper exhibitMapper;
    private final FileRepo fileRepo;

    @Transactional
    public ExhibitDTO createExhibit ( ExhibitDTO exhibitDTO ){
        Exhibit exhibit = exhibitMapper.toEntity(exhibitDTO);
        exhibit.setView(0);
        Exhibit savedExhibit = exhibitRepository.save(exhibit);
        return exhibitMapper.toDTO(savedExhibit);
    }

    @Transactional
    public ExhibitDTO updateExhibit ( Long id , ExhibitDTO exhibitDTO) {
        Exhibit existingExhibit = exhibitRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Exhibit not found"));

        existingExhibit.setContent(exhibitDTO.getContent());
        existingExhibit.setName(exhibitDTO.getName());
        existingExhibit.setType(exhibitDTO.getType());

        Exhibit updatedExhibit = exhibitRepository.save(existingExhibit);

        return exhibitMapper.toDTO(updatedExhibit);
    }

    @Transactional
    public void deleteExhibit (Long id){
        if(!exhibitRepository.existsById(id)){
            throw new ResourceNotFoundException("Exhibit not found");
        }

        exhibitRepository.deleteById(id);
    }

    @Transactional
    public ExhibitDTO getExhibit(Long id) {
        Exhibit exhibit = exhibitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exhibit not found"));

        exhibit.setView(exhibit.getView() + 1);
        ExhibitDTO dto = exhibitMapper.toDTO(exhibit);

        List<File> files = fileRepo.findByTargetTypeAndTargetId(File.Type.EXHIBIT, exhibit.getId());
        dto.setFiles(files);

        return dto;
    }

    public Page<ExhibitDTO> getAllExhibits(Pageable pageable, String search, String type) {
        Page<Exhibit> exhibits;

        boolean hasSearch = search != null && !search.isEmpty();
        boolean hasType = type != null && !type.isEmpty();

        if (hasSearch && hasType) {
            // tìm theo cả name và type
            exhibits = exhibitRepository.findByNameContainingIgnoreCaseAndType(pageable, search, Exhibit.Type.valueOf(type));
        } else if (hasSearch) {
            exhibits = exhibitRepository.findByNameContainingIgnoreCase(pageable, search);
        } else if (hasType) {
            exhibits = exhibitRepository.findByType(pageable, Exhibit.Type.valueOf(type));
        } else {
            exhibits = exhibitRepository.findAll(pageable);
        }

        return exhibits.map(exhibit -> {
            ExhibitDTO dto = exhibitMapper.toDTO(exhibit);
            List<File> files = fileRepo.findByTargetTypeAndTargetId(File.Type.EXHIBIT, exhibit.getId());
            dto.setFiles(files);
            return dto;
        });
    }

}
