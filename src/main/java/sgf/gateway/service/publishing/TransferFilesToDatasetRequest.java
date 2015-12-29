package sgf.gateway.service.publishing;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TransferFilesToDatasetRequest {

    String getDatasetIdentifier();

    String getDatasetTitle();

    List<MultipartFile> getFiles();
}
