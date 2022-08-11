import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ImageCollectionModel extends Model implements Serializable {

    private List<ImageModel> imageModels;

    public ImageCollectionModel() {
        this.imageModels = new ArrayList<>();
    }

    public void addImage(File file) {
        imageModels.add(new ImageModel(file));
        notifyObservers();
    }

    public List<ImageModel> getImageModels() {
        return imageModels;
    }
}
