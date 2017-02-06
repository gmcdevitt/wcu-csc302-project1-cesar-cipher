package decryptor;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.CharUtils;

import java.text.DecimalFormat;

public class CesarDecryptor implements Decryptor {
    private Integer offset;
    private char[] plaintext;
    private char[] cipher;

    //Now I know my abc's
    private char[] alphabet =
            {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                    'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                    's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    /**
     * Main crux of guessing an offset, taken from https://www.math.cornell.edu/~mec/2003-2004/cryptography/subs/frequencies.html
     * General frequency of each letter in the english alphabet
     */
    private double[] letterFrequencies =
            {8.12, 1.49, 2.71, 4.32, 12.02, 2.3, 2.03, 5.92, 7.31, 0.1,
                    0.69, 3.98, 2.61, 6.95, 7.68, 1.82, 0.11, 6.02,
                    6.28, 9.1, 2.88, 1.11, 2.09, 0.17, 2.11, 0.07};

    public CesarDecryptor() {

    }

    CesarDecryptor(int offset, String cipher) {
        this.setOffset(offset);
        this.setCipher(cipher.toCharArray());
    }

    CesarDecryptor(int offset) {
        this.setOffset(offset);
    }

    CesarDecryptor(String cipher) {
        this.setCipher(cipher.toCharArray());
    }

    private void setCipher(char[] cipher) {
        this.cipher = cipher;
        this.plaintext = new char[cipher.length];
    }

    private void setOffset(int offset) {
        this.offset = offset;
    }

    public void decrypt() {
        if (cipher == null) {
            throw new RuntimeException("cipher was null, please set a ciphertext via setCipher(String) and try again.");
        }

        for (int i = 0; i < cipher.length; i++) {
            plaintext[i] = getCipherCharacterByOffset(cipher[i], offset);
        }
    }

    char getCipherCharacterByOffset(char cipherChar, Integer offset) {
        if (offset == null) {
            throw new RuntimeException("Offset is null!");
        }

        char plainTextChar;
        if (CharUtils.isAsciiAlpha(cipherChar)) { //Only do the offset check if its actually a letter
            plainTextChar = alphabet[Math.abs((ArrayUtils.indexOf(alphabet, Character.toLowerCase(cipherChar)) + offset)) % 26]; //Main logic to decrypt
            plainTextChar = CharUtils.isAsciiAlphaUpper(cipherChar) ? Character.toUpperCase(plainTextChar) : plainTextChar; //Retain case sensitivity
        } else {
            plainTextChar = cipherChar;
        }

        return plainTextChar;
    }

    String getPlaintext() {
        return new String(plaintext);
    }

    /**
     * Returns the frequency of each letter in the cipher text, can be used to make an educated guess on which offset to use first.
     * @return a double[26] of the frequency of each letter in the ciphertext
     */
    double[] getLetterFrequency() {
        double[] frequency = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int length = 0; //we cant rely on cipher.length value because of special characters

        //Go through each letter in the cipher and add to the array
        for (char letter : cipher) {
            if (CharUtils.isAsciiAlpha(letter)) {
                frequency[ArrayUtils.indexOf(alphabet, Character.toLowerCase(letter))]++;
                length++;
            }
        }

        //Divide the count by the number of characters
        for (int i = 0; i < frequency.length; i++) {
            frequency[i] = Double.valueOf(new DecimalFormat("#.##").format((frequency[i] / length) * 100));
        }

        return frequency;
    }

    public int guessOffset() {
        double[] frequency = getLetterFrequency();



        return 0;
    }
}

