package dev.dex.fcpeuro.controller;

import dev.dex.fcpeuro.model.ImageAndType;
import dev.dex.fcpeuro.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping("/{partId}")
    public ResponseEntity<?> getImagesByPartId(@PathVariable Integer partId) {
        return ResponseEntity.ok(imageService.findByPartId(partId));
    }

    @GetMapping("/download/{name}")
    public ResponseEntity<?> getImageByPartId(@PathVariable String name) {
        ImageAndType imageAndType = imageService.downloadImage(name);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(imageAndType.type()))
                .body(imageAndType.img());
    }
}
