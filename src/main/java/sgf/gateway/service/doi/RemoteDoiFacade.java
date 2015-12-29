package sgf.gateway.service.doi;

public interface RemoteDoiFacade {

    String mintDoi(DataciteDoiRequest dataciteDoiRequest);

    DoiMetadata getDoiMetadata(String doi);

    void updateDoi(DataciteDoiRequest dataciteDoiRequest);
}
