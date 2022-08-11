import java.io.*;

public class FotagModel extends Model {

    private final static String HISTORYPATH  = "history";

    private ImageCollectionModel imageCollectionModel;
    private boolean isGrid;
    private int minRating;


    public FotagModel() {
       this.imageCollectionModel = new ImageCollectionModel();
       this.isGrid = true;
    }

    public ImageCollectionModel getImageCollectionModel() {
        return imageCollectionModel;
    }

    public void addImage(File file) {
        imageCollectionModel.addImage(file);
    }

    public boolean isGrid() {
        return isGrid;
    }

    public void setGrid(boolean grid) {
        isGrid = grid;
        notifyObservers();
    }

    public int getMinRating() {
        return minRating;
    }

    public void setMinRating(int minRating) {
        this.minRating = minRating;
        notifyObservers();
    }

    public void saveHistory() {
        File file = new File(HISTORYPATH);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(imageCollectionModel);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadHistory() {
        File file = new File(HISTORYPATH);
        if (file.isFile()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                ObjectInputStream ois = new ObjectInputStream(fis);
                imageCollectionModel = (ImageCollectionModel) ois.readObject();
                ois.close();
                fis.close();
                notifyObservers();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
