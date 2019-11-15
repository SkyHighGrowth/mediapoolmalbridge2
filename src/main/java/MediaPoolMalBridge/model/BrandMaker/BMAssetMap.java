package MediaPoolMalBridge.model.BrandMaker;

import MediaPoolMalBridge.model.asset.TransferringAsset;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class BMAssetMap extends ConcurrentHashMap<String, TransferringAsset> {
}
