package com.diemdt.literaturemuseum.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.diemdt.literaturemuseum.entity.File;
import com.diemdt.literaturemuseum.repository.FileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepo fileRepo;

    @Value("${aws.s3.access.key}")
    private String awsS3AccessKey;

    @Value("${aws.s3.secret.key}")
    private String awsS3SecretKey;

    @Override
    public File saveFile(MultipartFile file, String name, File.Type fileType, Long targetId, String description) {
        String saveFileUrl = saveFileToAWSS3Bucket(file, fileType.name()); // Sử dụng tên enum làm folder

        File fileToSave = File.builder()
                .fileUrl(saveFileUrl)
                .name(name)
                .targetId(targetId)
                .targetType(fileType)
                .description(description)
                .build();

        return fileRepo.save(fileToSave);
    }

    @Override
    public List<File> getAllFiles() {
        return fileRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public List<File> getFilesByTarget(File.Type fileType, Long targetId) {
        return fileRepo.findByTargetTypeAndTargetId(fileType, targetId);
    }

    @Override
    public File assignToTarget(Long fileId, File.Type fileType, Long targetId) {
        File file = fileRepo.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        file.setTargetType(fileType);
        file.setTargetId(targetId);
        return fileRepo.save(file);
    }

    @Override
    public List<File> getFilesByTargetBatch(File.Type fileType, List<Long> targetIds) {
        return fileRepo.findByTargetTypeAndTargetIdIn(fileType, targetIds);
    }


    private String saveFileToAWSS3Bucket(MultipartFile file, String type) {
        try {
            String originalFileName = file.getOriginalFilename();
            String folder = type.toLowerCase(); // folder theo kiểu targetType, ví dụ: work, author...
            String s3FileName = folder + "/" + originalFileName;

            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsS3AccessKey, awsS3SecretKey);

            AmazonS3 amazonS3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(Regions.AP_SOUTHEAST_2)
                    .build();

            InputStream inputStream = file.getInputStream();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());

            String bucketName = "museum-diemdt";
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3FileName, inputStream, objectMetadata);
            amazonS3Client.putObject(putObjectRequest);

            return "https://" + bucketName + ".s3.amazonaws.com/" + s3FileName;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }



    }
}
