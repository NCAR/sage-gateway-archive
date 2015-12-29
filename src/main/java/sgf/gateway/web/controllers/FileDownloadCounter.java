package sgf.gateway.web.controllers;

public class FileDownloadCounter {

    private static int diskDownloadCount = 0;

    private static int srmDownloadCount = 0;

    public static void diskDownloadStarted() {

        diskDownloadCount++;
    }

    public static void diskDownloadCompleted() {

        diskDownloadCount--;
    }

    public static int getDiskDownloadCount() {

        return diskDownloadCount;
    }

    public static void srmDownloadStarted() {

        srmDownloadCount++;
    }

    public static void srmDownloadCompleted() {

        srmDownloadCount--;
    }

    public static int getSrmDownloadCount() {

        return srmDownloadCount;
    }
}
