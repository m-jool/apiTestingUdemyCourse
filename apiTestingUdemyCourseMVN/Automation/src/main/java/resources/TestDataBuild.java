package resources;

import pojo.AddPlace;
import pojo.Location;

import java.util.ArrayList;
import java.util.List;

public class TestDataBuild {
    public AddPlace addPlacePayload(String name, String lang, String address) {
        AddPlace addPlace = new AddPlace();
        addPlace.setAccuracy(50);
        addPlace.setLanguage(lang);
        addPlace.setName(name);
        addPlace.setPhone_number("(+91) 983 893 3937");
        addPlace.setAddress(address);
        addPlace.setWebsite("http://google.com");

        List<String> list = new ArrayList<String>();
        list.add("shoe park");
        list.add("shop");
        addPlace.setTypes(list);

        Location location = new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);
        addPlace.setLocation(location);

        return addPlace;
    }

    public String deletePlacePayload(String placeID){
        return "{\"place_id\":\""+placeID+"\"}";
    }
}
