package eu.davidea.avocadoserver.infrastructure.exceptions;

/**
 * @author Davide Steduto
 * @since 08/08/2016
 */
public class ObjectNotFoundException extends BusinessException {

    private ObjectNotFoundException() {
        super(ExceptionCode.NOT_FOUND, null);
    }

    public static ObjectNotFoundException noRestaurantFound(Long restaurantId) {
        return new ObjectNotFoundException()
                .setFriendlyMessage("Restaurant not found with the provided id")
                .setTarget("Restaurant")
                .addDetail("id=" + restaurantId);
    }

    public static ObjectNotFoundException noRestaurantFound(String name) {
        return new ObjectNotFoundException()
                .setFriendlyMessage("No Restaurant found with the provided name")
                .setTarget("Restaurant")
                .addDetail("name=" + name);
    }

    public static ObjectNotFoundException noRestaurantFound(Float latitude, Float longitude, Float radius) {
        return new ObjectNotFoundException()
                .setFriendlyMessage("No Restaurant found nearby")
                .setTarget("Restaurant")
                .addDetail("latitude=" + latitude)
                .addDetail("longitude=" + longitude)
                .addDetail("radius=" + radius);
    }

    @Override
    public ObjectNotFoundException setFriendlyMessage(String friendlyMessage) {
        super.setFriendlyMessage(friendlyMessage);
        return this;
    }

    @Override
    public ObjectNotFoundException setTarget(String target) {
        super.setTarget(target);
        return this;

    }

    @Override
    public ObjectNotFoundException addDetail(String detail) {
        super.addDetail(detail);
        return this;
    }

}