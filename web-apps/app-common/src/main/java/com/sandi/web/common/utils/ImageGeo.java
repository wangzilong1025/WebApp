package com.sandi.web.common.utils;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.lang.Rational;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;

import java.io.File;

public class ImageGeo {
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;

    public static void ImageGeo(String filename) {
        double lat = 0.0;
        double lon = 0.0;
        double alt = 0.0;
        boolean error = false;
        try {
            error = false;
            File jpegFile = new File(filename);
            Metadata metadata = JpegMetadataReader.readMetadata(jpegFile);

            GpsDirectory gpsdir = (GpsDirectory) metadata
                    .getDirectory(GpsDirectory.class);
            Rational latpart[] = gpsdir
                    .getRationalArray(GpsDirectory.TAG_GPS_LATITUDE);
            Rational lonpart[] = gpsdir
                    .getRationalArray(GpsDirectory.TAG_GPS_LONGITUDE);
            String northing = gpsdir
                    .getString(GpsDirectory.TAG_GPS_LATITUDE_REF);
            String easting = gpsdir
                    .getString(GpsDirectory.TAG_GPS_LONGITUDE_REF);

            try {
                alt = gpsdir.getDouble(GpsDirectory.TAG_GPS_ALTITUDE);
            } catch (Exception ex) {
            }

            double latsign = 1.0d;
            if (northing.equalsIgnoreCase("S"))
                latsign = -1.0d;
            double lonsign = 1.0d;
            if (easting.equalsIgnoreCase("W"))
                lonsign = -1.0d;
            lat = (Math.abs(latpart[0].doubleValue())
                    + latpart[1].doubleValue() / 60.0d + latpart[2]
                    .doubleValue() / 3600.0d) * latsign;
            lon = (Math.abs(lonpart[0].doubleValue())
                    + lonpart[1].doubleValue() / 60.0d + lonpart[2]
                    .doubleValue() / 3600.0d) * lonsign;

            if (Double.isNaN(lat) || Double.isNaN(lon))
                error = true;
        } catch (Exception ex) {
            error = true;
        }
        System.out.println(filename + ": (" + lat + ", " + lon + ")");
    }


}
