
public class LetterBag {
	private static final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789,.() '\"![]/%-_;?-=:"
			+ '\n' + '\r';
	private int[] letterFrequencies;
	
	public LetterBag(){
		letterFrequencies = new int[alphabet.length()];
	}
	/**
	 * adds a letter to the bag
	 * @param letter the letter to be added
	 */
	public void clear(){
		for(int i=0; i<letterFrequencies.length; i++){
			letterFrequencies[i]=0;
		}
	}
	public void add(String letter){
		String lower=letter.toLowerCase();
		int index=getIndexForLetter(lower);
		letterFrequencies[index]++;
	}
	/**
	 * returns index in alphabet of a letter
	 * @param lower lowercase alphabet letter 
	 * @return index of input letter in defined alphabet String
	 */
	private int getIndexForLetter(String lower){
		return alphabet.indexOf(lower);
	}
	/**
	 * returns total number of 'words' in bag
	 * @return total number of 'words'
	 */
	public int getTotalWords(){
		int wordCount=0;
		for(int i=0; i<letterFrequencies.length; i++){
			wordCount+=letterFrequencies[i];
		}
		return wordCount;
	}
	/**
	 * returns amount of unique 'words' in the bag
	 * @return int number of unique 'words'
	 */
	public int getNumUniqueWords(){
		int uniqueWordCount=0;
		for(int i=0; i<letterFrequencies.length; i++){
			if(letterFrequencies[i]>0)	uniqueWordCount++;
		}
		return uniqueWordCount;
	}
	/**
	 * returns number of times a letter occurs in bag
	 * @param letter letter to search bag for
	 * @return number of times input letter occurs
	 */
	public int getNumOccurances(String letter){
		return letterFrequencies[getIndexForLetter(letter)];
	}
	/**
	 * returns most frequently found letter in bag
	 * @return letter most frequently found
	 */
	public String getMostFrequent(){
		int mostFrequent=letterFrequencies[0], mostFrequentIndex=0;
		for(int i=1; i<letterFrequencies.length; i++){
			if(letterFrequencies[i]>mostFrequent){
				mostFrequent=letterFrequencies[i];
				mostFrequentIndex=i;
			}
		}
		return alphabet.substring(mostFrequentIndex, mostFrequentIndex+1);
	}
	/**
	 * returns most frequently found letter in bag that is less than frequency of input letter
	 * @param letter who's frequency the returned value's frequency must be less than or equal to
	 * @return second most frequently found letter, after input letter's frequency
	 */
	public String getMostFrequent(String a){
		int mostFrequent=letterFrequencies[getIndexForLetter(a)], mostFrequentIndex=getIndexForLetter(a);
		int currentMostFrequent=0;
		for(int i=0; i<letterFrequencies.length; i++){
			if(letterFrequencies[i]>currentMostFrequent && letterFrequencies[i]<=mostFrequent && i!=getIndexForLetter(a)){
				currentMostFrequent=letterFrequencies[i];
				mostFrequentIndex=i;
			}
		}
		return alphabet.substring(mostFrequentIndex, mostFrequentIndex+1);
	}
	/**
	 * returns "num" amount of most frequent letters in bag
	 * @param num number of most frequent letters to search for
	 * @return array with size num of most frequent letters in descending array
	 */
	public String[] getNMostFrequent(int num){
		String[] mostFrequent=new String[num];
		mostFrequent[0]=getMostFrequent();
		for(int i=1; i<num; i++){
			mostFrequent[i]=getMostFrequent(mostFrequent[i-1]);
		}
		return mostFrequent;
	}
	
}
