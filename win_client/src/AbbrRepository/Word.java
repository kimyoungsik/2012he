package AbbrRepository;

public class Word {
	private int id;
	private String orig;//원래글자
	private String abbr;//축약글자
	private String converter; //바꿀글자
	private int frequency;
	
	public Word(String orig, String abbr, String conv){
		id = new Integer(0);
		this.orig = orig;
		this.abbr = abbr;
		this.converter = conv;
		frequency = new Integer(0);
	}
	
	public Word(int id, String orig, String abbr, String conv){
		this.id = id;
		this.orig = orig;
		this.abbr = abbr;
		this.converter = conv;
		this.frequency = new Integer(0);
	}
	
	public Word(int id, String orig, String abbr, String conv, int freq){
		this.id = id;
		this.orig = orig;
		this.abbr = abbr;
		this.converter = conv;
		this.frequency = freq;
	}
	
	public int getId(){
		return id;
	}
	
	public String getOrig(){
		return orig;
	}
	
	public String getAbbr(){
		return abbr;
	}
	
	public String getConverter(){
		return converter;
	}
	
	public int getFrequency(){
		return frequency;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setOrig(String orig){
		this.orig = orig;
	}
	
	public void setAbbr(String abbr){
		this.abbr = abbr;
	}
	
	public void setConverter(String conv){
		this.converter = conv;
	}
	
	public void setFrequency(int freq){
		this.frequency = freq;
	}
}
