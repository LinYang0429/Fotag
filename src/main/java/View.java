import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

public class View extends JFrame implements Observer {

    private FotagModel model;

    /**
     * Create a new View.
     */
    public View(FotagModel model) {
        // Hook up this observer so that it will be notified when the model
        // changes.
        this.model = model;
        model.addObserver(this);
        model.loadHistory();

        // Set up the window.
        this.setTitle("Fotag");
        this.setMinimumSize(new Dimension(300, 400));
        this.setSize(1280, 720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        Toolbar toolbar = new Toolbar(model);
        this.add(toolbar, BorderLayout.NORTH);

        ImageCollectionView imageCollectionView = new ImageCollectionView(model.getImageCollectionModel());
        model.addObserver(imageCollectionView);
        JScrollPane scrollPane = new JScrollPane(imageCollectionView);
        scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPane, BorderLayout.CENTER);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we)
            {
                model.saveHistory();
            }
        });

        setVisible(true);
    }

    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
        if (observable instanceof FotagModel) {
            if (((FotagModel) observable).isGrid()) {
                this.setMinimumSize(new Dimension(300, 400));
            } else {
                this.setMinimumSize(new Dimension(550, 400));
            }
        }
    }
}
