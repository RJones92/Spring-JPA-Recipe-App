package com.example.springrecipebook.Bootstrap;

import com.example.springrecipebook.Repositories.CategoryRepository;
import com.example.springrecipebook.Repositories.RecipeRepository;
import com.example.springrecipebook.Repositories.UnitOfMeasureRepository;
import com.example.springrecipebook.model.*;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

import static com.example.springrecipebook.enums.Difficulty.EASY;
import static com.example.springrecipebook.enums.Difficulty.MEDIUM;

@Component
public class DataInitialiser implements ApplicationListener<ContextRefreshedEvent> {

    private static final String EACH = "Each";
    private static final String TABLESPOON = "Tablespoon";
    private static final String TEASPOON = "Teaspoon";
    private static final String DASH = "Dash";
    private static final String PINT = "Pint";
    private static final String CUP = "Cup";
    private static final String AMERICAN = "American";
    private static final String MEXICAN = "Mexican";

    private Map<String, UnitOfMeasure> uomMap;
    private Map<String, Category> categoryMap;

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public DataInitialiser(CategoryRepository categoryRepository, RecipeRepository recipeRepository,
                           UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
    }

    private List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>(2);

        uomMap = getUomsFromList(Arrays.asList(
                EACH,
                TABLESPOON,
                TEASPOON,
                DASH,
                PINT,
                CUP
        ));

        categoryMap = getCategoriesFromList(Arrays.asList(
                AMERICAN,
                MEXICAN
        ));

        recipes.add(createGuacRecipe(uomMap, categoryMap));
        recipes.add(createTacoRecipe(uomMap, categoryMap));

