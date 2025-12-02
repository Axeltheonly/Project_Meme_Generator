package com.meme.controller;

import com.meme.model.Meme;
import com.meme.service.MemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/memes")
@CrossOrigin(origins = "*")
public class MemeController {
    private final MemeService memeService;

    public MemeController(MemeService memeService) {
        this.memeService = memeService;
    }

    // NOUVEAU : Récupérer un mème par ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Meme> meme = memeService.findById(id);
        if (meme.isPresent()) {
            return ResponseEntity.ok(meme.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/saveBase64")
    public ResponseEntity<?> saveBase64(@RequestBody Base64Request req) throws IOException {
        Meme saved = memeService.saveBase64Image(req.getData(), req.getTopText(), req.getBottomText());
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public List<Meme> all() {
        return memeService.listAll();
    }

    public static class Base64Request {
        private String data;
        private String topText;
        private String bottomText;
        public Base64Request() {}
        public String getData() { return data; }
        public void setData(String data) { this.data = data; }
        public String getTopText() { return topText; }
        public void setTopText(String topText) { this.topText = topText; }
        public String getBottomText() { return bottomText; }
        public void setBottomText(String bottomText) { this.bottomText = bottomText; }
    }
}
