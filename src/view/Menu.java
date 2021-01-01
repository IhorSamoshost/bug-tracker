package view;

public interface Menu {
    void show();

    void back();

    default void exitProgram() {
        System.exit(0);
    }
}
