import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import javax.swing.filechooser.*;
import java.awt.event.*;
import java.io.File;

public class Toolbar extends JPanel implements Observer {

    private FotagModel model;
    private JFileChooser fc = new JFileChooser();

    public Toolbar(FotagModel model) {
        this.model = model;
        model.addObserver(this);
        this.setLayout(new BorderLayout());
        JPanel left = new JPanel();
        JPanel right = new JPanel();

        JToggleButton grid = new JToggleButton();
        ImageIcon gridImage = new ImageIcon("gridIcon.jpeg");
        Image gridIcon = gridImage.getImage().getScaledInstance( 30, 30,  java.awt.Image.SCALE_SMOOTH ) ;
        grid.setIcon(new ImageIcon(gridIcon));
        grid.setSelected(true);


        JToggleButton list = new JToggleButton();
        ImageIcon listImage = new ImageIcon("listIcon.jpeg");
        Image listIcon = listImage.getImage().getScaledInstance( 30, 30,  java.awt.Image.SCALE_SMOOTH ) ;
        list.setIcon(new ImageIcon(listIcon));
        list.setSelected(false);

        grid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setGrid(true);
                grid.setSelected(true);
                list.setSelected(false);
            }
        });
        list.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setGrid(false);
                grid.setSelected(false);
                list.setSelected(true);
            }
        });

        JLabel fotag = new JLabel("Fotag!");
        fotag.setFont(new Font("Serif", Font.BOLD, 18));

        FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
        fc.setMultiSelectionEnabled(true);
        fc.addChoosableFileFilter(imageFilter);
        fc.setAcceptAllFileFilterUsed(false);

        JButton load = new JButton();
        ImageIcon loadImage = new ImageIcon("load.png");
        Image loadIcon = loadImage.getImage().getScaledInstance( 30, 30,  java.awt.Image.SCALE_SMOOTH );
        load.setIcon(new ImageIcon(loadIcon));
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(Toolbar.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    for (File file: fc.getSelectedFiles()) {
                        model.addImage(file);
                    }
                }
            }
        });

        JLabel filter = new JLabel("Filter by:");
        filter.setFont(new Font("", Font.PLAIN, 12));

        StarsBar starsBar = new StarsBar(model);

        JButton reset = new JButton("Reset filter");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                starsBar.setRating(0);
            }
        });

        left.add(grid);
        left.add(list);
        left.add(fotag);
        this.add(left, BorderLayout.WEST);

        right.add(load);
        right.add(filter);
        right.add(starsBar);
        right.add(reset);
        this.add(right, BorderLayout.EAST);
    }

    @Override
    public void update(Object observable) {

    }
}
