package sgf.gateway.utils;

import java.text.DecimalFormat;

public class FileSizeUtils {

    public static final long ONE_KILOBYTE = 1024;

    public static final long ONE_MEGABYTE = ONE_KILOBYTE * ONE_KILOBYTE;

    public static final long ONE_GIGABYTE = ONE_MEGABYTE * ONE_KILOBYTE;

    public static final long ONE_TERABYTE = ONE_GIGABYTE * ONE_KILOBYTE;

    public static final long ONE_PETABYTE = ONE_TERABYTE * ONE_KILOBYTE;

    private FileSizeUtils() {

    }

    public static String getUnit(long fileSize) {

        if (fileSize < 0) {

            throw new IllegalArgumentException("fileSize must be greater then 0 (zero)");
        }

        String unit;

        if (fileSize / ONE_PETABYTE > 0) {

            unit = "PB";

        } else if (fileSize / ONE_TERABYTE > 0) {

            unit = "TB";

        } else if (fileSize / ONE_GIGABYTE > 0) {

            unit = "GB";

        } else if (fileSize / ONE_MEGABYTE > 0) {

            unit = "MB";

        } else if (fileSize / ONE_KILOBYTE > 0) {

            unit = "KB";

        } else {

            unit = "bytes";
        }

        return unit;
    }

    public static String getSize(long fileSize) {

        String size = getSize(fileSize, 2);

        return size;
    }

    public static String getSize(long fileSize, int precision) {

        if (fileSize < 0) {

            throw new IllegalArgumentException("fileSize must be 0 (zero) or greater");
        }

        if (precision < 0) {

            throw new IllegalArgumentException("precision must be 0 (zero) or greater");
        }

        StringBuilder zeros = new StringBuilder();

        for (int i = 0; i < precision; i++) {

            zeros.append("#");
        }

        DecimalFormat decimalFormat = new DecimalFormat("#." + zeros.toString());
        decimalFormat.setDecimalSeparatorAlwaysShown(false);

        String size;

        if (fileSize / ONE_PETABYTE > 0) {

            size = decimalFormat.format(Double.valueOf(fileSize) / ONE_PETABYTE);

        } else if (fileSize / ONE_TERABYTE > 0) {

            size = decimalFormat.format(Double.valueOf(fileSize) / ONE_TERABYTE);

        } else if (fileSize / ONE_GIGABYTE > 0) {

            size = decimalFormat.format(Double.valueOf(fileSize) / ONE_GIGABYTE);

        } else if (fileSize / ONE_MEGABYTE > 0) {

            size = decimalFormat.format(Double.valueOf(fileSize) / ONE_MEGABYTE);

        } else if (fileSize / ONE_KILOBYTE > 0) {

            size = decimalFormat.format(Double.valueOf(fileSize) / ONE_KILOBYTE);

        } else {

            size = decimalFormat.format(fileSize);
        }

        return size;
    }
}
