package decryptor;

import datasource.DataSource;
import org.apache.commons.lang3.CharUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SubstitutionDecryptor extends CharacterDecryptor implements Decryptor {
    private char[] determinedAlphabet;

    SubstitutionDecryptor(DataSource dataSource) {
        this.setCipher(dataSource.getContents());
    }

    public SubstitutionDecryptor(DataSource dataSource, char[] alphabet) {
        this.setCipher(dataSource.getContents());
        this.determinedAlphabet = alphabet;
    }

    public void decrypt() {
        Map<Character, Character> letterMapping = new HashMap<Character, Character>();
        if (determinedAlphabet == null) {
            //Do it the hard way
            letterMapping = getLetterMapping(getLetterFrequency());
            decrypt(letterMapping);
        } else {
            //Wow this is easy
            //Build the map
            for (int i = 0; i < 26; i++) {
                letterMapping.put(alphabet[i], determinedAlphabet[i]);
            }
            decrypt(letterMapping);
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

    private void decrypt(Map<Character, Character> mapping) {
        boolean isUpper;
        for (int i = 0; i < cipher.length; i++) {
            isUpper = Character.isUpperCase(cipher[i]);
            if (CharUtils.isAsciiAlpha(cipher[i])) {
                plaintext[i] = isUpper ? Character.toUpperCase(mapping.get(Character.toLowerCase(cipher[i]))) : mapping.get(Character.toLowerCase(cipher[i]));
            } else {
                plaintext[i] = cipher[i];
            }
        }
    }
}
