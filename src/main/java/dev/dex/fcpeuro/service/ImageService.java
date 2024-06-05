package dev.dex.fcpeuro.service;

import dev.dex.fcpeuro.entity.Image;
import dev.dex.fcpeuro.model.ImageAndType;
import dev.dex.fcpeuro.repo.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public List<Image> findByPartId(Integer partId) {
        return imageRepository.findByPartId(partId);
    }

    public ImageAndType downloadImage(String name) {
        Image image = imageRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Image not found: " + name));
        String filePath = image.getFilePath();
        byte[] img;
        try {
            img = Files.readAllBytes(new File(filePath).toPath());
        } catch (IOException e) {
            throw new RuntimeException("Cannot read image.");
        }
        return new ImageAndType(img, image.getType());
    }

}