        return recipes;
    }

    private Map<String, UnitOfMeasure> getUomsFromList(List<String> listOfUoms){
        Map<String, UnitOfMeasure> uomMap = new HashMap<>();

        for (String uom : listOfUoms) {
            uomMap.put(
                    uom,
                    unitOfMeasureRepository.findByUnitName(uom).orElseThrow(
                            () -> new RuntimeException("Expected UOM not found"))
            );
        }
        return uomMap;
    }

    private Map<String, Category> getCategoriesFromList(List<String> listOfCategories) {
        Map<String, Category> categoryMap = new HashMap<>();

        for (String category : listOfCategories) {
            categoryMap.put(
                    category,
                    categoryRepository.findByDescription(category).orElseThrow(
                            () -> new RuntimeException("Expected Category not found"))
            );
        }
        return categoryMap;
    }

    private Recipe createGuacRecipe(Map<String, UnitOfMeasure> uomMap, Map<String, Category> categoryMap) {
        final String guacamole_directions =
                "1 Cut the avocado, remove flesh: Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n"
                        + "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)"
                        + "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                        "\n" +
                        "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                        "\n" +
                        "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                        "\n" +
                        "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n"
                        + "4 Serve: Serve immediately, or if making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.";

        Recipe guacamole = new Recipe();
        guacamole.setDescription("Perfect Guacamole");
        guacamole.setPrepTime(10);
        guacamole.setCookTime(0);
        guacamole.setServings(2);
        guacamole.setSource("Simply Recipes");
        guacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/#recipe159");
        guacamole.setDirections(guacamole_directions);
        guacamole.setDifficulty(EASY);

        Notes guacamoleNotes = new Notes();
        guacamoleNotes.setRecipeNotes("Be careful handling chillies if using. Wash your hands thoroughly after handling and do not touch your eyes or the area near your eyes with your hands for several hours.");
        guacamoleNotes.setRecipe(guacamole);
        guacamole.setNotes(guacamoleNotes);

        guacamole.getIngredients().add(new Ingredient("ripe avocados", new BigDecimal(2), uomMap.get(EACH), guacamole));
        guacamole.getIngredients().add(new Ingredient("Kosher salt", new BigDecimal(".5"), uomMap.get(TEASPOON), guacamole));
        guacamole.getIngredients().add(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(2), uomMap.get(TABLESPOON), guacamole));
        guacamole.getIngredients().add(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), uomMap.get(TABLESPOON), guacamole));
        guacamole.getIngredients().add(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), uomMap.get(EACH), guacamole));
        guacamole.getIngredients().add(new Ingredient("Cilantro", new BigDecimal(2), uomMap.get(TABLESPOON), guacamole));
        guacamole.getIngredients().add(new Ingredient("freshly grated black pepper", new BigDecimal(2), uomMap.get(DASH), guacamole));
        guacamole.getIngredients().add(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), uomMap.get(EACH), guacamole));

        guacamole.getCategories().add(categoryMap.get(AMERICAN));
        guacamole.getCategories().add(categoryMap.get(MEXICAN));

        return guacamole;
    }

    private Recipe createTacoRecipe(Map<String, UnitOfMeasure> uomMap, Map<String, Category> categoryMap) {

        final String taco_directions = "1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "\n" +
                "\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm";

        Recipe taco = new Recipe();
        taco.setDescription("Spicy Grilled Chicken Taco");
        taco.setPrepTime(15);
        taco.setCookTime(20);
        taco.setServings(4);
        taco.setSource("Simply Recipes");
        taco.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        taco.setDirections(taco_directions);
        taco.setDifficulty(MEDIUM);

        Notes tacoNotes = new Notes();
        tacoNotes.setRecipeNotes("Look for ancho chile powder with the Mexican ingredients at your grocery store, on buy it online. (If you can't find ancho chili powder, you replace the ancho chili, the oregano, and the cumin with 2 1/2 tablespoons regular chili powder, though the flavor won't be quite the same.)");
        tacoNotes.setRecipe(taco);
        taco.setNotes(tacoNotes);

        taco.getIngredients().add(new Ingredient("Ancho Chili Powder", new BigDecimal(2), uomMap.get(TABLESPOON), taco));
        taco.getIngredients().add(new Ingredient("Dried Oregano", new BigDecimal(1), uomMap.get(TEASPOON), taco));
        taco.getIngredients().add(new Ingredient("Dried Cumin", new BigDecimal(1), uomMap.get(TEASPOON), taco));
        taco.getIngredients().add(new Ingredient("Sugar", new BigDecimal(1), uomMap.get(TEASPOON), taco));
        taco.getIngredients().add(new Ingredient("Salt", new BigDecimal(".5"), uomMap.get(TEASPOON), taco));
        taco.getIngredients().add(new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1), uomMap.get(EACH), taco));
        taco.getIngredients().add(new Ingredient("finely grated orange zestr", new BigDecimal(1), uomMap.get(TABLESPOON), taco));
        taco.getIngredients().add(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), uomMap.get(TABLESPOON), taco));
        taco.getIngredients().add(new Ingredient("Olive Oil", new BigDecimal(2), uomMap.get(TABLESPOON), taco));
        taco.getIngredients().add(new Ingredient("boneless chicken thighs", new BigDecimal(4), uomMap.get(TABLESPOON), taco));
        taco.getIngredients().add(new Ingredient("small corn tortillasr", new BigDecimal(8), uomMap.get(EACH), taco));
        taco.getIngredients().add(new Ingredient("packed baby arugula", new BigDecimal(3), uomMap.get(CUP), taco));
        taco.getIngredients().add(new Ingredient("medium ripe avocados, slic", new BigDecimal(2), uomMap.get(EACH), taco));
        taco.getIngredients().add(new Ingredient("radishes, thinly sliced", new BigDecimal(4), uomMap.get(EACH), taco));
        taco.getIngredients().add(new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), uomMap.get(PINT), taco));
        taco.getIngredients().add(new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), uomMap.get(EACH), taco));
        taco.getIngredients().add(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), uomMap.get(EACH), taco));
        taco.getIngredients().add(new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), uomMap.get(CUP), taco));
        taco.getIngredients().add(new Ingredient("lime, cut into wedges", new BigDecimal(4), uomMap.get(EACH), taco));

        taco.getCategories().add(categoryMap.get(AMERICAN));
        taco.getCategories().add(categoryMap.get(MEXICAN));

        return taco;
    }

}
