package dev.dex.fcpeuro.util;

import dev.dex.fcpeuro.entity.category.*;
import dev.dex.fcpeuro.model.*;

import java.util.*;

public class CategoryUtil {

    public static List<CategoryResponse> createCategoryResponse(List<CategoryBot> categoriesBot) {
        List<CategoryResponse> res = new ArrayList<>();

        // Add categoriesTop
        categoriesBot.stream()
                .map(categoryBot -> categoryBot.getCategoryMid().getCategoryTop().getCategoryTop())
                .distinct()
                .sorted()
                .forEach(categoryTop -> res.add(new CategoryResponse(categoryTop, new ArrayList<>())));


        // Add categoriesMid
        for (CategoryBot categoryBot: categoriesBot) {
            String categoryTopStr = categoryBot.getCategoryMid().getCategoryTop().getCategoryTop();
            String categoryMidStr = categoryBot.getCategoryMid().getCategoryMid();

            CategoryResponse categoryResponse = findCategoryResponse(res, categoryTopStr);
            List<CategoryMidResponse> categoryMidResponses = categoryResponse.getCategoriesMid();

            if (findCategoryMidResponse(categoryMidResponses, categoryMidStr) == null) {
                categoryMidResponses.add(new CategoryMidResponse(categoryMidStr, new ArrayList<>()));
            }

        }

        // Add categoriesBot
        for (CategoryBot categoryBot: categoriesBot) {
            String categoryTopStr = categoryBot.getCategoryMid().getCategoryTop().getCategoryTop();
            String categoryMidStr = categoryBot.getCategoryMid().getCategoryMid();
            String categoryBotStr = categoryBot.getCategoryBot();

            CategoryResponse categoryResponse = findCategoryResponse(res, categoryTopStr);
            List<CategoryMidResponse> categoryMidResponses = categoryResponse.getCategoriesMid();

            CategoryMidResponse categoryMidResponse = findCategoryMidResponse(categoryMidResponses, categoryMidStr);
            List<String> categoriesBotResponses = categoryMidResponse.getCategoriesBot();
            if (!categoriesBotResponses.contains(categoryBotStr)) {
                categoriesBotResponses.add(categoryBotStr);
            }
        }

        return res;
    }

    private static CategoryResponse findCategoryResponse(List<CategoryResponse> res, String categoryTop) {
        for (CategoryResponse categoryResponse: res) {
            if (categoryResponse.getCategoryTop().equals(categoryTop)) {
                return categoryResponse;
            }
        }
        return null;
    }

    private static CategoryMidResponse findCategoryMidResponse(List<CategoryMidResponse> categoryMidResponses,
                                                                      String categoryMid) {
        for (CategoryMidResponse categoryMidResponse: categoryMidResponses) {
            if (categoryMidResponse.getCategoryMid().equals(categoryMid)) {
                return categoryMidResponse;
            }
        }
        return null;
    }
}
