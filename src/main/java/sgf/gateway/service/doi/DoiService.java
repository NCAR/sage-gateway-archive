package sgf.gateway.service.doi;

public interface DoiService {

    void mintDoi(DataciteDoiRequest dataciteRequest);

    DoiMetadata getDoiMetadata(String doiIdentifier);

    void updateDoi(DataciteDoiRequest dataciteDoiRequest);
}
