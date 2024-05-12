
public class MainMemory {
	
	public static Word[] DRAM = new Word[1024];

	public static Word read(Word address) {
		if(address.getUnsigned() > 1024) {
			address.set((int) (address.getUnsigned()%1024));
		}
		Word ret = new Word();
		try {
			ret.copy(DRAM[(int) address.getUnsigned()]);
			Processor.currentClockCycle+=50;
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
			DRAM[(int) address.getUnsigned()].copy(value);
		}
		catch(Exception e) {
			DRAM[(int) address.getUnsigned()] = new Word();
			DRAM[(int) address.getUnsigned()].copy(value);
		}
		Processor.currentClockCycle+=50;
	}
	
	public static void load(String[] data){
		int index = 0;
		for(String s: data) {
			if(s != null) {
				for(int i = 31; i > 0; i--) {
					if(DRAM[index] == null)
						DRAM[index] = new Word();
					DRAM[index].setBit(i,new Bit(Integer.parseInt(s.substring(i, i+1)) == 1));
				}
			}
			index++;
		}
	}
	
	public static void clear() {
		for(Word w: DRAM) {
			if(w != null)
				if(w.getSigned() != 0)
					w.set(0);
		}
	}
}