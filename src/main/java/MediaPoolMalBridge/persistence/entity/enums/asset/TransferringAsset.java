package MediaPoolMalBridge.persistence.entity.enums.asset;

public class TransferringAsset {

    private int malStatesRepetitions = 0;

    private int bmStatesRepetitions = 0;

    private TransferringAssetStatus transferringAssetStatus = TransferringAssetStatus.INVALID;

    private TransferringMALConnectionAssetStatus transferringMALConnectionAssetStatus = TransferringMALConnectionAssetStatus.INVALID;

    private TransferringBMConnectionAssetStatus transferringBMConnectionAssetStatus = TransferringBMConnectionAssetStatus.INVALID;

    public TransferringAssetStatus getTransferringAssetStatus() {
        return transferringAssetStatus;
    }

    public void setTransferringAssetStatus(TransferringAssetStatus transferringAssetStatus) {
        this.transferringAssetStatus = transferringAssetStatus;
    }

    public TransferringMALConnectionAssetStatus getTransferringMALConnectionAssetStatus() {
        return transferringMALConnectionAssetStatus;
    }

    public void setTransferringMALConnectionAssetStatus(TransferringMALConnectionAssetStatus transferringMALConnectionAssetStatus) {
        if (this.transferringMALConnectionAssetStatus.equals(transferringMALConnectionAssetStatus)) {
            ++malStatesRepetitions;
        }
        if (malStatesRepetitions == 23) {
            this.transferringMALConnectionAssetStatus = TransferringMALConnectionAssetStatus.ERROR;
            return;
        }
        this.transferringMALConnectionAssetStatus = transferringMALConnectionAssetStatus;
    }

    public TransferringBMConnectionAssetStatus getTransferringBMConnectionAssetStatus() {
        return transferringBMConnectionAssetStatus;
    }

    public void setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus transferringBMConnectionAssetStatus) {
        if (this.transferringBMConnectionAssetStatus.equals(transferringBMConnectionAssetStatus)) {
            ++bmStatesRepetitions;
        }
        if (bmStatesRepetitions == 23) {
            this.transferringBMConnectionAssetStatus = TransferringBMConnectionAssetStatus.ERROR;
            return;
        }
        this.transferringBMConnectionAssetStatus = transferringBMConnectionAssetStatus;
    }
}
