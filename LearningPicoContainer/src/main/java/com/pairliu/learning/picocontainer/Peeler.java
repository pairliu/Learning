package com.pairliu.learning.picocontainer;

import org.picocontainer.Startable;

public class Peeler implements Startable {
	private final Peelable peelable;

	public Peeler(Peelable peelable) {
		this.peelable = peelable;
	}

	public void start() {
		peelable.peel();
	}

	public void stop() {

	}
}
