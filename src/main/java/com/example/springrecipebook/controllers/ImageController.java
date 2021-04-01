package com.example.springrecipebook.controllers;

import com.example.springrecipebook.services.ImageService;
import com.example.springrecipebook.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/recipe/{recipeId}")
public class ImageController {

    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/image")
    public String showUploadForm(Model model, @PathVariable String recipeId) {
        model.addAttribute("recipe", recipeService.getRecipeCommandById(Long.valueOf(recipeId)));
        return "/recipe/imageUploadForm";
    }

    @PostMapping("/image")
    public String postImage(@PathVariable String recipeId, @RequestParam("imageFile") MultipartFile imageFile) {
        imageService.saveImageFile(Long.valueOf(recipeId), imageFile);
        return "redirect:/recipe/" + recipeId + "/show";
    }
}
