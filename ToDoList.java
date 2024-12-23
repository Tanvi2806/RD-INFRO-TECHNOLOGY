import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class Task extends JPanel{
    private JLabel index;
    private JTextField taskName;
    private JButton done;
    private boolean checked= false;

    Task()
    {
        this.setLayout(new BorderLayout());
        this.add(index = new JLabel(""), BorderLayout.WEST);
        this.add(taskName = new JTextField("Enter "), BorderLayout.CENTER);
        this.add (done = new JButton("Done"), BorderLayout.EAST);

        done.addActionListener(e -> {
            checked = true ;
            taskName.setEditable(false);
        });

    }
    public void setIndex(int num)
    {
        index.setText(num + "");
    }
    public boolean isChecked()
    {
        return checked;
    }

}

class List extends JPanel{
    List()
    {
        this.setLayout(new GridLayout(10,1,5,5));

    }
public void refreshIndexes()
{
    Component[] tasks = this.getComponents();
    for(int i=0;i<tasks.length; i++){
        if (tasks[i] instanceof Task) ((Task) tasks[i]).setIndex(i+1);
    }

}

public void clearCompledted()
{
    for(Component task : getComponents())
    {
        if (task instanceof Task && ((Task) task).isChecked()) remove(task);
    }
    refreshIndexes();
}

}

public class ToDoList extends JFrame{
    private List taskList;

    ToDoList()
    {
        this.setTitle("To-Do List");
        this.setSize(400,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        this.add(new JLabel("To-Do List",SwingConstants.CENTER),BorderLayout.NORTH);

        taskList = new List();
        this.add(new JScrollPane(taskList), BorderLayout.CENTER);

        JPanel footer = new JPanel();
        JButton addTask = new JButton("Add Task");
        JButton clearTask = new JButton("Clear Finished");

        footer.add(addTask);
        footer.add(clearTask);

        this.add(footer, BorderLayout.SOUTH);
        addTask.addActionListener(e -> {
            Task task = new Task();
            taskList.add(task);
            taskList.refreshIndexes();
            taskList.revalidate();

        });
        clearTask.addActionListener(e -> {
            taskList.clearCompledted();
            taskList.repaint();
        });
        this.setVisible(true);
    }
    public static void main(String[] args)
    {
        new ToDoList();
    }
}