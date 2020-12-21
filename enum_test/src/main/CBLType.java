package main;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum CBLType {
	MAX(1 , "max" , Arrays.asList("max" , "MAX")),
	MID(2 , "mid" , Arrays.asList("mid" , "MID")),
	ALT_ONLY_THE_DAY(3 , "alt_only_the_day" , Arrays.asList("alt_only_the_day" , "ALT_ONLY_THE_DAY")),
	ALT_30DAYS(4 , "alt_30days", Arrays.asList("alt_30days" , "ALT_30DAYS")),
	ALT_NO_ADJUSTED(5,"alt_no_adjusted" , Arrays.asList("alt_no_adjusted" , "ALT_NO_ADJUSTED")),
	EMPTY(0, "empty", Collections.EMPTY_LIST);

	private int type1Value;
	private String type2Value;
	private List<String> codeList;

	CBLType(int type1Value, String type2Value , List<String> codeList) {
		this.type1Value = type1Value;
		this.type2Value = type2Value;
		this.codeList = codeList;
	}

	public static CBLType findByCBLCode(String code){
		return Arrays.stream(CBLType.values())
			.filter(cblGroup -> cblGroup.hasPayCode(code))
			.findAny()
			.orElse(EMPTY);
	}

	public boolean hasPayCode(String name){
		return codeList.stream()
			.anyMatch(code -> code.equals(name));
	}

	public int getType1Value() {
		return type1Value;
	}

	public String getType2Value() {
		return type2Value;
	}
}
