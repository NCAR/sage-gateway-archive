package sgf.gateway.model.metrics.factory.impl;

import org.junit.Before;
import org.junit.Test;
import org.safehaus.uuid.UUID;
import sgf.gateway.dao.NewInstanceIdentifierStrategy;
import sgf.gateway.dao.impl.hibernate.VersionedUUIDIdentifier;
import sgf.gateway.model.metrics.FileDownload;
import sgf.gateway.service.metrics.FileDownloadDetails;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class FileDownloadFactoryImplTest {

    private TransformStep mockTransformStep1;
    private TransformStep mockTransformStep2;
    private TransformStep mockTransformStep3;

    private List<TransformStep> transformSteps;

    private NewInstanceIdentifierStrategy mockNewInstanceIdentifierStrategy;

    private FileDownloadDetails mockFileDownloadDetails;

    @Before
    public void setup() {

        mockTransformStep1 = mock(TransformStep.class);
        mockTransformStep2 = mock(TransformStep.class);
        mockTransformStep3 = mock(TransformStep.class);

        transformSteps = new ArrayList<TransformStep>();
        transformSteps.add(mockTransformStep1);
        transformSteps.add(mockTransformStep2);
        transformSteps.add(mockTransformStep3);

        VersionedUUIDIdentifier mockVersionedUUIDIdentifier = mock(VersionedUUIDIdentifier.class);
        when(mockVersionedUUIDIdentifier.getIdentifierValue()).thenReturn(UUID.valueOf("24769510-4569-11e2-bcfd-0800200c9a66"));
        when(mockVersionedUUIDIdentifier.getVersion()).thenReturn(1);

        mockNewInstanceIdentifierStrategy = mock(NewInstanceIdentifierStrategy.class);
        when(mockNewInstanceIdentifierStrategy.generateNewIdentifier(FileDownload.class)).thenReturn(mockVersionedUUIDIdentifier);

        mockFileDownloadDetails = mock(FileDownloadDetails.class);
    }

    @Test
    public void transformStepsCalled() {

        FileDownloadFactoryImpl fileDownloadFactoryImpl = new FileDownloadFactoryImpl(mockNewInstanceIdentifierStrategy, transformSteps);
        fileDownloadFactoryImpl.createFileDownload(mockFileDownloadDetails);

        verify(mockTransformStep1).transform(any(FileDownload.class));
        verify(mockTransformStep2).transform(any(FileDownload.class));
        verify(mockTransformStep3).transform(any(FileDownload.class));
    }
}
