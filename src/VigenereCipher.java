public class VigenereCipher {

	private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789,.() '\"![]/%-_;?-=:"
			+ '\n' + '\r';
	private static final String SIMPLE_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static LetterBag decoded1 = new LetterBag();

	public static void main(String[] args) {
		String cipherText ="Being in the military is full of dangerous unseen anywhere else.  Sure, people could die from a car crash or drowning,"
				+ " but how big of a chance is that compared to fighting for your country?  Those men and women bravely walk out there, knowing that"
				+ " the risk of death or injury is so much higher for them.  But why would they do it?  There are so many reasons out there for that"
				+ " question.  The love for their country, the pride in serving, giving back to their country.  They give back to their country in a "
				+ "way that means so much more than any other.  We all know that anyone can be the one to sit in an office and sip coffee while reading"
				+ " the latest celeb news on the internet.  But these brave men and women decide to do something else-serve their country.  Being in "
				+ "the military is no easy job.  The conditions can be terrible, the lack of communication, and the fact that they have to leave their"
				+ " family.  So many people feel that it’s their duty to fight for their country, and it’s heartwarming to know so. Something in my "
				+ "current household really bothers me.  Mom and Dad talk about how we need to search for good safe jobs so we can live long lives and "
				+ "prosper.  I feel though that that statement comes across as selfish.  Everyone wants to have a good safe job.  Everyone wants to live"
				+ " a long and comfortable life.  When you join the military, however, you have to give all those things up.  Fighting for your country "
				+ "isn’t comfortable, it isn’t clean, it isn’t safe.  It doesn’t offer a chance for you to become rich. I feel like the only way to pay a"
				+ " large amount of respect to your country is by fighting for them.  It’s sometimes shocking to see yourselves from a different point of view."
				+ "  Many Americans must think that we asians are selfish, cowardly choosing the safety of an office over volunteering for the military.  "
				+ "How else can we show respect to our country?  Honestly Mom and Dad’s hearts are still sitting in China, the place where they were born. "
				+ " But my heart is different.  It was born and still is here, in America.  In America, and wanting to show 100% respect for my country.";
		vigenereCipherCrackThreeLetter(vigenereCipherEncrypt(cipherText,"key", ALPHABET), ALPHABET);
		System.out.println();
		// System.out.println(vigenereCipherDecrypt(cipherText,
		// ThreeLetterVigenere(cipherText), ALPHABET));
	}
	
	public static String vigenereCipherCrackThreeLetter(String cipher, String alphabet){
		String password="";
		for(int i=0; i<3; i++){
			password+=getPasswordLetter(cipher, i, 3, alphabet);
		}
		System.out.println(password);
		return vigenereCipherDecrypt(cipher, password, alphabet);
	}
	public static String vigenereCipherCrack(String cipher, int passwordLength, String alphabet){
		String password="";
		for(int i=0; i<passwordLength; i++){
			password+=getPasswordLetter(cipher, i, passwordLength, alphabet);
		}
		System.out.println(password);
		return vigenereCipherDecrypt(cipher, password, alphabet);
		
	}
	private static String getPasswordLetter(String cipher, int startIndex, int lengthOfPassword, String alphabet){
		String group=getGroup(cipher, startIndex, lengthOfPassword);
		for(int shift=0; shift<alphabet.length(); shift++){
			String decoded=Cipher.rotationCipherDecrypt(group, shift, alphabet);
			LetterBag bag=getBagForString(decoded);
			if(matchingEnglishFrequencies(bag)){
				String passwordLetter=alphabet.substring(shift, shift+1);
				return passwordLetter;
			}
		}
		return null;
	}
	private static boolean matchingEnglishFrequencies(LetterBag bag){
		String[] highestFrequency=bag.getNMostFrequent(3);
		String[] actualEnglishFrequency={" ","e","t"}; 
		double percentEnglish = (3) *.3;
		int success=0;
		for(int i=0; i<highestFrequency.length; i++){
			if(highestFrequency[i].equals(actualEnglishFrequency[i])) success++;
		}
		if (success > percentEnglish )
			return true;

		return false;
	}
	private static LetterBag getBagForString(String decoded) {
		LetterBag bag=new LetterBag();
		for(int index=0; index<decoded.length(); index++){
			bag.add(decoded.substring(index, index+1));
		}
		return bag;
	}
	public static String vigenereCipherDecrypt(String cipher, String password, String alphabet) {
		String output ="";
		for(int i=0; i< cipher.length(); i++){
			String letter=cipher.substring(i, i+1);
			int index=alphabet.indexOf(letter);
			String shiftLetter=password.substring(i%password.length(), (i%password.length())+1);
			int currentIndex=alphabet.indexOf(shiftLetter); //number to shift
			index=shiftIndexBackward(index, currentIndex, alphabet.length());
			output+=(alphabet.substring(index, index+1));
		}
		return output;
	}
	public static String getGroup(String text, int start, int skip){
		String group = "";
		for(int i=start; i<text.length(); i++){
			group += text.substring(i, i+1);
		}
		return group;
	}

	public static String getLettersOut(String encrypted, int index, int length) {
		String temp = "";
		for (int i = index; i < encrypted.length(); i+=length) {
			temp += encrypted.substring(i, i + 1);
		}
		return temp;
	}

	public static String rotationCipherDecrypt(String cipherText, int shiftAmount, String alphabet) {
		String newString = " ";
		for (int index = 0; index < cipherText.length(); index++) {
			String letter = cipherText.substring(index, index + 1);
			int currentIndex = alphabet.indexOf(letter);
			currentIndex = (currentIndex - shiftAmount) % alphabet.length();
			newString += alphabet.substring(currentIndex, currentIndex + 1);
		}
		return newString;
	}

	public static String findCodeLetter(String temp, String alphabet) {
		for (int shiftAmount = 0; shiftAmount < alphabet.length(); shiftAmount++) {
			String decoded = rotationCipherDecrypt(temp, shiftAmount, alphabet);
			for (int j = 0; j < decoded.length(); j++) {
				decoded1.add(decoded.substring(j, j + 1));
			}

			if (decoded1.getMostFrequent().equals(" ")) {
				return alphabet.substring(shiftAmount, shiftAmount + 1);
			}

		}

		return null;

	}

	public static void vigernereCipherFrequency(String encrypted, String alphabet, int passSize) {
		String code = "";
		for (int i = 0; i < passSize; i++) {
			String group = getLettersOut(encrypted, i, passSize);
			code += findCodeLetter(group, alphabet);
	
		}
		System.out.println(code);
	}
	public static int shiftIndexBackward(int index, int shift, int alphabetLength) {
		while (shift < 0){
			shift += alphabetLength;
		}
		if(index<shift){
			shift = alphabetLength-shift;
			index +=shift;
		}
		else
			index -= shift;
		index %= alphabetLength;
		return index;
	}
	public static String vigenereCipherEncrypt(String plain, String password, String alphabet) {
		String output ="";
		for(int i=0; i< plain.length(); i++){
			String letter=plain.substring(i, i+1);
			int index=alphabet.indexOf(letter);
			String shiftLetter=password.substring(i%password.length(), (i%password.length())+1);
			int currentIndex=alphabet.indexOf(shiftLetter); //number to shift
			index=shiftIndexForward(index, currentIndex, alphabet.length());
			output+=(alphabet.substring(index, index+1));
		}
		return output;
	}
	public static int shiftIndexForward(int index, int shift, int alphabetLength) {
		while (shift < 0){
			shift += alphabetLength;
		}
		index += shift;
		index %= alphabetLength;
		return index;
	}
}