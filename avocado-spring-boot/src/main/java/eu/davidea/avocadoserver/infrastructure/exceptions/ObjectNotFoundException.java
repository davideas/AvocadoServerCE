package eu.davidea.avocadoserver.infrastructure.exceptions;

/**
 * @author Davide Steduto
 * @since 08/08/2016
 */
public class ObjectNotFoundException extends ServiceException {

    private ObjectNotFoundException() {
        super(ExceptionCode.OBJECT_NOT_FOUND, null);
    }

    public static ObjectNotFoundException noRestaurantFound(Long restaurantId) {
        ObjectNotFoundException exception = new ObjectNotFoundException();
        exception.setFriendlyMessage("Restaurant not found with the provided id")
                .setTarget("Restaurant")
                .addDetail("id=" + restaurantId);
        return exception;
    }

    public static ObjectNotFoundException noRestaurantFound(String name) {
        ObjectNotFoundException exception =  new ObjectNotFoundException();
        exception.setFriendlyMessage("No Restaurant found with the provided name")
                .setTarget("Restaurant")
                .addDetail("name=" + name);
        return exception;
    }

    public static ObjectNotFoundException noRestaurantFound(Float latitude, Float longitude, Float radius) {
        ObjectNotFoundException exception = new ObjectNotFoundException();
        exception.setFriendlyMessage("No Restaurant found nearby")
                .setTarget("Restaurant")
                .addDetail("latitude=" + latitude)
                .addDetail("longitude=" + longitude)
                .addDetail("radius=" + radius);
        return exception;
    }

}