import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.*;

public class ImageModel extends Model implements Serializable {

    private File file;
    private Date date;
    private int rating;

    public ImageModel(File file) {
        this.file = file;
        try {
            BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            this.date = new Date(attr.creationTime().toMillis());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.rating = 0;
    }

    public Date getDate() {
        return date;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
        notifyObservers();
    }

    public String getFilename() {
        return file.getName();
    }

    public Image getImage() throws IOException {
        return ImageIO.read(file);
    }
}
