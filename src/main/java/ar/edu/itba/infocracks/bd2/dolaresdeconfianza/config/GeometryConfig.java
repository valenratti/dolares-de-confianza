package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.config;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

public class GeometryConfig {

    private final static GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    public static GeometryFactory geometryFactory(){
        return geometryFactory;
    }

    public static Point pointFromCoordinates(double x, double y){
        return geometryFactory().createPoint(new Coordinate(x,y));
    }

    // Haversine Distance formula: https://en.wikipedia.org/wiki/Haversine_formula
    // Esta en el docs de la clase de Postgis
    public static double getDistanceInMeters(Coordinate A, Coordinate B) {
        final int R = 6371; // Earth Radius in km, use 3959 if you want in miles
        double latDistance = toRad(B.y-A.y);
        double lonDistance = toRad(B.x-A.x);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(A.y)) * Math.cos(toRad(B.y)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c * 1000;
    }

    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }

}
