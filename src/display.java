import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by malefikus on 27/07/17.
 * 界面
 */
public class display {
    private display() {
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Fileio imp = new Fileio();
//                imp.Import();
                list1.setListData(imp.output.toArray());
                textArea1.setText("Imported!");
            }
        });
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Fileio exp = new Fileio();
                exp.Export();
                textArea1.setText("Exported!");
            }
        });
        groupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Fileio gro = new Fileio();
                list1.setListData(gro.grouped.entrySet().toArray());
                textArea1.setText("Grouped!");
            }
        });
        numButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                Fileio num = new Fileio();
//                list1.setListData(num.Num().toArray());
//                Assign ass = new Assign();
//                list1.setListData(ass.AssignNum().toArray());
//                Merge mer = new Merge();
//                list1.setListData(mer.mergeGroup().toArray());
                Room room = new Room();
                list1.setListData(room.assignRoom().toArray());
                textArea1.setText("Number Displayed!");
            }
        });
    }

    // creating GUI, the entrance of the system
    public static void main(String[] args) {
        JFrame frame = new JFrame("中期答辩分组");
        frame.setContentPane(new display().panel_main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JPanel panel_main;
    private JButton importButton;
    private JButton exportButton;
    private JList list1;
    private JTextArea textArea1;
    private JButton numButton;
    private JButton groupButton;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}