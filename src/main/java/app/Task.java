package app;

public class Task {
    private String text;
    private boolean done;

    public Task(String text) {
        this.text = text;
        this.done = false;
    }

    public String getText() {
        return text;
    }

    public boolean isDone() {
        return done;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void toggleDone() {
        this.done = !this.done;
    }

    @Override
    public String toString() {
        return text;
    }
}
