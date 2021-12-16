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

}
