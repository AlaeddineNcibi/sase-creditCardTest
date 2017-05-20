package edu.lhc.krr.sase.history;

public class WinRun {
	private int id;
	private int windowSize;
	private int nbRun;
	
	
	public WinRun() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WinRun(int id, int windowSize, int nbRun) {
		super();
		this.id = id;
		this.windowSize = windowSize;
		this.nbRun = nbRun;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getWindowSize() {
		return windowSize;
	}
	public void setWindowSize(int windowSize) {
		this.windowSize = windowSize;
	}
	public int getNbRun() {
		return nbRun;
	}
	public void setNbRun(int nbRun) {
		this.nbRun = nbRun;
	}

}
