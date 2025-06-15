package com.diemdt.literaturemuseum.service;

import com.diemdt.literaturemuseum.dto.AwardDTO;
import com.diemdt.literaturemuseum.entity.Award;
import com.diemdt.literaturemuseum.exception.ResourceNotFoundException;
import com.diemdt.literaturemuseum.mapper.AwardMapper;
import com.diemdt.literaturemuseum.repository.AwardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AwardService {
    private final AwardRepository awardRepository;
    private final AwardMapper awardMapper;

    @Transactional
    public AwardDTO createAward ( AwardDTO awardDTO ){
        Award award = awardMapper.toEntity(awardDTO);
        Award savedAward = awardRepository.save(award);
        return awardMapper.toDTO(savedAward);
    }

    @Transactional
    public AwardDTO updateAward ( Long id , AwardDTO awardDTO) {
        Award existingAward = awardRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Award not found"));
        existingAward.setName(awardDTO.getName());
        existingAward.setDescription(awardDTO.getDescription());

        Award updatedAward = awardRepository.save(existingAward);

        return awardMapper.toDTO(updatedAward);
    }

    @Transactional
    public void deleteAward (Long id){
        if(!awardRepository.existsById(id)){
            throw new ResourceNotFoundException("Award not found");
        }

        awardRepository.deleteById(id);
    }

    @Transactional
    public AwardDTO getAward(Long id) {
        Award award = awardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Award not found"));
        return awardMapper.toDTO(award);
    }


    public Page<AwardDTO> getAllAwards (Pageable pageable, String search){
        Page<Award> awards;
        if (search == null || search.isBlank()) {
            awards =  awardRepository.findAll(pageable);
        } else {
            awards = awardRepository.findByNameContainingIgnoreCase(pageable, search);
        }


        return awards.map(award -> awardMapper.toDTO(award));
    }
}
