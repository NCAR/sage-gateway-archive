package sgf.gateway.web.controllers;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class FileDownloadCounterTest {

    @Test
    public void fileDownloadStartedAndCompleted() {

        FileDownloadCounter.diskDownloadStarted();

        assertThat(FileDownloadCounter.getDiskDownloadCount(), is(equalTo(1)));

        FileDownloadCounter.diskDownloadCompleted();

        assertThat(FileDownloadCounter.getDiskDownloadCount(), is(equalTo(0)));
    }

    @Test
    public void srmDownloadStartedAndCompleted() {

        FileDownloadCounter.srmDownloadStarted();

        assertThat(FileDownloadCounter.getSrmDownloadCount(), is(equalTo(1)));

        FileDownloadCounter.srmDownloadCompleted();

        assertThat(FileDownloadCounter.getSrmDownloadCount(), is(equalTo(0)));
    }
}
