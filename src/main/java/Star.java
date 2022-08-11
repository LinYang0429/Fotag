import javax.swing.*;
import java.awt.*;

public class Star extends JButton {

    private boolean selected;
    private int index;

    public Star(boolean selected, int index) {
        this.selected = selected;
        this.index = index;
        this.setPreferredSize(new Dimension(20, 20));
        updateIcon();
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        updateIcon();
        this.revalidate();
    }

    public int getIndex() {
        return index;
    }

    private void updateIcon() {
        if (this.selected) {
            ImageIcon selectedImage = new ImageIcon("starSelected.png");
            Image selectedIcon = selectedImage.getImage().getScaledInstance( 20, 20,  java.awt.Image.SCALE_SMOOTH );
            this.setIcon(new ImageIcon(selectedIcon));
        } else {
            ImageIcon selectedImage = new ImageIcon("starUnselected.png");
            Image unselectedIcon = selectedImage.getImage().getScaledInstance( 20, 20,  java.awt.Image.SCALE_SMOOTH );
            this.setIcon(new ImageIcon(unselectedIcon));
        }
    }

}
