package sgf.gateway.service.metadata.impl.spring;


import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.safehaus.uuid.UUID;
import sgf.gateway.dao.metadata.LogicalFileRepository;
import sgf.gateway.model.metadata.inventory.ChecksumMismatchException;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.service.factory.MessageDigestFactory;
import sgf.gateway.service.messaging.ExceptionHandlingService;
import sgf.gateway.service.messaging.UnhandledException;
import sgf.gateway.utils.file.stream.FileInputStreamStrategy;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ChecksumServiceTest {

    private static final String ALGORITHM = "MD5";
    private static final byte[] MESSAGE_DIGEST_BYTES = new byte[]{(byte) 110, (byte) -64, (byte) 45, (byte) -9, (byte) 86, (byte) -126, (byte) -51, (byte) 84,
            (byte) -108, (byte) 99, (byte) -59, (byte) 67, (byte) 59, (byte) 51, (byte) -27, (byte) 111};

    private static final String DIGEST_HEX_STRING = "6ec02df75682cd549463c5433b33e56f";

    private LogicalFile mockLogicalFile;
    private ExceptionHandlingServiceSpy exceptionServiceSpy;
    private InputStream mockInputStream;
    private MessageDigest mockMessageDigest;
    private MessageDigestFactory mockMessageDigestFactory;
    private LogicalFileRepository fakeLogicalFileRepository;
    private FileInputStreamStrategy fakeFileInputStreamStrategy;
    private ChecksumServiceImpl checksumService;

    @Before
    public void setup() throws IOException {

        mockLogicalFile = mock(LogicalFile.class);

        fakeLogicalFileRepository = mock(LogicalFileRepository.class);
        when(fakeLogicalFileRepository.get(UUID.valueOf("ece4d063-ecf4-40c0-970a-81df0f79251f"))).thenReturn(mockLogicalFile);

        mockInputStream = mock(InputStream.class);

        fakeFileInputStreamStrategy = mock(FileInputStreamStrategy.class);

        when(fakeFileInputStreamStrategy.getFileInputStream(mockLogicalFile)).thenReturn(mockInputStream);

        exceptionServiceSpy = new ExceptionHandlingServiceSpy();

        // Return a mock MD5 message digest from factory
        mockMessageDigest = mock(MessageDigest.class);
        mockMessageDigestFactory = mock(MessageDigestFactory.class);
        when(mockMessageDigestFactory.getMessageDigest(ALGORITHM)).thenReturn(mockMessageDigest);

        checksumService = new ChecksumServiceImpl(fakeLogicalFileRepository, fakeFileInputStreamStrategy, mockMessageDigestFactory, exceptionServiceSpy);
    }


    @Test
    public void badInputStreamCausesHandledException() throws IOException {

        when(mockInputStream.read(any(byte[].class))).thenThrow(new IOException());

        checksumService.addChecksumToFile(UUID.valueOf("ece4d063-ecf4-40c0-970a-81df0f79251f"));

        assertThat((IOException) exceptionServiceSpy.getCause(), Is.isA(IOException.class));
    }


    @Test
    public void goodInputStreamSetsExpectedChecksumValue() throws IOException {

        // Mock an input stream with one byte
        when(mockInputStream.read(any(byte[].class))).thenReturn(1).thenReturn(-1);
        when(mockMessageDigest.digest()).thenReturn(MESSAGE_DIGEST_BYTES);

        checksumService.addChecksumToFile(UUID.valueOf("ece4d063-ecf4-40c0-970a-81df0f79251f"));

        verify(mockLogicalFile).setMd5Checksum(DIGEST_HEX_STRING);
    }

    @Test
    public void conflictingChecksumCausesHandledException() throws Exception {

        // Mock an input stream with one byte
        when(mockInputStream.read(any(byte[].class))).thenReturn(1).thenReturn(-1);
        when(mockMessageDigest.digest()).thenReturn(MESSAGE_DIGEST_BYTES);
        doThrow(new ChecksumMismatchException("foo")).when(mockLogicalFile).setMd5Checksum(anyString());

        checksumService.addChecksumToFile(UUID.valueOf("ece4d063-ecf4-40c0-970a-81df0f79251f"));

        assertThat((ChecksumMismatchException) exceptionServiceSpy.getCause(), Is.isA(ChecksumMismatchException.class));
    }


    public class ExceptionHandlingServiceSpy implements ExceptionHandlingService {

        private UnhandledException exception;

        @Override
        public void handledException(UnhandledException exception) {
            this.exception = exception;
        }

        @Override
        public void handleUnexpectedException(UnhandledException exception) {
            this.exception = exception;
        }

        public Throwable getCause() {
            return exception.getCause();
        }
    }

}