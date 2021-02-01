package com.brandmaker.mediapoolmalbridge.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MalPriorities {

    private final AppConfig appConfig;

    private final Map<String, Integer> brandIdToPriority = new HashMap<>();

    private Map<Integer, List<String>> priorityToListOfBrands;

    private List<Integer> orderedPriorities;

    private int smallestPriority;

    public MalPriorities(AppConfig appConfig) {
        this.appConfig = appConfig;
        loadPriorities();
    }

    public void loadPriorities() {
        priorityToListOfBrands = appConfig.getAppConfigData().getMalPriorities();

        priorityToListOfBrands.forEach((key, value) -> value.forEach(y -> brandIdToPriority.put(y, key)));

        orderedPriorities = new ArrayList<>(priorityToListOfBrands.keySet());
        Collections.sort(orderedPriorities);

        smallestPriority = orderedPriorities.get(orderedPriorities.size() - 1) + 1000;
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

}
