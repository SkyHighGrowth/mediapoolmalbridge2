package MediaPoolMalBridge.clients.MAL.colors.client.model;

import MediaPoolMalBridge.clients.MAL.singleresponse.MALAbstractResponse;

import java.util.List;

/**
 * Response of {@link MediaPoolMalBridge.clients.MAL.colors.client.MALGetColorsClient}
 */
public class MALGetColorsResponse extends MALAbstractResponse {

    /**
     * List of {@link Color} objects
     */
    private List<Color> colors;

    public List<Color> getColors() {
        return colors;
    }

    public void setColors(List<Color> colors) {
        this.colors = colors;
    }
}
