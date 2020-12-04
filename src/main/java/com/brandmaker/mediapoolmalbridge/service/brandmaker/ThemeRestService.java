package com.brandmaker.mediapoolmalbridge.service.brandmaker;

/**
 * Service that returns theme id by color id
 */
public interface ThemeRestService {

    /**
     * Get color id by color name
     *
     * @param name Color name
     * @return BM Theme color Id
     */
    String getColorIdByName(String name);
}
