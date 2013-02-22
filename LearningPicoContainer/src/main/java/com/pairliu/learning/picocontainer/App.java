package com.pairliu.learning.picocontainer;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) {
		MutablePicoContainer pico = new DefaultPicoContainer();
		//Only add class, not interface. Maybe not abstract class either. 
		pico.addComponent(Apple.class);
		pico.addComponent(Juicer.class);
		pico.addComponent(Peeler.class);
		
		//Then when you want to get an instance, just do something like:
		Juicer juicer = (Juicer)pico.getComponent(Juicer.class);
	}
}
