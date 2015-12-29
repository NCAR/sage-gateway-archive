package sgf.gateway.service.yadis;

import java.net.URI;

public interface RemoteYadisProxy {

    URI getSamlAttributeEndpoint(String openId);
}
