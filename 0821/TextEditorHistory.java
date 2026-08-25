import java.util.ArrayDeque;
import java.util.Deque;

public class TextEditorHistory {
    private final Deque<String> undoStack = new ArrayDeque<>();
    private final Deque<String> redoStack = new ArrayDeque<>();

    public void type(String text) {
        if (text != null && !text.isBlank()) {
            undoStack.push(text);
            redoStack.clear();
            System.out.println("輸入: \"" + text + "\"");
        }
        printState();
    }

    public void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("無法 Undo：已無歷史紀錄");
            return;
        }
        String text = undoStack.pop();
        redoStack.push(text);
        System.out.println("Undo: \"" + text + "\"");
        printState();
    }

    public void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("無法 Redo：已無可重做紀錄");
            return;
        }
        String text = redoStack.pop();
        undoStack.push(text);
        System.out.println("Redo: \"" + text + "\"");
        printState();
    }

    private void printState() {
        System.out.println("  Undo Stack: " + undoStack);
        System.out.println("  Redo Stack: " + redoStack);
        System.out.println("----------------------------------------");
    }

    public static void main(String[] args) {
        TextEditorHistory editor = new TextEditorHistory();

        editor.type("Hello");
        editor.type("World");
        editor.undo();
        editor.type("Java");
        editor.undo();
        editor.redo();
        editor.undo();
        editor.undo();
        editor.undo(); // 測試空 undo
        editor.redo();
        editor.redo();
        editor.redo(); // 測試空 redo
    }
}