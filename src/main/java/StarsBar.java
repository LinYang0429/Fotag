import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StarsBar extends JPanel implements Observer {

    private Model model;
    private int rating;
    private List<Star> stars;

    public StarsBar (Model model) {
        this.model = model;
        if (model instanceof ImageModel) {
            this.rating = ((ImageModel) model).getRating();
        } else {
            this.rating = 0;
        }
        this.stars = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Star star = new Star(false, i);
            star.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    rating = star.getIndex() + 1;
                    StarsBar.this.setRating(rating);
                }
            });
            stars.add(star);
            this.add(star);
        }
        this.setPreferredSize(new Dimension(150, 30));
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        drawStars();
    }

    public void setRating(int rating) {
        this.rating = rating;
        drawStars();
        if (model instanceof ImageModel) {
            ((ImageModel) model).setRating(rating);
        } else if (model instanceof FotagModel) {
            ((FotagModel) model).setMinRating(rating);
        }
    }

    private void drawStars() {
        for (int i = 0; i < 5; i++) {
            stars.get(i).setSelected(i < rating);
        }
    }

    @Override
    public void update(Object observable) {

    }
}
