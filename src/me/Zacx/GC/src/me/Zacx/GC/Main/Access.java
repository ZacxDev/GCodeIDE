package me.Zacx.GC.Main;

import me.Zacx.GC.Input.KeyHandle;

public class Access {

	public static Core c;
	public static KeyHandle keyHandle;
	
	public Access(Core c) {
		this.c= c;
		keyHandle = new KeyHandle();
	}
	
}
