package com.example.springrecipebook.services;

import com.example.springrecipebook.Repositories.RecipeRepository;
import com.example.springrecipebook.model.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(Long recipeId, MultipartFile imageFile) {

        try {
            Recipe recipe = recipeRepository.findById(recipeId).get();

            Byte[] byteObjects = convertPrimativeBytesToWrapperBytes(imageFile.getBytes());
            recipe.setImage(byteObjects);
            recipeRepository.save(recipe);
        } catch (IOException e) {
            log.error("Error occurred writing image", e);
            e.printStackTrace();
        }

    }

    private Byte[] convertPrimativeBytesToWrapperBytes(byte[] primativeBytes) {
        Byte[] wrapperBytes = new Byte[primativeBytes.length];
        int i = 0;
        for (byte b : primativeBytes) {
            wrapperBytes[i++] = b;
        }
        return wrapperBytes;
    }
}
