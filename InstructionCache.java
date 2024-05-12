
public class InstructionCache {
	public static Word[] SRAM = new Word[9];
	public InstructionCache() {
		for(int i = 0; i < 9; i++) {
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
			if(addy <= SRAM[0].getSigned() || addy > SRAM[0].getSigned()+8) {
				boolean L2 = false;
				int L2Addy = 0;
				for(int i = 0; i < 4; i++) {
					if(!(addy < L2Cache.read(new Word(i*8)).getSigned() || addy >  L2Cache.read(new Word(i*8)).getSigned()+8*(i+1))) {
						L2 = true;
						L2Addy = i;
						break;
					}
				}
				if(L2) {
					for(int i = L2Addy; i < L2Addy+9; i++) {
						InstructionCache.write(new Word(i), L2Cache.read(new Word(i)));
					}
					Processor.currentClockCycle+=350;
				}
				else {
					SRAM[0] = new Word(addy);
					for(int i = addy; i < addy+9; i++) {
						InstructionCache.write(new Word(i), MainMemory.read(new Word(i)));
					}
					Processor.currentClockCycle+=350;
				}
			}
			else {
				Processor.currentClockCycle+=10;
				ret.copy(SRAM[(int) address.getUnsigned()]);
			}
			return MainMemory.read(address);
			//return ret;
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
