package com.meme.controller;

import com.meme.model.Meme;
import com.meme.service.MemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/memes")
@CrossOrigin(origins = "*")
public class MemeController {
    private final MemeService memeService;





    public MemeController(MemeService memeService) {
        this.memeService = memeService;
    }


    // Upload raw image + texts
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file,
                                    @RequestParam(value = "topText", required = false) String topText,
                                    @RequestParam(value = "bottomText", required = false) String bottomText) throws IOException {
        Meme saved = memeService.saveUploadedFile(file, topText, bottomText);
        return ResponseEntity.ok(saved);
    }


    // Save base64 image from client canvas
    @PostMapping("/saveBase64")
    public ResponseEntity<?> saveBase64(@RequestBody Base64Request req) throws IOException {
        Meme saved = memeService.saveBase64Image(req.getData(), req.getTopText(), req.getBottomText());
        return ResponseEntity.ok(saved);
    }


    @GetMapping
    public List<Meme> all() {
        return memeService.listAll();
    }


    // DTO for base64
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
