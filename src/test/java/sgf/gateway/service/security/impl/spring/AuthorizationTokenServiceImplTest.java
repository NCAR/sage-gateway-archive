package sgf.gateway.service.security.impl.spring;

import org.junit.Test;

import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AuthorizationTokenServiceImplTest {

    @Test
    public void getPathFromFullURI() {

        AuthorizationTokenServiceImpl service = new AuthorizationTokenServiceImpl(null, 0);

        URI uri = URI.create("http://localhost/archive/request/9307816b-6204-4aed-8ecf-ba402a594ca8/file/735206b2-772f-4f55-83e0-27945e9ef877");

        URI path = service.getPath(uri);

        assertThat(path, equalTo(URI.create("/archive/request/9307816b-6204-4aed-8ecf-ba402a594ca8/file/735206b2-772f-4f55-83e0-27945e9ef877")));
    }

    @Test
    public void getPathFromPathOnlyURI() {

        AuthorizationTokenServiceImpl service = new AuthorizationTokenServiceImpl(null, 0);

        URI uri = URI.create("/archive/request/9307816b-6204-4aed-8ecf-ba402a594ca8/file/735206b2-772f-4f55-83e0-27945e9ef877");

        URI path = service.getPath(uri);

        assertThat(path, equalTo(URI.create("/archive/request/9307816b-6204-4aed-8ecf-ba402a594ca8/file/735206b2-772f-4f55-83e0-27945e9ef877")));
    }
}
