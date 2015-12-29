package sgf.gateway.service.metadata;

import org.safehaus.uuid.UUID;

public interface ChecksumService {

    void addChecksumToFile(UUID logicalFileIdentifier);
}
