package com.example.springrecipebook.controllers;

import com.example.springrecipebook.commands.RecipeCommand;
import com.example.springrecipebook.services.ImageService;
import com.example.springrecipebook.services.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

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

    @GetMapping("/showImage")
    public void renderImageFromDB(@PathVariable String recipeId, HttpServletResponse response)
            throws IOException {
        RecipeCommand recipeCommand = recipeService.getRecipeCommandById(Long.valueOf(recipeId));
        if (recipeCommand.getImage() != null ) {
            byte[] byteArray = convertWrappedBytesToPrimitiveBytes(recipeCommand.getImage());

            response.setContentType("image/jpeg");
            InputStream inputStream = new ByteArrayInputStream(byteArray);
            IOUtils.copy(inputStream, response.getOutputStream());
        }
    }

    private byte[] convertWrappedBytesToPrimitiveBytes(Byte[] wrappedBytes) {
        byte[] primitiveBytes = new byte[wrappedBytes.length];

        int i = 0;
        for (Byte wrappedByte : wrappedBytes) {
            primitiveBytes[i++] = wrappedByte;
        }
        return primitiveBytes;
    }
}
