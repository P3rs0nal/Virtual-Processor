
public class L2Cache {
public static Word[] SRAM = new Word[36];
	
	public L2Cache() {
		for(int i = 0; i < 32; i++) {
			SRAM[i] = new Word(0);
		}
	}
	
	public static Word read(Word address) {
		if(address.getUnsigned() > 1024) {
			address.set((int) (address.getUnsigned()%1024));
		}
		Word ret = new Word();
		try {
			int addy = (int) address.getUnsigned();
			if(addy < SRAM[0].getSigned() || addy > SRAM[0].getSigned()+8) {
				SRAM[0] = new Word(addy);
				for(int i = addy; i < addy+9; i++) {
					SRAM[i] = MainMemory.read(new Word(i));
				}
				Processor.currentClockCycle+=50;
			}
			Processor.currentClockCycle+=350;
			ret.copy(SRAM[(int) address.getUnsigned()]);
			return ret;
		}
		catch(Exception e) {
			return ret;
		}
	}
	
	public static void write(Word address, Word value) {
		if(address.getUnsigned() > 1024) {
			address.set((int) (address.getUnsigned()%1024));
		}
		try {
			SRAM[(int) address.getUnsigned()].copy(value);
		}
		catch(Exception e) {
			SRAM[(int) address.getUnsigned()] = new Word();
			SRAM[(int) address.getUnsigned()].copy(value);
		}
	}
}
