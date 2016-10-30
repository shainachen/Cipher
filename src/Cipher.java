import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class Cipher {
	public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789,.() '\"![]/%-_;?=:" + '\n' + '\r';
	private static final String SIMPLE_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	// Set this variable to the default alphabet you wish to use
	private static final String DEFAULT_ALPHABET = ALPHABET;
	private static Dictionary dictionary=Dictionary.buildDictionary("/Users/gwc/Documents/workspace/CipherBlankTemplate/words.txt");
	
	public static void main(String[] args){
//		System.out.println(rotationCipherEncrypt("help me i am under attack", 3, ALPHABET));
//		System.out.println(rotationCipherDecrypt("khosphldpxqghudwwdfn", 3, ALPHABET));
//		System.out.println(rotationCipherCrack("khos!ph!l!dp!xqghu!dwwdfn", ALPHABET));
//		String text="S[yw[FovC;xM;xCvpiIsS;xFkxixse;CME[By[LyxiurMG[NosNvif;WSBie;CME[KkCijoLyAx;tCytJo[yxhimeL;iyCmJI[HEwRsjW;xFkxivmIo[6;hGn[ylsTo'i6lyD[Uo[IxsU;ezyyR;sRriPC[GC[MxpW;xFo[QumL;sD;eipvSsxiCsivePqie;wM;gMwtJoBiDlyD[LyfMnCimsSvhizsQCmzvCiErBovQDeLn'i3lCBixC[BsjDovCxxiviTopQ;sD;wIsriDsM%[6;dIxsUj[7EwRsriyrik[xreGBwiyjiDlC;wIsrx;pCFiJ%[YC[Gx[6;oLyAiriioBGCxQ/[yxhiS[JyzC;lGw[TovW;qSmle;fSD[6;vCkpJI[IxsU;rMDlGxkikfMExirmK%[!riikgOEeGxxyxgCC[AyqC;wCmsLn ikrB;xFo[DBmCxhQ;gMwiixiVD'iPeKspW;qCwfCBwe;AFyqiSdTo[QziLD[KI[UrsJo[JsjC;ePyyLn ikvC;wM;gJywC;xM;xFo[Cxhf;MiweW;jCopivmIo[6;oLyAiDlCw ilyR;wRotNsrE;fymoireQ;qyniiwiiBiyvmXo[RreR;xFoCinsixsR;iToriurMG[Ko'iS[AyyJn[LyxizsQCmzvCiozCx[DkxFyqiurMGmLq[RriK%";
//		System.out.println(vigenereCipherEncrypt("I am here to tell you that no, you do not know people. Sure, you may “know” people and can easily justify that like I did above. What we know about others is only the skin of a fruit so large, so complex that nobody could possibly understand. There’s different levels of skin too. I “know” Justin on a “hairs of the skin” level. As in I know he exists, and I love him very much, but I really know nothing about him. The acquaintances come second, and the friends come next. Family members, whom I’ve spent my whole life around, are so close to the end. I may feel like I know them, but stepping back has made me realize that they do not even know me. I could not possibly even fathom knowing them.", "key", ALPHABET));
//		System.out.println(vigenereCipherDecrypt("jsotosleoIqhgFdxvofo", "code", ALPHABET));
//		vigenereCipherCrackThreeLetter(text, ALPHABET);
		
	}
	
	/**
	 * Returns plaintext encrypted by the rotation cipher with a shift of movement.
	 * @param alphabet the alphabet to be used for the encryption
	 * @param plain the plain text to be encrypted.
	 * @param shift the number of characters in ALPHABET to shift by.
	 * @return returns the encrypted plainText.
	 */
	public static String rotationCipherEncrypt(String plain, int shift, String alphabet) {
		String newString="";
		for(int index=0; index<plain.length(); index++){
			String letter=plain.substring(index, index+1);
			int currentIndex=alphabet.indexOf(letter); 
			int index1=currentIndex;
			currentIndex=shiftIndexForward(currentIndex, shift, alphabet.length());
			newString+=alphabet.substring(currentIndex, currentIndex+1);
		}
		return newString;
	}	

	/**
	 * Returns a the result of decrypting cipherText by shiftAmount using the rotation cipher.
	 * @param alphabet the alphabet to be used for the encryption
	 * @param cipher the encrypted text.
	 * @param shift the key to decrypt the cipher.
	 * @return returns the decrypted cipherText.
	 */
	public static String rotationCipherDecrypt(String cipher, int shift, String alphabet) {
		String newString="";
//		System.out.println("Alphabet length: "+(alphabet.length()-1));
		for(int index=0; index<cipher.length(); index++){
			String letter=cipher.substring(index, index+1);
			int currentIndex=alphabet.indexOf(letter); 
//			System.out.println("Current Index Initial: "+currentIndex);
			currentIndex=shiftIndexBackward(currentIndex, shift, alphabet.length());
//			System.out.println("Current Index Final: "+currentIndex);
			newString+=alphabet.substring(currentIndex, currentIndex+1);
		}
//		System.out.println(newString);
		return newString;
	}
	
	/**
	 * Returns plaintext encrypted by the vigenere cipher encoded with the String code
	 * @param alphabet the alphabet to be used for the encryption
	 * @param plain the plain text to be encrypted.
	 * @param password the code to use as the encryption key.
	 * @return returns the encrypted plainText.
	 */
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
	
	public static String vigenereCipherEncrypt(String plainText, String code) {
		return vigenereCipherEncrypt(plainText, code, DEFAULT_ALPHABET);
	}

	/**
	 * Returns the result of decrypting cipherText with the key code.
	 * @param alphabet the alphabet to be used for the encryption
	 * @param cipherText the encrypted text.
	 * @param code the decryption key
	 * @return returns the decrypted cipherText.
	 */
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
	
	public static String vigenereCipherDecrypt(String cipherText, String code) {
		return vigenereCipherDecrypt(cipherText, code, DEFAULT_ALPHABET);
	}

	public static String rotationCipherCrack(String cipher, String alphabet) {
		String plaintext = "";
		for (int i = 0; i < cipher.length(); i++) {
			plaintext = rotationCipherDecrypt(cipher, i, alphabet);
			if (isEnglish(plaintext) == true)
				return plaintext;
		}
		return plaintext;
	}
	
	public static String vigenereCipherCrackThreeLetter(String cipher, String alphabet){
		String password="";
		for(int i=0; i<3; i++){
			password+=getPasswordLetter(cipher, i, 3, alphabet);
		}
//		System.out.println("password: "+password);
		return vigenereCipherDecrypt(cipher, password, alphabet);
	}
	
	public static String vigenereCipherCrack(String cipher, int passwordLength, String alphabet){
		String password="";
		for(int i=0; i<passwordLength; i++){
			password+=getPasswordLetter(cipher, i, passwordLength, alphabet);
		}
//		System.out.println(password);
		return vigenereCipherDecrypt(cipher, password, alphabet);
		
	}
	private static String getPasswordLetter(String cipher, int startIndex, int lengthOfPassword, String alphabet){
		String group=getGroup(cipher, startIndex, lengthOfPassword);
		double minScore = Double.MAX_VALUE;
		int bestShift = 0;
		for(int shift=0; shift<alphabet.length(); shift++){
			String decoded=rotationCipherDecrypt(group, shift, alphabet);
			LetterBag bag=getBagForString(decoded);
			double score = similarityToEnglish(bag, lengthOfPassword, alphabet);
			if(Math.abs(score) < minScore){
				minScore = Math.abs(score);
				bestShift = shift;
				//passwordLetter=alphabet.substring(shift, shift+1);
			}
		}
//		System.out.println("best score: "+bestShift);
		String passwordLetter=alphabet.substring(bestShift, bestShift+1);
//		System.out.println("password letter: "+passwordLetter);
		return passwordLetter;
		//return null;
		/*
		 String rotated = Cipher.rotationCipherDecrypt(group.toString(), shift);
			Bag b = new Bag(rotated);
			double score = similarityToEnglish(b, group.length());
			if(Math.abs(score) < minScore){
				//System.out.println(num + "   score: " + score);
				minScore = Math.abs(score);
				bestShift = shift;
			}
		 */
	}
	public static double getFrequency(LetterBag b, String letter, int StringLength ){
		double frequency = ((double)b.getNumOccurances(letter)/(double)StringLength);
		return frequency;
	}
	
	public static double similarityToEnglish(LetterBag b, int stringLength, String alphabet){
		double score = 0;
		double[] englishFrequency = {8.17, 1.49, 2.78, 4.25, 12.7, 2.23, 2.01, 6.09, 6.97, 0.15, 0.77, 4.03, 2.40, 6.75, 7.50, 1.93, 0.09, 5.99, 6.33, 9.1, 2.8, 0.98, 2.36, 0.15, 1.98, 0.07};
		score += (18.3 - getFrequency(b, " ", stringLength));
		for(int i = 0; i < 26; i++){
			String letter = alphabet.substring(i, i+1);
			double diff = (englishFrequency[i] -getFrequency(b, letter, stringLength));
//			System.out.println(letter + " " + diff);
			score += diff;
		}
//		System.out.println("score: " + score);
		return score;
	}
	public static String getGroup(String text, int start, int skip){
		String group = "";
		for(int i=start; i<text.length(); i++){
			group += text.substring(i, i+1);
		}
		return group;
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
	
	/**
	 * Returns the result of encrypting plainText using the int array Permutation cipher as shift amount and String alphabet
	 * @param plainText the plain text to be encrypted.
	 * @param permutation the key to encrypt the cipher.
	 * @param alphabet the alphabet to be used for the encryption
	 * @return returns the encrypted plainText
	 */
	public static String substitutionCipher(String plainText, int[] permutation, String alphabet){
		String newString="";
		for(int index=0; index<plainText.length(); index++){
			String letter=plainText.substring(index, index+1);
			int currentIndex=permutation[alphabet.indexOf(letter)]; 
			newString+=alphabet.substring(currentIndex, currentIndex+1);
		}
		return newString;
	}
	/**
	 * Returns boolean value when testing if permutation is valid (all values in range of array length, all values unique)
	 * @param permutation the permutation to check if valid from
	 * @return returns true if valid permutation, false otherwise
	 */
	public static boolean isValidPermutation(int[] permutation){
		for(int i=0; i<permutation.length; i++){
			if(permutation[i]<0||permutation[i]>permutation.length-1)	return false;  //check value in range (eg. cannot have value 28 if length is 4)
			if(!allUniqueValues(i, permutation))	return false;	//check all values to be unique
		}
		return true;
	}
	/**
	 * Returns boolean value true if all values in array are unique, false otherwise
	 * @param index the current index value of the permutation
	 * @param permutation array of values to check from
	 * @return boolean true if values are unique, false otherwise
	 */
	public static boolean allUniqueValues(int index, int[] permutation){
		for(int i=0; i<permutation.length; i++){
			if(i!=index && permutation[index]==permutation[i])	return false;
		}
		return true;
	}
	
	/**
	 * Returns int array with random unique permutation
	 * @param length the length of the returned permutation
	 * @return int array with randomized unique values
	 */
	public static int[] randomPermutation(int length){
		int[] permutation=new int[length];
		for(int i=0; i<length; i++){	//assign unique value to each index 
			permutation[i]=i;
		}
		for(int i=0; i<length; i++){ 	//randomly swap values
			int randomIndex=(int)(Math.random()*length);
			int temp=permutation[randomIndex];
			permutation[randomIndex]=permutation[i];
			permutation[i]=temp;
		}
		return permutation;
	}
	
	
	
	/**
	 * returns a copy of the input plaintext String with invalid characters
	 * stripped out.
	 * 
	 * @param plaintext
	 *          The plaintext string you wish to remove illegal characters from
	 * @param alphabet
	 *          A string of all legal characters.
	 * @return String A copy of plain with all characters not in alphabet removed.
	 */
	private static String stripInvalidChars(String plaintext, String alphabet) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < plaintext.length(); i++) { 			// loop through plaintext
			if (alphabet.indexOf(plaintext.charAt(i)) >= 0) 	// get index of char
				b.append(plaintext.charAt(i)); 									// if it exists, keep it
			else
																												// otherwise skip it &
				System.out.println("Stripping letter: \"" + plaintext.charAt(i) // display
																																				// a
																																				// message
						+ "\"");
		}
		return b.toString();
	}

	/**
	 * returns true if plaintext is valid English.
	 * 
	 * @param plaintext
	 *          the text you wish to test for whether it's valid English
	 * @return boolean returns true if plaintext is valid English.
	 */
	private static boolean isEnglish(String plaintext) {
		int counter = 0;
		String[] individualWords = getWords(plaintext);
		double percentEnglish = (individualWords.length) *.6;
		for (int i = 0; i < individualWords.length; i++) {
			if (dictionary.isWord(individualWords[i]) == true)
				counter++;
		}
		if (counter > percentEnglish )
			return true;

		return false;
	}
	
	public static String trimSpaces(String input) {
		int startIndex = 0, endIndex = input.length() - 1;
		while (input.substring(startIndex, startIndex + 1).equals(" ")) {
			startIndex++;
		}
		while (input.substring(endIndex, endIndex + 1).equals(" ")) {
			endIndex--;
		}
		return input.substring(startIndex, endIndex + 1);
	}
	
	public static String[] getWords(String input) {
		int arrayCount = 0, spaceCount = 0;
		String temp = "";
		input = trimSpaces(input);
		for (int i = 0; i < input.length() - 1; i++) {
			if (input.substring(i, i + 1).equals(" ") && !input.substring(i + 1, i + 2).equals(" ")) {
				spaceCount++;
			}
		}
		String[] individualWords = new String[spaceCount + 1];
		for (int i = 0; i < input.length(); i++) {
			if (!input.substring(i, i + 1).equals(" ")) {
				temp += input.substring(i, i + 1);
			} else {
				individualWords[arrayCount] = temp;
				while (input.substring(i, i + 1).equals(" ")) {
					i++;
				}
				i--;
				temp ="";
				arrayCount++;
			}
			individualWords[arrayCount] = temp;
		}
		return individualWords;
	}
	
	/**
	* Shifts the index value of a character by the shift amount
	* @param index current index value
	* @param shift number of indexes to shift by
	* @param alphabetLength length of cipher alphabet
	* @return shifted index
	**/
	public static int shiftIndexForward(int index, int shift, int alphabetLength) {
		while (shift < 0){
			shift += alphabetLength;
		}
		index += shift;
		index %= alphabetLength;
		return index;
	}
	
	public static int shiftIndexBackward(int index, int shift, int alphabetLength) {
		while (shift < 0){
			shift += alphabetLength;
		}
		if(index<shift){
			shift = (alphabetLength+1)-shift;
			while (shift < 0){
				shift += alphabetLength;
			}
			index +=shift;
		}
		else
			index -= shift;
		index %= alphabetLength;
		return index;
	}


}