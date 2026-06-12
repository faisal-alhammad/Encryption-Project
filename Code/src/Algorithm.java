public abstract class Algorithm {
    protected String key = "Cipher";
    protected Visualizer visualizer;

    public Algorithm(String key, Visualizer visualizer) {
        this.key = (key == null || key.isEmpty()) ? "Cipher" : key;
        this.visualizer = visualizer;
    }

    public abstract String encrypt(String text);
    public abstract String decrypt(String text);
    public abstract void showSteps();
}
