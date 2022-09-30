package resources;

public enum APIresources {
    AddPlaceApi("/maps/api/place/add/json"),
    GetPlaceApi("/maps/api/place/get/json"),
    DeletePlaceApi("/maps/api/place/delete/json");

    private String path;

    APIresources(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}


