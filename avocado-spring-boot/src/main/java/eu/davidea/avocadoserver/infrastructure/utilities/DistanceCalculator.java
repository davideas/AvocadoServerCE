package eu.davidea.avocadoserver.infrastructure.utilities;

/**
 * This routine calculates the distance between two points (given the latitude/longitude of those points).
 * <p>
 * Definitions:<br>
 * - South latitudes are <i>negative</i>, east longitudes are <i>positive</i>
 * <p>
 * Passed to function:
 * <pre>
 *   lat1, lon1 = Latitude and Longitude of point 1 (in decimal degrees)
 *   lat2, lon2 = Latitude and Longitude of point 2 (in decimal degrees)
 *   unit = the unit you desire for results
 *         where: 'M' is statute miles (default)
 *                'K' is kilometers
 *                'N' is nautical miles
 * </pre>
 * Worldwide cities and other features databases with latitude longitude are available at
 * <a href="http://www.geodatasource.com">http://www.geodatasource.com</a>.
 * <p>
 * For enquiries, please contact <a href="mailto:sales@geodatasource.com">sales@geodatasource.com</a>
 * <p>
 * GeoDataSource.com (C) All Rights Reserved 2017
 */
public class DistanceCalculator {

    public enum Unit {
        MILE, KILOMETER, NAUTICAL_MILE
    }

    public static void main(String[] args) throws java.lang.Exception {
        System.out.println(distance(32.9697, -96.80322, 29.46786, -98.53506, Unit.MILE) + " Miles\n");
        System.out.println(distance(32.9697, -96.80322, 29.46786, -98.53506, Unit.KILOMETER) + " Kilometers\n");
        System.out.println(distance(32.9697, -96.80322, 29.46786, -98.53506, Unit.NAUTICAL_MILE) + " Nautical Miles\n");
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2, Unit unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        switch (unit) {
            case NAUTICAL_MILE:
                dist *= 0.8684;
            case KILOMETER:
                dist *= 1.609344;
            default:
                dist *= 60 * 1.1515;
        }
        return dist;
    }

    /**
     * Converts decimal degrees to radians.
     */
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /**
     * Converts radians to decimal degrees
     */
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

}