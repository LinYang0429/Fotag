import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ImageView extends JFrame implements Observer {

    private final static int MAXWIDTH = 750;
    private final static int MAXHEIGHT = 550;

    private ImageModel model;

    public ImageView(ImageModel model) {
        this.model = model;
        model.addObserver(this);

        this.setTitle(model.getFilename());
        this.setSize(800, 600);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Image image = null;
        try {
            image = model.getImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int width = image.getWidth(null);
        int height = image.getHeight(null);

        int scaledWidth = (width >= height ? MAXWIDTH : (int) (width / (height / (double) MAXHEIGHT)));
        int scaledHeight = (height >= width ? MAXHEIGHT : (int) (height / (width / (double) MAXWIDTH)));
        Image scaledImage = image.getScaledInstance(scaledWidth, scaledHeight, java.awt.Image.SCALE_SMOOTH);

        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        this.add(imageLabel);
    }

    @Override
    public void update(Object observable) {

    }
}
