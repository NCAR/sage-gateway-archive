package sgf.gateway.validation.persistence;

import org.junit.Test;
import sgf.gateway.dao.metadata.PlatformTypeRepository;
import sgf.gateway.model.metadata.activities.observing.PlatformType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AssertUniquePlatformTypeValidatorTest {

    @Test
    public void testPlatformNotFound() {

        PlatformTypeRepository mockPlatformRepository = mock(PlatformTypeRepository.class);

        AssertUniquePlatformTypeValidator validator = new AssertUniquePlatformTypeValidator(mockPlatformRepository);

        boolean valid = validator.isValid("short_name", null);

        assertThat(valid, equalTo(true));
    }

    @Test
    public void testPlatformFound() {

        PlatformType mockPlatformType = mock(PlatformType.class);

        PlatformTypeRepository mockPlatformRepository = mock(PlatformTypeRepository.class);
        when(mockPlatformRepository.findByName("short_name")).thenReturn(mockPlatformType);

        AssertUniquePlatformTypeValidator validator = new AssertUniquePlatformTypeValidator(mockPlatformRepository);

        boolean valid = validator.isValid("short_name", null);

        assertThat(valid, equalTo(false));
    }

    @Test
    public void testBlankShortNameText() {

        PlatformTypeRepository mockPlatformRepository = mock(PlatformTypeRepository.class);

        AssertUniquePlatformTypeValidator validator = new AssertUniquePlatformTypeValidator(mockPlatformRepository);

        boolean valid = validator.isValid("", null);

        assertThat(valid, equalTo(true));
    }

    @Test
    public void testNullShortNameText() {

        PlatformTypeRepository mockPlatformRepository = mock(PlatformTypeRepository.class);

        AssertUniquePlatformTypeValidator validator = new AssertUniquePlatformTypeValidator(mockPlatformRepository);

        boolean valid = validator.isValid(null, null);

        assertThat(valid, equalTo(true));
    }
}
