package prova_canvas;

public class Main {

	public static void main(String[] args) {
		Frame f=new Frame();
		Thread_move th=new Thread_move(f, f.getCanvas());
		f.setVisible(true);
		th.start();
	}

}
