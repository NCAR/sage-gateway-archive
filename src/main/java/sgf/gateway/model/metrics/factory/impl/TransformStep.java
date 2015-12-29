package sgf.gateway.model.metrics.factory.impl;

import sgf.gateway.model.metrics.FileDownload;

public interface TransformStep {

    FileDownload transform(FileDownload fileDownload);
}
