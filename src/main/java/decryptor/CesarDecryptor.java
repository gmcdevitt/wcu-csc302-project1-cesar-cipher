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

    CesarDecryptor(int offset, String cipher) {
        this.setOffset(offset);
        this.setCipher(cipher);
    }

    CesarDecryptor(int offset) {
        this.setOffset(offset);
    }

    CesarDecryptor(String cipher) {
        this.setCipher(cipher);
    }

    public CesarDecryptor() {

    }

    private void setCipher(String cipher) {
        this.cipher = cipher.toCharArray();
        this.plaintext = new char[cipher.length()];
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
            frequency[i] = Double.valueOf(new DecimalFormat("#.#").format((frequency[i] / length) * 100));
        }

        return frequency;
    }
}

