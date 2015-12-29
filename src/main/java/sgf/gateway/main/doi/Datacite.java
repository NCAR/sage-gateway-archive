package sgf.gateway.main.doi;

public class Datacite {

    private String creator;
    private String title;
    private String publisher;
    private String publicationyear;
    private String resourcetype;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublicationyear() {
        return publicationyear;
    }

    public void setPublicationyear(String publicationyear) {
        this.publicationyear = publicationyear;
    }

    public String getResourcetype() {
        return resourcetype;
    }

    public void setResourcetype(String resourcetype) {
        this.resourcetype = resourcetype;
    }

    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("datacite.creator: " + this.creator + "\n");
        stringBuilder.append("datacite.title: " + this.title + "\n");
        stringBuilder.append("datacite.publisher: " + this.publisher + "\n");
        stringBuilder.append("datacite.publicationyear: " + this.publicationyear + "\n");
        stringBuilder.append("datacite.resourcetype: " + this.resourcetype + "\n");

        return stringBuilder.toString();
    }
}
