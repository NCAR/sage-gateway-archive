package sgf.gateway.search.service;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.search.api.Results;

public interface MoreLikeThisService {

    Results moreLikeThis(Dataset dataset);
}
