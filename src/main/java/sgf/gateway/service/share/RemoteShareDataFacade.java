package sgf.gateway.service.share;


import sgf.gateway.model.metadata.Dataset;

public interface RemoteShareDataFacade {

    void pushToShare(Dataset dataset);
}
