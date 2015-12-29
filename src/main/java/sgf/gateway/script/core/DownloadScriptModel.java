package sgf.gateway.script.core;

import sgf.gateway.model.Gateway;
import sgf.gateway.model.security.User;
import sgf.gateway.script.core.impl.FileDownloadModel;

import java.util.Collection;

public interface DownloadScriptModel {

    Gateway getGateway();

    User getUser();

    String getMyProxyServer();

    String getMyProxyServerPort();

    Collection<FileDownloadModel> getFileDownloadModels();
}
