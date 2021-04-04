package com.example.springrecipebook.controllers;

import com.example.springrecipebook.commands.RecipeCommand;
import com.example.springrecipebook.services.ImageService;
import com.example.springrecipebook.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ImageControllerTest {

    @Mock
    ImageService imageService;
    @Mock
    RecipeService recipeService;
    @InjectMocks
    ImageController imageController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    void testShowUploadForm() throws Exception {
        Long recipeId = 1L;
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(recipeId);
        when(recipeService.getRecipeCommandById(anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/" + recipeId + "/image"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/imageUploadForm"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).getRecipeCommandById(anyLong());
    }

    @Test
    void testPostImage() throws Exception {
        MockMultipartFile multipartFile =
                new MockMultipartFile("imageFile",
                        "testing.txt",
                        "text/plain",
                        "bla bla bla".getBytes());

        mockMvc.perform(multipart("/recipe/1/image")
                .file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1/show"));

        verify(imageService, times(1)).saveImageFile(anyLong(), any());
    }

    @Test
    void testRenderImageFromDB() throws Exception {
        //GIVEN
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        String s = "fake image text";
        Byte[] wrappedBytes = new Byte[s.getBytes().length];
        int i = 0;
        for (byte primitiveByte : s.getBytes()) {
            wrappedBytes[i++] = primitiveByte;
        }
        recipeCommand.setImage(wrappedBytes);

        when(recipeService.getRecipeCommandById(anyLong())).thenReturn(recipeCommand);

        //WHEN
        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/showImage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        //THEN
        assertEquals(s.getBytes().length, response.getContentAsByteArray().length);
    }
}