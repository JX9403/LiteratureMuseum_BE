package com.diemdt.literaturemuseum.service;

import com.diemdt.literaturemuseum.dto.ArtifactDTO;
import com.diemdt.literaturemuseum.entity.Artifact;
import com.diemdt.literaturemuseum.entity.File;
import com.diemdt.literaturemuseum.entity.News;
import com.diemdt.literaturemuseum.exception.ResourceNotFoundException;
import com.diemdt.literaturemuseum.mapper.ArtifactMapper;
import com.diemdt.literaturemuseum.repository.ArtifactRepository;
import com.diemdt.literaturemuseum.repository.FileRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtifactService {
    private final ArtifactRepository artifactRepository;
    private final ArtifactMapper artifactMapper;
    private final FileRepo fileRepo;
    @Transactional
    public ArtifactDTO createArtifact ( ArtifactDTO artifactDTO ){
        Artifact artifact = artifactMapper.toEntity(artifactDTO);
        artifact.setView(0);
        Artifact savedArtifact = artifactRepository.save(artifact);
        return artifactMapper.toDTO(savedArtifact);
    }

    @Transactional
    public ArtifactDTO updateArtifact ( Long id , ArtifactDTO artifactDTO) {
        Artifact existingArtifact = artifactRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Artifact not found"));
        existingArtifact.setContent(artifactDTO.getContent());
        existingArtifact.setName(artifactDTO.getName());
        existingArtifact.setUsdz(artifactDTO.getUsdz());
        existingArtifact.setModel(artifactDTO.getModel());
        existingArtifact.setUrl(artifactDTO.getUrl());


        Artifact updatedArtifact = artifactRepository.save(existingArtifact);

        return artifactMapper.toDTO(updatedArtifact);
    }

    @Transactional
    public void deleteArtifact (Long id){
        if(!artifactRepository.existsById(id)){
            throw new ResourceNotFoundException("Artifact not found");
        }

        artifactRepository.deleteById(id);
    }

    @Transactional
    public ArtifactDTO getArtifact(Long id) {
        Artifact artifact = artifactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artifact not found"));

        artifact.setView(artifact.getView() + 1);

        ArtifactDTO dto = artifactMapper.toDTO(artifact);

        List<File> files = fileRepo.findByTargetTypeAndTargetId(File.Type.ARTIFACT, artifact.getId());
        dto.setFiles(files);

        return dto;
    }


    public Page<ArtifactDTO> getAllArtifacts (Pageable pageable, String search){
        Page<Artifact> artifacts ;
        if (search == null || search.isEmpty()) {
             artifacts =  artifactRepository.findAll(pageable);
        } else {
            artifacts = artifactRepository.findByNameContainingIgnoreCase(pageable, search);
        }



        return artifacts.map(artifact -> {
            ArtifactDTO dto = artifactMapper.toDTO(artifact);

            List<File> files = fileRepo.findByTargetTypeAndTargetId(File.Type.ARTIFACT, artifact.getId());
            dto.setFiles(files);

            return dto;
        });
    }
}
