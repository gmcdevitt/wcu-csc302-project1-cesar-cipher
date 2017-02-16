package decryptor;

import datasource.DataSource;
import org.apache.commons.lang3.CharUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SubstitutionDecryptor extends CharacterDecryptor implements Decryptor {

    SubstitutionDecryptor(DataSource dataSource) {
        this.setCipher(dataSource.getContents());
    }

    public void decrypt() {
        Map<Character, Character> letterMapping = getLetterMapping(getLetterFrequency());
        boolean isUpper;
        for (int i = 0; i < cipher.length; i++) {
            isUpper = Character.isUpperCase(cipher[i]);
            if (CharUtils.isAsciiAlpha(cipher[i])) {
                plaintext[i] = isUpper ? Character.toUpperCase(letterMapping.get(Character.toLowerCase(cipher[i]))) : letterMapping.get(Character.toLowerCase(cipher[i]));
            } else {
                plaintext[i] = cipher[i];
            }
        }
    }

    private Map<Character, Character> getLetterMapping(double[] letterFrequency) {
        Map<Character, Character> letterMap = new HashMap<Character, Character>();
        Map<Integer, Integer> orderMap =  new HashMap<Integer, Integer>();
        double[] old = new double[26];
        System.arraycopy(letterFrequency, 0, old, 0, 26);
        Arrays.sort(letterFrequency);


        for (int i = 0; i < 26; i++) {
            orderMap.put(Arrays.binarySearch(letterFrequency, old[i]), i);
        }

        for (int i = 0; i < 26; i++) {
            int indexOfLetter = super.findClosestMatchInArray(super.letterFrequencies, letterFrequency[i]);
            letterMap.put(alphabet[i], alphabet[orderMap.get(indexOfLetter)]);
        }
        return letterMap;
    }
}
