package untildawn.practice.Model;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser {
    public enum Mode {
        OPEN, SAVE
    }

    public enum SelectionMode {
        FILES, DIRECTORIES
    }

    private Mode mode;
    private SelectionMode selectionMode;
    private FileHandle directory;
    private Listener listener;

    public FileChooser(Mode mode) {
        this.mode = mode;
        this.selectionMode = SelectionMode.FILES;
    }

    public void setDirectory(FileHandle directory) {
        this.directory = directory;
    }

    public void setSelectionMode(SelectionMode selectionMode) {
        this.selectionMode = selectionMode;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void show() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Image");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif"));

        if (directory != null) {
            fileChooser.setCurrentDirectory(directory.file());
        }

        int result = mode == Mode.OPEN ?
            fileChooser.showOpenDialog(null) :
            fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (listener != null) {
                Array<FileHandle> files = new Array<>();
                files.add(new FileHandle(selectedFile));
                listener.selected(files);
            }
        } else {
            if (listener != null) {
                listener.canceled();
            }
        }
    }

    public interface Listener {
        void selected(Array<FileHandle> files);
        void canceled();
    }
}
