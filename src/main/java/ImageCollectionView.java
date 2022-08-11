import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ImageCollectionView extends JPanel implements Observer {

    private final static int MAXSIDE = 200;

    private ImageCollectionModel model;
    private DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    private boolean isGrid;
    private int minRating;

    public ImageCollectionView(ImageCollectionModel model) {
        this.model = model;
        model.addObserver(this);
        this.isGrid = true;
        this.minRating = 0;
        reAddImageView();
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                update(model);
            }
        });
    }

    private void reAddImageView() {
        this.removeAll();
        if (isGrid) {
            this.setLayout(new GridLayout(0, Math.max(1, getWidth() / (MAXSIDE + 20))));
        } else {
            this.setLayout(new GridLayout(0, 1));
            this.setMinimumSize(new Dimension(300,300));
        }
        for (ImageModel imageModel: model.getImageModels()) {
            if (imageModel.getRating() >= minRating) {
                JPanel itemPanel = new JPanel();
                if (isGrid) {
                    itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
                } else {
                    itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.X_AXIS));
                }

                Image image = null;
                try {
                    image = imageModel.getImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int width = image.getWidth(null);
                int height = image.getHeight(null);

                int scaledWidth = (width >= height ? MAXSIDE : (int) (width / (height / (double) MAXSIDE)));
                int scaledHeight = (height >= width ? MAXSIDE : (int) (height / (width / (double) MAXSIDE)));
                Image scaledImage = image.getScaledInstance(scaledWidth, scaledHeight, java.awt.Image.SCALE_SMOOTH);

                JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                imageLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        ImageView frame = new ImageView(imageModel);
                        frame.setVisible(true);
                    }
                });
                imageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                itemPanel.add(imageLabel);

                JPanel textPanel = new JPanel();
                textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.PAGE_AXIS));

                JLabel filenameLabel = new JLabel(imageModel.getFilename());
                filenameLabel.setMaximumSize(new Dimension(200, 30));
                textPanel.add(filenameLabel);

                JLabel dateLabel = new JLabel(df.format(imageModel.getDate()));
                textPanel.add(dateLabel);
                textPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

                JPanel starsBar = new StarsBar(imageModel);
                starsBar.setAlignmentX(Component.LEFT_ALIGNMENT);
                textPanel.add(starsBar);

                JButton reset = new JButton("Reset rating");
                reset.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ((StarsBar) starsBar).setRating(0);
                        update(model);
                    }
                });
                textPanel.add(reset);

                itemPanel.add(textPanel);

                this.add(itemPanel);
            }
        }
    }

    @Override
    public void update(Object observable) {
        if (observable instanceof FotagModel) {
            this.isGrid = ((FotagModel) observable).isGrid();
            this.minRating = ((FotagModel) observable).getMinRating();
        }
        reAddImageView();
        this.revalidate();
        this.repaint();
    }
}
