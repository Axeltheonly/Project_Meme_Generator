package com.meme.service;


import com.meme.model.Meme;
import com.meme.repository.MemeRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;


@Service
public class MemeService {
    private final MemeRepository memeRepository;


    private final String uploadDir = "src/main/resources/static/uploads";

    public Optional<Meme> findById(Long id) {
        return memeRepository.findById(id);
    }
    public MemeService(MemeRepository memeRepository) {
        this.memeRepository = memeRepository;
    }


    public Meme saveUploadedFile(MultipartFile file, String topText, String bottomText) throws IOException {
        Files.createDirectories(Paths.get(uploadDir));
        String original = StringUtils.cleanPath(file.getOriginalFilename());
        String ext = "";
        int i = original.lastIndexOf('.');
        if (i >= 0) ext = original.substring(i);
        String filename = UUID.randomUUID().toString() + ext;
        Path target = Paths.get(uploadDir).resolve(filename);
        file.transferTo(target);
        Meme m = new Meme(filename, original, topText, bottomText);
        return memeRepository.save(m);
    }


    // Save base64 image (generated on client)
    public Meme saveBase64Image(String base64Data, String topText, String bottomText) throws IOException {
        Files.createDirectories(Paths.get(uploadDir));
// expected: data:image/png;base64,....
        String[] parts = base64Data.split(",");
        String meta = parts[0];
        String data = parts[1];
        String ext = ".png";
        if (meta.contains("jpeg") || meta.contains("jpg")) ext = ".jpg";
        byte[] bytes = Base64.getDecoder().decode(data);
        String filename = UUID.randomUUID().toString() + ext;
        Path filePath = Paths.get(uploadDir).resolve(filename);
        try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
            fos.write(bytes);
        }
        Meme m = new Meme(filename, filename, topText, bottomText);
        return memeRepository.save(m);
    }


    public List<Meme> listAll() {
        return memeRepository.findAll();
    }
}
