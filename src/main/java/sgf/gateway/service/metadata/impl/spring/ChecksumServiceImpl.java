package sgf.gateway.service.metadata.impl.spring;

import org.safehaus.uuid.UUID;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sgf.gateway.dao.metadata.LogicalFileRepository;
import sgf.gateway.model.metadata.inventory.ChecksumMismatchException;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.service.factory.MessageDigestFactory;
import sgf.gateway.service.messaging.ExceptionHandlingService;
import sgf.gateway.service.messaging.UnhandledException;
import sgf.gateway.service.metadata.ChecksumService;
import sgf.gateway.utils.file.stream.FileInputStreamStrategy;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Date;

public class ChecksumServiceImpl implements ChecksumService {

    private LogicalFileRepository logicalFileRepository;
    private FileInputStreamStrategy fileInputStreamStrategy;
    private MessageDigestFactory messageDigestFactory;
    private ExceptionHandlingService exceptionHandlingService;

    public ChecksumServiceImpl(LogicalFileRepository logicalFileRepository, FileInputStreamStrategy fileInputStreamStrategy, MessageDigestFactory messageDigestFactory, ExceptionHandlingService exceptionHandlingService) {

        this.logicalFileRepository = logicalFileRepository;
        this.fileInputStreamStrategy = fileInputStreamStrategy;
        this.exceptionHandlingService = exceptionHandlingService;
        this.messageDigestFactory = messageDigestFactory;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addChecksumToFile(UUID logicalFileIdentifier) {

        LogicalFile logicalFile = this.logicalFileRepository.get(logicalFileIdentifier);

        String md5checksum = this.tryCalculateMD5Checksum(logicalFile);

        try {

            logicalFile.setMd5Checksum(md5checksum);

        } catch (ChecksumMismatchException e) {

            e.setAlgorithm(ChecksumMismatchException.Algorithm.MD5);
            e.setNewChecksum(md5checksum);
            e.setLogicalFileId(logicalFile.getIdentifier());
            this.exceptionHandlingService.handledException(new UnhandledException(e));
        }

        logicalFile.setMd5ChecksumDate(new Date());
    }

    private String tryCalculateMD5Checksum(LogicalFile logicalFile) {

        InputStream inputStream = null;
        String checksumString = null;

        try {

            inputStream = this.fileInputStreamStrategy.getFileInputStream(logicalFile);
            MessageDigest digest_md5 = this.messageDigestFactory.getMessageDigest("MD5");
            updateDigest(digest_md5, inputStream);

            checksumString = digestBytesToHexString(digest_md5.digest());

        } catch (Exception e) {

            UnhandledException unhandledException = new UnhandledException(e);
            unhandledException.put("Logical File ID", null);
            this.exceptionHandlingService.handledException(unhandledException);

        } finally {

            if (inputStream != null) {

                try {
                    inputStream.close();
                } catch (IOException e) {
                    // do nothing.
                }
            }
        }

        return checksumString;
    }

    private void updateDigest(MessageDigest md5Digest, InputStream inputStream) throws IOException {

        int nread;
        byte[] dataBytes = new byte[1024];
        while ((nread = inputStream.read(dataBytes)) != -1) {
            md5Digest.update(dataBytes, 0, nread);
        }
    }

    private String digestBytesToHexString(byte[] digestBytes) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < digestBytes.length; i++) {
            sb.append(Integer.toString((digestBytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

}
