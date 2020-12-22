package com.brandmaker.mediapoolmalbridge.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MalPriorities {

    private final Map<String, Integer> brandIdToPriority = new HashMap<>();

    private final Map<Integer, List<String>> priorityToListOfBrands;

    private final List<Integer> orderedPriorities;

    private final List<String> brandIds;

    private final int smallestPriority;

    public MalPriorities(final AppConfig appConfig) {

        priorityToListOfBrands = appConfig.getAppConfigData().getMalPriorities();

        priorityToListOfBrands.forEach((key, value) -> value.forEach(y -> brandIdToPriority.put(y, key)));

        orderedPriorities = new ArrayList<>(priorityToListOfBrands.keySet());
        Collections.sort(orderedPriorities);

        smallestPriority = orderedPriorities.get(orderedPriorities.size() - 1) + 1000;
        brandIds = new ArrayList<>(brandIdToPriority.keySet());
    }

    public int getMalPriority(final String brandId) {
        if (StringUtils.isBlank(brandId)) {
            return brandIdToPriority.computeIfAbsent("null", bI -> smallestPriority);
        }
        return brandIdToPriority.computeIfAbsent(brandId, bI -> smallestPriority);
    }

    public List<String> getBrandIdsForPriority(final int priority) {
        return priorityToListOfBrands.get(priority);
    }

    public List<Integer> getOrderedPriorities() {
        return orderedPriorities;
    }

    public Map<String, Integer> getBrandIdToPriority() {
        return brandIdToPriority;
    }

    public Map<Integer, List<String>> getPriorityToListOfBrands() {
        return priorityToListOfBrands;
    }

    public List<String> getBrandIds() {
        return brandIds;
    }

    public int getSmallestPriority() {
        return smallestPriority;
    }

    public boolean contains(final String brandId) {
        return brandIds.contains(brandId);
    }
}
